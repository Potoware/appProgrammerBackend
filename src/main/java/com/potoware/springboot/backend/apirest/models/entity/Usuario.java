package com.potoware.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 12)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacio")
	private String apellido;
	
	@NotEmpty(message = "no puede estar vacio")
	@Email(message = "no tiene un formato valido")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	@Size(min = 8,message = "debe tener entre 8 y 32 caracteres")
	private String contrasenia;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@ManyToMany (fetch = FetchType.LAZY,
		      cascade = {
		              CascadeType.PERSIST,
		              CascadeType.MERGE
		          })
    @JoinTable( 
        name = "usuarios_roles", 
        joinColumns = @JoinColumn(
          name = "usuario_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "rol_id", referencedColumnName = "id")) 
	@Column(nullable = false)
	private Set<Rol> roles;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	 
	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public void addRol(Rol rol) {
		this.roles.add(rol);
		rol.getUsuarios().add(this);
	}
	  
	public void removeRol(long rolId) {
		Rol rol = this.roles.stream().filter(r -> r.getId() == rolId).findFirst().orElse(null);
		if (rol != null) {
			this.roles.remove(rol);
			rol.getUsuarios().remove(this);
		}
	}	
	
	public Set<Rol> getRoles() {
		return roles;
	}
	  
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	private static final long serialVersionUID = 1L;
	
	

}
