/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aldenor.avaliacao.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * @author alden
 */
@Getter @Setter
public abstract class Pessoa {

    public Pessoa(String CPF, String nome, String userName, String password, String dateNascimento) {
        this.CPF = CPF;
        this.nome = nome;
        this.userName = userName;
        this.password = password;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.dateNascimento = df.parse(dateNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    private String CPF;
    private String nome;
    private String userName;
    private String password;
    private Date dateNascimento;
    
    
    
    
}
