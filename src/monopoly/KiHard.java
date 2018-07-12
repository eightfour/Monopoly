package monopoly;

/**
 * Diese Klasse modelliert eine Ki mit dem Standard "Schwer" Schwierigkeitsgrad,
 * den Sie von der Oberklasse erbt.
 *
 */
public class KiHard extends Ki {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -4209085207265072891L;

	/**
	 * Konstruktor, mit Superaufruf an die Oberklasse.
	 * 
	 * @param iD
	 * @param farbe
	 * @param spiel
	 */
	public KiHard(int iD, FarbEnum farbe, Spiel spiel) {
		super(iD, farbe, "KI", spiel);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean entscheidungStrasse() {
		
		//kaufentscheidungen soll anhand der statistik entschieden werden.
		//die f�lle von kieasy sollen auch �bernommen werden.
		//als erstes soll den wahrscheinlichkeiten kaufen.
		//wenn sie jedoch eine strasse gekauft hat die zugeh�rig ist haben die anderen zugeh�rigen strassen vorrang.
		
		
		
		return false;
	}
	
	
	
	

}
