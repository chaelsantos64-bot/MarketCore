package marketcore.service;

import marketcore.produto.Produto;
import marketcore.repository.ProdutoRepository;

import java.util.List;


public class ProdutoService {

   private ProdutoRepository produtoRepository =  new ProdutoRepository();

    public void cadastrarProduto(Produto produto) {
        produtoRepository.cadastrarProduto(produto);
    }

    public Produto buscarProduto(String id){
        return produtoRepository.buscarProduto(id);
    }
    public void excluirProduto(String id) {
        produtoRepository.excluirProduto(id);
    }

    public void atualizarProduto(
            String id,
            String novoNome,
            double novoPreco,
            Integer novaQuantidade
    ) {

        Produto produtoEncontrado = buscarProduto(id);

        produtoEncontrado.setNome(novoNome);
        produtoEncontrado.setPreco(novoPreco);
        produtoEncontrado.setQuantidade(novaQuantidade);

        produtoRepository.atualizarProduto(produtoEncontrado);
    }

    public void listarProdutos(){
        List<Produto> produtos = produtoRepository.listarTodosProdutos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado");
            } else {
                for (Produto produto : produtos) {
                    System.out.println(produto);
                }
            }
    }
}