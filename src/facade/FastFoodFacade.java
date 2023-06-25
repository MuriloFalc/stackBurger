package facade;

import java.util.ArrayList;
import java.util.List;

import model.cliente.Cliente;
import model.pedido.Item;
import model.pedido.Pedido;
import model.produto.Produto;

public class FastFoodFacade {
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private List<Produto> produtos;

    public FastFoodFacade() {
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.produtos = new ArrayList<>();
        inicializarProdutos();
    }

    public void inicializarProdutos() {
        produtos.add(new Produto("Hambúrguer", 10.0));
        produtos.add(new Produto("Batata Frita", 5.0));
        produtos.add(new Produto("Refrigerante", 7.0));
    }

    public List<Produto> getProdutosDisponiveis() {
        return produtos;
    }

    public Produto getProdutoPorNumero(int numeroProduto) {
        if (numeroProduto >= 1 && numeroProduto <= produtos.size()) {
            return produtos.get(numeroProduto - 1);
        }
        return null;
    }

    public void adicionarItemPedido(Cliente cliente, Item item) {
        Pedido pedido = obterPedidoCliente(cliente);
        if (pedido == null) {
            pedido = new Pedido(cliente);
            pedidos.add(pedido);
        }
        pedido.adicionarItem(item);
    }

    public void removerItemPedido(Cliente cliente, Item item) {
        Pedido pedido = obterPedidoCliente(cliente);
        if (pedido != null) {
            pedido.removerItem(item);
            if (pedido.getItens().isEmpty()) {
                pedidos.remove(pedido);
            }
        }
    }

    public List<Item> getItensPedido(Cliente cliente) {
        Pedido pedido = obterPedidoCliente(cliente);
        if (pedido != null) {
            return pedido.getItens();
        }
        return new ArrayList<>();
    }

    public Pedido encerrarPedido(Cliente cliente) {
        Pedido pedido = obterPedidoCliente(cliente);
        if (pedido != null) {
            pedidos.remove(pedido);
            return pedido;
        }
        return null;
    }

    public void encerrarAtendimento(Cliente cliente) {
        clientes.remove(cliente);
    }

    private Pedido obterPedidoCliente(Cliente cliente) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().equals(cliente)) {
                return pedido;
            }
        }
        return null;
    }
}
