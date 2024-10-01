package br.ETS.almoxarifado.produto;

public record DadosProduto
        (int id,
        String produto,
        String partNumber,
        String divisao,
        int quantidade){
}
