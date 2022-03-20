package market.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.models.dao.ClienteDAO;
import market.models.persistence.Categoria;
import market.models.persistence.Cliente;
import market.models.persistence.Produto;

public class ClienteService {

	private final Logger LOG = LogManager.getLogger(ProdutoService.class);

	private EntityManager entityManager;

	private ClienteDAO clienteDao;
	
	public ClienteService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.clienteDao = new ClienteDAO(entityManager);
	}

	private void getBeginTransaction() {
		entityManager.getTransaction().begin();
	}
	private void commitAndCloseTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	public void create(Cliente cliente) {
		this.LOG.info("Preparando para a criação de um cliente!");
		validateClientIsNull(cliente);
		try {
			getBeginTransaction();
			this.clienteDao.create(cliente);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um cliente, causado por: " + e.getMessage());
			throw new RuntimeException("The ID is null");
		}
		this.LOG.info("Cliente foi criado com sucesso");
	}
	
	public void delete(Long id) {
		this.LOG.info("Preparando para procurar o cliente");
		if(id==null) {
			this.LOG.error("o ID do produto está nulo");
			throw new RuntimeException("The ID is null");
		}
		Cliente cliente = this.clienteDao.clientePorId(id);
		validateClientIsNull(cliente);
		this.LOG.info("Cliente encontrado com sucesso");
		getBeginTransaction();
		this.clienteDao.delete(cliente);
		this.LOG.info("Cliente deletado com sucesso");
		commitAndCloseTransaction();
	}
	public Cliente clientePorId(Long id) {
		this.LOG.info("Preparando para procurar o cliente");
		if (id == null) {
			this.LOG.error("o ID do cliente está nulo");
			throw new RuntimeException("The ID is null");
		}
		Cliente cli = this.clienteDao.clientePorId(id);
		validateClientIsNull(cli);
		return cli;
	}

	public void update(Cliente newCliente, Long clienteId) {
		this.LOG.info("Preparando para Atualizar o Cliente");
		if (newCliente == null || clienteId == null) {
			this.LOG.error("Um dos parâmetros está nulo!");
			throw new RuntimeException("The parameter is null");
		}

		Cliente cli = this.clienteDao.clientePorId(clienteId);
		validateClientIsNull(cli);

		this.LOG.info("Cliente encontrado no banco!");

		getBeginTransaction();
		cli.setNome(newCliente.getNome());
		cli.setCpf(newCliente.getCpf());
		cli.setDataNascimento(newCliente.getDataNascimento());

		commitAndCloseTransaction();
		this.LOG.info("Cliente atualizado com sucesso!");

	}

	public List<Cliente> listAll() {
		this.LOG.info("Preparando para listar os clientes");
		List<Cliente> clientes = this.clienteDao.listaTodosClientes();

		if (clientes == null) {
			this.LOG.info("Não foram encontrados Clientes");
			return new ArrayList<Cliente>();
		}
		this.LOG.info("Foram encontrados " + clientes.size() + " clientes.");
		return clientes;
	}

	public List<Cliente> listByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.info("O parametro nome está vazio");
			throw new RuntimeException("The parameter name is null");
		}

		this.LOG.info("Preparando para buscar os clientes com o nome: " + name);
		List<Cliente> clientes = this.clienteDao.listaClientesPorNome(name);

		if (clientes == null) {
			this.LOG.info("Não foram encontrados Clientes");
			return new ArrayList<Cliente>();
		}

		this.LOG.info("Foram encontrados " + clientes.size() + " clientes.");
		return clientes;
	}
	private void validateClientIsNull(Cliente cliente) {
		if (cliente == null) {
			this.LOG.error("O cliente não Existe!");
			throw new EntityNotFoundException("Client not Found!");
		}
	}
}
