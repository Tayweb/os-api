package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.domain.Funcionario;
import com.spring.repository.FuncionarioRepository;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Funcionario funcionario = funcionarioRepository.acessoLogin(username);

		if (funcionario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}
		return new User(funcionario.getLogin(), funcionario.getPassword(), funcionario.getAuthorities());
	}

	public void insereAcessoPadrao(Long id) {

		// Descobre a constraint de restrição
		String constraint = funcionarioRepository.consultaConstraintRole();

		if (constraint != null) {
			// Remove a restrição
			jdbcTemplate.execute(" alter table usuarios_role drop constraint " + constraint);

		}

		// Insere o acesso padrão
		funcionarioRepository.inserirAcessoRolePadrao(id);

	}

}
