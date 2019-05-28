
/**
 * Classe base de inicio.
 */
public class Inicio {

    private int inicio;

    /**
     * Construtor sem par�metro.
     */
    public Inicio() {
        //Valor de in�cio do arquivo -1 semelhante ao null
        this(-1);
    }

    /**
     * Construtor com par�metro.
     *
     * @param inicio Um in�cio.
     */
    public Inicio(int inicio) {
        this.inicio = inicio;
    }

    // Get�s e Set�s
    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getInicio() {
        return this.inicio;
    }
}
