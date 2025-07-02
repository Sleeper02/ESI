package com.mycompany.facilita_uni.modelo;

import java.io.Serializable;

public class ContasLuz extends Contas implements Serializable {
    protected double Kwh; // MUDADO para double para aceitar valores como 3.00
    protected int TempodeUso;

    // Construtor CORRIGIDO
    public ContasLuz(double valor, String nome, String dataVencimento, double Kwh, int TempodeUso) {
        super(valor, nome, dataVencimento);
        this.Kwh = Kwh;
        this.TempodeUso = TempodeUso; // CORRIGIDO: Atribuição que faltava
    }
    
    // Adicionado um construtor vazio para facilitar a criação via setters
    public ContasLuz() {
        super(0, "", ""); // Inicia com valores padrão
    }

    // --- Getters ---
    public double Kwh() { // MUDADO para retornar double
        return Kwh;
    }

    public int TempodeUso() {
        return TempodeUso;
    }

    @Override
    public double getValor() {
        return valor;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDataVencimento() {
        return dataVencimento;
    }

    // --- Setters CORRIGIDOS ---

    public void setKwh(double Kwh) { // MUDADO para aceitar double
        this.Kwh = Kwh;
    }
    
    // ADICIONADO: Método setter para o tempo de uso
    public void setTempodeUso(int TempodeUso) {
        this.TempodeUso = TempodeUso;
    }

    public void setNome(String nome) { // Parâmetro nomeado corretamente
        this.nome = nome; // CORRIGIDO
    }

    public void setValor(double valor) { 
        this.valor = valor;
    }

    public void setDataVencimento(String dataVencimento) { // CORRIGIDO: Mudado para public
        this.dataVencimento = dataVencimento;
    }
}