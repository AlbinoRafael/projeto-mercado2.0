package market.conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAFabricaDeConexao {

private EntityManagerFactory factory;
	
	public JPAFabricaDeConexao() {
		this.factory = Persistence.createEntityManagerFactory("market2");
	}
	public EntityManager getEntityManager() {
		return this.factory.createEntityManager();
	}
	
}
