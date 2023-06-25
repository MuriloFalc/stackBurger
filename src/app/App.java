package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import facade.FastFoodFacade;
import model.cliente.Cliente;
import model.pedido.Item;
import model.pedido.Pedido;
import model.produto.Produto;

public class App {
    private static FastFoodFacade fastFoodFacade = new FastFoodFacade();
    private static Cliente clienteAtual;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = 0;
        do {
            exibirMenuPrincipal();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    iniciarAtendimento();
                    break;
                case 2:
                    if (clienteAtual != null) {
                        exibirMenuPedido();
                    } else {
                        System.out.println("\nNão há cliente em atendimento.");
                        pressionarEnter();
                    }
                    break;
                case 3:
                    System.out.println("\nPrograma Terminado!");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    pressionarEnter();
                    break;
            }
        } while (opcao != 3);
    }
    
    private static void limpaTela() {
		for (int i = 0; i < 25; i++) {
			System.out.println();
		}
	}

    private static void exibirMenuPrincipal() {
    	limpaTela();
        System.out.println("===============================");
        System.out.println("      MENU PRINCIPAL");
        System.out.println("===============================");
        System.out.println("<1> Iniciar Atendimento");
        System.out.println("<2> Fazer Pedido");
        System.out.println("<3> Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuPedido() {
        int opcao = 0;
        do {
        	limpaTela();
            System.out.println("===============================");
            System.out.println("         MENU PEDIDO");
            System.out.println("===============================");
            System.out.println("<1> Adicionar item");
            System.out.println("<2> Remover item");
            //System.out.println("<3> Cancelar item");
            System.out.println("<4> Exibir detalhes do pedido");
            System.out.println("<5> Encerrar pedido");
            System.out.println("<6> Encerrar atendimento");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarItem();
                    break;
                case 2:
                    removerItem();
                    break;
                case 3:
                    cancelarItem();
                    break;
                case 4:
                    exibirDetalhesPedido();
                    break;
                case 5:
                    encerrarPedido();
                    break;
                case 6:
                    encerrarAtendimento();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    pressionarEnter();
                    break;
            }
        } while (opcao != 6);
    }

    private static void pressionarEnter() {
        System.out.println("Pressione Enter para continuar...");
        scanner.nextLine();
    }

    private static void iniciarAtendimento() {
    	limpaTela();
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        clienteAtual = new Cliente(nomeCliente);
        System.out.println("Atendimento iniciado para o cliente: " + clienteAtual.getNome());
        pressionarEnter();
    }

    private static void exibirProdutosDisponiveis() {
    	limpaTela();
        List<Produto> produtosDisponiveis = fastFoodFacade.getProdutosDisponiveis();

        System.out.println("===============================");
        System.out.println("    PRODUTOS DISPONÍVEIS");
        System.out.println("===============================");
        for (int i = 0; i < produtosDisponiveis.size(); i++) {
            Produto produto = produtosDisponiveis.get(i);
            System.out.println("<" + (i + 1) + "> " + produto.getNome() + " - R$ " + produto.getPreco());
        }
        System.out.println("===============================");
    }

    private static void adicionarItem() {
    	limpaTela();
        exibirProdutosDisponiveis();

        System.out.print("Escolha o número do produto desejado: ");
        int numeroProduto = scanner.nextInt();
        scanner.nextLine();

        Produto produtoSelecionado = fastFoodFacade.getProdutoPorNumero(numeroProduto);
        if (produtoSelecionado != null) {
            System.out.print("Digite a quantidade desejada: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            Item item = new Item(produtoSelecionado, quantidade);
            fastFoodFacade.adicionarItemPedido(clienteAtual, item);

            System.out.println("Item adicionado ao pedido.");
        } else {
            System.out.println("Número de produto inválido.");
        }
        pressionarEnter();
    }

   
    private static void removerItem() {
    	limpaTela();
        List<Item> itensPedido = fastFoodFacade.getItensPedido(clienteAtual);

        if (itensPedido.isEmpty()) {
            System.out.println("Não há itens no pedido.");
        } else {
            System.out.println("===============================");
            System.out.println("        ITENS NO PEDIDO");
            System.out.println("===============================");
            for (int i = 0; i < itensPedido.size(); i++) {
                Item item = itensPedido.get(i);
                System.out.println("<" + (i + 1) + "> " + item.getProduto().getNome() + " - " + item.getQuantidade() + " unidades");
            }
            System.out.println("===============================");

            System.out.print("Escolha o número do item a ser removido: ");
            int numeroItem = scanner.nextInt();
            scanner.nextLine();

            if (numeroItem >= 1 && numeroItem <= itensPedido.size()) {
                Item itemRemovido = itensPedido.get(numeroItem - 1);
                fastFoodFacade.removerItemPedido(clienteAtual, itemRemovido);

                System.out.println("Item removido do pedido.");
            } else {
                System.out.println("Número de item inválido.");
            }
        }
        pressionarEnter();
    }

    private static void cancelarItem() {
        // Implementação do cancelarItem()
    }

    private static void exibirDetalhesPedido() {
    	limpaTela();
        List<Item> itensPedido = fastFoodFacade.getItensPedido(clienteAtual);

        if (itensPedido.isEmpty()) {
            System.out.println("Não há itens no pedido.");
        } else {
            double valorTotal = 0.0;

            System.out.println("===============================");
            System.out.println("      DETALHES DO PEDIDO");
            System.out.println("===============================");
            for (int i = 0; i < itensPedido.size(); i++) {
                Item item = itensPedido.get(i);
                double subtotal = item.getProduto().getPreco() * item.getQuantidade();
                valorTotal += subtotal;
                System.out.println(item.getProduto().getNome() + " - " + item.getQuantidade() + " unidades - Subtotal: R$ " + subtotal);
            }
            System.out.println("===============================");
            System.out.println("Valor total do pedido: R$ " + valorTotal);
        }
        pressionarEnter();
    }

    private static void encerrarPedido() {
        Pedido pedido = fastFoodFacade.encerrarPedido(clienteAtual);
        if (pedido != null) {
            System.out.println("Pedido encerrado. Número do pedido: " + pedido.getNumero());
        } else {
            System.out.println("Não há itens no pedido.");
        }
        pressionarEnter();
    }

    private static void encerrarAtendimento() {
        fastFoodFacade.encerrarAtendimento(clienteAtual);
        System.out.println("Atendimento encerrado para o cliente: " + clienteAtual.getNome());
        clienteAtual = null;
        pressionarEnter();
    }
}
