package br.ETS.almoxarifado.produto;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection){
        this.connection = connection;
    }

    public void salvar(DadosProduto dadosProduto){
        String sql = "INSERT INTO tbmateriaisdiretos(ID, PRODUTO, PARTNUMBER, DIVISAO, QUANTIDADE) VALUES(?,?,?,?,?)";

        var produto = new Produto(dadosProduto);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,  dadosProduto.id());
            preparedStatement.setString(2,  dadosProduto.produto());
            preparedStatement.setString(3,  dadosProduto.partNumber());
            preparedStatement.setString(4,  dadosProduto.divisao());
            preparedStatement.setInt(5,  dadosProduto.quantidade());
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Produto> listar(){
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM tbmateriaisdiretos";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String nomeProduto = resultSet.getString(2);
                String partNumber = resultSet.getString(3);
                String divisao = resultSet.getString(4);
                int quantidade = resultSet.getInt(5);

                DadosProduto dadosProduto = new DadosProduto(id, nomeProduto, partNumber, divisao, quantidade);
                Produto produto = new Produto(dadosProduto);
                produtos.add(produto);
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public Produto listarPorID(int id) {
        String sql = "SELECT * FROM tbmateriaisdiretos WHERE id = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Produto produto = null;

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int numeroID = resultSet.getInt(1);
                String nomeProduto = resultSet.getNString(2);
                String partNumber = resultSet.getString(3);
                String divisao = resultSet.getString(4);
                int quantidade = resultSet.getInt(5);

                DadosProduto dadosProduto = new DadosProduto(numeroID, nomeProduto, partNumber, divisao, quantidade);
                produto = new Produto(dadosProduto);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return produto;
    }

    public void alterar(int id, int quantidade){
        
    }
}
