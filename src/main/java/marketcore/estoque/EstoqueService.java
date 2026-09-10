package marketcore.estoque;


import marketcore.exception.CancelamentoPedidoException;
import marketcore.exception.EstoqueInsuficienteException;

public class EstoqueService {
    public boolean verificarDisponibilidade(
            Estoque estoque, int quantidDesejada){
        if (estoque.getQuantidadeDisponivel() >= quantidDesejada) {
            return true;
        }
        return false;
    }

    public void reduzirEstoque(Estoque estoque, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero"
            );
        }
        if (estoque.getQuantidadeDisponivel() < quantidade) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente"
            );
        }

        int novaQuantidade =
                estoque.getQuantidadeDisponivel() - quantidade;

        estoque.getProduto().setQuantidade(novaQuantidade);
    }

    public void adicionarEstoque(Estoque estoque, int quantidade){
        if (quantidade <= 0) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente"
            );
        }

        int novaQuantidade = estoque.getQuantidadeDisponivel() + quantidade;
        estoque.getProduto().setQuantidade(novaQuantidade);

    }
    public void devolverEstoque(Estoque estoque, int quantidade){
        if (quantidade <= 0) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente"
            );
        }
        int qDevolvida = estoque.getQuantidadeDisponivel() + quantidade;
        estoque.getProduto().setQuantidade(qDevolvida);

    }
}
