package com.gmail.cachorios.ui.views.admin;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.entity.Documento;
import com.gmail.cachorios.backend.data.entity.MovimientoDetalle;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.enums.ETipoDocumento;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.gmail.cachorios.core.ui.view.component.DocumentoACU;
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

@Route(value = LarConst.PAGE_DOCUMENTOS, layout = MainAppLayout.class)
@Caption("Documentos")
@Icon(VaadinIcon.MODAL_LIST)
public class Documentos extends Abm<Documento, Abm.Model> {

    @Autowired
    public Documentos(FilterableAbmService<Documento> service) {
        super("Documento", service);

        setWith("900px");

        configureGrid(this.getGrid());

        this.iniciar(LarConst.TITULO_DOCUMENTO);
    }

    @Override
    public void configurarListener() {
        super.configurarListener();
        addPreUpdateListener(e -> {
            if(getPadre()!= null) {
                getPresenter().getEntidad().setMovimientoDetalle((MovimientoDetalle) getPadre());
            }
        });
//		addSaveListener(e -> ((Persona)getPadre()).addPlan((Plan)(e.getRegistroActivo())) );
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
        TextField descripcion, nombreArchivo, mimeType;
        ComboBox<ETipoDocumento> cbDocs;

        descripcion = new TextField("Descripcion");
        descripcion.getElement().setAttribute("colspan", "2");
        binder.bind(descripcion,"descripcion");

        nombreArchivo = new TextField("");
        binder.bind(nombreArchivo,"nombreArchivo");

        mimeType = new TextField("");
        binder.bind(nombreArchivo,"mimeType");

        cbDocs = new ComboBox<>("Tipo");
        cbDocs.getElement().setAttribute("colspan", "1");
        cbDocs.setItems(ETipoDocumento.values());
        binder.bind(cbDocs, "tipo");

        DocumentoACU documentoACU = new DocumentoACU("Documento", this, true);
        documentoACU.addValueChangeListener(e -> setNombreArchivo(documentoACU, nombreArchivo, mimeType));

        addLoadFormListener(e -> setFile(documentoACU));

        form.add(descripcion, cbDocs, documentoACU);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3)
        );
    }

    private void setNombreArchivo(DocumentoACU doc, TextField nombreArchivo, TextField mimeType) {
        if(!doc.getDescripcion().isEmpty()) {
            nombreArchivo.setValue(doc.getDescripcion());
            mimeType.setValue(doc.getMimeType());
        }
    }

    private void setFile(DocumentoACU documentoACU) {
        if(!getPresenter().getEntidad().isNew()) {
            documentoACU.setFile(getPresenter().getEntidad());
        }
    }
}
