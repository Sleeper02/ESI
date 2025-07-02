/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.facilita_uni.interfac;

// Imports necessários para integração com o sistema
import com.mycompany.facilita_uni.controle.Controlador;
import com.mycompany.facilita_uni.modelo.ContasFixas;
import com.mycompany.facilita_uni.modelo.Catalogo;
import com.mycompany.facilita_uni.persistencia.PersistenciaCatalogo;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *
 * @author Pedro
 */
public class ViewFixa extends javax.swing.JFrame {
    private JScrollPane scrollPane;
    private JPanel contasPanel;
    private JLabel totalLabel;
    private JLabel quantidadeLabel;
    
    /**
     * Creates new form ViewFixa
     */
    public ViewFixa() {
        initComponents();
        configurarJanela();
        carregarContas();
    }
    
       private void configurarJanela() {
        setTitle("Visualizar Contas Fixas");
        setLocationRelativeTo(null); // Centralizar na tela
        setResizable(true);
        
        // Configurar layout principal
        setLayout(new BorderLayout());
        
        // Título
        JLabel titulo = new JLabel("CONTAS FIXAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);
        
        // Painel para as contas com scroll
        contasPanel = new JPanel();
        contasPanel.setLayout(new BoxLayout(contasPanel, BoxLayout.Y_AXIS));
        contasPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(contasPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior com totais e botões
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        // Painel dos totais
        JPanel painelTotais = new JPanel(new GridLayout(2, 1, 5, 5));
        painelTotais.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        quantidadeLabel = new JLabel("Total de contas: 0", SwingConstants.CENTER);
        quantidadeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        quantidadeLabel.setForeground(new Color(0, 100, 200)); // Azul
        
        totalLabel = new JLabel("Valor Total: R$ 0,00", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(new Color(0, 100, 0)); // Verde escuro
        
        painelTotais.add(quantidadeLabel);
        painelTotais.add(totalLabel);
        painelInferior.add(painelTotais, BorderLayout.NORTH);
        
        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnFechar = new JButton("Fechar");
        
        btnAtualizar.addActionListener(e -> carregarContas());
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        painelInferior.add(painelBotoes, BorderLayout.CENTER);
        
        add(painelInferior, BorderLayout.SOUTH);
    }

     private void carregarContas() {
        try {
            // Limpar painel anterior
            contasPanel.removeAll();
            
            // Carregar dados do arquivo (mesmo processo do FixaConta)
            String caminhoArquivo = System.getProperty("user.dir") + "/dados.ser";
            PersistenciaCatalogo persistencia = new PersistenciaCatalogo();
            Controlador controlador = persistencia.carregarDeArquivo(caminhoArquivo);
            Catalogo catalogoCF = controlador.getCatalogo();
            
            List<ContasFixas> contas = catalogoCF.getContasFixas();
            
            if (contas.isEmpty()) {
                // Mostrar mensagem quando não há contas
                JLabel mensagemVazia = new JLabel("Nenhuma conta fixa cadastrada.", SwingConstants.CENTER);
                mensagemVazia.setFont(new Font("Arial", Font.ITALIC, 14));
                mensagemVazia.setForeground(Color.GRAY);
                contasPanel.add(mensagemVazia);
                
                // Resetar totais
                quantidadeLabel.setText("Total de contas: 0");
                totalLabel.setText("Valor Total: R$ 0,00");
            } else {
                // Adicionar cada conta como um painel
                double total = 0;
                int contasVencidas = 0;
                int contasProximoVencimento = 0;
                
                for (int i = 0; i < contas.size(); i++) {
                    ContasFixas conta = contas.get(i);
                    JPanel contaPanel = criarPainelConta(conta, i);
                    contasPanel.add(contaPanel);
                    contasPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento
                    total += conta.getValor();
                    
                    // Verificar status do vencimento
                    String status = verificarStatusVencimento(conta.getDataVencimento());
                    if ("VENCIDA".equals(status)) {
                        contasVencidas++;
                    } else if ("PRÓXIMO VENCIMENTO".equals(status)) {
                        contasProximoVencimento++;
                    }
                }
                
                // Atualizar totais
                String textoQuantidade = String.format("Total: %d contas", contas.size());
                if (contasVencidas > 0) {
                    textoQuantidade += String.format(" (%d vencidas)", contasVencidas);
                }
                if (contasProximoVencimento > 0) {
                    textoQuantidade += String.format(" (%d próx. venc.)", contasProximoVencimento);
                }
                
                quantidadeLabel.setText(textoQuantidade);
                totalLabel.setText(String.format("Valor Total: R$ %.2f", total));
            }
            
            // Refresh da interface
            contasPanel.revalidate();
            contasPanel.repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar as contas: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private String verificarStatusVencimento(String dataVencimento) {
        try {
            // Tentar diferentes formatos de data
            LocalDate dataVenc = null;
            DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            };
            
            for (DateTimeFormatter formatter : formatters) {
                try {
                    dataVenc = LocalDate.parse(dataVencimento, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    // Continuar tentando outros formatos
                }
            }
            
            if (dataVenc == null) {
                return "FORMATO INVÁLIDO";
            }
            
            LocalDate hoje = LocalDate.now();
            
            if (dataVenc.isBefore(hoje)) {
                return "VENCIDA";
            } else if (dataVenc.isEqual(hoje) || dataVenc.isBefore(hoje.plusDays(7))) {
                return "PRÓXIMO VENCIMENTO";
            } else {
                return "EM DIA";
            }
            
        } catch (Exception e) {
            return "ERRO";
        }
    }
        
    private JPanel criarPainelConta(ContasFixas conta, int indice) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        painel.setBackground(Color.WHITE);
        
        // Verificar status do vencimento para colorir o painel
        String status = verificarStatusVencimento(conta.getDataVencimento());
        Color corBorda = Color.LIGHT_GRAY;
        if ("VENCIDA".equals(status)) {
            corBorda = new Color(220, 53, 69); // Vermelho
            painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corBorda, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        } else if ("PRÓXIMO VENCIMENTO".equals(status)) {
            corBorda = new Color(255, 193, 7); // Amarelo/Laranja
            painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corBorda, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        }
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        
        // Nome da conta
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(labelNome, gbc);
        
        JLabel valorNome = new JLabel(conta.getNome());
        valorNome.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 0;
        painel.add(valorNome, gbc);
        
        // Valor
        JLabel labelValor = new JLabel("Valor:");
        labelValor.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.insets = new Insets(2, 20, 2, 5); // Mais espaço à esquerda
        painel.add(labelValor, gbc);
        
        JLabel valorPreco = new JLabel(String.format("R$ %.2f", (double)conta.getValor()));
        valorPreco.setFont(new Font("Arial", Font.BOLD, 12));
        valorPreco.setForeground(new Color(0, 100, 0)); // Verde escuro
        gbc.gridx = 3; gbc.gridy = 0;
        gbc.insets = new Insets(2, 5, 2, 5);
        painel.add(valorPreco, gbc);
        
        // Data de vencimento
        JLabel labelData = new JLabel("Vencimento:");
        labelData.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(labelData, gbc);
        
        JLabel valorData = new JLabel(conta.getDataVencimento());
        valorData.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(valorData, gbc);
        
        
        // Botão de excluir
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 10));
        btnExcluir.setBackground(new Color(220, 53, 69)); // Vermelho
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorder(BorderFactory.createRaisedBevelBorder());
        btnExcluir.setPreferredSize(new Dimension(70, 25));
        
        // Adicionar listener para o botão de excluir
        btnExcluir.addActionListener(e -> excluirConta(conta, indice));
        
        gbc.gridx = 4; gbc.gridy = 0;
        gbc.gridheight = 2; // Ocupar duas linhas
        gbc.insets = new Insets(2, 20, 2, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnExcluir, gbc);
        
        return painel;
    }
         private void excluirConta(ContasFixas conta, int indice) {
        // Confirmar exclusão
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir a conta:\n\n" +
            "Nome: " + conta.getNome() + "\n" +
            "Valor: R$ " + String.format("%.2f", (double)conta.getValor()) + "\n" +
            "Vencimento: " + conta.getDataVencimento() + "\n\n" +
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
                Catalogo catalogoCF = controlador.getCatalogo();
                
                // Remover a conta da lista
                List<ContasFixas> contas = catalogoCF.getContasFixas();
                if (indice >= 0 && indice < contas.size()) {
                    ContasFixas contaRemovida = contas.remove(indice);
                    
                    // Salvar os dados no arquivo
                    persistencia.salvarParaArquivo(controlador, caminhoArquivo);
                    
                    // Mostrar mensagem de sucesso
                    JOptionPane.showMessageDialog(this,
                        "Conta excluída com sucesso!\n\n" +
                        "Nome: " + contaRemovida.getNome() + "\n" +
                        "Valor: R$ " + String.format("%.2f", (double)contaRemovida.getValor()),
                        "Exclusão Realizada",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recarregar a lista
                    carregarContas();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro: Conta não encontrada na lista!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir a conta: " + e.getMessage(),
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
            .addGap(0, 874, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ViewFixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewFixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewFixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewFixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewFixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
