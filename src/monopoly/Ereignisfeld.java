package monopoly;

/**
 * 
 * @author Tim
 * @version 0.1
 *
 */

/**
 * Die Klasse Ereignisfeld modeliert die Ereignisfelder auf dem Spielbrett.
 */
public class Ereignisfeld extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 1240240137696728447L;

	/**
	 * Der Konstruktor. Initialisiert den Namen des Spielfeldes und die
	 * Position.
	 * 
	 * @param name
	 * @param position
	 */
	public Ereignisfeld(String name, int position) {
		super(name, position);
	}

	/**
	 * aktionAusfueren fuer die Ereignisfeldklasse.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer die Ereignisfeldklasse.
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
		Ereignisfeld e = (Ereignisfeld) obj;
		if (getPosition() != e.getPosition()) {
			return false;
		}
		if (this.getName() != e.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * toString Mehtode der Klasse Ereignisfeld.
	 * 
	 * @return String
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "" + getName() + " ";
	}
}
