// 1
import java.util.Random;

public class SimulacaoImpressora {
    public static void main(String[] args) {
        Fila impressora = new Fila(5); // Capacidade máxima de 5 documentos
        Random random = new Random();
        int contadorDocumentos = 1;

        for (int i = 0; i < 10; i++) {
            if (random.nextDouble() < 0.7) { // Probabilidade de adicionar um documento
                String nomeArquivo = "documento_" + contadorDocumentos + ".txt";
                String nomeUsuario = "usuario_" + (random.nextInt(3) + 1);
                impressora.enfileira(new Documento(nomeArquivo, nomeUsuario));
                contadorDocumentos++;
            } else if (!impressora.filaVazia()) {
                impressora.imprime();
            }
            impressora.verFila();
            System.out.println("--------------------");

            if (random.nextDouble() < 0.3 && contadorDocumentos > 1) {
                String arquivoParaConsultar = "documento_" + random.nextInt(contadorDocumentos) + ".txt";
                impressora.consultaDoc(arquivoParaConsultar);
                System.out.println("--------------------");
            }

            try {
                Thread.sleep(random.nextInt(2000)); // Simula um tempo entre as operações
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nProcessando documentos restantes na fila:");
        while (!impressora.filaVazia()) {
            impressora.imprime();
        }
        impressora.verFila();
    }
}