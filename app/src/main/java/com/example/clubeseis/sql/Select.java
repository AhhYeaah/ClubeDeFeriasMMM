package com.example.clubeseis.sql;

import android.content.Context;
import android.os.StrictMode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Select {

    Context context;
    Data data = new Data();
    private String name_check;
    private double valorAloc;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName_check() {
        return name_check;
    }

    public void setName_check(String name_check) {
        this.name_check = name_check;
    }

    public double getValorAloc() {
        return valorAloc;
    }

    public boolean logar(String email, String password) {

        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)

        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC LOGIN_CONTA @email = ?, @password = ?;";
                statement = conexao.getConnection().prepareStatement("EXEC LOGIN_CONTA @email = ? , @password = ?;"); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    data.setUserID(resultSet.getInt(1));
                }
                resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    data.setUserName(resultSet.getString(2));
                }
                resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    data.setUserCarteira(resultSet.getInt(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            data.setUserEmail(email);
            data.setUserPassword(password);
        }
        //Sem conexão, se tentar vai crashar

        return data.getUserID() != 0;
    }


    public int checarCartCpf(int num_cart, String cpf) {

        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)
        int booleanDaDeepWeb = 3;
        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC UPDATE_ASSOC_CHECK @NUM_CART = ?, @CPF = ?";
                statement = conexao.getConnection().prepareStatement(sql); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setInt(1, num_cart);
                statement.setString(2, cpf);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) { //não sei
                    booleanDaDeepWeb = resultSet.getInt(1);
                }
                if (booleanDaDeepWeb != 0) {
                    resultSet = statement.executeQuery();
                    while (resultSet.next()) { //não sei
                        this.setName_check(resultSet.getString(2));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Sem conexão, se tentar vai crashar
        return booleanDaDeepWeb;
    }

    public boolean updateCartCpf(String cpf, String email, String password) {
        int a = 3;
        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)

        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC UPDATE_ASSOC_FINAL @CPF = ?, @EMAIL = ?, @SENHA = ?;";
                statement = conexao.getConnection().prepareStatement(sql); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setString(1, cpf);
                statement.setString(2, email);
                statement.setString(3, password);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    a = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Sem conexão, se tentar vai crashar
        return (a == 1);
    }


    // Alocacoes do associao

    public boolean canAssocAloc(int num_cart) {

        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)

        int bol = 2;
        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC CAN_ASSOC_ALOC @NUM_CART = ?";
                statement = conexao.getConnection().prepareStatement(sql); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setInt(1, num_cart);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    bol = (resultSet.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        //Sem conexão, se tentar vai crashar

        return (bol == 1);
    }

    public void doAssocAloc(int id_valor, int num_cart, Date date) {

        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)

        int placeholder = 2;
        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC DO_ASSOC_ALOC @ID_VALOR = ?, @NUM_CART = ?, @DATE = ?; ";
                statement = conexao.getConnection().prepareStatement(sql); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setInt(1, id_valor);
                statement.setInt(2, num_cart);
                statement.setDate(3, (java.sql.Date) date);
                statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean whatsAssocAloc(String email, String password, Context context) throws ParseException { //Ve qual alocação o associado tem

        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)
        int idExists = 0;
        Date placeholder = null;
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC WHATS_ASSOC_ALOC @EMAIL = ?, @PASSWORD = ?";
                statement = conexao.getConnection().prepareStatement(sql); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    data.setUserName(resultSet.getString(1));
                }
                resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    placeholder = resultSet.getDate(2);
                }
                resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    idExists = resultSet.getInt(3);
                }
                resultSet = statement.executeQuery();
                while (resultSet.next()) { //não sei
                    this.valorAloc = resultSet.getDouble(4);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Sem conexão, se tentar vai crashar

        if (idExists != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(placeholder);
            data.setAlocDate(calendar);
        }

        return idExists != 0;
    }

    public void deletarAloc(String email, String password) {

        Conexao conexao = new Conexao();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)
        if (conexao.getConnection() != null) {
            try {
                PreparedStatement statement;
                String sql = "EXEC DELETE_ALOC @EMAIL = ?, @PASSWORD = ?";
                statement = conexao.getConnection().prepareStatement(sql); //o statement (ação no banco) usa o metodo pra ser criado
                statement.setString(1, email);
                statement.setString(2, password);
                statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
