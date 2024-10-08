package br.ETS.almoxarifado;

import br.ETS.almoxarifado.produto.DadosProduto;
import br.ETS.almoxarifado.produto.ProdutoService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var opcao = exibirMenu();

        while (opcao!=6) {
            try{
                switch (opcao) {
                    case 1 -> adicionarNovoProduto();
                    case 2 -> exibirProdutosCadastrados();
                    case 3 -> adicionarQuantidadeDeUmProduto();
                    case 4 -> removerQuantidadeDeUmProduto();
                    case 5 -> removerOProdutoDoAlmoxarifado();

                }
            } catch (RegraDaAplicacaoException e){
                System.out.println(e.getMessage());
                System.out.println("Pressione ENTER para voltar ao menu principal");
                scanner.nextLine();
            }
            opcao=exibirMenu();
        }
    }

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

    private static void adicionarNovoProduto(){
        System.out.println("Insira o ID do produto que deseja cadastrar:");
        var id = Integer.parseInt(scanner.nextLine());
        System.out.println("Insira o nome do produto que deseja cadastrar:");
        var produto = scanner.nextLine();
        System.out.println("Insira o PARTNUMBER do produto que deseja cadastrar:");
        var partNumber = scanner.nextLine();
        System.out.println("Insira a divisão do produto que deseja cadastrar:");
        var divisao = scanner.nextLine();
        System.out.println("Insira a quantidade desse produto:");
        var quantidade = Integer.parseInt(scanner.nextLine());

        produtoService.adicionarNovoProduto(new DadosProduto(id, produto, partNumber, divisao, quantidade));
        System.out.printf("O produto %s foi cadastrado com sucesso.\n", produto);
    }
        private static void exibirProdutosCadastrados(){
            System.out.println("Os produtos cadastrados são:");
            var produtos = produtoService.exibirProdutosAlmoxarifado();
            produtos.forEach(System.out::println);
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            scanner.nextLine();
        }

        private static void adicionarQuantidadeDeUmProduto(){
            System.out.println("Digite o ID do produto:");
            var id = Integer.parseInt(scanner.nextLine());
            System.out.println("Digite a quantidade que deseja inserir desse produto:");
            var quantidade = Integer.parseInt(scanner.nextLine());

            produtoService.adicionarQuantidadeDeUmProduto(id, quantidade);
            System.out.printf("A quantidade de %d foi adicionada ao produto com ID %d\n", quantidade, id);
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            scanner.nextLine();
        }

        private static void removerQuantidadeDeUmProduto(){
            System.out.println("Digite o ID do produto:");
            var id = Integer.parseInt(scanner.nextLine());
            System.out.println("Digite a quantidade que deseja remover desse produto:");
            var quantidade = Integer.parseInt(scanner.nextLine());

            produtoService.removerQuantidadeDeUmProduto(id, quantidade);
            System.out.printf("A quantidade de %d foi removida do produto com ID %d\n", quantidade, id);
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            scanner.nextLine();
        }

        private static void removerOProdutoDoAlmoxarifado(){
            System.out.println("Digite o ID do produto:");
            var id = Integer.parseInt(scanner.nextLine());

            produtoService.removerProdutoAlmoxarifado(id);
            System.out.printf("O produto com id %d foi removido com sucesso\n", id);
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            scanner.nextLine();
        }
}