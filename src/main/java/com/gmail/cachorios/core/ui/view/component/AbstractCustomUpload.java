package com.gmail.cachorios.core.ui.view.component;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.upload.SucceededEvent;

@Tag("custom-select")
@HtmlImport("frontend://styles/custom-select.html")
public abstract class AbstractCustomUpload<T> extends AbstractCompositeField<Div, AbstractCustomUpload<T>, T> {
//    private IVisualizable padre;
    private String titulo, filename;
    private Button ver;
    private boolean conSelect;
    private CustomUpload select;
    private String DIR_TO_UPLOAD;

    public AbstractCustomUpload(String titulo/*, IVisualizable padre*/, T nullValue, boolean conSelect) {
        super(nullValue);

        this.conSelect = conSelect;
//        this.padre = padre;
        this.titulo = titulo;
        this.DIR_TO_UPLOAD = "";
    }

    private String adaptarNombre(String fuente) {
        return DIR_TO_UPLOAD.concat((filename = fuente));
    }

    private void alSeleccionar(SucceededEvent evento) {
        String a = "Holamundo!";
        setPresentationValue(generarObjeto(filename));
    }

    private void alRemover() {
        limpiarElemento();
    }

    private Label generateCaption() {
        Label lblCaption = new Label();

        lblCaption.setText(titulo);
        lblCaption.setWidth("100%");
        lblCaption.setClassName("lbl-caption");

        return lblCaption;
    }

    protected CustomUpload generarSelect() {
        Button select = new Button();

        select.getElement().setProperty("title", "Elegir");
        select.setIcon(VaadinIcon.PLUS_CIRCLE_O.create());

        CustomUpload upload = new CustomUpload(this::alSeleccionar, this::adaptarNombre, select);
        upload.addFileRemoveListener(e -> alRemover());

        return upload;
    }

    /*protected Button generarVer() {
        Button ver = new Button();

        ver.getElement().setProperty("title", "Ver");
        ver.setIcon(VaadinIcon.EYE.create());
        ver.addClickListener(e -> {
            try {
                if (!isEmpty()) {
                    verElemento();
                } else {
                    Notification.show("Esta vacio");
                }
            } catch (Exception ex) {
                Notification.show("ERROR X2");
            }
        });

        return ver;
    }*/

    protected String getDescripcion(T valor) { return valor.toString(); }

//    protected void verElemento() {
//        new WinUploadView(getDescripcion(getValue())).open();
//    }

    protected void limpiarElemento() {
        clear();
        setFocus();
    }

    /*public IVisualizable getPadre() {
        return padre;
    }*/

    public void setFocus() {
        if (ver != null) {
            ver.focus();
        }
    }

    abstract public T generarObjeto(String value);

    @Override
    protected Div initContent() {
        Div content = new Div();
        content.setWidth("80%");

        content.add(generateCaption());

        if (conSelect) {
            content.add((select = generarSelect()));
        }

        content.getStyle().set("padding", "var(--lumo-space-xs) 0");
        content.getElement().getClassList().add("panel-block");

        return content;
    }

    @Override
    protected void setPresentationValue(T value) {
        setFocus();

        if (value != null && !value.equals(getEmptyValue())) {
            select.getElement().setProperty("title", "Cambiar");
            if (ver != null) { ver.setEnabled(true); ver.focus(); }
        } else {
            select.getElement().setProperty("title", "Elegir");
            if (ver != null) { ver.setEnabled(false); }
        }

//        avisarAlPadre();
        setModelValue(value, false);
    }

    @Override
    public void setRequiredIndicatorVisible(boolean required) {
        //CustomUpload.setRequiredIndicatorVisible(required);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;//descripcion.isRequiredIndicatorVisible();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        select.setVisible(!readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return select.isVisible();
    }
}