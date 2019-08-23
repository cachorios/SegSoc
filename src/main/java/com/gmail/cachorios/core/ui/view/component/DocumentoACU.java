package com.gmail.cachorios.core.ui.view.component;


import com.gmail.cachorios.core.ui.view.abm.Abm;

public class DocumentoACU extends AbstractCustomUpload<String> {
    public DocumentoACU(String titulo, Abm padre, boolean conSelect) {
        super(titulo, padre, null, conSelect);
    }

    @Override
    public String generarObjeto(String value) {
        return value;
    }
}