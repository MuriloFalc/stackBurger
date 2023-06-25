package model.pedido;

import java.util.ArrayList;
import java.util.List;

import model.cliente.Cliente;

public class Pedido {
    private static int numeroPedidoAtual = 1;

    private int numero;
    private Cliente cliente;
    private List<Item> itens;

    public Pedido(Cliente cliente) {
        this.numero = numeroPedidoAtual;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        numeroPedidoAtual++;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public void removerItem(Item item) {
        itens.remove(item);
    }
}
