package market.models.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market.models.persistence.ProdutoCliente;

public class ProdutoClienteDAO {
	
	private EntityManager entityManager;

	public ProdutoClienteDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(ProdutoCliente produtoCliente) {
		this.entityManager.persist(produtoCliente);
	}

	public void delete(ProdutoCliente produtoCliente) {
		this.entityManager.remove(produtoCliente);
	}

	@SuppressWarnings("unchecked")
	public List<ProdutoCliente> listaTodosProdutoClientes() {
		String jpql = "SELECT pc FROM ProdutoCliente pc";
		return this.entityManager.createNativeQuery(jpql, ProdutoCliente.class).getResultList();
	}

	public ProdutoCliente produtoClientePorId(Long id) {
		String jpql = "SELECT pc FROM ProdutoCliente pc where pc.id = :id";
		return (ProdutoCliente) this.entityManager.createQuery(jpql, ProdutoCliente.class).setParameter("id", id)
				.getSingleResult();
	}

}
