package com.gmail.cachorios.backend.servicios;


import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.app.seguridad.SecurityUtils;
import com.gmail.cachorios.backend.data.entity.User;
import com.vaadin.flow.component.UI;

public  class SegSocServices {
    private static User user;
    public static User getUsuarioActivo() {
        if(SecurityUtils.isUserLoggedIn()) {
            if (user == null) {
                UserService userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
                user = userService.getRepository().findByEmailIgnoreCase(SecurityUtils.getUsername());
            }
            return user;
        }
        return null;
    }

    public static void refreshUsuarioActivo() {
        user = null;
        getUsuarioActivo();
    }

    public static void navegarA(String pagina){
        UI.getCurrent().getPage().executeJavaScript("window.location.href='".concat(pagina).concat("'"));
    }
}
