package market.models.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market.models.persistence.Produto;

public class ProdutoDAO {

	private EntityManager entityManager;

	public ProdutoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Produto produto) {
		this.entityManager.persist(produto);
	}

	public void delete(Produto produto) {
		this.entityManager.remove(this.entityManager.merge(produto));
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listaTodosProdutos() {
		String sql = "SELECT * FROM produto";
		return (List<Produto>) this.entityManager.createNativeQuery(sql, Produto.class).getResultList();

	}
	@SuppressWarnings("unchecked")
	public List<Produto> listaProdutosPorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String sql = "SELECT * FROM produto where nome like nome";
		return (List<Produto>) this.entityManager.createNativeQuery(sql, Produto.class).setParameter("nome", nomeFormt).getResultList();

	}
	
	
	public Produto produtoPorId(int id) {
		String sql = "SELECT * FROM produto where id = id";
		return this.entityManager.createNamedQuery(sql, Produto.class).setParameter("id", id).getSingleResult();
	}
	public Produto produtoPorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String sql = "SELECT * FROM produto where nome = nome";
		return this.entityManager.createNamedQuery(sql, Produto.class).setParameter("nome", nomeFormt).getSingleResult();
	}
	
	public Produto update(Produto novoProduto) {
		return this.entityManager.merge(this.entityManager.merge(novoProduto));
	}

}
