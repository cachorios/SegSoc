package com.gmail.cachorios.core.ui.component;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;

import java.util.*;
import java.util.stream.Collectors;

public class MuchoaMuchoGrid<T> extends AbstractCompositeField<VerticalLayout, MuchoaMuchoGrid<T>, Set<T>> implements HasValueChangeMode {
    private final Grid<T> gridIzq = new Grid<>();
    private final Grid<T> gridDer = new Grid<>();

    private final ListDataProvider<T> izqDP;
    private final ListDataProvider<T> derDP;

    private final Button agregarButton = new Button();
    private final Button quitarButton = new Button();
    private final Button agregarTodoButton = new Button();
    private final Button quitarTodoButton = new Button();

    private final VerticalLayout contenedorBotones = new VerticalLayout();
    private final Label caption = new Label();

    public MuchoaMuchoGrid(final ListDataProvider<T> dataProvider) {
        super(null);
        izqDP = dataProvider;
        gridIzq.setDataProvider(izqDP);

        derDP = DataProvider.ofCollection(new LinkedHashSet<>());
        gridDer.setDataProvider(derDP);
        derDP.addDataProviderListener(e -> {
            this.setModelValue(new LinkedHashSet(derDP.getItems()), true);
        });

        gridIzq.setSelectionMode(Grid.SelectionMode.MULTI);
        gridDer.setSelectionMode(Grid.SelectionMode.MULTI);

        agregarButton.setIcon(VaadinIcon.ANGLE_RIGHT.create());
        agregarButton.setWidth("2em");

        quitarButton.setIcon(VaadinIcon.ANGLE_LEFT.create());
        quitarButton.setWidth("2em");

        agregarTodoButton.setIcon(VaadinIcon.ANGLE_DOUBLE_RIGHT.create());
        agregarTodoButton.setWidth("2em");

        quitarTodoButton.setIcon(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
        quitarTodoButton.setWidth("2em");

        contenedorBotones.add(agregarButton, quitarButton);
        contenedorBotones.setSpacing(false);
        contenedorBotones.setMargin(false);
        contenedorBotones.setSizeUndefined();
        contenedorBotones.setClassName("m2m");

        HorizontalLayout contenido = new HorizontalLayout(gridIzq, contenedorBotones, gridDer);
        contenido.setSizeFull();
        contenido.setMargin(false);
        contenido.setSpacing(false);

        getContent().add(caption, contenido);

        getContent().setSizeFull();
        getContent().setMargin(false);
        getContent().setSpacing(false);
        gridIzq.setSizeFull();
        gridDer.setSizeFull();

        getContent().setFlexGrow(1, gridIzq, gridDer);

        agregarButton.addClickListener(e -> actualizarSeleccion(new LinkedHashSet<>(gridIzq.getSelectedItems()), new HashSet<>()));
        agregarTodoButton.addClickListener(e -> {
            izqDP.getItems().stream().forEach(gridIzq.getSelectionModel()::select);
            actualizarSeleccion(new LinkedHashSet<>(gridIzq.getSelectedItems()), new HashSet<>());
        });

        quitarButton.addClickListener(e -> actualizarSeleccion(new HashSet<>(), new LinkedHashSet<>(gridDer.getSelectedItems())));
        quitarTodoButton.addClickListener(e -> {
            derDP.getItems().stream().forEach(gridDer.getSelectionModel()::select);
            actualizarSeleccion(new HashSet<>(), new LinkedHashSet<>(gridDer.getSelectedItems()));
        });

        setValueChangeMode(ValueChangeMode.ON_CHANGE);
    }

    public MuchoaMuchoGrid(final Collection<T> valores) {
        this(DataProvider.ofCollection(new LinkedHashSet<>(valores)));
    }

    public MuchoaMuchoGrid(final String caption, final ListDataProvider<T> dataProvider) {
        this(dataProvider);
        setCaption(caption);
    }

    public MuchoaMuchoGrid(final String caption, final Collection<T> valores) {
        this(caption, DataProvider.ofCollection(new LinkedHashSet<>(valores)));
    }

    public void setCaption(String text) {
        caption.setHeight("1.8rem");
        caption.setText(text);
    }

    public <V> MuchoaMuchoGrid<T> addColumn(final ValueProvider<T, V> columna, final String titulo) {
        gridIzq.addColumn(columna).setHeader(titulo);
        gridDer.addColumn(columna).setHeader(titulo);
        return this;
    }

    public MuchoaMuchoGrid<T> leyendaIzquierdo(final String leyenda) {
        //gridIzq.setCaption(leyenda);
        return this;
    }

    public MuchoaMuchoGrid<T> leyendaDerecha(final String leyenda) {
        ////gridDer.setCaption(leyenda);
        return this;
    }

    public MuchoaMuchoGrid<T> altoFila(String sfila) {
        //        gridIzq.setHeight(sfila);
        //        gridDer.setHeight(sfila);
        getContent().setHeight(sfila);
        return this;
    }

    public MuchoaMuchoGrid<T> verAgregarTodo() {
        contenedorBotones.add(agregarTodoButton); //0);
        return this;
    }

    public MuchoaMuchoGrid<T> verQuitarTodo() {
        contenedorBotones.add(quitarTodoButton); //,contenedorBotones.getComponentCount());
        return this;
    }

    public MuchoaMuchoGrid<T> sizeFull() {
        getContent().setSizeFull();
        return this;
    }

    public void actualizarSeleccion(final Set<T> itemAgregados, final Set<T> itemQuitados) {
        izqDP.getItems().addAll(itemQuitados);
        izqDP.getItems().removeAll(itemAgregados);
        izqDP.refreshAll();

        derDP.getItems().addAll(itemAgregados);
        derDP.getItems().removeAll(itemQuitados);
        derDP.refreshAll();

        gridIzq.getSelectionModel().deselectAll();
        gridDer.getSelectionModel().deselectAll();
    }

    public void setValue(Set<T> o) {
        Objects.requireNonNull(o);
        final Set<T> nuevosValores = o.stream()
                                      .map(Objects::requireNonNull)
                                      .collect(Collectors.toCollection(LinkedHashSet::new));

        actualizarSeleccion(nuevosValores, new LinkedHashSet<>(gridIzq.getSelectedItems()));
    }

    @Override
    public Set<T> getValue() {
        //return Collections.unmodifiableSet(new LinkedHashSet<>(derDP.getItems() ));
        return new LinkedHashSet<>(derDP.getItems());
    }

    @Override
    protected void setPresentationValue(Set<T> t) {
        this.setModelValue(new LinkedHashSet<>(derDP.getItems()), true);
    }

    @Override
    public ValueChangeMode getValueChangeMode() {
        return null;
    }

    @Override
    public void setValueChangeMode(ValueChangeMode valueChangeMode) {

    }
}
