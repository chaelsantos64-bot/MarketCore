package marketcore.carrinho;

import marketcore.produto.Produto;

public class ItemCarrinho {

    private Produto produto;
    private Integer quantidade;

    public ItemCarrinho(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto: " + produto.getNome()
                + " | Quantidade: " + quantidade
                + " | Preço unitário: R$ " + produto.getPreco();
    }
}