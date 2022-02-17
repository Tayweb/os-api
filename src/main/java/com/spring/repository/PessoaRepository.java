package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.domain.Pessoa;
import com.spring.domain.Tecnico;

@Repository
public interface PessoaRepository extends JpaRepository<Tecnico, Long> {

	@Query("select obj from Pessoa obj where obj.cpf =:cpf")
	Pessoa buscarCPF(@Param("cpf") String cpf);

}
