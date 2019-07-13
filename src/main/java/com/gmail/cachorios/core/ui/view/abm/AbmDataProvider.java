package com.gmail.cachorios.core.ui.view.abm;


import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import java.util.List;

public class AbmDataProvider<T extends EntidadInterface> extends FilterablePageableDataProvider<T, String> {
    private final FilterableAbmService<T> crudService;
    private List<QuerySortOrder> defaultSortOrders;


    public AbmDataProvider(FilterableAbmService<T> crudService) {
        this.crudService = crudService;
        setSortOrders();
    }

    private void setSortOrders() {
        QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
        builder.thenAsc("id");
        defaultSortOrders = builder.build();
    }

    @Override
    protected Page<T> fetchFromBackEnd(Query<T, String> query, Pageable pageable) {
        return crudService.findAnyMatching(query.getFilter(), pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return defaultSortOrders;
    }

    @Override
    protected int sizeInBackEnd(Query<T, String> query) {
        return (int) crudService.countAnyMatching(query.getFilter());
    }

}
