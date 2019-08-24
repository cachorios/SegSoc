package com.gmail.cachorios.core.ui.abm;

import com.gmail.cachorios.core.data.EntidadInterface;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class AbmGrilla<E extends EntidadInterface> extends Grid<E> {

    private final Class<E> entidadClass;
    private final CrudFilter filtro = new CrudFilter();

    public AbmGrilla(Class<E> entidadClass) {
       // super(entidadClass); // Para cargar todos los campos
        this.entidadClass = entidadClass;
        setSelectionMode(SelectionMode.SINGLE);



        //ComponentUtil.addListener()
    }

    private void establecerFiltro(){
        HeaderRow filaFiltro = this.appendHeaderRow();

        getColumns().forEach(col ->{
            final TextField field = new TextField();
            field.getElement().setAttribute("abm-role", "busqueda");

            field.addValueChangeListener(e->{
                filtro.getConstraints().remove(col.getKey());

                if(!field.isEmpty()){
                    filtro.getConstraints().put(col.getKey(), e.getValue());
                }

                super.getDataProvider().refreshAll();
            });

            field.setValueChangeMode(ValueChangeMode.EAGER);

            filaFiltro.getCell(col).setComponent(field);
            field.setSizeFull();
            field.setPlaceholder("Filtro...");
        });
    }

    public void iniciar(){
        establecerFiltro();
    }
}
