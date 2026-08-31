package marketcore.main;

import marketcore.cliente.Cliente;
import marketcore.pedido.ItemPedido;
import marketcore.pedido.Pedido;
import marketcore.pedido.StatusPedido;
import marketcore.produto.Produto;
import marketcore.repository.ItemPedidoRepository;
import marketcore.repository.PedidoRepository;
import marketcore.service.ClienteService;
import marketcore.service.ProdutoService;

import java.time.LocalDateTime;
import java.util.List;

public class TesteConexao {

    public static void main(String[] args) {

        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();

        PedidoRepository pedidoRepository = new PedidoRepository();
        ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository();


        // IDs diferentes a cada execução

        long codigo = System.currentTimeMillis();

        Long clienteId = codigo;
        String produtoId = "PROD-" + codigo;
        String pedidoId = "PED-" + codigo;
        String itemId = "ITEM-" + codigo;


        System.out.println("========== MARKET CORE ==========");


        // =========================
        // CLIENTE
        // =========================

        Cliente cliente = new Cliente(
                clienteId,
                "Cliente Teste",
                "cliente@teste.com"
        );

        clienteService.cadastrarCliente(cliente);

        System.out.println("\nCLIENTE CADASTRADO");


        Cliente clienteEncontrado =
                clienteService.buscarCliente(clienteId);

        System.out.println("\nCLIENTE ENCONTRADO:");
        System.out.println(clienteEncontrado);


        clienteService.atualizarCliente(
                clienteId,
                "Cliente Atualizado",
                "cliente.atualizado@teste.com"
        );

        Cliente clienteAtualizado =
                clienteService.buscarCliente(clienteId);

        System.out.println("\nCLIENTE ATUALIZADO:");
        System.out.println(clienteAtualizado);



        // =========================
        // PRODUTO
        // =========================

        Produto produto = new Produto(
                "Notebook Gamer",
                produtoId,
                10,
                4500.00
        );

        produtoService.cadastrarProduto(produto);

        System.out.println("\nPRODUTO CADASTRADO");


        Produto produtoEncontrado =
                produtoService.buscarProduto(produtoId);

        System.out.println("\nPRODUTO ENCONTRADO:");
        System.out.println(produtoEncontrado);


        produtoService.atualizarProduto(
                produtoId,
                "Notebook Gamer Pro",
                5000.00,
                8
        );

        Produto produtoAtualizado =
                produtoService.buscarProduto(produtoId);

        System.out.println("\nPRODUTO ATUALIZADO:");
        System.out.println(produtoAtualizado);



        // =========================
        // PEDIDO
        // =========================

        Pedido pedido = new Pedido(
                clienteAtualizado,
                StatusPedido.PENDENTE,
                pedidoId,
                produtoAtualizado.getPreco(),
                LocalDateTime.now()
        );

        pedidoRepository.cadastrarPedido(pedido);

        System.out.println("\nPEDIDO CADASTRADO");


        Pedido pedidoEncontrado =
                pedidoRepository.buscarPedido(pedidoId);

        System.out.println("\nPEDIDO ENCONTRADO:");
        System.out.println(pedidoEncontrado);



        // =========================
        // ATUALIZAR PEDIDO
        // =========================

        pedidoEncontrado.setStatus(
                StatusPedido.PROCESSANDO
        );

        pedidoRepository.atualizarPedido(
                pedidoEncontrado
        );

        Pedido pedidoAtualizado =
                pedidoRepository.buscarPedido(pedidoId);

        System.out.println("\nPEDIDO ATUALIZADO:");
        System.out.println(pedidoAtualizado);



        // =========================
        // ITEM PEDIDO
        // =========================

        itemPedidoRepository.cadastrarItemPedido(
                itemId,
                pedidoId,
                produtoId,
                2,
                produtoAtualizado.getPreco()
        );

        System.out.println("\nITEM PEDIDO CADASTRADO");



        // =========================
        // BUSCAR ITENS DO PEDIDO
        // =========================

        List<ItemPedido> itens =
                itemPedidoRepository.buscarItensPorPedido(pedidoId);

        System.out.println("\nITENS DO PEDIDO:");

        for (ItemPedido item : itens) {

            System.out.println(
                    "ID: " + item.getId()
            );

            System.out.println(
                    "Pedido: " + item.getPedidoId()
            );

            System.out.println(
                    "Produto: " + item.getProdutoId()
            );

            System.out.println(
                    "Quantidade: " + item.getQuantidade()
            );

            System.out.println(
                    "Preço unitário: R$ " + item.getPrecoUnitario()
            );

            System.out.println("-------------------------");
        }



        // =========================
        // ATUALIZAR ITEM PEDIDO
        // =========================

        ItemPedido itemAtualizado = new ItemPedido(
                itemId,
                pedidoId,
                produtoId,
                3,
                4800.00
        );

        itemPedidoRepository.atualizarItemPedido(
                itemAtualizado
        );

        System.out.println("\nITEM PEDIDO ATUALIZADO");


        List<ItemPedido> itensAtualizados =
                itemPedidoRepository.buscarItensPorPedido(pedidoId);

        for (ItemPedido item : itensAtualizados) {

            System.out.println(
                    "Quantidade atualizada: "
                            + item.getQuantidade()
            );

            System.out.println(
                    "Preço atualizado: R$ "
                            + item.getPrecoUnitario()
            );
        }



        // =========================
        // LISTAR PEDIDOS
        // =========================

        System.out.println("\n========== PEDIDOS ==========");

        List<Pedido> pedidos =
                pedidoRepository.listarTodosPedidos();

        for (Pedido pedidoDaLista : pedidos) {

            System.out.println(pedidoDaLista);

            System.out.println(
                    "-------------------------"
            );
        }


        // =========================
        // LISTAR PRODUTOS
        // =========================

        System.out.println("\n========== PRODUTOS ==========");

        produtoService.listarProdutos();



        // =========================
        // TESTANDO DELETE
        // =========================

        System.out.println("\n========== EXCLUSÕES ==========");


        // primeiro exclui o item por causa das Foreign Keys

        itemPedidoRepository.excluirItemPedido(itemId);

        System.out.println(
                "ItemPedido excluído."
        );


        pedidoRepository.excluirPedido(pedidoId);

        System.out.println(
                "Pedido excluído."
        );


        produtoService.excluirProduto(produtoId);

        System.out.println(
                "Produto excluído."
        );


        clienteService.excluirCliente(clienteId);

        System.out.println(
                "Cliente excluído."
        );


        System.out.println(
                "\n========== TESTE FINALIZADO =========="
        );
    }
}