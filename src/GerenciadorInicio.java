
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Realiza as opera��es b�sicas no arquivo de in�cio.
 *
 */
public class GerenciadorInicio {

	private String nomeArquivo;
	private RandomAccessFile arquivo;

	/**
	 * Construtor sem par�metro.
	 *
	 * Abre o arquivo com o nome default ao inicializar a classe.
	 */
	public GerenciadorInicio() {

		setNomeArquivo("INICIO.DAT");
		abrirArquivo();
	}

	/**
	 * Construtor com par�metro.
	 *
	 * Abre o arquivo com o nome especificado ao inicializar a classe.
	 */
	public GerenciadorInicio(String nomeArquivo) {
		setNomeArquivo(nomeArquivo);
		abrirArquivo();
	}

	/**
	 * Destrutor da classe. Fecha o arquivo.
	 */
	public void finalize() {
		fecharArquivo();
	}

	// Get�s e Set�s
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
	 * Abre o arquivo.
	 */
	public void abrirArquivo() {
		try {
			// Cria a refer�ncia externa ao objeto fileArquivo
			File fileArquivo = new File(getNomeArquivo());
			// Abre o arquivo para leitura e escrita
			arquivo = new RandomAccessFile(fileArquivo, "rw");
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
		}
	}

	/**
	 * Fecha a refer�ncia ao arquivo.
	 */
	public void fecharArquivo() {
		try {
			// Fecha o arquivo
			arquivo.close();
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
		}
	}

	/**
	 * Inclui um registro do arquivo.
	 *
	 * @param registro Registro de in�cio a ser adicionado
	 * @return Retorna verdadeiro ou falso se conseguiu realizar a inclus�o.
	 */
	public boolean inserir(RegistroInicio registro) {
		try {
			// Posiciona o ponteiro de grava��o no in�cio do arquivo
			arquivo.seek(0);
			// Escreve o registro no arquivo
			registro.escrita(arquivo);
			return true;
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
		}
		return false;
	}

	/**
	 * Atualiza um registro no arquivo com base na posi��o.
	 *
	 * @param posicao Posi��o do registro a ser atualizado.
	 * @param inicio  Um in�cio com os novos dados.
	 * @return Retorna verdadeiro ou falso se conseguiu atualizar o registro.
	 */
	public boolean atualizarArquivo(int posicao, Inicio inicio) {
		try {
			// Posiciona o arquivo no posi��o a ser alterado
			arquivo.seek(posicao * RegistroInicio.getTamanhoRegistro());
			// Instancia o regitro
			RegistroInicio registro = new RegistroInicio(inicio);
			// Escreve o registro no arquivo
			registro.escrita(arquivo);
			// Sucesso na atualiza��o
			return true;
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
		}
		return false;
	}

	/**
	 * Leitura de um registro de uma posi��o..
	 *
	 * @param posicao Posi��o do registro a ser lido.
	 * @return Retorna Um servidor lido do arquivo.
	 */
	public Inicio leitura(int posicao) {
		RegistroInicio registro = null;
		try {
			// Verifica se o arquivo tem algum registro
			if (getArquivo().length() > 0) {
				// Posiciona o arquivo no posi��o a ser alterado
				arquivo.seek(posicao * RegistroInicio.getTamanhoRegistro());
				// Instancia o registro de servidor
				registro = new RegistroInicio();
				// Leo o registro no arquivo
				registro.leitura(arquivo);
			} else {
				// Retorna -1 caso o arquivo esteja vazio
				registro = new RegistroInicio(new Inicio(-1));
			}
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
		}
		// Retorna o servidor do registro
		return (Inicio) registro;
	}

	/**
	 * Recupera as informa��es do arquivo.
	 *
	 * @return Uma string com os dados do arquivo.
	 */
	public String informacoes() {
		String informacoes = "";
		try {
			// Concatena as informa��es do arquivo
			informacoes = "Tamanho do Arquivo : " + getArquivo().length() + " Kb";
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
		}
		return informacoes;
	}

	/**
	 * Apaga os registro do arquivo.
	 *
	 * @return Se conseguiu esvaziar o arquivo.
	 */
	public boolean zeraArquivo() {
		try {
			// Seta o tamanho do arquivo em 0.
			arquivo.setLength(0);
			return true;
		} catch (IOException io) {
			System.out.println("Problemas ao manipular o arquivo: " + io);
			return false;
		}
	}
}
