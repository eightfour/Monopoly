package monopoly;

/**
 * 
 * @author Tim
 * @version 0.1
 *
 */

/**
 * Die Klasse Los modeliert das Losspielfeld auf dem Spielbrett.
 */
public class Los extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -6637213033845340984L;

	/**
	 * Der Konstruktor. Initialisiert den Namen des Spielfeldes und die
	 * Position.
	 * 
	 * @param name
	 * @param position
	 */
	public Los(String name, int position) {
		super(name, position);
	}

	/**
	 * equals Methode fuer aktionAusfueren.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer die LosKlasse.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj.getClass().equals(getClass()))) {
			return false;
		}
		Los l = (Los) obj;
		if (this.getPosition() != l.getPosition()) {
			return false;
		}
		if (this.getName() != l.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * toString Mehtode der Klasse Los.
	 * 
	 * @return String
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "Los";
	}

}
