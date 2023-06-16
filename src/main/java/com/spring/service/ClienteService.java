package com.spring.service;

import com.spring.domain.Cliente;
import com.spring.dtos.ClienteDTO;
import com.spring.repository.ClienteRepository;
import com.spring.service.exceptions.DataIntegratyViolationException;
import com.spring.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente buscarid(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Cliente.class.getName()));
	}

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente salvar(ClienteDTO clienteDTO) {
		verificarCPF(clienteDTO);
		return clienteRepository.save(mapper.map(clienteDTO, Cliente.class));
	}

	public Cliente atualizar(Long id, @Valid ClienteDTO clienteDTO) {
		Cliente cliente = buscarid(id);
		verificarCPF(clienteDTO);
		cliente.setNome(clienteDTO.getNome());
		cliente.setCpf(clienteDTO.getCpf());
		cliente.setTelefone(clienteDTO.getTelefone());

		return clienteRepository.save(cliente);
	}

	private void verificarCPF(ClienteDTO clienteDTO) {
		Optional<Cliente> cliente = clienteRepository.buscarCPF(clienteDTO.getCpf());
		if (cliente.isPresent() && !cliente.get().getCpf().equals(clienteDTO.getCpf())) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}
	}
}
