package com.gmail.cachorios.ui;

import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;

import com.github.appreciated.app.layout.component.applayout.Behaviour;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;

import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.app.seguridad.SecurityUtils;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.servicios.UsuarioService;
import com.gmail.cachorios.ui.views.admin.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;

import static com.github.appreciated.app.layout.entity.Section.HEADER;
import static com.github.appreciated.app.layout.entity.Section.FOOTER;


@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@HtmlImport("frontend://styles/shared-styles.html")

public class MainAppLayout extends AppLayoutRouterLayout {
    private DefaultNotificationHolder notifications = new DefaultNotificationHolder();

    public MainAppLayout() {
        super();
        notifications.addClickListener(notification -> {/* ... */});
    
        UsuarioService usuarioService = ApplicationContextProvider.getApplicationContext().getBean(UsuarioService.class);
        Usuario usuario = usuarioService.getRepository().findByEmailIgnoreCase(SecurityUtils.getUsername());
    
        LeftHeaderItem header = new LeftHeaderItem(usuario.getFullName(), null, usuario.getFotoUrl());
        header.getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        
        init(AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("Seguimientos Acc. Social")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new LeftClickableItem("Salir",
                                VaadinIcon.SIGN_OUT.create(),
                                clickEvent -> UI.getCurrent().getPage().executeJs("location.assign('logout')") ))
                                
                        .build())
                .withAppMenu(
                    LeftAppMenuBuilder.get()
                        .addToSection(header, HEADER)
//                        .addToSection(new LeftClickableItem("Clickable Entry",
//                                VaadinIcon.COG.create(),
//                                clickEvent -> Notification.show("onClick ...")
//                        ), HEADER)
//                        .add(new LeftNavigationItem(Personas.class))
//                        .add(LeftSubMenuBuilder
//                                .get("My Submenu", VaadinIcon.PLUS.create())
//                                .add(LeftSubMenuBuilder
//                                        .get("My Submenu", VaadinIcon.PLUS.create())
//                                        .add(new LeftNavigationItem(Personas.class))
//                                        .add(new LeftNavigationItem(Personas.class))
//                                        .add(new LeftNavigationItem(Personas.class))
//                                        .build())
//                                .add(new LeftNavigationItem(Personas.class))
//                                .add(new LeftNavigationItem(Personas.class))
//                                .build())
                        .add(new LeftNavigationItem(Personas.class))
                        .add(new LeftNavigationItem(Movimientos.class))
                        .add(new LeftNavigationItem(Productos.class))
                        .add(new LeftNavigationItem(Planes.class))
        
                        .withStickyFooter()
//                        .addToSection(
//                            new LeftClickableItem("Footer Clickable!",
//                                    VaadinIcon.COG.create(),
//                                    clickEvent -> Notification.show("Clicked!")),
//                                FOOTER)
                        .addToSection(new LeftNavigationItem(Parametros.class),FOOTER)
                        .addToSection(new LeftNavigationItem(Usuarios.class),FOOTER)
                        
                        .build()
                ).build());
    }

   
   /*
//        String diricono = "/frontend/images/" + "cacho"+ ".png";
        MenuHeaderComponent header = new MenuHeaderComponent(usuario.getFullName(), null, usuario.getFotoUrl());
        header.getContent().setAlignItems(FlexComponent.Alignment.CENTER);

        AppLayout appLayout = AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("DEJOS")
                .withAppBar(
                        AppBarBuilder.get()
                                .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                // todo: El boton de logout cambia los estilos de los botones
                //                .add(new TopClickableComponent("",
                //                        VaadinIcon.SIGN_OUT.create(),
                //                        clickEventLogout -> SegSocServices.navegarA("/logout")))
                                .build())
                .withAppMenu(LeftAppMenuBuilder.get()
                        .addToSection(header, HEADER)
                        .addToSection(new LeftClickableComponent("Clickable Entry",
                                VaadinIcon.COG.create(),
                                clickEvent -> Notification.show("onClick ...")
                        ), HEADER)
                        .add(new LeftNavigationComponent(Usuarios.class))
                        .add(new LeftNavigationComponent(Personas.class))
                        .add(new LeftNavigationComponent(Movimientos.class))
                        .add(new LeftNavigationComponent(Productos.class))
                        .add(new LeftNavigationComponent(Planes.class))
                        .add(new LeftNavigationComponent(Parametros.class))

                        *//*.add(LeftSubMenuBuilder
                                .get("Liquidación", VaadinIcon.PLUS.create())
                                .add(new LeftNavigationComponent(Liquidaciones.class))
                                .add(new LeftNavigationComponent(LiquidacionDetalles.class))
                                .add(new LeftNavigationComponent(Conceptos.class))
                                .add(new LeftNavigationComponent(LugaresPagos.class))
                                .add(new LeftNavigationComponent(Porcentajes.class))
                                .build())

                        .addToSection(new LeftClickableComponent("Clickable Entry",
                                VaadinIcon.COG.create(),
                                clickEvent -> Notification.show("onClick ...")
                        ), FOOTER)*//*
                        .build())
                .build();

        return appLayout;
    }*/
}
