package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class MovimientoDetalle extends AbstractEntityId {

    @Id
    private Long id;

    private String productoDescripcion;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    @NotNull
    private Movimiento movimiento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoDetalle", fetch = FetchType.LAZY)
    @NotNull
    private List<Documento> documentos;

    @Override
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return (isNew() ? "Nuevo detalle" : producto.getDescripcion() + " (" + productoDescripcion + ") ");
    }
}
