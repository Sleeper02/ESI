/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.facilita_uni.interfac;

// Imports necessários para integração com o sistema
import com.mycompany.facilita_uni.controle.Controlador;
import com.mycompany.facilita_uni.modelo.Eletrodomestico;
import com.mycompany.facilita_uni.modelo.Usuario;
import com.mycompany.facilita_uni.persistencia.PersistenciaUsuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Pedro
 */
public class ViewEletrodomesticos extends javax.swing.JFrame {
    private JScrollPane scrollPane;
    private JPanel eletrodomesticosPanel;
    private JLabel totalEletrodomesticosLabel;
    private JLabel totalConsumoLabel;
    /**
     * Creates new form ViewEletrodomesticos
     */
     public ViewEletrodomesticos() {
        initComponents();
        configurarJanela();
        carregarEletrodomesticos();
    }
    
     
     private void configurarJanela() {
        setTitle("Visualizar Eletrodomésticos");
        setLocationRelativeTo(null); // Centralizar na tela
        setResizable(true);
        
        // Configurar layout principal
        setLayout(new BorderLayout());
        
        // Título
        JLabel titulo = new JLabel("ELETRODOMÉSTICOS CADASTRADOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);
        
        // Painel para os eletrodomésticos com scroll
        eletrodomesticosPanel = new JPanel();
        eletrodomesticosPanel.setLayout(new BoxLayout(eletrodomesticosPanel, BoxLayout.Y_AXIS));
        eletrodomesticosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(eletrodomesticosPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior com total e botões
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        // Painel do total
        JPanel painelTotal = new JPanel(new GridLayout(2, 1, 5, 5));
        painelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        totalEletrodomesticosLabel = new JLabel("Total de Eletrodomésticos: 0", SwingConstants.CENTER);
        totalEletrodomesticosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalEletrodomesticosLabel.setForeground(new Color(0, 100, 200)); // Azul
        
        totalConsumoLabel = new JLabel("Consumo Total: 0 kWh", SwingConstants.CENTER);
        totalConsumoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalConsumoLabel.setForeground(new Color(200, 100, 0)); // Laranja
        
        painelTotal.add(totalEletrodomesticosLabel);
        painelTotal.add(totalConsumoLabel);
        painelInferior.add(painelTotal, BorderLayout.NORTH);
        
        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnFechar = new JButton("Fechar");
        
        btnAtualizar.addActionListener(e -> carregarEletrodomesticos());
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        painelInferior.add(painelBotoes, BorderLayout.CENTER);
        
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void carregarEletrodomesticos() {
        try {
            // Limpar painel anterior
            eletrodomesticosPanel.removeAll();
            
            // Carregar dados do arquivo
            String caminhoArquivo = System.getProperty("user.dir") + "/dados.ser";
            PersistenciaUsuario persistencia = new PersistenciaUsuario();
            Controlador controlador = persistencia.carregarDeArquivo(caminhoArquivo);
            Usuario usuario = controlador.getUsuario();
            
            List<Eletrodomestico> eletrodomesticos = usuario.getEletrodomesticos();
            
            if (eletrodomesticos.isEmpty()) {
                // Mostrar mensagem quando não há eletrodomésticos
                JLabel mensagemVazia = new JLabel("Nenhum eletrodoméstico cadastrado.", SwingConstants.CENTER);
                mensagemVazia.setFont(new Font("Arial", Font.ITALIC, 14));
                mensagemVazia.setForeground(Color.GRAY);
                eletrodomesticosPanel.add(mensagemVazia);
                
                // Resetar totais
                totalEletrodomesticosLabel.setText("Total de Eletrodomésticos: 0");
                totalConsumoLabel.setText("Consumo Total: 0 kWh");
            } else {
                // Adicionar cada eletrodoméstico como um painel
                double consumoTotal = 0;
                for (int i = 0; i < eletrodomesticos.size(); i++) {
                    Eletrodomestico eletrodomestico = eletrodomesticos.get(i);
                    JPanel eletrodomesticoPanel = criarPainelEletrodomestico(eletrodomestico, i);
                    eletrodomesticosPanel.add(eletrodomesticoPanel);
                    eletrodomesticosPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento
                    
                    // Calcular e somar consumo total (watts * tempo de uso / 1000 para kWh)
                    double consumoKwh = (eletrodomestico.getWatts() * eletrodomestico.getTempoDeUso()) / 1000.0;
                    consumoTotal += consumoKwh;
                }
                
                // Atualizar totais
                totalEletrodomesticosLabel.setText("Total de Eletrodomésticos: " + eletrodomesticos.size());
                totalConsumoLabel.setText("Consumo Total: " + String.format("%.2f", consumoTotal) + " kWh");
            }
            
            // Refresh da interface
            eletrodomesticosPanel.revalidate();
            eletrodomesticosPanel.repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar os eletrodomésticos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private JPanel criarPainelEletrodomestico(Eletrodomestico eletrodomestico, int indice) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        painel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        // Número do eletrodoméstico (para exibição)
        JLabel labelNumero = new JLabel("#" + (indice + 1));
        labelNumero.setFont(new Font("Arial", Font.BOLD, 16));
        labelNumero.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridheight = 3;
        painel.add(labelNumero, gbc);
        
        // Nome do eletrodoméstico
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.insets = new Insets(2, 20, 2, 5);
        painel.add(labelNome, gbc);
        
        JLabel valorNome = new JLabel(eletrodomestico.getNome());
        valorNome.setFont(new Font("Arial", Font.PLAIN, 14));
        valorNome.setForeground(new Color(50, 50, 50));
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.insets = new Insets(2, 5, 2, 5);
        painel.add(valorNome, gbc);
        
        // Potência do eletrodoméstico
        JLabel labelPotencia = new JLabel("Potência:");
        labelPotencia.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(2, 20, 2, 5);
        painel.add(labelPotencia, gbc);
        
        JLabel valorPotencia = new JLabel(eletrodomestico.getWatts() + " W");
        valorPotencia.setFont(new Font("Arial", Font.PLAIN, 14));
        valorPotencia.setForeground(new Color(200, 100, 0)); // Laranja
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(2, 5, 2, 5);
        painel.add(valorPotencia, gbc);
        
        // Tempo de uso do eletrodoméstico
        JLabel labelTempo = new JLabel("Tempo de Uso:");
        labelTempo.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(2, 20, 2, 5);
        painel.add(labelTempo, gbc);
        
        JLabel valorTempo = new JLabel(eletrodomestico.getTempoDeUso() + " horas");
        valorTempo.setFont(new Font("Arial", Font.PLAIN, 14));
        valorTempo.setForeground(new Color(0, 100, 200)); // Azul
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.insets = new Insets(2, 5, 2, 5);
        painel.add(valorTempo, gbc);
        
        // Ícone decorativo
        JLabel icone = new JLabel("⚡");
        icone.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 3; gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.insets = new Insets(5, 20, 5, 10);
        painel.add(icone, gbc);
        
        // Botão de excluir
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 10));
        btnExcluir.setBackground(new Color(220, 53, 69)); // Vermelho
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorder(BorderFactory.createRaisedBevelBorder());
        btnExcluir.setPreferredSize(new Dimension(70, 25));
        
        // Adicionar listener para o botão de excluir
        btnExcluir.addActionListener(e -> excluirEletrodomestico(eletrodomestico, indice));
        
        gbc.gridx = 3; gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(5, 20, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnExcluir, gbc);
        
        return painel;
    }

    private void excluirEletrodomestico(Eletrodomestico eletrodomestico, int indice) {
        // Confirmar exclusão
        double consumoKwh = (eletrodomestico.getWatts() * eletrodomestico.getTempoDeUso()) / 1000.0;
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir o eletrodoméstico:\n\n" +
            "Nome: " + eletrodomestico.getNome() + "\n" +
            "Potência: " + eletrodomestico.getWatts() + " W\n" +
            "Tempo de Uso: " + eletrodomestico.getTempoDeUso() + " horas\n" +
            "Consumo: " + String.format("%.2f", consumoKwh) + " kWh\n\n" +
            "Esta ação não pode ser desfeita!",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                // Carregar dados do arquivo
                String caminhoArquivo = System.getProperty("user.dir") + "/dados.ser";
                PersistenciaUsuario persistencia = new PersistenciaUsuario();
                Controlador controlador = persistencia.carregarDeArquivo(caminhoArquivo);
                Usuario usuario = controlador.getUsuario();
                
                // Remover o eletrodoméstico da lista
                List<Eletrodomestico> eletrodomesticos = usuario.getEletrodomesticos();
                if (indice >= 0 && indice < eletrodomesticos.size()) {
                    Eletrodomestico eletrodomesticoRemovido = eletrodomesticos.remove(indice);
                    
                    // Salvar os dados no arquivo
                    persistencia.salvarParaArquivo(controlador, caminhoArquivo);
                    
                    // Mostrar mensagem de sucesso
                    double consumoRemovido = (eletrodomesticoRemovido.getWatts() * eletrodomesticoRemovido.getTempoDeUso()) / 1000.0;
                    JOptionPane.showMessageDialog(this,
                        "Eletrodoméstico excluído com sucesso!\n\n" +
                        "Nome: " + eletrodomesticoRemovido.getNome() + "\n" +
                        "Potência: " + eletrodomesticoRemovido.getWatts() + " W\n" +
                        "Tempo de Uso: " + eletrodomesticoRemovido.getTempoDeUso() + " horas\n" +
                        "Consumo: " + String.format("%.2f", consumoRemovido) + " kWh",
                        "Exclusão Realizada",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recarregar a lista
                    carregarEletrodomesticos();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro: Eletrodoméstico não encontrado na lista!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir o eletrodoméstico: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 662, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewEletrodomesticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewEletrodomesticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewEletrodomesticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewEletrodomesticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewEletrodomesticos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
