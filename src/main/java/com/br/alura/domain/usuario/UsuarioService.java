package com.br.alura.domain.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarUsuarios(Pageable paginacao) {
        return usuarioRepository.findAllByAtivoTrue(paginacao);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Object> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Object> buscarUsuarioPorNome(String nome) {
        return Optional.ofNullable(usuarioRepository.findByNome(nome));
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}