/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.facilita_uni.modelo;

import java.io.Serializable;

/**
 *
 * @author Pedro
 */public class Contas implements Serializable {
    protected double valor;
    protected String dataVencimento;
    protected String nome;

    public Contas(double valor, String dataVencimento, String nome){
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.nome = nome;
    }

    private void setdataVencimento(String dataVencimento){
        this.dataVencimento = dataVencimento;
    }

    private void setnome (String nome){
        this.nome = nome;
    }

    private void setValor(int valor){
        this.valor = valor;
    }

    protected String getDataVencimento(){
        return dataVencimento;
    }

    protected String getNome(){
        return nome;
    }

    protected double getValor(){
        return valor;
    }

}