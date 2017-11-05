import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;
	VentanaPrincipal ventanaPrincipal = this; // Referencio la ventana principal para pasarsela al ActionBoton.

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.DARK_GRAY,
			Color.YELLOW, Color.GRAY, Color.PINK, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		// PONGO LA IMAGEN PERO SE DESCUADRA AUNQUE COJA LAS MEDIDAS DEL JPANEL (son 0).
		panelImagen.setLayout(new GridLayout(1, 1));
		try {
			BufferedImage img = ImageIO.read(new File("logotipobuscaminas.jpg"));
			ImageIcon icono = new ImageIcon(img.getScaledInstance(100, 50, 1));
			JLabel label = new JLabel(icono);
			panelImagen.add(label);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(ventanaPrincipal, i, j));
			}
		}

		//Listener del botonEmpezar
		botonEmpezar.addActionListener(new ActionListener() {

			/**
			 * Crea un nuevo juego ControlJuego. Elimina todos los elementos de los paneles 
			 * inicializa los listener nuevamente y a�ade esos botones a los paneles respectivos.
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				juego = new ControlJuego();
				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego.length; j++) {
						panelesJuego[i][j].removeAll();
					}
				}
				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego.length; j++) {
						botonesJuego[i][j] = new JButton("-");
						botonesJuego[i][j].addActionListener(new ActionBoton(ventanaPrincipal, i, j));
						panelesJuego[i][j].add(botonesJuego[i][j]);
					}
				}
				pantallaPuntuacion.setText("0");
				refrescarPantalla();
			}
		});
	}

	/**
	 * Método que pinta en la pantalla el número de minas que hay alrededor de la
	 * celda Saca el botón que haya en la celda determinada y añade un JLabel
	 * centrado y no editable con el número de minas alrededor. Se pinta el color
	 * del texto según la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó
	 * más : rojo
	 * 
	 * @param i:
	 *            posición vertical de la celda.
	 * @param j:
	 *            posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		panelesJuego[i][j].removeAll();
		refrescarPantalla();
		JLabel labelMinas = new JLabel(String.valueOf(juego.getMinasAlrededor(i, j)));
		labelMinas.setForeground(correspondenciaColores[juego.getMinasAlrededor(i, j)]);
		GridBagLayout layout = new GridBagLayout();
		panelesJuego[i][j].setLayout(layout);
		panelesJuego[i][j].add(labelMinas, null);
		actualizarPuntuacion();
		refrescarPantalla();
	}

	/**
	 * Método que muestra una ventana que muestra el fin del juego
	 * 
	 * @param porExplosion
	 *            : Un booleano que indica si es final del juego porque ha explotado
	 *            una mina (true) o bien porque hemos desactivado todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		if (!porExplosion) {
			System.out.println("Has perdido");
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego.length; j++) {
					botonesJuego[i][j].setEnabled(false);
				}
			}
			JOptionPane.showMessageDialog(null, "          \n          BOOM!!!\n          ", "Has perdido!", 1);
		} else {
			JOptionPane.showMessageDialog(null, "          \n          HAS GANADO!!!!!!!\n          ",
					"ENHORABUENA", 1);
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

}
