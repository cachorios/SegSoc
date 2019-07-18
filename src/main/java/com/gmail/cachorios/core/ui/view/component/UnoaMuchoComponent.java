package com.gmail.cachorios.core.ui.view.component;

import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.core.ui.data.AbstractEntityId;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.internal.AbstractFieldSupport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag("uno-mucho-component")
@HtmlImport("frontend://src/components/uno-mucho-component.html")
public class UnoaMuchoComponent<T extends AbstractEntityId, P extends AbstractEntityId> extends Div
        implements HasValueAndElement<ComponentValueChangeEvent<UnoaMuchoComponent<T,P>, List<T>>, List<T>> {
    private Abm padre;
    private T registroActivo;
    private List<Button> botones;

    protected Button verButton      = new Button("Ver");
    protected Button nuevoButton    = new Button("Nuevo");
    protected Button editarButton   = new Button("Editar");
    protected Button borrarButton   = new Button("Quitar");
    protected Grid<T> grid;
    protected String titulo;

    private Class itemClass;
    private Class formClass;
    private Abm form;

    private final AbstractFieldSupport<UnoaMuchoComponent<T,P>,List<T>> fieldSupport;

    public UnoaMuchoComponent(String titulo, Abm padre) {
        this.titulo = titulo;
        this.padre = padre;

        this.fieldSupport =  new AbstractFieldSupport<>(this, new ArrayList<>(), Objects::equals, c ->{});

        crearDiseño();
   }

    @Override
    public void setValue(List<T> items) {
        this.fieldSupport.setValue(items);
        this.grid.setItems(items);
    }

    @Override
    public List<T> getValue() {
        return fieldSupport.getValue();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ComponentValueChangeEvent<UnoaMuchoComponent<T,P>, List<T>>> valueChangeListener) {
        return fieldSupport.addValueChangeListener(valueChangeListener);
    }

    private void crearDiseño(){

        verButton.setVisible(false);
        nuevoButton.setVisible(false);
        editarButton.setVisible(false);
        borrarButton.setVisible(false);

        grid = new Grid<>();
        gridSetting();
        grid.setSizeFull();

        add(getHeader(),  grid);

    }

    private void gridSetting(){
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(e -> {
            if (e.isFromClient() && e.getFirstSelectedItem().isPresent()) {
                registroActivo = e.getFirstSelectedItem().get();
            } else {
                registroActivo = null;
            }

            buttonsState(registroActivo != null);

        });
        buttonsState(true);

    }

    protected Component getHeader(){
        Component cTitulo = getTitulo();
        Component toolBar = getToolbar();
        HorizontalLayout header = new HorizontalLayout(cTitulo, toolBar );
        header.setFlexGrow(1, cTitulo);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        return header;
    }

    private Component getTitulo() {
        if(titulo == null || titulo.isEmpty()){
            titulo="";
        }
        return new H4(titulo);
    }

    private Component getToolbar(){
        verButton   .setThemeName("primary");
        nuevoButton .setThemeName("primary");
        editarButton.setThemeName("primary");
        borrarButton.setThemeName("primary");
        HorizontalLayout hl = new HorizontalLayout(verButton, nuevoButton, editarButton, borrarButton);

        return hl;
    }

    private void buttonsState(boolean b) {
        verButton.setEnabled(b);
        editarButton.setEnabled(b);
        borrarButton.setEnabled(b);
    }

    private Abm getForm(){
        if(form  == null){
            form = (Abm) ApplicationContextProvider.getApplicationContext().getBean(formClass);;
        }
        return form;
    }

    public <U extends Abm> UnoaMuchoComponent<T, P> withForm(Class<U> form){
        this.formClass = form;
        return this;
    }

    public UnoaMuchoComponent<T, P> withVer(){
        if(formClass != null){
            verButton.setVisible(true);
        }else{
            Notification.show("No se ha definido un formClass para mostrarInnerLayout los datos");
        }
        return this;
    }

    public UnoaMuchoComponent<T, P> withNuevo(Class<T> itemClass){
        if(formClass != null){
            nuevoButton.setVisible(true);
            this.itemClass = itemClass;
        }else{
            Notification.show("No se ha definido un formClass para mostrarInnerLayout los datos");
        }
        return this;
    }

    public UnoaMuchoComponent<T, P> withEditar(){
        if(formClass != null){
            editarButton.setVisible(true);
        }else{
            Notification.show("No se ha definido un formClass para mostrarInnerLayout los datos");
        }
        return this;
    }

    public UnoaMuchoComponent<T, P> withBorrar(){
        borrarButton.setVisible(true);
        return this;
    }

    public Grid<T> getGrid(){
        return grid;
    }

    public UnoaMuchoComponent<T, P> iniciar(){
        this.acciones();
        // getForm().setExecutableOnSaveOK(this::onFormSaveOK);
        return this;
    }

    private void acciones(){
        if(verButton.isVisible()){
            verButton.addClickListener(e -> verAcction(e));
        }
        if(editarButton.isVisible()){
            editarButton.addClickListener(e -> editarAcction(e));
        }

        if(nuevoButton.isVisible()){
            nuevoButton.addClickListener(e -> nuevoAcction(e));
        }

        if(borrarButton.isVisible()){
            borrarButton.addClickListener(e -> borrarAcction(e));
        }
    }

    private void verAcction(ClickEvent<Button> e) {
        Abm frm = getForm();
        frm.setPadre(this);
        frm.iniciar();
    }

    private void nuevoAcction(ClickEvent<Button> e)  {
        Abm frm = getForm();
        frm.setPadre(this);
        generarNuevo(itemClass);
        frm.iniciar();
        frm.openDialog();
    }

    private void generarNuevo( Class<T> cls) {
        try {
            registroActivo = cls.newInstance();  // use reflection to create instance
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void editarAcction(ClickEvent<Button> e) {
        Abm frm = getForm();
        frm.setPadre(this);
        frm.iniciar();
    }

    private void borrarAcction(ClickEvent<Button> e) {
        fieldSupport.getValue().remove(registroActivo);
        getGrid().setItems(fieldSupport.getValue());
    }
/*
    private void onFormSaveOK() {
        if (form.getModoVista().equals(EModoVista.NUEVO)) {
            fieldSupport.getValue().add(registroActivo);
        }

        this.getGrid().setItems(fieldSupport.getValue());
    }*/
}

