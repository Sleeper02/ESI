/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.facilita_uni.interfac;

// Imports necessários para integração com o sistema
import com.mycompany.facilita_uni.controle.Controlador;
import com.mycompany.facilita_uni.modelo.Atividade;
import com.mycompany.facilita_uni.modelo.Catalogo;
import com.mycompany.facilita_uni.persistencia.PersistenciaCatalogo;
import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Pedro
 */
public class ViewAtividades extends javax.swing.JFrame {

    private JScrollPane scrollPane;
    private JPanel atividadesPanel;
    private JLabel totalAtividadesLabel;
    /**
     * Creates new form ViewAtividades
     */
   

      public ViewAtividades() {
        initComponents();
        configurarJanela();
        carregarAtividades();
    }
    
    private void configurarJanela() {
        setTitle("Visualizar Atividades");
        setLocationRelativeTo(null); // Centralizar na tela
        setResizable(true);
        
        // Configurar layout principal
        setLayout(new BorderLayout());
        
        // Título
        JLabel titulo = new JLabel("ATIVIDADES CADASTRADAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);
        
        // Painel para as atividades com scroll
        atividadesPanel = new JPanel();
        atividadesPanel.setLayout(new BoxLayout(atividadesPanel, BoxLayout.Y_AXIS));
        atividadesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(atividadesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior com total e botões
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        // Painel do total
        JPanel painelTotal = new JPanel(new FlowLayout());
        painelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        totalAtividadesLabel = new JLabel("Total de Atividades: 0", SwingConstants.CENTER);
        totalAtividadesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAtividadesLabel.setForeground(new Color(0, 100, 200)); // Azul
        
        painelTotal.add(totalAtividadesLabel);
        painelInferior.add(painelTotal, BorderLayout.NORTH);
        
        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnFechar = new JButton("Fechar");
        
        btnAtualizar.addActionListener(e -> carregarAtividades());
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        painelInferior.add(painelBotoes, BorderLayout.CENTER);
        
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void carregarAtividades() {
    try {
        // Limpar painel anterior
        atividadesPanel.removeAll();
        
        // Carregar dados do arquivo (mesmo processo do InserirAtividade)
        String caminhoArquivo = System.getProperty("user.dir") + "/dados.ser";
        PersistenciaCatalogo persistencia = new PersistenciaCatalogo();
        Controlador controlador = persistencia.carregarDeArquivo(caminhoArquivo);
        Catalogo catalogoAT = controlador.getCatalogo();
        
        List<Atividade> atividades = catalogoAT.getAtividades();
        
        if (atividades.isEmpty()) {
            // Mostrar mensagem quando não há atividades
            JLabel mensagemVazia = new JLabel("Nenhuma atividade cadastrada.", SwingConstants.CENTER);
            mensagemVazia.setFont(new Font("Arial", Font.ITALIC, 14));
            mensagemVazia.setForeground(Color.GRAY);
            atividadesPanel.add(mensagemVazia);
            
            // Resetar total
            totalAtividadesLabel.setText("Total de Atividades: 0");
        } else {
            // Adicionar cada atividade como um painel
            for (int i = 0; i < atividades.size(); i++) {
                Atividade atividade = atividades.get(i);
                // ALTERAÇÃO: Passar o índice real da lista para o método
                JPanel atividadePanel = criarPainelAtividade(atividade, i);
                atividadesPanel.add(atividadePanel);
                atividadesPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento
            }
            
            // Atualizar total
            totalAtividadesLabel.setText("Total de Atividades: " + atividades.size());
        }
        
        // Refresh da interface
        atividadesPanel.revalidate();
        atividadesPanel.repaint();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao carregar as atividades: " + e.getMessage(), 
            "Erro", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private JPanel criarPainelAtividade(Atividade atividade, int indice) {
    JPanel painel = new JPanel(new GridBagLayout());
    painel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
        BorderFactory.createEmptyBorder(15, 20, 15, 20)
    ));
    painel.setBackground(Color.WHITE);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 10, 5, 10);
    
    // Número da atividade (para exibição)
    JLabel labelNumero = new JLabel("#" + (indice + 1));
    labelNumero.setFont(new Font("Arial", Font.BOLD, 16));
    labelNumero.setForeground(new Color(100, 100, 100));
    gbc.gridx = 0; gbc.gridy = 0;
    gbc.gridheight = 2;
    painel.add(labelNumero, gbc);
    
    // Nome da atividade
    JLabel labelNome = new JLabel("Atividade:");
    labelNome.setFont(new Font("Arial", Font.BOLD, 12));
    gbc.gridx = 1; gbc.gridy = 0;
    gbc.gridheight = 1;
    gbc.insets = new Insets(2, 20, 2, 5);
    painel.add(labelNome, gbc);
    
    JLabel valorNome = new JLabel(atividade.getNome());
    valorNome.setFont(new Font("Arial", Font.PLAIN, 14));
    valorNome.setForeground(new Color(50, 50, 50));
    gbc.gridx = 2; gbc.gridy = 0;
    gbc.insets = new Insets(2, 5, 2, 5);
    painel.add(valorNome, gbc);
    
    // Data da atividade
    JLabel labelData = new JLabel("Data:");
    labelData.setFont(new Font("Arial", Font.BOLD, 12));
    gbc.gridx = 1; gbc.gridy = 1;
    gbc.insets = new Insets(2, 20, 2, 5);
    painel.add(labelData, gbc);
    
    JLabel valorData = new JLabel(atividade.getData());
    valorData.setFont(new Font("Arial", Font.PLAIN, 14));
    valorData.setForeground(new Color(0, 100, 200)); // Azul
    gbc.gridx = 2; gbc.gridy = 1;
    gbc.insets = new Insets(2, 5, 2, 5);
    painel.add(valorData, gbc);
    
    // Ícone decorativo
    JLabel icone = new JLabel("📅");
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
    btnExcluir.addActionListener(e -> excluirAtividade(atividade, indice));
    
    gbc.gridx = 3; gbc.gridy = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(5, 20, 5, 10);
    gbc.anchor = GridBagConstraints.CENTER;
    painel.add(btnExcluir, gbc);
    
    return painel;
}

private void excluirAtividade(Atividade atividade, int indice) {
    // Confirmar exclusão
    int opcao = JOptionPane.showConfirmDialog(
        this,
        "Tem certeza que deseja excluir a atividade:\n\n" +
        "Nome: " + atividade.getNome() + "\n" +
        "Data: " + atividade.getData() + "\n\n" +
        "Esta ação não pode ser desfeita!",
        "Confirmar Exclusão",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );
    
    if (opcao == JOptionPane.YES_OPTION) {
        try {
            // Carregar dados do arquivo
            String caminhoArquivo = System.getProperty("user.dir") + "/dados.ser";
            PersistenciaCatalogo persistencia = new PersistenciaCatalogo();
            Controlador controlador = persistencia.carregarDeArquivo(caminhoArquivo);
            Catalogo catalogoAT = controlador.getCatalogo();
            
            // Remover a atividade da lista
            List<Atividade> atividades = catalogoAT.getAtividades();
            if (indice >= 0 && indice < atividades.size()) {
                Atividade atividadeRemovida = atividades.remove(indice);
                
                // Salvar os dados no arquivo
                persistencia.salvarParaArquivo(controlador, caminhoArquivo);
                
                // Mostrar mensagem de sucesso
                JOptionPane.showMessageDialog(this,
                    "Atividade excluída com sucesso!\n\n" +
                    "Nome: " + atividadeRemovida.getNome() + "\n" +
                    "Data: " + atividadeRemovida.getData(),
                    "Exclusão Realizada",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Recarregar a lista
                carregarAtividades();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro: Atividade não encontrada na lista!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao excluir a atividade: " + e.getMessage(),
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
            .addGap(0, 885, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ViewAtividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAtividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAtividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAtividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAtividades().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
