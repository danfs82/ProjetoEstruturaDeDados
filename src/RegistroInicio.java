
import java.io.*;

/**
 * Classe que realiza as operações de salvamento dos atributos de início.
 *
 */
public class RegistroInicio extends Inicio {

    /**
     * Construtor sem parâmetros.
     */
    public RegistroInicio() {
        super(-1);
    }

    /**
     * Construtor com parâmetros.
     *
     * @param inicio Início do encadeamento.
     */
    public RegistroInicio(int inicio) {
        super(inicio);
    }

    /**
     * Construtor com parâmetros.
     *
     * @param inicio Um início
     */
    public RegistroInicio(Inicio inicio) {
        super(inicio.getInicio());
    }

    /**
     * Realiza a leitura dos dados do arquivo especificado.
     *
     * Preenche os atributos de inicio utilizado o arquivo especificado.
     *
     * @param arquivo Referência ao arquivo com os dados do início.
     * @throws IOException
     */
    public void leitura(RandomAccessFile arquivo) throws IOException {
        setInicio(arquivo.readInt());
    }

    /**
     * Escreve os dados no arquivo especificado.
     *
     * Recupera os dados do servidor e escreve no arquivo especificado.
     *
     * @param arquivo Arquivo a ser gravado os dados.
     * @throws IOException
     */
    public void escrita(RandomAccessFile arquivo) throws IOException {
        arquivo.writeInt(getInicio());
    }

    /**
     * Retorna o tamanho do registro de cliente.
     *
     * @return Um inteiro com o tamanho do registro do cliente.
     */
    public static int getTamanhoRegistro() {
        // início int = 4 bytes
        return 4;
    }
}
