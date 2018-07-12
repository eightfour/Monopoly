package monopoly;

/**
 * Diese Klasse modelliert eine Ki mit dem Standard "Einfach"
 * Schwierigkeitsgrad, den Sie von der Oberklasse erbt.
 *
 */
public class KiEasy extends Ki {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 6546995736405000685L;

	/**
	 * Konstruktor, mit Superaufruf an die Oberklasse.
	 * 
	 * @param iD
	 * @param farbe
	 * @param spiel
	 */
	public KiEasy(int iD, FarbEnum farbe, String name, Spiel spiel) {
		super(iD, farbe, name, spiel);
		// TODO Auto-generated constructor stub
	}

}
