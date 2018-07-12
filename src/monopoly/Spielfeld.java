package monopoly;

import java.io.Serializable;

/**
 * Die abstrakte Klasse Spielfeld modelliert ein Spielfeld auf dem Spielbrett.
 */
public abstract class Spielfeld implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 11256942844567819L;

	/**
	 * Name des Spielfeldes.
	 */
	private String name;

	/**
	 * Position auf dem Spielbrett.
	 */
	private int position;

	/**
	 * @param name
	 * @param position
	 *            Konstruktor fuer den super aufruf in den abgeleiteten Klassen.
	 */
	public Spielfeld(String name, int position) {
		setName(name);
		setPosition(position);
	}

	/**
	 * Fuehrt fur den Aktuellen Spieler die Aktion aus, die beim betreten dieses
	 * Spielfelds eintritt.
	 */
	public abstract void aktionAusfuehren();

	/**
	 * equals Methode fuer die Spielfelder.
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
		Spielfeld s = (Spielfeld) obj;
		if (this.getPosition() != s.getPosition()) {
			return false;
		}
		if (this.getName() != s.getName()) {
			return false;
		}
		return true;
	}

	/**
	 * Getter fuer den Namen.
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter fuer die Postition.
	 * 
	 * @return int position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param String
	 *            name Setter fuer den Namen.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param int
	 *            position Setter fuer die Positon.
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * toString Methode fuer Spielfelder.
	 */
	@Override
	public abstract String toString();

}
