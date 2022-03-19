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

	@SuppressWarnings("unchecked")
	public List<Cliente> listaTodosClientes() {
		String sql = "SELECT * FROM cliente";
		return this.entityManager.createNativeQuery(sql, Cliente.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listaClientesPorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String sql = "SELECT * FROM cliente where nome = nome";
		return this.entityManager.createNativeQuery(sql, Cliente.class).setParameter("nome", nomeFormt).getResultList();
	}

	public Cliente clientePorId(int id) {
		String sql = "SELECT * FROM cliente where id = id";
		return (Cliente) this.entityManager.createNativeQuery(sql, Cliente.class).setParameter("id", id)
				.getSingleResult();
	}

	public Cliente clientePorNome(String nome) {
		String nomeFormt = nome.toLowerCase();
		String sql = "SELECT * FROM cliente where nome = nome";
		return (Cliente) this.entityManager.createNativeQuery(sql, Cliente.class).setParameter("nome", nomeFormt)
				.getSingleResult();
	}

}
