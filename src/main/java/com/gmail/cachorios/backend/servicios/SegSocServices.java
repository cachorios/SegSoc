package com.gmail.cachorios.backend.servicios;


import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.app.Context;
import com.gmail.cachorios.app.seguridad.SecurityUtils;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.vaadin.flow.component.UI;

public  class SegSocServices {
    private static Usuario usuario;
    public static Usuario getUsuarioActivo() {
        if(SecurityUtils.isUserLoggedIn()) {
            if (usuario == null) {
                UsuarioService usuarioService = Context.getBean(UsuarioService.class);
                usuario = usuarioService.getRepository().findByEmailIgnoreCase(SecurityUtils.getUsername());
            }
            return usuario;
        }
        return null;
    }

    public static void refreshUsuarioActivo() {
        usuario = null;
        getUsuarioActivo();
    }

    public static void navegarA(String pagina){
        UI.getCurrent().getPage().executeJavaScript("window.location.href='".concat(pagina).concat("'"));
    }
}
