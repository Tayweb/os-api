package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.domain.Cliente;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("select obj from Cliente obj where obj.cpf =:cpf")
	Optional<Cliente> buscarCPF(@Param("cpf") String cpf);
}
