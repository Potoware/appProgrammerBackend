package com.potoware.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "roles")
public class Rol implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String nombre;
    
    @ManyToMany(fetch = FetchType.LAZY,
    	      cascade = {
    	          CascadeType.PERSIST,
    	          CascadeType.MERGE
    	      },
    	      mappedBy = "roles")
    @JsonIgnore
    private Set<Usuario> usuarios;

    @ManyToMany (fetch = FetchType.LAZY,
		      cascade = {
		              CascadeType.PERSIST,
		              CascadeType.MERGE
		          })
    @JoinTable(
        name = "roles_permisos", 
        joinColumns = @JoinColumn(
          name = "rol_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "permiso_id", referencedColumnName = "id"))
    private Set<Permiso> permisos;

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
	
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void addPermiso(Permiso permiso) {
		this.permisos.add(permiso);
		permiso.getRoles().add(this);
	}
	  
	public void removePermiso(long permisoId) {
		Permiso permiso = this.permisos.stream().filter(p -> p.getId() == permisoId).findFirst().orElse(null);
		if (permiso != null) {
			this.permisos.remove(permiso);
			permiso.getRoles().remove(this);
		}
	}

	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}	
	  
    
}
