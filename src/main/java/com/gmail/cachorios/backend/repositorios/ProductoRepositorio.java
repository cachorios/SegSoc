package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    Page<Producto> findBy(Pageable pageable);

    Page<Producto> findByDescripcion(String filtro, Pageable pageable);

    long countByDescripcion(String filtro);
}
