package com.br.alura.domain.usuario;

public record DadosBuscaDeUsuario(Long id, String nome, String email) {

	public DadosBuscaDeUsuario (Usuario usuario) {
		this (usuario.getId(), usuario.getNome(), usuario.getEmail());
	}
}
