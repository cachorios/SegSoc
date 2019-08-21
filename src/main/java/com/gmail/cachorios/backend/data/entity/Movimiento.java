package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Movimiento extends AbstractEntityId {

    @NotNull
    private Date fecha;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Persona persona;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimiento",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MovimientoDetalle> detalles;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<MovimientoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<MovimientoDetalle> detalles) {
        this.detalles = detalles;
    }

    public Movimiento() {
        this.detalles = new ArrayList<>();
    }

    @Override
    public String toString() {
        return (isNew() ? "Nuevo movimiento" : persona.getNombre() + " " + fecha.toString());
    }
}
