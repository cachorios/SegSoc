package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Movimiento;
import com.gmail.cachorios.backend.data.entity.MovimientoDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoDetalleRepositorio extends JpaRepository<MovimientoDetalle, Long> {
    Page<MovimientoDetalle> findBy(Pageable pageable);

    Page<MovimientoDetalle> findByMovimiento(Movimiento movimiento, Pageable pageable);

    long countByMovimiento(Movimiento movimiento);
}
