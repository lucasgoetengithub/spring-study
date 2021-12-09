package com.estudo.springstudy.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.springstudy.domain.Categoria;
import com.estudo.springstudy.service.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	CategoriaService categoriaService;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = categoriaService.Find(id);		
		return ResponseEntity.ok().body(obj);
	}
}
