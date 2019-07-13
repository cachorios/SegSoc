package com.gmail.cachorios.ui;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.MenuHeaderComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftClickableComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;

import com.gmail.cachorios.app.ApplicationContextProvider;
import com.gmail.cachorios.app.seguridad.SecurityUtils;
import com.gmail.cachorios.backend.data.entity.User;
import com.gmail.cachorios.backend.servicios.UserService;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;
import static com.github.appreciated.app.layout.entity.Section.HEADER;



@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@HtmlImport("frontend://styles/shared-styles.html")

public class MainAppLayout extends AppLayoutRouterLayout {
    private DefaultNotificationHolder notifications;

    public MainAppLayout() {
        super();
    }

    @Override
    public AppLayout createAppLayoutInstance() {

        notifications = new DefaultNotificationHolder(newStatus -> { });

        UserService userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
        User user = userService.getRepository().findByEmailIgnoreCase(SecurityUtils.getUsername());


//        String diricono = "/frontend/images/" + "cacho"+ ".png";

        MenuHeaderComponent header = new MenuHeaderComponent(user.getFullName(), null, user.getPhotoUrl());
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

                        .add(LeftSubMenuBuilder
                                .get("Liquidación", VaadinIcon.PLUS.create())
                                /*.add(new LeftNavigationComponent(Liquidaciones.class))
                                .add(new LeftNavigationComponent(LiquidacionDetalles.class))
                                .add(new LeftNavigationComponent(Conceptos.class))
                                .add(new LeftNavigationComponent(LugaresPagos.class))
                                .add(new LeftNavigationComponent(Porcentajes.class))*/
                                .build())
                        //.add(new LeftNavigationComponent(Regimenes.class))
                        .addToSection(new LeftClickableComponent("Clickable Entry",
                                VaadinIcon.COG.create(),
                                clickEvent -> Notification.show("onClick ...")
                        ), FOOTER)
                        .build())
                .build();

        return appLayout;
    }
}
