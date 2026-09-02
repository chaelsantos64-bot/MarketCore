package marketcore.main;

import marketcore.carrinho.Carrinho;
import marketcore.carrinho.ItemCarrinho;
import marketcore.cliente.Cliente;
import marketcore.pedido.Pedido;
import marketcore.produto.Produto;
import marketcore.repository.PedidoRepository;
import marketcore.service.PedidoService;
import marketcore.service.ProdutoService;

public class Main {

    public static void main(String[] args) {

        ProdutoService produtoService = new ProdutoService();

        Produto iphone14 = new Produto(
                "IPHONE 14 PRO",
                "P-001",
                10,
                4500.0
        );

        Produto iphone15 = new Produto(
                "IPHONE 15 PRO",
                "P-002",
                10,
                4800.0
        );

        produtoService.cadastrarProduto(iphone14);
        produtoService.cadastrarProduto(iphone15);

        System.out.println("=== PRODUTOS ===");
        produtoService.listarProdutos();

        Carrinho carrinho = new Carrinho();

        ItemCarrinho item1 = new ItemCarrinho(iphone14, 1);
        ItemCarrinho item2 = new ItemCarrinho(iphone15, 2);

        carrinho.adicionarItem(item1);
        carrinho.adicionarItem(item2);

        System.out.println("\n=== CARRINHO ===");
        carrinho.listarItens();

        System.out.println(
                "\nTotal da compra: R$ "
                        + carrinho.calcularTotal()
        );

        PedidoRepository pedidoRepository = new PedidoRepository();

        PedidoService pedidoService = new PedidoService(pedidoRepository);
        Cliente cliente = new Cliente(1L,"micha","micha@gmail.com");

        Pedido pedido = pedidoService.criarPedido(cliente,"PED-001",carrinho);


        System.out.println("\n=== PEDIDO CRIADO ===");
        System.out.println(pedido);

        System.out.println("\n=== ESTOQUE ANTES ===");

        System.out.println(
                iphone14.getNome()
                        + ": "
                        + iphone14.getQuantidade()
        );

        System.out.println(
                iphone15.getNome()
                        + ": "
                        + iphone15.getQuantidade()
        );

        pedidoService.finalizarPedido(
                pedido,
                carrinho
        );

        System.out.println("\n=== PEDIDO FINALIZADO ===");
        System.out.println(pedido);

        System.out.println("\n=== ESTOQUE DEPOIS ===");

        System.out.println(
                iphone14.getNome()
                        + ": "
                        + iphone14.getQuantidade()
        );

        System.out.println(
                iphone15.getNome()
                        + ": "
                        + iphone15.getQuantidade()
        );
    }
}