package market.models.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market.models.persistence.Cliente;

public class ClienteDAO {

	private EntityManager entityManager;

	public ClienteDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Cliente cliente) {
		this.entityManager.persist(cliente);
	}

	public void delete(Cliente cliente) {
		this.entityManager.remove(cliente);
	}

	public List<Cliente> listaTodosClientes() {
		String jpql = "SELECT c FROM Cliente c";
		return this.entityManager.createQuery(jpql, Cliente.class).getResultList();
	}

	public List<Cliente> listaClientesPorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String jpql = "SELECT c FROM Cliente c where c.nome like :nome";
		return this.entityManager.createQuery(jpql, Cliente.class).setParameter("nome", nomeFormt).getResultList();
	}

	public Cliente clientePorId(Long id) {
		String jpql = "SELECT c FROM Cliente c where c.id = :id";
		return (Cliente) this.entityManager.createQuery(jpql, Cliente.class).setParameter("id", id).getSingleResult();
	}

	public Cliente clientePorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String jpql = "SELECT c FROM cliente c where c.nome = :nome";
		return (Cliente) this.entityManager.createQuery(jpql, Cliente.class).setParameter("nome", nomeFormt)
				.getSingleResult();
	}

}
