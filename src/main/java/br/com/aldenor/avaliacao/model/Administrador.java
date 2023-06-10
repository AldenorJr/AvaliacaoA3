/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aldenor.avaliacao.model;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author alden
 */
@Getter @Setter
public class Administrador extends Pessoa {

    public Administrador(String CPF, String nome, String userName, String password, String dateNascimento) {
        super(CPF, nome, userName, password, dateNascimento);
    }
    
    private int ID;

}
