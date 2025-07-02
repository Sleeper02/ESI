/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.facilita_uni.modelo;
import java.io.Serializable;

/**
 *
 * @author Pedro
 */
public class ContasFixas extends Contas implements Serializable{

    public ContasFixas(int valor, String nome, String dataVencimento){
        super(valor,nome,dataVencimento);
    }

    public double getValor(){
        return valor;
    }

    public String getNome(){
        return nome;
    }

    public String getDataVencimento(){
        return dataVencimento;
    }

    private void setNome(String valor){
        this.nome = nome;
    }

    private void setValor(int valor){ //Edição de valor caso necessite
        this.valor = valor;
    }

    private void setdataVencimento(String dataVencimento){ //Edição de data caso necessite
        this.dataVencimento = dataVencimento;
    }

}