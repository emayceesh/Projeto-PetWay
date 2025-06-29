package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.repository.ClienteRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public String save(Cliente cliente) {

		this.verificarTelefoneCliente(cliente);
		this.verificarCpfCliente(cliente.getCpf());

		this.clienteRepository.save(cliente);
		return "Cliente cadastrado com sucesso!";
	}

	public String update(Cliente cliente, long id) {

		cliente.setId(id);
		this.clienteRepository.save(cliente);

		return "Cliente alterado com sucesso!";
	}

	
	//Verifica se já existe o cpf que está sendo cadastrado OU alterado 
	public void verificarCpfCliente(String cpf) {
		List<Cliente> clienteComCpf = clienteRepository.findByCpfStartingWith(cpf);
		if (!clienteComCpf.isEmpty()) {
			throw new RuntimeException("CPF já cadastrado!");
		}
	}

	//Verifica se o cliente tem um telefone cadastrado, p/ cadastraCompleto setar true ou false
	public void verificarTelefoneCliente(Cliente cliente) {
		if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
			cliente.setCadastroCompleto(false);
		} else {
			cliente.setCadastroCompleto(true);
		}
	}

	public String delete(long id) {

		this.clienteRepository.deleteById(id);

		return "Cliente excluido com sucesso!";
	}

	public Page<Cliente> findAll(int numPage, int numQntdPorPagina) {

		PageRequest config = PageRequest.of(numPage-1, numQntdPorPagina);

		return this.clienteRepository.findAll(config);
	}

	public Cliente findById(long id) {

		Cliente cliente = this.clienteRepository.findById(id).get();

		return cliente;
	}

	public List<Cliente> findByCpf(String cpf) {
		return this.clienteRepository.findByCpfStartingWith(cpf);
	}

	public List<Cliente> findByNomeClienteIgnoreCaseStartingWith(String nome) {

		return this.clienteRepository.findByNomeClienteIgnoreCaseStartingWith(nome);
	}

}
