package com.br.alura.domain.topico;

import java.time.LocalDateTime;

import com.br.alura.domain.usuario.Usuario;

import ch.qos.logback.core.status.Status;

public record DadosAtualizacaoTopico(Long id , String titulo, String mensagem, LocalDateTime data_criacao, StatusTopico status, Usuario autor , Curso curso) {

    public DadosAtualizacaoTopico(Topico topico) {

        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getDataCriacao(), topico.getStatus(), topico.getAutor() ,topico.getCurso() );

    }

}