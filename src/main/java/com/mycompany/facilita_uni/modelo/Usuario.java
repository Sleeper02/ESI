package com.mycompany.facilita_uni.modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Usuario implements Serializable {
    private List<ContasFixas> contasFixas = new ArrayList<>();
    private List<Eletrodomestico> eletrodomesticos = new ArrayList<>();
    private List<Atividade> atividades = new ArrayList<>();
    private List<ContasLuz> contasLuz = new ArrayList<>(); // ADICIONADO
    
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
    public List<ContasLuz> getContasLuz() { // ADICIONADO
        return contasLuz;
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
    public void setContasLuz(List<ContasLuz> contasLuz) { // ADICIONADO
        this.contasLuz = contasLuz;
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
    public void adicionarContaLuz(ContasLuz contaLuz) { // ADICIONADO
        contasLuz.add(contaLuz);
    }
}