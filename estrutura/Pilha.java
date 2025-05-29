package estrutura;

public class Pilha<T> {
    private T[] elementos;
    private int topo; // índice do topo da pilha
    private int capacidadeMaxima; // Nova propriedade para a capacidade máxima

    @SuppressWarnings("unchecked")
    public Pilha(int capacidade) {
        this.elementos = (T[]) new Object[capacidade];
        this.topo = -1; // Pilha vazia
        this.capacidadeMaxima = capacidade;
    }

    // Adiciona um elemento ao topo da pilha
    public void push(T elemento) {
        if (isFull()) {
            throw new IllegalStateException("Pilha cheia. Capacidade máxima atingida.");
        }
        topo++;
        elementos[topo] = elemento;
    }

    // Remove e retorna o elemento do topo da pilha
    public T pop() {
        if (isEmpty()) {
            return null; // ou lançar uma exceção NoSuchElementException
        }
        T elementoRemovido = elementos[topo];
        elementos[topo] = null; // Opcional: para liberar referência e ajudar o GC
        topo--;
        return elementoRemovido;
    }

    // Retorna o elemento do topo da pilha sem removê-lo
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return elementos[topo];
    }

    // Verifica se a pilha está vazia
    public boolean isEmpty() {
        return topo == -1;
    }

    // Verifica se a pilha está cheia
    public boolean isFull() {
        return topo == elementos.length - 1;
    }

    // Retorna o número de elementos na pilha
    public int size() {
        return topo + 1;
    }

    // Retorna a capacidade máxima da pilha
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * Retorna o elemento na posição lógica 'index' da pilha (0 para o topo, 1 para o abaixo do topo, etc.).
     * @param index A posição lógica do elemento na pilha.
     * @return O elemento na posição especificada, ou null se o índice for inválido.
     */
    public T getElementoAt(int index) {
        if (index < 0 || index >= size()) {
            return null; // Índice fora dos limites da pilha
        }
        return elementos[topo - index]; // Pilha LIFO: 0 é o topo
    }

    /**
     * Consulta a posição de um elemento na pilha (a partir do topo).
     * @param elementoConsulta O elemento a ser consultado (requer que T.equals() esteja implementado corretamente).
     * @return A posição lógica do elemento (0-based, 0 para o topo), ou -1 se não encontrado.
     */
    public int consultarPosicao(T elementoConsulta) {
        if (isEmpty()) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            if (elementos[topo - i] != null && elementos[topo - i].equals(elementoConsulta)) {
                return i; // Posição a partir do topo (0 para o topo)
            }
        }
        return -1;
    }

    public String listarElementos() {
        if (isEmpty()) {
            return "Pilha de reimpressão vazia (0/" + capacidadeMaxima + ").";
        }
        StringBuilder sb = new StringBuilder("Documentos na Pilha de Reimpressão (Total: " + size() + "/" + capacidadeMaxima + "):\n");
        for (int i = topo; i >= 0; i--) {
            sb.append("  [").append(topo - i + 1).append("] ").append(elementos[i].toString()).append("\n"); // Posição base 1 (1 é o topo)
        }
        return sb.toString();
    }
}