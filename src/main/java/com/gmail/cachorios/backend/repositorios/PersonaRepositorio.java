package com.gmail.cachorios.backend.repositorios;

import com.gmail.cachorios.backend.data.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonaRepositorio extends JpaRepository<Persona, Long> {

    Page<Persona> findBy(Pageable pageable);

    Page<Persona> findByNombreLikeIgnoreCase(
            String nombre, Pageable pageable);

    long countByNombreLikeIgnoreCase(
            String nombre);

//    @Query(value = "SELECT e FROM persona e WHERE e.id <> ?1", nativeQuery = true)
//    Page<Persona> findDistinctOf(Long persona, Pageable pageable);
//
//    @Query(value = "SELECT COUNT(e) FROM persona e WHERE e.id <> ?1", nativeQuery = true)
//    long countDistinctOf(Long persona);

    @Query("SELECT e FROM Persona e WHERE e.id <> ?1")
    Page<Persona> findDistinctOf(Long persona, Pageable pageable);

    @Query("SELECT COUNT(e) FROM Persona e WHERE e.id <> ?1")
    long countDistinctOf(Long persona);
}
