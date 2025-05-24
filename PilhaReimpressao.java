import java.time.LocalDateTime;

public class PilhaReimpressao {
    private No topo;

    public boolean estaVazia() {
        return topo == null;
    }

    public void empilhar(Documento documento) {
        No novoNo = new No(documento);
        novoNo.setProximo(topo);
        topo = novoNo;
    }

    public Documento desempilhar() {
        if (estaVazia()) {
            throw new RuntimeException("Pilha de reimpressão vazia! Nenhum documento para reimprimir.");
        }
        Documento documento = topo.getDocumento();
        topo = topo.getProximo();
        return documento;
    }

    public int consultarPosicao(String nomeArquivo) {
        No atual = topo;
        int posicao = 1;
        while (atual != null) {
            if (atual.getDocumento().getArquivo().equals(nomeArquivo)) {
                return posicao;
            }
            atual = atual.getProximo();
            posicao++;
        }
        return -1; 
    }

    public long calcularTempoEspera(String nomeArquivo) {
        No atual = topo;
        while (atual != null) {
            if (atual.getDocumento().getArquivo().equals(nomeArquivo)) {
                LocalDateTime agora = LocalDateTime.now();
                return java.time.Duration.between(atual.getDocumento().getHorarioSocilitado(), agora).toMinutes();
            }
            atual = atual.getProximo();
        }
        return -1; 
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Pilha de reimpressão vazia.";
        }
        StringBuilder sb = new StringBuilder();
        No atual = topo;
        while (atual != null) {
            sb.append(atual.getDocumento().toString()).append("\n");
            atual = atual.getProximo();
        }
        return sb.toString();
    }

    private static class No {
        private Documento documento;
        private No proximo;

        public No(Documento documento) {
            this.documento = documento;
        }

        public Documento getDocumento() {
            return documento;
        }

        public No getProximo() {
            return proximo;
        }

        public void setProximo(No proximo) {
            this.proximo = proximo;
        }
    }
}