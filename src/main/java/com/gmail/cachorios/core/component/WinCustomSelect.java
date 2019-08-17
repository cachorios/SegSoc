package com.gmail.cachorios.core.ui.component;

import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.vaadin.flow.component.dialog.Dialog;

public class WinCustomSelect<T extends EntidadInterface> extends Dialog {

//    private AbstractCustomSelect<T> padre;
//    protected FilterablePageableDataProvider<T,  String> dataProvider;
//    ///private List<ColumnList> listaCols;
//    private T registroSeleccionado;
//    private Grid<T> grilla;
//    private TextField filtro = new TextField();
//    private Button cancelar, seleccionar, agregar;
//    private String titulo;
//    ///IPresentableForm<T> form;
//
//    public WinCustomSelect(String caption, FilterablePageableDataProvider<T,  String> dataProvider,
//                           List<ColumnList> listaCols, AbstractCustomSelect<T> padre) {
//
//        titulo = caption;
//        this.padre = padre;
//        this.dataProvider = dataProvider;
//        this.listaCols = listaCols;
//
//        armarVentana();
//        setShortcuts();
//    }
//
//    private void armarVentana() {
//        grilla = new Grid<>();
//
//        //setStyleName("sciolar-window");
//        setCloseOnEsc(false);
//        setCloseOnOutsideClick(false);
//
//        setWidth("680px");
//        setHeight("60vH");
//
//        /** filtro **/
//        HorizontalLayout filtroLayout = new HorizontalLayout();
//        filtroLayout.setWidth("100%");
//        filtroLayout.setHeight("50px");
//        filtroLayout.add(filtro);
//        filtro.clear();
//        filtro.setPlaceholder("Filtrar...");
//        filtro.setWidth("100%");
//
//        filtro.setValueChangeMode(ValueChangeMode.EAGER);
//        filtro.addValueChangeListener(e -> {
//            ((FilterablePageableDataProvider<T, Long, String>) grilla.getDataProvider()).setFiltro(e.getValue());
//            seleccionar.setEnabled(false);
//        });
//
//        /** fin filtro **/
//
//        /** Botonera **/
//        seleccionar = new Button(C.CRUD_FORM_BTN_ELEGIR, e -> {
//            padre.setValue(registroSeleccionado);
//            close();
//        });
//        seleccionar.getElement().setAttribute("theme", "primary");
//        seleccionar.setWidth("120px");
//        seleccionar.setEnabled(false);
//
//        cancelar = new Button(C.CRUD_FORM_BTN_CANCELAR, clickEvent -> close());
//        cancelar.setWidth("12px%");
//        cancelar.getElement().setAttribute("theme", "primary");
//
//
//        HorizontalLayout botonera = new HorizontalLayout();
//        botonera.setWidth("100%");
//        botonera.setHeight("50px");
//
//        if (padre.conAdd()) {
//            agregar = new Button(C.CRUD_MSG_AGREGAR, e -> agregarElemento());
//            agregar.setWidth("120px");
//            agregar.setEnabled(true);
//            agregar.getElement().setAttribute("theme", "primary");
//
//            botonera.add(agregar, seleccionar, cancelar);
//            botonera.setAlignSelf(FlexComponent.Alignment.END, agregar);
//            botonera.setAlignItems(FlexComponent.Alignment.END);
//        } else {
//            botonera.add(seleccionar, cancelar);
//            botonera.setAlignSelf(FlexComponent.Alignment.END, seleccionar, cancelar);
//        }
//
//
//        /** fin botonera **/
//
//
//        grilla.setSelectionMode(Grid.SelectionMode.SINGLE);
//        ((GridSingleSelectionModel<T>) grilla.getSelectionModel()).setDeselectAllowed(false);
//        grilla.setDataProvider(dataProvider);
//        if (listaCols != null) {
//            listaCols.forEach(col -> {
//                if (col.tieneFormato()) {
//                    grilla.addColumn(col.getProveedor()).setHeader(col.getTitulo()).setSortable(col.isOrdenable())
//                          .setId(col.getPropiedad());
//                    ////.setHidable(true)
//                    ////.setRenderer(col.getRenderer());
//
//                } else {
//                    grilla.addColumn(col.getProveedor()).setHeader(col.getTitulo()).setSortable(col.isOrdenable())
//                          .setKey(col.getPropiedad());
//                    ////.setHidable(true);
//
//                }
//            });
//        }
//        grilla.deselectAll();
////        grilla.setSizeFull();
//        grilla.getDataProvider().refreshAll();
//        grilla.addSelectionListener(e -> {
//            if (e.isFromClient() && e.getFirstSelectedItem().isPresent()) {
//                seleccionar.setEnabled(true);
//                registroSeleccionado = e.getFirstSelectedItem().get();
//            } else {
//                seleccionar.setEnabled(false);
//                registroSeleccionado = null;
//            }
//        });
//
//        Label lblTitulo = new Label(titulo);
//        lblTitulo.setClassName("custom-select-titulo");
//
//        VerticalLayout layout = new VerticalLayout();
//        layout.setClassName("custom-select");
//
//        layout.add(lblTitulo,filtroLayout, grilla, botonera);
//        layout.setFlexGrow(0, filtroLayout);
//        layout.setFlexGrow(0, botonera);
//        layout.setFlexGrow(1, grilla);
//        layout.expand(grilla);
//
//        add(layout);
//    }
//
//    private void agregarElemento() {
//        form = padre.getFormAgregar();
//        form.setExecutableOnSaveOK(() -> {
//            padre.setValue(form.getObjetoActivo());
//            close();
//        });
//        form.iniciar(EModoVista.NUEVO, padre.getItemAdd());
//    }
//
//    private void setShortcuts() {
//        ////filtro.addShortcutListener(new FocusShortcut(filtro, ShortcutAction.KeyCode.F2));
//        ////seleccionar.setClickShortcut(ShortcutAction.KeyCode.ENTER);
//        ////cancelar.setClickShortcut(ShortcutAction.KeyCode.F12);
//    }

}
