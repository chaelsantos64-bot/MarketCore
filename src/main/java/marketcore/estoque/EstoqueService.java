package marketcore.estoque;

import marketcore.entidades.Produto;

public class EstoqueService {
    private Produto produto;

    public EstoqueService(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeDisponivel() {
        return produto.getQuantidade();
    }
}
