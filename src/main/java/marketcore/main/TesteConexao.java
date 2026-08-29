package marketcore.main;

import marketcore.cliente.Cliente;
import marketcore.produto.Produto;
import marketcore.repository.ProdutoRepository;
import marketcore.service.ClienteService;
import marketcore.service.ProdutoService;


public class TesteConexao {

    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();
        // 1. CRIAR NOVO CLIENTE
        Cliente cliente = new Cliente(
                1L,
                "CLIENTE1",
                "cliente1@com"
        );

        // 2. CADASTRAR NO BANCO
        clienteService.cadastrarCliente(cliente);

        System.out.println("=== CLIENTE CADASTRADO ===");
        
        ProdutoService produtoService = new ProdutoService();

        // 1. CRIAR NOVO PRODUTO
        Produto produto = new Produto(
                "MONITOR GAMER",
                "P-020",
                8,
                1800.0
        );

        // 2. CADASTRAR NO BANCO
        produtoService.cadastrarProduto(produto);

        System.out.println("=== PRODUTO CADASTRADO ===");

        Produto produtoEncontrado =
                produtoService.buscarProduto("P-020");

        System.out.println(produtoEncontrado);


        // 3. ATUALIZAR PRODUTO
        produtoService.atualizarProduto(
                "P-020",
                "MONITOR GAMER 27 POLEGADAS",
                2100.0,
                12
        );

        System.out.println("\n=== PRODUTO ATUALIZADO ===");

        Produto produtoAtualizado =
                produtoService.buscarProduto("P-020");

        System.out.println(produtoAtualizado);


        // 4. LISTAR TODOS
        System.out.println("\n=== TODOS OS PRODUTOS ===");

        produtoService.listarProdutos();


        // 5. EXCLUIR
        produtoService.excluirProduto("P-020");

        System.out.println("\nProduto P-020 excluído com sucesso.");
    }

}