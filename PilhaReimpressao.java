

public class PilhaReimpressao {
    private No topo;
    private int capacidade;
    private int ocupacao;

    public PilhaReimpressao(int capacidade) {
        this.topo = null;
        this.capacidade = capacidade;
        this.ocupacao = 0;
    }

    public boolean estaVazia() {
        return ocupacao == 0;
    }

    public boolean estaCheia() {
        return ocupacao == capacidade;
    }

    public void empilhar(Documento documento) {
        if (estaCheia()) {
            throw new RuntimeException("Pilha de reimpressão cheia! Capacidade máxima atingida.");
        }
        No novoNo = new No(documento);
        novoNo.setProximo(topo);
        topo = novoNo;
        ocupacao++;
    }

    public Documento desempilhar() {
        if (estaVazia()) {
            throw new RuntimeException("Pilha de reimpressão vazia! Nenhum documento para reimprimir.");
        }
        Documento documento = topo.getDocumento();
        topo = topo.getProximo();
        ocupacao--;
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

    public String getHorarioSolicitacaoPorNome(String nomeArquivo) {
        No atual = topo;
        while (atual != null) {
            if (atual.getDocumento().getArquivo().equals(nomeArquivo)) {
                return atual.getDocumento().getHorario();
            }
            atual = atual.getProximo();
        }
        return null;
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Pilha de reimpressão vazia.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Ocupação: ").append(ocupacao).append("/").append(capacidade).append("\n");
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