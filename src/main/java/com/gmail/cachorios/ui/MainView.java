package com.gmail.cachorios.ui;

import com.gmail.cachorios.app.seguridad.SecurityUtils;

import com.gmail.cachorios.core.ui.abm.ConfirmDialog;
import com.gmail.cachorios.core.ui.abm.interfaces.HasConfirmation;
import com.gmail.cachorios.core.ui.component.OfflineBanner;
import com.gmail.cachorios.ui.views.admin.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gmail.cachorios.ui.utils.LarConst.*;


//@Push
@Viewport(VIEWPORT)
@PWA(name = "LAR App Seguimiento Acción Social ", shortName = "SegAccSoc",
        startPath = "login",
        backgroundColor = "#227aef", themeColor = "#227aef",
        offlinePath = "offline-page.html",
        offlineResources = {"images/offline-login-banner.jpg"})

public class MainView extends AppLayout {
    private final com.gmail.cachorios.core.ui.abm.ConfirmDialog confirmDialog = new ConfirmDialog();
    private final Tabs menu;
    public MainView() {
    
        new OfflineBanner();
    
//        confirmDialog.setCancelable(true);
//        confirmDialog.setConfirmButtonTheme("raised tertiary error");
//        confirmDialog.setCancelButtonTheme("raised tertiary");
        
        this.setDrawerOpened(false);
        Span appName = new Span("Seguridad Social");
        appName.addClassName("hide-on-mobile");
    
        menu = createMenuTabs();
        
        this.addToNavbar(appName);
        this.addToNavbar(true, menu);
        this.getElement().appendChild(confirmDialog.getElement());
    
        getElement().addEventListener("search-focus", e -> {
            getElement().getClassList().add("hide-navbar");
        });
    
        getElement().addEventListener("search-blur", e -> {
            getElement().getClassList().remove("hide-navbar");
        });
    }
    
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        confirmDialog.setOpened(false);
        if (getContent() instanceof HasConfirmation) {
           // ((HasConfirmation) getContent()).setConfirmDialog(confirmDialog);
        }
        
        String target = RouteConfiguration.forSessionScope().getUrl(this.getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab)tab));
    }
    
    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }
    
    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>(4);
        tabs.add(createTab(VaadinIcon.EDIT, TITULO_PERSONA, Personas.class));
        tabs.add(createTab(VaadinIcon.EDIT, TITULO_MOVIMIENTO, Movimientos.class));
        tabs.add(createTab(VaadinIcon.EDIT, TITULO_PLAN, Planes.class));
        
        if (SecurityUtils.isAccessGranted(Usuarios.class)) {
            tabs.add(createTab(VaadinIcon.USER,TITULO_USUARIOS, Usuarios.class));
        }
        
        if (SecurityUtils.isAccessGranted(Parametros.class)) {
            tabs.add(createTab(VaadinIcon.CALENDAR, TITULO_PARAMETRO, Parametros.class));
        }
        final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
        final Tab logoutTab = createTab(createLogoutLink(contextPath));
        tabs.add(logoutTab);
        return tabs.toArray(new Tab[tabs.size()]);
    }
    
    private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
    }
    
    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }
    
    private static Anchor createLogoutLink(String contextPath) {
        final Anchor a = populateLink(new Anchor(), VaadinIcon.ARROW_RIGHT, TITLE_LOGOUT);
        a.setHref(contextPath + "/logout");
        return a;
    }
    
    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }
    
//    @Override
//    public void showRouterLayoutContent(HasElement content) {
//        if (content != null) {
//            getElement().appendChild(content.getElement());
//        }
//
//        this.confirmDialog.setOpened(false);
//        if (content instanceof HasConfirmation) {
//            ((HasConfirmation) content).setConfirmDialog(this.confirmDialog);
//        }
//    }
}
