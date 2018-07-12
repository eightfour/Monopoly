package monopoly;

import java.io.Serializable;

/**
 * Diese Klasse modeliert ein Haus. Haueser koennen auf Strassen unter gegebenen
 * Bedingungen gebaut werden.
 */
public class Haus implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -802894837530961452L;
	/**
	 * fixer Kaufpreis von 200.
	 */
	private static final int kaufpreis = 200;

	/**
	 * Getter fuer den statisch finalen Kaupfreis eines Hauses.
	 * 
	 * @return den Kaufpreis von 200.
	 */
	public static int getKaufpreis() {
		return kaufpreis;
	}

}
