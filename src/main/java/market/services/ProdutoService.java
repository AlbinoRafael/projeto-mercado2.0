package market.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.models.dao.ProdutoDAO;
import market.models.persistence.Categoria;
import market.models.persistence.Produto;

public class ProdutoService {

	private final Logger LOG = LogManager.getLogger(ProdutoService.class);

	private EntityManager entityManager;

	private ProdutoDAO productDAO;

	private CategoriaService categoryService;

	public ProdutoService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.productDAO = new ProdutoDAO(entityManager);
		this.categoryService = new CategoriaService(entityManager);
	}

	private void getBeginTransaction() {
		entityManager.getTransaction().begin();
	}

	private void commitAndCloseTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void create(Produto product) {
		this.LOG.info("Preparando para a criação de um produto!");
		if (product == null) {
			this.LOG.error("O produto informado está nulo!");
			throw new RuntimeException("O produto está nulo!");
		}
		String categoryName = product.getCategoria().getNome();
		this.LOG.info("Buscando se ja existe a categoria: " + categoryName);
		Categoria category = this.categoryService.findByName(categoryName);
		if (category != null) {
			this.LOG.info("categoria '" + categoryName + "' já existe");
			product.setCategoria(category);
		}
		try {
			getBeginTransaction();
			this.productDAO.create(product);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um produto, causado por: " + e.getMessage());
			throw new RuntimeException("The ID is null");
		}
		this.LOG.info("Produto foi criado com sucesso");
	}

	public void delete(Long id) {
		this.LOG.info("Preparando para procurar o produto");
		if (id == null) {
			this.LOG.error("o ID do produto está nulo");
			throw new RuntimeException("The ID is null");
		}
		Produto product = this.productDAO.produtoPorId(id);
		validateProductIsNull(product);
		this.LOG.info("Produto encontrado com sucesso");
		getBeginTransaction();
		this.productDAO.delete(product);
		this.LOG.info("Produto deletado com sucesso");
		commitAndCloseTransaction();
	}

	public Produto produtoPorId(Long id) {
		this.LOG.info("Preparando para procurar o produto");
		if (id == null) {
			this.LOG.error("o ID do produto está nulo");
			throw new RuntimeException("The ID is null");
		}
		Produto product = this.productDAO.produtoPorId(id);
		validateProductIsNull(product);
		return product;
	}

	public void update(Produto newProduct, Long productId) {
		this.LOG.info("Preparando para Atualizar o Produto");
		if (newProduct == null || productId == null) {
			this.LOG.error("Um dos parâmetros está nulo!");
			throw new RuntimeException("The parameter is null");
		}

		Produto product = this.productDAO.produtoPorId(productId);
		validateProductIsNull(product);

		this.LOG.info("Produto encontrado no banco!");

		getBeginTransaction();
		product.setNome(newProduct.getNome());
		product.setDescricao(newProduct.getDescricao());
		product.setPreco(newProduct.getPreco());
		product.setCategoria(this.categoryService.findByName(newProduct.getCategoria().getNome()));

		commitAndCloseTransaction();
		this.LOG.info("Produto atualizado com sucesso!");

	}

	public List<Produto> listAll() {
		this.LOG.info("Preparando para listar os produtos");
		List<Produto> products = this.productDAO.listaTodosProdutos();

		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<Produto>();
		}
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	public List<Produto> listByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.info("O parametro nome está vazio");
			throw new RuntimeException("The parameter name is null");
		}

		this.LOG.info("Preparando para buscar os produtos com o nome: " + name);
		List<Produto> products = this.productDAO.listaProdutosPorNome(name);

		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<Produto>();
		}

		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	private void validateProductIsNull(Produto product) {
		if (product == null) {
			this.LOG.error("O Produto não Existe!");
			throw new EntityNotFoundException("Product not Found!");
		}
	}
}
