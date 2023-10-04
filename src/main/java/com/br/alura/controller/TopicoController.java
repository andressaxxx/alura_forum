package com.br.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.alura.domain.topico.DadosAtualizacaoTopico;
import com.br.alura.domain.topico.DadosDetalhamentoTopico;
import com.br.alura.domain.topico.DadosTopico;
import com.br.alura.domain.topico.StatusTopico;
import com.br.alura.domain.topico.Topico;
import com.br.alura.domain.topico.TopicoRepository;
import com.br.alura.domain.usuario.Usuario;
import com.br.alura.domain.usuario.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    
    @Autowired UsuarioRepository usuarioRepository;

    @PostMapping("preencher")
    public ResponseEntity preencherTopico(@RequestBody @Valid DadosTopico dados) {

        var topico = new Topico(dados);
        topicoRepository.save(topico);
        return ResponseEntity.ok().body(dados);

    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        Page<Topico> page = topicoRepository.findTopicosNaoFechados(paginacao);
        Page<DadosDetalhamentoTopico> dadosPage = page.map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(dadosPage);
    }

    @GetMapping("/{id}")
    public  ResponseEntity detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.getReferenceById(dados.id());
        if (topico != null) {
        	 if (topico.getStatus().equals(StatusTopico.FECHADO)){
                 return ResponseEntity.badRequest().body("Tópico Fechado!");
             } else if (topico.getCurso() != null){
            	 if (topico.getAutor() != null & topico.getMensagem() != null) {
            		 topico.setCurso(dados.curso());
            		 topico.setTitulo(dados.titulo());
            		 topico.setAutor(dados.autor());
            	 } else {
            		 return ResponseEntity.badRequest().body("Precisamos de um autor e uma pergunta :)");
            	 }
             } else {
            	 return ResponseEntity.badRequest().body("Curso não encontrado");
             }
        } else {
        	return ResponseEntity.badRequest().body("Tópico não encontrado!");
        }
       

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topico.setStatus(StatusTopico.FECHADO);
        topicoRepository.save(topico);

        return ResponseEntity.noContent().build();
    }

}