package com.gmail.cachorios.core.ui.view.abm;

import com.gmail.cachorios.core.ui.data.AbstractEntity;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

public class AbmPresenter<T extends AbstractEntity> {
    private FilterablePageableDataProvider<T, String> filteredDataProvider;

    public AbmPresenter(FilterableAbmService<T> crudService, Usuario currentUsuario) {
        this.filteredDataProvider = new AbmDataProvider<>(crudService);
    }
}
