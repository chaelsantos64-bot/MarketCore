package marketcore.service;

import marketcore.carrinho.Carrinho;
import marketcore.carrinho.ItemCarrinho;
import marketcore.cliente.Cliente;
import marketcore.exception.CarrinhoVazioException;
import marketcore.exception.PedidoNaoEncontradoException;
import marketcore.pedido.Pedido;
import marketcore.pedido.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private List<Pedido> pedidos = new ArrayList<>();

    public Pedido criarPedido(Cliente cliente ,String id, Carrinho carrinho) {
        if (carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException("O CARRINHO ESTA VAZIO");
        }
        List<ItemCarrinho> itens = carrinho.getItens();

        double valorTotal = carrinho.calcularTotal();

        LocalDateTime dataAtual = LocalDateTime.now();


        Pedido pedido = new Pedido(cliente,
                StatusPedido.PENDENTE,
                id,
                itens,
                valorTotal,
                dataAtual
        );

        pedidos.add(pedido);

        return pedido;
    }


    public void listarPedidos() {
        if(pedidos.isEmpty()){
            System.out.println("Nenhum pedido encontrado");
        }else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }

    public Pedido buscarPedido(String id) {
        for (Pedido pedido : pedidos) {

            if (pedido.getId().equals(id)) {
                return pedido;
            }
        }

        throw new PedidoNaoEncontradoException(
                "ID do pedido não encontrado. ID informado: " + id);
    }

    public void cancelarPedido(String id) {
        Pedido pedido = buscarPedido(id);
        pedido.setStatus(StatusPedido.CANCELADO);
    }

    public void finalizarPedido(Pedido pedido, Carrinho carrinho) {

        carrinho.finalizarCompras();
        pedido.setStatus(StatusPedido.CONCLUIDO);
        carrinho.limparCarrinho();
    }
}