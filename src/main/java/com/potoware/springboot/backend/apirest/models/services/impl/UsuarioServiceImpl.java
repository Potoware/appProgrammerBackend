package com.potoware.springboot.backend.apirest.models.services.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.potoware.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.potoware.springboot.backend.apirest.models.entity.Usuario;
import com.potoware.springboot.backend.apirest.models.services.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
	
		usuarioDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		
		return usuarioDao.findById(id).orElse(null);
	}

}
