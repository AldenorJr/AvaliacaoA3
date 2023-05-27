/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aldenor.avaliacao.database;

import br.com.aldenor.avaliacao.Main;
import br.com.aldenor.avaliacao.model.Adminstrador;
import br.com.aldenor.avaliacao.model.Balconista;
import br.com.aldenor.avaliacao.model.Pessoa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author alden
 */
@RequiredArgsConstructor
public class DatabaseMethod {

    private final Connection connection;

    public void createTableFuncionario() throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "
                + "`Funcionarios`(`ID` SERIAL PRIMARY KEY,`Caixa` VARCHAR(20), `Turno` VARCHAR(20) NOT NULL, `CPF` VARCHAR(11) NOT NULL, "
                + "`Nome` VARCHAR(40) NOT NULL, `userName` VARCHAR(20) NOT NULL, `Password` VARCHAR(20) NOT NULL, `DataNascimento` DATE NOT NULL)")) {
            stm.executeUpdate();
            connection.close();
        }
    }
    
    public void createTableAdminstrador() throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "
                + "`Adminstrador`(`ID` SERIAL PRIMARY KEY, `CPF` VARCHAR(11) NOT NULL, "
                + "`Nome` VARCHAR(40) NOT NULL, `userName` VARCHAR(20) NOT NULL, `Password` VARCHAR(20) NOT NULL, `DataNascimento` DATE NOT NULL)")) {
            stm.executeUpdate();
            connection.close();
        }
    }
    
    public void updateAccountInformation(int ID, String name, String caixas, String turno) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement("UPDATE `Funcionarios` SET `Nome` = ?, `Caixa` = ?, `Turno` = ? WHERE `ID` = ?")){
            stm.setString(1, name);
            stm.setString(2, caixas);
            stm.setString(3, turno);
            stm.setInt(4, ID);
            stm.executeUpdate();
        } finally {
            connection.close();
        }
    }
    
    public void updateAccountInformation(Balconista balconista) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement("UPDATE `Funcionarios` SET `Nome` = ?, `Caixa` = ?,`Password` = ?, `userName` = ?, `Turno` = ? WHERE `CPF` = ?")){
            stm.setString(1, balconista.getNome());
            stm.setString(2, balconista.getCaixa());
            stm.setString(3, balconista.getPassword());
            stm.setString(4, balconista.getUserName());
            stm.setString(5, balconista.getTurno());
            stm.setString(6, balconista.getCPF());
            stm.executeUpdate();
        } finally {
            connection.close();
        }
    }
    
    public void updateCaixaInformation(String caixa, int ID) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement("UPDATE `Funcionarios` SET `Caixa` = ? WHERE `ID` = ?")){
            stm.setString(1, caixa);
            stm.setInt(2, ID);
            stm.executeUpdate();
        } finally {
            connection.close();
        }
    }
    
    public void clearCaixas() throws SQLException {
        try(Connection connection = Main.hikariConnect.getConnection(); 
                PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Funcionarios`")) {
            try(ResultSet rs = stm.executeQuery()) {
                while(rs.next()) {
                    new DatabaseMethod(Main.hikariConnect.getConnection()).updateCaixaInformation("Vago", rs.getInt("ID"));
                }
            } finally {
                connection.close();
            }
        }
    }
    
    public void clearCaixas(String turno) throws SQLException {
        try(Connection connection = Main.hikariConnect.getConnection(); 
                PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Funcionarios`")) {
            try(ResultSet rs = stm.executeQuery()) {
                while(rs.next()) {
                    if(rs.getString("Turno").equalsIgnoreCase(turno) || turno.equalsIgnoreCase("todos")) {
                        new DatabaseMethod(Main.hikariConnect.getConnection()).updateCaixaInformation("Vago", rs.getInt("ID"));
                    }
                }
            } finally {
                connection.close();
            }
        }
    }
    
    public void setFuncionario(Balconista balconista) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("INSERT INTO `Funcionarios` (`Caixa`, `Turno`, `CPF`, `Nome`, `userName`, `Password`, `DataNascimento`)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)")) {
            stm.setString(1, balconista.getCaixa());
            stm.setString(2, balconista.getTurno());
            stm.setString(3, balconista.getCPF());
            stm.setString(4, balconista.getNome());
            stm.setString(5, balconista.getUserName());
            stm.setString(6, balconista.getPassword());
            stm.setDate(7, new Date(balconista.getDateNascimento().getTime()));
            stm.executeUpdate();
            connection.close();
        }
    }
    
    public void setAdminstradores(Adminstrador adminstrador) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("INSERT INTO `Adminstrador` (`CPF`, `Nome`, `userName`, `Password`, `DataNascimento`)"
                + " VALUES(?, ?, ?, ?, ?)")) {
            stm.setString(1, adminstrador.getCPF());
            stm.setString(2, adminstrador.getNome());
            stm.setString(3, adminstrador.getUserName());
            stm.setString(4, adminstrador.getPassword());
            stm.setDate(5, new Date(adminstrador.getDateNascimento().getTime()));
            stm.executeUpdate();
            connection.close();
        }
    }
    
    public boolean hasFuncionarioAccount(String username, String password) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Funcionarios` WHERE `userName` = ? AND `Password` = ?;")) {
            stm.setString(1, username);
            stm.setString(2, password);
            try(ResultSet rs = stm.executeQuery()) {
                return rs.next();
            } finally {
                connection.close();
            }
        }
    }
    
    public Adminstrador getAdminstradorAccount(String username, String password) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Adminstrador` WHERE `userName` = ? AND `Password` = ?;")) {
            stm.setString(1, username);
            stm.setString(2, password);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try(ResultSet rs = stm.executeQuery()) {
                rs.next();
                return new Adminstrador(rs.getString("CPF"), 
                        rs.getString("Nome"), 
                        rs.getString("userName"), 
                        rs.getString("Password"), 
                        df.format(rs.getDate("DataNascimento")));
            } finally {
                connection.close();
            }
        }
    }
    
    public void deleteBalconistaByID(int ID) throws SQLException {
         try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Funcionarios` WHERE `ID` = ?;")) {
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
            connection.close();
        }
    }
    
    public Balconista getBalconistaAccount(String username, String password) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Funcionarios` WHERE `userName` = ? AND `Password` = ?;")) {
            stm.setString(1, username);
            stm.setString(2, password);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try(ResultSet rs = stm.executeQuery()) {
                rs.next();
                return new Balconista(rs.getString("Caixa"), 
                        rs.getString("Turno"), 
                        rs.getString("CPF"), 
                        rs.getString("Nome"), 
                        rs.getString("userName"), 
                        rs.getString("Password"), 
                        df.format(rs.getDate("DataNascimento")));
            } finally {
                connection.close();
            }
        }
    }
    
    public boolean hasAdminstradoresAccount(String username, String password) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Adminstrador` WHERE `userName` = ? AND `Password` = ?;")) {
            stm.setString(1, username);
            stm.setString(2, password);
            try(ResultSet rs = stm.executeQuery()) {
                return rs.next();
            } finally {
                connection.close();
            }
        }
    }
    
    public boolean hasUsernameAdminstradorAccount(String username) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM `Adminstrador` WHERE `userName` = ?;")) {
            stm.setString(1, username);
            try(ResultSet rs = stm.executeQuery()) {
                return rs.next();
            } finally {
                connection.close();
            }
        }
    }
    
}
