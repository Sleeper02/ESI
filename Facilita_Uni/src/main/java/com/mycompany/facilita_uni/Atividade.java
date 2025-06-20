package com.mycompany.facilita_uni;

import java.io.Serializable;

public class Atividade implements Serializable {
    private String nome;
    private String data; // formato livre: "dd/mm", por exemplo

    public Atividade(String nome, String data) {
        this.nome = nome;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return "Atividade: " + nome + ", Data: " + data;
    }
}
