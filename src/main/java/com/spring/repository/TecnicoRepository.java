package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

	@Query("select obj from Tecnico obj where obj.cpf =:cpf")
	Tecnico buscarCPF(@Param("cpf") String cpf);

}
