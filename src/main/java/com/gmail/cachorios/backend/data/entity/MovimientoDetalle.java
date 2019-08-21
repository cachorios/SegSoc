package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MovimientoDetalle extends AbstractEntityId {

    private String productoDescripcion;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    @NotNull
    private Movimiento movimiento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoDetalle", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Documento> documentos;

    public String getProductoDescripcion() {
        return productoDescripcion;
    }

    public void setProductoDescripcion(String productoDescripcion) {
        this.productoDescripcion = productoDescripcion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public MovimientoDetalle() {
        this.documentos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return (isNew() ? "Nuevo detalle" : ((producto != null) ? producto.getDescripcion() : productoDescripcion));
    }
}
