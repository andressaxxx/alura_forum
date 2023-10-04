package com.br.alura.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.alura.domain.usuario.DadosAutenticaco;
import com.br.alura.domain.usuario.DadosBuscaDeUsuario;
import com.br.alura.domain.usuario.Usuario;
import com.br.alura.domain.usuario.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registrarUsuario(@RequestBody @Valid DadosAutenticaco dadosAutenticacao, UriComponentsBuilder uriComponentsBuilder) {
        Usuario usuario = new Usuario(dadosAutenticacao.email(), dadosAutenticacao.senha());
        if (usuarioService.buscarUsuarioPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Já existe um usuário com o email informado");
        }
        usuarioService.salvarUsuario(usuario);
        DadosBuscaDeUsuario buscarUsuario = new DadosBuscaDeUsuario(usuario);
        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(buscarUsuario.id()).toUri();
        return ResponseEntity.created(uri).body(buscarUsuario);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> buscarUsuarioPorId(@PathVariable Long id) {
        Optional <Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um usuário com o id informado");
        }
        var usuario = usuarioOptional.get();
        DadosBuscaDeUsuario buscarUsuario = new DadosBuscaDeUsuario(usuario);
        return ResponseEntity.ok(buscarUsuario);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Object> listarUsuarios(@PageableDefault(page = 0, size = 20, sort = "id") Pageable paginacao) {
        Optional <Page<Usuario>> usuariosOptional = Optional.ofNullable(usuarioService.listarUsuarios(paginacao));
        if (usuariosOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existem usuários cadastrados");
        }
        Page<Usuario> usuarios = usuariosOptional.get();
        Page<DadosBuscaDeUsuario> buscaUsuario = usuarios.map(DadosBuscaDeUsuario::new);
        return ResponseEntity.ok(buscaUsuario);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid DadosAutenticaco dadosUsuario) {
        Optional <Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um usuário com o id informado");
        }
        var usuario = usuarioOptional.get();
        BeanUtils.copyProperties(dadosUsuario, usuario);
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(new DadosBuscaDeUsuario(usuario));
    }

    @PutMapping("/{id}/ativar")
    @Transactional
    public ResponseEntity<Object> ativarUsuario(@PathVariable Long id) {
        Optional <Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um usuário com o id informado");
        }
        Usuario usuario = usuarioOptional.get();
        if (usuario.isAtivo()) {
            return ResponseEntity.badRequest().body("O usuário já está ativo");
        }
        usuario.setAtivo(true);
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok().body(" O usuário " + id + " foi ativado com sucesso!");
    }

    @PutMapping("/{id}/desativar")
    @Transactional
    public ResponseEntity<Object> desativarUsuario(@PathVariable Long id) {
        Optional <Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um usuário com o id informado");
        }
        Usuario usuario = usuarioOptional.get();
        if (!usuario.isAtivo()) {
            return ResponseEntity.badRequest().body("O usuário já está desativado");
        }
        usuario.setAtivo(false);
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok().body(" O usuário " + id + " foi desativado com sucesso!");
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> excluirUsuario(@PathVariable Long id) {
        Optional <Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um usuário com o id informado");
        }
        usuarioService.excluirUsuario(id);
        return ResponseEntity.ok().body(" O usuário " + id + " foi excluído com sucesso!");
    }

}