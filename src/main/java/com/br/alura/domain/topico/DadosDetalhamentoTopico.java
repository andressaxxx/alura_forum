package com.br.alura.domain.topico;

import java.time.LocalDateTime;

import ch.qos.logback.core.status.Status;

public record DadosDetalhamentoTopico(Long id , String titulo, String mensagem, LocalDateTime data_criacao, StatusTopico status, String autor , Curso curso) {

    public DadosDetalhamentoTopico(Topico topico) {

        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome() ,topico.getCurso() );

    }

}