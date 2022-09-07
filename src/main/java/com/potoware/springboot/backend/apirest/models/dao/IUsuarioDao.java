package com.potoware.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.potoware.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

}
