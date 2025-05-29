// src/main/java/br/maua/ed/revisao/estruturas/Fila.java
package estrutura;

public class Fila<T> {
    private T[] elementos;
    private int inicio;
    private int fim;
    private int total;
    private int capacidadeMaxima;

    @SuppressWarnings("unchecked")
    public Fila(int capacidade) {
        this.elementos = (T[]) new Object[capacidade];
        this.inicio = 0;
        this.fim = -1;
        this.total = 0;
        this.capacidadeMaxima = capacidade;
    }

    public void add(T elemento) {
        if (isFull()) {
            throw new IllegalStateException("Fila cheia. Capacidade máxima atingida.");
        }
        fim = (fim + 1) % elementos.length;
        elementos[fim] = elemento;
        total++;
    }

    public T remove() {
        if (isEmpty()) {
            return null;
        }
        T elementoRemovido = elementos[inicio];
        elementos[inicio] = null;
        inicio = (inicio + 1) % elementos.length;
        total--;
        return elementoRemovido;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return elementos[inicio];
    }

    public boolean isEmpty() {
        return total == 0;
    }

    public boolean isFull() {
        return total == capacidadeMaxima;
    }

    public int size() {
        return total;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * Retorna o elemento na posição lógica 'index' da fila (0 para o primeiro, 1 para o segundo, etc.).
     * @param index A posição lógica do elemento na fila.
     * @return O elemento na posição especificada, ou null se o índice for inválido.
     */
    public T getElementoAt(int index) {
        if (index < 0 || index >= total) {
            return null; // Índice fora dos limites da fila
        }
        int indiceReal = (inicio + index) % elementos.length;
        return elementos[indiceReal];
    }

    /**
     * Consulta a posição de um elemento na fila.
     * @param elementoConsulta O elemento a ser consultado (requer que T.equals() esteja implementado corretamente).
     * @return A posição lógica do elemento (0-based), ou -1 se não encontrado.
     */
    public int consultarPosicao(T elementoConsulta) {
        if (isEmpty()) {
            return -1;
        }
        for (int i = 0; i < total; i++) {
            int indiceReal = (inicio + i) % elementos.length;
            if (elementos[indiceReal] != null && elementos[indiceReal].equals(elementoConsulta)) {
                return i;
            }
        }
        return -1;
    }

    public String listarDocumentos() {
        if (isEmpty()) {
            return "Fila de impressão vazia (0/" + capacidadeMaxima + ").";
        }
        StringBuilder sb = new StringBuilder("Documentos na Fila de Impressão (Total: " + total + "/" + capacidadeMaxima + "):\n");
        for (int i = 0; i < total; i++) {
            int indiceReal = (inicio + i) % elementos.length;
            sb.append("  [").append(i + 1).append("] ").append(elementos[indiceReal].toString()).append("\n"); // Posição base 1
        }
        return sb.toString();
    }
}