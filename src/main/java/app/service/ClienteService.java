package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import app.entity.Cliente;
import app.repository.ClienteRepository;

public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public String save(Cliente cliente) {

		this.verificarTelefoneCliente(cliente);
		this.verificarCpfCliente(cliente.getCpf());

		this.clienteRepository.save(cliente);
		return "Aluno matriculado com sucesso!";
	}

	public String update(Cliente cliente, long id) {

		this.verificarTelefoneCliente(cliente);
		this.verificarCpfCliente(cliente.getCpf());

		cliente.setId(id);
		this.clienteRepository.save(cliente);

		return "Aluno alterado com sucesso";
	}

	
	//Verifica se já existe o cpf que está sendo cadastrado OU alterado 
	public void verificarCpfCliente(String cpf) {
		List<Cliente> clienteComCpf = clienteRepository.findByCpf(cpf);
		if (!clienteComCpf.isEmpty()) {
			throw new RuntimeException("CPF já cadastrado!");
		}
	}

	//Verifica se o cliente tem um telefone cadastrado, p/ cadastraCompleto setar true ou false
	public void verificarTelefoneCliente(Cliente cliente) {
		if (cliente.getTelefone() == null || cliente.getTelefone().isEmpty()) {
			cliente.setCadastroCompleto(false);
		} else {
			cliente.setCadastroCompleto(true);
		}
	}

	public String delete(long id) {

		this.clienteRepository.deleteById(id);

		return "Aluno deletado comm sucesso!";
	}

	public List<Cliente> findAll() {

		List<Cliente> listaClientes = this.clienteRepository.findAll();

		return listaClientes;
	}

	public Cliente findById(long id) {

		Cliente cliente = this.clienteRepository.findById(id).get();

		return cliente;
	}

	public List<Cliente> findByCpf(String cpf) {
		return this.clienteRepository.findByCpf(cpf);
	}

	public List<Cliente> findByNomeCliente(String nome) {

		return this.clienteRepository.findByNomeClienteStartingWith(nome);
	}

	public List<Cliente> findByCelular(String celular) {
		return this.clienteRepository.findByCelularContaining(celular);
	}

}
