package com.potoware.springboot.backend.apirest.models.services;

public interface ICifradoService {

	String cifrarContrasenia(String contrasenia);
	
	boolean verificarContrasenia(String contrasenia, String hash);
	
}
