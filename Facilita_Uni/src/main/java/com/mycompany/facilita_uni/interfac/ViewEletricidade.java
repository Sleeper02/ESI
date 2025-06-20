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
public class ViewEletricidade extends javax.swing.JFrame {

    /**
     * Creates new form ViewEletricidade
     */
      private JScrollPane scrollPane;
    private JPanel eletrodomesticosPanel;
    private JLabel totalLabel;
    private JLabel consumoTotalLabel;
    
    /**
     * Creates new form ViewEletricidade
     */
    public ViewEletricidade() {
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
        JLabel titulo = new JLabel("ELETRODOMÉSTICOS", SwingConstants.CENTER);
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
        
        // Painel inferior com totais e botões
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        // Painel dos totais
        JPanel painelTotais = new JPanel(new GridLayout(2, 1, 5, 5));
        painelTotais.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        consumoTotalLabel = new JLabel("Consumo Total: 0 kWh", SwingConstants.CENTER);
        consumoTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        consumoTotalLabel.setForeground(new Color(0, 100, 200)); // Azul
        
        totalLabel = new JLabel("Custo Estimado: R$ 0,00", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(new Color(200, 100, 0)); // Laranja
        
        painelTotais.add(consumoTotalLabel);
        painelTotais.add(totalLabel);
        painelInferior.add(painelTotais, BorderLayout.NORTH);
        
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
        
        // Carregar dados do arquivo (mesmo processo do EletricidadeConta)
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
            consumoTotalLabel.setText("Consumo Total: 0 kWh");
            totalLabel.setText("Custo Estimado: R$ 0,00");
        } else {
            // Adicionar cada eletrodoméstico como um painel
            int consumoTotal = 0;
            double custoTotal = 0;
            
            for (int i = 0; i < eletrodomesticos.size(); i++) {
                Eletrodomestico eletro = eletrodomesticos.get(i);
                // CORREÇÃO: Passar o índice como segundo parâmetro
                JPanel eletroPanel = criarPainelEletrodomestico(eletro, i);
                eletrodomesticosPanel.add(eletroPanel);
                eletrodomesticosPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento
                
                // Calcular consumo total
                int consumoIndividual = eletro.getWatts() * eletro.getTempoDeUso();
                consumoTotal += consumoIndividual;
                
                // Estimar custo (usando uma tarifa média de R$ 0,65 por kWh)
                double kWhIndividual = consumoIndividual / 1000.0; // Converter watts para kWh
                custoTotal += kWhIndividual * 0.65; // Tarifa estimada
            }
            
            // Atualizar totais
            double kWhTotal = consumoTotal / 1000.0;
            consumoTotalLabel.setText(String.format("Consumo Total: %.2f kWh", kWhTotal));
            totalLabel.setText(String.format("Custo Estimado: R$ %.2f", custoTotal));
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
    
       private JPanel criarPainelEletrodomestico(Eletrodomestico eletro, int indice) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        painel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        
        // Nome do eletrodoméstico
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(labelNome, gbc);
        
        JLabel valorNome = new JLabel(eletro.getNome());
        valorNome.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 0;
        painel.add(valorNome, gbc);
        
        // Potência (Watts)
        JLabel labelWatts = new JLabel("Potência:");
        labelWatts.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.insets = new Insets(2, 20, 2, 5); // Mais espaço à esquerda
        painel.add(labelWatts, gbc);
        
        JLabel valorWatts = new JLabel(eletro.getWatts() + " W");
        valorWatts.setFont(new Font("Arial", Font.PLAIN, 12));
        valorWatts.setForeground(new Color(0, 100, 200)); // Azul
        gbc.gridx = 3; gbc.gridy = 0;
        gbc.insets = new Insets(2, 5, 2, 5);
        painel.add(valorWatts, gbc);
        
        // Tempo de uso
        JLabel labelTempo = new JLabel("Tempo de uso:");
        labelTempo.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(labelTempo, gbc);
        
        JLabel valorTempo = new JLabel(eletro.getTempoDeUso() + " horas");
        valorTempo.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(valorTempo, gbc);
        
        // Consumo calculado
        JLabel labelConsumo = new JLabel("Consumo:");
        labelConsumo.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(2, 20, 2, 5);
        painel.add(labelConsumo, gbc);
        
        int consumoWh = eletro.getWatts() * eletro.getTempoDeUso();
        double consumokWh = consumoWh / 1000.0;
        JLabel valorConsumo = new JLabel(String.format("%.2f kWh", consumokWh));
        valorConsumo.setFont(new Font("Arial", Font.BOLD, 12));
        valorConsumo.setForeground(new Color(200, 100, 0)); // Laranja
        gbc.gridx = 3; gbc.gridy = 1;
        gbc.insets = new Insets(2, 5, 2, 5);
        painel.add(valorConsumo, gbc);
        
        // Custo estimado
        JLabel labelCusto = new JLabel("Custo estimado:");
        labelCusto.setFont(new Font("Arial", Font.BOLD, 12));
        labelCusto.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        painel.add(labelCusto, gbc);
        
        double custoEstimado = consumokWh * 0.65; // Tarifa média
        JLabel valorCusto = new JLabel(String.format("R$ %.2f", custoEstimado));
        valorCusto.setFont(new Font("Arial", Font.ITALIC, 11));
        valorCusto.setForeground(Color.GRAY);
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.gridwidth = 2;
        painel.add(valorCusto, gbc);
        
        // Botão de excluir
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 10));
        btnExcluir.setBackground(new Color(220, 53, 69)); // Vermelho
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorder(BorderFactory.createRaisedBevelBorder());
        btnExcluir.setPreferredSize(new Dimension(70, 25));
        
        // Adicionar listener para o botão de excluir
        btnExcluir.addActionListener(e -> excluirEletrodomestico(eletro, indice));
        
        gbc.gridx = 4; gbc.gridy = 0;
        gbc.gridheight = 3; // Ocupar três linhas
        gbc.gridwidth = 1; // Resetar gridwidth
        gbc.insets = new Insets(2, 20, 2, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnExcluir, gbc);
        
        return painel;
    }

       
         private void excluirEletrodomestico(Eletrodomestico eletro, int indice) {
        // Calcular consumo e custo para exibir na confirmação
        int consumoWh = eletro.getWatts() * eletro.getTempoDeUso();
        double consumokWh = consumoWh / 1000.0;
        double custoEstimado = consumokWh * 0.65;
        
        // Confirmar exclusão
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir o eletrodoméstico:\n\n" +
            "Nome: " + eletro.getNome() + "\n" +
            "Potência: " + eletro.getWatts() + " W\n" +
            "Tempo de uso: " + eletro.getTempoDeUso() + " horas\n" +
            "Consumo: " + String.format("%.2f kWh", consumokWh) + "\n" +
            "Custo estimado: R$ " + String.format("%.2f", custoEstimado) + "\n\n" +
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
                    Eletrodomestico eletroRemovido = eletrodomesticos.remove(indice);
                    
                    // Salvar os dados no arquivo
                    persistencia.salvarParaArquivo(controlador, caminhoArquivo);
                    
                    // Mostrar mensagem de sucesso
                    JOptionPane.showMessageDialog(this,
                        "Eletrodoméstico excluído com sucesso!\n\n" +
                        "Nome: " + eletroRemovido.getNome() + "\n" +
                        "Potência: " + eletroRemovido.getWatts() + " W\n" +
                        "Tempo de uso: " + eletroRemovido.getTempoDeUso() + " horas",
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
            .addGap(0, 884, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ViewEletricidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewEletricidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewEletricidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewEletricidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewEletricidade().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
