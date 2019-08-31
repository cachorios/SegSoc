package com.gmail.cachorios.ui.views.login;

import com.gmail.cachorios.app.seguridad.SecurityUtils;
import com.gmail.cachorios.ui.utils.LarConst;
import com.gmail.cachorios.ui.views.admin.Personas;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.page.Viewport;

import com.vaadin.flow.router.*;

@Route
@PageTitle(LarConst.APP_TITLE)
@JsModule("./styles/shared-styles.js")
@Viewport(LarConst.VIEWPORT)

public class LoginView extends LoginOverlay implements AfterNavigationObserver, BeforeEnterListener{
    
    
    public LoginView() {
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle(LarConst.APP_TITLE);
        i18n.getHeader().setDescription(
                "admin@gmail.com + admin\n" + "dario@gmail.com.com + dario");
        i18n.setAdditionalInformation(null);
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Ingresar");
        i18n.getForm().setTitle("Login");
        i18n.getForm().setUsername("Correo");
        i18n.getForm().setPassword("Clave");
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        setAction("login");
        setOpened(true);
    }
    
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(SecurityUtils.isUserLoggedIn()){
            UI.getCurrent().getPage().getHistory().replaceState(null, "");
            beforeEnterEvent.rerouteTo(Personas.class);
        }
        
    }
    
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
