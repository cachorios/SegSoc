package com.gmail.cachorios.ui.views.admin;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.Role;
import com.gmail.cachorios.backend.data.entity.Producto;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.view.abm.Abm;
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
import org.springframework.security.access.annotation.Secured;

@Route(value = LarConst.PAGE_PRODUCTO, layout = MainAppLayout.class)
@Caption("Productos")
@Icon(VaadinIcon.BARCODE)
@Secured(Role.ADMIN)
public class Productos extends Abm<Producto, Abm.Model> {

    @Autowired
    public Productos(FilterableAbmService<Producto> service) {
        super("Producto", service);

        setWith("500px");
        configureGrid(this.getGrid());
        this.iniciar(LarConst.TITULO_PRODUCTO);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_PRODUCTO;
    }

    private void configureGrid(Grid<Producto> grid) {
        grid.addColumn(Producto::getDescripcion).setHeader("Descripcion").setWidth("15%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Producto> binder) {
        TextField descripcion;

        descripcion = new TextField("Descripcion");
        descripcion.getElement().setAttribute("colspan", "1");
        binder.bind(descripcion, "descripcion");

        form.add(descripcion);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1)/*,
                new FormLayout.ResponsiveStep("18em",2),
                new FormLayout.ResponsiveStep("19em",3),
                new FormLayout.ResponsiveStep("20em",4),
                new FormLayout.ResponsiveStep("21em",5)*/
        );
    }
}
