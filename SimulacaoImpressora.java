import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Arquivo: " + arquivo + ", Usuário: " + usuario + ", Solicitado em: " + horarioSocilitado.format(formatter);
    }
}

public class SimulacaoImpressora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FilaImpressao filaImpressao = new FilaImpressao(5);
        PilhaReimpressao pilhaReimpressao = new PilhaReimpressao(); // A capacidade da pilha pode ser definida no construtor, se necessário

        while (true) {
            System.out.println("\nOpções:");
            System.out.println("1 - Adicionar documento à fila de impressão");
            System.out.println("2 - Imprimir documento da fila");
            System.out.println("3 - Consultar posição na fila");
            System.out.println("4 - Calcular tempo de espera na fila");
            System.out.println("5 - Adicionar documento à pilha de reimpressão");
            System.out.println("6 - Reimprimir documento da pilha");
            System.out.println("7 - Consultar posição na pilha de reimpressão");
            System.out.println("8 - Calcular tempo de espera na pilha de reimpressão");
            System.out.println("9 - Ver estado da fila de impressão");
            System.out.println("10 - Ver estado da pilha de reimpressão");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String escolha = scanner.nextLine();

            try {
                switch (escolha) {
                    case "1":
                        System.out.print("Nome do arquivo: ");
                        String nomeArquivoFila = scanner.nextLine();
                        System.out.print("Nome do usuário: ");
                        String nomeUsuarioFila = scanner.nextLine();
                        filaImpressao.enfileirar(new Documento(nomeArquivoFila, nomeUsuarioFila));
                        break;
                    case "2":
                        Documento docImpressoFila = filaImpressao.desenfileirar();
                        LocalDateTime horarioImpressaoFila = LocalDateTime.now();
                        long tempoEsperaFila = java.time.Duration.between(docImpressoFila.getHorarioSocilitado(), horarioImpressaoFila).toSeconds();
                        DateTimeFormatter formatterImpressao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        System.out.println("Imprimindo: " + docImpressoFila + " às " + horarioImpressaoFila.format(formatterImpressao) + ". Tempo de espera: " + tempoEsperaFila + " segundos.");
                        break;
                    case "3":
                        System.out.print("Nome do arquivo para consultar na fila: ");
                        String arquivoConsultaFila = scanner.nextLine();
                        int posicaoFila = filaImpressao.consultarPosicao(arquivoConsultaFila);
                        if (posicaoFila != -1) {
                            System.out.println("Documento '" + arquivoConsultaFila + "' na posição: " + posicaoFila);
                        } else {
                            System.out.println("Documento '" + arquivoConsultaFila + "' não encontrado na fila.");
                        }
                        break;
                    case "4":
                        System.out.print("Nome do arquivo para calcular tempo de espera na fila: ");
                        String arquivoTempoEsperaFila = scanner.nextLine();
                        long tempoEsperaCalcFila = filaImpressao.calcularTempoEspera(arquivoTempoEsperaFila);
                        if (tempoEsperaCalcFila != -1) {
                            System.out.println("Tempo de espera estimado para '" + arquivoTempoEsperaFila + "': " + tempoEsperaCalcFila + " minutos.");
                        } else {
                            System.out.println("Documento '" + arquivoTempoEsperaFila + "' não encontrado na fila.");
                        }
                        break;
                    case "5":
                        System.out.print("Nome do arquivo para reimpressão: ");
                        String nomeArquivoPilha = scanner.nextLine();
                        System.out.print("Nome do usuário: ");
                        String nomeUsuarioPilha = scanner.nextLine();
                        pilhaReimpressao.empilhar(new Documento(nomeArquivoPilha, nomeUsuarioPilha));
                        break;
                    case "6":
                        Documento docReimpresso = pilhaReimpressao.desempilhar();
                        LocalDateTime horarioReimpressao = LocalDateTime.now();
                        long tempoDesdeSolicitacao = java.time.Duration.between(docReimpresso.getHorarioSocilitado(), horarioReimpressao).toSeconds();
                        DateTimeFormatter formatterReimpressao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        System.out.println("Reimprimindo: " + docReimpresso + " às " + horarioReimpressao.format(formatterReimpressao) + ". Tempo desde a solicitação: " + tempoDesdeSolicitacao + " segundos.");
                        break;
                    case "7":
                        System.out.print("Nome do arquivo para consultar na pilha de reimpressão: ");
                        String arquivoConsultaPilha = scanner.nextLine();
                        int posicaoPilha = pilhaReimpressao.consultarPosicao(arquivoConsultaPilha);
                        if (posicaoPilha != -1) {
                            System.out.println("Documento '" + arquivoConsultaPilha + "' na posição (topo -> base): " + posicaoPilha);
                        } else {
                            System.out.println("Documento '" + arquivoConsultaPilha + "' não encontrado na pilha de reimpressão.");
                        }
                        break;
                    case "8":
                        System.out.print("Nome do arquivo para calcular tempo de espera na pilha: ");
                        String arquivoTempoEsperaPilha = scanner.nextLine();
                        long tempoEsperaCalcPilha = pilhaReimpressao.calcularTempoEspera(arquivoTempoEsperaPilha);
                        if (tempoEsperaCalcPilha != -1) {
                            System.out.println("Tempo desde a solicitação para '" + arquivoTempoEsperaPilha + "': " + tempoEsperaCalcPilha + " minutos.");
                        } else {
                            System.out.println("Documento '" + arquivoTempoEsperaPilha + "' não encontrado na pilha de reimpressão.");
                        }
                        break;
                    case "9":
                        System.out.println("Estado da fila de impressão:\n" + filaImpressao);
                        break;
                    case "10":
                        System.out.println("Estado da pilha de reimpressão:\n" + pilhaReimpressao);
                        break;
                    case "0":
                        System.out.println("Saindo da simulação.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}