package com.gmail.cachorios.ui.views.admin;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.repositorios.ParametroRepositorio;
import com.gmail.cachorios.backend.servicios.ParametroService;
import com.gmail.cachorios.core.ui.data.enums.ETipoParametro;
import com.gmail.cachorios.core.ui.data.util.converter.BooleanConverter;
import com.gmail.cachorios.core.ui.data.util.converter.ImporteConverter;
import com.gmail.cachorios.core.ui.data.util.converter.IntegerConverter;
import com.gmail.cachorios.core.ui.data.util.converter.LongConverter;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = LarConst.PAGE_PARAMETRO, layout = MainAppLayout.class)
@Caption("Parametros")
@Icon(VaadinIcon.OPTIONS)
public class Parametros extends Abm<Parametro, Abm.Model> {

    @Autowired
    public Parametros(ParametroService service) {
        super("Parametro", service);

        setWith("1000px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_PARAMETRO);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_PARAMETRO;
    }

    private void configureGrid(Grid<Parametro> grid) {
        grid.addColumn(Parametro::getNombre).setHeader("Nombre").setWidth("15%");
        grid.addColumn(Parametro::getClase).setHeader("Clase").setKey("clase").setWidth("40%");
        grid.addColumn(Parametro::getOrden).setHeader("Orden").setKey("orden").setWidth("40%");
    }

    private void setOrden(AbstractField.ComponentValueChangeEvent e, ETipoParametro eTipoParametro, TextField campo) {
        if(e.isFromClient()) {
            ParametroRepositorio parametroRepositorio = ApplicationContextProvider.getApplicationContext().getBean(ParametroRepositorio.class);
            int maxOrden = parametroRepositorio.getMaxOrdenByTipo(eTipoParametro);
            // todo: Ver por que el ENUM envia tipo dato 'bytea' a la BD
            campo.setValue(String.valueOf(++maxOrden));
        }
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Parametro> binder) {
        TextField clase, orden, nombre, valorint, valordob, valorstr, valorbol, valorchr;
        DatePicker valordat;
        ComboBox<ETipoParametro> tipoComboBox;

        nombre = new TextField("Nombre");
        nombre.getElement().setAttribute("colspan", "2");
        binder.bind(nombre,"nombre");

        orden = new TextField("Orden");
        orden.getElement().setAttribute("colspan", "1");
        binder.forField(orden).withConverter(new IntegerConverter()).bind("orden");

        tipoComboBox = new ComboBox<>("Tipo");
        tipoComboBox.getElement().setAttribute("colspan", "2");
        tipoComboBox.setItems(ETipoParametro.values());
        tipoComboBox.addValueChangeListener(e -> setOrden(e, e.getSource().getValue(), orden));
        binder.bind(tipoComboBox, "tipo");

        clase = new TextField("Clase");
        clase.getElement().setAttribute("colspan", "2");
        binder.bind(clase,"clase");

        valorint = new TextField("Valor entero");
        valorint.getElement().setAttribute("colspan", "2");
        binder.forField(valorint).withConverter(new LongConverter()).bind("valorint");

        valorbol = new TextField("Valor boleano");
        valorbol.getElement().setAttribute("colspan", "1");
        binder.forField(valorbol).withConverter(new BooleanConverter()).bind("valorbol");

        valordob = new TextField("Valor decimal");
        valordob.getElement().setAttribute("colspan", "2");
        binder.forField(valordob).withConverter(new ImporteConverter()).bind("valordob");

        valordat = new DatePicker("Valor fecha");
        valordat.getElement().setAttribute("colspan", "2");
        binder.forField(valordat).withConverter(new LocalDateToDateConverter()).bind("valordat");

        valorchr = new TextField("Valor caracter");
        valorchr.getElement().setAttribute("colspan", "1");
        binder.bind(valorchr, "valorchr");

        valorstr = new TextField("Valor texto");
        valorstr.getElement().setAttribute("colspan", "2");
        binder.bind(valorstr, "valorstr");

        form.add(nombre, tipoComboBox, orden, clase, valorint, valorbol, valordob, valordat, valorchr, valorstr);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3),
                new FormLayout.ResponsiveStep("23em",4),
                new FormLayout.ResponsiveStep("24em",5)
        );
    }
}
