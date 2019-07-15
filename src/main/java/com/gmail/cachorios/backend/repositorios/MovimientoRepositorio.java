package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Movimiento;
import com.gmail.cachorios.backend.data.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long> {
    Page<Movimiento> findBy(Pageable pageable);

    Page<Movimiento> findByPersona(Persona persona, Pageable pageable);

    long countByPersona(Persona persona);
}
