package com.mycompany.facilita_uni.controle;

import com.mycompany.facilita_uni.modelo.Eletrodomestico;
import com.mycompany.facilita_uni.modelo.Atividade;
import com.mycompany.facilita_uni.modelo.ContasFixas;
import com.mycompany.facilita_uni.modelo.Catalogo;
import java.io.Serializable;
import java.util.List;

public class Controlador implements Serializable {
    private Catalogo catalogo;

    public Controlador() {
        catalogo = new Catalogo();
    }

    // Delegação para Catalogo
    public double calcularTotal() {
        return catalogo.calcularTotal();
    }

    public void adicionarContaFixa(ContasFixas c) {
        catalogo.getContasFixas().add(c);
    }

    public void adicionarEletrodomestico(Eletrodomestico e) {
        catalogo.getEletrodomesticos().add(e);
    }

    public void adicionarAtividade(Atividade a) {
        catalogo.getAtividades().add(a);
    }

    public List<ContasFixas> getContasFixas() {
        return catalogo.getContasFixas();
    }

    public List<Eletrodomestico> getEletrodomesticos() {
        return catalogo.getEletrodomesticos();
    }

    public List<Atividade> getAtividades() {
        return catalogo.getAtividades();
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }
}