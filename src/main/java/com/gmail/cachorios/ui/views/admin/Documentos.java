package com.gmail.cachorios.ui.views.admin;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.entity.Documento;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.enums.ETipoDocumento;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.ui.MainView;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = LarConst.PAGE_DOCUMENTOS, layout = MainView.class)
@Caption("Documentos")
@Icon(VaadinIcon.MODAL_LIST)
public class Documentos extends Abm<Documento, Abm.Model> {

    @Autowired
    public Documentos(FilterableAbmService<Documento> service) {
        super("Documento", service);

        setWith("1000px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_DOCUMENTO);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_DOCUMENTOS;
    }

    private void configureGrid(Grid<Documento> grid) {
        grid.addColumn(Documento::getDescripcion).setHeader("Descripcion").setWidth("15%");
        grid.addColumn(Documento::getTipo).setHeader("Tipo").setKey("tipo").setWidth("40%");
    }

    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Documento> binder) {
        TextField descripcion;
        ComboBox<ETipoDocumento> cbDocs;

        descripcion = new TextField("Descripcion");
        descripcion.getElement().setAttribute("colspan", "2");
        binder.bind(descripcion,"descripcion");

        cbDocs = new ComboBox<>("Tipo");
        cbDocs.getElement().setAttribute("colspan", "2");
        cbDocs.setItems(ETipoDocumento.values());

        form.add(descripcion, cbDocs);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2)
        );
    }
}
