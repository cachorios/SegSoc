package com.gmail.cachorios.core.ui.component.selectCompuesto;


import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.view.abm.AbmDataProvider;
import com.gmail.cachorios.core.ui.component.SearchBar;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import java.util.function.Function;

@Tag("sc-find")
@JsModule("./src/components/sc_find.js")
public class SCFind<T extends EntidadInterface> extends PolymerTemplate<TemplateModel> {
    @Id("grid")
    private Grid<T> grid;
    @Id("titulo")
    private H4 titulo;
    @Id("searchbar")
    private SearchBar searchBar;


    private FilterablePageableDataProvider<T, String> filteredDataProvider;

    public SCFind(FilterableAbmService<T> service, Function< T, T> callRet ) {
        filteredDataProvider = new AbmDataProvider<>(service);

        grid.setDataProvider(filteredDataProvider);
        searchBar.hideAction();
        searchBar.addFilterChangeListener(e-> setFilter(searchBar.getFilter()) );

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addItemDoubleClickListener( e->  callRet.apply(e.getItem()));
                //e-> seleccionar(e.getItem())  );
    }

    private void seleccionar(T item){
        System.out.println(item);
    }

    public Grid<T> getGrid(){
        return grid;
    }

    private void setFilter(String filtro){
        filteredDataProvider.setFilter(filtro);
    }

    public void setTitulo(String titulo){
        this.titulo.removeAll();
        this.titulo.setText(titulo);
    }

}
