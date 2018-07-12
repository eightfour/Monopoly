package monopoly;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Diese Klasse modelliert eine Ki, die selbststaendig Spielzuege ausfuehrt.
 *
 */
public class Ki extends Spieler implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 7464572619182997085L;

	/**
	 * Ein Spiel als benoetigte Komponente.
	 */
	private IBediener bed;

	/**
	 * Eine Ki wird wie ein normaler Spieler initialisiert. SetIstKi wird auf
	 * true gesetzt.
	 * 
	 * @param iD
	 * @param farbe
	 */
	public Ki(int iD, FarbEnum farbe, String name, Spiel spiel) {
		super(iD, farbe, name);
		setIstKi(true);
		bed = spiel;
	}

	/**
	 * wann soll eine ki bauen, eine ki soll bauen, wenn sie bauen darf und mehr
	 * als 500 puffer hat. wenn die ki auf einer strasse bauen kann, baut sie,
	 * es wird nach besitzrechtkarten von 0-n gebaut.
	 * 
	 * @return boolean true oder false fuer ja und nein.
	 */
	public boolean bauenKI() {

		for (Besitzrechtkarten b : getBesitzrechtkarten()) {
			if (b.getSpielfeld().getClass().equals(Strasse.class)) {
				Strasse strasse = (Strasse) b.getSpielfeld();
				if (bed.darfbauen(strasse)) {
					if (strasse.getHotel() != null) {
						continue;
					}
					if (strasse.getHotel() == null) {
						if (strasse.getHaus1() == null || strasse.getHaus2() == null) {
							if (getGuthaben() >= 700) {
								bed.bauen(strasse);
								return true;
							}
						} else {
							if (getGuthaben() >= 1000) {
								bed.bauen(strasse);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Je nachdem welche Entscheidung die Ki trifft gibt es true für ja und
	 * false für nein zurück
	 * 
	 * @return gibt die Entscheidung der Ki zurück
	 */
	public boolean entscheidung() {

		Spielfeld sf = bed.getSpielbrett().getSpielfeld()[this.getPosition()];
		switch (sf.getClass().toString()) {
		case "class monopoly.Strasse":
			return entscheidungStrasse();
		case "class monopoly.Bahnhof":
			return entscheidungBahnhof();
		case "class monopoly.Versorgungswerk":
			return entscheidungVersorgungswerk();
		case "class monopoly.Ereignisfeld":
			System.out.println("Dein Guthabenbetrag ist: " + this.getGuthaben());
			return entscheidungEreignisfeld();
		case "class monopoly.FreiParken":
			return entscheidungFreiParken();
		case "class monopoly.GeheInsGefaengnis":
			return entscheidungGeheInsGefaengnis();
		case "class monopoly.Los":
			return entscheidungLos();
		case "class monopoly.Steuer":
			return entscheidungSteuer();
		case "class monopoly.Gefaengnis":
			return entscheidungGefaengnis();
		}
		System.err.println("BUG IN KI ENTSCHEIDUNG!!!");
		return false;

	}

	/**
	 * Die Entscheidung die die Ki bei einem Bahnhofsfeld ausfuehrt. (Hier soll
	 * die Ki entscheiden ob Sie einen bahnhof kauft oder nicht.) CASE1: Die ki
	 * besitzt keinen anderen Bahnhof Die ki kauft den bahnhof, wenn sie mehr
	 * als 700 hat
	 * 
	 * CASE2: Die ki besitzt einen anderen Bahnhof Die ki kauft den bahnhof,
	 * wenn sie mehr als 600 hat
	 * 
	 * CASE3: Die ki besitzt zwei andere Bahnhoefe Die ki kauft den bahnhof,
	 * wenn sie mehr als 500 hat
	 * 
	 * CASE4: Die ki besitzt drei andere Bahnhoefe Die ki kauft den bahnhof,
	 * wenn sie mehr als 400 hat
	 * 
	 * 
	 * @return boolean true oder false fuer ja und nein.
	 */
	public boolean entscheidungBahnhof() {
		int bahnhofcounter = 0;
		ArrayList<Besitzrechtkarten> bes = this.getBesitzrechtkarten();
		for (Besitzrechtkarten be : bes) {
			if (be.istBahnhofBesitzrechtkarte()) {
				if (!(be.getSpielfeld() == null)) {
					bahnhofcounter++;
				}
			}
		}
		switch (bahnhofcounter) {
		case 0:
			if (this.getGuthaben() >= 700) {
				return true;
			} else {
				return false;
			}
		case 1:
			if (this.getGuthaben() >= 600) {
				return true;
			} else {
				return false;
			}
		case 2:
			if (this.getGuthaben() >= 500) {
				return true;
			} else {
				return false;
			}
		case 3:
			if (this.getGuthaben() >= 400) {
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}

	}

	/**
	 * Die Entscheidung die die Ki bei einem Ereignisfeld ausfuehrt.
	 * 
	 * @return boolean
	 */
	public boolean entscheidungEreignisfeld() {
		kontrolleAbgebenKI();
		return false;
	}

	/**
	 * Die Entscheidung die die Ki bei einem FreiParkenfeld ausfuehrt.
	 * 
	 * @return boolean
	 */
	public boolean entscheidungFreiParken() {
		kontrolleAbgebenKI();
		return false;
	}

	/**
	 * Die Entscheidung die die Ki bei einem Gefaengnissfeld ausfuehrt.
	 * 
	 * @return boolean true wenn die KI über ein so geringes Guthaben verfügt
	 *         dass 80% ihres vermögens sehr wenig ist (wobei das dann
	 *         eigentlich kein Sinn ergibt aber so kann sie wenigstens an den
	 *         Punkt kommen wo sie sich freikaufen kann) ansonsten wird sie
	 *         einfach nur Sinnvollerweise würfeln
	 */
	public boolean entscheidungGefaengnis() {
		if (getGefaengnisCounter() == 0) {
			return false;
		}
		if (this.getGuthaben() >= 400) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Die Entscheidung die die Ki bei einem GeheInsGefaengnisfeld ausfuehrt.
	 * 
	 * @return boolean
	 */
	public boolean entscheidungGeheInsGefaengnis() {
		kontrolleAbgebenKI();
		return false;
	}

	/**
	 * Die Entscheidung die die Ki bei einem Losfeld ausfuehrt.
	 * 
	 * @return boolean
	 */
	public boolean entscheidungLos() {
		kontrolleAbgebenKI();
		return false;
	}

	/**
	 * Die Entscheidung die die Ki bei einem Steuerfeld ausfuehrt.
	 * 
	 * @return boolean
	 */
	public boolean entscheidungSteuer() {
		kontrolleAbgebenKI();
		return true;
	}

	/**
	 * Die Entscheidung die die Ki bei einem Strassenfeld ausfuehrt.
	 * 
	 * @return boolean true oder false fuer ja und nein.
	 * 
	 *         Wann soll eine KI eine Strasse kaufen? 01 Im Falle das zwei
	 *         strassen zugehoerig sind soll die KI immer die strasse kaufen,
	 *         wenn sie bereits eine besitzt. + sie kann sie kaufen + 400
	 *         puffer. 02 Im Falle das drei strassen zugehoerig sind soll die KI
	 *         immer die strasse kaufen, wenn sie bereits zwei strassen besitzt.
	 *         + sie kann sie kaufen + 400 puffer. 03 Im Falle das zwei strassen
	 *         zugehoerig sind soll die KI immer die strasse kaufen, wenn sie
	 *         bereits eine strassen besitzt. + sie kann sie kaufen + 600
	 *         puffer. 04 Im Falle das die Ki eine Strasse kaufen soll, kauft
	 *         sie sie immer, wenn sie einen puffer von 800 hat.
	 */
	public boolean entscheidungStrasse() {

		// case 01
		Strasse dieseStrasse = (Strasse) bed.getSpielbrett().getSpielfeld()[this.getPosition()];

		if (dieseStrasse.getStrassenID() == StrassenID.dunkellila) {
			for (Besitzrechtkarten b : getBesitzrechtkarten()) {
				if (b.getSpielfeld().getClass().equals(Strasse.class)) {
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.dunkellila) {
						if (getGuthaben() >= dieseStrasse.getKaufpreis() + 400) {
							return true;
						} else {
							return false;
						}
					}
				}

			}
		}

		if (dieseStrasse.getStrassenID() == StrassenID.blau) {
			for (Besitzrechtkarten b : this.getBesitzrechtkarten()) {
				if (b.getSpielfeld().getClass().equals(Strasse.class)) {
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.blau) {
						if (getGuthaben() >= (dieseStrasse.getKaufpreis() + 400)) {
							return true;
						} else {
							return false;
						}
					}
				}

			}
		}

		// case 2 und 3
		int anzahlstrassen = 0;

		for (Besitzrechtkarten b : this.getBesitzrechtkarten()) {
			if (b.getSpielfeld().getClass().equals(Strasse.class)) {
				if (((Strasse) b.getSpielfeld()).getStrassenID() == dieseStrasse.getStrassenID()) {
					anzahlstrassen++;
				}
			}
		}

		if (anzahlstrassen == 2) {
			if (getGuthaben() >= (dieseStrasse.getKaufpreis() + 400)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (anzahlstrassen == 1) {
				if (getGuthaben() >= (dieseStrasse.getKaufpreis() + 600)) {
					return true;
				} else {
					return false;
				}
			}
		}

		// case 04
		if (getGuthaben() >= dieseStrasse.getKaufpreis() + 800) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Die Entscheidung die die Ki bei einem Versorgungswerkfeld ausfuehrt. hat
	 * die KI bereits das andere versorgungswerk kauft sie das versorgungswerk
	 * immer wenn ihr guthaben groesser gleich 300 ist. hat die KI kein
	 * versorgunswerk kauft sie immer ein versorgugnswerk wenn ihr guthaben
	 * groesser gleich 500 ist.
	 * 
	 * @return boolean true oder false fuer ja und nein.
	 */
	public boolean entscheidungVersorgungswerk() {
		Versorgungswerk veleWerk = (Versorgungswerk) bed.getSpielbrett().getSpielfeld()[12];
		Versorgungswerk vWasserWerk = (Versorgungswerk) bed.getSpielbrett().getSpielfeld()[28];
		Besitzrechtkarten wasserWerk = new Besitzrechtkarten(vWasserWerk, this);
		Besitzrechtkarten eleWerk = new Besitzrechtkarten(veleWerk, this);

		if (getPosition() == 12) {// Elektrizitaetswerk
			for (Besitzrechtkarten b : getBesitzrechtkarten()) {
				if (b.equals(wasserWerk)) {
					if (getGuthaben() >= 300) {
						return true;
					} else {
						return false;
					}
				}
			}
			if (getGuthaben() >= 500) {
				return true;
			} else {
				return false;
			}
		} else {
			if (getPosition() == 28) { // Wasserwerk
				for (Besitzrechtkarten b : getBesitzrechtkarten()) {
					if (b.equals(eleWerk)) {
						if (getGuthaben() >= 300) {
							return true;
						} else {
							return false;
						}
					}
				}
				if (getGuthaben() >= 500) {
					return true;
				} else {
					return false;
				}
			}
		}
		System.out.println("BUG IN KI-ENTSCHEIDUNG-VERSORGUNGSWERK");
		return false;
	}

	/**
	 * intelligentes Verhalten ob die ki verkaufen soll wenn es Sinn macht. hier
	 * kann die ki auch bauen, wenn es gerade lukrativ ist. bzw wenn Es sinn
	 * macht.
	 * 
	 * @return boolean true oder false fuer ja und nein.
	 */

	public boolean kontrolleAbgebenKI() {

		verkaufenKI();
		bauenKI();

		return false;
	}

	/**
	 * Wenn Ki weniger als 100 hat soll es etwas verkaufen
	 * 
	 * als erstes werden alle versorgungswerke verkauft als zweites wird pro
	 * runde alle Bahnhöfe verkauft als drittes werden einzelne Strassen
	 * verkauft als viertes wird haus und hotel abgerissen als fünftes wird eine
	 * Strasse ohne haus und Hotel abgerissen
	 * 
	 * @return boolean true oder false fuer verkauft und nicht verkauft.
	 */

	public boolean verkaufenKI() {
		int anzahl = 0;
		Besitzrechtkarten b1 = null;
		Besitzrechtkarten b2 = null;
		double vermoegen = 0;

		if (getGuthaben() <= 100) {

			// hier werden Versorgungswerke verkauft
			// es werden alle Versorgungswerke verkauft
			for (Besitzrechtkarten b : this.getBesitzrechtkarten()) {
				if (b.getSpielfeld().getClass().equals(Versorgungswerk.class)) {
					anzahl++;
					switch (anzahl) {
					case 1:
						b1 = b;
						break;
					case 2:
						b2 = b;
						break;
					default:
						break;
					}
				}
			}

			if (anzahl != 0) {
				if (anzahl == 1) {
					vermoegen = getGuthaben() + Versorgungswerk.getKaufpreis();
					setGuthaben(vermoegen);
					getBesitzrechtkarten().remove(b1);
					System.out.println("Sie haben ihre Versorgungswerkkarte verkauft.");
					return true;
				} else {
					vermoegen = getGuthaben() + Versorgungswerk.getKaufpreis();
					setGuthaben(vermoegen);
					getBesitzrechtkarten().remove(b1);
					vermoegen = getGuthaben() + Versorgungswerk.getKaufpreis();
					setGuthaben(vermoegen);
					getBesitzrechtkarten().remove(b2);
					System.out.println("Sie haben ihre beiden Versorgunswerkkarten verkauft.");
					return true;
				}
			}

			anzahl = 0;
			b1 = null;
			b2 = null;
			// hier werden Bahnhöfe verkauft
			// es wird immer nur ein bahnhof verkauft

			for (Besitzrechtkarten b : this.getBesitzrechtkarten()) {
				if (b.getSpielfeld().getClass().equals(Bahnhof.class)) {
					anzahl++;
					if (anzahl == 1) {
						b1 = b;
						vermoegen = getGuthaben() + Bahnhof.getKaufpreis();
						setGuthaben(vermoegen);
						getBesitzrechtkarten().remove(b1);
						System.out.println("Sie haben ihre Bahnhofskarte verkauft.");
						return true;
					}
				}
			}

			anzahl = 0;
			int dunkellilacount = 0;
			int hellblaucount = 0;
			int lilacount = 0;
			int orangecount = 0;
			int rotcount = 0;
			int gelbcount = 0;
			int grüncount = 0;
			int blaucount = 0;
			Besitzrechtkarten dunkellilab1 = null;
			Besitzrechtkarten hellblaub1 = null;
			Besitzrechtkarten lilab1 = null;
			Besitzrechtkarten orangeb1 = null;
			Besitzrechtkarten rotb1 = null;
			Besitzrechtkarten gelbb1 = null;
			Besitzrechtkarten grünb1 = null;
			Besitzrechtkarten blaub1 = null;
			for (Besitzrechtkarten b : this.getBesitzrechtkarten()) {
				if (b.getSpielfeld().getClass().equals(Strasse.class)) {
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.dunkellila) {
						dunkellilacount++;
						if (dunkellilab1 == null) {
							dunkellilab1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.hellblau) {
						hellblaucount++;
						if (hellblaub1 == null) {
							hellblaub1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.lila) {
						lilacount++;
						if (lilab1 == null) {
							lilab1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.orange) {
						orangecount++;
						if (orangeb1 == null) {
							orangeb1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.rot) {
						rotcount++;
						if (rotb1 == null) {
							rotb1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.gelb) {
						gelbcount++;
						if (gelbb1 == null) {
							gelbb1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.grün) {
						grüncount++;
						if (grünb1 == null) {
							grünb1 = b;
						}
					}
					if (((Strasse) b.getSpielfeld()).getStrassenID() == StrassenID.blau) {
						blaucount++;
						if (blaub1 == null) {
							blaub1 = b;
						}
					}
				}
			}
			// wenn es nichts zum verkaufen gibt "return false"

			if (dunkellilacount == 0 && hellblaucount == 0 && lilacount == 0 && orangecount == 0 && rotcount == 0
					&& gelbcount == 0 && grüncount == 0 && blaucount == 0) {
				return false;
			}

			// hier werden Strassen verkauft auf denen noch nichts gebaut wurde
			// nach abschluss "return"

			if (dunkellilacount == 1) {
				vermoegen = getGuthaben() + ((Strasse) dunkellilab1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(dunkellilab1);
				System.out
						.println("Sie haben ihre " + ((Strasse) dunkellilab1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (hellblaucount == 1 || hellblaucount == 2) {
				vermoegen = getGuthaben() + ((Strasse) hellblaub1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(hellblaub1);
				System.out.println("Sie haben ihre " + ((Strasse) hellblaub1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (lilacount == 1 || lilacount == 2) {
				vermoegen = getGuthaben() + ((Strasse) lilab1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(lilab1);
				System.out.println("Sie haben ihre " + ((Strasse) lilab1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (orangecount == 1 || orangecount == 2) {
				vermoegen = getGuthaben() + ((Strasse) orangeb1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(orangeb1);
				System.out.println("Sie haben ihre " + ((Strasse) orangeb1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (rotcount == 1 || rotcount == 2) {
				vermoegen = getGuthaben() + ((Strasse) rotb1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(rotb1);
				System.out.println("Sie haben ihre " + ((Strasse) rotb1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (gelbcount == 1 || gelbcount == 2) {
				vermoegen = getGuthaben() + ((Strasse) gelbb1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(gelbb1);
				System.out.println("Sie haben ihre " + ((Strasse) gelbb1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (grüncount == 1 || grüncount == 2) {
				vermoegen = getGuthaben() + ((Strasse) grünb1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(grünb1);
				System.out.println("Sie haben ihre " + ((Strasse) grünb1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}
			if (blaucount == 1) {
				vermoegen = getGuthaben() + ((Strasse) blaub1.getSpielfeld()).getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(blaub1);
				System.out.println("Sie haben ihre " + ((Strasse) blaub1.getSpielfeld()).getName() + " verkauft.");
				return true;
			}

			// wenn er hier landet heist es dass er alle einzelne Strassen
			// verkauft und nun anfängt haus und hotel abzureißen
			// !!man beachte!! hier reißt er nur ab VERKAUFT KEINE STRASSE

			if (dunkellilacount == 2) {
				Strasse s1 = (Strasse) dunkellilab1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[3];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (hellblaucount == 3) {
				Strasse s1 = (Strasse) hellblaub1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[8];
				}
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[9];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (lilacount == 3) {
				Strasse s1 = (Strasse) lilab1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[13];
				}
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[14];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (orangecount == 3) {
				Strasse s1 = (Strasse) orangeb1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[18];
				}
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[19];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (rotcount == 3) {
				Strasse s1 = (Strasse) rotb1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[23];
				}
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[24];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (gelbcount == 3) {
				Strasse s1 = (Strasse) gelbb1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[27];
				}
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[29];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (grüncount == 3) {
				Strasse s1 = (Strasse) grünb1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[32];
				}
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[34];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}
			if (blaucount == 2) {
				Strasse s1 = (Strasse) blaub1.getSpielfeld();
				if (s1.getHotel() == null && s1.getHaus2() == null && s1.getHaus1() == null) {
					s1 = (Strasse) bed.getSpielbrett().getSpielfeld()[39];
				}
				if (s1.getHotel() == null) {
					if (s1.getHaus2() == null) {
						if (s1.getHaus1() != null) {
							vermoegen = getGuthaben() + (Haus.getKaufpreis() / 2);
							setGuthaben(vermoegen);
							s1.setHaus1(null);
							System.out.println("Sie haben ein Haus auf der " + s1.getName() + " verkauft.");
							return true;
						}
					} else {
						vermoegen = getGuthaben() + s1.getKaufpreis() + Haus.getKaufpreis();
						setGuthaben(vermoegen);
						s1.setHaus2(null);
						System.out.println("Sie haben ihre zwei Häuser auf der " + s1.getName() + " verkauft.");
						return true;
					}
				} else {
					vermoegen = getGuthaben() + s1.getKaufpreis() + (Hotel.getKaufpreis() / 2);
					setGuthaben(vermoegen);
					s1.setHotel(null);
					System.out.println("Sie haben ihr Hotel auf der " + s1.getName() + " verkauft.");
					return true;
				}
			}

			// hier landet er wenn er keine einzelne Strassen und auf allen
			// Strassen keine Haus und Hotels mehr hat
			// verkauft wird eine Strasse

			if (dunkellilacount == 2) {
				Strasse s1 = (Strasse) dunkellilab1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(dunkellilab1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (hellblaucount == 3) {
				Strasse s1 = (Strasse) hellblaub1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(hellblaub1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (lilacount == 3) {
				Strasse s1 = (Strasse) lilab1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(lilab1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (orangecount == 3) {
				Strasse s1 = (Strasse) orangeb1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(orangeb1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (rotcount == 3) {
				Strasse s1 = (Strasse) rotb1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(rotb1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (gelbcount == 3) {
				Strasse s1 = (Strasse) gelbb1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(gelbb1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (grüncount == 3) {
				Strasse s1 = (Strasse) grünb1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(grünb1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}
			if (blaucount == 2) {
				Strasse s1 = (Strasse) blaub1.getSpielfeld();
				vermoegen = getGuthaben() + s1.getKaufpreis();
				setGuthaben(vermoegen);
				getBesitzrechtkarten().remove(blaub1);
				System.out.println("Sie haben ihre " + s1.getName() + " verkauft.");
				return true;
			}

		} else {
			return false;
		}

		return false;
	}

}
