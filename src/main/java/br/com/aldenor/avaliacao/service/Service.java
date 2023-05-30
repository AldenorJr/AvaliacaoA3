/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aldenor.avaliacao.service;

import br.com.aldenor.avaliacao.database.DatabaseMethod;
import br.com.aldenor.avaliacao.model.Balconista;
import br.com.aldenor.avaliacao.model.Pessoa;
import lombok.SneakyThrows;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author alden
 */
public class Service {
    
    public void clearTable(JTable table) {
        DefaultTableModel model = getDefaultTableModel(table);
        model.setNumRows(0);
    }
    public void addTable(JTable table, Balconista balconista) {
        DefaultTableModel model = getDefaultTableModel(table);
        model.addRow(new Object[]{balconista.getID(), balconista.getNome(), balconista.getCPF(),
                balconista.getIdade(), balconista.getTurno(), balconista.getCaixa()});
    }

    @SneakyThrows
    public void addTable(JTable table, String turno) throws SQLException {
        DatabaseMethod databaseMethod = new DatabaseMethod();
        clearTable(table);
        List<Pessoa> balconistaList = databaseMethod.getAllCadastrosBalconistas();
        for(Pessoa pessoa : balconistaList) {
            if(pessoa instanceof Balconista) {
                Balconista balconista = (Balconista) pessoa;
                if(turno.equalsIgnoreCase("todos") || balconista.getTurno().equalsIgnoreCase(turno)) addTable(table, balconista);
            }
        }
        databaseMethod.closeConnection();
    }

    public void updateTableCaixa(JTable table, String turno) throws SQLException {
        clearTable(table);
        addTable(table, turno);
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
