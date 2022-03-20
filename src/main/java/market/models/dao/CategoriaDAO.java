package market.models.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market.models.persistence.Categoria;

public class CategoriaDAO {

	private EntityManager entityManager;

	public CategoriaDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Categoria categoria) {
		this.entityManager.persist(categoria);
	}

	public void delete(Categoria categoria) {
		this.entityManager.remove(this.entityManager.merge(categoria));
	}

	public List<Categoria> listaTodasCategorias() {
		String jpql = "SELECT c FROM Categoria c";
		return (List<Categoria>) this.entityManager.createQuery(jpql, Categoria.class).getResultList();

	}

	public List<Categoria> listaCategoriasPorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String jpql = "SELECT c FROM Categoria where nome like :nome";
		return (List<Categoria>) this.entityManager.createQuery(jpql, Categoria.class).setParameter("nome", nomeFormt)
				.getResultList();

	}

	public Categoria categoriaPorId(Long id) {
		String jpql = "SELECT c FROM Categoria where c.id = :id";
		return (Categoria) this.entityManager.createQuery(jpql, Categoria.class).setParameter("id", id)
				.getSingleResult();
	}

	public Categoria categoriaPorNome(String nome) {
		String jpql = "SELECT c FROM Categoria c where c.nome = :nome";
		return (Categoria) this.entityManager.createQuery(jpql, Categoria.class).setParameter("nome", nome)
				.getSingleResult();
	}

	public Categoria update(Categoria novaCategoria) {
		return this.entityManager.merge(this.entityManager.merge(novaCategoria));
	}
}
