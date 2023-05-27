/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.aldenor.avaliacao;

import br.com.aldenor.avaliacao.database.DatabaseMethod;
import br.com.aldenor.avaliacao.database.HikariConnect;
import br.com.aldenor.avaliacao.model.Adminstrador;
import br.com.aldenor.avaliacao.model.Pessoa;
import br.com.aldenor.avaliacao.telas.LoginPage;
import java.sql.SQLException;
import lombok.SneakyThrows;

/**
 *
 * @author alden
 */
public class Main {

    public static final HikariConnect hikariConnect = new HikariConnect();
    public static Pessoa pessoaAccount = null;
    private static boolean generationDefaultAdmin = true;
    
    public static void main(String[] args) throws SQLException {
        System.out.println("Iniciando banco de dados.");
        hikariConnect.MySQLConnectLoad("127.0.0.1", "test", "root", "");
        new DatabaseMethod(hikariConnect.getConnection()).createTableFuncionario();
        new DatabaseMethod(hikariConnect.getConnection()).createTableAdminstrador();
        if(generationDefaultAdmin) generationDefaultAccountAdmin();
        System.out.println("Tabelas carregadas.");
        System.out.println("Carregando tela incial...");
        LoginPage.startLoginPage();
    }
    
    public static void generationDefaultAccountAdmin() throws SQLException {
        if(!new DatabaseMethod(hikariConnect.getConnection()).hasUsernameAdminstradorAccount("admin")) {
            Adminstrador admin = new Adminstrador("Admin", "Admin", "Admin", "Admin", "16/09/2004");
            new DatabaseMethod(hikariConnect.getConnection()).setAdminstradores(admin);
        }
    }
}