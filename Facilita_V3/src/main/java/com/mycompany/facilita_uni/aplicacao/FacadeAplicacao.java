package com.mycompany.facilita_uni.aplicacao;
import com.mycompany.facilita_uni.controle.Controlador;
import com.mycompany.facilita_uni.persistencia.PersistenciaCatalogo;
import com.mycompany.facilita_uni.modelo.ContasFixas;  // ← ADICIONE ESTA LINHA

public class FacadeAplicacao {
    private Controlador controlador;
    private PersistenciaCatalogo persistencia;
    
    public FacadeAplicacao() {
        this.controlador = new Controlador();
        this.persistencia = new PersistenciaCatalogo();
    }
    
    // Delega para o controlador
    public double calcularTotal() { return controlador.calcularTotal(); }
    public void adicionarContaFixa(ContasFixas c) { controlador.adicionarContaFixa(c); }
    
    // Gerencia persistência
    public void salvarDados(String arquivo) { persistencia.salvarParaArquivo(controlador, arquivo); }
    public void carregarDados(String arquivo) { controlador = persistencia.carregarDeArquivo(arquivo); }
}