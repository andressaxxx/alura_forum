package com.br.alura.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

	public record DadosAutenticaco(
			
			@NotBlank
			@Email
			String email, 
			
			@NotBlank
			String senha) {
		
}
