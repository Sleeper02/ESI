package com.mycompany.facilita_uni;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;

public class Controlador implements Serializable {
    private Catalogo usuario;

    public Controlador() {
        usuario = new Catalogo();
    }

    // Delegação para Usuario
    public double calcularTotal() {
        return usuario.calcularTotal();
    }

    public void adicionarContaFixa(ContasFixas c) {
        usuario.getContasFixas().add(c);
    }

    public void adicionarEletrodomestico(Eletrodomestico e) {
        usuario.getEletrodomesticos().add(e);
    }

    public void adicionarAtividade(Atividade a) {
        usuario.getAtividades().add(a);
    }

    public List<ContasFixas> getContasFixas() {
        return usuario.getContasFixas();
    }

    public List<Eletrodomestico> getEletrodomesticos() {
        return usuario.getEletrodomesticos();
    }

    public List<Atividade> getAtividades() {
        return usuario.getAtividades();
    }

    public Catalogo getUsuario() {
        return usuario;
    }

    // Serialização
    public void salvarParaArquivo(String nomeArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Controlador carregarDeArquivo(String nomeArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Controlador c = (Controlador) in.readObject();
            System.out.println("Dados carregados com sucesso.");
            return c;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo não encontrado ou erro ao carregar. Criando novo controlador.");
            return new Controlador();
        }
    }
}
