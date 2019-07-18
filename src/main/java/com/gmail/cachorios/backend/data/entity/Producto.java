package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Producto extends AbstractEntityId {

    @Id
    private Long id;

    @NotNull
    @Size(max = 255, min = 4, message = "La descripcion debe tener entre 4 y 255 caracteres.")
    private String descripcion;

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString(){
        return (isNew() ? "Nuevo producto" : descripcion );
    }
}
