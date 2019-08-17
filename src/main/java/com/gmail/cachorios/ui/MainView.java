package com.gmail.cachorios.ui;

import com.gmail.cachorios.app.seguridad.SecurityUtils;

import com.gmail.cachorios.ui.views.admin.Personas;
import com.gmail.cachorios.ui.views.admin.Planes;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.cachorios.ui.utils.LarConst.*;


//@Push
@Viewport(VIEWPORT)
@PWA(name = "LAR App Seguimiento Acción Social ", shortName = "SegAccSoc",
        startPath = "login",
        backgroundColor = "#227aef", themeColor = "#227aef",
        offlinePath = "offline-page.html",
        offlineResources = {"images/offline-login-banner.jpg"})

public class MainView extends AppLayout {
    private final Tabs menu;
    public MainView() {
    
//        new OfflineBanner();
//
        this.setDrawerOpened(false);
        Span appName = new Span("SegSoc");
        appName.addClassName("hide-on-mobile");
    
        menu = createMenuTabs();
        this.addToNavbar(appName);
        this.addToNavbar(true, menu);
       // this.getElement().appendChild(confirmDialog.getElement());
    
        getElement().addEventListener("search-focus", e -> {
            getElement().getClassList().add("hide-navbar");
        });
    
        getElement().addEventListener("search-blur", e -> {
            getElement().getClassList().remove("hide-navbar");
        });
    
        
        //this.setDrawerOpened(false);
//
////        Span appName = new Span("Inicio");
////        appName.addClassName("hide-on-mobile");
//
//        UsuarioService usuarioService = ApplicationContextProvider.getApplicationContext().getBean(UsuarioService.class);
//        Usuario usuario = usuarioService.getRepository().findByEmailIgnoreCase(SecurityUtils.getUsername());
//
//
//        LeftHeaderItem header = new LeftHeaderItem(usuario.getFullName(), null, usuario.getFotoUrl());
//        header.getContent().setAlignItems(FlexComponent.Alignment.CENTER);
//
//        init(AppLayoutBuilder
//                .get(Behaviour.LEFT_RESPONSIVE)
//                .withTitle("Seguimientos Acc. Social")
//                .withAppBar(AppBarBuilder
//                        .get()
//                        .add(new LeftClickableItem("Salir",
//                                VaadinIcon.SIGN_OUT.create(),
//                                clickEvent -> UI.getCurrent().getPage().executeJs("location.assign('logout')") ))
//
//                        .build())
//                .withAppMenu(
//                    LeftAppMenuBuilder.get()
//                        .addToSection(header, HEADER)
//                        .add(new LeftNavigationItem(Personas.class))
//                        .add(new LeftNavigationItem(Movimientos.class))
//                        .add(new LeftNavigationItem(Productos.class))
//                        .add(new LeftNavigationItem(Planes.class))
//
//                        .withStickyFooter()
//                        .addToSection(new LeftNavigationItem(Parametros.class),FOOTER)
//                        .addToSection(new LeftNavigationItem(Usuarios.class),FOOTER)
//
//                        .build()
//                ).build());
//
    
    }
    
    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }
    
    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>(4);
        tabs.add(createTab(VaadinIcon.EDIT, TITULO_PERSONA, View1.class));
        tabs.add(createTab(VaadinIcon.EDIT, TITULO_MOVIMIENTO, View2.class));
        tabs.add(createTab(VaadinIcon.EDIT, TITULO_PLAN, Planes.class));
        
        if (SecurityUtils.isAccessGranted(View3.class)) {
            tabs.add(createTab(VaadinIcon.USER,TITULO_USUARIOS,View3.class));
        }
        
        if (SecurityUtils.isAccessGranted(View5.class)) {
            tabs.add(createTab(VaadinIcon.CALENDAR, TITULO_PARAMETRO,View5.class));
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
