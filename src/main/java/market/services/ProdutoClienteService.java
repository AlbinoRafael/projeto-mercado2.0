package market.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.models.dao.ProdutoClienteDAO;
import market.models.persistence.Produto;
import market.models.persistence.ProdutoCliente;

public class ProdutoClienteService {
	private final Logger LOG = LogManager.getLogger(ProdutoService.class);

	private EntityManager entityManager;

	private ProdutoClienteDAO produtoClienteDao;
	private ProdutoService produtoService;
	private ClienteService clienteService;

	public ProdutoClienteService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.produtoClienteDao = new ProdutoClienteDAO(entityManager);
		this.produtoService = new ProdutoService(entityManager);
		this.clienteService = new ClienteService(entityManager);
	}

	private void getBeginTransaction() {
		entityManager.getTransaction().begin();
	}

	private void commitAndCloseTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public ProdutoCliente findById(Long id) {
		this.LOG.info("Preparando para procurar o produtoCliente");
		if (id == null) {
			this.LOG.error("o ID do produtoCliente está nulo");
			throw new RuntimeException("The ID is null");
		}
		ProdutoCliente product = this.produtoClienteDao.produtoClientePorId(id);
		validateProductClientIsNull(product);
		return product;
	}

	public void create(ProdutoCliente produtoCliente) {
		this.LOG.info("Preparando para a criação de um produtoCliente!");
		validateProductClientIsNull(produtoCliente);
		try {
			getBeginTransaction();
			this.produtoClienteDao.create(produtoCliente);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um produtoCliente, causado por: " + e.getMessage());
			throw new RuntimeException("The ID is null");
		}
		this.LOG.info("ProdutoCliente foi criado com sucesso");
	}

	public void delete(Long id) {
		this.LOG.info("Preparando para procurar o produtoCliente");
		if (id == null) {
			this.LOG.error("o ID do produtoCliente está nulo");
			throw new RuntimeException("The ID is null");
		}
		ProdutoCliente produtoCliente = this.produtoClienteDao.produtoClientePorId(id);
		validateProductClientIsNull(produtoCliente);
		this.LOG.info("Produto encontrado com sucesso");
		getBeginTransaction();
		this.produtoClienteDao.delete(produtoCliente);
		this.LOG.info("ProdutoCliente deletado com sucesso");
		commitAndCloseTransaction();
	}

	public List<ProdutoCliente> listAll() {
		this.LOG.info("Preparando para listar os produtos");
		List<ProdutoCliente> products = this.produtoClienteDao.listaTodosProdutoClientes();

		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<ProdutoCliente>();
		}
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	private void validateProductClientIsNull(ProdutoCliente product) {
		if (product == null) {
			this.LOG.error("O ProdutoCliente não Existe!");
			throw new EntityNotFoundException("ProductClient not Found!");
		}
	}
}
