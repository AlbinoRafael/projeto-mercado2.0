package market.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.models.dao.CategoriaDAO;
import market.models.persistence.Categoria;
import market.models.persistence.Cliente;
import market.models.persistence.Produto;

public class CategoriaService {

	private final Logger LOG = LogManager.getLogger(ProdutoService.class);

	private EntityManager entityManager;

	private CategoriaDAO categoriaDao;

	public CategoriaService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.categoriaDao = new CategoriaDAO(entityManager);
	}

	private void getBeginTransaction() {
		this.LOG.info("Abrindo Transação com o banco de dados...");
		entityManager.getTransaction().begin();
	}

	private void commitAndCloseTransaction() {
		this.LOG.info("Commitando e Fechando transação com o banco de dados");
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public Categoria findByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.error("O nome não pode ser nulo");
			throw new RuntimeException("The name is null");
		}
		try {
			return this.categoriaDao.categoriaPorNome(name.toLowerCase());
		} catch (NoResultException r) {
			this.LOG.info("Não foi encontrado Categoria, será criada!");
			return null;
		}
	}

	public Categoria getById(Long id) {
		if (id == null) {
			this.LOG.error("O ID está nulo!");
			throw new RuntimeException("The parameter is null!");
		}

		Categoria category = this.categoriaDao.categoriaPorId(id);

		if (category == null) {
			this.LOG.error("Não foi encontrado a categoria de id " + id);
			throw new EntityNotFoundException("Category not found!");
		}
		return category;
	}
	public void create(Categoria categoria) {
		this.LOG.info("Preparando para a criação de um cliente!");
		validateCategoryIsNull(categoria);
		try {
			getBeginTransaction();
			this.categoriaDao.create(categoria);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um cliente, causado por: " + e.getMessage());
			throw new RuntimeException("The ID is null");
		}
		this.LOG.info("Cliente foi criado com sucesso");
	}
	
	public void delete(Long id) {
		if (id == null) {
			this.LOG.error("O ID está nulo!");
			throw new RuntimeException("The parameter is null!");
		}
		Categoria category = this.categoriaDao.categoriaPorId(id);
		validateCategoryIsNull(category);
		
		this.LOG.info("Categoria encontrada com sucesso!");

		getBeginTransaction();
		categoriaDao.delete(category);
		commitAndCloseTransaction();
		
		this.LOG.info("Categoria deletada com sucesso!");
	}
	
	public List<Categoria> listAll() {
		this.LOG.info("Preparando para listar os clientes");
		List<Categoria> clientes = this.categoriaDao.listaTodasCategorias();

		if (clientes == null) {
			this.LOG.info("Não foram encontrados Clientes");
			return new ArrayList<Categoria>();
		}
		this.LOG.info("Foram encontrados " + clientes.size() + " clientes.");
		return clientes;
	}
	
	private void validateCategoryIsNull(Categoria category) {
		if (category == null) {
			this.LOG.error("A categoria não Existe!");
			throw new EntityNotFoundException("Category not Found!");
		}
	}

}
