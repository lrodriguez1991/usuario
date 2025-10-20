package com.example.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.usuario.controller.UsuarioController;
import com.example.usuario.model.Usuario;
import com.example.usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	UsuarioService service;

	@Autowired
	ObjectMapper mapper;

	@TestConfiguration
	static class MockConfig {
		@Bean
		public UsuarioService usuarioService() {
			return Mockito.mock(UsuarioService.class);
		}
	}

	@Test
	void listarTest() {
		when(service.listar()).thenReturn(Arrays.asList(new Usuario("LUIS ARTURO", "LRODRIGUEZ@GMAIL.COM")));
		List<Usuario> lista = service.listar();
		assertEquals(1, lista.size());
	}

	@Test
	void obtenerTest() {
		Usuario eUsuario = new Usuario("LUIS", "LRODRIGUEZ1@GMAIL.COM");
		eUsuario.setId(1L);
		when(service.obtener(1L)).thenReturn(Optional.of(eUsuario));

		Optional<Usuario> pUsuario = service.obtener(1L);
		assertTrue(pUsuario.isPresent());
		assertEquals("LUIS", pUsuario.get().getName());
	}

	@Test
	void registrarTest() throws Exception {
		Usuario nUsuario = new Usuario("LUIS ARTURO", "LRODRIGUEZ@GMAIL.COM");
		when(service.registrar(any())).thenAnswer(i -> {
			Usuario arg = i.getArgument(0);
			arg.setId(1L);
			return arg;
		});

		mvc.perform(post("/api/usuarios").contentType("application/json").content(mapper.writeValueAsString(nUsuario)))
				.andExpect(status().isCreated());
	}

	@Test
	void eliminarTest() {
		service.eliminar(1L);
		verify(service, times(1)).eliminar(1L);
	}

}
