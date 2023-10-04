package com.br.alura.domain.topico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    @Query("SELECT t FROM Topico t WHERE t.status != 'FECHADO'")
    Page<Topico> findTopicosNaoFechados(Pageable pageable);
    List<Topico> findAllByAutorId(Long autor_id);
    Topico findByTitulo(String titulo);

}