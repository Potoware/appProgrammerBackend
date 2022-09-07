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

import com.potoware.springboot.backend.apirest.models.entity.Rol;
import com.potoware.springboot.backend.apirest.models.services.impl.RolServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RolRestController {

	@Autowired 
	RolServiceImpl rolService;
	
	@GetMapping("/roles")
	public List<Rol> index(){
		return rolService.findAll();
	}
	
	@GetMapping("/roles/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Rol rol = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			 rol = rolService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error de conexion a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (rol == null){
			response.put("mensaje", "El rol ID:".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Rol>(rol, HttpStatus.OK);
	}
	
	@PostMapping("/roles")
	public ResponseEntity<?> create(@Valid @RequestBody Rol rol, BindingResult result) {
		Rol rolNuevo = null;
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
			rolNuevo = rolService.save(rol);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El rol ha sido creado con exito");
		response.put("rol", rolNuevo);
		
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED);
	}
	
	@PutMapping("/roles/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Rol rol, BindingResult result, @PathVariable Long id) {
		Rol rolActual = rolService.findById(id);
		Rol rolActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err-> "El campo "+ err.getField() +" "+ err.getDefaultMessage()).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		if(rolActual == null) {
			response.put("mensaje", "Error al actualizar en la base de datos, el rol id: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			rolActual.setNombre(rol.getNombre());
			rolActualizado = rolService.save(rolActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El rol ha sido actualizado con exito");
		response.put("rol", rolActualizado);
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			rolService.delete(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El rol ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.OK);
	}
	
}
