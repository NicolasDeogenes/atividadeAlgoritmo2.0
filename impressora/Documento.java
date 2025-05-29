package impressora;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Documento {
    private String nomeArquivo;
    private String nomeUsuario;
    private LocalDateTime horarioSolicitacao;
    private LocalDateTime horarioImpressao;

    public Documento(String nomeArquivo, String nomeUsuario) {
        this.nomeArquivo = nomeArquivo;
        this.nomeUsuario = nomeUsuario;
        this.horarioSolicitacao = LocalDateTime.now();
        this.horarioImpressao = null;
    }

    // Getters
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public LocalDateTime getHorarioSolicitacao() {
        return horarioSolicitacao;
    }

    public LocalDateTime getHorarioImpressao() {
        return horarioImpressao;
    }

    // Setter para o horário de impressão
    public void setHorarioImpressao(LocalDateTime horarioImpressao) {
        this.horarioImpressao = horarioImpressao;
    }

    public long calcularTempoEspera() {
        if (horarioSolicitacao != null && horarioImpressao != null) {
            return Duration.between(horarioSolicitacao, horarioImpressao).getSeconds();
        }
        return -1;
    }

    // Sobrescrita do método equals para permitir comparação por conteúdo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Documento documento = (Documento) o;
        return Objects.equals(nomeArquivo, documento.nomeArquivo) &&
               Objects.equals(nomeUsuario, documento.nomeUsuario);
    }

    // Sobrescrita do método hashCode para consistência com equals
    @Override
    public int hashCode() {
        return Objects.hash(nomeArquivo, nomeUsuario);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String solicitacaoFormatada = horarioSolicitacao.format(formatter);
        String impressaoFormatada = (horarioImpressao != null) ? horarioImpressao.format(formatter) : "Aguardando";
        long tempoEspera = calcularTempoEspera();
        String tempoEsperaStr = (tempoEspera != -1) ? tempoEspera + "s" : "N/A";

        return "Documento{" +
               "arquivo='" + nomeArquivo + '\'' +
               ", usuario='" + nomeUsuario + '\'' +
               ", solicitado=" + solicitacaoFormatada +
               ", impresso=" + impressaoFormatada +
               ", espera=" + tempoEsperaStr +
               '}';
    }
}