package br.ETS.almoxarifado.produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {
    // Atributo que mantém a conexão com o banco de dados.
    private Connection connection;

    // Construtor que recebe uma conexão e inicializa o DAO com essa conexão.
    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método responsável por salvar um novo produto no banco de dados.
    public void salvar(DadosProduto dadosProduto) {
        // SQL para inserir um novo registro na tabela 'tbmateriaisdiretos'.
        String sql = "INSERT INTO tbmateriaisdiretos(ID, PRODUTO, PARTNUMBER, DIVISAO, QUANTIDADE) VALUES(?,?,?,?,?)";

        // Cria um objeto Produto a partir dos dados fornecidos.
        var produto = new Produto(dadosProduto);

        try {
            // Prepara o comando SQL para execução, substituindo os parâmetros pelos valores correspondentes.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dadosProduto.id());  // Atribui o ID.
            preparedStatement.setString(2, dadosProduto.produto());  // Atribui o nome do produto.
            preparedStatement.setString(3, dadosProduto.partNumber());  // Atribui o part number.
            preparedStatement.setString(4, dadosProduto.divisao());  // Atribui a divisão.
            preparedStatement.setInt(5, dadosProduto.quantidade());  // Atribui a quantidade.

            // Executa o comando SQL para inserir o novo produto.
            preparedStatement.execute();

            // Fecha o PreparedStatement e a conexão.
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            // Lança uma exceção em caso de falha na execução SQL.
            throw new RuntimeException(e);
        }
    }

    // Método responsável por listar todos os produtos do banco de dados.
    public ArrayList<Produto> listar() {
        // Lista que será preenchida com os produtos recuperados do banco de dados.
        ArrayList<Produto> produtos = new ArrayList<>();
        // SQL para selecionar todos os registros da tabela 'tbmateriaisdiretos'.
        String sql = "SELECT * FROM tbmateriaisdiretos";

        try {
            // Prepara o comando SQL para execução.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Executa a consulta e armazena o resultado em um ResultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            // Percorre o resultado da consulta e cria objetos Produto para cada registro encontrado.
            while (resultSet.next()) {
                int id = resultSet.getInt(1);  // Recupera o ID do produto.
                String nomeProduto = resultSet.getString(2);  // Recupera o nome do produto.
                String partNumber = resultSet.getString(3);  // Recupera o part number do produto.
                String divisao = resultSet.getString(4);  // Recupera a divisão.
                int quantidade = resultSet.getInt(5);  // Recupera a quantidade em estoque.

                // Cria um objeto DadosProduto com os dados recuperados.
                DadosProduto dadosProduto = new DadosProduto(id, nomeProduto, partNumber, divisao, quantidade);
                // Cria um objeto Produto e o adiciona à lista de produtos.
                Produto produto = new Produto(dadosProduto);
                produtos.add(produto);
            }

            // Fecha o PreparedStatement, o ResultSet e a conexão.
            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            // Lança uma exceção em caso de falha na execução SQL.
            throw new RuntimeException(e);
        }
        // Retorna a lista de produtos.
        return produtos;
    }

    // Método responsável por buscar um produto no banco de dados pelo ID.
    public Produto listarPorID(int id) {
        // SQL para selecionar um produto específico da tabela 'tbmateriaisdiretos' pelo ID.
        String sql = "SELECT * FROM tbmateriaisdiretos WHERE id = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Produto produto = null;

        try {
            // Prepara o comando SQL para execução, substituindo o parâmetro pelo valor do ID.
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            // Executa a consulta e armazena o resultado em um ResultSet.
            resultSet = preparedStatement.executeQuery();

            // Se o produto for encontrado, cria um objeto Produto com os dados recuperados.
            while (resultSet.next()) {
                int numeroID = resultSet.getInt(1);  // Recupera o ID.
                String nomeProduto = resultSet.getNString(2);  // Recupera o nome do produto.
                String partNumber = resultSet.getString(3);  // Recupera o part number.
                String divisao = resultSet.getString(4);  // Recupera a divisão.
                int quantidade = resultSet.getInt(5);  // Recupera a quantidade.

                // Cria um objeto DadosProduto e Produto com os dados recuperados.
                DadosProduto dadosProduto = new DadosProduto(numeroID, nomeProduto, partNumber, divisao, quantidade);
                produto = new Produto(dadosProduto);
            }
            // Fecha o ResultSet, o PreparedStatement e a conexão.
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            // Lança uma exceção em caso de falha na execução SQL.
            throw new RuntimeException(e);
        }

        // Retorna o produto encontrado ou null, caso não exista.
        return produto;
    }

    // Método responsável por alterar a quantidade de um produto no banco de dados.
    public void alterar(int id, int quantidade) {
        // SQL para atualizar a quantidade de um produto na tabela 'tbmateriaisdiretos' baseado no ID.
        String sql = "UPDATE tbmateriaisdiretos SET quantidade = ? WHERE id = ?";
        PreparedStatement preparedStatement;

        try {
            // Prepara o comando SQL para execução, substituindo os parâmetros pelos valores de quantidade e ID.
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quantidade);  // Define a nova quantidade.
            preparedStatement.setInt(2, id);  // Define o ID do produto a ser alterado.

            // Executa o comando SQL para alterar a quantidade do produto.
            preparedStatement.execute();

            // Fecha o PreparedStatement e a conexão.
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            // Lança uma exceção em caso de falha na execução SQL.
            throw new RuntimeException(e);
        }
    }

    public void deletar(int id){
        String sql = "DELETE FROM tbmateriaisdiretos WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}