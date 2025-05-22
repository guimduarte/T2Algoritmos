// 1
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Fila{
    //atributos
    private No primeiro;
    private No ultimo;
    private int capacidadeMaxima;
    private int tamanhoAtual;

    public Fila(int capacidadeMaxima){
        this.primeiro = null;
        this.ultimo = null;
        this.capacidadeMaxima = capacidadeMaxima;
        this.tamanhoAtual = 0;
    }

    public boolean filaVazia(){
        return primeiro == null;
    }

    public boolean filaCheia(){
        return tamanhoAtual == capacidadeMaxima;
    }


    public void enfileira(Documento documento){
        if(filaCheia()){
            System.out.println("A fila de impressão está cheia");
            return;
        }
        No novo = new No(documento);
        if (filaVazia()){
            primeiro = novo;
        }else{
            ultimo.setProximo(novo);
        }
        ultimo = novo;
        tamanhoAtual++;
        System.out.println("Documento ' " + documento.getArquivo() + " ' do usuario " + documento.getUsuario() + " foi adicionado a fila às " + documento.getHorario());
    }

    public void imprime(){
        if(filaVazia()){
            System.out.println("A fila está vazia");
            return;
        }
        No tempNo = primeiro;
        Documento documento = (Documento) tempNo.getInfo();
        primeiro = primeiro.getProximo();
        if (primeiro == null){
            ultimo = null;
        }
        tamanhoAtual--;

        LocalDateTime horarioImpressao = LocalDateTime.now();
        Duration tempoEspera = Duration.between(documento.getHorarioSocilitado(), horarioImpressao);
        long segundosEspera = tempoEspera.getSeconds();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Imprimindo o documento '" + documento.getArquivo() + "' de " + documento.getUsuario());
        System.out.println("Horário de solicitação: " + documento.getHorarioSocilitado().format(formatter));
        System.out.println("Horário de impressão:   " + horarioImpressao.format(formatter));
        System.out.println("Tempo total de espera: " + segundosEspera + " segundos.");
    }

    public void consultaDoc(String arquivoConsulta){
        if(filaVazia()){
            System.out.println("A fila está vazia");
            return;
        }
        No atual = primeiro;
        int posicao = 1;
        boolean encontrado = false;
        while (atual != null){
            Documento documento = (Documento) atual.getInfo();
            if (documento.getArquivo().equals(arquivoConsulta)){
                System.out.println("Documento " + arquivoConsulta + " encontrado na posicao " + posicao + " da fila.");
                System.out.println("horário solicitado: " + documento.getHorarioSocilitado());
                encontrado = true;
                break;
            }
            atual = atual.getProximo();
            posicao++;
        }
        if(!encontrado){
            System.out.println(arquivoConsulta + "nao foi encontrado na fila");
        }
    }

    public void verFila(){
        if(filaVazia()){
            System.out.println("Fila vazia");
            return;
        }
        System.out.println("Fila atual:");
        No atual = primeiro;
        int posicao = 1;
        while (atual != null){
            System.out.println("Posição " + posicao + ": " + atual.getInfo());
            atual = atual.getProximo();
            posicao++;
        }
        System.out.println("Total de documentos na fila: " + tamanhoAtual + "/" + capacidadeMaxima);
    }

    @Override
    public String toString(){
        if(filaVazia()) return "fila vazia";
        String s = "";
        No runner = primeiro;
        while(runner != null){
            s += runner + " -> ";
            runner = runner.getProximo();
        }
        return s;
    }
}

class No{ //so pode ter uma classe publica, por isso estava dando erro
    private Object info;
    private No proximo;

//construtor
    public No(Object info){
        this.info = info;
        proximo = null;
    }
//getter e setters
    public Object getInfo(){
        return info;
    }

    public void setInfo(Object info){
        this.info = info;
    }

    public No getProximo(){
        return proximo;
    }

    public void setProximo(No proximo){
        this.proximo = proximo;
    }

    @Override
    public String toString() {
        if (info instanceof Documento) {
            return ((Documento) info).getArquivo();
        }
        return "[" + info + "]";
    }
}