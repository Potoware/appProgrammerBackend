package com.potoware.springboot.backend.apirest.models.services.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.potoware.springboot.backend.apirest.models.services.ICifradoService;

@Service
public class CifradoServiceImpl implements ICifradoService{

	@Override
	public String cifrarContrasenia(String contrasenia) {
		
		return BCrypt.hashpw(contrasenia, BCrypt.gensalt()) ;
	}

	@Override
	public boolean verificarContrasenia(String contrasenia, String hash) {
		// TODO Auto-generated method stub
		return false;
	}

}
