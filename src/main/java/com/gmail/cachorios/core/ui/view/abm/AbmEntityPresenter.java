package com.gmail.cachorios.core.ui.view.abm;


import com.gmail.cachorios.backend.data.entity.User;
import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;


public class AbmEntityPresenter<T extends EntidadInterface>  extends EntityPresenter<T,Abm<T,?>>  {
    private FilterablePageableDataProvider<T, String> filteredDataProvider;


    public AbmEntityPresenter(FilterableAbmService<T> crudService, User currentUser) {
        super(crudService, currentUser);
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
        getView().getBinder().readBean(entidad);
        getView().getForm().getButtons().setSaveDisabled(true);
        getView().getForm().getButtons().setDeleteDisabled(esNuevo());
        getView().actualizarTitulo(esNuevo());
        getView().openDialog();

        return entidad;
    }

    public void save() {
        if (writeEntity()) {
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