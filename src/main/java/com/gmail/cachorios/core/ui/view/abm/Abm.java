package com.gmail.cachorios.core.ui.view.abm;

import com.gmail.cachorios.app.HasLogger;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.util.TemplateUtil;
import com.gmail.cachorios.core.ui.view.abm.eventos.*;
import com.gmail.cachorios.core.ui.view.abm.interfaces.EntityView;
import com.gmail.cachorios.core.ui.view.component.FormButtonsBar;
import com.gmail.cachorios.core.ui.view.component.SearchBar;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.H4;

import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;


@Tag("lar-abm")
@HtmlImport("src/components/lar-abm.html")
public abstract class Abm<T extends EntidadInterface, D extends Abm.Model> extends PolymerTemplate<D> implements HasLogger, EntityView<T>, HasUrlParameter<Long> {

    public interface IAbmForm<T> {
        FormButtonsBar getButtons();
        HasText getTitle();
        FormLayout getFormLayuot();
        void setBinder(BeanValidationBinder<T> binder);
       
    }
    
    public interface Model extends TemplateModel {
        void setSeachBar(Boolean viewSeachBar);
        void setButtonText(String nuevoText);
    }
	
    
    @Id("grid")
    private Grid<T> grid;
    @Id("titulo")
    private H4 titulo;
    @Id("searchbar")
    private SearchBar searchBar;
    
    @Id("accion")
    private Button accion;

    private final Dialog dialog = new Dialog();
    private ConfirmDialog confirmation = new ConfirmDialog();
    private final IAbmForm<T> form;
    private String nombreEntidad;

    private AbmEntityPresenter<T> presenter;
    protected abstract String getBasePage();
    private final BeanValidationBinder<T> binder;
    
    private EntidadInterface padre;

    private FilterableAbmService service;

    private Usuario currentUsuario;

    public Abm(String nombreEntidad, FilterableAbmService<T> service) {
        
        this.service = service;
        this.nombreEntidad = nombreEntidad;
        this.form = new AbmForm<>();
        
        service.setPadre(null);
        binder = new BeanValidationBinder<>(service.getBeanType());
        crearForm(form.getFormLayuot() , binder);
        
        grid.setDataProvider(getDataProvider());
        
        dialog.add((Component) this.form);
//        dialog.setWidth("450px");
        dialog.setHeight("100%");
        accion.setVisible(false);
        configurarListener();
    }
    
    public void setViewSearchBar(boolean viewSearchBar){
        searchBar.setVisible(viewSearchBar);
        accion.setVisible(!viewSearchBar);
    }
    public void setAccionText(String text){
        getModel().setButtonText(text);
    }
    
    protected DataProvider getDataProvider(){
        return new AbmDataProvider(service);
    }
    

    public void setWith(String w){
        dialog.setWidth(w);
    }

    public void setHeigth(String h){
        dialog.setHeight("h");
    }


    protected abstract void crearForm(FormLayout formLayuot, BeanValidationBinder<T> binder);


    public IAbmForm<T> getForm() {
        return form;
    }

    public void configurarListener(){
        
        searchBar.addFilterChangeListener(e->getPresenter().filter(searchBar.getFilter()));
        searchBar.setActionText("Nuevo "+this.nombreEntidad);
        searchBar.addActionClickListener(e->  getPresenter().crearNuevo() );
        
        getGrid().addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(entity -> {
                fireEvent(new RowFocusChangedEvent(this, false, entity ));
                if(padre==null) {
                    navigateToEntity(entity.getId().toString());
                }else{
                    getPresenter().cargarEntidad(entity.getId());
                }
                getGrid().deselectAll();
            });
        });

        getForm().getButtons().addSaveListener(e -> getPresenter().save());
        getForm().getButtons().addDeleteListener(e -> getPresenter().delete());
        getForm().getButtons().addCancelListener(e -> getPresenter().cancel());

        getDialog().getElement().addEventListener("opened-changed", e -> {
            if(!getDialog().isOpened()) {
                  getPresenter().cancel();
            }
        });

        getBinder().addValueChangeListener(e -> getPresenter().onValueChange(isDirty()));
    }
	
	
	
	protected void navigateToEntity(String id) {
        getUI().ifPresent(ui -> ui.navigate(TemplateUtil.generateLocation(getBasePage(), id)));
    }

    @Override
    public ConfirmDialog getConfirmDialog() {
        return confirmation;
    }
    @Override
    public void setConfirmDialog(ConfirmDialog confirmDialog) {
        this.confirmation = confirmDialog;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent,@OptionalParameter Long id) {
        if (id != null) {
            getPresenter().cargarEntidad(id);
        } else if (getDialog().isOpened()) {
            getPresenter().closeSilently();
        }
    }

    public Dialog getDialog(){
        return dialog;
    }

    public void closeDialog() {
        getDialog().setOpened(false);
        if(padre == null) {
            navigateToEntity(null);
        }
    }

    public void openDialog() {
        getDialog().setOpened(true);
    }

    public void actualizarTitulo(boolean nuevo){
        getForm().getTitle().setText( (nuevo ? "Nuevo " : "Editar ") + nombreEntidad );

    }

    @Override
    public void write(T entity) throws ValidationException {
        getBinder().writeBean(entity);
    }

    @Override
    public boolean isDirty() {
        return getBinder().hasChanges();
    }

    @Override
    public void clear() {
        getBinder().readBean(null);
    }

    @Override
    public String getEntityName() {
        return nombreEntidad;
    }

    protected BeanValidationBinder<T> getBinder() {
        return binder;
    }

    public void setCurrentUsuario(Usuario currentUsuario){
        this.currentUsuario = currentUsuario;
    }

    public AbmEntityPresenter<T>    getPresenter(){
        if(presenter == null){
            presenter = new AbmEntityPresenter<>(service, currentUsuario);
            presenter.setView(this);
        }

        return presenter;
    }

    public void iniciar(String titulo){
        this.titulo.setText(titulo);
        iniciar();
    }

    public void iniciar(){

        searchBar.setPlaceHolder("Busqueda");
        searchBar.setActionText("Nuevo Registro");
        searchBar.setCheckboxText("Limpiar");

        grid.getElement().setAttribute("slot", "grid");
        grid.setClassName("grid");

        dialog.getElement().addAttachListener(event -> UI.getCurrent().getPage().executeJavaScript(
                "$0.$.overlay.setAttribute('theme', 'right-form');", dialog.getElement()));

    }



    public Class<T> getEntidadType() {
        return service.getBeanType();
    }

    public Grid<T> getGrid() {
        return grid;
    }
    
    public EntidadInterface getPadre() {
        return padre;
    }
    
    public void setPadre(EntidadInterface padre) {
        if(padre != null){
            this.padre = padre;
            //service.setPadre(padre);
            grid.setDataProvider( getDataProvider() );
            
        }
        
        
        
    }
    
    /***
     * Eventos
     * */

	public void fireEvent(ComponentEvent<?> componentEvent) {
		super.fireEvent(componentEvent);
	}
    
    public Registration addPreUpdateListener(ComponentEventListener<PreUpdateEvent> listener){
        return addListener(PreUpdateEvent.class, listener);
    }
	 
    public Registration addRowFocusChanged(ComponentEventListener<RowFocusChangedEvent> listener){
        return addListener(RowFocusChangedEvent.class, listener);
    }
    public Registration addLoadFormListener(ComponentEventListener<LoadFormEvent> listener){
        return addListener(LoadFormEvent.class, listener);
    }
    
    public Registration addPostUpdateListener(ComponentEventListener<PostUpdateEvent> listener){
        return addListener(PostUpdateEvent.class, listener);
    }
    public Registration addPostDeleteListener(ComponentEventListener<PostDeleteEvent> listener){
        return addListener(PostDeleteEvent.class, listener);
    }
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener){
        return addListener(SaveEvent.class, listener);
    }
    
}
