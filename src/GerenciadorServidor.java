
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

public class GerenciadorServidor {

    private String nomeArquivo;
    private RandomAccessFile arquivo;

    /**
     * Construtor sem parâmetro.
     *
     * Abre o arquivo com o nome default ao inicializar a classe.
     */
    public GerenciadorServidor() {
        setNomeArquivo("SERVIDOR.DAT");
        abrirArquivo();
    }

    /**
     * Construtor com parâmetro.
     *
     * Abre o arquivo com o nome especificado ao inicializar a classe.
     */
    public GerenciadorServidor(String nomeArquivo) {
        setNomeArquivo(nomeArquivo);
        abrirArquivo();
    }

    /**
     * Destrutor da classe. Fecha o arquivo.
     */
    public void finalize() {
        fecharArquivo();
    }

    // Get´s e Set´s
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public RandomAccessFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(RandomAccessFile arquivo) {
        this.arquivo = arquivo;
    }

    /**
     * Abre o arquivo com o nome especificado.
     */
    public void abrirArquivo() {
        try {
            //Cria a referência externa ao objeto fileArquivo
            File fileArquivo = new File(getNomeArquivo());
            //Abre o arquivo para leitura e escrita
            arquivo = new RandomAccessFile(fileArquivo, "rw");
        } catch (IOException io) {
            System.out.println("Problemas ao mmanipular o arquivo: " + io);
        }
    }

    /**
     * Fecha a referência ao arquivo.
     */
    public void fecharArquivo() {
        try {
            //Fecha o arquivo
            arquivo.close();
        } catch (IOException io) {
            System.out.println("Problemas ao manipular o arquivo: " + io);
        }
    }

    /**
     * Inclui um registro do arquivo.
     *
     * @param registro Registro de servidor a ser adicionado
     * @return Retorna verdadeiro ou falso se conseguiu realizar a inclusão.
     */
    public boolean inserirFimArquivo(RegistroServidor registro) {
        try {
            //Posiciona o ponteiro de gravação no final do arquivo
            arquivo.seek(arquivo.length());
            //Escreve o registro no arquivo
            registro.escrita(arquivo);
            return true;
        } catch (IOException io) {
            System.out.println("Problemas ao mmanipular o arquivo: " + io);
        }
        return false;
    }

    /**
     * Retorna a quantidade de registros.
     *
     * @return Um número inteiro com a quantidade de registros.
     */
    public int getQuantidadeRegistro() {
        int contador = 0;
        try {
            //Instancia um registro para armazenar os dados lido do arquivo.
            RegistroServidor registro = new RegistroServidor();
            //Posiciono no início do arquivo.
            arquivo.seek(0);
            //Enquanto o ponteiro de leitura for menor que o tamanho do arquivo.
            while (getArquivo().getFilePointer() < getArquivo().length()) {
                //Realiza a leitura de um registro do arquivo
                registro.leitura(arquivo);
                //Incrementa o contador de registro.
                contador = contador + 1;
            }
        } catch (IOException io) {
            System.out.println("Problemas ao mmanipular o arquivo: " + io);
        }
        return contador;
    }

    /**
     * Recupera as informações do arquivo.
     *
     * @return Uma string com os dados do arquivo.
     */
    public String informacoes() {
        String informacoes = "";
        try {
            //Concatena as informações do arquivo
            informacoes = "Tamanho do Arquivo : " + arquivo.length() + " kb " + "\n Número de Registros: " + getQuantidadeRegistro();
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Erro ao manipular o arquivo" + io);
        }
        return informacoes;
    }

    /**
     * Atualiza um registro no arquivo.
     *
     * @param chave Chave do registro a ser atualizado.
     * @param servidor Um servidor com os novos dados.
     * @return Retorna verdadeiro ou falso se conseguiu atualizar o registro.
     */
    public boolean atualizarArquivo(int posicao, Servidor servidor) {
        try {

            arquivo.seek(posicao * RegistroServidor.getTamanhoRegistro());

            RegistroServidor registro = new RegistroServidor(servidor);

            registro.escrita(arquivo);

            return true;

        } catch (IOException io) {
            System.out.println("Problemas ao mmanipular o arquivo: " + io);
        }
        return false;
    }

    /**
     * Leitura de um registro de uma posição..
     *
     * @param posicao Posição do registro a ser lido.
     *
     * @return Retorna Um servidor lido do arquivo.
     */
    public Servidor leitura(int posicao) {
        RegistroServidor registro = null;
        try {
            //Posiciona o arquivo no posição a ser alterado
            arquivo.seek(posicao * RegistroServidor.getTamanhoRegistro());
            //Instancia o registro de cliente
            registro = new RegistroServidor();
            //Leo o registro no arquivo
            registro.leitura(arquivo);
        } catch (IOException io) {
            System.out.println("Problemas ao manipular o arquivo: " + io);
        }
        //Retorna o cliente do registro
        return (Servidor) registro;
    }

    /**
     * Realiza o retorno dos dados do arquivo. Retorna todos os dados do arquivo
     * inclusive os excluídos.
     *
     * @return Uma String com os dados do arquivo.
     */
    public String listarFisico() {
        //Variável para concatenar os dados
        String linha = "";
        //Instancia um registro para armazenar os dados lido do arquivo.
        RegistroServidor registro = new RegistroServidor();
        try {
            arquivo.seek(0);
            //Enquanto o ponteiro de leitura for menor que o tamanho do arquivo
            while (getArquivo().getFilePointer() < getArquivo().length()) {
                //Realiza a leitura de um registro do arquivo
                registro.leitura(arquivo);
                //Concatena os dados do registro
                linha = linha + registro.toString() + "\n";
            }
        } catch (EOFException eof) {
            System.out.println("Chegou ao final do arquivo: " + eof);
        } catch (IOException io) {
            System.out.println("Problemas ao manipular o arquivo: " + io);
        }
        return linha;
    }

    public boolean zeraArquivo() {
        try {
            //Seta o tamanho do arquivo em 0.
            arquivo.setLength(0);
            return true;
        } catch (IOException io) {
            System.out.println("Problemas ao manipular o arquivo: " + io);
            return false;
        }
    }

    public long getTamanhoArquivo() {
        long tamanho = 0;
        try {
            tamanho = getArquivo().length();
        } catch (IOException io) {
            System.out.println("Problemas ao manipular o arquivo: " + io);
        }
        return tamanho;
    }

    /**
     * Retorna a posição do registro do fim do arquivo.
     *
     * @return Um inteiro com a posição do registro do fim do arquivo.
     */
    public int getPosicaoFimArquivo() {
        int posicao = 0;
        try {
            //retorna a quantidade registros
            posicao = (int) (arquivo.length() / RegistroServidor.getTamanhoRegistro());
        } catch (IOException io) {
            System.out.println("Problemas ao manipular o arquivo: " + io);
        }
        //Desconta 1 pois o arquivo começa em 0
        return posicao - 1;
    }

}
