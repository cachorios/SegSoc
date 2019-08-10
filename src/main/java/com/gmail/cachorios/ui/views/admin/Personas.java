package com.gmail.cachorios.ui.views.admin;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.app.Context;
import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.servicios.PersonaService;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.enums.EFactorRH;
import com.gmail.cachorios.core.ui.data.enums.EGrupoSanguineo;
import com.gmail.cachorios.core.ui.data.enums.EParentesco;
import com.gmail.cachorios.core.ui.data.enums.ESexo;
import com.gmail.cachorios.core.ui.data.util.converter.IntegerConverter;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.core.ui.view.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = LarConst.PAGE_PERSONA, layout = MainAppLayout.class)
@Caption("Personas")
@Icon(VaadinIcon.USER)
public class Personas extends Abm<Persona, Abm.Model> {

    @Autowired
    public Personas(FilterableAbmService<Persona> service) {
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
        grid.addColumn(Persona::getNombre).setHeader("Nombre").setWidth("60%");
        grid.addColumn(Persona::getDocumento).setHeader("Documento").setKey("documento").setWidth("30%");
        grid.addColumn(Persona::getSexo).setHeader("Sexo").setKey("sexo").setWidth("10%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Persona> binder) {
        
        TextField nombre, documento, direccion, descripcionDireccion, numeroPartida;
        ComboBox<ESexo> cbSexo;
        ComboBox<EParentesco> cbParentesco;
        ComboBox<EGrupoSanguineo> cbGrupoSanguineo;
        Planes planes = Context.getBean(Planes.class);
        
        ComboBox<EFactorRH> cbFactor;
        
        SelectCompuesto<Persona> csPersona = new SelectCompuesto<>("Familiar de...", PersonaService.class );
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
        binder.bind(cbParentesco, "parentesco");

        cbParentesco.setEnabled(csPersona.getValue() != null);
        csPersona.addValueChangeListener(e -> cbParentesco.setEnabled(csPersona.getValue() != null));

        cbGrupoSanguineo = new ComboBox<>("Grupo sanguineo");
        cbGrupoSanguineo.getElement().setAttribute("colspan", "2");
        cbGrupoSanguineo.setItems(EGrupoSanguineo.values());
        binder.bind(cbGrupoSanguineo, "grupoSanguineo");

        cbFactor = new ComboBox<>("Factor RH");
        cbFactor.getElement().setAttribute("colspan", "2");
        cbFactor.setItems(EFactorRH.values());
        binder.bind(cbFactor, "factor");

        nombre = new TextField("Nombre");
        nombre.getElement().setAttribute("colspan", "2");
        binder.bind(nombre,"nombre");

        documento = new TextField("Documento");
        documento.getElement().setAttribute("colspan", "2");
        binder.bind(documento, "documento");

        cbSexo = new ComboBox<>("Sexo");
        cbSexo.getElement().setAttribute("colspan", "1");
        cbSexo.setItems(ESexo.values());
        binder.bind(cbSexo, "sexo");

        direccion = new TextField("direccion");
        direccion.getElement().setAttribute("colspan", "2");
        binder.bind(direccion, "direccion");

        descripcionDireccion = new TextField("Descripcion de direccion");
        descripcionDireccion.getElement().setAttribute("colspan", "2");
        binder.bind(descripcionDireccion, "descripcionDireccion");

        numeroPartida = new TextField("Numero de partida");
        numeroPartida.getElement().setAttribute("colspan", "1");
        binder.forField(numeroPartida).withConverter(new IntegerConverter()).bind("numeroPartida");
    
        planes.getElement().setAttribute("colspan", "5");
        planes.setHeigth("150px");

        addLoadFormListener(e -> planes.setPadre(e.getRegistroActivo()) );
        planes.setViewSearchBar(false);
        planes.setAccionText("Nuevo Plan");

        Div cPlanes = new Div(planes);
        cPlanes.setWidth("100%");
        
        cPlanes.getElement().setAttribute("colspan", "5");
        form.add(nombre, documento, cbSexo, csPersona, cbParentesco, cbGrupoSanguineo, cbFactor, direccion, descripcionDireccion, numeroPartida, cPlanes);
        
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("18em",2),
                new FormLayout.ResponsiveStep("19em",3),
                new FormLayout.ResponsiveStep("20em",4),
                new FormLayout.ResponsiveStep("20em",5)
        );
    }
}
