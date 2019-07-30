package com.gmail.cachorios.core.ui.view.abm;


import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.view.abm.eventos.LoadFormEvent;
import com.gmail.cachorios.core.ui.view.abm.eventos.PostUpdateEvent;
import com.gmail.cachorios.core.ui.view.abm.eventos.PreUpdateEvent;
import com.gmail.cachorios.core.ui.view.abm.eventos.SaveEvent;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;


public class AbmEntityPresenter<T extends EntidadInterface>  extends EntityPresenter<T,Abm<T,?>>  {
    private FilterablePageableDataProvider<T, String> filteredDataProvider;


    public AbmEntityPresenter(FilterableAbmService<T> crudService, Usuario currentUsuario) {
        super(crudService, currentUsuario);
        this.filteredDataProvider = new AbmDataProvider<>(crudService);
    }

    @Override
    public void setView(Abm<T,?> view) {
        super.setView(view);
        view.getGrid().setDataProvider(filteredDataProvider);
    }


    public void filter(String filter) {
        filteredDataProvider.setFilter(filter);
    }

    public void cancel() {
        cancel(this::closeSilently, getView()::openDialog);
    }

    public void closeSilently() {
        close();
        getView().closeDialog();
    }

    @Override
    public T crearNuevo() {
        return  open(super.crearNuevo());
    }

    public void cargarEntidad(Long id){
        cargarEntidad(id, this::open);
    }

    private T open(T entidad){
        getView().fireEvent(new LoadFormEvent(getView(),false, entidad));
        getView().getBinder().readBean(entidad);
        getView().getForm().getButtons().setSaveDisabled(true);
        getView().getForm().getButtons().setDeleteDisabled(esNuevo());
        getView().actualizarTitulo(esNuevo());
        getView().openDialog();

        return entidad;
    }

    public void save() {
        if (writeEntity()) {
            getView().fireEvent(new PreUpdateEvent(getView(), false, getEntidad()));
    
            if (getView().getPadre() == null) {
                super.save(e -> {
                    if (esNuevo()) {
                        getView().showCreatedNotification();
                        filteredDataProvider.refreshAll();
                    } else {
                        getView().showUpdatedNotification();
                        filteredDataProvider.refreshItem(e);
                    }
                    closeSilently();
                });
    
                getView().fireEvent(new PostUpdateEvent(getView(), false, getEntidad()));
            }else{
                getView().fireEvent(new SaveEvent(getView(), false, getEntidad()));
                closeSilently();
            }
            
        }
    }

    public void delete() {
        super.delete(e -> {
            getView().showDeletedNotification();
            filteredDataProvider.refreshAll();
            closeSilently();
        });
    }

    public void onValueChange(boolean isDirty) {
        getView().getForm().getButtons().setSaveDisabled(!isDirty);
    }



}
