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

import com.potoware.springboot.backend.apirest.models.entity.Permiso;
import com.potoware.springboot.backend.apirest.models.services.impl.PermisoServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PermisoRestController {

	@Autowired
	PermisoServiceImpl permisoService;
	
	@GetMapping("/permisos")
	public List<Permiso> index(){
		return permisoService.findAll();
	}
	
	@GetMapping("/permisos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Permiso permiso = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			 permiso = permisoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error de conexion a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (permiso == null){
			response.put("mensaje", "El permiso ID:".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Permiso>(permiso, HttpStatus.OK);
	}
	
	@PostMapping("/permisos")
	public ResponseEntity<?> create(@Valid @RequestBody Permiso permiso, BindingResult result) {
		Permiso permisoNuevo = null;
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
			permisoNuevo = permisoService.save(permiso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido creado con exito");
		response.put("permiso", permisoNuevo);
		
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED);
	}	
	
	@PutMapping("/permisos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Permiso permiso, BindingResult result, @PathVariable Long id) {
		Permiso permisoActual = permisoService.findById(id);
		Permiso permisoActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err-> "El campo "+ err.getField() +" "+ err.getDefaultMessage()).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		if(permisoActual == null) {
			response.put("mensaje", "Error al actualizar en la base de datos, el permiso id: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			permisoActual.setNombre(permiso.getNombre());
			permisoActualizado = permisoService.save(permisoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El permiso ha sido actualizado con exito");
		response.put("permiso", permisoActualizado);
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/permisos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			permisoService.delete(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El permiso ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.OK);
	}
}
