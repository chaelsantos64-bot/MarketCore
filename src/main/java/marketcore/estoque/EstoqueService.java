package marketcore.estoque;


public class EstoqueService {
    public Boolean verificarDisponibilidade(
            Estoque estoque, int quantidDesejada){
        if (estoque.getQuantidadeDisponivel() >= quantidDesejada) {
            return true;
        }
        return false;
    }

    public void reduzirEstoque(Estoque estoque, int quantidade){
        int novaQuantidade =
                estoque.getQuantidadeDisponivel() - quantidade;

        if (novaQuantidade >= quantidade) {

        }
    }
}
