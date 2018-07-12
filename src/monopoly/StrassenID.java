package monopoly;

import java.io.Serializable;

/**
 * Eine StrassenID modeliert eine StrassenID fuer eine Strasse. StrassenIDs
 * koennen folgende Werte annehmen: dunkellila, hellblau, lila, orange, rot,
 * gelb, dunkelgrün oder blau
 */
public enum StrassenID implements Serializable {

	/**
	 * Die moeglichen Farben, die zur auswahl stehen. Es stehen zur auswahl:
	 * dunkelila, hellblau, lila, orange, rot, gelb, dunkelgruen, blau und fuer
	 * interne verarbeitungsprozesse keinefarbe.
	 */
	dunkellila, hellblau, lila, orange, rot, gelb, grün, blau, keinefarbe;

	@SuppressWarnings("rawtypes")
	public static int getFarbe(Enum zahl) {
		switch (zahl.toString()) {
		case "dunkellila":
			return 0;
		case "hellblau":
			return 1;
		case "lila":
			return 2;
		case "orange":
			return 3;
		case "rot":
			return 4;
		case "gelb":
			return 5;
		case "grün":
			return 6;
		case "blau":
			return 7;
		default:
			return 8;
		}
	}

	/**
	 * Ein universalgetter fuer die einzelnen Farben Er gibt die Farbe mit
	 * Ordinalzahl+1 aus.
	 * 
	 * @param zahl
	 *            die bei 1-8 eine Farbe und bei einer anderen eingabe
	 *            keinefarbe ergibt.
	 * @return das jeweilige ENUM.
	 */
	public static StrassenID getFarbe(int zahl) {
		switch (zahl) {
		case 1:
			return dunkellila;
		case 2:
			return hellblau;
		case 3:
			return lila;
		case 4:
			return orange;
		case 5:
			return rot;
		case 6:
			return gelb;
		case 7:
			return grün;
		case 8:
			return blau;
		default:
			return keinefarbe;
		}
	}
}
