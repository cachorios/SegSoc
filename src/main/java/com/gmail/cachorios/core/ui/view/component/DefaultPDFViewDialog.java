package com.gmail.cachorios.core.ui.view.component;

import com.vaadin.flow.component.dialog.Dialog;

public class DefaultPDFViewDialog {
    private final Dialog dialog = new Dialog();

    public DefaultPDFViewDialog() {
        config();
    }

    public void config(){
        dialog.setHeight("600px");
        dialog.setWidth("800px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);

        dialog.addDialogCloseActionListener(e -> {
            dialog.close();
            dialog.removeAll();
        });
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void open() {
        dialog.open();
    }
}
