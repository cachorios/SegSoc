package com.gmail.cachorios.backend.data.entity;

import com.gmail.cachorios.core.data.AbstractEntityId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario extends AbstractEntityId {

	@NotEmpty
	@Email
	@Size(max = 255)
	@Column(unique = true)
	private String email;

	@NotNull
	@Size(min = 4, max = 255)
	private String passwordHash;

	@NotBlank
	@Size(max = 255)
	private String nombre;

	@NotBlank
	@Size(max = 255)
	private String apellido;

	@NotBlank
	@Size(max = 255)
	private String role;

	@Size(max = 2083)
	private String fotoUrl;

	private boolean locked = false;

	@PrePersist
	@PreUpdate
	private void prepareData(){
		this.email = email == null ? null : email.toLowerCase();
	}

	public Usuario() {
		// An empty constructor is needed for all beans
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

    public String getFullName(){
		return getNombre() + " " + getApellido();
	}

}
