package monopoly;

/**
 * 
 * @author Tim
 * @version 0.1
 *
 */

/**
 * Die Klasse FreiParken modeliert das FreiParkenfeld auf dem Spielbrett.
 */
public class FreiParken extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -5619851125318509134L;

	/**
	 * Konstruktor der Klasse FreiParken. Initialisert den Namen und die
	 * Position.
	 * 
	 * @param name
	 * @param position
	 */
	public FreiParken(String name, int position) {
		super(name, position);
	}

	/**
	 * aktionAusfueren fuer die FeiParken klasse.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer die FreiParken Klasse.
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
		FreiParken f = (FreiParken) obj;
		if (getPosition() != f.getPosition()) {
			return false;
		}
		if (this.getName() != f.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * toString der Klasse FreiParken.
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "FreiParken";
	}

}
