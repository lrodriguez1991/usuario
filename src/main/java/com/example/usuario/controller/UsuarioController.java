package com.example.usuario.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuario.model.Usuario;
import com.example.usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private final UsuarioService service;

	public UsuarioController(UsuarioService service) {
		this.service = service;
	}

	@GetMapping
	public List<Usuario> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
		return service.obtener(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Usuario> registrar(@RequestBody Usuario p) {
		Usuario newUsuario = service.registrar(p);
		return ResponseEntity.created(URI.create("/api/usuarios/" + newUsuario.getId())).body(newUsuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> editar(@PathVariable Long id, @RequestBody Usuario p) {
		return ResponseEntity.ok(service.editar(id, p));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		service.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
