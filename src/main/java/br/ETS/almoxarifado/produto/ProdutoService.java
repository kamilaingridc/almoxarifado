package br.ETS.almoxarifado.produto;

import br.ETS.almoxarifado.RegraDaAplicacaoException;

import java.util.ArrayList;

public class ProdutoService {
    private ArrayList<Produto> produtos = new ArrayList<>();

    public void adicionarNovoProduto(DadosProduto dadosProduto){
        var produto = new Produto(dadosProduto);

        if(produtos.contains(produto)){
            throw new RegraDaAplicacaoException("Já existe um produto com esse ID");
        }
        produtos.add(produto);
    }

    public ArrayList<Produto> exibirProdutosAlmoxarifado(){
        return produtos;
    }

//    public Produto encontrarProdutoPeloID(int id){
//        for (Produto produto : produtos){
//            if(produto.getId() == id){
//                return produto;
//            }
//        }
//        throw new RegraDaAplicacaoException("Produto com este ID não foi encontrado.");
//    }

    public Produto encontrarProdutoPeloID(int id){
        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RegraDaAplicacaoException("Produto com este ID não foi encontrado."));
    }

    public void adicionarQuantidadeDeUmProduto(int id, int quantidadeASerAdicionada){
        var produto = encontrarProdutoPeloID(id);
        if (quantidadeASerAdicionada <= 0){
            throw new RegraDaAplicacaoException("Quantidade a ser adicionada deve ser maior que 0.");
        }

        produto.setQuantidade(produto.getQuantidade() + quantidadeASerAdicionada);
    }

    public void removerQuantidadeDeUmProduto(int id, int quantidadeASerRemovida){
        var produto = encontrarProdutoPeloID(id);
        if (quantidadeASerRemovida <= 0){
            throw new RegraDaAplicacaoException("Quantidade a ser removida deve ser maior que 0.");
        }
        if (quantidadeASerRemovida > produto.getQuantidade()) {
            throw new RegraDaAplicacaoException("Quantidade a ser removida deve ser igual ou menor o número do estoque.");
        }

        produto.setQuantidade(produto.getQuantidade() - quantidadeASerRemovida);
    }

    public void removerProdutoAlmoxarifado(int id){
        var produto = encontrarProdutoPeloID(id);
        produtos.remove(produto);
    }
}