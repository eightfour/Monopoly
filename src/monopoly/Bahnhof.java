package monopoly;

/**
 * @author Tim
 * @author bisci
 * @version
 */

/**
 * Die Klasse modeliert einen der insgesamt vier Bahnhoefe auf dem Spielfeld.
 */
public class Bahnhof extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -6818677128526065007L;
	/**
	 * Alle Bahnhoefe besitzen einen statischen "fixen" Kaufpreis von 200.
	 */
	private static int kaufpreis = 200;

	/**
	 * Getter fuer den Kaufpreis.
	 * 
	 * @return Kaufpreis.
	 */
	public static int getKaufpreis() {
		return kaufpreis;
	}

	/**
	 * Konstruktor der den Namen des Bahnhofes und seine Position auf dem
	 * Spielfeld initialisiert.
	 * 
	 * @param name
	 * @param position
	 */
	public Bahnhof(String name, int position) {
		super(name, position);
	}

	/**
	 * aktionAusfuehren fuer einen Bahnhof.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals fuer einen Bahnhof.
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
		Bahnhof b = (Bahnhof) obj;
		if (this.getName() != b.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * Gibt einen Bahnhof auf der Konsole aus.
	 * 
	 * @return String eine Instanz eines Bahnhofes.
	 */
	@Override
	public String toString() {
		return "" + getName() + " -" + " - Kaufpreis: " + getKaufpreis() + "";
	}

}
