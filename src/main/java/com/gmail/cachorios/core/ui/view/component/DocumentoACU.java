package com.gmail.cachorios.core.ui.view.component;


public class DocumentoACU extends AbstractCustomUpload<String> {
    public DocumentoACU(String titulo, boolean conSelect) {
        super(titulo, null, conSelect);
    }

    @Override
    public String generarObjeto(String value) {
        return value;
    }
}