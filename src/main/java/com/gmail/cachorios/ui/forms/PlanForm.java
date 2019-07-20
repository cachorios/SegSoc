package com.gmail.cachorios.ui.forms;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.data.entity.Plan;
import com.gmail.cachorios.backend.servicios.ParametroTipoService;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.enums.ETipoParametro;
import com.gmail.cachorios.core.ui.data.util.converter.ImporteConverter;
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

@Route(value = LarConst.PAGE_PLAN, layout = MainAppLayout.class)
@Caption("PlanForm")
@Icon(VaadinIcon.SITEMAP)
public class PlanForm extends Abm<Plan, TemplateModel> {

    @Autowired
    public PlanForm(FilterableAbmService<Plan> service) {
        super("Plan", service);

        setWith("500px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_PLAN);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_PLAN;
    }

    private void configureGrid(Grid<Plan> grid) {
        grid.addColumn(Plan::getNombre).setHeader("Nombre").setWidth("60%");
        grid.addColumn(Plan::getMonto).setHeader("Monto").setKey("monto").setWidth("40%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Plan> binder) {
        TextField monto;

        SelectCompuesto<Parametro> csNombre = new SelectCompuesto<>("Nombre", ParametroTipoService.class );
        ((ParametroTipoService) csNombre.getService()).setTipo(ETipoParametro.PLAN);
        csNombre.setColSpan(3L)
                .setWidthInput("150px")
                .withFind("Lista de Planes")
                .addColumn(Parametro::getId,"Codigo")
                .addColumn(Parametro::getNombre,"Nombre")
                //     .withVer()
                //.withAdd()
                .withClear();
        csNombre.getElement().setAttribute("colspan", "1");
        binder.bind(csNombre, "nombre");

        monto = new TextField("Monto");
        monto.getElement().setAttribute("colspan", "1");
        binder.forField(monto).withConverter(new ImporteConverter()).bind("monto");

        form.add(csNombre, monto);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1)/*,
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3),
                new FormLayout.ResponsiveStep("23em",4),
                new FormLayout.ResponsiveStep("24em",5)*/
        );
    }
}
