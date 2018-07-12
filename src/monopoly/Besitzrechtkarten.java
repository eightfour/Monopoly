package monopoly;

import java.io.Serializable;

/**
 * Diese Klasse modeliert Besitzrechtkarten. Diese werden verwendet wenn Spieler
 * Spielfelder wie z.B. Strassen erwerben moechten. Spieler koennen Strassen,
 * Versorgungswerke oder Bahnhoefe erwerben.
 */
public class Besitzrechtkarten implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -1978696043472923641L;

	/**
	 * Eine Besitzrechtkarte kennt immer ihr Spielfeld.
	 */
	private Spielfeld spielfeld;

	/**
	 * Eine Besitzrechtkarte kann ihren Spieler kennen.
	 */
	private Spieler spieler;

	/**
	 * @param Spielfeld.
	 *            Konsruktor der das Spielfeld initialisiert.
	 */
	public Besitzrechtkarten(Spielfeld spielfeld, Spieler spieler) {
		setSpielfeld(spielfeld);
		setBesitzer(spieler);

	}

	/**
	 * Equals-Methode fuer Besitzrechtkarten.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Besitzrechtkarten other = (Besitzrechtkarten) obj;
		return (getSpielfeld().getPosition() == other.getSpielfeld().getPosition())
				& (getBesitzer().getID() == other.getBesitzer().getID());

	}

	/**
	 * Getter fuer den Besitzer.
	 * 
	 * @return Spieler spieler
	 */
	public Spieler getBesitzer() {
		return spieler;
	}

	/**
	 * Getter fuer das Spielfeld.
	 * 
	 * @return spielfeld
	 */
	public Spielfeld getSpielfeld() {
		return spielfeld;
	}

	/**
	 * Gibt an ob die Aktuelle Besitzrechtkarte einem Bahnhof zugehoerig ist.
	 * Gibt true zurueck wenn ja, false, wenn nein.
	 * 
	 * @return boolean
	 */
	public boolean istBahnhofBesitzrechtkarte() {
		if (getSpielfeld() instanceof Bahnhof) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gibt an ob die Aktuelle Besitzrechtkarte einer Strasse zugehoerig ist.
	 * Gibt true zurueck wenn ja, false, wenn nein.
	 * 
	 * @return boolean
	 */
	public boolean istStrassenBesitzrechtkarte() {
		if (getSpielfeld() instanceof Strasse) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gibt an ob die Aktuelle Besitzrechtkarte eine Versorgungswerk zugehoerig
	 * ist. Gibt true zurueck wenn ja, false, wenn nein.
	 * 
	 * @return boolean
	 */
	public boolean istVersorgungswerkBesitzrechtkarte() {
		if (getSpielfeld() instanceof Versorgungswerk) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Setter fuer den Besitzer.
	 * 
	 * @param spieler
	 */
	public void setBesitzer(Spieler spieler) {
		this.spieler = spieler;
	}

	/**
	 * Setter fuer das Spielfeld.
	 * 
	 * @param spielfeld
	 */
	public void setSpielfeld(Spielfeld spielfeld) {
		this.spielfeld = spielfeld;
	}

	// /**
	// * Equals-Methode fuer Besitzrechtkarten.
	// */
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// Besitzrechtkarten other = (Besitzrechtkarten) obj;
	// if (spieler == null) {
	// if (other.spieler != null)
	// return false;
	// } else if (!spieler.equals(other.spieler))
	// return false;
	// if (spielfeld == null) {
	// if (other.spielfeld != null)
	// return false;
	// } else if (!spielfeld.equals(other.spielfeld))
	// return false;
	// return true;
	// }

	/**
	 * toString Methode, die eine Besitzrechtkarte als String ausgibt.
	 * 
	 */
	// TODO optisch anpassen?!
	@Override
	public String toString() {
		return "Besitzrechtkarten [spielfeld=" + spielfeld + ", spieler=" + spieler.getID() + "]";
	}

}
