package marketcore.pedido;

import marketcore.carrinho.ItemCarrinho;
import marketcore.cliente.Cliente;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private StatusPedido status;
    private String id;
    private double valorTotal;
    private LocalDateTime data;


    public Pedido(Cliente cliente, StatusPedido status, String id, double valorTotal, LocalDateTime data) {
        this.cliente = cliente;
        this.status = status;
        this.id = id;
        this.valorTotal = valorTotal;
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cliente: "+ cliente + "ID: " + id
                + "\nValor total: R$ " + valorTotal
                + "\nData: " + data
                + "\nStatus: " + status;
    }
}