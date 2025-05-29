// src/main/java/br/maua/ed/revisao/reimpressao/Reimpressao.java
package reImpressao;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reimpressao {
    private String nomeArquivo;
    private String nomeUsuario;
    private LocalDateTime horarioSolicitacao;
    private LocalDateTime horarioReimpressao; // Horário em que foi reimpresso

    public Reimpressao(String nomeArquivo, String nomeUsuario) {
        this.nomeArquivo = nomeArquivo;
        this.nomeUsuario = nomeUsuario;
        this.horarioSolicitacao = LocalDateTime.now();
        this.horarioReimpressao = null;
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

    public LocalDateTime getHorarioReimpressao() {
        return horarioReimpressao;
    }

    // Setter para o horário de reimpressão
    public void setHorarioReimpressao(LocalDateTime horarioReimpressao) {
        this.horarioReimpressao = horarioReimpressao;
    }

    /**
     * Calcula o tempo total decorrido desde a solicitação até a reimpressão.
     * @return O tempo decorrido em segundos, ou -1 se ainda não foi reimpresso.
     */
    public long calcularTempoAtendimento() {
        if (horarioSolicitacao != null && horarioReimpressao != null) {
            return Duration.between(horarioSolicitacao, horarioReimpressao).getSeconds();
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimpressao that = (Reimpressao) o;
        return Objects.equals(nomeArquivo, that.nomeArquivo) &&
               Objects.equals(nomeUsuario, that.nomeUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeArquivo, nomeUsuario);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String solicitacaoFormatada = horarioSolicitacao.format(formatter);
        String reimpressaoFormatada = (horarioReimpressao != null) ? horarioReimpressao.format(formatter) : "Aguardando";
        long tempoAtendimento = calcularTempoAtendimento();
        String tempoAtendimentoStr = (tempoAtendimento != -1) ? tempoAtendimento + "s" : "N/A";

        return "Reimpressao{" +
               "arquivo='" + nomeArquivo + '\'' +
               ", usuario='" + nomeUsuario + '\'' +
               ", solicitado=" + solicitacaoFormatada +
               ", reimpresso=" + reimpressaoFormatada +
               ", tempoAtendimento=" + tempoAtendimentoStr +
               '}';
    }
}