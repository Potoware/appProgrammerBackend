package com.potoware.springboot.backend.apirest.models.services;

import java.util.List;

import com.potoware.springboot.backend.apirest.models.entity.Rol;

public interface IRolService {

public List<Rol> findAll();
	
	public Rol save(Rol rol);
	
	public void delete(Long id);
	
	public Rol findById(Long id);
}
