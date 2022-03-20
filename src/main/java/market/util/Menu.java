package market.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.aplicacao.Programa;
import market.conexao.JPAFabricaDeConexao;
import market.models.dao.ClienteDAO;
import market.models.dao.ProdutoClienteDAO;
import market.models.dao.ProdutoDAO;
import market.models.persistence.Categoria;
import market.models.persistence.Cliente;
import market.models.persistence.Produto;
import market.models.persistence.ProdutoCliente;
import market.services.CategoriaService;
import market.services.ClienteService;
import market.services.ProdutoClienteService;
import market.services.ProdutoService;

public class Menu {
	private static final Logger LOG = LogManager.getLogger(Menu.class);	
	private static EntityManager entityManager = new JPAFabricaDeConexao().getEntityManager();
	public static void menuPrincipal() throws SQLException {
		boolean continua = true;
		do {
			System.out.println("\n\n");
			System.out.println("====================================================");
			System.out.println("=                   Menu Principal                 =");
			System.out.println("====================================================");
			System.out.println("=                                                  =");
			System.out.println("=                    1-Clientes                    =");
			System.out.println("=                    2-Produtos                    =");
			System.out.println("=                    3-Vendas                      =");
			System.out.println("=                                                  =");
			System.out.println("=                    0-Sair                        =");
			System.out.println("====================================================");
			System.out.print("Selecione uma opção: ");

			Scanner sc = new Scanner(System.in);
			int opcao = sc.nextInt();
			switch (opcao) {
			case 1:
				menuClientes();
				;
				break;
			case 2:
				menuProdutos();
				break;
			case 3:
				menuCategoria();
				break;
			case 4:
				menuProdutoClientes();
				break;
			case 0:
				continua = false;
				break;
			default:
				continua = true;
			}
		} while (continua);
	}

	public static void menuClientes() throws SQLException {
		boolean continua = true;
		do {
			System.out.println("\n");
			System.out.println("====================================================");
			System.out.println("=                  Menu de Clientes                =");
			System.out.println("====================================================");
			System.out.println("=                                                  =");
			System.out.println("=                 1-Criar novo cliente             =");
			System.out.println("=                 2-Listar clientes                =");
			System.out.println("=                 3-Deletar um cliente             =");
			System.out.println("=                                                  =");
			System.out.println("=                 0-Sair                           =");
			System.out.println("====================================================");
			System.out.print("Selecione uma opção: ");

			Scanner sc = new Scanner(System.in);
			int opcao = sc.nextInt();
			System.out.println();
			switch (opcao) {
			case 1:
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				ClienteService cliService = new ClienteService(entityManager);
				sc.nextLine();
				System.out.print("Digite o nome do cliente: ");
				String nome = sc.nextLine();
				System.out.print("Digite o cpf do cliente: ");
				String cpf = sc.nextLine();
				System.out.print("Digite a data de nascimento: ");
				String dataNasc = sc.nextLine();
				LocalDate dtNsc = LocalDate.parse(dataNasc,dtf);
				Cliente cliente = new Cliente(nome, cpf,dtNsc);
				cliService.create(cliente);
				break;
			case 2:
				cliService = new ClienteService(entityManager);
				cliService.listAll();
				break;
			case 3:
				cliService = new ClienteService(entityManager);
				System.out.println("Selecione a seguir o ID do cliente: \n");
				cliService.listAll();
				System.out.print("\nDigite o ID do cliente a ser removido: ");
				try {
					long idCliente = sc.nextLong();
					cliService.delete(idCliente);
				} catch (InputMismatchException e) {
					System.out.println("\nInsira apenas numeros!");
				} catch (NullPointerException e) {
					System.out.println("\nProduto não encontrado no banco. Produto não deletado.");
				}
				break;
			case 0:
				continua = false;
				break;
			default:
				continua = true;
			}
		} while (continua);

	}

	public static void menuProdutos() throws SQLException {
		boolean continua = true;
		do {
			System.out.println("\n\n");
			System.out.println("====================================================");
			System.out.println("=                  Menu de Produtos                =");
			System.out.println("====================================================");
			System.out.println("=                                                  =");
			System.out.println("=                1-Criar novo produto              =");
			System.out.println("=                2-Listar produtos                 =");
			System.out.println("=                3-Deletar um produto              =");
			System.out.println("=                                                  =");
			System.out.println("=                0-Sair                            =");
			System.out.println("====================================================");
			System.out.print("Selecione uma opção: ");

			Scanner sc = new Scanner(System.in);
			int opcao = sc.nextInt();
			switch (opcao) {
			case 1:
				sc.nextLine();
				System.out.print("Digite o nome do produto: ");
				String nome = sc.nextLine();
				System.out.print("Digite a descrição do produto: ");
				String descricao = sc.nextLine();
				System.out.print("Digite o preço: ");
				BigDecimal preco = sc.nextBigDecimal();
				sc.nextLine();
				System.out.print("Qual a categoria: ");
				String categoria = sc.nextLine();
				ProdutoService prodservice = new ProdutoService(entityManager);
				Produto product = new Produto(nome, descricao, preco, new Categoria(categoria));
				prodservice.create(product);
				break;
			case 2:
				prodservice = new ProdutoService(entityManager);
				prodservice.listAll();
				break;
			case 3:
				prodservice = new ProdutoService(entityManager);
				System.out.println("Selecione a seguir o ID do produto: \n");
				prodservice.listAll();
				System.out.print("\nDigite o ID do produto a ser removido: ");
				try {
					int idProduto = sc.nextInt();
					prodservice.delete((long) idProduto);
				} catch (InputMismatchException e) {
					System.out.println("\nInsira apenas numeros!");
				} catch (NullPointerException e) {
					System.out.println("\nProduto não encontrado no banco. Produto não deletado.");
				}
				break;
			case 0:
				continua = false;
				break;
			default:
				continua = true;
			}
		} while (continua);
	}

	public static void menuCategoria() throws SQLException {
		boolean continua = true;
		do {
			System.out.println("\n\n");
			System.out.println("====================================================");
			System.out.println("=                  Menu de Categoria               =");
			System.out.println("====================================================");
			System.out.println("=                                                  =");
			System.out.println("=                1-Criar nova categoria            =");
			System.out.println("=                2-Listar categoria                =");
			System.out.println("=                3-Deletar uma categoria           =");
			System.out.println("=                                                  =");
			System.out.println("=                0-Sair                            =");
			System.out.println("====================================================");
			System.out.print("Selecione uma opção: ");

			Scanner sc = new Scanner(System.in);
			int opcao = sc.nextInt();
			switch (opcao) {
			case 1:
				sc.nextLine();
				CategoriaService catService = new CategoriaService(entityManager);
				System.out.println("Digite o nome para a categoria: ");
				String nome = sc.nextLine();
				Categoria categoria = new Categoria(nome);
				catService.create(categoria);
				break;
			case 2:
				catService = new CategoriaService(entityManager);
				catService.listAll();
				break;
			case 3:
				catService = new CategoriaService(entityManager);
				System.out.println("Selecione a seguir o ID da categoria: \n");
				catService.listAll();
				System.out.print("\nDigite o ID da categoria a ser removida: ");
				try {
					int idVenda = sc.nextInt();
					catService.delete((long) idVenda);
				} catch (InputMismatchException e) {
					System.out.println("\nInsira apenas numeros!");
				} catch (NullPointerException e) {
					System.out.println("\nVenda não encontrada no banco. Venda não deletada.");
				}
				break;
			case 0:
				continua = false;
				break;
			default:
				continua = true;
			}
		} while (continua);
	}

	public static void menuProdutoClientes() throws SQLException {
		Locale.setDefault(Locale.US);
		boolean continua = true;
		do {
			System.out.println("\n\n");
			System.out.println("===========================================================");
			System.out.println("=                  Menu de ProdutoCLiente                 =");
			System.out.println("===========================================================");
			System.out.println("=                                                         =");
			System.out.println("=                1-Criar novo item de ProdutoCLiente      =");
			System.out.println("=                2-Listar itens de um ProdutoCLiente      =");
			System.out.println("=                3-Deletar itens um ProdutoCLiente        =");
			System.out.println("=                                                         =");
			System.out.println("=                0-Sair                                   =");
			System.out.println("===========================================================");
			System.out.print("Selecione uma opção: ");

			Scanner sc = new Scanner(System.in);
			int opcao = sc.nextInt();
			switch (opcao) {
			case 1:
				sc.nextLine();
				ProdutoClienteService prodcliservice = new ProdutoClienteService(entityManager);
				ProdutoService prodService = new ProdutoService(entityManager);
				ClienteService cliService = new ClienteService(entityManager);
				prodService.listAll();
				System.out.print("\nDigite o id do produto: ");
				int idProd = sc.nextInt();
				Produto prod = prodService.produtoPorId((long) idProd);
				System.out.print("\nDigite o id do cliente: ");
				int idCli = sc.nextInt();
				Cliente cli = cliService.clientePorId((long) idCli);
				ProdutoCliente itemVenda = new ProdutoCliente(prod,cli);
				prodcliservice.create(itemVenda);
				break;
			case 2:
				sc.nextLine();
				prodcliservice = new ProdutoClienteService(entityManager);
				prodcliservice.listAll();
				break;
			case 3:
				sc.nextLine();
				prodcliservice = new ProdutoClienteService(entityManager);
				System.out.println("Selecione o ID do produtocliente: \n");
				prodcliservice.listAll();
				System.out.print("\nDigite o id da produtocliente: ");
				try {
				int prodcli = sc.nextInt();			
					prodcliservice.delete((long) prodcli);
				} catch (InputMismatchException e) {
					System.out.println("\nInsira apenas numeros!");
				} catch (NullPointerException e) {
					System.out.println("\n item Venda não encontrado no banco.item Venda não deletado.");
				}
				break;
			case 0:
				continua = false;
				break;
			default:
				continua = true;
			}
		} while (continua);
	}
}
