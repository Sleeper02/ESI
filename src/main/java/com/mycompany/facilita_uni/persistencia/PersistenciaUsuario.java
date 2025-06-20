package com.mycompany.facilita_uni.persistencia;

import com.mycompany.facilita_uni.controle.Controlador;
import java.io.*;

public class PersistenciaUsuario {
    
    public void salvarParaArquivo(Controlador controlador, String nomeArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(controlador);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Controlador carregarDeArquivo(String nomeArquivo) {
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