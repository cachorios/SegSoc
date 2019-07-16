package com.gmail.cachorios.ui.forms;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.servicios.ParametroService;
import com.gmail.cachorios.backend.servicios.PersonaService;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.util.converter.ImporteConverter;
import com.gmail.cachorios.core.ui.data.util.converter.IntegerConverter;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.core.ui.view.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = LarConst.PAGE_USERS, layout = MainAppLayout.class)
@Caption("PersonaForm")
@Icon(VaadinIcon.USER)
public class PersonaForm extends Abm<Persona, TemplateModel> {

    @Autowired
    public PersonaForm(FilterableAbmService<Persona> service) {
        super("Persona", service);

        setWith("1600px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_PERSONA);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_PERSONA;
    }

    private void configureGrid(Grid<Persona> grid) {
        grid.addColumn(Persona::getNombre).setHeader("Nombre").setWidth("15%");
        grid.addColumn(Persona::getDocumento).setHeader("Documento").setKey("documento").setWidth("40%");
        grid.addColumn(Persona::getSexo).setHeader("Sexo").setKey("sexo").setWidth("40%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Persona> binder) {
        TextField nombre, documento, sexo, direccion, descripcionDireccion, numeroPartida;

        SelectCompuesto<Persona> csPersona = new SelectCompuesto<>("Cabeza", PersonaService.class );
        csPersona.setColSpan(3L)
                .setWidthInput("150px")
                .withFind("Lista de Persona")
                .addColumn(Persona::getId,"Codigo")
                .addColumn(Persona::getNombre,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csPersona.getElement().setAttribute("colspan", "3");
        binder.bind(csPersona, "cabeza");

        SelectCompuesto<Parametro> csParentesco = new SelectCompuesto<>("Parentesco", ParametroService.class );
        csParentesco.setColSpan(2L)
                .setWidthInput("150px")
                .withFind("Lista de Parentescos")
                .addColumn(Parametro::getId,"Codigo")
                .addColumn(Parametro::getNombre,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csParentesco.getElement().setAttribute("colspan", "2");
        binder.bind(csParentesco, "parentesco");

        SelectCompuesto<Parametro> csGrupoSanguineo = new SelectCompuesto<>("Grupo sanguineo", ParametroService.class );
        csGrupoSanguineo.setColSpan(3L)
                .setWidthInput("150px")
                .withFind("Lista de grupo sanguineo")
                .addColumn(Parametro::getId,"Codigo")
                .addColumn(Parametro::getNombre,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csGrupoSanguineo.getElement().setAttribute("colspan", "3");
        binder.bind(csGrupoSanguineo, "grupoSanguineo");

        SelectCompuesto<Parametro> csFactorRH = new SelectCompuesto<>("Factor RH", ParametroService.class );
        csFactorRH.setColSpan(2L)
                .setWidthInput("150px")
                .withFind("Lista de factores RH")
                .addColumn(Parametro::getId,"Codigo")
                .addColumn(Parametro::getNombre,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csFactorRH.getElement().setAttribute("colspan", "2");
        binder.bind(csFactorRH, "factor");

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

        form.add(nombre, documento, sexo, csPersona, csParentesco, csGrupoSanguineo, csFactorRH, direccion, descripcionDireccion, numeroPartida);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3),
                new FormLayout.ResponsiveStep("23em",4),
                new FormLayout.ResponsiveStep("24em",5)
        );
    }
}
