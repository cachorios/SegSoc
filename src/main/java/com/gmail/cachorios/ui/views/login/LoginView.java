package com.gmail.cachorios.ui.views.login;

import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("login-view")
@HtmlImport("src/views/login/login-view.html")

@Route(value = "login")
@PageTitle(LarConst.APP_TITLE)
@Viewport(LarConst.VIEWPORT)

public class LoginView extends PolymerTemplate<LoginView.Model> implements PageConfigurator, AfterNavigationObserver {


    @Override
    public void configurePage(InitialPageSettings settings) {
        // Force login page to use Shady DOM to avoid problems with browsers and
        // password managers not supporting shadow DOM
        settings.addInlineWithContents(InitialPageSettings.Position.PREPEND,
                "window.customElements=window.customElements||{};" +
                        "window.customElements.forcePolyfill=true;" +
                        "window.ShadyDOM={force:true};", InitialPageSettings.WrapMode.JAVASCRIPT);
    }

    public interface Model extends TemplateModel {
        void setError(boolean error);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        boolean error = event.getLocation().getQueryParameters().getParameters().containsKey("error");
        getModel().setError(error);
    }
}
