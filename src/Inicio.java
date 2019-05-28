
/**
 * Classe base de inicio.
 */
public class Inicio {

    private int inicio;

    /**
     * Construtor sem parâmetro.
     */
    public Inicio() {
        //Valor de início do arquivo -1 semelhante ao null
        this(-1);
    }

    /**
     * Construtor com parâmetro.
     *
     * @param inicio Um início.
     */
    public Inicio(int inicio) {
        this.inicio = inicio;
    }

    // Get´s e Set´s
    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getInicio() {
        return this.inicio;
    }
}
