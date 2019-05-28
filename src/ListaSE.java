
import javax.swing.JOptionPane;

/**
 * Realiza a manipulação encadeada do arquivo binário.
 *
 */
public class ListaSE {

    /**
     * Inicio é recuperado do arquivo de início.
     */
    /**
     * Aloca um novo registro no arquivo e retorna posição de inclusão.
     *
     * @param dado Um novo servidor no arquivo.
     * @return a posição de inserção.
     */
    public int alocar(Servidor dado) {
        //Instancia o Gerenciador de Servidor
        GerenciadorServidor gerenciador = new GerenciadorServidor();
        //Insere o novo registro no fim do arquivo
        gerenciador.inserirFimArquivo(new RegistroServidor(dado));
        //Retorna posição de inserção
        return gerenciador.getPosicaoFimArquivo();

    }

    /**
     * Atualiza um servidor em uma posição.
     *
     * @param dado Dado de um servidor.
     * @param posicao Posição do servidor a ser atualizado.
     *
     * @return Se consegui atualizar.
     */
    public boolean setDado(Servidor dado, int posicao) {
        //Instancia o Gerenciador de Servidor
        GerenciadorServidor ogerenciadorServidor = new GerenciadorServidor();
        //Retorna se conseguiu atualizar o dado
        return ogerenciadorServidor.atualizarArquivo(posicao, dado);
    }

    public Servidor getDado(int posicao) {

        if (posicao >= 0) {
            //Gerenciador do Arquivo
            GerenciadorServidor gerenciador = null;
            //Servidor a ser retornado
            Servidor dado = null;
            //Posição deve ser diferente de -1
            if (posicao != -1) {
                //Instancia o Gerenciador de Servidor
                gerenciador = new GerenciadorServidor();
                //Realiza a leitura de um servidor da posição especificada
                dado = gerenciador.leitura(posicao);
            }
            return dado;
        } else {

            return null;
        }
    }

    /**
     * Modificado o valor de início do arquivo.
     *
     * Salva o valor de dado(início) no arquivo de início.
     *
     * @param dado Valor do início.
     */
    public void setInicio(int dado) {
        //Instancia o Gerenciador de Início
        GerenciadorInicio gerente = new GerenciadorInicio();
        //Instancia um Registro de Início com o dado
        RegistroInicio registro = new RegistroInicio(new Inicio(dado));
        //Insere o início no arquivo
        gerente.inserir(registro);
    }

    /**
     * Retorna o valor de inicio do arquivo.
     *
     * @return O valor do início.
     */
    public int getInicio() {
        //Instancia o Gerenciador de Início
        GerenciadorInicio gerenciador = new GerenciadorInicio();
        //Inicio a ser retornado
        Inicio dado = gerenciador.leitura(0);
        //Retorna o valor do início
        return dado.getInicio();
    }

    /**
     * Inclusão no início.
     *
     * Incluí logicamente um registro no início.
     *
     * @param novo Um novo Servidor;
     *
     * @return Se incluiu no início o novo Servidor.
     */
    public boolean inserirInicio(Servidor novo) {
        novo.setProximo(getInicio());   //Coloca o início no próximo do novo
        int posicaoNovo = alocar(novo); // Aloca(salva no arquivo) e obtêm a posição de um novo nó no arquivo
        setInicio(posicaoNovo); // atribui o novo início
        return true;
    }

    public boolean inserir(Servidor novo) {

        int checagem = consultarMatricula(novo.getMatricula());

        Servidor atual = null;
        Servidor anterior = null;
        boolean flag = false;

        //Lista vazia
        if (checagem == -1) {

            inserirInicio(novo);
            return true;

            //Lista não vazia e valor da matrícula não encontrado na lista (não tem duplicidade)
        } else if (checagem == -2) {

            GerenciadorServidor gerenciador = new GerenciadorServidor();
            atual = getDado(getInicio());
            // Caso em que o o elemento inicial é maior que o novo
            do {

                if (novo.getMatricula() < atual.getMatricula()) {

                    if (anterior == null) {

                        inserirInicio(novo);
                        return true;

                    } else {
                        novo.setProximo(anterior.getProximo());
                        anterior.setProximo(alocar(novo));
                        gerenciador.atualizarArquivo(consultarMatricula(anterior.getMatricula()), anterior);
                        return true;
                    }

                } else {

                    if (atual.getProximo() == -1) {

                        anterior = atual;
                        anterior.setProximo(alocar(novo));
                        gerenciador.atualizarArquivo(consultarMatricula(anterior.getMatricula()), anterior);
                        return true;

                    } else {

                        anterior = atual;
                        atual = getDado(atual.getProximo());
                    }

                }
            } while (anterior.getMatricula() != -1);

        } else {

            return false;

        }

        return false;
    }

    public int consultarMatricula(int matricula) {
        boolean flag = false;
        int posicao = getInicio();
        Servidor atual = null;

        // Retorna -1 se o inicio estiver vazio
        if (posicao == -1) {
            return posicao;

        }

        do {
            atual = getDado(posicao);

            // retorna a posicao se achou a matrícula
            if (atual.getMatricula() == matricula) {

                return posicao;

            } else {

                if (atual.getProximo() >= 0) {

                    //passa para o próximo se não achou a matrícula (se houver próximo)
                    posicao = atual.getProximo();

                } else {

                    //retorna -2 se não encontrou a matrícula no arquivo
                    return -2;

                }
            }

        } while (atual.getProximo() >= 0);

        return posicao;

    }

    public int consultarNome(String nome) {
        boolean flag = false;
        int posicao = getInicio();
        Servidor atual = null;

        // Retorna -1 se o inicio estiver vazio
        if (posicao == -1) {
            return posicao;

        }

        do {
            atual = getDado(posicao);

            // retorna a posicao se achou a matrícula
            if (atual.getNome().equalsIgnoreCase(nome)) {

                return posicao;

            } else {

                if (atual.getProximo() >= 0) {

                    //passa para o próximo se não achou a matrícula (se houver próximo)
                    posicao = atual.getProximo();

                } else {

                    //retorna -2 se não encontrou a matrícula no arquivo
                    return -2;

                }
            }

        } while (atual.getProximo() >= 0);

        return posicao;

    }

    public boolean excluir(int posicao) {

        Servidor atual = getDado(getInicio());
        Servidor anterior;
        GerenciadorServidor gerenciador = new GerenciadorServidor();

        int posicaoauxiliar = getInicio();

        if (posicao == getInicio()) {

            setInicio(atual.getProximo());
            return true;

        } else {

            while (atual.getProximo() != -1) {

                if (atual.getProximo() != posicao) {
                    posicaoauxiliar = atual.getProximo();
                    atual = getDado(atual.getProximo());
                } else {

                    anterior = atual;
                    atual = getDado(atual.getProximo());

                    anterior.setProximo(atual.getProximo());

                    gerenciador.atualizarArquivo(posicaoauxiliar, anterior);
                    return true;

                }

            }

        }

        return false;

    }

    /**
     * Lista lógicamente o arquivo usando o próximo.
     *
     * @return Uma String com os dados do arquivo.
     */
    public String listarLogico() {
        //String de retorno
        String linha = "";
        if (getInicio() != -1) {
            //Pega o Servidor atual a partir do início
            Servidor atual = getDado(getInicio());
            linha = "Início = " + getInicio() + "\n--------------------------------\n";
            while (atual != null) {
                //Concatena os dados do registro
                linha = linha + atual.toString() + "\n";
                //Pega o próximo atual
                atual = getDado(atual.getProximo());
            }
        }
        return linha;
    }

    /**
     * Lista fisicamente o arquivo do início até o fim.
     *
     * @return Uma String com os dados do arquivo.
     */
    public String listarFisico() {
        //String de retorno
        String linha = "";
        //Instancia o Gerenciador de Servidor
        GerenciadorServidor gerente = new GerenciadorServidor();
        linha = gerente.listarFisico();
        return linha;
    }

    /**
     * Recupera as informações dos arquivos.
     *
     * @return Uma string com os dados do arquivo.
     */
    public String informacoes() {
        //String de retorno
        String linha = "";
        //Instancia o Gerenciador de Servidor
        GerenciadorServidor servidor = new GerenciadorServidor();
        linha = "Arquivo Servidor = " + servidor.informacoes();

        //Instancia o Gerenciador de Início
        GerenciadorInicio inicio = new GerenciadorInicio();
        linha = linha + "\nArquivo Início = " + inicio.informacoes();

        return linha;
    }

    /**
     * Apaga os dados dos arquivos.
     *
     * @return Se conseguiu esvaziar os arquivos.
     */
    public boolean zeraArquivo() {
        //Instancia o Gerenciador de Servidor
        GerenciadorServidor servidor = new GerenciadorServidor();
        servidor.zeraArquivo();

        //Instancia o Gerenciador de Início
        GerenciadorInicio inicio = new GerenciadorInicio();
        inicio.zeraArquivo();

        return true;
    }
}
