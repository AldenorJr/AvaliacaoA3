/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aldenor.avaliacao.service;

import br.com.aldenor.avaliacao.database.DatabaseMethod;
import br.com.aldenor.avaliacao.model.Administrador;
import br.com.aldenor.avaliacao.model.Balconista;
import br.com.aldenor.avaliacao.model.Pessoa;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lombok.SneakyThrows;

/**
 *
 * @author alden
 */
public class ServiceAdminstrador {
    
    public void clearTable(JTable table) {
        DefaultTableModel model = getDefaultTableModel(table);
        model.setNumRows(0);
    }
    public void addTable(JTable table, Administrador administrador) {
        DefaultTableModel model = getDefaultTableModel(table);
        model.addRow(new Object[]{administrador.getID(), administrador.getNome(), administrador.getCPF(),
                administrador.getIdade()});
    }

    @SneakyThrows
    public void addTable(JTable table) throws SQLException {
        DatabaseMethod databaseMethod = new DatabaseMethod();
        clearTable(table);
        List<Pessoa> balconistaList = databaseMethod.getAllCadastrosAdministrador();
        for(Pessoa pessoa : balconistaList) {
            if(pessoa instanceof Administrador) {
                Administrador administrador = (Administrador) pessoa;
                addTable(table, administrador);
            }
        }
        databaseMethod.closeConnection();
    }

    public void updateTableCaixa(JTable table) throws SQLException {
        clearTable(table);
        addTable(table);
    }

    private DefaultTableModel getDefaultTableModel (JTable table) {
        return (DefaultTableModel) table.getModel();
    }

    public String getDate(String date) throws ParseException {
        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return displayDateFormat.format(sqlDateFormat.parse(date));
    }
    
}
