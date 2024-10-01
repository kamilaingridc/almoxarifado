package br.ETS.almoxarifado.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    public static void main(String[] args) {

        try{
            DriverManager.getConnection("jdbc:mysql://localhost:3306/almoxarifado?"+
            "user=root&password=0000");
            System.out.println("Conexão realizada com sucesso!");

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
