package com.gmail.cachorios.ui.views.admin;

import com.gmail.cachorios.app.Context;
import com.gmail.cachorios.backend.data.entity.MovimientoDetalle;
import com.gmail.cachorios.backend.data.entity.Producto;
import com.gmail.cachorios.backend.servicios.ProductoService;
import com.gmail.cachorios.core.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.abm.Abm;
import com.gmail.cachorios.core.ui.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainView;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = LarConst.PAGE_MOVIMIENTODET, layout = MainView.class)
//@Caption("MovimientoDetalles")
//@Icon(VaadinIcon.MODAL_LIST)
public class MovimientoDetalles extends Abm<MovimientoDetalle, Abm.Model> {

    @Autowired
    public MovimientoDetalles(FilterableAbmService<MovimientoDetalle> service) {
        super("MovimientoDetalle", service);

        setWith("1000px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_MOVIMIENTODET);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_MOVIMIENTODET;
    }

    private void configureGrid(Grid<MovimientoDetalle> grid) {
        grid.addColumn(MovimientoDetalle::getProducto).setHeader("Producto").setWidth("15%");
        grid.addColumn(MovimientoDetalle::getProductoDescripcion).setHeader("Descripcion producto").setKey("descPro").setWidth("40%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<MovimientoDetalle> binder) {
        TextField productoDescripcion;

        productoDescripcion = new TextField("Productos");
        productoDescripcion.getElement().setAttribute("colspan", "2");
        binder.bind(productoDescripcion,"productoDescripcion");

        SelectCompuesto<Producto> csProducto = new SelectCompuesto<>("Producto", ProductoService.class );
        csProducto.setColSpan(2L)
                .setWidthInput("150px")
                .withFind("Lista de Producto")
                .addColumn(Producto::getId,"Codigo")
                .addColumn(Producto::getDescripcion,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csProducto.getElement().setAttribute("colspan", "2");
        binder.bind(csProducto, "producto");

        Documentos documentos = Context.getBean(Documentos.class);
        documentos.getElement().setAttribute("colspan", "5");
        documentos.setHeigth("150px");

        addLoadFormListener(e -> documentos.setPadre(e.getRegistroActivo()) );
        documentos.setViewSearchBar(false);
        documentos.setAccionText("Nuevo Documento");

        Div cDocumentos = new Div(documentos);
        cDocumentos.setWidth("100%");

        cDocumentos.getElement().setAttribute("colspan", "5");

        form.add(productoDescripcion, cDocumentos, csProducto);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3),
                new FormLayout.ResponsiveStep("23em",4),
                new FormLayout.ResponsiveStep("24em",5)
        );
    }
}
