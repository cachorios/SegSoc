package com.gmail.cachorios.ui.forms;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.gmail.cachorios.backend.data.Role;
import com.gmail.cachorios.backend.data.entity.User;

import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.backend.servicios.UserService;
import com.gmail.cachorios.core.ui.view.abm.*;


import com.gmail.cachorios.core.ui.view.component.CustomSelect;
import com.gmail.cachorios.core.ui.view.component.selectCompuesto.SelectCompuesto;
import com.gmail.cachorios.ui.MainAppLayout;
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

import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;


@Route(value = LarConst.PAGE_USERS, layout = MainAppLayout.class)
@Caption("UsuarioForm")
@Icon(VaadinIcon.HOME)

@Secured(Role.ADMIN)
public class UsuarioForm extends Abm<User, TemplateModel> {

    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioForm(FilterableAbmService<User> service, PasswordEncoder passwordEncoder  ) {

        super("Usuario", service);
        this.passwordEncoder = passwordEncoder;
        //this.userService = service;
        //ancho del formulario
        setWith("1600px");

        configureGrid();

        this.iniciar(LarConst.TITULO_USUARIOS);
    }

    @Override
    protected String getBasePage() {
        return LarConst.PAGE_USERS;
    }

    private void configureGrid() {
        this.getGrid().addColumn(TemplateRenderer.<User> of("<img src='[[item.foto]]' alt='foto'  height=\"30\" style='border-radius: 100%;' >").withProperty("foto", User::getPhotoUrl))
                .setHeader("Foto")
                .setFlexGrow(0).setSortable(false);
        Grid.Column col1 = this.getGrid().addColumn(User::getLastName).setHeader("Apellido").setFlexGrow(1).setSortable(true).setKey("lastName");
        Grid.Column col2 = this.getGrid().addColumn(User::getFirstName).setHeader("Nombre").setFlexGrow(1).setSortable(true).setKey("firstName");

//        HeaderRow topRow = this.getGrid().prependHeaderRow();
//        topRow.join(col1, col2).setComponent(new Label("Apellido y Nombre"));

        this.getGrid().addColumn(User::getEmail).setHeader("Correo").setFlexGrow(1).setSortable(true).setKey("email");

        this.getGrid().addColumn(User::getRole).setHeader("Role").setFlexGrow(0).setSortable(true).setWidth("125px").setResizable(false).setKey("role");
//        this.getGrid().addColumn(User::isLocked).setHeader("Bloqueado").setFlexGrow(0).setSortable(true).setWidth("110px").setResizable(false).setKey("locked");
    }



    @Override
    protected void crearForm(FormLayout form, BeanValidationBinder<User> binder) {
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
        binder.bind(first, "firstName");
        last = new TextField("Apellido");
        binder.bind(last, "lastName");
        password = new PasswordField("Clave de  acceso");
        password.getElement().setAttribute("colspan", "3");


//        empresa = new CustomSelect<>("Empleador", EmpleadorService.class);
//        empresa.getElement().setAttribute("colspan", "3");

//        empresa
//                .withSetConsultarCodigo((service, string) -> service.findById(Long.valueOf(string)))
//                .withSetObtenerVarlor(empleador -> empleador.getId().toString())
//                .withSetObtenerLeyenda(empleador -> empleador.getNombre())
//
//        ;
//        empresa.withFind()
//                .init();

        //binder.bind(empresa, "empleador");



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
/*
<vaadin-text-field id="email" label="Email (login)" colspan="2"></vaadin-text-field>
<vaadin-text-field id="first" label="First Name"></vaadin-text-field>
<vaadin-text-field id="last" label="Last Name"></vaadin-text-field>
<vaadin-password-field id="password" label="Password" colspan="2"></vaadin-password-field>
<vaadin-combo-box id="role" label="Role" colspan="3"></vaadin-combo-box>
*/

