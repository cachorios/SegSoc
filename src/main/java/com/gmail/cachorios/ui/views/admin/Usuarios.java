package com.gmail.cachorios.ui.views.admin;

import com.gmail.cachorios.backend.data.Role;
import com.gmail.cachorios.backend.data.entity.Usuario;

import com.gmail.cachorios.core.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.abm.*;


import com.gmail.cachorios.ui.MainView;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value = LarConst.PAGE_USERS, layout = MainView.class)
//@Caption("Usuarios")
//@Icon(VaadinIcon.USERS)
@Secured(Role.ADMIN)
public class Usuarios extends Abm<Usuario, Abm.Model> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Usuarios(FilterableAbmService<Usuario> service, PasswordEncoder passwordEncoder  ) {

        super("Usuario", service);
        this.passwordEncoder = passwordEncoder;

        setWith("600px");
        configureGrid();
        this.iniciar(LarConst.TITULO_USUARIOS);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_USERS;
    }

    private void configureGrid() {
        this.getGrid().addColumn(TemplateRenderer.<Usuario> of("<img src='[[item.foto]]' alt='foto'  height=\"30\" style='border-radius: 100%;' >").withProperty("foto", Usuario::getFotoUrl))
                .setHeader("Foto")
                .setFlexGrow(0).setSortable(false);
        Grid.Column col1 = this.getGrid().addColumn(Usuario::getApellido).setHeader("Apellido").setFlexGrow(1).setSortable(true).setKey("apellido");
        Grid.Column col2 = this.getGrid().addColumn(Usuario::getNombre).setHeader("Nombre").setFlexGrow(1).setSortable(true).setKey("nombre");

        this.getGrid().addColumn(Usuario::getEmail).setHeader("Correo").setFlexGrow(1).setSortable(true).setKey("email");

        this.getGrid().addColumn(Usuario::getRole).setHeader("Role").setFlexGrow(0).setSortable(true).setWidth("125px").setResizable(false).setKey("role");
    }



    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<Usuario> binder) {
        TextField email, first, last;
        PasswordField password;
        ComboBox<String> role;

        ListDataProvider<String> roleProvider = DataProvider.ofItems(Role.getAllRoles());
        role = new ComboBox<>("Role");
        role.setItemLabelGenerator(s -> s != null ? s : "");
        role.setDataProvider(roleProvider);
        role.getElement().setAttribute("colspan", "3");
        binder.bind(role, "role");


        email = new TextField("Email (login)");
        email.getElement().setAttribute("colspan", "3");
        binder.bind(email,"email");

        first = new TextField("Nombre");
        first.getElement().setAttribute("colspan", "2");
        binder.bind(first, "nombre");
        last = new TextField("Apellido");
        binder.bind(last, "apellido");
        password = new PasswordField("Clave de  acceso");
        password.getElement().setAttribute("colspan", "3");

        binder.forField(password).withValidator(pass -> {
            return pass.matches("^(|(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})$");
        }, "need 6 or more chars, mixing digits, lowercase and uppercase letters")
                .bind(user -> password.getEmptyValue(), (user, pass) -> {
                    if (!password.getEmptyValue().equals(pass)) {
                        user.setPasswordHash(passwordEncoder.encode(pass));
                    }
                });

        form.add(email, first,last,password, role);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("21em",2),
                new FormLayout.ResponsiveStep("22em",3)
        );
    }
}

