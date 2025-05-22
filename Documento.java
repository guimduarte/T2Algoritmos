// 1
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Documento {
    private String arquivo;
    private String usuario;
    private LocalDateTime horario;

    public Documento(String arquivo, String usuario){
        this.arquivo = arquivo;
        this.usuario = usuario;
        this.horario = LocalDateTime.now();
    }

    public String getArquivo(){
        return arquivo;
    }

    public String getUsuario(){
        return usuario;
    }

    public LocalDateTime getHorarioSocilitado(){
        return horario;
    }
    
    //formatar horario dia/mes/ano // hora:minuto:segundo
    public String getHorario(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return horario.format(formatter);
    }

    @Override
    public String toString() {
        return "Arquivo: " + arquivo + ", Usuário: " + usuario + ", Solicitado em: " + getHorario();
    }

}
