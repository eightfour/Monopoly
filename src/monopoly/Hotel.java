package monopoly;

import java.io.Serializable;

/**
 * Diese Klasse modeliert ein Hotel. Hotels koennen auf Strassen unter gegebenen
 * Bedingungen gebaut werden.
 */
public class Hotel implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -5592197039170574626L;
	/**
	 * fixer Kaufpreis von 500.
	 */
	private static final int kaufpreis = 500;

	/**
	 * Getter fuer den statisch finalen Kaupfreis eines Hotels.
	 * 
	 * @return den Kaufpreis von 500.
	 */
	public static int getKaufpreis() {
		return kaufpreis;
	}

}
