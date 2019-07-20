package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.core.ui.data.enums.ETipoParametro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParametroRepositorio extends JpaRepository<Parametro, Long> {
    Page<Parametro> findBy(Pageable pageable);

    Page<Parametro> findByNombreLikeIgnoreCase(
            String nombre, Pageable pageable);

    long countByNombreLikeIgnoreCase(
            String nombre);

    Page<Parametro> findByTipo(
            ETipoParametro tipo, Pageable pageable);

    long countByTipo(
            ETipoParametro tipo);

    @Query(value = "SELECT MAX(e.orden) FROM parametro e WHERE e.tipo = ?1", nativeQuery = true)
    int getMaxOrdenByTipo(ETipoParametro tipo);
}
