package market.aplicacao;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.util.Menu;

public class Programa {

	private static final Logger LOG = LogManager.getLogger(Programa.class);
	public static void main(String[] args) {
		
		try {
			Menu.menuPrincipal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
