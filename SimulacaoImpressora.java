import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList; 
import java.util.List;    

class Documento {
    private String arquivo;
    private String usuario;
    private LocalDateTime horarioSocilitado;

    public Documento(String arquivo, String usuario) {
        this.arquivo = arquivo;
        this.usuario = usuario;
        this.horarioSocilitado = LocalDateTime.now();
    }

    public String getArquivo() {
        return arquivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getHorarioSocilitado() {
        return horarioSocilitado;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Arquivo: " + arquivo + ", Usuário: " + usuario + ", Solicitado em: " + horarioSocilitado.format(formatter);
    }
}


class FilaImpressao {
    private List<Documento> fila;
    private int capacidade;

    public FilaImpressao(int capacidade) {
        this.capacidade = capacidade;
        this.fila = new ArrayList<>();
    }

    public void enfileirar(Documento doc) {
        if (fila.size() < capacidade) {
            fila.add(doc);
            JOptionPane.showMessageDialog(null, "Documento '" + doc.getArquivo() + "' adicionado à fila.");
        } else {
            JOptionPane.showMessageDialog(null, "Fila de impressão cheia!", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Fila de impressão cheia.");
        }
    }

    public Documento desenfileirar() {
        if (!fila.isEmpty()) {
            return fila.remove(0);
        }
        JOptionPane.showMessageDialog(null, "Fila de impressão vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
        throw new RuntimeException("Fila de impressão vazia.");
    }

    public int consultarPosicao(String nomeArquivo) {
        for (int i = 0; i < fila.size(); i++) {
            if (fila.get(i).getArquivo().equalsIgnoreCase(nomeArquivo)) {
                return i + 1; 
            }
        }
        return -1;
    }

    public long calcularTempoEspera(String nomeArquivo) {
        for (int i = 0; i < fila.size(); i++) {
            if (fila.get(i).getArquivo().equalsIgnoreCase(nomeArquivo)) {
                return (long) i * 1; 
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (fila.isEmpty()) {
            return "Fila de impressão vazia.";
        }
        StringBuilder sb = new StringBuilder("Estado da Fila:\n");
        for (int i = 0; i < fila.size(); i++) {
            sb.append((i + 1)).append(". ").append(fila.get(i)).append("\n");
        }
        return sb.toString();
    }
}

class PilhaReimpressao {
    private List<Documento> pilha;

    public PilhaReimpressao() {
        this.pilha = new ArrayList<>();
    }

    public void empilhar(Documento doc) {
        pilha.add(0, doc); 
        JOptionPane.showMessageDialog(null, "Documento '" + doc.getArquivo() + "' adicionado à pilha de reimpressão.");
    }

    public Documento desempilhar() {
        if (!pilha.isEmpty()) {
            return pilha.remove(0);
        }
        JOptionPane.showMessageDialog(null, "Pilha de reimpressão vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
        throw new RuntimeException("Pilha de reimpressão vazia.");
    }

    public int consultarPosicao(String nomeArquivo) {
        for (int i = 0; i < pilha.size(); i++) {
            if (pilha.get(i).getArquivo().equalsIgnoreCase(nomeArquivo)) {
                return i + 1; // Posição do topo para a base
            }
        }
        return -1;
    }

    public long calcularTempoEspera(String nomeArquivo) {
        // Para pilha, o tempo de espera não é usual como na fila,
        // mas podemos calcular o tempo desde a solicitação.
        for (Documento doc : pilha) {
            if (doc.getArquivo().equalsIgnoreCase(nomeArquivo)) {
                return java.time.Duration.between(doc.getHorarioSocilitado(), LocalDateTime.now()).toMinutes();
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (pilha.isEmpty()) {
            return "Pilha de reimpressão vazia.";
        }
        StringBuilder sb = new StringBuilder("Estado da Pilha (Topo -> Base):\n");
        for (int i = 0; i < pilha.size(); i++) {
            sb.append((i + 1)).append(". ").append(pilha.get(i)).append("\n");
        }
        return sb.toString();
    }
}
// --- Fim das classes de exemplo ---

public class SimulacaoImpressora {
    public static void main(String[] args) {
        FilaImpressao filaImpressao = new FilaImpressao(5);
        PilhaReimpressao pilhaReimpressao = new PilhaReimpressao();

        while (true) {
            String menu = "Opções:\n"
                        + "1 - Adicionar documento à fila de impressão\n"
                        + "2 - Imprimir documento da fila\n"
                        + "3 - Consultar posição na fila\n"
                        + "4 - Calcular tempo de espera na fila\n"
                        + "5 - Adicionar documento à pilha de reimpressão\n"
                        + "6 - Reimprimir documento da pilha\n"
                        + "7 - Consultar posição na pilha de reimpressão\n"
                        + "8 - Calcular tempo de espera na pilha de reimpressão\n"
                        + "9 - Ver estado da fila de impressão\n"
                        + "10 - Ver estado da pilha de reimpressão\n"
                        + "0 - Sair";

            String escolha = JOptionPane.showInputDialog(null, menu, "Simulador de Impressora", JOptionPane.PLAIN_MESSAGE);

            if (escolha == null) { // Usuário fechou a caixa de diálogo ou clicou em Cancelar
                escolha = "0"; // Trata como sair
            }

            try {
                switch (escolha) {
                    case "1":
                        String nomeArquivoFila = JOptionPane.showInputDialog("Nome do arquivo:");
                        if (nomeArquivoFila == null || nomeArquivoFila.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nome do arquivo não pode ser vazio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        String nomeUsuarioFila = JOptionPane.showInputDialog("Nome do usuário:");
                        if (nomeUsuarioFila == null || nomeUsuarioFila.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nome do usuário não pode ser vazio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        filaImpressao.enfileirar(new Documento(nomeArquivoFila, nomeUsuarioFila));
                        break;
                    case "2":
                        Documento docImpressoFila = filaImpressao.desenfileirar();
                        LocalDateTime horarioImpressaoFila = LocalDateTime.now();
                        long tempoEsperaFila = java.time.Duration.between(docImpressoFila.getHorarioSocilitado(), horarioImpressaoFila).toSeconds();
                        DateTimeFormatter formatterImpressao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        JOptionPane.showMessageDialog(null, "Imprimindo: " + docImpressoFila + "\nàs " + horarioImpressaoFila.format(formatterImpressao) + ".\nTempo de espera: " + tempoEsperaFila + " segundos.");
                        break;
                    case "3":
                        String arquivoConsultaFila = JOptionPane.showInputDialog("Nome do arquivo para consultar na fila:");
                         if (arquivoConsultaFila == null || arquivoConsultaFila.trim().isEmpty()) break;
                        int posicaoFila = filaImpressao.consultarPosicao(arquivoConsultaFila);
                        if (posicaoFila != -1) {
                            JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaFila + "' na posição: " + posicaoFila);
                        } else {
                            JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaFila + "' não encontrado na fila.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case "4":
                        String arquivoTempoEsperaFila = JOptionPane.showInputDialog("Nome do arquivo para calcular tempo de espera na fila:");
                        if (arquivoTempoEsperaFila == null || arquivoTempoEsperaFila.trim().isEmpty()) break;
                        long tempoEsperaCalcFila = filaImpressao.calcularTempoEspera(arquivoTempoEsperaFila);
                        if (tempoEsperaCalcFila != -1) {
                            JOptionPane.showMessageDialog(null, "Tempo de espera estimado para '" + arquivoTempoEsperaFila + "': " + tempoEsperaCalcFila + " minutos.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Documento '" + arquivoTempoEsperaFila + "' não encontrado na fila.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case "5":
                        String nomeArquivoPilha = JOptionPane.showInputDialog("Nome do arquivo para reimpressão:");
                        if (nomeArquivoPilha == null || nomeArquivoPilha.trim().isEmpty()) {
                             JOptionPane.showMessageDialog(null, "Nome do arquivo não pode ser vazio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        String nomeUsuarioPilha = JOptionPane.showInputDialog("Nome do usuário:");
                         if (nomeUsuarioPilha == null || nomeUsuarioPilha.trim().isEmpty()) {
                             JOptionPane.showMessageDialog(null, "Nome do usuário não pode ser vazio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        pilhaReimpressao.empilhar(new Documento(nomeArquivoPilha, nomeUsuarioPilha));
                        break;
                    case "6":
                        Documento docReimpresso = pilhaReimpressao.desempilhar();
                        LocalDateTime horarioReimpressao = LocalDateTime.now();
                        long tempoDesdeSolicitacao = java.time.Duration.between(docReimpresso.getHorarioSocilitado(), horarioReimpressao).toSeconds();
                        DateTimeFormatter formatterReimpressao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        JOptionPane.showMessageDialog(null, "Reimprimindo: " + docReimpresso + "\nàs " + horarioReimpressao.format(formatterReimpressao) + ".\nTempo desde a solicitação: " + tempoDesdeSolicitacao + " segundos.");
                        break;
                    case "7":
                        String arquivoConsultaPilha = JOptionPane.showInputDialog("Nome do arquivo para consultar na pilha de reimpressão:");
                        if (arquivoConsultaPilha == null || arquivoConsultaPilha.trim().isEmpty()) break;
                        int posicaoPilha = pilhaReimpressao.consultarPosicao(arquivoConsultaPilha);
                        if (posicaoPilha != -1) {
                            JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaPilha + "' na posição (topo -> base): " + posicaoPilha);
                        } else {
                            JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaPilha + "' não encontrado na pilha de reimpressão.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case "8":
                        String arquivoTempoEsperaPilha = JOptionPane.showInputDialog("Nome do arquivo para calcular tempo de espera na pilha:");
                        if (arquivoTempoEsperaPilha == null || arquivoTempoEsperaPilha.trim().isEmpty()) break;
                        long tempoEsperaCalcPilha = pilhaReimpressao.calcularTempoEspera(arquivoTempoEsperaPilha);
                        if (tempoEsperaCalcPilha != -1) {
                            JOptionPane.showMessageDialog(null, "Tempo desde a solicitação para '" + arquivoTempoEsperaPilha + "': " + tempoEsperaCalcPilha + " minutos.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Documento '" + arquivoTempoEsperaPilha + "' não encontrado na pilha de reimpressão.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case "9":
                        JOptionPane.showMessageDialog(null, "Estado da fila de impressão:\n" + filaImpressao.toString(), "Estado da Fila", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "10":
                        JOptionPane.showMessageDialog(null, "Estado da pilha de reimpressão:\n" + pilhaReimpressao.toString(), "Estado da Pilha", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "0":
                        JOptionPane.showMessageDialog(null, "Saindo da simulação.");
                        return; 
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro na Execução", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}