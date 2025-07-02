package com.mycompany.facilita_uni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalogo implements Serializable {
    private List<ContasFixas> contasFixas = new ArrayList<>();
    private List<Eletrodomestico> eletrodomesticos = new ArrayList<>();
    private List<Atividade> atividades = new ArrayList<>();

    public double calcularTotal() {
        double total = 0;
        for (ContasFixas conta : contasFixas) {
            total += conta.getValor();
        }
        for (Eletrodomestico e : eletrodomesticos) {
            double consumoKWh = (e.getWatts() * e.getTempoDeUso()) / 1000.0;
            total += consumoKWh * 0.85;
        }
        return total;
    }

    // Getters
    public List<ContasFixas> getContasFixas() {
        return contasFixas;
    }

    public List<Eletrodomestico> getEletrodomesticos() {
        return eletrodomesticos;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    // Setters
    public void setContasFixas(List<ContasFixas> contasFixas) {
        this.contasFixas = contasFixas;
    }

    public void setEletrodomesticos(List<Eletrodomestico> eletrodomesticos) {
        this.eletrodomesticos = eletrodomesticos;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    // Métodos auxiliares
    public void adicionarContaFixa(ContasFixas conta) {
        contasFixas.add(conta);
    }

    public void adicionarEletrodomestico(Eletrodomestico eletro) {
        eletrodomesticos.add(eletro);
    }

    public void adicionarAtividade(Atividade atividade) {
        atividades.add(atividade);
    }
}
