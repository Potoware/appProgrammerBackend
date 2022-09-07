package com.potoware.springboot.backend.apirest.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.potoware.springboot.backend.apirest.models.dao.IRolDao;
import com.potoware.springboot.backend.apirest.models.entity.Rol;
import com.potoware.springboot.backend.apirest.models.services.IRolService;

@Service
public class RolServiceImpl implements IRolService{

	@Autowired
	private IRolDao rolDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Rol> findAll() {
		return (List<Rol>) rolDao.findAll();
	}

	@Override
	@Transactional
	public Rol save(Rol rol) {
		
		return rolDao.save(rol);
	}

	@Override
	@Transactional
	public void delete(Long id) {
	
		rolDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Rol findById(Long id) {
		
		return rolDao.findById(id).orElse(null);
	}

}
