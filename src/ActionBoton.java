import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrÃ¡ que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener {
	// Atributos
	VentanaPrincipal ventana;
	int i;
	int j;

	// Constructores.
	public ActionBoton() {

	}

	/**
	 * 
	 * @param ventana
	 *            Referencia a VentanaPrincipal(se usa para acceder a controlJuego y
	 *            por tanto, al tablero (matriz)).
	 * @param i
	 *            fila en la matriz del tablero
	 * @param j
	 *            Columna en la matriz del tablero.
	 */
	public ActionBoton(VentanaPrincipal ventana, int i, int j) {
		this.ventana = ventana;
		this.i = i;
		this.j = j;
	}

	/**
	 * AcciÃ³n que ocurrirÃ¡ cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (ventana.getJuego().abrirCasilla(i, j)) { // Si al abrir no hay una mina...

			if (!ventana.getJuego().esFinJuego()) { // Si cuando ha abierto la casilla, no ha ganado...

				ventana.mostrarNumMinasAlrededor(i, j); // Abro esa casilla y pinto las minas que rodean esa casilla.
			} else { // Si ha abierto la última casilla...
				ventana.mostrarNumMinasAlrededor(i, j); // Abro esa casilla
				ventana.mostrarFinJuego(true); // Muestro al usuario que ha ganado.
			}

		} else { // El usuario abrió una mina, se informa de que ha perdido la partida.
			ventana.mostrarFinJuego(false);
		}
	}

}
