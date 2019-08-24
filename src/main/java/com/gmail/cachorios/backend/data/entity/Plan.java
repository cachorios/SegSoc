package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.data.AbstractEntityId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Plan extends AbstractEntityId {

    @ManyToOne
    private Parametro nombre;

    @NotNull
    private Double monto;

    @ManyToOne
    private Persona persona;

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
