package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntity;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class Plan extends AbstractEntity {

    @Id
    private Long id;

    @NotNull
    private Parametro nombre;

    @NotNull
    private Double monto;

    @ManyToOne
    private Persona persona;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parametro getNombre() {
        return nombre;
    }

    public void setNombre(Parametro nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return (isNew() ? "Nuevo plan" : nombre.getNombre());
    }
}
