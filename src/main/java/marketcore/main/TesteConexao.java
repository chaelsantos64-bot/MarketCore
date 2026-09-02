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


        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();

        PedidoRepository pedidoRepository = new PedidoRepository();
        ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository();

        PedidoService pedidoService = new PedidoService(pedidoRepository);


        // =========================
        // IDs ÚNICOS PARA O TESTE
        // =========================

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
        // CARRINHO
        // =========================

        Carrinho carrinho = new Carrinho();

        ItemCarrinho itemCarrinho = new ItemCarrinho(
                produtoAtualizado,
                2
        );

        carrinho.adicionarItem(itemCarrinho);

        System.out.println("\nCARRINHO CRIADO");

        System.out.println(
                "TOTAL DO CARRINHO: R$ "
                        + carrinho.calcularTotal()
        );

        // =========================
        // CRIAR PEDIDO PELO SERVICE
        // =========================

        Pedido pedido = pedidoService.criarPedido(
                clienteAtualizado,
                pedidoId,
                carrinho
        );

        System.out.println("\nPEDIDO CADASTRADO PELO SERVICE:");
        System.out.println(pedido);


        // =========================
        // BUSCAR PEDIDO PELO SERVICE
        // =========================

        Pedido pedidoEncontrado =
                pedidoService.buscarPedido(pedidoId);

        System.out.println("\nPEDIDO ENCONTRADO:");
        System.out.println(pedidoEncontrado);


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
                    "Preço unitário: R$ "
                            + item.getPrecoUnitario()
            );

            System.out.println("-------------------------");
        }


        // =========================
        // LISTAR PEDIDOS PELO SERVICE
        // =========================

        System.out.println(
                "\n========== PEDIDOS =========="
        );

        List<Pedido> pedidos =
                pedidoService.listarPedidos();

        for (Pedido pedidoDaLista : pedidos) {

            System.out.println(pedidoDaLista);

            System.out.println(
                    "-------------------------"
            );
        }


        // =========================
        // FINALIZAR PEDIDO
        // =========================

        pedidoService.finalizarPedido(
                pedido,
                carrinho
        );

        System.out.println("\nPEDIDO FINALIZADO");


        // BUSCA NOVAMENTE NO BANCO
        Pedido pedidoFinalizado =
                pedidoService.buscarPedido(pedidoId);

        System.out.println(
                "\nPEDIDO APÓS FINALIZAÇÃO:"
        );

        System.out.println(pedidoFinalizado);


        // =========================
        // TESTANDO DELETE
        // =========================

        System.out.println(
                "\n========== EXCLUSÕES =========="
        );


        // primeiro ItemPedido por causa da FK

        itemPedidoRepository.excluirItemPedido(itemId);

        System.out.println(
                "ItemPedido excluído."
        );


        // agora usando PedidoService

        pedidoService.excluirPedido(pedidoId);

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