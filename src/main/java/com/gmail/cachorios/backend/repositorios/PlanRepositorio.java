package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepositorio extends JpaRepository<Plan, Long> {
    Page<Plan> findBy(Pageable pageable);

    Page<Plan> findByPersona(Persona persona, Pageable pageable);

    long countByPersona(Persona persona);

    @Query(value = "SELECT e FROM parametro e WHERE e.tipo = 1", nativeQuery = true)
    List getAll();
}
