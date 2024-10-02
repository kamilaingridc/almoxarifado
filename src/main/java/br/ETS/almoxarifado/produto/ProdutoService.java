package br.ETS.almoxarifado.produto;

import br.ETS.almoxarifado.RegraDaAplicacaoException;
import br.ETS.almoxarifado.connection.ConnectionFactory;

import java.sql.Connection;
import java.util.ArrayList;

public class ProdutoService {
    private ArrayList<Produto> produtos = new ArrayList<>();

    private ConnectionFactory connectionFactory;

    public ProdutoService() {
        this.connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.recuperarConexao();
        this.produtos = new ProdutoDAO(connection).listar();
    }

    public void adicionarNovoProduto(DadosProduto dadosProduto){
        var produto = new Produto(dadosProduto);

        if(produtos.contains(produto)){
            throw new RegraDaAplicacaoException("Já existe um produto com esse ID");
        }
        Connection connection = connectionFactory.recuperarConexao();
        new ProdutoDAO(connection).salvar(dadosProduto);
    }

    public ArrayList<Produto> exibirProdutosAlmoxarifado() {
        Connection connection = connectionFactory.recuperarConexao();
        return new ProdutoDAO(connection).listar();
    }

    public Produto encontrarProdutoPeloID(int id){
        Connection connection = connectionFactory.recuperarConexao();
        Produto produto = new ProdutoDAO(connection).listarPorID(id);

        if(produto != null){
            return produto;
        } else {
            throw new RegraDaAplicacaoException("Não existe produto com esse ID");
        }
    }

    public void adicionarQuantidadeDeUmProduto(int id, int quantidadeASerAdicionada){
        var produto = encontrarProdutoPeloID(id);
        if (quantidadeASerAdicionada <= 0){
            throw new RegraDaAplicacaoException("Quantidade a ser adicionada deve ser maior que 0.");
        }
        produto.setQuantidade(produto.getQuantidade() + quantidadeASerAdicionada);

//        Connection connection = connectionFactory.recuperarConexao();
//        new ProdutoDAO(connection).salvar(produto);
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