package com.potoware.springboot.backend.apirest.models.services;

import java.util.List;

import com.potoware.springboot.backend.apirest.models.entity.Permiso;

public interface IPermisoService {

public List<Permiso> findAll();
	
	public Permiso save(Permiso permiso);
	
	public void delete(Long id);
	
	public Permiso findById(Long id);
}
