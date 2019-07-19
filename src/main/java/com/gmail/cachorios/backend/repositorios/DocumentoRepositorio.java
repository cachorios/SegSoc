package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Documento;
import com.gmail.cachorios.backend.data.entity.MovimientoDetalle;
import com.gmail.cachorios.backend.data.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {

    Page<Documento> findBy(Pageable pageable);

    Page<Documento> findByMovimientoDetalle(MovimientoDetalle persona, Pageable pageable);

    long countByMovimientoDetalle(MovimientoDetalle movimiento);

    Page<Documento> findByPersona(Persona persona, Pageable pageable);

    long countByPersona(Persona persona);
}
