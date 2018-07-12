package monopoly;

/**
 * 
 * @author Tim
 * @version 0.1 
 */

/**
 * Die Klasse modeliert eine Strasse. Strasse ist ein Spielfeldobjekt mit einem
 * spaeter spezifizierten Kaufpreis, einem Hotel oder 0 bis maximal zwei
 * Haeuser.
 */
public class Strasse extends Spielfeld {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -1535153367233912249L;

	/**
	 * Kaufpreis
	 */
	private int kaufpreis;

	/**
	 * Hotel
	 */
	private Hotel hotel;

	/**
	 * Haeuser (entweder 0,1 oder 2)
	 */
	private Haus haus1;

	/**
	 * Haeuser (entweder 0,1 oder 2)
	 */
	private Haus haus2;

	/**
	 * Die StrassenID der Strasse. Diese besteht aus einem enum, und kann
	 * folgende Werte annehmen. dunkellila, hellblau, lila, orange, rot, gelb,
	 * dunkelgrün oder blau.
	 */
	private StrassenID StrassenID;

	/**
	 * 
	 * @param Der
	 *            Name der Strasse auf dem Spielfeld.
	 * @param Die
	 *            Position der Strasse auf dem Spielfeld.
	 * @param Der
	 *            kaufpreis der Strasse auf dem Spielfeld.
	 * @param die
	 *            ID zur Erkennung ob der Spieler eine komplette Strasse
	 *            besitzt. Der Konstruktor setzt den Namen und die Position und
	 *            den kaufpreis auf dem Spielbrett. per farbe! sind 8 stück 2
	 *            mit 2 6 mit 3.
	 */
	public Strasse(String name, int position, int kaufpreis, StrassenID strassenID) {
		super(name, position);
		setKaufpreis(kaufpreis);
		setStrassenID(strassenID);

	}

	/**
	 * aktionAusfuehren fuer eine Strasse.
	 */
	@Override
	public void aktionAusfuehren() {
		// TODO Auto-generated method stub

	}

	/**
	 * equals Methode fuer eine Strasse.
	 */

	// TODO
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj.getClass().equals(getClass()))) {
			return false;
		}
		Strasse s = (Strasse) obj;
		if (getPosition() != s.getPosition()) {
			return false;
		}
		if (this.getName() != s.getName()) {
			return false;
		}
		if (this.getKaufpreis() != s.getKaufpreis()) {
			return false;
		}
		return true;

	}

	/**
	 * Getter fuer Haus1
	 * 
	 * @return Haus1
	 */
	public Haus getHaus1() {
		return haus1;
	}

	/**
	 * Getter fuer Haus2
	 * 
	 * @return Haus2
	 */
	public Haus getHaus2() {
		return haus2;
	}

	/**
	 * Getter fuer das Hotel
	 * 
	 * @return Hotel
	 */
	public Hotel getHotel() {
		return hotel;
	}

	/**
	 * Getter fuer den Kaufpreis.
	 * 
	 * @return kaufpreis
	 */
	public int getKaufpreis() {
		return kaufpreis;
	}

	/**
	 * Getter für die StrassenID
	 * 
	 * @return die StrassenID
	 */
	public StrassenID getStrassenID() {
		return StrassenID;

		/**
		 * Setter für die StrassenID
		 * 
		 * @param die
		 *            übergebene StrassenID
		 */
	}

	/**
	 * Hilfsmethode fuer die toString() Methode, die angibt ob sich auf der
	 * Strasse Haueser oder ein Hotel befinden.
	 * 
	 * @return
	 */
	private String hatHaeuserOderHotel() {
		String s = "";
		if (getHotel() != null) {
			s = "Ein Hotel";
		} else {
			if (getHaus1() != null & getHaus2() != null) {
				s = "Zwei Haeuser";
			} else {
				if (getHaus1() != null | getHaus2() != null) {
					s = "Ein Haus";
				} else {
					s = "Kein Hotel/Haeuser";
				}
			}
		}
		return s;
	}

	/**
	 * Setter fuer Haus1
	 * 
	 * @param haus1
	 */
	public void setHaus1(Haus haus1) {
		this.haus1 = haus1;
	}

	/**
	 * Setter fuer Haus2
	 * 
	 * @param haus2
	 */
	public void setHaus2(Haus haus2) {
		this.haus2 = haus2;
	}

	/**
	 * Setter fuer das Hotel.
	 * 
	 * @param hotel
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	/**
	 * Setter fuer den Kaufpreis
	 * 
	 * @param kaufpreis
	 */
	public void setKaufpreis(int kaufpreis) {
		this.kaufpreis = kaufpreis;
	}

	public void setStrassenID(StrassenID strassenID) {
		this.StrassenID = strassenID;
	}

	/**
	 * @return String, gibt die Strasse auf der Konsole aus.
	 */
	// muss eventuell noch "optisch" angepasst werden.
	@Override
	public String toString() {
		return "" + getName() + " - " + " Kaufpreis: " + getKaufpreis() + " Bebaut mit: " + hatHaeuserOderHotel() + "";
	}

}
