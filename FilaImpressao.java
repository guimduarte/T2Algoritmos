public class FilaImpressao {
    private Documento[] dados;
    private int primeiro;
    private int ultimo;
    private int ocupacao;

    public FilaImpressao(int capacidade) {
        dados = new Documento[capacidade];
        primeiro = 0;
        ultimo = 0;
        ocupacao = 0;
    }

    public boolean estaVazia() {
        return ocupacao == 0;
    }

    public boolean estaCheia() {
        return ocupacao == dados.length;
    }

    private int proximaPosicao(int posicao) {
        return (posicao + 1) % dados.length;
    }

    public void enfileirar(Documento documento) {
        if (estaCheia()) {
            throw new RuntimeException("Fila de impressão cheia! Capacidade máxima atingida.");
        }
        dados[ultimo] = documento;
        ultimo = proximaPosicao(ultimo);
        ocupacao++;
    }

    public Documento desenfileirar() {
        if (estaVazia()) {
            throw new RuntimeException("Fila de impressão vazia! Nenhum documento para imprimir.");
        }
        Documento documento = dados[primeiro];
        primeiro = proximaPosicao(primeiro);
        ocupacao--;
        return documento;
    }

    public int consultarPosicao(String nomeArquivo) {
        for (int i = primeiro, cont = 0; cont < ocupacao; cont++, i = proximaPosicao(i)) {
            if (dados[i].getArquivo().equals(nomeArquivo)) {
                return cont + 1; 
            }
        }
        return -1; 
    }

    public long calcularTempoEspera(String nomeArquivo) {
        for (int i = primeiro, cont = 0; cont < ocupacao; cont++, i = proximaPosicao(i)) {
            if (dados[i].getArquivo().equals(nomeArquivo)) {
                LocalDateTime agora = LocalDateTime.now();
                return java.time.Duration.between(dados[i].getHorarioSocilitado(), agora).toMinutes();
            }
        }
        return -1; 
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Fila de impressão vazia.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = primeiro, cont = 0; cont < ocupacao; cont++, i = proximaPosicao(i)) {
            sb.append(dados[i].toString()).append("\n");
        }
        return sb.toString();
    }
}