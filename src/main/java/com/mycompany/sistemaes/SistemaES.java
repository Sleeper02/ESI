package com.mycompany.sistemaes;

import java.util.Scanner;

public class SistemaES {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Caminho absoluto usando a pasta onde o programa está sendo executado
        String caminhoArquivo = System.getProperty("user.dir") + "/dados.ser";

        Controlador controlador = Controlador.carregarDeArquivo(caminhoArquivo);

        int opcao;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Inserir nova conta");
            System.out.println("2. Ver contas");
            System.out.println("3. Inserir atividade");
            System.out.println("4. Ver atividades");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.println("1. Conta Fixa | 2. Conta de Luz");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Valor (em reais): ");
                    int valor = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Data de vencimento: ");
                    String data = scanner.nextLine();

                    if (tipo == 1) {
                        controlador.adicionarContaFixa(new ContasFixas(valor, nome, data));
                    } else {
                        System.out.print("Consumo (kWh): ");
                        int kwh = scanner.nextInt();
                        System.out.print("Tempo de uso (horas): ");
                        int tempo = scanner.nextInt();
                        scanner.nextLine();
                        controlador.adicionarEletrodomestico(new Eletrodomestico(nome, kwh, tempo));
                    }
                    System.out.println("Conta adicionada com sucesso.");
                    break;

                case 2:
                    System.out.println("\n--- CONTAS FIXAS ---");
                    for (ContasFixas c : controlador.getContasFixas()) {
                        System.out.println(c.getNome() + " - R$" + c.getValor() + " - Vence em: " + c.getDataVencimento());
                    }
                    System.out.println("\n--- ELETRODOMÉSTICOS ---");
                    for (Eletrodomestico e : controlador.getEletrodomesticos()) {
                        System.out.println(e.getNome() + " - Consumo: " + e.getWatts() + "W por " + e.getTempoDeUso() + "h");
                    }
                    System.out.printf("\nTOTAL ESTIMADO: R$ %.2f\n", controlador.calcularTotal());
                    break;

                case 3:
                    System.out.print("Nome da atividade: ");
                    String nomeAtividade = scanner.nextLine();
                    System.out.print("Data (ex: 05/05): ");
                    String dataAtividade = scanner.nextLine();
                    controlador.adicionarAtividade(new Atividade(nomeAtividade, dataAtividade));
                    System.out.println("Atividade adicionada.");
                    break;

                case 4:
                    System.out.println("\n--- ATIVIDADES ---");
                    for (Atividade a : controlador.getAtividades()) {
                        System.out.println(a);
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        controlador.salvarParaArquivo(caminhoArquivo);
        scanner.close();
    }
}
