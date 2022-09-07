package com.potoware.springboot.backend.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.potoware.springboot.backend.apirest.models.dao.IPermisoDao;
import com.potoware.springboot.backend.apirest.models.entity.Permiso;
import com.potoware.springboot.backend.apirest.models.services.IPermisoService;

@Service
public class PermisoServiceImpl implements IPermisoService{

	@Autowired
	private IPermisoDao permisoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Permiso> findAll() {
		return (List<Permiso>) permisoDao.findAll();
	}

	@Override
	@Transactional
	public Permiso save(Permiso rol) {
		
		return permisoDao.save(rol);
	}

	@Override
	@Transactional
	public void delete(Long id) {
	
		permisoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Permiso findById(Long id) {
		
		return permisoDao.findById(id).orElse(null);
	}

}
