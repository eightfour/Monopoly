package monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import monopolyGui.ResourceLoader;

/**
 * 
 * @author Tim
 * @version 0.1
 *
 */

/**
 * Die Klasse modeliert ein Spielbrett, auf dem die Spielerbewegungen ausgefuert
 * werden.
 */
public class Spielbrett implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718785202989055986L;
	/**
	 * Ein Spielfeld Array, dass 40 Spielfeldobjekte haelt.
	 */
	private Spielfeld[] spielfeld = new Spielfeld[40];

	/**
	 * Konstruktor
	 */
	public Spielbrett() {
		this.initSpielfeld();
	}

	/**
	 * Getter fuer ein Spielfeld an einer Bestimmten Position.
	 * 
	 * @param pos
	 * @return
	 */
	public Spielfeld geteinSpielfeld(int pos) {
		return spielfeld[pos];
	}

	// Kommentar

	/**
	 * Getter fuer das Spielfeld
	 * 
	 * @return
	 */
	public Spielfeld[] getSpielfeld() {
		return spielfeld;
	}

	/**
	 * Spielfeld wird durch Einlesen der Spielfelder.csv generiert. Die
	 * Aufstellung der csv: Strassenname,Position,Art,Preis,Farbe.
	 * 
	 * @throws java.io.IOException
	 */
	private void initSpielfeld() {
		String all;
		String[] arrString = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ResourceLoader.load("Spielfelder.csv")));
			all = bufferedReader.readLine();
			arrString = all.split(";");
			bufferedReader.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		int feld = 0;
		for (int i = 0; i < arrString.length; i += 5) {
			switch (Integer.parseInt(arrString[i + 2])) {
			case 0:
				this.spielfeld[feld] = new Los(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 1:
				this.spielfeld[feld] = new Strasse(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)),
						Integer.parseInt(arrString[i + 3]), StrassenID.getFarbe(Integer.parseInt(arrString[i + 4])));
				feld++;
				break;
			case 2:
				this.spielfeld[feld] = new Bahnhof(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 3:
				this.spielfeld[feld] = new Versorgungswerk(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 4:
				this.spielfeld[feld] = new Ereignisfeld(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 5:
				this.spielfeld[feld] = new Steuer(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 6:
				this.spielfeld[feld] = new FreiParken(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 7:
				this.spielfeld[feld] = new GeheInsGefaengnis(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			case 8:
				this.spielfeld[feld] = new Gefaengnis(arrString[i], ((Integer.parseInt(arrString[i + 1]) - 1)));
				feld++;
				break;
			}
		}
	}

	// public void setSpielfeld(Spielfeld[] spielfeld) {
	// this.spielfeld = spielfeld;
	// }

	/**
	 * @return String, das aktuelle Spielbrett. toString(), gibt das Spielbrett
	 *         auf der Konsole aus.
	 */
	// TODO Muss eventuell noch "optisch" angepasst werden.
	@Override
	public String toString() {
		String spielBrett = "";
		for (int i = 0; i < spielfeld.length; i++) {
			spielBrett += "Spielfeld " + (i + 1) + ": " + spielfeld[i] + ";" + "\n";
		}
		return spielBrett;
	}

}
