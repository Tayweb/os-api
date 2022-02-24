package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.domain.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query("select obj from Funcionario obj where obj.cpf =:cpf")
	Funcionario buscarCPF(@Param("cpf") String cpf);
	
	@Query("select obj from Funcionario obj where obj.login =:login")
	Funcionario buscarlogin1(@Param("login") String login);
	
	@Query("select u from Funcionario u where u.login =?1")
	Funcionario acessoLogin(String login);
	
	@Query("select obj from Funcionario obj where obj.ativo = true")
	List<Funcionario> ListaFuncioAtivo();

	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update funcionario set token = ?1 where login = ?2")
	void atualizaTokenUser(String login, String senha);
	
	
	@Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_role' and column_name = 'role_id'\r\n"
			+ "	and constraint_name <> 'unique_role_user';", nativeQuery = true)
	String consultaConstraintRole();



	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuarios_role (funcionario_id, role_id) values(?1,(select id from role where nome_role = 'ROLE_USUARIO'))")
	void inserirAcessoRolePadrao(Long id);
}
