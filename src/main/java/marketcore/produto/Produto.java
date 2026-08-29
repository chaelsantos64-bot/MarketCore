package marketcore.produto;

import marketcore.exception.EstoqueInsuficienteException;

public class Produto {

    private String nome;
    private String id;
    private Integer quantidade;
    private Double preco;

    public Produto(String nome, String id, Integer quantidade, Double preco) {
        this.nome = nome;
        this.id = id;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public void reduzirEstoque(Integer quantidadeComprada) {

        if (quantidadeComprada <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero. Valor informado: "
                            + quantidadeComprada
            );
        }

        if (quantidadeComprada > quantidade) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para o produto: " + nome
            );
        }

        Integer novaQuantidade = quantidade - quantidadeComprada;
        this.quantidade = novaQuantidade;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto: " + nome
                + "\nID: " + id
                + "\nQuantidade: " + quantidade
                + "\nPreço: R$ " + preco;
    }
}