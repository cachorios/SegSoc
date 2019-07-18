package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByEmailIgnoreCase(String email);

    Page<Usuario> findBy(Pageable pageable);

    Page<Usuario> findByEmailLikeIgnoreCaseOrNombreLikeIgnoreCaseOrApellidoLikeIgnoreCaseOrRoleLikeIgnoreCase(
        String email, String firsName, String lastName, String role, Pageable pageable);

    long countByEmailLikeIgnoreCaseOrNombreLikeIgnoreCaseOrApellidoLikeIgnoreCaseOrRoleLikeIgnoreCase(
            String email, String firsName, String lastName, String role);

}
