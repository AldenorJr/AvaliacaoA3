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
        DatabaseMethod databaseMethod = new DatabaseMethod();
        databaseMethod.createTableFuncionario();
        databaseMethod.createTableAdminstrador();
        if(generationDefaultAdmin) generationDefaultAccountAdmin();
        System.out.println("Tabelas carregadas.");
        System.out.println("Carregando tela incial...");
        LoginPage.startLoginPage();
        databaseMethod.closeConnection();
    }
    
    public static void generationDefaultAccountAdmin() throws SQLException {
        DatabaseMethod databaseMethod = new DatabaseMethod();
        if(!databaseMethod.hasUsernameAdminstradorAccount("admin")) {
            Adminstrador admin = new Adminstrador("Admin", "Admin", "Admin", "Admin", "16/09/2004");
            databaseMethod.setAdminstradores(admin);
        }
        databaseMethod.closeConnection();
    }
}
