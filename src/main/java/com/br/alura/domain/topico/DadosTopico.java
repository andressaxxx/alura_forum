package com.br.alura.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosTopico (
		
		@NotBlank
        String titulo,
        
        @NotBlank
        String mensagem,
        
        @NotBlank
        String autor,

        @NotNull
        Curso curso){
}
