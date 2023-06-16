package com.spring.service;

import com.spring.config.ModelMapperConfig;
import com.spring.domain.Funcionario;
import com.spring.domain.enuns.CargoEnum;
import com.spring.domain.enuns.StatusEnum;
import com.spring.dtos.FuncionarioDTO;
import com.spring.repository.FuncionarioRepository;
import com.spring.service.exceptions.DataIntegratyViolationException;
import com.spring.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
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
	private ModelMapper mapper;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	public Funcionario buscarid(Long id) {
		Optional<Funcionario> obj = funcionarioRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Funcionario.class.getName()));
	}

	public List<Funcionario> listar() {
		return funcionarioRepository.ListaFuncioAtivo();
	}

	public List<Funcionario> listarTecnico() {
		return funcionarioRepository.ListaFuncioTecAtivo();
	}

	public Funcionario salvar(FuncionarioDTO funcionarioDTO) {
		verificarCPF(funcionarioDTO);
		verificarLogin(funcionarioDTO);
		return funcionarioRepository.save(mapper.map(funcionarioDTO, Funcionario.class));

	}

	public Funcionario atualizar(Long id, @Valid FuncionarioDTO funcionarioDTO) throws Exception {
		Funcionario funcionario = buscarid(id);
		verificarCPF(funcionarioDTO);

		funcionario.setNome(funcionarioDTO.getNome());
		funcionario.setCpf(funcionarioDTO.getCpf());
		funcionario.setTelefone(funcionarioDTO.getTelefone());
		funcionario.setCargo(CargoEnum.toEnum(funcionarioDTO.getCargo()));
		funcionario.setLogin(funcionarioDTO.getLogin());
		funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionarioDTO.getSenha()));
		return funcionarioRepository.save(funcionario);
	}

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

	private void verificarCPF(FuncionarioDTO funcionarioDTO) {
		Optional<Funcionario> cliente = funcionarioRepository.buscarCPF(funcionarioDTO.getCpf());
		if (cliente.isPresent() && !cliente.get().getCpf().equals(funcionarioDTO.getCpf())) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}
	}

	private void verificarLogin(FuncionarioDTO funcionarioDTO) {
		Optional<Funcionario> funcionario = funcionarioRepository.buscarCPF(funcionarioDTO.getLogin());
		if (funcionario.isPresent()) {
			throw new DataIntegratyViolationException("Login já cadastrado");
		}
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
