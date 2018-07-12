package monopoly;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Die Klase modeliert einen Spieler, der nachher mit dem Spiel interagiert.
 * 
 * @author Tim
 *
 */
public class Spieler implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -4981006497112207357L;
	/**
	 * Identifikationsnummer eines Spielers.
	 */
	private int ID;
	/**
	 * Farbe eines Spielers.
	 */
	private FarbEnum farbe;
	/**
	 * Position auf dem Spielbrett.
	 */
	private int position;
	/**
	 * Das Guthaben eines Spielers.
	 */
	private double guthaben = 1500;
	/**
	 * Alle Besitzrechtkaten, die ein Spieler besitzt.
	 */
	private ArrayList<Besitzrechtkarten> besitzrechtkarten = new ArrayList<Besitzrechtkarten>();
	/**
	 * Wie lange ein Spieler im Gefaengnis ist.
	 */
	private int gefaengnisCounter;

	/**
	 * Gibt an ob die aktuelle Entitaet ein echter Spieler oder eine Spiel-Ki
	 * ist.
	 */
	private boolean istKi;
	/**
	 * Der name des spielers
	 */
	private String name;

	/**
	 * Konstruktor, der die ID, und die Farbe initialisiert.
	 * 
	 * @param iD
	 * @param farbe
	 */
	public Spieler(int iD, FarbEnum farbe, String name) {

		setID(iD);
		setFarbe(farbe);
		setGefaengnisCounter(3);
		setIstKi(false);
		setName(name);
	}

	/**
	 * equals Methode fuer einen Spieler.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spieler other = (Spieler) obj;
		if (farbe != other.farbe)
			return false;
		return true;
	}

	/**
	 * Getter fuer die besitzrechtkarten.
	 * 
	 * @return
	 */
	public ArrayList<Besitzrechtkarten> getBesitzrechtkarten() {
		return besitzrechtkarten;
	}

	/**
	 * Getter fuer die Farbe.
	 * 
	 * @return
	 */
	public FarbEnum getFarbe() {
		return farbe;
	}

	/**
	 * Getter fuer den gefaengnisCounter
	 * 
	 * @return gefaengnisCounter
	 */
	public int getGefaengnisCounter() {
		return gefaengnisCounter;
	}

	/**
	 * Getter fuer das Guthaben.
	 * 
	 * @return
	 */
	public double getGuthaben() {
		return guthaben;
	}

	/**
	 * Getter fuer die ID
	 * 
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Getter für den namen des Spielers
	 * 
	 * @return der name des Spielers
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter fuer die Position.
	 * 
	 * @return
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Der Getter fuer den boolean-Wert istKi.
	 * 
	 * @return
	 */
	public boolean isIstKi() {
		return istKi;
	}

	/**
	 * Setter fuer die besitzrechtkarten
	 * 
	 * @param besitzrechtkarten
	 */
	public void setBesitzrechtkarten(ArrayList<Besitzrechtkarten> besitzrechtkarten) {
		this.besitzrechtkarten = besitzrechtkarten;
	}

	/**
	 * Setter fuer die Farbe.
	 * 
	 * @param farbe
	 */
	public void setFarbe(FarbEnum farbe) {
		this.farbe = farbe;
	}

	/**
	 * Setter fuer den gefaengnisCounter
	 * 
	 * @param gefaengnisCounter
	 */
	public void setGefaengnisCounter(int gefaengnisCounter) {
		this.gefaengnisCounter = gefaengnisCounter;
	}

	/**
	 * Setter fuer das Guthaben
	 * 
	 * @param d
	 */
	public void setGuthaben(double d) {
		this.guthaben = (((double) ((int) (d * 100))) / 100);
	}

	/**
	 * Setter fuer die ID
	 * 
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Der Setter fuer den boolean-Wert istKi.
	 * 
	 * @return
	 */
	public void setIstKi(boolean istKi) {
		this.istKi = istKi;
	}

	/**
	 * Setter für den namen des Spielers
	 * 
	 * @param name
	 *            den der Spieler haben soll
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter fuer die Position
	 * 
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * toString Methode fuer einen Spieler.
	 */
	// TODO OPTISCH ANPASSEN?!
	@Override
	public String toString() {
		return "Spieler [ID=" + ID + ", farbe=" + farbe + ", position=" + position + ", guthaben=" + guthaben
				+ ", besitzrechtkarten=" + besitzrechtkarten + "]";
	}

}
