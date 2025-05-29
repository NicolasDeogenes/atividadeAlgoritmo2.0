package impressora;

import estrutura.Fila;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class SistemaImpressora {
    private Fila<Documento> filaImpressao;
    private static final int CAPACIDADE_MAXIMA = 5;

    public SistemaImpressora() {
        this.filaImpressao = new Fila<>(CAPACIDADE_MAXIMA);
    }

    public void adicionarDocumento(String nomeArquivo, String nomeUsuario) {
        try {
            Documento novoDocumento = new Documento(nomeArquivo, nomeUsuario);
            filaImpressao.add(novoDocumento);
            System.out.println("Documento '" + nomeArquivo + "' adicionado à fila.");
        } catch (IllegalStateException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void imprimirProximoDocumento() {
        if (filaImpressao.isEmpty()) {
            System.out.println("Fila de impressão vazia. Nenhum documento para imprimir.");
            return;
        }
        Documento documentoImpresso = filaImpressao.remove();
        if (documentoImpresso != null) {
            documentoImpresso.setHorarioImpressao(LocalDateTime.now());
            System.out.println("Imprimindo: " + documentoImpresso.getNomeArquivo() + " (Usuário: " + documentoImpresso.getNomeUsuario() + ")");
            System.out.println("Tempo de espera: " + documentoImpresso.calcularTempoEspera() + " segundos.");
        }
    }

    public void consultarDocumento(String nomeArquivo, String nomeUsuario) {
        Documento docParaBuscar = new Documento(nomeArquivo, nomeUsuario); // Cria um documento 'dummy' para comparação
        int posicao = filaImpressao.consultarPosicao(docParaBuscar);

        if (posicao != -1) {
            Documento docEncontrado = filaImpressao.getElementoAt(posicao); // Recupera a instância real
            if (docEncontrado != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                System.out.println("Documento encontrado na fila!");
                System.out.println("   Arquivo: " + docEncontrado.getNomeArquivo());
                System.out.println("   Usuário: " + docEncontrado.getNomeUsuario());
                System.out.println("   Posição na fila: " + (posicao + 1)); // Posição base 1
                System.out.println("   Horário de solicitação: " + docEncontrado.getHorarioSolicitacao().format(formatter));
            } else {
                 System.out.println("Documento '" + nomeArquivo + "' (Usuário: " + nomeUsuario + ") não encontrado na fila.");
            }
        } else {
            System.out.println("Documento '" + nomeArquivo + "' (Usuário: " + nomeUsuario + ") não encontrado na fila.");
        }
    }

    public void exibirStatusFila() {
        System.out.println(filaImpressao.listarDocumentos());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaImpressora sistema = new SistemaImpressora();

        System.out.println("=== Sistema de Gerenciamento de Impressões ===");

        int opcao;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Adicionar Documento à Fila");
            System.out.println("2. Imprimir Próximo Documento");
            System.out.println("3. Consultar Documento na Fila");
            System.out.println("4. Exibir Status da Fila");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // consome a entrada inválida
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do arquivo: ");
                    String arquivo = scanner.nextLine();
                    System.out.print("Nome do usuário: ");
                    String usuario = scanner.nextLine();
                    sistema.adicionarDocumento(arquivo, usuario);
                    break;
                case 2:
                    sistema.imprimirProximoDocumento();
                    break;
                case 3:
                    System.out.print("Nome do arquivo para consulta: ");
                    String arquivoConsulta = scanner.nextLine();
                    System.out.print("Nome do usuário para consulta: ");
                    String usuarioConsulta = scanner.nextLine();
                    sistema.consultarDocumento(arquivoConsulta, usuarioConsulta);
                    break;
                case 4:
                    sistema.exibirStatusFila();
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