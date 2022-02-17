package com.spring.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Cliente;
import com.spring.domain.OS;
import com.spring.domain.Tecnico;
import com.spring.domain.enuns.Prioridade;
import com.spring.domain.enuns.Status;
import com.spring.repository.ClienteRepository;
import com.spring.repository.OSRepository;
import com.spring.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Tainá Fontes", "114.873.248-98", "(79) 99810-5605");
		Cliente c1 = new Cliente(null, "João Silva", "407.348.658-64", "(79) 988956412");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
	}
}
