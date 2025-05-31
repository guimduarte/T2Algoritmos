import javax.swing.JOptionPane;
import java.time.LocalDateTime;
//LocalDateTime ao inves do date, pois é mais novo, conciso e imutavel.
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulacaoImpressora {
    public static void main(String[] args) {
        //Arrumar capacidade caso precise.
        FilaImpressao filaImpressao = new FilaImpressao(6);
        PilhaReimpressao pilhaReimpressao = new PilhaReimpressao(3);
        DateTimeFormatter timeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String nomeArquivoDeEntrada = "entrada.txt";
        try (Scanner leitorArquivo = new Scanner(new File(nomeArquivoDeEntrada))) {
            System.out.println("Lendo documentos do arquivo: " + nomeArquivoDeEntrada);
            int documentosLidos = 0;
            int documentosAdicionados = 0;

            while (leitorArquivo.hasNextLine()) {
                String linha = leitorArquivo.nextLine();
                documentosLidos++;
                String[] partes = linha.split(",");

                if (partes.length == 2) {
                    String nomeArquivo = partes[0].trim();
                    String nomeUsuario = partes[1].trim();

                    if (nomeArquivo.isEmpty() || nomeUsuario.isEmpty()) {
                        System.err.println("AVISO: Linha ignorada (campos vazios): \"" + linha + "\" no arquivo " + nomeArquivoDeEntrada);
                        continue;
                    }

                    if (!filaImpressao.estaCheia()) {
                        filaImpressao.enfileirar(new Documento(nomeArquivo, nomeUsuario));
                        System.out.println("Do arquivo -> Adicionado à fila: " + nomeArquivo + " (Usuário: " + nomeUsuario + ")");
                        documentosAdicionados++;
                    } else {
                        System.err.println("AVISO: Fila de impressão cheia. Não foi possível adicionar o documento do arquivo: " + nomeArquivo);
                    }
                } else {
                    System.err.println("AVISO: Linha mal formatada ignorada: \"" + linha + "\" no arquivo " + nomeArquivoDeEntrada + " (Esperado: nomeArquivo,nomeUsuario)");
                }
            }
            String resumoLeitura = String.format("Leitura de '%s' concluída.\nLidas %d linhas.\nAdicionados %d documentos à fila de impressão.",
                                                nomeArquivoDeEntrada, documentosLidos, documentosAdicionados);
            JOptionPane.showMessageDialog(null, resumoLeitura, "Carga Inicial de Arquivos", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Arquivo de entrada '" + nomeArquivoDeEntrada + "' não encontrado.\nO programa iniciará com as filas vazias.",
                    "Aviso de Arquivo", JOptionPane.WARNING_MESSAGE);
            System.err.println("Arquivo de entrada não encontrado: " + nomeArquivoDeEntrada);
        }

        while (true) {
            String menuPrincipalStr = "Menu Principal:\n"
                    + "1 - Gerenciar Fila de Impressão\n"
                    + "2 - Gerenciar Pilha de Reimpressão\n"
                    + "3 - Consultar Estado Fila ou Pilha\n"
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
                                + "4 - Consultar horário de solicitação\n" 
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
                                JOptionPane.showMessageDialog(null, "Documento '" + nomeArquivoFila + "' adicionado à fila.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "2":
                                if (filaImpressao.estaVazia()) {
                                    JOptionPane.showMessageDialog(null, "Fila de impressão vazia.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                                Documento docImpressoFila = filaImpressao.desenfileirar();
                                LocalDateTime horarioSolicitacaoFila = docImpressoFila.getHorarioSocilitado();
                                LocalDateTime horarioAtendimentoFila = LocalDateTime.now();
                                long tempoEsperaFilaSegundos = java.time.Duration
                                        .between(horarioSolicitacaoFila, horarioAtendimentoFila)
                                        .toSeconds();

                                String solicitadoStrFila = horarioSolicitacaoFila.format(timeOnlyFormatter);
                                String atendidoStrFila = horarioAtendimentoFila.format(timeOnlyFormatter);

                                String mensagemFila = String.format("%s por %s - Solicitado: %s - Atendido: %s - Espera: %ds",
                                        docImpressoFila.getArquivo(),
                                        docImpressoFila.getUsuario(),
                                        solicitadoStrFila,
                                        atendidoStrFila,
                                        tempoEsperaFilaSegundos);
                                JOptionPane.showMessageDialog(null, mensagemFila, "Documento Impresso", JOptionPane.INFORMATION_MESSAGE);
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
                                String arquivoConsultaHorarioFila = JOptionPane.showInputDialog("Nome do arquivo para consultar horário de solicitação na fila:");
                                if (arquivoConsultaHorarioFila == null || arquivoConsultaHorarioFila.trim().isEmpty()) break;
                                
                                String horarioFila = filaImpressao.getHorarioSolicitacaoPorNome(arquivoConsultaHorarioFila);
                                
                                if (horarioFila != null) {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaHorarioFila + "' foi solicitado em: " + horarioFila);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaHorarioFila + "' não encontrado na fila.", "Informação", JOptionPane.INFORMATION_MESSAGE);
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
                                + "4 - Consultar horário de solicitação\n" 
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
                                JOptionPane.showMessageDialog(null, "Documento '" + nomeArquivoPilha + "' adicionado à pilha de reimpressão.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "2":
                                if (pilhaReimpressao.estaVazia()) {
                                    JOptionPane.showMessageDialog(null, "Pilha de reimpressão vazia.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                                Documento docReimpresso = pilhaReimpressao.desempilhar();
                                LocalDateTime horarioSolicitacaoPilha = docReimpresso.getHorarioSocilitado();
                                LocalDateTime horarioAtendimentoPilha = LocalDateTime.now();
                                long tempoEsperaPilhaSegundos = java.time.Duration
                                        .between(horarioSolicitacaoPilha, horarioAtendimentoPilha)
                                        .toSeconds();

                                String solicitadoStrPilha = horarioSolicitacaoPilha.format(timeOnlyFormatter);
                                String atendidoStrPilha = horarioAtendimentoPilha.format(timeOnlyFormatter);

                                String mensagemPilha = String.format("%s por %s - Solicitado: %s - Atendido: %s - Espera: %ds",
                                        docReimpresso.getArquivo(),
                                        docReimpresso.getUsuario(),
                                        solicitadoStrPilha,
                                        atendidoStrPilha,
                                        tempoEsperaPilhaSegundos);
                                JOptionPane.showMessageDialog(null, mensagemPilha, "Documento Reimpresso", JOptionPane.INFORMATION_MESSAGE);
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
                                String arquivoConsultaHorarioPilha = JOptionPane.showInputDialog("Nome do arquivo para consultar horário de solicitação na pilha:");
                                if (arquivoConsultaHorarioPilha == null || arquivoConsultaHorarioPilha.trim().isEmpty()) break;

                                String horarioPilha = pilhaReimpressao.getHorarioSolicitacaoPorNome(arquivoConsultaHorarioPilha);
                                
                                if (horarioPilha != null) {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaHorarioPilha + "' foi solicitado para reimpressão em: " + horarioPilha);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Documento '" + arquivoConsultaHorarioPilha + "' não encontrado na pilha.", "Informação", JOptionPane.INFORMATION_MESSAGE);
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