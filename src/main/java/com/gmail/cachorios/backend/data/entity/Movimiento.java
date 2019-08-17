package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Movimiento extends AbstractEntityId {

    @Id
    private Long id;

    @NotNull
    private Date fecha;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Persona persona;

    @OneToMany(mappedBy = "movimiento")
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

    @Override
    public String toString() {
        return (isNew() ? "Nuevo movimiento" : persona.getNombre() + " " + fecha.toString());
    }
}
