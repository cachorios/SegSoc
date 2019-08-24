package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.data.AbstractEntityId;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Producto extends AbstractEntityId {

  

    @NotEmpty
    @Size(max = 255, min = 4, message = "La descripcion debe tener entre 4 y 255 caracteres.")
    private String descripcion;

    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    @Override
    public String toString(){
        return (isNew() ? "Nuevo producto" : descripcion );
    }
}
