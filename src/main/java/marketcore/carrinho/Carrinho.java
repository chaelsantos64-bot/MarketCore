package marketcore.carrinho;

import marketcore.produto.Produto;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();

    public void adicionarItem(ItemCarrinho item) {
        itens.add(item);
    }

    public void listarItens() {

        if (!itens.isEmpty()) {

            for (ItemCarrinho item : itens) {
                System.out.println(item);
            }

        } else {
            System.out.println("Nenhum item encontrado.");
        }
    }

    public double calcularTotal() {

        double total = 0;

        for (ItemCarrinho item : itens) {

            Produto produto = item.getProduto();

            double valor = produto.getPreco() * item.getQuantidade();

            total += valor;
        }

        return total;
    }

    public void finalizarCompras() {

        for (ItemCarrinho item : itens) {

            Produto produto = item.getProduto();

            Integer quantidade = item.getQuantidade();

            produto.reduzirEstoque(quantidade);
        }
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void limparCarrinho(){
        itens.clear();
    }
}