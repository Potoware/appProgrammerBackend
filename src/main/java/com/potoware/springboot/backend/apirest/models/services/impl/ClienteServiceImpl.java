package com.potoware.springboot.backend.apirest.models.services.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.potoware.springboot.backend.apirest.models.dao.IClienteDao;
import com.potoware.springboot.backend.apirest.models.entity.Cliente;
import com.potoware.springboot.backend.apirest.models.services.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
	
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		
		return clienteDao.findById(id).orElse(null);
	}

}
