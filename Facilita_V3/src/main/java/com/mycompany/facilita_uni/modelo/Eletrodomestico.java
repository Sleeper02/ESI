package com.mycompany.facilita_uni.modelo;
import java.io.Serializable;

public class Eletrodomestico implements Serializable {
    protected String nome;
    protected int watts;
    protected int tempoDeUso; // Tempo em horas
    protected int consumo; // Removido a inicialização aqui
    
    public Eletrodomestico(String nome, int watts, int tempoDeUso) {
        this.nome = nome;
        this.watts = watts;
        this.tempoDeUso = tempoDeUso;
        this.consumo = calcularConsumo(); // Calcular após definir os valores
    }
    
    // Método para calcular o consumo
    private int calcularConsumo() {
        return tempoDeUso * watts/1000;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getWatts() {
        return watts;
    }
    
    public int getTempoDeUso() {
        return tempoDeUso;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setWatts(int watts) {
        this.watts = watts;
        this.consumo = calcularConsumo(); // Recalcular quando watts mudar
    }
    
    public void setTempoDeUso(int tempoDeUso) {
        this.tempoDeUso = tempoDeUso;
        this.consumo = calcularConsumo(); // Recalcular quando tempo mudar
    }
    
    public int getConsumo() {
        return consumo;
    }
}