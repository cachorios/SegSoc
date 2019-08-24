package com.gmail.cachorios.core.ui.abm;

import com.gmail.cachorios.core.data.EntidadInterface;
import com.gmail.cachorios.core.ui.component.FormButtonsBar;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

import com.vaadin.flow.data.binder.BeanValidationBinder;

import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("lar-abm-form")
@JsModule("./src/components/lar-abm-form.js")
public class AbmForm<T extends EntidadInterface> extends PolymerTemplate<TemplateModel> implements Abm.IAbmForm<T> {

    @Id
    private H3 titulo;
    @Id
    private FormLayout form;
    @Id
    private FormButtonsBar botonera;

    public AbmForm() {
        titulo.setText("Edicion de ");
    }




    @Override
    public FormLayout getFormLayuot() {
        return form;
    }

    @Override
    public FormButtonsBar getButtons() {
        return botonera;
    }

    @Override
    public HasText getTitle() {
        return titulo;
    }


    @Override
    public void setBinder(BeanValidationBinder<T> binder) {

    }
}