/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 *
 * @author Pedro
 */
public class Controlador implements Serializable {
    private List<ContasFixas> contasFixas;
    private List<Eletrodomestico> eletrodomesticos;
    private List<Atividade> atividades = new ArrayList<>();

    public Controlador() {
        contasFixas = new ArrayList<>();
        eletrodomesticos = new ArrayList<>();
    }

    public void adicionarAtividade(Atividade a) {
        atividades.add(a);
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    
    public void adicionarContaFixa(ContasFixas conta) {
        contasFixas.add(conta);
    }

    public void adicionarEletrodomestico(Eletrodomestico eletro) {
        eletrodomesticos.add(eletro);
    }

    public double calcularTotal() {
        double total = 0;

        // Soma das contas fixas
        for (ContasFixas conta : contasFixas) {
            total += conta.getValor();
        }

        // Simulação da conta de luz
        for (Eletrodomestico e : eletrodomesticos) {
            // Consumo em kWh = (watts * horas) / 1000
            double consumoKWh = (e.getWatts() * e.getTempoDeUso()) / 1000.0;
            double custoKWh = 0.85; // Exemplo d
            total += consumoKWh * custoKWh;
        }

        return total;
    }

    public List<ContasFixas> getContasFixas() {
        return contasFixas;
    }

    public List<Eletrodomestico> getEletrodomesticos() {
        return eletrodomesticos;
    }
    
public void salvarParaArquivo(String nomeArquivo) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
        System.out.println("Salvando: " + contasFixas.size() + " contas fixas, " + eletrodomesticos.size() + " eletros, " + atividades.size() + " atividades.");
        out.writeObject(this);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static Controlador carregarDeArquivo(String nomeArquivo) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
        Controlador c = (Controlador) in.readObject();
        System.out.println("Carregado: " + c.getContasFixas().size() + " contas fixas, " + c.getEletrodomesticos().size() + " eletros, " + c.getAtividades().size() + " atividades.");
        return c;
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Arquivo não encontrado ou erro ao carregar. Criando novo controlador.");
        return new Controlador();
    }
}


}