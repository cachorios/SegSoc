package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Parametro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametroRepositorio extends JpaRepository<Parametro, Long> {
    Page<Parametro> findBy(Pageable pageable);

    Page<Parametro> findByNombreLikeIgnoreCase(
            String nombre, Pageable pageable);

    long countByNombreLikeIgnoreCase(
            String nombre);
}
