package monopoly;

/**
 * 
 * @author Tim
 * @version 0.1
 *
 */

/**
 * Die Klasse Gefaengnis modeliert das Gefaengnisfeld auf dem Spielbrett.
 */
public class Gefaengnis extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 819561188379988430L;

	/**
	 * Der Konstruktor. Initialisiert den Namen des Spielfeldes und die
	 * Position.
	 * 
	 * @param name
	 * @param position
	 */
	public Gefaengnis(String name, int position) {
		super(name, position);
	}

	/**
	 * aktionAusfueren fuer Gefaengnis.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer Gefaengnis.
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
		Gefaengnis g = (Gefaengnis) obj;
		if (getPosition() != g.getPosition()) {
			return false;
		}
		if (this.getName() != g.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * toString der Klasse Gefaengnis
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "Gefaengnis";
	}

}
