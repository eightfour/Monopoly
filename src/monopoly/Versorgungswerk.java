package monopoly;

/**
 * @author Tim, Thomas
 * @version 0.1
 */

/**
 * Die Klasse Versorgungswerk modeliert auf dem Spielbrett entweder ein Wasser-
 * oder Elektizitaetswerk.
 */
public class Versorgungswerk extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 3027448688944644696L;
	/**
	 * Statischer "fixer" Kaufpreis von 150
	 */
	private static final int kaufpreis = 150;

	/**
	 * Getter fuer Kaufpreis
	 * 
	 * @return kaufpreis
	 */
	public static int getKaufpreis() {
		return kaufpreis;
	}

	/**
	 * Der Konstruktor der den Namen des Versorgungswerkes und seine Position
	 * auf dem Spielfeld initialisert.
	 * 
	 * @param name
	 * @param position
	 */
	public Versorgungswerk(String name, int position) {
		super(name, position);
	}

	/**
	 * aktionAusfueren fuer ein Versorgungswerk.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals fuer ein Versorgunswerk.
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
		Versorgungswerk v = (Versorgungswerk) obj;
		if (this.getName() != v.getName()) {
			return false;
		}
		return true;
	}

	/**
	 * Gibt ein Versorgungswerk auf der Konsole aus.
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "" + getName() + " - " + " Kaufpreis: " + getKaufpreis() + "";
	}

}
