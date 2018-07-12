package monopoly;

import java.io.Serializable;

/**
 * Die Klasse FarbEnum modeliert eine Spielerfarbe, die von den Spielern
 * ausgewaeht werden kann.
 */
public enum FarbEnum implements Serializable {

	/**
	 * Die moeglichen Farben, die zur auswahl stehen. Es stehen zur auswahl:
	 * Gelb, rot, gruen, schwarz, und fuer interne verarbeitungsprozesse
	 * keinefarbe.
	 */
	GELB, ROT, GRUEN, BLAU, SCHWARZ, keinefarbe;

	/**
	 * Ein universalgetter fuer die einzelnen Farben Er gibt die Farbe mit
	 * Ordinalzahl+1 aus.
	 * 
	 * @param zahl
	 *            die bei 1-5 eine Farbe und bei einer anderen eingabe
	 *            keinefarbe ergibt.
	 * @return das jeweilige ENUM.
	 */
	public static FarbEnum getFarbe(int zahl) {
		switch (zahl) {
		case 1:
			return ROT;
		case 2:
			return BLAU;
		case 3:
			return GRUEN;
		case 4:
			return GELB;
		case 5:
			return SCHWARZ;
		default:
			return keinefarbe;
		}
	}
	public static int getFarbe(FarbEnum zahl) {
		switch (zahl) {
		case ROT:
			return 1;
		case BLAU:
			return 2;
		case GRUEN:
			return 3;
		case GELB:
			return 4;
		case SCHWARZ:
			return 5;
		default:
			return 0;
		}
	}

}
