package br.ETS.almoxarifado;

public class RegraDaAplicacaoException extends RuntimeException{

    // construtor
    public RegraDaAplicacaoException(String erro){
        super(erro); //chama o construtor da super classe
    }
}