package com.mycompany.facilita_uni.controle;

import com.mycompany.facilita_uni.modelo.Eletrodomestico;
import com.mycompany.facilita_uni.modelo.Atividade;
import com.mycompany.facilita_uni.modelo.ContasFixas;
import com.mycompany.facilita_uni.modelo.Usuario;
import java.io.Serializable;
import java.util.List;

public class Controlador implements Serializable {
    private Usuario usuario;

    public Controlador() {
        usuario = new Usuario();
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

    public Usuario getUsuario() {
        return usuario;
    }
}