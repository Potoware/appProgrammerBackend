package com.potoware.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.potoware.springboot.backend.apirest.models.entity.Usuario;
import com.potoware.springboot.backend.apirest.models.services.impl.CifradoServiceImpl;
import com.potoware.springboot.backend.apirest.models.services.impl.UsuarioServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	UsuarioServiceImpl usuarioService;
	
	@Autowired
	CifradoServiceImpl cifradoService;
	
	@GetMapping("/usuarios")
	public List<Usuario> index(){
		return usuarioService.findAll();
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			 usuario = usuarioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error de conexion a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null){
			response.put("mensaje", "El usuario ID:".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario usuarioNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			/*List<String> errors = new ArrayList<>();
			for(FieldError err:result.getFieldErrors()) {
				errors.add("El campo "+ err.getField() +" "+ err.getDefaultMessage());
			}*/
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err-> "El campo "+ err.getField() +" "+ err.getDefaultMessage()).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			usuario.setContrasenia(cifradoService.cifrarContrasenia(usuario.getContrasenia()));
			usuarioNuevo = usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con exito");
		response.put("cliente", usuarioNuevo);
		
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
		Usuario usuarioActual = usuarioService.findById(id);
		Usuario usuarioActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err-> "El campo "+ err.getField() +" "+ err.getDefaultMessage()).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		if(usuarioActual == null) {
			response.put("mensaje", "Error al actualizar en la base de datos, el usuario id: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setContrasenia(cifradoService.cifrarContrasenia(usuario.getContrasenia()));
			usuarioActualizado = usuarioService.save(usuarioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario ha sido actualizado con exito");
		response.put("usuario", usuarioActualizado);
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.delete(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.OK);
	}
}
