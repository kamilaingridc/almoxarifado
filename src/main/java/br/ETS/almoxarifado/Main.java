package br.ETS.almoxarifado;

import br.ETS.almoxarifado.produto.ProdutoService;

import java.util.Scanner;

public class Main {

    private static ProdutoService produtoService = new ProdutoService();
    private static Scanner scanner = new Scanner(System.in);

    private static int exibirMenu(){
        System.out.println("Almoxarifado ETS");
        System.out.println("""
                Selecione uma opção:
                1) Inserir novo produto
                2) Listar produtos
                3) Adicionar quantidade de produtos
                4) Remover quantidade de produtos
                5) Remover produto
                6) Encerrar aplicação
                """);
        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {

        var opcao = exibirMenu();
    }

}