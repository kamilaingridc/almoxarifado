package br.ETS.almoxarifado.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection recuperarConexao(){
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/almoxarifado?"+
                    "user=root&password=0000");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
