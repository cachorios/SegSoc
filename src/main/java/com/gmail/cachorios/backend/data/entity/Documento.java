package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;
import com.gmail.cachorios.core.ui.data.enums.ETipoDocumento;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Documento extends AbstractEntityId {

    @NotNull
    @Size(min = 3, message = "La descripcion debe contener mas de 2 caracteres.")
    private String descripcion;

    @NotNull
    private String nombreArchivo;

    @NotNull
    private String mimeType;

    @NotNull
    private ETipoDocumento tipo;

    @ManyToOne
    private Persona persona;

    @ManyToOne
    private MovimientoDetalle movimientoDetalle;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
