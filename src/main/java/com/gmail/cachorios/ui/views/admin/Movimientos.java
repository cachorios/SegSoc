package com.gmail.cachorios.ui.views.admin;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.app.Context;
import com.gmail.cachorios.backend.data.entity.Movimiento;
import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.servicios.PersonaService;
import com.gmail.cachorios.backend.servicios.SegSocServices;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.core.ui.view.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.utils.LarConst;
import com.gmail.cachorios.ui.utils.renderers.DateRenderer;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = LarConst.PAGE_MOVIMIENTO, layout = MainAppLayout.class)
@Caption("Movimientos")
@Icon(VaadinIcon.ARROW_RIGHT)
public class Movimientos extends Abm<Movimiento, Abm.Model> {

    @Autowired
    public Movimientos(FilterableAbmService<Movimiento> service) {
        super("Movimiento", service);

        setWith("900px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_MOVIMIENTO);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_MOVIMIENTO;
    }

    private void configureGrid(Grid<Movimiento> grid) {
        grid.addColumn(new DateRenderer<>(Movimiento::getFecha, "dd/MM/yyyy")).setHeader("Fecha").setWidth("15%");
        grid.addColumn(Movimiento::getPersona).setHeader("Persona").setKey("persona").setWidth("40%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Movimiento> binder) {
        addLoadFormListener(e -> getPresenter().getEntidad().setUsuario(SegSocServices.getUsuarioActivo()) );

        DatePicker fecha;

        fecha = new DatePicker("Fecha");
        fecha.getElement().setAttribute("colspan", "2");
        binder.forField(fecha).withConverter(new LocalDateToDateConverter()).bind("fecha");

        SelectCompuesto<Persona> csPersona = new SelectCompuesto<>("Persona", PersonaService.class );
        csPersona.setColSpan(2L)
                .setWidthInput("150px")
                .withFind("Lista de personas")
                .addColumn(Persona::getId,"Codigo")
                .addColumn(Persona::getNombre,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csPersona.getElement().setAttribute("colspan", "3");
        binder.bind(csPersona, "persona");

        MovimientoDetalles detalles = Context.getBean(MovimientoDetalles.class);
        detalles.getElement().setAttribute("colspan", "5");
        detalles.setHeigth("150px");

        addLoadFormListener(e -> detalles.setPadre(e.getRegistroActivo()) );
        detalles.setViewSearchBar(false);
        detalles.setAccionText("Nuevo Detalle");

        Div cDetalles = new Div(detalles);
        cDetalles.setWidth("100%");

        cDetalles.getElement().setAttribute("colspan", "5");

        form.add(fecha, csPersona, cDetalles);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("12em",2),
                new FormLayout.ResponsiveStep("13em",3),
                new FormLayout.ResponsiveStep("14em",4),
                new FormLayout.ResponsiveStep("15em",5)
        );
    }
}
