package reImpressao;

import estrutura.Pilha;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class SistemaReimpressao {
    private Pilha<Reimpressao> pilhaReimpressao;
    private static final int CAPACIDADE_MAXIMA = 3;

    public SistemaReimpressao() {
        this.pilhaReimpressao = new Pilha<>(CAPACIDADE_MAXIMA);
    }

    public void solicitarReimpressao(String nomeArquivo, String nomeUsuario) {
        try {
            Reimpressao novaReimpressao = new Reimpressao(nomeArquivo, nomeUsuario);
            pilhaReimpressao.push(novaReimpressao);
            System.out.println("Solicitação de reimpressão para '" + nomeArquivo + "' adicionada à pilha.");
        } catch (IllegalStateException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void executarReimpressao() {
        if (pilhaReimpressao.isEmpty()) {
            System.out.println("Pilha de reimpressão vazia. Nenhum documento para reimprimir.");
            return;
        }
        Reimpressao reimpressaoExecutada = pilhaReimpressao.pop();
        if (reimpressaoExecutada != null) {
            reimpressaoExecutada.setHorarioReimpressao(LocalDateTime.now());
            System.out.println("Reimpressão executada: " + reimpressaoExecutada.getNomeArquivo() + " (Usuário: " + reimpressaoExecutada.getNomeUsuario() + ")");
            System.out.println("Tempo de atendimento: " + reimpressaoExecutada.calcularTempoAtendimento() + " segundos.");
        }
    }

    public void consultarDocumento(String nomeArquivo, String nomeUsuario) {
        Reimpressao reimpressaoParaBuscar = new Reimpressao(nomeArquivo, nomeUsuario); // Cria um 'dummy' para comparação
        int posicao = pilhaReimpressao.consultarPosicao(reimpressaoParaBuscar);

        if (posicao != -1) {
            Reimpressao reimpressaoEncontrada = pilhaReimpressao.getElementoAt(posicao); // Recupera a instância real
            if (reimpressaoEncontrada != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                System.out.println("Documento encontrado na pilha de reimpressão!");
                System.out.println("Arquivo: " + reimpressaoEncontrada.getNomeArquivo());
                System.out.println("Usuário: " + reimpressaoEncontrada.getNomeUsuario());
                System.out.println("Posição na pilha (a partir do topo): " + (posicao + 1)); // Posição base 1
                System.out.println("Horário de solicitação: " + reimpressaoEncontrada.getHorarioSolicitacao().format(formatter));
            } else {
                 System.out.println("Documento '" + nomeArquivo + "' (Usuário: " + nomeUsuario + ") não encontrado na pilha.");
            }
        } else {
            System.out.println("Documento '" + nomeArquivo + "' (Usuário: " + nomeUsuario + ") não encontrado na pilha.");
        }
    }

    public void exibirStatusPilha() {
        System.out.println(pilhaReimpressao.listarElementos());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaReimpressao sistema = new SistemaReimpressao();

        System.out.println("=== Sistema de Reimpressão Emergencial ===");

        int opcao;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Solicitar Reimpressão");
            System.out.println("2. Executar Reimpressão");
            System.out.println("3. Consultar Documento na Pilha");
            System.out.println("4. Exibir Status da Pilha");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do arquivo para reimpressão: ");
                    String arquivo = scanner.nextLine();
                    System.out.print("Nome do usuário: ");
                    String usuario = scanner.nextLine();
                    sistema.solicitarReimpressao(arquivo, usuario);
                    break;
                case 2:
                    sistema.executarReimpressao();
                    break;
                case 3:
                    System.out.print("Nome do arquivo para consulta: ");
                    String arquivoConsulta = scanner.nextLine();
                    System.out.print("Nome do usuário para consulta: ");
                    String usuarioConsulta = scanner.nextLine();
                    sistema.consultarDocumento(arquivoConsulta, usuarioConsulta);
                    break;
                case 4:
                    sistema.exibirStatusPilha();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}