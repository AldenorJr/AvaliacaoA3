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
import java.util.Calendar;
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
    
    public int getIdade() {
        
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(getDateNascimento());
        Calendar dateNow = Calendar.getInstance();
        
        int diferencaMes = dateNow.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);
        int diferencaDia = dateNow.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);
        int idade = (dateNow.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));
        
        if(diferencaMes < 0 || (diferencaMes == 0 && diferencaDia < 0)) {
            idade--;
        }
        return idade;
    
        
    }
    
    public boolean isValid() {
        String cpf = getCPF();

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }
        if (Character.getNumericValue(cpf.charAt(9)) != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }
        return Character.getNumericValue(cpf.charAt(10)) == secondDigit;
    }
    
    private String CPF;
    private String nome;
    private String userName;
    private String password;
    private Date dateNascimento;
    
    
    
    
}
