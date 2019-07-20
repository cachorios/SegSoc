package com.gmail.cachorios.ui.forms;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Plan;
import com.gmail.cachorios.backend.servicios.PersonaService;
import com.gmail.cachorios.backend.servicios.PlanService;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.enums.EFactorRH;
import com.gmail.cachorios.core.ui.data.enums.EGrupoSanguineo;
import com.gmail.cachorios.core.ui.data.enums.EParentesco;
import com.gmail.cachorios.core.ui.data.util.converter.IntegerConverter;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.core.ui.view.component.MuchoaMuchoGrid;
import com.gmail.cachorios.core.ui.view.component.UnoaMuchoComponent;
import com.gmail.cachorios.core.ui.view.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = LarConst.PAGE_PERSONA, layout = MainAppLayout.class)
@Caption("PersonaForm")
@Icon(VaadinIcon.USER)
public class PersonaForm extends Abm<Persona, TemplateModel> {

    @Autowired
    public PersonaForm(FilterableAbmService<Persona> service) {
        super("Persona", service);

        setWith("1000px");

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
        ComboBox<EParentesco> cbParentesco;
        ComboBox<EGrupoSanguineo> cbGrupoSanguineo;
        ComboBox<EFactorRH> cbFactor;

        // MuchoaMuchoGrid<Plan> planes;

        /*PlanService planService = ApplicationContextProvider.getApplicationContext().getBean(PlanService.class);
        planes = new MuchoaMuchoGrid<Plan>(planService.getAll());
        planes.addColumn(Plan::getNombre,"Nombre")
                .addColumn(Plan::getMonto,"Monto")
                .altoFila("12rem")
                .leyendaIzquierdo("Disponible").leyendaDerecha("Seleccionado");*/

        UnoaMuchoComponent<Plan, Persona> planes = new UnoaMuchoComponent("Planes", this);

        planes.setHeight("300px");
        planes.getElement().setAttribute("colspan", "5");

        planes.getGrid().addColumn(Plan::getNombre).setHeader("Nombre").setFlexGrow(0);
        planes.getGrid().addColumn(Plan::getMonto).setHeader("Monto").setFlexGrow(1);

        planes.withForm(PlanForm.class)
                .withVer()
                .withNuevo(Plan.class);

        planes.iniciar();

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

        cbParentesco = new ComboBox<>("Parentesco");
        cbParentesco.getElement().setAttribute("colspan", "2");
        cbParentesco.setItems(EParentesco.values());

        cbGrupoSanguineo = new ComboBox<>("Grupo sanguineo");
        cbGrupoSanguineo.getElement().setAttribute("colspan", "2");
        cbGrupoSanguineo.setItems(EGrupoSanguineo.values());

        cbFactor = new ComboBox<>("Factor RH");
        cbFactor.getElement().setAttribute("colspan", "2");
        cbFactor.setItems(EFactorRH.values());

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
        binder.forField(numeroPartida).withConverter(new IntegerConverter()).bind("numeroPartida");

        form.add(nombre, documento, sexo, csPersona, cbParentesco, cbGrupoSanguineo, cbFactor, direccion, descripcionDireccion, numeroPartida, planes);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("18em",2),
                new FormLayout.ResponsiveStep("19em",3),
                new FormLayout.ResponsiveStep("20em",4),
                new FormLayout.ResponsiveStep("21em",5)
        );
    }
}
