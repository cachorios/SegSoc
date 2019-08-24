package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.data.AbstractEntityId;
import com.gmail.cachorios.core.data.enums.ETipoParametro;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Parametro extends AbstractEntityId {

    @Enumerated
    @Column(name = "tipo", columnDefinition = "smallint default 0")
    private ETipoParametro tipo;

    @Column(name = "clase")
    @Size(min = 4, max = 32, message = "El campo debe tener entre 4 y 32 caracteres")
    private String clase;

    @Column(name = "orden")
    @NotNull(message = "Campo vacio: Orden")
    private Integer orden;

    @Column(name = "nombre")
    @NotNull(message = "Campo vacio: Nombre")
    @Size(min = 4, max = 32, message = "El campo debe tener entre 4 y 32 caracteres")
    private String nombre;

    @Column(name = "valorint")
    private Long valorint;

    @Column(name = "valordob")
    private Double valordob;

    @Column(name = "valorstr")
    @Size(max = 255, message = "El campo debe tener maximo 255 caracteres")
    private String valorstr;

    @Column(name = "valorbol", columnDefinition = "boolean default false")
    @NotNull(message = "Campo vacio: Valor boleano")
    private Boolean valorbol;

    @Column(name = "valordat")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date valordat;

    @Column(name = "valorchr", columnDefinition = "char", length = 1)
    private String valorchr;

    public Parametro() {
        super();
        orden = 0;
    }

   
    public Parametro(ETipoParametro tipo, String clase, Integer orden, String nombre, Long valorint, Double valordob,
                     String valorstr, Boolean valorbol, Date valordat, String valorchr) {
        this();
        this.tipo = tipo;
        this.clase = clase;
        this.orden = orden;
        this.nombre = nombre;
        this.valorint = valorint;
        this.valordob = valordob;
        this.valorstr = valorstr;
        this.valorbol = valorbol;
        this.valordat = valordat;
        this.valorchr = valorchr;
    }

    public ETipoParametro getTipo() {
        return tipo;
    }

    public void setTipo(ETipoParametro tipo) {
        this.tipo = tipo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getValorint() {
        return valorint;
    }

    public void setValorint(Long valorint) {
        this.valorint = valorint;
    }

    public Double getValordob() {
        return valordob;
    }

    public void setValordob(Double valordob) {
        this.valordob = valordob;
    }

    public String getValorstr() {
        return valorstr;
    }

    public void setValorstr(String valorstr) {
        this.valorstr = valorstr;
    }

    public Boolean getValorbol() {
        return valorbol;
    }

    public void setValorbol(Boolean valorbol) {
        this.valorbol = valorbol;
    }

    public Date getValordat() {
        return valordat;
    }

    public void setValordat(Date valordat) {
        this.valordat = valordat;
    }

    public String getValorchr() {
        return valorchr;
    }

    public void setValorchr(String valorchr) {
        this.valorchr = valorchr;
    }

    @Override
    public String toString() {
        return (isNew() ? "Nuevo parametro" : nombre);
    }
}
