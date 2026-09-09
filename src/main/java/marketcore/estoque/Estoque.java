package marketcore.estoque;

import marketcore.entidades.Produto;

public class Estoque {
    private Produto produto;

    public Estoque(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeDisponivel() {
        return produto.getQuantidade();
    }
}
