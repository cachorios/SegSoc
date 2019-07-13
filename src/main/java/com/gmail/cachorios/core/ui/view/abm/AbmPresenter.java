package com.gmail.cachorios.core.ui.view.abm;

import com.gmail.cachorios.core.ui.data.AbstractEntity;
import com.gmail.cachorios.backend.data.entity.User;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

public class AbmPresenter<T extends AbstractEntity> {
    private FilterablePageableDataProvider<T, String> filteredDataProvider;

    public AbmPresenter(FilterableAbmService<T> crudService, User currentUser) {
        this.filteredDataProvider = new AbmDataProvider<>(crudService);
    }
}
