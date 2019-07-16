package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntity;
import com.gmail.cachorios.core.ui.data.enums.ETipoDocumento;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class Documento extends AbstractEntity {

    @Id
    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private ETipoDocumento tipo;

    @ManyToOne
    private Persona persona;

    @ManyToOne
    private MovimientoDetalle movimientoDetalle;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public MovimientoDetalle getMovimientoDetalle() {
        return movimientoDetalle;
    }

    public ETipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(ETipoDocumento tipo) {
        this.tipo = tipo;
    }

    public void setMovimientoDetalle(MovimientoDetalle movimientoDetalle) {
        this.movimientoDetalle = movimientoDetalle;
    }

    @Override
    public String toString() {
        return (isNew() ? "Nuevo documento" : descripcion);
    }
}
