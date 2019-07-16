package com.gmail.cachorios.ui.forms;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.servicios.ParametroService;
import com.gmail.cachorios.backend.servicios.ParametroService;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.core.ui.view.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = LarConst.PAGE_USERS, layout = MainAppLayout.class)
@Caption("ParametroForm")
@Icon(VaadinIcon.OPTIONS)
public class ParametroForm extends Abm<Parametro, TemplateModel> {

    @Autowired
    public ParametroForm(FilterableAbmService<Parametro> service) {
        super("Parametro", service);

        setWith("1600px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_PERSONA);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_PERSONA;
    }

    private void configureGrid(Grid<Parametro> grid) {
        grid.addColumn(Parametro::getNombre).setHeader("Nombre").setWidth("15%");
        grid.addColumn(Parametro::getClase).setHeader("Clase").setKey("clase").setWidth("40%");
        grid.addColumn(Parametro::getOrden).setHeader("Orden").setKey("orden").setWidth("40%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Parametro> binder) {
        TextField tipo, clase, orden, nombre, valorint, valordob, valorstr, valorbol, valordat, valorchr;

        /*

        nombre = new TextField("Nombre");
        nombre.getElement().setAttribute("colspan", "2");
        binder.bind(nombre,"nombre");

        documento = new TextField("Documento");
        documento.getElement().setAttribute("colspan", "2");
        binder.bind(documento, "documento");

        sexo = new TextField("Sexo");
        sexo.getElement().setAttribute("colspan", "1");
        binder.bind(sexo, "sexo");

        direccion = new TextField("direccion");
        direccion.getElement().setAttribute("colspan", "2");
        binder.bind(direccion, "direccion");

        descripcionDireccion = new TextField("Descripcion de direccion");
        descripcionDireccion.getElement().setAttribute("colspan", "3");
        binder.bind(descripcionDireccion, "descripcionDireccion");

        numeroPartida = new TextField("Numero de partida");
        numeroPartida.getElement().setAttribute("colspan", "2");
        binder.bind(numeroPartida, "numeroPartida");

        form.add(nombre, documento, sexo, csParametro, csParentesco, csGrupoSanguineo, csFactorRH, direccion, descripcionDireccion, numeroPartida);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3),
                new FormLayout.ResponsiveStep("23em",4),
                new FormLayout.ResponsiveStep("24em",5)
        );*/
    }
}
