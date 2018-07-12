package monopoly;

import java.io.Serializable;

/**
 * Diese Klasse modeliert eine Ereigniskarte die gezogen werden kann.
 */
public class Ereigniskarte implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 6503974753119974063L;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private int betrag;
	/**
	 * 
	 */
	private int pos;
	/**
	 * 
	 */
	private int zahlenSpieler;
	/**
	 * 
	 */
	private int haus;

	private int hotel;

	/**
	 * Der Konstruktor.
	 * 
	 * @param der
	 *            Text den die Ereigniskarte anzeigen soll
	 * @param der
	 *            Betrag der Spielern abgezogen werden soll
	 * @param die
	 *            Position auf die der Spieler gesetzt werden soll
	 * @param wenn
	 *            die Spieler an den aktuellen Spieler ein betrag zahlen soll
	 *            oder wenn er an die Spieler einen Betrag zahlen soll
	 * @param falls
	 *            der Spieler pro Hotel oder Haus gewisse kosten zahlen muss
	 */
	public Ereigniskarte(String name, int betrag, int pos, int zahlenSpieler, int haus, int hotel) {
		this.name = name;
		this.betrag = betrag;
		this.pos = pos;
		this.zahlenSpieler = zahlenSpieler;
		this.haus = haus;
		this.hotel = hotel;
	}

	/**
	 * 
	 * @return
	 */
	public int getBetrag() {
		return betrag;
	}

	/**
	 * 
	 * @return
	 */
	public int getHaus() {
		return haus;
	}

	public int getHotel() {
		return hotel;
	}

	/**
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * 
	 * @return
	 */
	public int getZahlenSpieler() {
		return zahlenSpieler;
	}

	/**
	 * toString Methode fuer eine Ereigniskarte. Gibt nur den Namen der
	 * Ereigniskarte aus.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Ereigniskarte [" + name + "]";
	}

	/**
	 * Ebenfalls eine toString Methode fuer eine Ereigniskarte. Gibt alle
	 * Eigenschaften der Ereigniskarte aus.
	 * 
	 * @return String
	 */
	public String toStringo() {
		return "Ereigniskarte [name=" + name + ", " + betrag + ", " + pos + ", " + zahlenSpieler + ", " + haus + ", "
				+ hotel + "]";
	}

}
