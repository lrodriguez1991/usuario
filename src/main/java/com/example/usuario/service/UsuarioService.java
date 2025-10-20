package com.example.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.usuario.model.Usuario;
import com.example.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}

	public List<Usuario> listar() {
		return repository.findAll();
	}

	public Optional<Usuario> obtener(Long id) {
		return repository.findById(id);
	}

	public Usuario registrar(Usuario p) {
		return repository.save(p);
	}

	public Object editar(Long id, Usuario p) {
		return repository.findById(id).map(u -> {
			u.setName(p.getName());
			u.setEmail(p.getEmail());
			return repository.save(u);
		}).orElseThrow(() -> new RuntimeException("Not found"));
	}

	public void eliminar(Long id) {
		repository.deleteById(id);
	}
}
