
/**
 * Programa que armazena um registro de tamanho fixo em um arquivo bin�rio.
 */
import javax.swing.JOptionPane;

public class Principal {

    public static RegistroServidor leitura(String mensagem, GerenciadorServidor gerente, ListaSE lista, int matricula) {
        //Regiatra o servidor p�blico, fazendo checagem de duplicidade

        String auxiliarString;
        int auxiliarInt;
        double auxiliarDouble;

        if (!mensagem.equals("")) {
            JOptionPane.showMessageDialog(null, mensagem);
        }

        RegistroServidor servidor = new RegistroServidor();
        //Preenche o servidor com os dados lidos

        boolean flag;

        if (matricula == -1) {
            auxiliarInt = (int) getNumero("Digite a Matr�cula (at� 6 d�gitos "
                    + "num�ricos)", "Digite n�mero v�lido de matr�cula (at� 6 "
                    + "d�gitos num�ricos)", 1, 999999);

            if (auxiliarInt == -1) {

                return null;
            }

            Servidor servidorduplo = lista.getDado(lista.consultarMatricula(auxiliarInt));

            // Se servidor != null encontrou o registro
            if (servidorduplo != null) {
                JOptionPane.showMessageDialog(null, "Servidor j� existente no registro! \n" + servidorduplo.toString());
                servidor = null;
            }

        } else {

            auxiliarInt = matricula;

        }

        if (servidor != null) {

            servidor.setMatricula(auxiliarInt);

            auxiliarString = getTexto("Digite o Nome");

            if (auxiliarString.equals("")) {

                return null;
            }

            servidor.setNome(auxiliarString);
            auxiliarString = getTexto("Digite o Setor");

            if (auxiliarString == null) {

                return null;
            }

            servidor.setSetor(auxiliarString);
            auxiliarDouble = getNumero("Digite o Sal�rio (apenas num�rico)", "Digite valor de sal�rio v�lido (de R$ 0,00 a R$ 39.000,00)", 0, 39000);

            if (auxiliarDouble == -1) {

                return null;
            }

            servidor.setSalario(auxiliarDouble);
            auxiliarInt = (int) getNumero("Digite a Classe (1 a 13)", "Digite valor v�lido de Classe (1 a 13)", 1, 13);

            if (auxiliarInt == -1) {
                return null;
            }
            servidor.setClasse(auxiliarInt);
            servidor.setProximo(-1);
        }
        return servidor;
    }

    public static boolean eNumerico(String texto) {
        // Verifica se o texto tem valor num�rico
        try {
            double d = Double.parseDouble(texto);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static double getNumero(String prompt, String erro, double min, double max) {
        //Solicita ao usu�rio uma entrada em n�mero
        String auxiliarString;
        double auxiliarDouble;

        auxiliarDouble = -1;

        do {

            auxiliarString = JOptionPane.showInputDialog(prompt);

            if (auxiliarString == null) {
                return -1;
            }

            auxiliarString = auxiliarString.trim();

            if (!eNumerico(auxiliarString)) {

                JOptionPane.showMessageDialog(null, erro);

            } else {

                auxiliarDouble = Integer.parseInt(auxiliarString);

                if (auxiliarDouble < min || auxiliarDouble > max) {

                    JOptionPane.showMessageDialog(null, erro);
                }
            }
        } while (auxiliarString.equals("") || !eNumerico(auxiliarString) || auxiliarDouble < min || auxiliarDouble > max);

        return Double.parseDouble(auxiliarString);
    }

    public static String getTexto(String mensagem) {
        //Solicita ao usu�rio uma entrada em texto
        String auxiliarString;

        do {

            auxiliarString = JOptionPane.showInputDialog(mensagem);

            if (auxiliarString == null) {
                return "";
            }
            auxiliarString = auxiliarString.trim();

            if (auxiliarString.equals("")) {

                JOptionPane.showMessageDialog(null, "Digite um valor v�lido.");
            }
        } while (auxiliarString.equals(""));

        return auxiliarString;
    }

    public static void SubmenuAlterar(GerenciadorServidor gerente, RegistroServidor servidor, ListaSE lista) {

        String auxiliarString;
        int auxiliarInt;

        int opcao = -1;
        do {
            auxiliarString = JOptionPane.showInputDialog(
                    " 1 - Alterar por matr�cula\n "
                    + "2 - Alterar por nome\n "
                    + "3 - Voltar\n"
                    + "Digite uma Op��o:");
            if (auxiliarString == null) {

                opcao = 3;

            } else if (!eNumerico(auxiliarString)) {

                opcao = -1;
            } else {

                opcao = Integer.parseInt(auxiliarString);
            }
            switch (opcao) {

                case 1: {
                    //Pergunta qual a matr�cula a ser atualizada

                    auxiliarInt = (int) getNumero("Digite a matr�cula do servidor a ser alterado: ", "Digite valor v�lido de matr�cula", 1, 1000000);

                    if (auxiliarInt != -1) {

                        int posicao = lista.consultarMatricula(auxiliarInt);

                        servidor = (RegistroServidor) lista.getDado(posicao);

                        int proximo = servidor.getProximo();

                        if (servidor != null) {

                            JOptionPane.showMessageDialog(null, "Achei o servidor \n" + servidor.toString());

                            servidor = leitura("Digite os novos dados do servidor", gerente, lista, auxiliarInt);

                            if (servidor != null) {

                                servidor.setProximo(proximo);

                                if (gerente.atualizarArquivo(posicao, servidor) == true) {

                                    JOptionPane.showMessageDialog(null, "Registro do servidor " + auxiliarInt + " atualizado com sucesso.\n" + "Novos dados: \n" + servidor.toString());

                                } else {

                                    JOptionPane.showMessageDialog(null, "Registro " + auxiliarInt + " n�o foi atualizado.");
                                }
                            }
                        } else {

                            JOptionPane.showMessageDialog(null, "N�o achei o servidor \n");
                        }
                    }
                    break;
                }

                case 2: {
                    //Pergunta qual o nome a ser atualizado

                    auxiliarString = getTexto("Digite o nome do servidor a ser atualizado: ");

                    if (!auxiliarString.equals("")) {

                        int posicao = lista.consultarNome(auxiliarString);

                        servidor = (RegistroServidor) lista.getDado(posicao);

                        if (servidor != null) {

                            JOptionPane.showMessageDialog(null, "Achei o servidor \n" + servidor.toString());

                            auxiliarInt = servidor.getMatricula();

                            int proximo = servidor.getProximo();

                            servidor = leitura("Digite os novos dados do servidor", gerente, lista, auxiliarInt);

                            if (servidor != null) {

                                servidor.setProximo(proximo);
                                //Atualiza o arquivo servidor com os dados para a matr�cula especificada

                                if (gerente.atualizarArquivo(posicao, servidor) == true) {
                                    JOptionPane.showMessageDialog(null, "Registro do servidor " + auxiliarString + " atualizado com sucesso.\n" + "Novos dados: \n" + servidor.toString());
                                } else {
                                    JOptionPane.showMessageDialog(null, "Registro do servidor " + auxiliarString + " n�o foi atualizado.");
                                }
                            }
                        } else {

                            JOptionPane.showMessageDialog(null, "N�o achei o servidor \n");

                        }
                    }

                    break;
                }

                case 3: {

                    break;

                }

                default: {

                    JOptionPane.showMessageDialog(null, "Op��o inv�lida!.");
                    break;
                }
            }
        } while (opcao != 3);
    }

    public static void SubmenuExcluir(GerenciadorServidor gerente, RegistroServidor servidor, ListaSE lista) {

        int auxiliarInt;
        String auxiliarString;
        int opcao = -1;
        do {

            auxiliarString = JOptionPane.showInputDialog(
                    " 1 - Excluir por matr�cula\n "
                    + "2 - Excluir por nome\n "
                    + "3 - Voltar\n"
                    + "Digite uma Op��o:");

            if (auxiliarString == null) {

                opcao = 3;

            } else if (!eNumerico(auxiliarString)) {

                opcao = -1;
            } else {

                opcao = Integer.parseInt(auxiliarString);
            }
            switch (opcao) {

                case 1: {
                    auxiliarInt = (int) getNumero("Digite a matr�cula do servidor a ser exclu�do: ", "Digite valor v�lido de matr�cula", 1, 1000000);

                    if (auxiliarInt != -1) {

                        int posicao = lista.consultarMatricula(auxiliarInt);

                        if (lista.excluir(posicao)) {
                            JOptionPane.showMessageDialog(null, "Servidor exclu�do.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Registro n�o encontrado!");
                        }

                    }
                    break;
                }

                case 2: {

                    auxiliarString = getTexto("Digite o nome do servidor a ser exclu�do: ");

                    if (!auxiliarString.equals("")) {

                        int posicao = lista.consultarNome(auxiliarString);

                        if (lista.excluir(posicao)) {

                            JOptionPane.showMessageDialog(null, "Servidor exclu�do.");

                        } else {

                            JOptionPane.showMessageDialog(null, "Registro n�o encontrado!");
                        }
                    }
                    break;
                }

                case 3: {

                    break;
                }

                default: {

                    JOptionPane.showMessageDialog(null, "Op��o inv�lida!.");
                    break;
                }
            }
        } while (opcao != 3);
    }

    public static void SubmenuConsultar(GerenciadorServidor gerente, RegistroServidor servidor, ListaSE lista) {

        int auxiliarInt;
        String auxiliarString;
        int opcao = -1;
        do {
            auxiliarString = JOptionPane.showInputDialog(
                    " 1 - Consultar por matr�cula\n "
                    + "2 - Consultar por nome\n "
                    + "3 - Voltar\n"
                    + "Digite uma Op��o:");
            if (auxiliarString == null) {

                opcao = 3;

            } else if (!eNumerico(auxiliarString)) {

                opcao = -1;
            } else {

                opcao = Integer.parseInt(auxiliarString);
            }

            switch (opcao) {

                case 1: {

                    auxiliarInt = (int) getNumero("Digite a matr�cula do servidor a ser pesquisado: ", "Digite valor v�lido de matr�cula", 1, 1000000);

                    if (auxiliarInt != -1) {
                        // Procura o registro do servidor com a chave no arquivo
                        servidor = (RegistroServidor) lista.getDado(lista.consultarMatricula(auxiliarInt));
                        // Se servidor != null encontrou o registro
                        if (servidor != null) {
                            JOptionPane.showMessageDialog(null, "Achei o servidor \n" + servidor.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Servidor n�o localizado.");
                        }
                    }
                    break;
                }
                case 2: {

                    auxiliarString = getTexto("Digite o nome do servidor a ser pesquisado: ");

                    if (!auxiliarString.equals("")) {
                        // Procura o registro do servidor com a chave no arquivo
                        servidor = (RegistroServidor) lista.getDado(lista.consultarNome(auxiliarString));
                        // Se servidor != null encontrou o registro
                        if (servidor != null) {
                            JOptionPane.showMessageDialog(null, "Achei o servidor \n" + servidor.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Servidor n�o localizado.");
                        }
                    }
                    break;
                }

                case 3: {
                    break;
                }

                default: {
                    JOptionPane.showMessageDialog(null, "Op��o inv�lida!.");
                    break;
                }
            }
        } while (opcao != 3);
    }

    public static void SubmenuPropriedades(GerenciadorServidor gerente, RegistroServidor servidor, ListaSE lista) {

        int auxiliarInt;
        String auxiliarString;
        int opcao = -1;
        do {

            auxiliarString = JOptionPane.showInputDialog(
                    " 1 - Tamanho  e quantidade de registros do arquivo\n "
                    + "2 - Limpar arquivo\n"
                    + "3 - Voltar\n"
                    + "Digite uma Op��o:");
            if (auxiliarString == null) {

                opcao = 99;

            } else if (!eNumerico(auxiliarString)) {

                opcao = -1;
            } else {

                opcao = Integer.parseInt(auxiliarString);
            }

            switch (opcao) {

                case 1: {
                    //Retorna as informa��es do arquivo
                    String informacoes = gerente.informacoes();
                    JOptionPane.showMessageDialog(null, informacoes);
                    break;
                }
                case 2: {
                    //Esvazia o arquivo de dados
                    if (lista.zeraArquivo() == true) {
                        JOptionPane.showMessageDialog(null, "Arquivo zerado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Arquivo n�o foi zerado!");
                    }
                    break;
                }

                case 3: {

                    break;

                }

                default: {

                    JOptionPane.showMessageDialog(null, "Op��o inv�lida!.");
                    break;
                }
            }
        } while (opcao != 3);
    }

    public static void main(String Arg[]) {
        //Classe que gerencia o arquivo de servidor

        GerenciadorServidor gerente = new GerenciadorServidor();

        RegistroServidor servidor = new RegistroServidor();

        ListaSE lista = new ListaSE();

        String auxiliarString;
        int auxiliarInt;
        double auxiliarDouble;

        int opcao = -1;
        do {
            auxiliarString = JOptionPane.showInputDialog("\t### Gerenciador de Servidores ###\n"
                    + " 1 - Incluir \n "
                    + " 2 - Excluir\n "
                    + " 3 - Atualizar \n "
                    + " 4 - Consultar\n "
                    + " 5 - Listar L�gico\n "
                    + " 6 - Listar F�sico \n "
                    + " 7 - Propriedades \n "
                    + "99 - Sair\n"
                    + "Digite uma Op��o: ");
            if (auxiliarString == null) {

                opcao = 99;

            } else if (!eNumerico(auxiliarString)) {

                opcao = -1;
            } else {

                opcao = Integer.parseInt(auxiliarString);
            }

            switch (opcao) {
                case 1: {
                    //Chama o m�todo leitura para retornar um servidor instanciado e preenchido
                    servidor = leitura("", gerente, lista, -1);

                    if (servidor != null) {

                        if (lista.inserir(servidor)) {
                            JOptionPane.showMessageDialog(null, "Registro inserido com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Registro n�o foi inserido.");
                        }
                    }
                    break;
                }

                case 2: {

                    SubmenuExcluir(gerente, servidor, lista);
                    break;
                }

                case 3: {
                    SubmenuAlterar(gerente, servidor, lista);
                    break;
                }

                case 4: {

                    SubmenuConsultar(gerente, servidor, lista);
                    break;
                }

                case 5: {
                    //Lista logicamente os dados do arquivo. N�o inclui chave com -1
                    String saida = lista.listarLogico();
                    JOptionPane.showMessageDialog(null, "Lista L�gico:\n" + saida);
                    break;
                }

                case 6: {
                    //Lista fisicamente os dados do arquivo
                    String saida = lista.listarFisico();
                    JOptionPane.showMessageDialog(null, "Lista F�sico:\n" + saida);
                    break;
                }

                case 7: {

                    SubmenuPropriedades(gerente, servidor, lista);
                    break;
                }

                case 99: {
                    JOptionPane.showMessageDialog(null, "Saindo do Sistema!");
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Op��o inv�lida!");
                    break;
                }
            }
        } while (opcao != 99);
    }
}
