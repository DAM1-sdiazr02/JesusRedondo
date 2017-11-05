import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {

	private final static int MINA = -1; // Representaci�n de las minas.
	int MINAS_INICIALES; // Minas totales (dificultad).
	final int LADO_TABLERO = 10;

	private int[][] tablero; // Matriz que forma el tablero.
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	/**
	 * Mejora extra: Pide la dificultad y controla el rango de minas y las posibles
	 * excepciones. MINAS_INICIALES ya no es final ni tiene valor inicialmente.
	 * 
	 * @return minas a rellenar en el panel.
	 */
	public int pedirDificultad() {
		try {
			int minasIniciales;
			do {
				minasIniciales = Integer.parseInt(JOptionPane.showInputDialog(null,
						"\nIntroduce el n� de minas:\n M�XIMO-->99\n M�NIMO-->1.", "Bienvenido al Buscaminas!!", 1));
			} while (minasIniciales < 1 || minasIniciales > 99);

			return minasIniciales;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "\nS�lo se aceptan n�meros...", "ERROR", 1);
			return pedirDificultad();
		}

	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {
		Random rd = new Random();

		MINAS_INICIALES = pedirDificultad(); //Pido la dificultad por teclado.

		//Coloco las minas
		for (int i = 0; i < MINAS_INICIALES; i++) {
			int coordX = rd.nextInt(LADO_TABLERO), coordY = rd.nextInt(LADO_TABLERO);
			if (tablero[coordX][coordY] != MINA) {
				tablero[coordX][coordY] = MINA;
			} else { //Si en la posicion aleatoria ya habia una mina, repito la iteraci�n.
				i--;
			}
		}
		//Coloco las minas adjuntas.
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] == MINA) {
					calculoMinasAdjuntas(i, j);
				}
			}
		}
		depurarTablero();

	}

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como mucho la i y la
	 * j valdrán como poco 0.
	 * 
	 * @param i:
	 *            posición verticalmente de la casilla a rellenar
	 * @param j:
	 *            posición horizontalmente de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {

		if (i == 0 && j == 0) {
			if (tablero[i + 1][j + 1] != MINA) {
				tablero[i + 1][j + 1] += 1;
			}
			if (tablero[i][j + 1] != MINA) {
				tablero[i][j + 1] += 1;
			}
			if (tablero[i + 1][j] != MINA) {
				tablero[i + 1][j] += 1;
			}

			// caso2
		} else if (i == 0 && j == 9) {
			if (tablero[i][j - 1] != MINA) {
				tablero[i][j - 1] += 1;
			}
			if (tablero[i + 1][j - 1] != MINA) {
				tablero[i + 1][j - 1] += 1;
			}
			if (tablero[i + 1][j] != MINA) {
				tablero[i + 1][j] += 1;
			}
			// caso3
		} else if (i == 9 && j == 0) {
			if (tablero[i - 1][j] != MINA) {
				tablero[i - 1][j] += 1;
			}
			if (tablero[i][j + 1] != MINA) {
				tablero[i][j + 1] += 1;
			}
			if (tablero[i - 1][j + 1] != MINA) {
				tablero[i - 1][j + 1] += 1;
			}

			// Caso4
		} else if (i == 9 && j == 9) {
			if (tablero[i][j - 1] != MINA) {
				tablero[i][j - 1] += 1;
			}
			if (tablero[i - 1][j] != MINA) {
				tablero[i - 1][j] += 1;
			}
			if (tablero[i - 1][j - 1] != MINA) {
				tablero[i - 1][j - 1] += 1;
			}

			// caso5
		} else if (i == 0 && (j < 9 && j > 0)) {
			if (tablero[i][j + 1] != MINA) {
				tablero[i][j + 1] += 1;
			}
			if (tablero[i + 1][j + 1] != MINA) {
				tablero[i + 1][j + 1] += 1;
			}
			if (tablero[i + 1][j] != MINA) {
				tablero[i + 1][j] += 1;
			}
			if (tablero[i + 1][j - 1] != MINA) {
				tablero[i + 1][j - 1] += 1;
			}
			if (tablero[i][j - 1] != MINA) {
				tablero[i][j - 1] += 1;
			}
			// caso 6
		} else if (j == 0 && (i < 9 && i > 0)) {
			if (tablero[i + 1][j] != MINA) {
				tablero[i + 1][j] += 1;
			}
			if (tablero[i + 1][j + 1] != MINA) {
				tablero[i + 1][j + 1] += 1;
			}
			if (tablero[i][j + 1] != MINA) {
				tablero[i][j + 1] += 1;
			}
			if (tablero[i - 1][j] != MINA) {
				tablero[i - 1][j] += 1;
			}
			if (tablero[i - 1][j + 1] != MINA) {
				tablero[i - 1][j + 1] += 1;
			}

			// caso 7
		} else if (i == 9 && (j < 9 && j > 0)) {
			if (tablero[i][j - 1] != MINA) {
				tablero[i][j - 1] += 1;
			}
			if (tablero[i - 1][j - 1] != MINA) {
				tablero[i - 1][j - 1] += 1;
			}
			if (tablero[i - 1][j] != MINA) {
				tablero[i - 1][j] += 1;
			}
			if (tablero[i - 1][j + 1] != MINA) {
				tablero[i - 1][j + 1] += 1;
			}
			if (tablero[i][j + 1] != MINA) {
				tablero[i][j + 1] += 1;
			}

			// caso8
		} else if (j == 9 && (i < 9 && i > 0)) {
			if (tablero[i - 1][j] != MINA) {
				tablero[i - 1][j] += 1;
			}
			if (tablero[i - 1][j - 1] != MINA) {
				tablero[i - 1][j - 1] += 1;
			}
			if (tablero[i][j - 1] != MINA) {
				tablero[i][j - 1] += 1;
			}
			if (tablero[i + 1][j - 1] != MINA) {
				tablero[i + 1][j - 1] += 1;
			}
			if (tablero[i + 1][j] != MINA) {
				tablero[i + 1][j] += 1;
			}

		} else {

			for (int j2 = i - 1; j2 < i + 2; j2++) {
				for (int k = j - 1; k < j + 2; k++) {
					if (tablero[j2][k] != MINA) {
						tablero[j2][k] += 1;
					}
				}
			}

		}

		return tablero[i][j];

	}

	/**
	 * Método que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el GestorJuego. Por lo tanto siempre sumaremos puntos
	 * @param i:
	 *            posición verticalmente de la casilla a abrir
	 * @param j:
	 *            posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		if (tablero[i][j] == MINA) {
			return false;
		}
		puntuacion++;
		return true;
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() { // Si la puntuacion es 80 en este caso.
		if (puntuacion == (Math.pow(LADO_TABLERO, 2)) - MINAS_INICIALES) {
			return true;
		}
		return false;
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, símplemente consultarlo
	 * @param i
	 *            : posición vertical de la celda.
	 * @param j
	 *            : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

}
