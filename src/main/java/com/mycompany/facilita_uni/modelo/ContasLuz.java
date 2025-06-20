/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.facilita_uni.modelo;
import com.mycompany.facilita_uni.modelo.Contas;
import java.io.Serializable;

/**
 *
 * @author Pedro
 */
public class ContasLuz extends Contas implements Serializable{ //Dará uma estimativa do minimo da conta
    protected int Kwh; //Usuario terá que inserir o kwh para o calculo da conta de luz
    protected int TempodeUso; 

    public ContasLuz(int valor, String nome, String dataVencimento, int Kwh, int TempodeUso){
        super(valor,nome,dataVencimento);
        this.Kwh = Kwh;
    }

    public int Kwh(){
        return Kwh;
    }

    public int TempodeUso(){
        return TempodeUso;
    }

    public int getValor(){
        return valor;
    }

    public String getNome(){
        return nome;
    }

    public String getDataVencimento(){
        return dataVencimento;
    }

    public void setKwh(int Kwh){
        this.Kwh = Kwh;
    }

    public void setNome(String valor){
        this.nome = nome;
    }

    public void setValor(int valor){ //Edição de valor caso necessite
        this.valor = valor;
    }

    private void setdataVencimento(String dataVencimento){ //Edição de data caso necessite
        this.dataVencimento = dataVencimento;
    }

}