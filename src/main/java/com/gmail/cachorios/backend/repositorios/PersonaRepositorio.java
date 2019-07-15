package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepositorio extends JpaRepository<Persona, Long> {

    Page<Persona> findBy(Pageable pageable);

    Page<Persona> findByNombreLikeIgnoreCase(
            String nombre, Pageable pageable);

    long countByNombreLikeIgnoreCase(
            String nombre);
}
