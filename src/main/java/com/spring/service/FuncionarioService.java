package com.spring.service;

import com.spring.domain.Funcionario;
import com.spring.domain.enuns.CargoEnum;
import com.spring.domain.enuns.StatusEnum;
import com.spring.dtos.FuncionarioDTO;
import com.spring.repository.FuncionarioRepository;
import com.spring.service.exceptions.DataIntegratyViolationException;
import com.spring.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	public Funcionario buscarid(Long id) {
		Optional<Funcionario> obj = funcionarioRepository.findById(id); // Já que está Optional, ele pode encontrar o id
																		// ou não
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Funcionario.class.getName()));
	}

	public List<Funcionario> listar() {
		return funcionarioRepository.ListaFuncioAtivo();
	}

	public List<Funcionario> listarTecnico() {
		return funcionarioRepository.ListaFuncioTecAtivo();
	}

	public Funcionario salvar(FuncionarioDTO objDTO) throws Exception {
		if (verificarCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}
		if (verificarLogin(objDTO) != null) {
			throw new DataIntegratyViolationException("Login já cadastrado");
		}

		return fromDTO(objDTO);

	}

	public Funcionario atualizar(Long id, @Valid FuncionarioDTO objDTO) throws Exception {
		Funcionario funcionario = buscarid(id);

		if (verificarCPF(objDTO) != null && verificarCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}

		funcionario.setNome(objDTO.getNome());
		funcionario.setCpf(objDTO.getCpf());
		funcionario.setTelefone(objDTO.getTelefone());
		funcionario.setCargo(CargoEnum.toEnum(objDTO.getCargo()));
		funcionario.setLogin(objDTO.getLogin());

		String senhaCriptografada = new BCryptPasswordEncoder().encode(objDTO.getSenha());
		funcionario.setSenha(senhaCriptografada);

		return funcionarioRepository.save(funcionario);
	}

//	public void delete(Long id) {
//		Funcionario obj = buscarid(id);
//		if (obj.getList().size() > 0) {
//			throw new DataIntegratyViolationException("Funcionário possui Ordens de Serviços ativo, não pode ser excluido");
//		}
//		funcionarioRepository.deleteById(id);
//
//	}

	public Funcionario desativarFuncionario(Long id) {

		Funcionario obj = buscarid(id);
		boolean existeOSAberto = obj.getList().stream().anyMatch(os -> {
			
			try {
				return os.getStatus().equals(StatusEnum.ABERTO);
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			}
			
			return false;
		});
		
		boolean existeOSAndamento = obj.getList().stream().anyMatch(os -> {
			
			try {
				return os.getStatus().equals(StatusEnum.ANDAMENTO);
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			}
			
			return false;
		});

		if (existeOSAberto || existeOSAndamento) {
			throw new DataIntegratyViolationException(
					"Funcionário possui Ordens de Serviços ativos, não pode ser desativado");

		}

		obj.setDataDemissao(LocalDateTime.now());
		obj.setAtivo(false);
		return funcionarioRepository.save(obj);

	}

	private Funcionario verificarCPF(FuncionarioDTO objDTO) {
		Funcionario obj = funcionarioRepository.buscarCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

	private Funcionario verificarLogin(FuncionarioDTO objDto) {
		Funcionario obj = funcionarioRepository.buscarlogin1(objDto.getLogin());
		if (obj != null) {
			return obj;
		}
		return null;
	}

	private Funcionario fromDTO(FuncionarioDTO objDto) throws IllegalAccessException {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(objDto.getId());
		funcionario.setNome(objDto.getNome());
		funcionario.setCpf(objDto.getCpf());
		funcionario.setTelefone(objDto.getTelefone());
		funcionario.setCargo(CargoEnum.toEnum(objDto.getCargo()));
		funcionario.setLogin(objDto.getLogin());

		String senhaCriptografada = new BCryptPasswordEncoder().encode(objDto.getSenha());

		funcionario.setSenha(senhaCriptografada);

		funcionario = funcionarioRepository.save(funcionario);
		implementacaoUserDetailsService.insereAcessoPadrao(funcionario.getId());

		return funcionario;
	}

}
