package br.ETS.almoxarifado.produto;

import java.util.Objects;

public class Produto {
    private int id;
    private String produto;
    private String partNumber;
    private String divisao;
    private int quantidade;

    public Produto(DadosProduto dadosProduto){
        this.id = dadosProduto.id();
        this.produto = dadosProduto.produto();
        this.partNumber = dadosProduto.partNumber();
        this.divisao = dadosProduto.divisao();
        this.quantidade = dadosProduto.quantidade();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", produto='" + produto + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", divisao='" + divisao + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
