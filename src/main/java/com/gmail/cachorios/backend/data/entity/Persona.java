package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.ui.data.AbstractEntityId;
import com.gmail.cachorios.core.ui.data.enums.EFactorRH;
import com.gmail.cachorios.core.ui.data.enums.EGrupoSanguineo;
import com.gmail.cachorios.core.ui.data.enums.EParentesco;
import com.gmail.cachorios.core.ui.data.enums.ESexo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Persona extends AbstractEntityId {

    @NotBlank
    private String nombre;
    @NotEmpty
    private String documento;
    @NotNull
    private ESexo sexo;
    @NotNull
    private EGrupoSanguineo grupoSanguineo;
    @NotNull
    private EFactorRH factor;
    @NotEmpty
    private String direccion;
    
    @NotEmpty
    private String descripcionDireccion;
    
    @NotNull
    private Integer numeroPartida;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    private Persona cabeza;
    private EParentesco parentesco;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Documento> fotosDocumento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Plan> planes;

    public Persona() {
        this.planes = new ArrayList<>();
    }

    public Persona getCabeza() {
        return cabeza;
    }

    public void setCabeza(Persona cabeza) {
        this.cabeza = cabeza;
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

    public ESexo getSexo() {
        return sexo;
    }

    public void setSexo(ESexo sexo) {
        this.sexo = sexo;
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

    public List<Plan> getPlanes() {
        return planes;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }
    
    public void addPlan(Plan plan){
        if(planes == null){
            planes = new ArrayList<>();
        }
        planes.add(plan);
        
    }
    
    public EParentesco getParentesco() {
        return parentesco;
    }
    
    public void setParentesco(EParentesco parentesco) {
        this.parentesco = parentesco;
    }
    
    public EGrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }
    
    public void setGrupoSanguineo(EGrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
    
    public EFactorRH getFactor() {
        return factor;
    }
    
    public void setFactor(EFactorRH factor) {
        this.factor = factor;
    }
    
    
    
    @Override
    public String toString() {
        return (isNew() ? "Nueva persona" : nombre);
    }
}
