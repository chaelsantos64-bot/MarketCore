package marketcore.main;

import marketcore.cliente.Cliente;
import marketcore.pedido.Pedido;
import marketcore.pedido.StatusPedido;
import marketcore.produto.Produto;
import marketcore.repository.PedidoRepository;
import marketcore.service.ClienteService;
import marketcore.service.ProdutoService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        PedidoRepository pedidoRepository = new PedidoRepository();

        System.out.println("=== MARKET CORE ===");


        // CLIENTE

        Cliente cliente = new Cliente(
                200L,
                "Cliente Teste",
                "cliente@teste.com"
        );

        clienteService.cadastrarCliente(cliente);

        Cliente clienteEncontrado =
                clienteService.buscarCliente(200L);

        System.out.println("\nCLIENTE ENCONTRADO:");
        System.out.println(clienteEncontrado);


        clienteService.atualizarCliente(
                200L,
                "Cliente Atualizado",
                "clienteatualizado@teste.com"
        );

        Cliente clienteAtualizado =
                clienteService.buscarCliente(200L);

        System.out.println("\nCLIENTE ATUALIZADO:");
        System.out.println(clienteAtualizado);



        // PRODUTO

        Produto produto = new Produto(
                "Notebook Gamer",
                "P-200",
                10,
                4500.00
        );

        produtoService.cadastrarProduto(produto);

        Produto produtoEncontrado =
                produtoService.buscarProduto("P-200");

        System.out.println("\nPRODUTO ENCONTRADO:");
        System.out.println(produtoEncontrado);


        produtoService.atualizarProduto(
                "P-200",
                "Notebook Gamer Pro",
                5200.00,
                8
        );

        Produto produtoAtualizado =
                produtoService.buscarProduto("P-200");

        System.out.println("\nPRODUTO ATUALIZADO:");
        System.out.println(produtoAtualizado);



        // PEDIDO

        Pedido pedido = new Pedido(
                clienteAtualizado,
                StatusPedido.PENDENTE,
                "PED-200",
                produtoAtualizado.getPreco(),
                LocalDateTime.now()
        );

        pedidoRepository.cadastrarPedido(pedido);

        Pedido pedidoEncontrado =
                pedidoRepository.buscarPedido("PED-200");

        System.out.println("\nPEDIDO ENCONTRADO:");
        System.out.println(pedidoEncontrado);



        // ATUALIZANDO PEDIDO

        pedidoEncontrado.setStatus(
                StatusPedido.PROCESSANDO
        );

        pedidoRepository.atualizarPedido(
                pedidoEncontrado
        );

        Pedido pedidoAtualizado =
                pedidoRepository.buscarPedido("PED-200");

        System.out.println("\nPEDIDO ATUALIZADO:");
        System.out.println(pedidoAtualizado);



        // LISTANDO PRODUTOS

        System.out.println("\n=== PRODUTOS ===");

        produtoService.listarProdutos();



        // LISTANDO PEDIDOS

        System.out.println("\n=== PEDIDOS ===");

        List<Pedido> pedidos =
                pedidoRepository.listarTodosPedidos();

        for (Pedido pedidoDaLista : pedidos) {
            System.out.println(pedidoDaLista);
            System.out.println("----------------------");
        }


        System.out.println("\n=== FIM DO TESTE ===");
    }
}