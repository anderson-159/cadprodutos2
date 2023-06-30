package br.com.f1rst3.cadprodutos.dto.request;

public class ProdutoSalvarRequestDto {
    private String nome;
    private Integer quantidade;

    public String getNome() {
        return nome;
    }

    public ProdutoSalvarRequestDto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoSalvarRequestDto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}
