package com.br.alura.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario , Long>{
	
	UserDetails findByEmail(String email);
	UserDetails findByNome(String nome);
	Page<Usuario> findAllByAtivoTrue(Pageable paginacao);


}
