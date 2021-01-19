package com.example.clubeseis.sql;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static String ip = ""; //ipv4 do pc
    private static String port = ""; //porta usada pelo sql server
    private static String username = "";
    private static String password = "";
    //classe que mantem a biblioteca pra conecção da lib que foi declarada no gradle
    private static String database = ""; //nome da database pra confecção do url, e conecção do banco de dados
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    private static Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public void conectarBanco(Context context) {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password); //no lugar de user e password poderia ter os atributos estaticos que tinha no começo
            //DriverManager.getConnection([url], [username], [password])
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "ClassNotFound", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
