package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Persona extends AbstractEntityId {

    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Persona cabeza;

    private Parametro parentesco;

    @NotNull
    private String nombre;

    @NotNull
    private String documento;

    @NotNull
    @Size(max = 1)
    private String sexo;

    @NotNull
    private Parametro grupoSanguineo;

    @NotNull
    private Parametro factor;

    @NotNull
    private String direccion;

    @NotNull
    private String descripcionDireccion;

    @NotNull
    private Integer numeroPartida;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona", fetch = FetchType.LAZY)
    private List<Documento> fotosDocumento;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "persona_plan"
            ,joinColumns        = @JoinColumn(name = "persona",   referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "plan",      referencedColumnName = "id"))
    private Set<Plan> planes;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona", fetch = FetchType.LAZY)
//    private List<Plan> planes;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getCabeza() {
        return cabeza;
    }

    public void setCabeza(Persona cabeza) {
        this.cabeza = cabeza;
    }

    public Parametro getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parametro parentesco) {
        this.parentesco = parentesco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Parametro getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(Parametro grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public Parametro getFactor() {
        return factor;
    }

    public void setFactor(Parametro factor) {
        this.factor = factor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcionDireccion() {
        return descripcionDireccion;
    }

    public void setDescripcionDireccion(String descripcionDireccion) {
        this.descripcionDireccion = descripcionDireccion;
    }

    public Integer getNumeroPartida() {
        return numeroPartida;
    }

    public void setNumeroPartida(Integer numeroPartida) {
        this.numeroPartida = numeroPartida;
    }

    public List<Documento> getFotosDocumento() {
        return fotosDocumento;
    }

    public void setFotosDocumento(List<Documento> fotosDocumento) {
        this.fotosDocumento = fotosDocumento;
    }

    public Set<Plan> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<Plan> planes) {
        this.planes = planes;
    }

    @Override
    public String toString() {
        return (isNew() ? "Nueva persona" : nombre);
    }
}
