package com.mycompany.facilita_uni.modelo;
import java.io.Serializable;

public class Eletrodomestico implements Serializable {
    protected String nome;
    protected int watts;
    protected int tempoDeUso; // Tempo em horas

    public Eletrodomestico(String nome, int watts, int tempoDeUso) {
        this.nome = nome;
        this.watts = watts;
        this.tempoDeUso = tempoDeUso;
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
    }

    public void setTempoDeUso(int tempoDeUso) {
        this.tempoDeUso = tempoDeUso;
    }
}
