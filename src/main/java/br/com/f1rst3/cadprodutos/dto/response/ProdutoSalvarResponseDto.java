package br.com.f1rst3.cadprodutos.dto.response;

public class ProdutoSalvarResponseDto {

    private Long id;
    private String nome;
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public ProdutoSalvarResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoSalvarResponseDto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoSalvarResponseDto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}
