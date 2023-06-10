/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aldenor.avaliacao.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author alden
 */
@Getter @Setter
public class Balconista extends Pessoa{

    public Balconista(String caixa, String turno, String CPF, String nome, String userName, String password, String dateNascimento) {
        super(CPF, nome, userName, password, dateNascimento);
        this.caixa = caixa;
        this.turno = turno;
    }
    private int ID;
    private String caixa;
    private String turno;
    
}
