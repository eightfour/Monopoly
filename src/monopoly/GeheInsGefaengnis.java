package monopoly;

/**
 * 
 * @author Tim
 * @version 0.1
 *
 */

/**
 * Die Klasse GeheInsGefaengnis modeliert das GeheInsGefaengnisfeld auf dem
 * Spielbrett.
 */
public class GeheInsGefaengnis extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -7455167693177988728L;

	/**
	 * Konstruktor der Klasse GeheInsGefaengnis. Initialisert den Namen und die
	 * Position.
	 * 
	 * @param name
	 * @param position
	 */
	public GeheInsGefaengnis(String name, int position) {
		super(name, position);
	}

	/**
	 * aktionAusfueren fuer GeheInsGefaengnis.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer GeheInsGefaengnis
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
		GeheInsGefaengnis g = (GeheInsGefaengnis) obj;
		if (getPosition() != g.getPosition()) {
			return false;
		}
		if (this.getName() != g.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * toString der Klasse GeheInsGefaengnis.
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "GeheInsGefaengnis";
	}

}
