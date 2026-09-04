package marketcore.main;

import marketcore.carrinho.Carrinho;
import marketcore.carrinho.ItemCarrinho;
import marketcore.cliente.Cliente;
import marketcore.pedido.ItemPedido;
import marketcore.pedido.Pedido;
import marketcore.pedido.StatusPedido;
import marketcore.produto.Produto;
import marketcore.repository.ItemPedidoRepository;
import marketcore.repository.PedidoRepository;
import marketcore.service.ClienteService;
import marketcore.service.PedidoService;
import marketcore.service.ProdutoService;

import java.time.LocalDateTime;
import java.util.List;

public class TesteConexao {
    public static void main(String[] args) {

        // =========================
        // SERVICES E REPOSITORIES
        // =========================

        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();

        PedidoRepository pedidoRepository = new PedidoRepository();
        ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository();

        PedidoService pedidoService =
                new PedidoService(pedidoRepository);


        // =========================
        // IDs ÚNICOS
        // =========================

        long codigo = System.currentTimeMillis();

        Long clienteId = codigo;

        String produtoId =
                "PROD-" + codigo;

        String pedidoId =
                "PED-" + codigo;

        String itemPedidoId =
                "ITEM-" + codigo;

        String pedidoConcluidoId =
                "PED-CONCLUIDO-" + codigo;


        System.out.println(
                "========== MARKET CORE =========="
        );


        // =========================
        // CLIENTE
        // =========================

        Cliente cliente = new Cliente(
                clienteId,
                "Cliente Teste",
                "cliente@teste.com"
        );

        clienteService.cadastrarCliente(cliente);

        System.out.println(
                "\nCLIENTE CADASTRADO"
        );


        Cliente clienteEncontrado =
                clienteService.buscarCliente(clienteId);

        System.out.println(
                "\nCLIENTE ENCONTRADO:"
        );

        System.out.println(
                clienteEncontrado
        );


        // =========================
        // ATUALIZAR CLIENTE
        // =========================

        clienteService.atualizarCliente(
                clienteId,
                "Cliente Atualizado",
                "cliente.atualizado@teste.com"
        );


        Cliente clienteAtualizado =
                clienteService.buscarCliente(clienteId);


        System.out.println(
                "\nCLIENTE ATUALIZADO:"
        );

        System.out.println(
                clienteAtualizado
        );


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


        System.out.println(
                "\nPRODUTO CADASTRADO"
        );


        Produto produtoEncontrado =
                produtoService.buscarProduto(produtoId);


        System.out.println(
                "\nPRODUTO ENCONTRADO:"
        );

        System.out.println(
                produtoEncontrado
        );


        // =========================
        // ATUALIZAR PRODUTO
        // =========================

        produtoService.atualizarProduto(
                produtoId,
                "Notebook Gamer Pro",
                5000.00,
                8
        );


        Produto produtoAtualizado =
                produtoService.buscarProduto(produtoId);


        System.out.println(
                "\nPRODUTO ATUALIZADO:"
        );

        System.out.println(
                produtoAtualizado
        );


        // =========================
        // CARRINHO 1
        // =========================

        Carrinho carrinho =
                new Carrinho();


        ItemCarrinho itemCarrinho =
                new ItemCarrinho(
                        produtoAtualizado,
                        2
                );


        carrinho.adicionarItem(
                itemCarrinho
        );


        System.out.println(
                "\nCARRINHO CRIADO"
        );


        System.out.println(
                "TOTAL: R$ "
                        + carrinho.calcularTotal()
        );


        // =========================
        // PEDIDO 1
        // =========================

        Pedido pedido =
                pedidoService.criarPedido(
                        clienteAtualizado,
                        pedidoId,
                        carrinho
                );


        System.out.println(
                "\nPEDIDO CADASTRADO:"
        );

        System.out.println(
                pedido
        );


        // =========================
        // BUSCAR PEDIDO
        // =========================

        Pedido pedidoEncontrado =
                pedidoService.buscarPedido(
                        pedidoId
                );


        System.out.println(
                "\nPEDIDO ENCONTRADO:"
        );

        System.out.println(
                pedidoEncontrado
        );


        // =========================
        // ITEM PEDIDO
        // =========================

        itemPedidoRepository.cadastrarItemPedido(
                itemPedidoId,
                pedidoId,
                produtoId,
                2,
                produtoAtualizado.getPreco()
        );


        System.out.println(
                "\nITEM PEDIDO CADASTRADO"
        );


        // ==========================================
        // CENÁRIO 1
        // CANCELAMENTO VÁLIDO
        // ==========================================

        System.out.println(
                "\n========== CENÁRIO 1 =========="
        );


        pedidoService.cancelarPedido(
                pedidoId
        );


        Pedido pedidoCancelado =
                pedidoService.buscarPedido(
                        pedidoId
                );


        System.out.println(
                "Status após cancelamento: "
                        + pedidoCancelado.getStatus()
        );


        // ==========================================
        // CENÁRIO 2
        // CANCELAR PEDIDO JÁ CANCELADO
        // ==========================================

        System.out.println(
                "\n========== CENÁRIO 2 =========="
        );


        try {

            pedidoService.cancelarPedido(
                    pedidoId
            );

        } catch (IllegalStateException e) {

            System.out.println(
                    "Erro esperado: "
                            + e.getMessage()
            );
        }


        // ==========================================
        // CENÁRIO 3
        // PEDIDO CONCLUÍDO NÃO PODE SER CANCELADO
        // ==========================================

        System.out.println(
                "\n========== CENÁRIO 3 =========="
        );


        // novo carrinho
        Carrinho carrinho2 =
                new Carrinho();


        ItemCarrinho itemCarrinho2 =
                new ItemCarrinho(
                        produtoAtualizado,
                        1
                );


        carrinho2.adicionarItem(
                itemCarrinho2
        );


        // novo pedido
        Pedido pedidoConcluido =
                pedidoService.criarPedido(
                        clienteAtualizado,
                        pedidoConcluidoId,
                        carrinho2
                );


        // finalizar
        pedidoService.finalizarPedido(
                pedidoConcluido,
                carrinho2
        );


        // buscar novamente no banco
        Pedido pedidoConcluidoBanco =
                pedidoService.buscarPedido(
                        pedidoConcluidoId
                );


        System.out.println(
                "Status após finalização: "
                        + pedidoConcluidoBanco.getStatus()
        );


        // tentar cancelar
        try {

            pedidoService.cancelarPedido(
                    pedidoConcluidoId
            );

        } catch (IllegalStateException e) {

            System.out.println(
                    "Erro esperado: "
                            + e.getMessage()
            );
        }


        // =========================
        // LISTAR PEDIDOS
        // =========================

        System.out.println(
                "\n========== PEDIDOS =========="
        );


        List<Pedido> pedidos =
                pedidoService.listarPedidos();


        for (Pedido pedidoDaLista : pedidos) {

            System.out.println(
                    pedidoDaLista
            );

            System.out.println(
                    "-------------------------"
            );
        }


        // =========================
        // LISTAR PRODUTOS
        // =========================

        System.out.println(
                "\n========== PRODUTOS =========="
        );


        produtoService.listarProdutos();


        // =========================
        // EXCLUSÕES
        // =========================

        System.out.println(
                "\n========== EXCLUSÕES =========="
        );


        /*
         * ORDEM IMPORTANTE:
         *
         * ItemPedido
         * ↓
         * Pedidos
         * ↓
         * Produto
         * ↓
         * Cliente
         *
         * Isso evita erro de Foreign Key.
         */


        // ITEM PEDIDO
        itemPedidoRepository.excluirItemPedido(
                itemPedidoId
        );


        System.out.println(
                "ItemPedido excluído."
        );


        // PEDIDO 1
        pedidoService.excluirPedido(
                pedidoId
        );


        System.out.println(
                "Pedido cancelado excluído."
        );


        // PEDIDO 2
        pedidoService.excluirPedido(
                pedidoConcluidoId
        );


        System.out.println(
                "Pedido concluído excluído."
        );


        // PRODUTO
        produtoService.excluirProduto(
                produtoId
        );


        System.out.println(
                "Produto excluído."
        );


        // CLIENTE
        clienteService.excluirCliente(
                clienteId
        );


        System.out.println(
                "Cliente excluído."
        );


        // =========================
        // FIM
        // =========================

        System.out.println(
                "\n========== TESTE FINALIZADO =========="
        );
    }
}