import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulacaoImpressora {
    public static void main(String[] args) {
        FilaImpressao filaImpressao = new FilaImpressao(10);
        PilhaReimpressao pilhaReimpressao = new PilhaReimpressao();

        while (true) {
            String menuPrincipalStr = "Menu Principal:\n"
                    + "1 - Gerenciar Fila de Impressão\n"
                    + "2 - Gerenciar Pilha de Reimpressão\n"
                    + "3 - Consultar Estados\n"
                    + "0 - Sair";

            String escolhaPrincipal = JOptionPane.showInputDialog(null, menuPrincipalStr, "Simulador de Impressora",
                    JOptionPane.PLAIN_MESSAGE);

            if (escolhaPrincipal == null) { 
                escolhaPrincipal = "0"; 
            }

            try {
                switch (escolhaPrincipal) {
                    case "1":
                        String menuFilaStr = "Menu Fila de Impressão:\n"
                                + "1 - Adicionar documento à fila\n"
                                + "2 - Imprimir próximo documento da fila\n"
                                + "3 - Consultar posição na fila\n"
                                + "4 - Calcular tempo de espera na fila\n"
                                + "0 - Voltar ao Menu Principal";
                        String escolhaFila = JOptionPane.showInputDialog(null, menuFilaStr, "Fila de Impressão", JOptionPane.PLAIN_MESSAGE);

                        if (escolhaFila == null) escolhaFila = "0"; 
                        switch (escolhaFila) {
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
                                if (filaImpressao.estaVazia()) {
                                    JOptionPane.showMessageDialog(null, "Fila de impressão vazia.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                                Documento docImpressoFila = filaImpressao.desenfileirar();
                                LocalDateTime horarioImpressaoFila = LocalDateTime.now();
                                long tempoEsperaFila = java.time.Duration
                                        .between(docImpressoFila.getHorarioSocilitado(), horarioImpressaoFila)
                                        .toSeconds();
                                DateTimeFormatter formatterImpressao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                                JOptionPane.showMessageDialog(null,
                                        "Imprimindo: " + docImpressoFila + "\nàs "
                                                + horarioImpressaoFila.format(formatterImpressao)
                                                + ".\nTempo de espera: "
                                                + tempoEsperaFila + " segundos.");
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
                            case "0":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opção inválida para Fila.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        break; 

                    case "2":
                        String menuPilhaStr = "Menu Pilha de Reimpressão:\n"
                                + "1 - Adicionar documento à pilha\n"
                                + "2 - Reimprimir último documento da pilha\n"
                                + "3 - Consultar posição na pilha\n"
                                + "4 - Calcular tempo desde solicitação na pilha\n"
                                + "0 - Voltar ao Menu Principal";
                        String escolhaPilha = JOptionPane.showInputDialog(null, menuPilhaStr, "Pilha de Reimpressão", JOptionPane.PLAIN_MESSAGE);

                        if (escolhaPilha == null) escolhaPilha = "0"; 

                        switch (escolhaPilha) {
                            case "1":
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
                            case "2":
                                if (pilhaReimpressao.estaVazia()) {
                                    JOptionPane.showMessageDialog(null, "Pilha de reimpressão vazia.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                                Documento docReimpresso = pilhaReimpressao.desempilhar();
                                LocalDateTime horarioReimpressao = LocalDateTime.now();
                                long tempoDesdeSolicitacao = java.time.Duration
                                        .between(docReimpresso.getHorarioSocilitado(), horarioReimpressao).toSeconds();
                                DateTimeFormatter formatterReimpressao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                                JOptionPane.showMessageDialog(null,
                                        "Reimprimindo: " + docReimpresso + "\nàs "
                                                + horarioReimpressao.format(formatterReimpressao)
                                                + ".\nTempo desde a solicitação: " + tempoDesdeSolicitacao + " segundos.");
                                break;
                            case "3":
                                String arquivoConsultaPilha = JOptionPane.showInputDialog("Nome do arquivo para consultar na pilha de reimpressão:");
                                if (arquivoConsultaPilha == null || arquivoConsultaPilha.trim().isEmpty()) break;
                                int posicaoPilha = pilhaReimpressao.consultarPosicao(arquivoConsultaPilha);
                                if (posicaoPilha != -1) {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaPilha + "' na posição (topo -> base): " + posicaoPilha);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaPilha + "' não encontrado na pilha.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                                }
                                break;
                            case "4":
                                String arquivoTempoEsperaPilha = JOptionPane.showInputDialog("Nome do arquivo para calcular tempo na pilha:");
                                if (arquivoTempoEsperaPilha == null || arquivoTempoEsperaPilha.trim().isEmpty()) break;
                                long tempoEsperaCalcPilha = pilhaReimpressao.calcularTempoEspera(arquivoTempoEsperaPilha); 
                                if (tempoEsperaCalcPilha != -1) {
                                    JOptionPane.showMessageDialog(null, "Tempo estimado para '" + arquivoTempoEsperaPilha + "': " + tempoEsperaCalcPilha + " minutos (exemplo).");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoTempoEsperaPilha + "' não encontrado na pilha.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                                }
                                break;
                            case "0":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opção inválida para Pilha.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    case "3": 
                        String menuConsultaStr = "Menu Consultas:\n"
                                + "1 - Ver estado da fila de impressão\n"
                                + "2 - Ver estado da pilha de reimpressão\n"
                                + "0 - Voltar ao Menu Principal";
                        String escolhaConsulta = JOptionPane.showInputDialog(null, menuConsultaStr, "Consultas", JOptionPane.PLAIN_MESSAGE);

                        if (escolhaConsulta == null) escolhaConsulta = "0"; 

                        switch (escolhaConsulta) {
                            case "1":
                                JOptionPane.showMessageDialog(null, "Estado da fila de impressão:\n" + filaImpressao.toString(), "Estado da Fila", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "2":
                                JOptionPane.showMessageDialog(null, "Estado da pilha de reimpressão:\n" + pilhaReimpressao.toString(), "Estado da Pilha", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "0":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opção inválida para Consulta.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        break; 
                    case "0":
                        JOptionPane.showMessageDialog(null, "Saindo da simulação.");
                        return; 

                    default:
                        JOptionPane.showMessageDialog(null, "Opção principal inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro na Execução", JOptionPane.ERROR_MESSAGE);
            }
        } 
    } 
}