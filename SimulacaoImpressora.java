import java.util.Random;

public class SimulacaoImpressora {
    public static void main(String[] args) {
        FilaImpressao fila = new FilaImpressao(5); 
        PilhaReimpressao pilha = new PilhaReimpressao();
        Random random = new Random();
        int contadorDocumentos = 1;

        for (int i = 0; i < 10; i++) {
            if (random.nextDouble() < 0.7) {
                String nomeArquivo = "documento_" + contadorDocumentos + ".txt";
                String nomeUsuario = "usuario_" + (random.nextInt(3) + 1);
                Documento novoDoc = new Documento(nomeArquivo, nomeUsuario);
                fila.enfileirar(novoDoc);
                contadorDocumentos++;
                System.out.println("Adicionado à fila: " + novoDoc);
            }
        
            else if (!fila.estaVazia()) {
                Documento documentoImpresso = fila.desenfileirar();
                System.out.println("IMPRIMINDO: " + documentoImpresso);

                if (random.nextDouble() < 0.2) {
                    pilha.empilhar(documentoImpresso);
                    System.out.println("FALHA! Documento enviado para reimpressão: " + documentoImpresso);
                }
            }

            if (random.nextDouble() < 0.3 && contadorDocumentos > 1) {
                String arquivoParaConsultar = "documento_" + random.nextInt(contadorDocumentos) + ".txt";
                int posicao = fila.consultarPosicao(arquivoParaConsultar);
                if (posicao > 0) {
                    System.out.println("CONSULTA: Arquivo '" + arquivoParaConsultar + "' está na posição " + posicao + " da fila.");
                } else {
                    System.out.println("CONSULTA: Arquivo '" + arquivoParaConsultar + "' não encontrado na fila.");
                }
            }

            if (!pilha.estaVazia() && random.nextDouble() < 0.4) {
                Documento reimpresso = pilha.desempilhar();
                System.out.println("REIMPRIMINDO (urgente): " + reimpresso);
            }

            System.out.println("\n--- FILA ATUAL ---\n" + fila);
            System.out.println("--- PILHA DE REIMPRESSÃO ---\n" + pilha);
            System.out.println("--------------------");

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nFINALIZANDO: Imprimindo documentos restantes...");
        while (!fila.estaVazia()) {
            Documento doc = fila.desenfileirar();
            System.out.println("IMPRIMIDO: " + doc);
        }
    }
}