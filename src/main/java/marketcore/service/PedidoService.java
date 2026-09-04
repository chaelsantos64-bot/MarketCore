package marketcore.service;

import marketcore.carrinho.Carrinho;
import marketcore.carrinho.ItemCarrinho;
import marketcore.cliente.Cliente;
import marketcore.exception.CarrinhoVazioException;
import marketcore.pedido.Pedido;
import marketcore.pedido.StatusPedido;
import marketcore.repository.PedidoRepository;


import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {
    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Cliente cliente, String id, Carrinho carrinho) {
        if (carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException("O CARRINHO ESTA VAZIO");
        }

        double valorTotal = carrinho.calcularTotal();

        LocalDateTime dataAtual = LocalDateTime.now();


        Pedido pedido = new Pedido(cliente,
                StatusPedido.PENDENTE,
                id,
                valorTotal,
                dataAtual
        );

        pedidoRepository.cadastrarPedido(pedido);

        return pedido;
    }


    public List<Pedido> listarPedidos() {
        return pedidoRepository.listarTodosPedidos();
    }

    public Pedido buscarPedido(String id) {
        return pedidoRepository.buscarPedido(id);
    }

    public void excluirPedido(String id) {
        pedidoRepository.excluirPedido(id);
    }

    public void finalizarPedido(Pedido pedido, Carrinho carrinho) {

        carrinho.finalizarCompras();
        pedido.setStatus(StatusPedido.CONCLUIDO);
        pedidoRepository.atualizarPedido(pedido);
        carrinho.limparCarrinho();
    }

    public void cancelarPedido(String id) {

        Pedido pedido = pedidoRepository.buscarPedido(id);

        if (pedido.getStatus() == StatusPedido.CONCLUIDO) {
            throw new IllegalStateException(
                    "Pedido concluido nao pode ser cancelado."
            );
        }

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException(
                    "Pedido ja esta cancelado."
            );
        }

        pedido.setStatus(StatusPedido.CANCELADO);

        pedidoRepository.atualizarPedido(pedido);
    }
}