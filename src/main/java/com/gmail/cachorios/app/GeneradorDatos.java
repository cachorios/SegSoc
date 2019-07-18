package com.gmail.cachorios.app;


import com.gmail.cachorios.backend.data.Role;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.UsuarioRepositorio;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringComponent
public class GeneradorDatos implements HasLogger {

    private UsuarioRepositorio usuarioRepositorio;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public GeneradorDatos(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData(){
        if(usuarioRepositorio.count() != 0L){
            getLogger().info("Usar los datos existentes");
            return;
        }

        getLogger().info("Generar Datos de demostración");

        getLogger().info("... Generando usuarios");
        createAdmin(usuarioRepositorio, passwordEncoder);

        createDeletableUsers(usuarioRepositorio, passwordEncoder);
        getLogger().info("Generated demo data");
    }

    private Usuario createAdmin(UsuarioRepositorio repo, PasswordEncoder encoder) {
        return repo.save(createUser("admin@gmail.com", "Luis", "Rios", encoder.encode("admin"),
                Role.ADMIN, "https://randomuser.me/api/portraits/men/34.jpg", true));
    }

    private void createDeletableUsers(UsuarioRepositorio userRepository, PasswordEncoder passwordEncoder) {
        userRepository
                .save(createUser("dario@gmail.com", "DArio", "Lopez", passwordEncoder.encode("dario"), Role.USUARIO,
                        "https://randomuser.me/api/portraits/men/10.jpg", false));
        userRepository
                .save(createUser("beto@gmail.com", "Beto", "Voeffray", passwordEncoder.encode("beto"), Role.SUPERVISOR,
                "https://randomuser.me/api/portraits/women/40.jpg", true));

//        for (int i = 1; i < 99 ; i++) {
//            userRepository
//                    .save(createUser("user"+ i +"@gmail.com",
//                            "usuario"+i,
//                            "apellido"+i,
//                             passwordEncoder.encode("usuario"+i), Role.SUPERVISOR,
//                            "https://randomuser.me/api/portraits/women/"+i+".jpg", false));
//
//        }
    }

    private Usuario createUser(String email, String firstName, String lastName, String passwordHash, String role,
                               String photoUrl, boolean locked) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(firstName);
        usuario.setApellido(lastName);
        usuario.setPasswordHash(passwordHash);
        usuario.setRole(role);
        usuario.setFotoUrl(photoUrl);
        usuario.setLocked(locked);
        return usuario;
    }

}
