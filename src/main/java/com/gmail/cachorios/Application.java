package com.gmail.cachorios;

import com.gmail.cachorios.app.seguridad.SecurityConfiguration;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.UsuarioRepositorio;
import com.gmail.cachorios.ui.MainView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackageClasses = {SecurityConfiguration.class, MainView.class, Application.class}) //, UsuarioService.class})
@EnableJpaRepositories(basePackageClasses = {UsuarioRepositorio.class })
@EntityScan(basePackageClasses =   {Usuario.class})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
