package monopoly;

/**
 * 
 * @author Tim
 *@version 0.1
 */

/**
 * Die Klasse Steuer modeliert die Steuer , die die einzelnen Spieler in
 * Monopoly verrichten muessen. Die Einkommenssteuer betraegt 30% des
 * Gesamtvermoegens, die Zusatzsteuer betraegt 10% des Gesamtvermoegens.
 *
 */
public class Steuer extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -6557945038581139489L;
	/**
	 * Betrag der Steuer.
	 */
	private int betrag;

	/**
	 * Konstruktor der den Namen der Steuer und die Positoin des Spielfeldes
	 * initialisiert.
	 * 
	 * @param name
	 * @param position
	 */
	public Steuer(String name, int position) {
		super(name, position);
	}

	/**
	 * equals Methode fuer die Steuer-Klasse.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer die Steuer-Klasse.
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
		Steuer s = (Steuer) obj;
		if (getPosition() != s.getPosition()) {
			return false;
		}
		if (this.getName() != s.getName()) {
			return false;
		}
		return true;

	}

	/**
	 * Getter fuer den Betrag
	 * 
	 * @return Betrag
	 */
	public int getBetrag() {
		return betrag;
	}

	/**
	 * Setter fuer den Betrag.
	 * 
	 * @param betrag
	 */
	public void setBetrag(int betrag) {
		this.betrag = betrag;
	}

	/**
	 * toString Methode der Klasse Steuer
	 * 
	 * @return String
	 */
	// TODO eventuell noch "optisch" anpassen.
	@Override
	public String toString() {
		return "" + getName() + "";
	}

}
