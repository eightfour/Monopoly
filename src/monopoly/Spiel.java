package monopoly;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import monopolyGui.ResourceLoader;

/**
 * 
 * @author Mehmet,Thomas,Burak,Tim
 * @version 1.0 Diese Klasse modeliert ein Monopoly Spiel, dass man Speichern
 *          und laden kann.
 */
public class Spiel implements IBediener, IDatenzugriff, Serializable {

	// Attribute

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 5037597746848960292L;
	/**
	 * Getter fuer die Versionsnummer fuer das Serialisieren.
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Nachdem ein spiel beendet wurde, soll es seinen spielfluss
	 * wiederaufnehmen.
	 * 
	 * @param spiel
	 */
	@SuppressWarnings("unused")
	private static void spielNachWiedereintritt(Spiel spiel) {
		System.out.print("Spiel wird geladen");
		spiel.gibPunkteAus();
		spiel.setAktuelleRunde(spiel.getAktuelleRunde() + 1);
		spiel.runden();
		spiel.gewinnerErmitteln();
	}

	public static void spielNachWiedereintrittGUI(Spiel spiel) {
		System.out.print("Spiel wird geladen");
		spiel.gibPunkteAus();
		spiel.setAktuelleRunde(spiel.getAktuelleRunde() + 1);
		spiel.runden();
	}
	/**
	 * Wuefel 1 mit dem gewuerfelt wird.
	 */
	private Wuerfel wuerfel1 = new Wuerfel();
	/**
	 * Wuefel 2 mit dem gewuerfelt wird.
	 */
	private Wuerfel wuerfel2 = new Wuerfel();
	private int[] wurf = new int[3];
	/**
	 * Das Spielbrett auf dem das Spiel stattfindet.
	 */
	private Spielbrett spielbrett = new Spielbrett();
	/**
	 * ArrayList von Spielern, die am Spiel teilnehmen.
	 */
	private ArrayList<Spieler> spieler = new ArrayList<Spieler>();
	/**
	 * Ein Ereigniskartenmanager, der fuer die randomisierte Ausgabe von
	 * Ereigniskarten zustaendig ist.
	 */
	private Ereigniskartenmanager ereigniskartenmanager = new Ereigniskartenmanager();
	/**
	 * Eine Ereigniskarte.
	 */
	private Ereigniskarte ereigniskarte;
	/**
	 * Der atkuelle Spieler am Zug.
	 */
	private Spieler spielerAmZug;
	/**
	 * Ein globaler Scanner, der nicht serialisiert werden soll und deshalb
	 * transient ist.
	 */
	private transient Scanner scan = new Scanner(System.in);
	/**
	 * Die Spieleranzahl, die an einem Spiel teilnimmt.
	 */
	int spieleranzahl = 0;
	/**
	 * Farben die zur Verfuegung stehen.
	 */
	private int[] farben = { 1, 2, 3, 4, 5 };
	/**
	 * Die maximale Rundenanzahl, die von einem Spieler im Dialog gewaehlt wird.
	 */
	private long maximaleRundenAnzahl = 0;

	/**
	 * Die aktuelle Runde, die gerade gespielt wird.
	 */
	private int aktuelleRunde = 0;
	/**
	 * der Spieler, der gerade am Zug ist.
	 */
	private int spielerAmZugAktuell = 0;
	/**
	 * Das Startguthaben der Spieler, dies wird am Anfang selbst im Dialog
	 * gewaehlt.
	 */
	private int startGuthaben = 0;
	// Attribute zum loggen:
	/**
	 * Wieviele Zuege insgesammt in einem Spiel durchgefuehrt wurden.
	 */
	private int gesamtZuege = 0;
	/**
	 * Ein Array, dass die Absolutwerte, wie oft die Spieler auf den einzelnen
	 * Feldern gelandet sind speichert.
	 */
	private int[] logArr = new int[spielbrett.getSpielfeld().length];
	/**
	 * Ein Array, dass die Prozentzahlen haelt wie oft die Spieler auf die
	 * Spiel-Felder gekommen sind.
	 */
	private double[] probArr = new double[logArr.length];

	/**
	 * Wieviele Spieler das Spiel beginnen
	 */
	private int spielerAnfangsZahl = 0;

	// Konstruktoren

	/**
	 * Wieviele Kis am Spiel teilnehmen
	 */
	private int kisAnfangsZahl = 0;

	/**
	 * Wieviele Spiele gespielt wurden(Wird aus Datei ausgelesen) readOnly
	 */
	private int spieleInfosLogCount = 0;

	// Getter und Setter

	private String eingabe;
	
	/**
	 * 
	 */
	private IDatenzugriffPDF ipdf = new IDatenzugriffPDF();
	/**
	 * Standard Konstruktor der Klasse Spiel.
	 */
	public Spiel() {
	}
	

	/**
	 * Konstruktor der Klasse Spiel, der ein Spiel uebergeben wird.
	 */
	public Spiel(Spiel spiel) {
	}
	
	@Override
	public void createPdf(String pfad) {
		ipdf.createPdf(pfad);

	}
	
	@Override
	public void speichern() {
	ipdf.speichern();
	}
	
	@Override
	public void deletefiles() {
		ipdf.deletefiles();
	}
	
	
	

	/**
	 * Diese Methode behandelt die Aktion auf dem Bahnhofsfeld wenn der Spieler
	 * darauf zu stehen kommt. Wenn der Bahnhof bereits einen Besitzer hat muss
	 * der aktuelle Spieler an den Besitzer einen Betrag zahlen. Der zu zahlende
	 * Betrag richtet sich danach, wie viele Bahnhöfe der Besitzer insgesamt
	 * besitzt: 1 Bahnhof im Besitz
	 * ............................................... 25€ Miete 2 Bahnhöfe im
	 * Besitz ............................................. 50€ Miete 3 Bahnhöfe
	 * im Besitz ............................................. 100€ Miete 4
	 * Bahnhöfe im Besitz ............................................. 200€
	 * Miete
	 * 
	 * @param spieler
	 *            der aktuelle Spieler
	 * @param wurf
	 *            die gewürfelte Zahl des aktuellen Spielers (beide Augenzahlen
	 *            addiert
	 */
	public void aktionBahnhof(Spieler spieler) {

		
		if(spieler.isIstKi()){
			if(aktionBahnhofEins(spieler)){
				aktionBahnhofZwei(spieler);
			}
		}else{
			aktionBahnhofEins(spieler);
			aktionBahnhofZwei(spieler);
		}


		// int spielerPosition = spieler.getPosition();
		// double spielerGuthaben = spieler.getGuthaben();
		// Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		// Spielfeld aktuellesfeld = spielfeld[spielerPosition];
		// // boolean richtigeEingabe = false;
		// // Scanner scan = new Scanner(System.in);
		// Besitzrechtkarten b = new Besitzrechtkarten((Bahnhof) aktuellesfeld,
		// spieler);
		//
		// // Bahnhof ermitteln
		// String aktuellerBahnhofName = null;
		// if (spielerPosition == 5) {
		// aktuellerBahnhofName = "Suedbahnhof";
		// } else {
		// if (spielerPosition == 15) {
		// aktuellerBahnhofName = "Westbahnhof";
		// } else {
		// if (spielerPosition == 25) {
		// aktuellerBahnhofName = "Nordbahnhof";
		// } else {
		// if (spielerPosition == 35) {
		// aktuellerBahnhofName = "Hauptbahnhof";
		// }
		// }
		//
		// }
		// }
		//
		// // besitzer ermitteln
		// boolean hatBesitzer = false;
		// Spieler besitzer = null;
		// ArrayList<Spieler> spielerImSpiel = this.spieler;
		// for (Spieler spieleri : spielerImSpiel) {
		// for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
		// if (bes.istBahnhofBesitzrechtkarte()) {
		// if (aktuellesfeld.equals(bes.getSpielfeld())) {
		// hatBesitzer = true;
		// besitzer = spieleri;
		// }
		//
		// }
		//
		// }
		// }
		//
		// if (hatBesitzer) {
		// if (besitzer.getID() == spieler.getID()) {
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert
		// ihnen.");
		// return;
		// }
		// // hat einen
		// // wieviele bahnhoefe besitzt dieser besitzer.
		// // besitzt diesen bahnhof
		// // besitzt zwei bahhoefe
		// // besitzt drei bahhoefe
		// // besitzt 4 bahhoefe
		//
		// // besitzer besitzt diesen bahnhof
		// // das besitzkartenarray wird durchgegangen, wenn position von den
		// // besitzkartenarray bahhoefen gleich
		//
		// // 5, 15, 25, 35 dann counten, wenn count
		// int bahnhofcounter = 0;
		// ArrayList<Besitzrechtkarten> bes = besitzer.getBesitzrechtkarten();
		// for (Besitzrechtkarten be : bes) {
		// if (be.istBahnhofBesitzrechtkarte()) {
		// if (!(be.getSpielfeld() == null)) {
		// bahnhofcounter++;
		// }
		// }
		// }
		//
		// switch (bahnhofcounter) {
		// case 1:
		// if (spielerGuthaben >= 25) {
		// // true:
		// // aktueller spieler kontostand - 4* augen der würfel
		// // besitzer des werkes guthaben + 4 * augen der würfel
		// spieler.setGuthaben(spielerGuthaben - 25);
		// besitzer.setGuthaben(besitzer.getGuthaben() + 25);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes.");
		// System.out.println("Sie mussten den Betrag von " + 25 + " zahlen.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		//
		// } else {
		// besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
		// spieler.setGuthaben(0);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes."); // hier
		// // eventuell
		// // anzeigen
		// // wem
		// System.out.println("Sie konnten den Betrag von " + 25
		// + " nicht Zahlen. Sie sind pleite und werden aus dem Spiel
		// entfernt.");
		// loescheSpielerAusSpiel(spieler);
		//
		// }
		// break;
		// case 2:
		// if (spielerGuthaben >= 50) {
		// // true:
		// // aktueller spieler kontostand - 4* augen der würfel
		// // besitzer des werkes guthaben + 4 * augen der würfel
		// spieler.setGuthaben(spielerGuthaben - 50);
		// besitzer.setGuthaben(besitzer.getGuthaben() + 50);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes.");
		// System.out.println("Sie mussten den Betrag von " + 50 + " zahlen.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		//
		// } else {
		// besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
		// spieler.setGuthaben(0);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes."); // hier
		// // eventuell
		// // anzeigen
		// // wem
		// System.out.println("Sie konnten den Betrag von " + 50
		// + " nicht Zahlen. Sie sind pleite und werden aus dem Spiel
		// entfernt.");
		// loescheSpielerAusSpiel(spieler);
		//
		// }
		// break;
		// case 3:
		// if (spielerGuthaben >= 100) {
		// // true:
		// // aktueller spieler kontostand - 4* augen der würfel
		// // besitzer des werkes guthaben + 4 * augen der würfel
		// spieler.setGuthaben(spielerGuthaben - 100);
		// besitzer.setGuthaben(besitzer.getGuthaben() + 100);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes.");
		// System.out.println("Sie mussten den Betrag von " + 100 + " zahlen.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		//
		// } else {
		// besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
		// spieler.setGuthaben(0);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes."); // hier
		// // eventuell
		// // anzeigen
		// // wem
		// System.out.println("Sie konnten den Betrag von " + 100
		// + " nicht Zahlen. Sie sind pleite und werden aus dem Spiel
		// entfernt.");
		// loescheSpielerAusSpiel(spieler);
		//
		// }
		// break;
		// case 4:
		// if (spielerGuthaben >= 200) {
		// // true:
		// // aktueller spieler kontostand - 4* augen der würfel
		// // besitzer des werkes guthaben + 4 * augen der würfel
		// spieler.setGuthaben(spielerGuthaben - 200);
		// besitzer.setGuthaben(besitzer.getGuthaben() + 200);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes.");
		// System.out.println("Sie mussten den Betrag von " + 200 + " zahlen.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		//
		// } else {
		// besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
		// spieler.setGuthaben(0);
		// System.out.println("Der " + aktuellerBahnhofName + " gehoert schon
		// jemand anderes."); // hier
		// // eventuell
		// // anzeigen
		// // wem
		// System.out.println("Sie konnten den Betrag von " + 200
		// + " nicht Zahlen. Sie sind pleite und werden aus dem Spiel
		// entfernt.");
		// loescheSpielerAusSpiel(spieler);
		//
		// }
		// break;
		//
		// }
		//
		// // Wenn er nicht nur diesen bahhof besitzt muss sein
		// // besitzkartenarray durchgegangen werden und wenn
		// // der name der bahhoefe ODER ALLE, dann counten
		//
		// } else {
		// // hat keinen
		//
		// // guthaben abfragen
		//
		// // guthaben über/gleich 200 oder unter 200 - wenn unter 200 dann
		// // user rückmeldung das er nichts kaufen kann ENDE
		// // wenn über oder 200 dann kaufen?
		// // wenn nein nicht kaufen ENDE
		// // wenn ja, dann guthaben aktueller spieler -200 und aktueller
		// // spieler bekommt besitzrechtkarte ENDE
		// // String eingabe = null;
		// if (spielerGuthaben >= 200) {
		// System.out.println("Moechten Sie den " + aktuellerBahnhofName + "
		// fuer " + Bahnhof.getKaufpreis()
		// + " kaufen?(ja/nein)");
		//
		// if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() :
		// entscheidung()) {
		// spieler.setGuthaben(spieler.getGuthaben() - 200);
		// spieler.getBesitzrechtkarten().add(b);
		// System.out.println("Sie haben den " + aktuellerBahnhofName + "
		// gekauft.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		// } else {
		// System.out.println("Sie haben den " + aktuellerBahnhofName + " nicht
		// gekauft.");
		// }
		//
		// } else {
		// System.out.println("Sie koennen den " + aktuellerBahnhofName + "
		// nicht kaufen.");
		// return;
		// }
		//
		// }

	}

	public boolean aktionBahnhofEins(Spieler spieler) {
		int spielerPosition = spieler.getPosition();
		double spielerGuthaben = spieler.getGuthaben();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesfeld = spielfeld[spielerPosition];
		// boolean richtigeEingabe = false;
		// Scanner scan = new Scanner(System.in);

		// Bahnhof ermitteln
		String aktuellerBahnhofName = null;
		if (spielerPosition == 5) {
			aktuellerBahnhofName = "Suedbahnhof";
		} else {
			if (spielerPosition == 15) {
				aktuellerBahnhofName = "Westbahnhof";
			} else {
				if (spielerPosition == 25) {
					aktuellerBahnhofName = "Nordbahnhof";
				} else {
					if (spielerPosition == 35) {
						aktuellerBahnhofName = "Hauptbahnhof";
					}
				}

			}
		}

		// besitzer ermitteln
		boolean hatBesitzer = false;
		Spieler besitzer = null;
		ArrayList<Spieler> spielerImSpiel = this.spieler;
		for (Spieler spieleri : spielerImSpiel) {
			for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
				if (bes.istBahnhofBesitzrechtkarte()) {
					if (aktuellesfeld.equals(bes.getSpielfeld())) {
						hatBesitzer = true;
						besitzer = spieleri;
					}

				}

			}
		}

		if (hatBesitzer) {
			if (besitzer.getID() == spieler.getID()) {
				// System.out.println("Der " + aktuellerBahnhofName + " gehoert
				// ihnen.");
				return false;
			}
			// hat einen
			// wieviele bahnhoefe besitzt dieser besitzer.
			// besitzt diesen bahnhof
			// besitzt zwei bahhoefe
			// besitzt drei bahhoefe
			// besitzt 4 bahhoefe

			// besitzer besitzt diesen bahnhof
			// das besitzkartenarray wird durchgegangen, wenn position von den
			// besitzkartenarray bahhoefen gleich

			// 5, 15, 25, 35 dann counten, wenn count
			int bahnhofcounter = 0;
			ArrayList<Besitzrechtkarten> bes = besitzer.getBesitzrechtkarten();
			for (Besitzrechtkarten be : bes) {
				if (be.istBahnhofBesitzrechtkarte()) {
					if (!(be.getSpielfeld() == null)) {
						bahnhofcounter++;
					}
				}
			}

			switch (bahnhofcounter) {
			case 1:
				if (spielerGuthaben >= 25) {
					// true:
					// aktueller spieler kontostand - 4* augen der würfel
					// besitzer des werkes guthaben + 4 * augen der würfel
					spieler.setGuthaben(spielerGuthaben - 25);
					besitzer.setGuthaben(besitzer.getGuthaben() + 25);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes.");
					System.out.println("Sie mussten den Betrag von " + 25 + " zahlen.");
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());

				} else {
					besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
					spieler.setGuthaben(0);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes."); // hier
																											// eventuell
																											// anzeigen
																											// wem
					System.out.println("Sie konnten den Betrag von " + 25
							+ " nicht Zahlen. Sie sind pleite und werden aus dem Spiel entfernt.");
					loescheSpielerAusSpiel(spieler);

				}
				break;
			case 2:
				if (spielerGuthaben >= 50) {
					// true:
					// aktueller spieler kontostand - 4* augen der würfel
					// besitzer des werkes guthaben + 4 * augen der würfel
					spieler.setGuthaben(spielerGuthaben - 50);
					besitzer.setGuthaben(besitzer.getGuthaben() + 50);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes.");
					System.out.println("Sie mussten den Betrag von " + 50 + " zahlen.");
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());

				} else {
					besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
					spieler.setGuthaben(0);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes."); // hier
																											// eventuell
																											// anzeigen
																											// wem
					System.out.println("Sie konnten den Betrag von " + 50
							+ " nicht Zahlen. Sie sind pleite und werden aus dem Spiel entfernt.");
					loescheSpielerAusSpiel(spieler);

				}
				break;
			case 3:
				if (spielerGuthaben >= 100) {
					// true:
					// aktueller spieler kontostand - 4* augen der würfel
					// besitzer des werkes guthaben + 4 * augen der würfel
					spieler.setGuthaben(spielerGuthaben - 100);
					besitzer.setGuthaben(besitzer.getGuthaben() + 100);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes.");
					System.out.println("Sie mussten den Betrag von " + 100 + " zahlen.");
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());

				} else {
					besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
					spieler.setGuthaben(0);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes."); // hier
																											// eventuell
																											// anzeigen
																											// wem
					System.out.println("Sie konnten den Betrag von " + 100
							+ " nicht Zahlen. Sie sind pleite und werden aus dem Spiel entfernt.");
					loescheSpielerAusSpiel(spieler);

				}
				break;
			case 4:
				if (spielerGuthaben >= 200) {
					// true:
					// aktueller spieler kontostand - 4* augen der würfel
					// besitzer des werkes guthaben + 4 * augen der würfel
					spieler.setGuthaben(spielerGuthaben - 200);
					besitzer.setGuthaben(besitzer.getGuthaben() + 200);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes.");
					System.out.println("Sie mussten den Betrag von " + 200 + " zahlen.");
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());

				} else {
					besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
					spieler.setGuthaben(0);
					System.out.println("Der " + aktuellerBahnhofName + " gehoert schon jemand anderes."); // hier
																											// eventuell
																											// anzeigen
																											// wem
					System.out.println("Sie konnten den Betrag von " + 200
							+ " nicht Zahlen. Sie sind pleite und werden aus dem Spiel entfernt.");
					loescheSpielerAusSpiel(spieler);

				}
				break;

			}

			// Wenn er nicht nur diesen bahhof besitzt muss sein
			// besitzkartenarray durchgegangen werden und wenn
			// der name der bahhoefe ODER ALLE, dann counten

		} else {
			if (spielerGuthaben < 200) {
				System.out.println("Sie koennen den " + aktuellerBahnhofName + " nicht kaufen.");
			} else {
				return true;
			}
		}
		return false;
	}

	public void aktionBahnhofZwei(Spieler spieler) {
		int spielerPosition = spieler.getPosition();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesfeld = spielfeld[spielerPosition];
		// boolean richtigeEingabe = false;
		// Scanner scan = new Scanner(System.in);
		Besitzrechtkarten b = new Besitzrechtkarten(aktuellesfeld, spieler);

		// Bahnhof ermitteln
		String aktuellerBahnhofName = null;
		if (spielerPosition == 5) {
			aktuellerBahnhofName = "Suedbahnhof";
		} else {
			if (spielerPosition == 15) {
				aktuellerBahnhofName = "Westbahnhof";
			} else {
				if (spielerPosition == 25) {
					aktuellerBahnhofName = "Nordbahnhof";
				} else {
					if (spielerPosition == 35) {
						aktuellerBahnhofName = "Hauptbahnhof";
					}
				}

			}
		}

		System.out.println(
				"Moechten Sie den " + aktuellerBahnhofName + " fuer " + Bahnhof.getKaufpreis() + " kaufen?(ja/nein)");

		if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() : entscheidung()) {
			spieler.setGuthaben(spieler.getGuthaben() - 200);
			spieler.getBesitzrechtkarten().add(b);
			System.out.println("Sie haben den " + aktuellerBahnhofName + " gekauft.");
			System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());
		} else {
			System.out.println("Sie haben den " + aktuellerBahnhofName + "  nicht gekauft.");
		}

	}

	/**
	 * Eine Ereigniskarte wird gezogen und jedes Feld wird einzeln abgefragt und
	 * abgearbeitet. Betrag, Position, ZahleAnSpieler, Haeuser und Hotel
	 * 
	 * @param spielerKi
	 *            der auf dem Feld landet.
	 */
	@Override
	public void aktionEreignisfeld() {
		Spieler spielerKi = spielerAmZug;
		if (spielerKi.isIstKi()) {
			spielerKi = spielerAmZug;
		}
		System.out.println(ereigniskarte);

		// Gefaengnis
		if (ereigniskarte.getPos() - 1 == 10) {
			aktionGeheInsGefaengnis(spielerKi);
		}
		if (ereigniskarte.getPos() != 10 || ereigniskarte.getBetrag() != 0 || ereigniskarte.getHaus() != 0
				|| ereigniskarte.getHotel() != 0 || ereigniskarte.getZahlenSpieler() != 0) {

			// Betraege zahlen oder bekommen
			if (ereigniskarte.getBetrag() != 0) {
				if (ereigniskarte.getPos() == 0) {
					spielerKi.setGuthaben((spielerKi.getGuthaben() + ereigniskarte.getBetrag()));
				}
				// Wenn man über Los kommt Bezahlung (wenn in der csv
				// vorhanden)!
				if (ereigniskarte.getPos() == 1) {
					spielerKi.setGuthaben((spielerKi.getGuthaben() + ereigniskarte.getBetrag()));
				}
				if (ereigniskarte.getPos() > 1 && spielerKi.getPosition() > ereigniskarte.getPos()) {
					spielerKi.setGuthaben((spielerKi.getGuthaben() + ereigniskarte.getBetrag()));
				}
			}

			// An allen Zahlen oder bekommen
			if (ereigniskarte.getZahlenSpieler() != 0) {
				for (Spieler s : this.spieler) {
					if (s.getFarbe() != spielerKi.getFarbe()) {
						spielerKi.setGuthaben(spielerKi.getGuthaben() + ereigniskarte.getZahlenSpieler());
						s.setGuthaben(s.getGuthaben() + ((ereigniskarte.getZahlenSpieler() * -1)));
					}
				}
			}

			// Zahlen od. Bekommen für Haeuser
			if (ereigniskarte.getHaus() != 0) {
				int haeuser = 0;
				if (spielerKi.getBesitzrechtkarten() != null) {
					for (Besitzrechtkarten a : spielerKi.getBesitzrechtkarten()) {
						if (a.istStrassenBesitzrechtkarte()) {
							if (((Strasse) a.getSpielfeld()).getHaus1() != null) {
								haeuser += 1;
							}
							if (((Strasse) a.getSpielfeld()).getHaus2() != null) {
								haeuser += 1;
							}
						}
					}
				}
				spielerKi.setGuthaben((spielerKi.getGuthaben() + (haeuser * ereigniskarte.getHaus())));
			}

			// Zahlen od. Bekommen für Hotels
			if (ereigniskarte.getHotel() != 0) {
				int hotels = 0;
				if (spielerKi.getBesitzrechtkarten() != null) {
					for (Besitzrechtkarten a : spielerKi.getBesitzrechtkarten()) {
						if (a.istStrassenBesitzrechtkarte()) {
							if (((Strasse) a.getSpielfeld()).getHotel() != null) {
								hotels += 1;
							}
						}
					}
				}
				spielerKi.setGuthaben((spielerKi.getGuthaben() + (hotels * ereigniskarte.getHotel())));
			}
			// Positionswechsel vorwärts
			if (ereigniskarte.getPos() > 0 && ereigniskarte.getPos() - 1 != 10) {
				if (ereigniskarte.getPos() > 99) {
					if (spielerKi.getPosition() + (ereigniskarte.getPos() / 100) > 39) {
						spielerKi.setPosition((spielerKi.getPosition()
								+ ((ereigniskarte.getPos() / 100) - spielbrett.getSpielfeld().length - 1)));
					} else {
						spielerKi.setPosition(spielerKi.getPosition() + (ereigniskarte.getPos() / 100));
					}

				} else {
					spielerKi.setPosition(ereigniskarte.getPos() - 1);
				}
				if (ereigniskarte.getPos() != 1) {
					aktionSpielfeld();
				}
			}

			// Positionswechsel rückwärts
			if (ereigniskarte.getPos() < 0) {
				if ((spielerKi.getPosition() + (ereigniskarte.getPos() / 100)) < 0) {
					// Wenn die Position hinter Los liegt wird der Rest von
					// Schlossallee zurück gesetzt.
					spielerKi.setPosition(spielbrett.getSpielfeld().length - 1
							+ (spielerKi.getPosition() + (ereigniskarte.getPos() / 100)));
					if (ereigniskarte.getBetrag() != 0) {
						spielerKi.setGuthaben(spielerKi.getGuthaben() + ereigniskarte.getBetrag());
					}
				} else {
					// Wenn die Position nicht hinter Los liegt wird normal
					// berechnet.
					spielerKi.setPosition((spielerKi.getPosition() + (ereigniskarte.getPos() / 100)));
				}
				if (ereigniskarte.getPos() != 1) {

					aktionSpielfeld();
				}
			}

		}
		// Checke ob ein Spieler pleite geht nach einer Ereigniskarte
		for (int i = 0; i < this.spieler.size(); i++) {
			if (this.spieler.get(i).getGuthaben() <= 0) {
				loescheSpielerAusSpiel(this.spieler.get(i));
			}
		}
		
	}

	/**
	 * Die Aktion, die eintritt, wenn ein Spieler das Freiparkenfeld betritt.
	 * 
	 * @param spieler
	 *            der auf dem Feld landet.
	 */
	public void aktionFreiParken(Spieler spieler) {
		System.out.println("Sie sind auf dem Freiparkenfeld.");
		/* Freiparkengeld wird dem aktuellen spieler angerechnet. */}

	// Momentan zaehlt aktionGefaengnis an zu counten wenn man in das Gefaengnis
	// kommt.
	/**
	 * Wenn der Spieler auf das Gefängnisfeld gesetzt wird tritt folgendes ein:
	 * -Es wird geprüft seit wiele Runden er im Gefängnis ist. - Es wird geprüft
	 * ob der Spieler Pasch gewürfelt hat Wenn nicht darf er noch zwei mal
	 * würfeln Ansonsten wird der Spieler gefragt ob er 80% seines Vermögens
	 * abgeben will um raus zu kommen
	 * 
	 * @param spieler
	 *            der aktuelle Spieler und zwei augenzahlen
	 * @param spieler
	 *            der aktuelle Spieler
	 * @return übertragen ob er aus dem Gefängnis raus is oder nicht.
	 */

	public boolean aktionGefaengnis(Spieler spieler) {
		int augen1 = new Wuerfel().wuerfeln();
		int augen2 = new Wuerfel().wuerfeln();

		switch (spieler.getGefaengnisCounter()) {
		case 3:
			System.out.println("Sie sind im Gefaengnis gelandet.");
			spieler.setGefaengnisCounter(spieler.getGefaengnisCounter() - 1);
			return false;
		case 2:
			// frage ob nichts machen, wuerfeln oder 80% abgeben
			return aktionGefaengnis2b1(spieler, augen1, augen2);
		// return aktionGefaengnisZweiBisEins(spieler, augen1, augen2);
		case 1:
			// frage ob nichts machen, wuerfeln oder 80% abgeben
			return aktionGefaengnis2b1(spieler, augen1, augen2);
		// return aktionGefaengnisZweiBisEins(spieler, augen1, augen2);
		case 0:
			// frage ob nichts machen
			// System.out.println("Wollen Sie im Gefaengnis bleiben?(ja/nein)");

			// if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() :
			// entscheidung()) {
			// System.out.println("Sie bleiben im Gefaengnis");
			// return false;
			// } else {
			System.out.println("Sie kommen aus dem Gefaengnis.");
			spieler.setGefaengnisCounter(3);
			return true;
		// }
		}
		System.out.println("BUG DETECTED!!! BITTE METHODE AKTION GEFAENGNIS PRUEFEN!!!");
		return false;
	}

	@Override
	public boolean aktionGefaengnis2(boolean ent) {
		if (!ent) {
			
			spielerAmZug.setGuthaben((spielerAmZug.getGuthaben() * 0.2));
			System.out.println("Sie haben geblecht.");
			System.out.println("Ihr Guthaben: " + spielerAmZug.getGuthaben());
			return true;
		} else {
			if (spielerAmZug.getGefaengnisCounter() > 0) {
				wuerfeln();
				if (wurf[0] == wurf[1]) {
					spielerAmZug.setGefaengnisCounter(3);
					System.out.println("Knastausbruch, du kommst raus... Würfel freigeschaltet.");
					return true;
				} else {
					spielerAmZug.setGefaengnisCounter(spielerAmZug.getGefaengnisCounter() - 1);
					System.out.println("Sorry eine Runde länger im Gefängnis.");
					return false;
				}
			} else {
				spielerAmZug.setGefaengnisCounter(3);
				System.out.println("Du hast deine Strafe abgesessen... Würfel freigeschaltet.");
				return true;
			}
		}
	}

	/**
	 * Hilfsmethode fuer aktionGefaengnis, wenn Spielergefaengniscounter auf 2
	 * und 1 wird der dialog gefuehrt ob der spieler nichts machen moechte, 80%
	 * seines vermogens abgeben moechte oder wuerfeln moechte.
	 * 
	 * @param spieler
	 * @param augen1
	 * @param augen2
	 * @return
	 */
	public boolean aktionGefaengnis2b1(Spieler spieler, int augen1, int augen2) {
		// System.out.println("Drücken Sie ja zum Würfeln.");
		// System.out.println("Wenn sie nein drücken wird automatisch 80%
		// gezahlt");
		if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() : entscheidung()) {
			//
			return aktionGefaengnisWuerfeln(spieler, augen1, augen2);
			//
		} else {
//			spieler.setGuthaben(((int)(spieler.getGuthaben()*0.2*100))/100);
			System.out.println(
					"Ihr neues Guthaben betraegt: " + spieler.getGuthaben() + " - " + (spieler.getGuthaben()*0.8) + " = " + (spieler.getGuthaben()*0.2));
			spieler.setGuthaben((spieler.getGuthaben()*0.2));
			spieler.setGefaengnisCounter(3);
			return false;
		}

	}

	public boolean aktionGefaengnisWuerfeln(Spieler spieler, int augen1, int augen2) {
		int[] wurfNeu = wuerfeln();
		System.out.println("Sie haben: " + wurfNeu[0] + " und " + wurfNeu[1] + " = " + wurfNeu[2] + " gewuerfelt.");
		if (augen1 == augen2) {
			spieler.setGefaengnisCounter(3);
			System.out.println("Sie kommen aus dem Gefaengnis Frei.");
			return true;
		} else {
			System.out.println("Sie kommen nicht aus dem Gefaengnis Frei.");
			spieler.setGefaengnisCounter(spieler.getGefaengnisCounter() - 1);
			return false;
		}
	}

	public boolean aktionGefaengnisZweiBisEins(Spieler spieler, int augen1, int augen2) {
		// frage ob nichts machen, wuerfeln oder 80% abgeben
		System.out.println("Wenn Sie wuerfeln wollen oder 80% ihres Guthabens zahlen moechten geben sie \"ja\" ein");
		System.out.println("Wenn Sie nichts machen wollen, geben Sie \"nein\" ein.");
		if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() : entscheidung()) {
			System.out.println("Wollen Sie wuerfeln? Geben Sie \"ja\" ein wenn Sie wuerfeln moechten.");
			System.out.println(
					"Geben Sie \"nein\" ein, wenn Sie 80% ihres Vermoegens zahlen wollen um aus dem Gefaengnis frei zu kommen.");
			if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() : entscheidung()) {
				// System.out.println("Druecken Sie eine beliebige Zahl zum
				// wuerfeln.");
				// beliebigeZahlDruecken();
				System.out.println("Sie haben: " + augen1 + " und " + augen2 + " = " + wurf[2] + " gewuerfelt.");
				if (augen1 == augen2) {
					spieler.setGefaengnisCounter(3);
					System.out.println("Sie kommen aus dem Gefaengnis Frei.");
					return true;
				} else {
					System.out.println("Sie kommen nicht aus dem Gefaengnis Frei.");
					spieler.setGefaengnisCounter(spieler.getGefaengnisCounter() - 1);
					return false;
				}
			} else {
				double guthaben = spieler.getGuthaben();
				double guthabenNeu = (guthaben * 20) / 100;
				spieler.setGuthaben(guthabenNeu);
				System.out.println(
						"Ihr neues Guthaben betraegt: " + guthaben + "-" + ((guthaben * 20) / 100) + "=" + guthabenNeu);
				spieler.setGefaengnisCounter(3);
				return true;
			}
		} else {
			spieler.setGefaengnisCounter(spieler.getGefaengnisCounter() - 1);
			System.out.println("Sie bleiben im Gefaengnis");
			return false;
		}
	}

	/**
	 * Die Aktion, die eintritt, wenn ein Spieler auf das
	 * gehe-ins-Gefaengnis-Feld kommt. Er wird auf die Position auf dem das
	 * Gefaengnis ist verschoben. Desweiteren wird eine Information auf der
	 * Konsole ausgegeben.
	 * 
	 * @param spieler
	 *            der auf dem Feld landet.
	 */

	public void aktionGeheInsGefaengnis(Spieler spieler) {
		logArr[30]++;
		spieler.setPosition(10);
		System.out.println("Sie werden nun in das Gefaengnis versezt.");
		// aktionGefaengnis(spieler);

	}

	/**
	 * Die Aktion, die eintritt, wenn ein Spieler auf das Los-Feld kommt. Er
	 * bekommt +500 und dies wird auf der Konsole ausgebenen.
	 * 
	 * @param spieler
	 *            der auf dem Feld landet.
	 */
	public void aktionLos(Spieler spieler) {
		System.out.println("Sie kommen auf Los und streichen 200 ein.");
		spieler.setGuthaben(spieler.getGuthaben() + 200);
		System.out.println("Dein neuer Guthabenbetrag ist: " + spieler.getGuthaben());
	}

	public void aktionSpielfeld() {
		switch (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().toString()) {
		// case "class monopoly.Strasse":
		// aktionStrasse(spielerAmZug);
		// break;

		// case "class monopoly.Bahnhof":
		// aktionBahnhof(spielerAmZug);
		//
		// break;
		// case "class monopoly.Versorgungswerk":
		// aktionVersorgungswerk(spielerAmZug);
		//
		// break;
//		case "class monopoly.Ereignisfeld":
//			aktionEreignisfeld(spielerAmZug);
//			System.out.println("Dein Guthabenbetrag ist: " + spielerAmZug.getGuthaben());
//			break;
		case "class monopoly.FreiParken":
			aktionFreiParken(spielerAmZug);

			break;
		case "class monopoly.GeheInsGefaengnis":
			aktionGeheInsGefaengnis(spielerAmZug);

			break;
		// case "class monopoly.Los":
		// aktionLos(spielerAmZug);
		// break;
		case "class monopoly.Steuer":
			aktionSteuer(spielerAmZug);

			break;
		// case "class monopoly.Gefaengnis":
		// // System.out.println("Sie sind im Gefaengnis.");
		// aktionGefaengnis(spielerAmZug);
		// return;
		default:
			// System.out.println(spielbrett.geteinSpielfeld(spielerAmZug.getPosition()));
		}

	}

	/**
	 * aktionSpielfeld bekommt den aktuellen wurf, das Spielfeld auf dem der
	 * aktuelle Spieler steht und den Spieler. Hier wird je nach Feld
	 * entschieden, was fuer eine Aktion ausgefuert wird.
	 * 
	 */
	public void aktionSpielfeld(Spieler spieler, Spielfeld neuesFeld) {
		switch (neuesFeld.getClass().toString()) {
		case "class monopoly.Strasse":
			aktionStrasse(spieler);

			break;
		case "class monopoly.Bahnhof":
			aktionBahnhof(spieler);

			break;
		case "class monopoly.Versorgungswerk":
			aktionVersorgungswerk(spieler);

			break;
		case "class monopoly.Ereignisfeld":
			aktionEreignisfeld();
			System.out.println("Dein Guthabenbetrag ist: " + spieler.getGuthaben());
			break;
		case "class monopoly.FreiParken":
			aktionFreiParken(spieler);

			break;
		case "class monopoly.GeheInsGefaengnis":
			aktionGeheInsGefaengnis(spieler);

			break;
		case "class monopoly.Los":
			aktionLos(spieler);

			break;
		case "class monopoly.Steuer":
			aktionSteuer(spieler);

			break;
		case "class monopoly.Gefaengnis":
			// System.out.println("Sie sind im Gefaengnis.");
			aktionGefaengnis(spieler);
			return;
		default:
		}
	}

	/**
	 * Die Aktion, die eintritt, wenn ein Spieler auf ein Steuer-Feld kommt. Dem
	 * Spieler wird der entsprechende Betrag abgebucht. Wenn er nicht
	 * zahlungsfaehig ist, wird er aus dem Spiel genommen. Zusatzsteuer betraegt
	 * 10% des Einkommens. Einkommenssteuer betraegt 30% des Einkommens.
	 * 
	 * @param spieler
	 *            der auf dem Feld landet.
	 */
	public void aktionSteuer(Spieler spieler) {

		double zusatzSteuer = spieler.getGuthaben() / 10;
		double einkommensSteuer = (spieler.getGuthaben() * 3) / 10;

		double neuesGuthabenZussatzSteuer = spieler.getGuthaben() - zusatzSteuer;
		double neuesGuthabenEinkommensSteuer = spieler.getGuthaben() - einkommensSteuer;

		if (spieler.getPosition() == 4) {
			if (spieler.getGuthaben() >= einkommensSteuer) {
				System.out.println(
						"Sie sind auf dem Einkommenssteuerfeld gelandet. Die Steuer betraegt 30% ihres Guthabens.");
				System.out.println("Aktuelles Guthaben:" + spieler.getGuthaben() + "-" + einkommensSteuer + "="
						+ neuesGuthabenEinkommensSteuer);
				spieler.setGuthaben(neuesGuthabenEinkommensSteuer);
				System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());
			} else {
				System.out.println(
						"Sie sind auf dem Einkommenssteuerfeld gelandet. Die Steuer betraegt 30% ihres Guthabens.");
				System.out.println("Sie koennen die Steuer nicht bezahlen und werden aus dem Spiel genommen.");
				spieler.setGuthaben(0);
				loescheSpielerAusSpiel(spieler);

			}

		} else {
			if (spieler.getPosition() == 38) {
				if (spieler.getGuthaben() >= zusatzSteuer) {
					System.out.println(
							"Sie sind auf dem Zusatzsteuerfeld gelandet. Die Steuer betraegt 10% ihres Guthabens.");
					System.out.println("Aktuelles Guthaben:" + spieler.getGuthaben() + "-" + zusatzSteuer + "="
							+ neuesGuthabenZussatzSteuer);
					spieler.setGuthaben(neuesGuthabenZussatzSteuer);
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());
				} else {
					System.out.println(
							"Sie sind auf dem Zusatzsteuerfeld gelandet. Die Steuer betraegt 10% ihres Guthabens.");
					System.out.println("Sie koennen die Steuer nicht bezahlen und werden aus dem Spiel genommen.");
					spieler.setGuthaben(0);
					loescheSpielerAusSpiel(spieler);
				}

			}

		}
	}

	/**
	 * Diese Methode wird aufgerufen wenn der aktuelle Spieler auf ein
	 * Strassenfeld läuft hierbei wird abgefragt, ob die Strasse schon einen
	 * Besitzer hat. Wenn sie einen Besitzer hat wird festgestellt wieviel der
	 * Spieler an den Besitzer zahlen muss. Das ist abhängig davon ob auf der
	 * Strasse ein/zwei Haus oder Hotel steht
	 * 
	 * @param spieler
	 *            der aktuelle Spieler
	 */
	public void aktionStrasse(Spieler spieler) {
		if(spieler.isIstKi()){
			if(aktionStrasseEins(spieler)){
				aktionStrassesZwei(spieler);
			}
		}else{
		
		aktionStrasseEins(spieler);
		
		aktionStrassesZwei(spieler);
		}
		// int spielerPosition = spieler.getPosition();
		// double spielerGuthaben = spieler.getGuthaben();
		// Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		// Spielfeld aktuellesfeld = spielfeld[spielerPosition];
		//
		// // besitzer ermitteln
		// boolean hatBesitzer = false;
		// Spieler besitzer = null;
		// ArrayList<Spieler> spielerImSpiel = this.spieler;
		// for (Spieler spieleri : spielerImSpiel) {
		// for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
		// if (bes.istStrassenBesitzrechtkarte()) {
		// if (aktuellesfeld.equals(bes.getSpielfeld())) {
		// hatBesitzer = true;
		// besitzer = spieleri;
		// }
		// }
		//
		// }
		// }
		//
		// Strasse s = (Strasse) aktuellesfeld;
		// if (hatBesitzer) {
		// if (besitzer.getID() == spieler.getID()) {
		// System.out.println("Der " + s.getName() + " gehoert ihnen.");
		// return;
		// }
		// if (s.getHotel() == null) {
		// if (s.getHaus1() == null) {
		//
		// int betrag = (s.getKaufpreis() / 10);
		// if (betrag > spieler.getGuthaben()) {
		// System.out.println(
		// "Sie haben nicht genügend Geld und werden aus dem Spiel entfernt.
		// Spielerfarbe:"
		// + spieler.getFarbe());
		// loescheSpielerAusSpiel(spieler);
		// return;
		// }
		// spieler.setGuthaben(spielerGuthaben - betrag);
		// besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
		// System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
		// return;
		// } else {
		// if (s.getHaus2() == null) {
		// int betrag = ((s.getKaufpreis() / 10) * 2);
		// if (betrag > spieler.getGuthaben()) {
		// System.out.println(
		// "Sie haben nicht genügend Geld und werden aus dem Spiel entfernt.
		// Spielerfarbe:"
		// + spieler.getFarbe());
		// loescheSpielerAusSpiel(spieler);
		// return;
		// }
		// spieler.setGuthaben(spielerGuthaben - betrag);
		// besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
		// System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
		// return;
		// }
		//
		// int betrag = ((s.getKaufpreis() / 10) * 4);
		// if (betrag > spieler.getGuthaben()) {
		// System.out.println(
		// "Sie haben nicht genügend Geld und werden aus dem Spiel entfernt.
		// Spielerfarbe:"
		// + spieler.getFarbe());
		// loescheSpielerAusSpiel(spieler);
		// return;
		// }
		// spieler.setGuthaben(spielerGuthaben - betrag);
		// besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
		// System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
		// return;
		// }
		//
		// } else {
		// int betrag = ((s.getKaufpreis() / 10) * 8);
		// if (betrag > spieler.getGuthaben()) {
		// System.out.println("Sie haben nicht genügend Geld und werden aus dem
		// Spiel entfernt. Spielerfarbe:"
		// + spieler.getFarbe());
		// loescheSpielerAusSpiel(spieler);
		// return;
		// }
		// spieler.setGuthaben(spielerGuthaben - betrag);
		// besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
		// System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
		// return;
		// }
		//
		// } else {
		// if (spieler.getGuthaben() < s.getKaufpreis()) {
		// System.out.println("Sie haben nicht genügend Geld um die Straße zu
		// kaufen!!");
		// System.out.println("der nächste ist drann!");
		// return;
		// }
		// System.out.println("Möchtest du Die " + s.getName() + " fuer " +
		// s.getKaufpreis() + " kaufen? (ja/nein)");
		// if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() :
		// entscheidung()) {
		// double umrechner = spieler.getGuthaben();
		// umrechner -= s.getKaufpreis();
		// spieler.setGuthaben(umrechner);
		// System.out.println("Dein neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		// Besitzrechtkarten b = new Besitzrechtkarten((Strasse) aktuellesfeld,
		// spieler);
		// spieler.getBesitzrechtkarten().add(b);
		// darfbauen(spieler, s);
		// return;
		// } else {
		// System.out.println("Du hast die Strasse nicht gekauft.");
		// return;
		// }
		// }
	}

	public boolean aktionStrasseEins(Spieler spieler) {
		int spielerPosition = spieler.getPosition();
		double spielerGuthaben = spieler.getGuthaben();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesfeld = spielfeld[spielerPosition];

		// besitzer ermitteln
		boolean hatBesitzer = false;
		Spieler besitzer = null;
		ArrayList<Spieler> spielerImSpiel = this.spieler;
		for (Spieler spieleri : spielerImSpiel) {
			for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
				if (bes.istStrassenBesitzrechtkarte()) {
					if (aktuellesfeld.equals(bes.getSpielfeld())) {
						hatBesitzer = true;
						besitzer = spieleri;
					}
				}
			}
		}

		Strasse s = (Strasse) aktuellesfeld;
		if (hatBesitzer) {
			if (besitzer.getID() == spieler.getID()) {
				// System.out.println("Der " + s.getName() + " gehoert ihnen.");
				return false;
			}
			if (s.getHotel() == null) {
				if (s.getHaus1() == null) {

					int betrag = (s.getKaufpreis() / 10);
					if (betrag > spieler.getGuthaben()) {
						System.out.println(
								"Sie haben nicht genügend Geld und werden aus dem Spiel entfernt. Spielerfarbe:"
										+ spieler.getFarbe());
						loescheSpielerAusSpiel(spieler);
						return false;
					}
					spieler.setGuthaben(spielerGuthaben - betrag);
					besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
					System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
					return false;
				} else {
					if (s.getHaus2() == null) {
						int betrag = ((s.getKaufpreis() / 10) * 2);
						if (betrag > spieler.getGuthaben()) {
							System.out.println(
									"Sie haben nicht genügend Geld und werden aus dem Spiel entfernt. Spielerfarbe:"
											+ spieler.getFarbe());
							loescheSpielerAusSpiel(spieler);
							return false;
						}
						spieler.setGuthaben(spielerGuthaben - betrag);
						besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
						System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
						return false;
					}

					int betrag = ((s.getKaufpreis() / 10) * 4);
					if (betrag > spieler.getGuthaben()) {
						System.out.println(
								"Sie haben nicht genügend Geld und werden aus dem Spiel entfernt. Spielerfarbe:"
										+ spieler.getFarbe());
						loescheSpielerAusSpiel(spieler);
						return false;
					}
					spieler.setGuthaben(spielerGuthaben - betrag);
					besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
					System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
					return false;
				}

			} else {
				int betrag = ((s.getKaufpreis() / 10) * 8);
				if (betrag > spieler.getGuthaben()) {
					System.out.println("Sie haben nicht genügend Geld und werden aus dem Spiel entfernt. Spielerfarbe:"
							+ spieler.getFarbe());
					loescheSpielerAusSpiel(spieler);
					return false;
				}
				spieler.setGuthaben(spielerGuthaben - betrag);
				besitzer.setGuthaben(besitzer.getGuthaben() + betrag);
				System.out.println("Du hast dem Besitzer " + betrag + " gezahlt");
				return false;
			}

		} else {
			if (spieler.getGuthaben() < s.getKaufpreis()) {
				System.out.println("Sie haben nicht genügend Geld um die Straße zu kaufen!!");
				System.out.println("der nächste ist drann!");
				return false;
			} else {
				return true;
			}

		}

	}

	public void aktionStrassesZwei(Spieler spieler) {
		int spielerPosition = spieler.getPosition();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesfeld = spielfeld[spielerPosition];

		Strasse s = (Strasse) aktuellesfeld;

		
		if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() : entscheidung()) {
			double umrechner = spieler.getGuthaben();
			umrechner -= s.getKaufpreis();
			spieler.setGuthaben(umrechner);
			System.out.println("Dein neuer Guthabenbetrag ist: " + spieler.getGuthaben());
			System.out.println(spielerAmZug.getName()+" kauft die " + aktuellesfeld.getName());
			Besitzrechtkarten b = new Besitzrechtkarten(aktuellesfeld, spieler);
			spieler.getBesitzrechtkarten().add(b);
			// darfbauen(spieler, s);
		} else {
			System.out.println("Du hast die Strasse nicht gekauft.");

		}

	}

	/**
	 * Die Aktion, die ausgefuehrt wird, wenn der Spieler auf einem
	 * Versorgungswerk landet. Der Spieler kann ein Versorgungswerk kaufen, oder
	 * muss entsprechend Miete zahlen, wenn es bereits einem anderen Besitzer
	 * gehoert. Sollte er Zahlungsunfaehig sein wird er aus dem Spiel genommen.
	 * 
	 * @param spieler
	 *            der aktuelle Spieler
	 * @param wurf
	 *            die gewürfelte Zahl des aktuellen Spielers (beide Augenzahlen
	 *            addiert
	 */
	public void aktionVersorgungswerk(Spieler spieler) {

		if(spieler.isIstKi()){
			if(aktionVersorgungswerkEins(spieler)){
				aktionVersorgungswerkZwei(spieler);
			}
		}else{
			aktionVersorgungswerkEins(spieler);
			aktionVersorgungswerkZwei(spieler);
		}
		


		// // , int[] wurf
		// ) {
		// // int wurfs = wurf[2];
		// int spielerPosition = spieler.getPosition();
		// double spielerGuthaben = spieler.getGuthaben();
		// Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		// Spielfeld aktuellesfeld = spielfeld[spielerPosition];
		// Besitzrechtkarten b = new Besitzrechtkarten((Versorgungswerk)
		// aktuellesfeld, spieler);
		//
		// // Versorgungswerk ermitteln
		// String aktuellesWerkName = null;
		// if (spielerPosition == 12) {
		// aktuellesWerkName = "Elektrizitaetswerk";
		// } else {
		// if (spielerPosition == 28) {
		// aktuellesWerkName = "Wasserwerk";
		// }
		// }
		//
		// // besitzer ermitteln
		// boolean hatBesitzer = false;
		// Spieler besitzer = null;
		// ArrayList<Spieler> spielerImSpiel = this.spieler;
		// for (Spieler spieleri : spielerImSpiel) {
		// for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
		// if (bes.istVersorgungswerkBesitzrechtkarte()) {
		// if (aktuellesfeld.equals(bes.getSpielfeld())) {
		// hatBesitzer = true;
		// besitzer = spieleri;
		// }
		//
		// }
		// }
		// }
		//
		// if (hatBesitzer) {
		// // wenn der aktuelle besitzer gleich dem aktuellen spieler, dann
		// // soll nichts geschehen
		//
		// if (spieler.getID() == besitzer.getID()) {
		// System.out.println("Das " + aktuellesWerkName + " gehoert ihnen.");
		// return;
		// }
		// // ...hat einen
		// // besitzt besitzer beide versorgungswerke?
		// boolean besitztWasserWerk = false;
		// boolean besitztEleWerk = false;
		// boolean besitztBeide = false;
		// Versorgungswerk veleWerk = (Versorgungswerk) spielfeld[12];
		// Versorgungswerk vWasserWerk = (Versorgungswerk) spielfeld[28];
		// Besitzrechtkarten wasserWerk = new Besitzrechtkarten(vWasserWerk,
		// besitzer);
		// Besitzrechtkarten eleWerk = new Besitzrechtkarten(veleWerk,
		// besitzer);
		// for (Besitzrechtkarten bes : besitzer.getBesitzrechtkarten()) {
		// if (bes.equals(wasserWerk)) {
		// besitztWasserWerk = true;
		// if (bes.equals(eleWerk)) {
		// besitztEleWerk = true;
		// }
		// }
		// if (bes.equals(eleWerk)) {
		// besitztEleWerk = true;
		// if (bes.equals(wasserWerk)) {
		// besitztWasserWerk = true;
		// }
		// }
		//
		// }
		// if (besitztEleWerk & besitztWasserWerk) {
		// besitztBeide = true;
		// }
		//
		// if (besitztBeide) {
		// // ja:
		// // analog zu nein nur 10 * augensumme der würfel.
		// if (spielerGuthaben >= 10 * wurf[2]) {
		// // true:
		// // aktueller spieler kontostand - 4* augen der würfel
		// // besitzer des werkes guthaben + 4 * augen der würfel
		// spieler.setGuthaben(spielerGuthaben - (10 * wurf[2]));
		// besitzer.setGuthaben(besitzer.getGuthaben() + (10 * wurf[2]));
		// System.out.println("Das " + aktuellesWerkName + " gehoert schon
		// jemand anderes.");
		// System.out.println("Sie mussten den Betrag von " + (10 * wurf[2]) + "
		// zahlen.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		//
		// } else {
		// // false:
		// // besitzer des versorgungswerkes bekommt restliches
		// // guthaben des aktuellen spielers und der
		// // kontostand des aktuellen spielers wird auf null gesetz
		// // und aus dem spiel genommen.
		// besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
		// spieler.setGuthaben(0);
		// System.out.println("Das " + aktuellesWerkName + " gehoert schon
		// jemand anderes."); // hier
		// // eventuell
		// // anzeigen
		// // wem
		// System.out.println("Sie konnten den Betrag von " + (10 * wurf[2])
		// + " nicht Zahlen. Sie sind pleite und werden aus dem Spiel
		// entfernt.");
		// loescheSpielerAusSpiel(spieler);
		//
		// }
		// } else {
		// // nein:
		// // aktueller kontostand groesser/gleich 4 * augen der würfel
		// if (spielerGuthaben >= 4 * wurf[2]) {
		// // true:
		// // aktueller spieler kontostand - 4* augen der würfel
		// // besitzer des werkes guthaben + 4 * augen der würfel
		// spieler.setGuthaben(spielerGuthaben - (4 * wurf[2]));
		// besitzer.setGuthaben(besitzer.getGuthaben() + (4 * wurf[2]));
		// System.out.println("Das " + aktuellesWerkName + " gehoert schon
		// jemand anderes.");
		// System.out.println("Sie mussten den Betrag von " + (4 * wurf[2]) + "
		// zahlen.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		// } else {
		// // false:
		// // besitzer des versorgungswerkes bekommt restliches
		// // guthaben des aktuellen spielers und der
		// // kontostand des aktuellen spielers wird auf null gesetz
		// // und aus dem spiel genommen.
		// besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
		// spieler.setGuthaben(0);
		// System.out.println("Das " + aktuellesWerkName + " gehoert schon
		// jemand anderes."); // hier
		// // eventuell
		// // anzeigen
		// // wem
		// System.out.println("Sie konnten den Betrag von " + (4 * wurf[2])
		// + " nicht Zahlen. Sie sind pleite und werden aus dem Spiel
		// entfernt.");
		// loescheSpielerAusSpiel(spieler);
		// }
		//
		// }
		//
		// } else {
		// // ...hat keinen
		// // guthaben abfragen
		//
		// // guthaben über/gleich 150 oder unter 150 - wenn unter 150 dann
		// // user rückmeldung das er nichts kaufen kann ENDE
		// // wenn über oder 150 dann kaufen?
		// // wenn nein nicht kaufen ENDE
		// // wenn ja, dann guthaben aktueller spieler -150 und aktueller
		// // spieler bekommt besitzrechtkarte ENDE
		// if (spielerGuthaben >= 150) {
		// System.out.println("Moechten Sie das " + aktuellesWerkName + " fuer "
		// + Versorgungswerk.getKaufpreis()
		// + " kaufen?(ja/nein)");
		// if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() :
		// entscheidung()) {
		// spieler.setGuthaben(spieler.getGuthaben() - 150);
		// spieler.getBesitzrechtkarten().add(b);
		// System.out.println("Sie haben das " + aktuellesWerkName + "
		// gekauft.");
		// System.out.println("Ihr neuer Guthabenbetrag ist: " +
		// spieler.getGuthaben());
		// } else {
		// System.out.println("Sie haben das " + aktuellesWerkName + " nicht
		// gekauft.");
		// }
		// } else {
		// System.out.println("Sie koennen das " + aktuellesWerkName + " nicht
		// kaufen.");
		// }
		//
		// }

	}

	public boolean aktionVersorgungswerkEins(Spieler spieler) {
		// int wurfs = wurf[2];
		int spielerPosition = spieler.getPosition();
		double spielerGuthaben = spieler.getGuthaben();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesfeld = spielfeld[spielerPosition];

		// Versorgungswerk ermitteln
		String aktuellesWerkName = null;
		if (spielerPosition == 12) {
			aktuellesWerkName = "Elektrizitaetswerk";
		} else {
			if (spielerPosition == 28) {
				aktuellesWerkName = "Wasserwerk";
			}
		}

		// besitzer ermitteln
		boolean hatBesitzer = false;
		Spieler besitzer = null;
		ArrayList<Spieler> spielerImSpiel = this.spieler;
		for (Spieler spieleri : spielerImSpiel) {
			for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
				if (bes.istVersorgungswerkBesitzrechtkarte()) {
					if (aktuellesfeld.equals(bes.getSpielfeld())) {
						hatBesitzer = true;
						besitzer = spieleri;
					}

				}
			}
		}

		if (hatBesitzer) {
			// wenn der aktuelle besitzer gleich dem aktuellen spieler, dann
			// soll nichts geschehen

			if (spieler.getID() == besitzer.getID()) {
				System.out.println("Das " + aktuellesWerkName + " gehoert ihnen.");
				return false;
			}
			// ...hat einen
			// besitzt besitzer beide versorgungswerke?
			boolean besitztWasserWerk = false;
			boolean besitztEleWerk = false;
			boolean besitztBeide = false;
			Versorgungswerk veleWerk = (Versorgungswerk) spielfeld[12];
			Versorgungswerk vWasserWerk = (Versorgungswerk) spielfeld[28];
			Besitzrechtkarten wasserWerk = new Besitzrechtkarten(vWasserWerk, besitzer);
			Besitzrechtkarten eleWerk = new Besitzrechtkarten(veleWerk, besitzer);
			for (Besitzrechtkarten bes : besitzer.getBesitzrechtkarten()) {
				if (bes.equals(wasserWerk)) {
					besitztWasserWerk = true;
					if (bes.equals(eleWerk)) {
						besitztEleWerk = true;
					}
				}
				if (bes.equals(eleWerk)) {
					besitztEleWerk = true;
					if (bes.equals(wasserWerk)) {
						besitztWasserWerk = true;
					}
				}

			}
			if (besitztEleWerk & besitztWasserWerk) {
				besitztBeide = true;
			}

			if (besitztBeide) {
				// ja:
				// analog zu nein nur 10 * augensumme der würfel.
				if (spielerGuthaben >= 10 * wurf[2]) {
					// true:
					// aktueller spieler kontostand - 4* augen der würfel
					// besitzer des werkes guthaben + 4 * augen der würfel
					spieler.setGuthaben(spielerGuthaben - (10 * wurf[2]));
					besitzer.setGuthaben(besitzer.getGuthaben() + (10 * wurf[2]));
					System.out.println("Das " + aktuellesWerkName + " gehoert schon jemand anderes.");
					System.out.println("Sie mussten den Betrag von " + (10 * wurf[2]) + " zahlen.");
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());

				} else {
					// false:
					// besitzer des versorgungswerkes bekommt restliches
					// guthaben des aktuellen spielers und der
					// kontostand des aktuellen spielers wird auf null gesetz
					// und aus dem spiel genommen.
					besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
					spieler.setGuthaben(0);
					System.out.println("Das " + aktuellesWerkName + " gehoert schon jemand anderes."); // hier
																										// eventuell
																										// anzeigen
																										// wem
					System.out.println("Sie konnten den Betrag von " + (10 * wurf[2])
							+ " nicht Zahlen. Sie sind pleite und werden aus dem Spiel entfernt.");
					loescheSpielerAusSpiel(spieler);

				}
			} else {
				// nein:
				// aktueller kontostand groesser/gleich 4 * augen der würfel
				if (spielerGuthaben >= 4 * wurf[2]) {
					// true:
					// aktueller spieler kontostand - 4* augen der würfel
					// besitzer des werkes guthaben + 4 * augen der würfel
					spieler.setGuthaben(spielerGuthaben - (4 * wurf[2]));
					besitzer.setGuthaben(besitzer.getGuthaben() + (4 * wurf[2]));
					System.out.println("Das " + aktuellesWerkName + " gehoert schon jemand anderes.");
					System.out.println("Sie mussten den Betrag von " + (4 * wurf[2]) + " zahlen.");
					System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());
				} else {
					// false:
					// besitzer des versorgungswerkes bekommt restliches
					// guthaben des aktuellen spielers und der
					// kontostand des aktuellen spielers wird auf null gesetz
					// und aus dem spiel genommen.
					besitzer.setGuthaben(besitzer.getGuthaben() + spieler.getGuthaben());
					spieler.setGuthaben(0);
					System.out.println("Das " + aktuellesWerkName + " gehoert schon jemand anderes."); // hier
																										// eventuell
																										// anzeigen
																										// wem
					System.out.println("Sie konnten den Betrag von " + (4 * wurf[2])
							+ " nicht Zahlen. Sie sind pleite und werden aus dem Spiel entfernt.");
					loescheSpielerAusSpiel(spieler);
				}

			}

		} else {
			if (spielerGuthaben < 150) {
				System.out.println("Sie koennen das " + aktuellesWerkName + " nicht kaufen.");
			} else {
				return true; // zwei soll ausgefuert werden
			}
		}
		return false;

	}

	public void aktionVersorgungswerkZwei(Spieler spieler) {
		int spielerPosition = spieler.getPosition();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesfeld = spielfeld[spielerPosition];
		Besitzrechtkarten b = new Besitzrechtkarten(aktuellesfeld, spieler);
		// Versorgungswerk ermitteln
		String aktuellesWerkName = null;
		if (spielerPosition == 12) {
			aktuellesWerkName = "Elektrizitaetswerk";
		} else {
			if (spielerPosition == 28) {
				aktuellesWerkName = "Wasserwerk";
			}
		}
		System.out.println("Moechten Sie das " + aktuellesWerkName + " fuer " + Versorgungswerk.getKaufpreis()
				+ " kaufen?(ja/nein)");
		if (spieler.isIstKi() ? ((Ki) spieler).entscheidung() : entscheidung()) {
			spieler.setGuthaben(spieler.getGuthaben() - 150);
			spieler.getBesitzrechtkarten().add(b);
			System.out.println("Sie haben das " + aktuellesWerkName + " gekauft.");
			System.out.println("Ihr neuer Guthabenbetrag ist: " + spieler.getGuthaben());
		} else {
			System.out.println("Sie haben das " + aktuellesWerkName + "  nicht gekauft.");
		}
	}

	/**
	 * @return gibt das aktuelle Spielbrett in CSV-Notation als String zurück
	 * 
	 */
	public String ausgabeSpielBrett() {

		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		String spielBrett = "";
		for (int i = 0; i < spielfeld.length; i++) {
			if (spielfeld[i].getClass().toString().equals("class monopoly.Strasse")
					|| spielfeld[i].getClass().toString().equals("class monopoly.Bahnhof")
					|| spielfeld[i].getClass().toString().equals("class monopoly.Versorgungswerk")) {
				spielBrett += "Spielfeld " + (i + 1) + ": " + spielfeld[i] + spielerAufSpielFeldErmitteln(spielfeld[i])
						+ " " + besitzerVonSpielfeldErmitteln(spielfeld[i]) + ";" + "\n";
			} else {
				spielBrett += "Spielfeld " + (i + 1) + ": " + spielfeld[i] + " "
						+ spielerAufSpielFeldErmitteln(spielfeld[i]) + ";" + "\n";
			}

		}

		return spielBrett;
	}

	/**
	 * Gibt die moeglichkeit ein Haus auf eine gewaehlte Besitzrechtkarte zu
	 * platzieren. Wenn das Haus nicht gebaut werden kann wird ein Fehler
	 * geworfen.
	 * 
	 * @param die
	 *            besitzrechtkarte auf die das Haus gebaut werden soll Diese
	 *            Methoode ist dazu da, dass man Häuser / Hotels bauen kann.
	 * @author bisci
	 */
	public boolean bauen(Besitzrechtkarten b) {

		Strasse s = (Strasse) b.getSpielfeld();
		if (s.getHaus1() == null && s.getHaus2() == null && s.getHotel() == null) {
			if (b.getBesitzer().getGuthaben() >= Haus.getKaufpreis()) {
				b.getBesitzer().setGuthaben(b.getBesitzer().getGuthaben() - Haus.getKaufpreis());
				s.setHaus1(new Haus());
//				System.out.println("Sie haben ein Haus gebaut");
				return true;
			}
		} else if (s.getHaus1() != null && s.getHaus2() == null && s.getHotel() == null) {
			if (b.getBesitzer().getGuthaben() >= Haus.getKaufpreis()) {
				b.getBesitzer().setGuthaben(b.getBesitzer().getGuthaben() - Haus.getKaufpreis());
				s.setHaus2(new Haus());
//				System.out.println("Sie haben ein zweites Haus gebaut");
				return true;
			}
		} else if (s.getHaus1() != null && s.getHaus2() != null && s.getHotel() == null) {
			if (b.getBesitzer().getGuthaben() >= Hotel.getKaufpreis()) {
				b.getBesitzer().setGuthaben(b.getBesitzer().getGuthaben() - Hotel.getKaufpreis());
				s.setHotel(new Hotel());
				s.setHaus1(null);
				s.setHaus2(null);
//			System.out.println("Sie haben ihre Häuser durch ein Hotel ersetzt");
				return true;
			}
		} else {
//			System.out.println("Es steht schon ein Hotel drauf ;) du bist am Limit");
			return false;
		}
		return false;
	}

	@Override
	public int bauen(Strasse s) {
		for(Besitzrechtkarten bes : spielerAmZug.getBesitzrechtkarten()){
			if(bes.istStrassenBesitzrechtkarte()){
				if(bes.getSpielfeld().equals(s)){
					bauen(bes);
				}
			}	
		}
		if (s.getHaus1() == null && s.getHaus2() == null && s.getHotel() == null) {
			if (spielerAmZug.getGuthaben() >= Haus.getKaufpreis()) {
				spielerAmZug.setGuthaben(spielerAmZug.getGuthaben() - Haus.getKaufpreis());
				s.setHaus1(new Haus());
				System.out.println("Sie haben ein Haus gebaut");
				return 0;
			}
		} else if (s.getHaus1() != null && s.getHaus2() == null && s.getHotel() == null) {
			if (spielerAmZug.getGuthaben() >= Haus.getKaufpreis()) {
				spielerAmZug.setGuthaben(spielerAmZug.getGuthaben() - Haus.getKaufpreis());
				s.setHaus2(new Haus());
				System.out.println("Sie haben ein zweites Haus gebaut");
				return 1;
			}
		} else if (s.getHaus1() != null && s.getHaus2() != null && s.getHotel() == null) {
			if (spielerAmZug.getGuthaben() >= Hotel.getKaufpreis()) {
				spielerAmZug.setGuthaben(spielerAmZug.getGuthaben() - Hotel.getKaufpreis());
				s.setHotel(new Hotel());
				s.setHaus1(null);
				s.setHaus2(null);
				System.out.println("Sie haben ihre Häuser durch ein Hotel ersetzt");
				return 2;
			}
		} else {
			System.out.println("Es steht schon ein Hotel drauf ;) du bist am Limit");
		}
		return -1;
	}

	/**
	 * Hilfsmethode die den Text "Druecken Sie eine beliebige Zahl." ausgibt.
	 * Als eingabe muss eine Zahl erfolgen, dass die Methode ohne Rueckgabewert
	 * terminiert.
	 */
//	private void beliebigeZahlDruecken() {
//		Scanner scan = new Scanner(System.in);
//		int e = 0;
//		do {
//			try {
//				scan = new Scanner(System.in);
//				e = scan.nextInt();
//				return;
//			} catch (Exception ex) {
//				System.out.println("Druecken Sie eine beliebige Zahl.");
//			}
//
//		} while (true);
//	}

	/**
	 * Ermittelt den Besitzer eines Spielfeldes und gibt seine ID als String
	 * zurueck.
	 * 
	 * @param spielfeld
	 * @return
	 */
	private String besitzerVonSpielfeldErmitteln(Spielfeld spielfeld) {
		String besitzer = "";
		for (Spieler s : spieler) {
			for (Besitzrechtkarten bes : s.getBesitzrechtkarten()) {
				if (bes.getSpielfeld().getPosition() == spielfeld.getPosition()) {
					besitzer = "" + s.getID();
				}
			}
		}
		return "Besitzer dieses Feldes: [ " + besitzer + " ]";
	}

	/**
	 * Wenn der besitzer alle Besitzrechkarten einer Strasse hat darf er auf
	 * einer dieser Karten ein Haus oder wenn er 2 Häuser hat ein Hotel kaufen.
	 * In der Methode wird geprüft ob er alle Bes.-Karten besitzt und genügend
	 * Geld für 1 Haus, 2Häuser oder auch ein Hotel hat je nachdem was zutrifft
	 * wird er gefragt was davon er bauen will.
	 * 
	 * @param der
	 *            Besitzer der Besitzrechkarten
	 */
	public boolean darfbauen(Spieler besitzer, Strasse s) {
		int anzahlstrassen = 0;
		if (s.getStrassenID() == StrassenID.dunkellila || s.getStrassenID() == StrassenID.blau) {
			anzahlstrassen += 1;
		}
		for (Spielfeld e : spielbrett.getSpielfeld()) {
			if (e instanceof Strasse) {
				if (((Strasse) e).getStrassenID() == s.getStrassenID()) {
					for (Besitzrechtkarten b : besitzer.getBesitzrechtkarten()) {
						if (b.getSpielfeld().getPosition() == e.getPosition()) {
							anzahlstrassen += 1;
						}
					}
				}
			}
		}
		if (anzahlstrassen == 3) {
			return true;
			// for (Besitzrechtkarten b : besitzer.getBesitzrechtkarten()) {
			// if (b.getSpielfeld().getPosition() == s.getPosition()) {
			// return true;
			// }
			// }
		}
		return false;
	}

	@Override
	public boolean darfbauen(Strasse s) {
		int anzahlstrassen = 0;
		if (s.getStrassenID() == StrassenID.dunkellila || s.getStrassenID() == StrassenID.blau) {
			anzahlstrassen += 1;
		}
		for (Spielfeld e : spielbrett.getSpielfeld()) {
			if (e instanceof Strasse) {
				if (((Strasse) e).getStrassenID() == s.getStrassenID()) {
					for (Besitzrechtkarten b : spielerAmZug.getBesitzrechtkarten()) {
						if (b.getSpielfeld().getPosition() == e.getPosition()) {
							anzahlstrassen += 1;
						}
					}
				}
			}
		}
		if (anzahlstrassen == 3) {
			return true;
			// for (Besitzrechtkarten b : besitzer.getBesitzrechtkarten()) {
			// if (b.getSpielfeld().getPosition() == s.getPosition()) {
			// return true;
			// }
			// }
		}
		return false;
	}

	/**
	 * Ein Dialog der auf der Konsole fraegt ob man ein Spiel speichern moechte
	 * oder nicht.
	 */
	private void dialogSpielSpeichern() {
		System.out.println("Wollen Sie das Spiel speichern? ja/nein");
		if (entscheidung()) {
			System.out.print("Speichere Spiel");
			gibPunkteAus();
//			speichern();
			warten();
			System.out.println(" Spiel Gespeichert.");
			return;
		} else {
			System.out.println("Das Spiel wird nicht gespeichert.");
			return;
		}
	}

	/**
	 * Die Eingabe fuer DIe Spieler.
	 */
	@Override
	public String eingabe(Boolean b) {
		if (b) {
			setEingabe("ja");
		} else {
			setEingabe("nein");
		}
		return eingabe;
	}

	/**
	 * Entscheidung fuer einen Menschlichen Spieler. Man kann nach ja oder nein
	 * entscheiden.
	 */
	public boolean entscheidung() {
		// Scanner scan = null;
		// String eingabe = null;
		try {
			// scan = new Scanner(System.in);
			// eingabe = scan.nextLine();
			if (!(eingabe.equals("ja") || eingabe.equals("nein"))) {
				System.out.println("Geben Sie \"ja\" oder \"nein\" ein.");
			} else {
				if (eingabe.equals("ja")) {
					return true;
				} else {
					if (eingabe.equals("nein")) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Geben Sie \"ja\" oder \"nein\" ein.");

		}
		return false;
	}

	// @Override Mehtoden aus IBediener

	/**
	 * Entscheidung fuer eine Ki ( wird nichtmehr verwendet)
	 */
	public boolean entscheidungKi(Spieler s) {
		Ki k = (Ki) s;
		if (s.getPosition() == 12 || s.getPosition() == 28) {
			k.entscheidungVersorgungswerk();
		}
		return false;
	}

	/**
	 * Wenn die methode aufgerufen wird zieht der aktuelle spieler eine
	 * Ereigniskarte. Die aktion wird direkt ausgefuehrt.
	 */
//	public void ereigniskarteZiehen() {
//		
//
//	}

	/**
	 * Hilfsmethode fuer initSpieler. Hier werden die Farben fuer die Spieler
	 * initialisiert.
	 * 
	 * @param a
	 * @return
	 */
	private boolean farbeWaehlen(int a) {
		int farbe = 0;
		do {
			try {
				scan = new Scanner(System.in);
				System.out.println("Bitte Farbe für Spieler " + a + " waehlen:");
				for (int farb : farben) {
					if (farb != 0) {
						System.out.println(farb + " - " + FarbEnum.getFarbe(farb));
					}
				}
				farbe = scan.nextInt();
			} catch (Exception e) {
				return false;
			}
		} while (farbe < 1 || farbe > 5);

		for (int i = 0; i < farben.length; i++) {
			if (farbe == farben[i]) {
				// spieler.add(new Spieler(a, FarbEnum.getFarbe(farbe)));
				farben[i] = 0;
				return true;
			}

		}
		return false;
	}

	/**
	 * Hilfsmethode fuer initSpieler. Hier werden die Farben fuer die Kis
	 * initialisiert.
	 * 
	 * @param a
	 * @return
	 */
	private boolean farbeWaehlenKI(int a) {
		int farbe = 0;
		do {
			try {
				scan = new Scanner(System.in);
				System.out.println("Bitte Farbe für Spieler " + a + " waehlen:");
				for (int farb : farben) {
					if (farb != 0) {
						System.out.println(farb + " - " + FarbEnum.getFarbe(farb));
					}
				}
				farbe = scan.nextInt();
			} catch (Exception e) {
				return false;
			}
		} while (farbe < 1 || farbe > 5);

		for (int i = 0; i < farben.length; i++) {
			if (farbe == farben[i]) {
				// spieler.add(new KiEasy(a, FarbEnum.getFarbe(farbe), this));
				farben[i] = 0;
				return true;
			}

		}
		return false;
	}

	@Override
	public boolean feldHatBesitzer() {
		if (!(istBesitzbaresFeld(spielbrett.getSpielfeld()[spielerAmZug.getPosition()]))) {
			return false;
		} else {
			Spielfeld f = spielbrett.getSpielfeld()[spielerAmZug.getPosition()];

			ArrayList<Spieler> spielerImSpiel = this.spieler;
			for (Spieler spieleri : spielerImSpiel) {
				for (Besitzrechtkarten bes : spieleri.getBesitzrechtkarten()) {
					if (f.equals(bes.getSpielfeld())) {
						return true;
					}

				}
			}

			// Spielfeld f =
			// spielbrett.getSpielfeld()[spielerAmZug.getPosition()];
			// for(Spieler spi: spieler){
			// for(Besitzrechtkarten bes : spi.getBesitzrechtkarten()){
			// if(bes.getSpielfeld().equals(f)){
			// return true;
		}

		return false;
	}

	/**
	 * Inkrementiert die gesamtZuege
	 */
	private void gesamtZuegeInc() {
		gesamtZuege++;
	}

	public int getaktuelleRunde() {
		return aktuelleRunde;
	}

	/**
	 * Getter fuer die aktuelle Runde
	 * 
	 * @return
	 */
	@Override
	public int getAktuelleRunde() {
		return aktuelleRunde;
	}

	// HILFSMETHODEN AUSGABE SPIELBRETT ENDE

	@Override
	public int getAnzahlSpielerAufFeld(int index) {
		int anzahl = 0;
		for (Spieler a : spieler) {
			if (a.getID() == 1) {
				if (a.getPosition() == index) {
					anzahl += 10000;
				}
			}
			if (a.getID() == 2) {
				if (a.getPosition() == index) {
					anzahl += 1000;
				}
			}
			if (a.getID() == 3) {
				if (a.getPosition() == index) {
					anzahl += 100;
				}
			}
			if (a.getID() == 4) {
				if (a.getPosition() == index) {
					anzahl += 10;
				}
			}
			if (a.getID() == 5) {
				if (a.getPosition() == index) {
					anzahl += 1;
				}
			}
		}
		return anzahl;
	}

	/**
	 * Getter fuer die Eingabe.
	 * 
	 * @return
	 */
	public String getEingabe() {
		return eingabe;
	}

	/**
	 * Getter fuer die Ereigniskarte
	 * 
	 * @return die Ereigniskarte
	 */
	@Override
	public Ereigniskarte getEreigniskarte() {
		return ereigniskarte;
	}

	/**
	 * Getter für den Ereigniskartenmanager
	 * 
	 * @return gibt den Ereigniskartenmanager zurück
	 */
	public Ereigniskartenmanager getEreigniskartenmanager() {
		return ereigniskartenmanager;
	}

	/**
	 * Getter fuer das Farbenarray(integer)
	 * 
	 * @return das farbarray
	 */
	public int[] getFarben() {
		return farben;
	}

	/**
	 * Gibt die maximale Rundenanzahl zurück
	 * 
	 * @return gibt die maximale Rundenzahl zurück
	 */
	public long getMaximaleRundenAnzahl() {
		return maximaleRundenAnzahl;
	}

	/**
	 * Getter fuer den Scanner
	 * 
	 * @return
	 */
	public Scanner getScan() {
		return scan;
	}

	/**
	 * Getter für das Spielbrett
	 * 
	 * @return gibt das Spielbrett zurück
	 */
	@Override
	public Spielbrett getSpielbrett() {
		return spielbrett;
	}

	/**
	 * Getter für die Arraylist aus Spielern
	 * 
	 * @return gibt eine Arraylist der Spieler zurück
	 */
	@Override
	public ArrayList<Spieler> getSpieler() {
		return spieler;
	}

	/**
	 * Getter für den aktuellen Spieler
	 * 
	 * @return gibt den momentanen Spieler zurück
	 */
	@Override
	public Spieler getSpielerAmZug() {
		return spielerAmZug;
	}

	/**
	 * Getter fuer den aktuellen Spieler am Zug.
	 * 
	 * @return
	 */
	public int getSpielerAmZugAktuell() {
		return spielerAmZugAktuell;
	}

	/**
	 * Getter fuer die Spieleranzahl
	 * 
	 * @return die spieleranzahl
	 */
	public int getSpieleranzahl() {
		return spieleranzahl;
	}

	// @Overrride Methoden von IDatenzugriff

	/**
	 * Getter duer das StartGuthaben
	 * 
	 * @return
	 */
	public int getStartGuthaben() {
		return startGuthaben;
	}

	/**
	 * Getter für den ersten Würfel
	 * 
	 * @return gibt den ersten Würfel zurück
	 */
	public Wuerfel getWuerfel1() {
		return wuerfel1;
	}

	// Hilfsmethoden fuer das Speichern und laden.

	/**
	 * Getter für den zweiten Würfel
	 * 
	 * @return gibt den zweiten Würfel zurück
	 */

	public Wuerfel getWuerfel2() {
		return wuerfel2;
	}

	/**
	 * Getter fuer den wurf.
	 * 
	 * @return das Wurfarray
	 */
	@Override
	public int[] getWurf() {
		return wurf;
	}

	/**
	 * Ermittelt den Gewinner des Spiels.
	 */
	private void gewinnerErmitteln() {
		System.out.print("Gewinner wird ermittelt. Einen Moment bitte");
		gibPunkteAus();
		gewinnerErmittelnRegulaer();
	}

	// Methoden die unmittelbar mit dem Spielfluss zu tun haben

	/**
	 * Diese Methode prüft ob einer der Spieler laut den Vorgaben die
	 * Siegbedinungen erfüllt hat. Desweiteren gibt sie den Platz aller Spieler
	 * aus
	 * 
	 */
	private void gewinnerErmittelnRegulaer() {
		ArrayList<Double> guthabenDerSpieler = new ArrayList<Double>();
		HashSet<Double> hs = new HashSet<Double>();
		for (Spieler spieler : spieler) {
			guthabenDerSpieler.add(vermoegenBerechnen(spieler));
		}
		hs.addAll(guthabenDerSpieler);
		guthabenDerSpieler.clear();
		guthabenDerSpieler.addAll(hs);
		Collections.sort(guthabenDerSpieler);
		Collections.reverse(guthabenDerSpieler);
		int platz = 1;
		System.out.println("\nSiegertreppchen:");
		for (Iterator<Double> guthaben = guthabenDerSpieler.iterator(); guthaben.hasNext();) {
			double g = guthaben.next();
			for (Iterator<Spieler> spieler = this.spieler.iterator(); spieler.hasNext();) {
				Spieler s = spieler.next();
				if (vermoegenBerechnen(s) == g) {
					System.out.println("Platz " + platz + ": " + "Spieler " + s.getID() +": "+s.getName()+ ", [Vermoegen: "
							+ vermoegenBerechnen(s) + "| Guthaben: " + s.getGuthaben() + "]");
					platz++;
				}
			}
		}
	}

	/**
	 * Gibt drei Punkte innerhalb dreier Sekunden aus. Jede Sekunde wird ein
	 * Punkt auf der Konsole ausgegeben.
	 */
	private void gibPunkteAus() {
		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			warten();
		}
	}

	/**
	 * gibt die infos über den aktuellen spieler aus
	 * 
	 * @return gibt die Informationen Geld, Farbe, Besitzrechtkarten
	 */
	public FarbEnum infoaktSpieler() {
	
		return null;
	}

	/**
	 * Legt die Spieleranzahl fest (2-5)spieler und initialisert diese mit ihren
	 * Farben, die sie im Dialog waehlen koennen. Außerdem werden auch Kis
	 * initialisert, sollten welche am Spiel teilnehmen.
	 * 
	 * @param spiel
	 *            Bekommt das momentan laufende Spiel übergeben um damit die
	 *            Spieleranzahl festzulegen
	 * @return int spieleranzahl gibt die anzahl der Spieler des momentan
	 *         laufenden Spiels zurück
	 */
	private int initSpieler(Spiel spiel) {
		// LRL
		int kiAnzahl = -1;
		// LRL
		boolean erstellt = false;
		do {
			try {
				scan = new Scanner(System.in);
				System.out.println("Bitte Anzahl von Spielern eingeben (2-5)");
				spieleranzahl = scan.nextInt();

				// LRL
				do {
					try {
						scan = new Scanner(System.in);
						System.out
								.println("wieviele KI's sollen an dem Spiel beteiligt sein?(0-" + spieleranzahl + ")");
						kiAnzahl = scan.nextInt();
					} catch (Exception e) {
						kiAnzahl = -1;
					}
				} while (kiAnzahl < 0 || kiAnzahl > spieleranzahl);
				// LRL

			} catch (Exception e) {
				spieleranzahl = 0;
			}
		} while (spieleranzahl < 2 || spieleranzahl > 5);

		for (int i = 1; i <= (spieleranzahl - kiAnzahl); i++) {
			do {
				erstellt = farbeWaehlen(i);
			} while (erstellt == false);
			erstellt = false;
		}

		if (kiAnzahl != 0) {
			System.out.println("Waehlen fuer KI");
			for (int i = ((spieleranzahl - kiAnzahl) + 1); i <= spieleranzahl; i++) {
				do {
					erstellt = farbeWaehlenKI(i);
				} while (erstellt == false);
				erstellt = false;
			}

		}

		// for (int i = 1; i <= spieleranzahl; i++) {
		// do {
		// erstellt = farbeWaehlen(i);
		// } while (erstellt == false);
		// erstellt = false;
		// }

		return spieleranzahl;
	}

	public void insGefaengnisZiehen() {

	}

	/**
	 * ermittelt wieviele spieler noch im spiel sind, sollte nur noch ein
	 * spieler im spiel sein. hat dieser gewonnen und das spiel endet.
	 */
	@Override
	public boolean irregulaeresEnde() {
		if (this.spieler.size() == 1) {
			System.out.println("Spieler " + this.spieler.get(0).getID()
					+ " ist als letzer im Spiel. Alle anderen sind ausgeschieden. Er hat gewonnen.");
			logProbabilityOfFields();
			System.out.println((spielInformationen()));
			// speicherSpielInformationen(spielInformationen());
			// System.exit(0);
			return true;
		}
		return false;
	}

	public boolean istBesitzbaresFeld(Spielfeld f) {
		if (f.getClass().equals(Strasse.class) || f.getClass().equals(Bahnhof.class)
				|| f.getClass().equals(Versorgungswerk.class)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean istBesitzbaresFeldAktuell() {
		if (spielbrett.getSpielfeld()[spielerAmZug.getPosition()].getClass().equals(Strasse.class)
				|| spielbrett.getSpielfeld()[spielerAmZug.getPosition()].getClass().equals(Bahnhof.class)
				|| spielbrett.getSpielfeld()[spielerAmZug.getPosition()].getClass().equals(Versorgungswerk.class)) {
			return true;
		}
		return false;
	}

	public boolean istBesitzrechtkarteVonSpieler(Besitzrechtkarten bes, Spieler sp) {
		for (Besitzrechtkarten b : sp.getBesitzrechtkarten()) {
			if (b.equals(bes)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean istBesitzrechtkarteVonSpieler(Spielfeld fd) {
		for (Besitzrechtkarten b : spielerAmZug.getBesitzrechtkarten()) {
			if (b.getSpielfeld().getPosition() == fd.getPosition()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean kaufGrundStueckEins() { // true wenn zwei ausgefuert werden
											// soll
		if (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().equals(Strasse.class)) {
			return aktionStrasseEins(spielerAmZug);
		} else {
			if (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().equals(Bahnhof.class)) {
				return aktionBahnhofEins(spielerAmZug);
			} else {
				if (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().equals(Versorgungswerk.class)) {
					return aktionVersorgungswerkEins(spielerAmZug);
				} else {
					System.out.println("Kein Kaufbares Feld.");
				}
			}

		}
		return false;

	}

	@Override
	public void kaufGrundStueckZwei() {
		if (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().equals(Strasse.class)) {
			aktionStrassesZwei(spielerAmZug);
		} else {
			if (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().equals(Bahnhof.class)) {
				aktionBahnhofZwei(spielerAmZug);
			} else {
				if (spielbrett.geteinSpielfeld(spielerAmZug.getPosition()).getClass().equals(Versorgungswerk.class)) {
					aktionVersorgungswerkZwei(spielerAmZug);
				} else {
					System.out.println("Kein Kaufbares Feld.");
				}
			}

		}
	}

	@Override
	public void kontrolleAbgeben() {
//		if (spielerAmZug.isIstKi()) {
//			((Ki) spielerAmZug).kontrolleAbgebenKI();
//		}
		if (spieler.size() - 1 == spielerAmZugAktuell) {
			runden();
		} else {
			spielerAmZugAktuell++;
			spielerAmZug = spieler.get(spielerAmZugAktuell);
			System.out.println("Der naechste ist am Zug.");
		}

	}

	public boolean kontrolleAbgebenKI() {
		return false;
	}

	// Was kann der Spieler am Ende eines Zuges noch machen nachdem er gezogen
	// ist?
	// Bin ein bisschen muede deshalb grundsaetzliche Ueberlegung...
	// verkauf von besitzrechtkarten
	// ein Haus bauen
	/**
	 * Diese Methode Regelt das Ende eines Zuges wobei sie ihm die Möglichkeit
	 * gibt noch Haeuser zu Bauen und Besitzrechtkarten zu verkaufen Gibt am
	 * Ende des Zuges des aktuellen Spielers die kontrolle an den naechesten
	 * Spieler in der reihe weiter.
	 * 
	 * @param der
	 *            Aktuelle Spieler der die Kontrolle abgeben muss
	 */
	// @Override
	public void kontrolleAbgebenSpiel(Spieler aktuellerSpieler) {

		if (aktuellerSpieler.isIstKi()) {
			((Ki) aktuellerSpieler).kontrolleAbgebenKI();
			return;
		}

		boolean richtigeEingabe = true;
		Spieler aktSpieler = aktuellerSpieler;
		// double spielerguthaben = aktuellerSpieler.getGuthaben();
		// Spielbrett s = spielbrett;

		while (richtigeEingabe) {
			try {
				System.out.println("Wollen sie ihren Zug beenden?(ja/nein)");

				if ((aktSpieler.isIstKi() ? ((Ki) aktSpieler).entscheidung() : entscheidung())) {

					return;

				} else {
					System.out.println(
							"Was wollen sie noch machen?  Sie können noch ein Haus bauen geben sie dafür 'ja' ein oder sie können noch eine ihrer besitzrechtkarten verkaufen geben sie dafür 'nein' ein");
					boolean strassenvorhanden = false;
					for (Besitzrechtkarten b : aktuellerSpieler.getBesitzrechtkarten()) {
						if (b.getSpielfeld() instanceof Strasse) {
							if (darfbauen(aktuellerSpieler, (Strasse) b.getSpielfeld())) {
								strassenvorhanden = true;
							}
						}
					}

					if ((aktSpieler.isIstKi() ? ((Ki) aktSpieler).entscheidung() : entscheidung())) {
						if (aktuellerSpieler.getBesitzrechtkarten().isEmpty()) {
							System.out.println(
									"Sie haben keine Karten auf denen sie ein Haus bauen könnten das tut uns leid");
							return;
						}
						if (!strassenvorhanden) {
							System.out.println(
									"Sie haben entweder keine Strasse oder nicht alle nötigen Strassen um zu bauen...");
							return;
						}

						System.out.println(
								"Auf welche ihrer Besitzrechkarten möchten sie ein Haus oder Hotel bauen? Geben sie die Nummer für die passende Besitzrechkarte ein");
						int i = 0;
						for (Besitzrechtkarten b : aktuellerSpieler.getBesitzrechtkarten()) {
							if (b.getSpielfeld() instanceof Strasse) {
								System.out.println(i + ":");
								System.out.println(aktuellerSpieler.getBesitzrechtkarten().get(i));
								i++;
							}

						}

						int scan1 = scan.nextInt();

						Besitzrechtkarten b = aktuellerSpieler.getBesitzrechtkarten().get(scan1);

						if (b.getSpielfeld().getClass().equals(Strasse.class)) {
							if (darfbauen(aktuellerSpieler, (Strasse) b.getSpielfeld())) {
								bauen(b);
							} else {
								System.out.println("Sie haben nicht alle Straßen um ein Haus zu bauen!");
							}

							richtigeEingabe = false;
						} else {
							System.out
									.println("Sie haben keine Besitzrechtkarten auf denen sie ein Haus bauen könnten");
							richtigeEingabe = false;
						}
					} else {
						if (aktuellerSpieler.getBesitzrechtkarten().isEmpty()) {
							System.out.println("Sie haben leider leider keine Karten die sie verkaufen könnten");
							return;
						}

						verkaufen(aktSpieler);
						richtigeEingabe = false;
						return;
					}
				}

			} catch (Exception e) {
				System.out.println("Das ist keine passende Karte");
				e.printStackTrace();

			}
		}
	}

	/**
	 * Diese Methode De-Serialisiert ein ins Verzeichniss geschriebenes
	 * Spiel-Objekt und ermoeglticht den Wiedereintritt in das Spiel.
	 * 
	 * @param spiel
	 *            das geladen werden soll.
	 */
	@Override
	public Spiel laden(String pfad) {
		ObjectInputStream o = null;
		Spiel s = null;
		try {
			o = new ObjectInputStream(new FileInputStream(pfad));
			s = (Spiel) o.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Konnte die Datei nicht finden.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fehler bei der Ein-/Ausgabe.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler des classpaths.");
			e.printStackTrace();
		} finally {
		}
		// spielNachWiedereintritt(s);
		spielNachWiedereintrittGUI(s);
		return s;
	}

	@Override
	public boolean letzteRundeErreicht() {
		if (maximaleRundenAnzahl == aktuelleRunde) {
			gewinnerErmittelnRegulaer();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Diese Methode laedt aus der spielCounter.log datei die aktuelle Spiele
	 * Anzahl
	 * 
	 * @return
	 */
	private boolean loadSpielerLogCount() {

		scan = new Scanner(new InputStreamReader(ResourceLoader.load("spielCounter.log")));
		spieleInfosLogCount = scan.nextInt();
		return true;

	}

	/**
	 * loescht einen Spieler aus dem Spiel bzw der SpielerArrayListe.
	 * Zusaetzlich wird die Spieleranzahl dekrementiert.
	 * 
	 * @param spieler
	 */
	public void loescheSpielerAusSpiel(Spieler spieler) {
//		spieleranzahl--;
		geheSpielFeldDurchUndLoescheHotels(spieler);
		this.spieler.remove(spieler);
	}
	
	public void geheSpielFeldDurchUndLoescheHotels(Spieler spieler){
		for(Spielfeld sf : spielbrett.getSpielfeld()){
			Besitzrechtkarten b = new Besitzrechtkarten(sf, spieler);
			macheHotelsUndHaueserPlatt(spieler, b);
		}
			
	}

	/**
	 * Eine Methode die die Abolsuten Zahlen berechnet und in einem Array
	 * speichert wie oft die Spieler im gesamten Spiel auf die einzelnen Felder
	 * gekommen sind.
	 * 
	 * @param position
	 * @return
	 */
	private int[] logCountOfFields(int position) {
		for (int i = 0; i < spielbrett.getSpielfeld().length; i++) {
			if (position == i) {
				logArr[i]++;
			}
		}
		return logArr;
	}

	/**
	 * Diese Methode berechnet die Prozentwerte aus den absoluten Zahlen wie oft
	 * die Spieler auf die einzelnen Spiel-Felder gekommen sind.
	 * 
	 * @return
	 */
	private double[] logProbabilityOfFields() {
		for (int i = 0; i < spielbrett.getSpielfeld().length; i++) {
			probArr[i] = (((double) logArr[i]) / ((double) gesamtZuege)) * 100;
		}
		return probArr;
	}

	private boolean macheHotelsUndHaueserPlatt(Spieler sp, Besitzrechtkarten be) {
		boolean hatEtwasPlattGemacht = false;
		for (Besitzrechtkarten bes : sp.getBesitzrechtkarten()) {
			if (be.getSpielfeld().getClass().equals(Strasse.class)
					&& bes.getSpielfeld().getClass().equals(Strasse.class)) {
				if (((Strasse) be.getSpielfeld()).getStrassenID()
						.equals(((Strasse) bes.getSpielfeld()).getStrassenID())) {
					Strasse str = (Strasse) spielbrett.getSpielfeld()[bes.getSpielfeld().getPosition()];
					Strasse strBes = (Strasse) bes.getSpielfeld();
					
					if (str.getHotel() != null) {
						sp.setGuthaben(sp.getGuthaben() + 450);
						strBes.setHotel(null);
						str.setHotel(null);
						hatEtwasPlattGemacht = true;
						continue;
					}
					if (str.getHaus2() != null) {
						sp.setGuthaben(sp.getGuthaben() + 200);
						strBes.setHaus2(null);
						strBes.setHaus2(null);
						str.setHaus2(null);
						str.setHaus2(null);
						hatEtwasPlattGemacht = true;
						continue;
					}
					if (str.getHaus1() != null) {
						sp.setGuthaben(sp.getGuthaben() + 100);
						strBes.setHaus1(null);
						str.setHaus1(null);
						hatEtwasPlattGemacht = true;
						continue;
					}
				}
			}
		}
		return hatEtwasPlattGemacht;
	}
	
//	public boolean macheHotelsUndHaueserPlatt(Spieler sp, Besitzrechtkarten be) {
//		boolean hatEtwasPlattGemacht = false;
//		for (Besitzrechtkarten bes : sp.getBesitzrechtkarten()) {
//			if (be.getSpielfeld().getClass().equals(Strasse.class)
//					&& bes.getSpielfeld().getClass().equals(Strasse.class)) {
//				if (((Strasse) be.getSpielfeld()).getStrassenID()
//						.equals(((Strasse) bes.getSpielfeld()).getStrassenID())) {
//					Strasse str = (Strasse) spielbrett.getSpielfeld()[bes.getSpielfeld().getPosition()];
//					Strasse strBes = (Strasse) bes.getSpielfeld();
//					
//					if (str.getHotel() != null) {
//						sp.setGuthaben(sp.getGuthaben() + 450);
//						str.setHotel(null);
//						hatEtwasPlattGemacht = true;
//						continue;
//					}
//					if (str.getHaus2() != null) {
//						sp.setGuthaben(sp.getGuthaben() + 200);
//						str.setHaus2(null);
//						str.setHaus2(null);
//						hatEtwasPlattGemacht = true;
//						continue;
//					}
//					if (str.getHaus1() != null) {
//						sp.setGuthaben(sp.getGuthaben() + 100);
//						str.setHaus1(null);
//						hatEtwasPlattGemacht = true;
//						continue;
//					}
//				}
//			}
//		}
//		return hatEtwasPlattGemacht;
//	}

	/**
	 * Eine Methode die true zurueck gibt, wenn nur Kis am Spiel beteiligt sind.
	 * 
	 * @return
	 */
	public boolean nurKis() {
		int ciCount = 0;
		for (Spieler s : spieler) {
			if (s.isIstKi()) {
				ciCount++;
			}
		}
		if (ciCount == spieleranzahl) {
			return true;
		}
		return false;
	}

	private void runden() {
		if (!letzteRundeErreicht()) {
			spielerAmZug = spieler.get(0);
			setSpielerAmZugAktuell(0);

			// rundenAnzahl der nach jedem zug mit maximaleRundenAnzahl
			// abgleicht.
			// for (;aktuelleRunde <= maximaleRundenAnzahl; aktuelleRunde++) {
			aktuelleRunde++;
			System.out.println("\nRunde " + aktuelleRunde + " von " + maximaleRundenAnzahl + " beginnt.");
			

//			if(spielerAmZug.isIstKi()){
//				zug();
//				
//				
//				
//			}
			
			
			
			
			
			irregulaeresEnde();
			
			
			
			
			

			// // schleife die alle spieler einen spielzug ausfueren laesst.
			// for (int i = 0; i < spieleranzahl; i++) {
			// int spieleranzahl1 = spieleranzahl;
			// spielerAmZugAktuell = i;
			// warten();
			// zug(spieler.get(spielerAmZugAktuell));
			// warten();
			// if (spieleranzahl1 == spieleranzahl) {
			// kontrolleAbgeben(spieler.get(spielerAmZugAktuell));
			// }
			// }
			// warten();
			// nach jedem zug das spielfeld ausgeben und die spieler mit
			// aktuellem guthaben position farbe etc, besitzrechtkarten usw...
			// System.out.println("Dies war Runde: " + aktuelleRunde + " von " +
			// maximaleRundenAnzahl);
			// System.out.println("Aktuelles Spielfeld:");
			// System.out.println(ausgabeSpielBrett());
			//
			// if (!nurKis()) {
			// dialogSpielSpeichern();
			// spielBeenden();
			// warten();
			// System.out.println("Druecken Sie eine beliebige Zahl um die
			// naechste Runde zu starten.");
			// beliebigeZahlDruecken();
			// }

			// informationen über spieler ausgeben.//
			// spielerInfos();

			// }
		}
	}

	/**
	 * In dieser Methode wird alles ausgefuehrt, was in einer einzelnen Runde im
	 * Spiel passieren kann.
	 */
	@SuppressWarnings("unused")
	private void runden2() {
		// rundenAnzahl der nach jedem zug mit maximaleRundenAnzahl abgleicht.
		for (; aktuelleRunde <= maximaleRundenAnzahl; aktuelleRunde++) {
			System.out.println("\nRunde " + aktuelleRunde + " von " + maximaleRundenAnzahl + " beginnt.");
			irregulaeresEnde();
			// schleife die alle spieler einen spielzug ausfueren laesst.
			for (int i = 0; i < spieleranzahl; i++) {
				int spieleranzahl1 = spieleranzahl;
				spielerAmZugAktuell = i;
				warten();
				zug();
				warten();
				if (spieleranzahl1 == spieleranzahl) {
					// kontrolleAbgeben(spieler.get(spielerAmZugAktuell));
				}
			}
			warten();
			// nach jedem zug das spielfeld ausgeben und die spieler mit
			// aktuellem guthaben position farbe etc, besitzrechtkarten usw...
			System.out.println("Dies war Runde: " + aktuelleRunde + " von " + maximaleRundenAnzahl);
			System.out.println("Aktuelles Spielfeld:");
			System.out.println(ausgabeSpielBrett());

			if (!nurKis()) {
				dialogSpielSpeichern();
				spielBeenden();
				warten();
				System.out.println("Druecken Sie eine beliebige Zahl um die naechste Runde zu starten.");
//				beliebigeZahlDruecken();
			}

			// informationen über spieler ausgeben.//
			// spielerInfos();

		}
	}

	/**
	 * Legt die maximale Rundenanzahl fest welche sich von 0 bis zu dem
	 * maximalen Long wert orientiert.
	 * 
	 * @return gibt die festgelegte maximale Rundenanzahl zurück
	 */
	@SuppressWarnings("resource")
	private long rundenAnzahlFestlegen() {
		// Long.MAX_VALUE = 9223372036854775807;
		// Integer.MAX_VALUE = 2147483647;
		Scanner scan = new Scanner(System.in);
		// long rundenAnzahl = 0;
		boolean richtigeEingabe = false;
		System.out.println("Spieler eins waehlt die maximale Rundenanzahl.(1 bis 9223372036854775807)");
		while (!richtigeEingabe) {
			try {
				scan = new Scanner(System.in);
				maximaleRundenAnzahl = scan.nextLong();
				if (maximaleRundenAnzahl < 1 || maximaleRundenAnzahl > Long.MAX_VALUE) {
					System.out.println("Geben Sie eine Zahl zwischen 1 und 9223372036854775807 ein.");
				} else {
					richtigeEingabe = true;
				}
			} catch (Exception e) {
				System.out.println("Geben Sie eine Zahl zwischen 1 und 9223372036854775807 ein!");
			}
		}
		// scan.close();
		setMaximaleRundenAnzahl(maximaleRundenAnzahl);
		return maximaleRundenAnzahl;
	}

	/**
	 * Setter fuer die aktuelle Runde.
	 * 
	 * @param aktuelleRunde
	 */
	public void setAktuelleRunde(int aktuelleRunde) {
		this.aktuelleRunde = aktuelleRunde;
	}

	/**
	 * Setter fuer die Eingabe.
	 * 
	 * @param eingabe
	 */
	public void setEingabe(String eingabe) {
		this.eingabe = eingabe;
	}

	/**
	 * Setter fuer die Ereigniskarte
	 * 
	 * @param ereigniskarte
	 */
	public void setEreigniskarte(Ereigniskarte ereigniskarte) {
		this.ereigniskarte = ereigniskarte;
	}

	/**
	 * setter für den Ereigniskartenmanager
	 * 
	 * @param der
	 *            Ereigniskartenmanager der gesetzt werden soll
	 */
	public void setEreigniskartenmanager(Ereigniskartenmanager ereigniskartenmanager) {
		this.ereigniskartenmanager = ereigniskartenmanager;
	}

	/**
	 * Setter fuer das Farbenarray(integer)
	 * 
	 * @param farben
	 */
	public void setFarben(int[] farben) {
		this.farben = farben;
	}

	/**
	 * setzt die maximale Rundenanzahl fest
	 * 
	 * @param maximaleRundenAnzahl
	 *            die festgesetzte maximale Rundenzahl
	 */
	public void setMaximaleRundenAnzahl(long maximaleRundenAnzahl) {
		this.maximaleRundenAnzahl = maximaleRundenAnzahl;
	}

	/**
	 * Setter fuer den Scanner
	 * 
	 * @param scan
	 */
	public void setScan(Scanner scan) {
		this.scan = scan;
	}

	/**
	 * setter für das Spielbrett
	 * 
	 * @param das
	 *            zu setzende Spielbrett
	 */
	public void setSpielbrett(Spielbrett spielbrett) {
		this.spielbrett = spielbrett;
	}

	/**
	 * setter für die Anzahl der Spieler die am Spiel teilnehmen
	 * 
	 * @param die
	 *            Arraylist in der die Spieler stehen
	 */
	public void setSpieler(ArrayList<Spieler> spieler) {
		this.spieler = spieler;
	}

	/**
	 * setter für den momentan aktiven Spieler
	 * 
	 * @param der
	 *            momentan aktive Spieler
	 */
	public void setSpielerAmZug(Spieler spielerAmZug) {
		this.spielerAmZug = spielerAmZug;
	}

	/**
	 * Setter fuer den akutellen Spieler am Zug.
	 * 
	 * @param spielerAmZugAktuell
	 */
	public void setSpielerAmZugAktuell(int spielerAmZugAktuell) {
		this.spielerAmZugAktuell = spielerAmZugAktuell;
	}

	/**
	 * Setter fuer die Spieleranzahl
	 * 
	 * @param spieleranzahl
	 */
	public void setSpieleranzahl(int spieleranzahl) {
		this.spieleranzahl = spieleranzahl;
	}

	/**
	 * Setter fuer das Startguthaben
	 * 
	 * @param startGuthaben
	 */
	public void setStartGuthaben(int startGuthaben) {
		this.startGuthaben = startGuthaben;
	}

	/**
	 * setter für den 1ten Würfel
	 * 
	 * @param der
	 *            erste übergebene Würfel
	 */
	public void setWuerfel1(Wuerfel wuerfel1) {
		this.wuerfel1 = wuerfel1;
	}

	/**
	 * setter für den 2ten Würfel
	 * 
	 * @param wuerfel2
	 *            der zweite übergebene Würfel
	 */
	public void setWuerfel2(Wuerfel wuerfel2) {
		this.wuerfel2 = wuerfel2;
	}

	/**
	 * Setter fuer den wurf. Die dritte Zahl ist die Addition der beiden Zahlen
	 *
	 */
	public void setWurf(int[] wurf) {
		this.wurf[0] = wurf[0];
		this.wurf[1] = wurf[1];
		this.wurf[2] = wurf[0] + wurf[1];
	}

	/**
	 * Die Methode speichert ein Spiel ab bzw. serialisiert das aktuelle
	 * Spiel-Objekt. Bzw. macht es persistent. Speichert das aktuelle objekt im
	 * Verzeichnis ab.
	 */
	
	public void speichern(String file) {
		ObjectOutputStream o = null;
		try {
			FileOutputStream os = new FileOutputStream(file);
			o = new ObjectOutputStream(os);
			o.writeObject(this);
			o.flush();
			os.flush();
		} catch (FileNotFoundException e) {
			//System.err.println("Konnte spiel.ser nicht erzeugen.");
			//e.printStackTrace();
		} catch (IOException e) {
			//System.err.println("Fehler bei der Ein-/ Ausgabe");
			//e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				//System.err.println("Fehler beim schliessen der Datei.");
				//e.printStackTrace();
			}
		}
		System.out.println("Spiel wurde gespeichert!");
	}

	/**
	 * Diese Methode schreibt nach einem vollstaendig gespielten spiel die
	 * Spiele Infos in die spielInfos.log datei.
	 * 
	 * @param infos
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean speicherSpielInformationen(String infos) {
		writeSpielerLogCount();
		try {
			Files.write(Paths.get(ResourceLoader.load("spielCounter.log")+""),
					("\nSpiel: " + spieleInfosLogCount + "\n" + infos + "\n").getBytes(), StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dialog um das Spiel zu beenden. Wenn man das Spiel beenden moechte
	 * drueckt man eine beliebige Zahl. Moechte man das Spiel doch nicht beenden
	 * dreuckt man die 0.
	 */
	private void spielBeenden() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int e;
		System.out.println(
				"Druecken Sie die 0 um das Spiel zu beenden.(Druecken Sie eine beliebige Zahl ausser die 0, wenn Sie nicht beenden wollen.)");
		do {
			try {
				scan = new Scanner(System.in);
				e = scan.nextInt();
				if (e == 0) {
					System.out.print("Spiel beenden");
					gibPunkteAus();
					warten();
					System.exit(0);

				} else {
					return;
				}
			} catch (Exception ex) {
				System.out.println("Druecken Sie eine beliebige Zahl.");
			}

		} while (true);

	}

	/**
	 * Spielen setzt sich aus dem Spiel initialiseren, den einzelnen Runden und
	 * zum Schluss den Gewinner ermitteln zusammen.
	 */
	@Override
	public void spielen(int anzspieler, int maxrundenanzahl, int kiAnzahl, String[] name) {
		loadSpielerLogCount();
		spieleranzahl = anzspieler;
		maximaleRundenAnzahl = maxrundenanzahl;
		int a = spieleranzahl - kiAnzahl;
		for (int i = 1; i <= spieleranzahl; i++) {
			if (a >= 1) {
				spieler.add(new Spieler(i, FarbEnum.getFarbe(i), name[i - 1]));
				a--;
			} else {
				Spieler temp = new KiEasy(i, FarbEnum.getFarbe(i), name[i - 1], this);
				temp.setIstKi(true);
				temp.setName("KI: "+ name[i-1]);
				spieler.add(temp);
			}
			setSpielerAmZugAktuell(0);
			setSpielerAmZug(spieler.get(0));
		}
		if(spielerAmZug.isIstKi()){
			
		}else{
			runden();
		}
		
	}

	public void spielen2() {
		loadSpielerLogCount();
		spielInitialisieren();
		runden();
		gewinnerErmitteln();
		logProbabilityOfFields();
		System.out.println((spielInformationen()));
		// speicherSpielInformationen(spielInformationen());
	}

	// Hilfsmethoden

	// HILFSMETHODEN AUSGABE SPIELBRETT ANFANG
	/**
	 * Ermittelt die Spieler die sich auf einem bestimmten Spielfeld befinden
	 * und gibt deren IDs als String zurueck.
	 * 
	 * @param spielfeld
	 * @return
	 */
	private String spielerAufSpielFeldErmitteln(Spielfeld spielfeld) {
		String spielerAufSpielfeld = "";
		for (Spieler s : spieler) {
			if ((s.getPosition()) == spielfeld.getPosition()) {
				spielerAufSpielfeld += s.getID() + " ";
			}

		}

		return "Spieler auf diesem Feld: [ " + spielerAufSpielfeld + "]";
	}

	/**
	 * gibt informationen ueber die spieler aus wie Seine ID, Farbe, Position,
	 * Guthaben und sein Vermoegen.
	 * 
	 * @param spieler
	 *            desen infos ermittelt werden.
	 * @return die Informationen als String
	 */
	private String spielerInfos(Spieler spieler) {
		String spielerInfos = "Spieler: " + spieler.getID() + ", Farbe: " + spieler.getFarbe() + ", Position: "
				+ (spieler.getPosition()) + ", Feld: " + spielbrett.getSpielfeld()[spieler.getPosition()].getName()
				+ ", Guthaben: " + spieler.getGuthaben() + ", Vermoegen: " + vermoegenBerechnen(spieler) + "\n";

		return spielerInfos;
	}

	/**
	 * Diese Methode zahelt wieviele Menschen und wie viele Kis am Anfang des
	 * Spiels beteiligt sind.
	 */
	private void spielerKiAnzahlAmAnfangCount() {
		for (Spieler s : spieler) {
			if (s.isIstKi()) {
				kisAnfangsZahl++;
			} else {
				spielerAnfangsZahl++;
			}
		}

	}

	/**
	 * Gibt relevante Informationen am Ende des Spieles aus. Diese
	 * Infomormationen werden geloggt, diese Daten werden Grundlage fuer eine
	 * Stochastisch agierende Ki die noch impementiert wird.
	 * 
	 * @return
	 */
	private String spielInformationen() {
		String ausgabe = "";
		ausgabe += "\nDas Spiel dauerte " + (aktuelleRunde - 1) + " von " + maximaleRundenAnzahl + " Runden.\n";
		ausgabe += "Spieler am Anfang: " + spielerAnfangsZahl + " davon Kis: " + kisAnfangsZahl + "\n";
		ausgabe += "Davon noch Spieler zum Schluss im Spiel: " + spieler.size() + "\n";
		ausgabe += "Insgesamt wurden " + gesamtZuege + " Zuege gespielt.\n";
		ausgabe += "Absolute Zahlen vom landen der Spieler/Kis auf den einzelnen Feldern:\n";
		for (int i = 0; i < spielbrett.getSpielfeld().length; i++) {
			ausgabe += "Position " + i + " " + spielbrett.getSpielfeld()[i].getName() + ": " + logArr[i] + " ("
					+ (((double) ((int) (probArr[i] * 100))) / 100) + "%)\n";
		}
		return ausgabe;
	}

	/**
	 * Hier werden grundlegende Anfangswerte von den Spielern im Dialog
	 * initialisert, dazu gehoeren die Spieleranzahl, die maximale Rundenanzahl
	 * und das Startguthaben.
	 */
	private void spielInitialisieren() {
		spieleranzahl = this.initSpieler(this); // initialisiert die Spieler
		// (wenn spieler ausscheidet
		// muss spielerAnzahl
		// verringert werden-
		// eventuell als globale
		// variable)
		maximaleRundenAnzahl = rundenAnzahlFestlegen(); // legt die
		// maximaleRundenAnzahl
		// fest
		startGuthaben = this.startGuthabenFestlegen(); // legt das
		// startguthaben
		// fest
		spielerKiAnzahlAmAnfangCount();
		System.out.print("Initialisere Spiel");
		gibPunkteAus();
	}

	// Methoden fuer das Loggen

	/**
	 * Wird dazu benutzt das Startguthaben aller Spieler festzulegen dies macht
	 * der erste Spieler
	 * 
	 * @return gibt das festgelegte Startguthaben zurück
	 */
	private int startGuthabenFestlegen() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int startGuthaben = 0;
		boolean richtigeEingabe = false;
		System.out.println("Legen Sie das Startguthaben der Spieler fest.(1500 bis 2147483647)");
		while (!richtigeEingabe) {
			try {
				scan = new Scanner(System.in);
				startGuthaben = scan.nextInt();
				if (startGuthaben < 1500 || startGuthaben > Integer.MAX_VALUE) {
					System.out.println("Geben Sie eine Zahl zwischen 1500 und 2147483647 ein.");
				} else {
					richtigeEingabe = true;
				}
			} catch (Exception e) {
				System.out.println("Geben Sie eine Zahl zwischen 1500 und 2147483647 ein!");
			}
		}
		for (Spieler spieler : spieler) {
			spieler.setGuthaben(startGuthaben);
		}
		return startGuthaben;
	}

	/**
	 * Diese Methode regelt den Verkauf von Besitzrechkarten
	 * 
	 * @param Der
	 *            spieler der seine Karten verkaufen will
	 */
	// HIER MUSS ERGAENZT WERDEN, WENN EIN SPIELER EINE BESITZECHTKARTE MIT
	// HAUESER ETC VERKAUFT DANN MUSS AUF DEN ANDEREN ZUGEHOERIGEN STRASSEN AUCH
	// DIE HAUSER ETC PLATT GEMACHT WERDEN.
	public void verkaufen(Spieler spieler) {
		int eingabe;
		double vermoegen = 0;
		Besitzrechtkarten b = null;

		if (spieler.getBesitzrechtkarten().isEmpty()) {
			System.out.println("Sie haben keine Besitzrechtkarten die sie verkaufen koennten.");
			return;
		}

		System.out.println(
				"Welche ihrer Besitzrechkarten möchten sie verkaufen? Geben sie die Nummer für die passende Besitzrechkarte ein.");
		System.out.println("Geben Sie die 0 ein wenn Sie doch keine Besitzrechtkarte verkaufen moechten.");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < spieler.getBesitzrechtkarten().size(); i++) {
			System.out.print((i + 1) + ": ");
			System.out.println(spieler.getBesitzrechtkarten().get(i));
		}

		while (true) {
			try {
				scan = new Scanner(System.in);
				eingabe = scan.nextInt();
				if (eingabe < 0 || eingabe > spieler.getBesitzrechtkarten().size()) {
					System.out.println("Nur Zahlen zwischen " + 0 + " und " + spieler.getBesitzrechtkarten().size()
							+ " eingeben.(inclusive)");
				} else {
					if (eingabe == 0) {
						System.out.println("Sie haben keine Besitzrechtkarte verkauft.");
						return;
					} else {
						b = spieler.getBesitzrechtkarten().get(eingabe - 1);
						break;
					}
				}
			} catch (Exception e) {
				System.out.println(
						"Geben Sie Zahlen zwischen " + 0 + " und " + spieler.getBesitzrechtkarten().size() + " ein!");
			}
		}

		if (b.istBahnhofBesitzrechtkarte()) {
			vermoegen = spieler.getGuthaben() + Bahnhof.getKaufpreis();
			spieler.setGuthaben(vermoegen);
			spieler.getBesitzrechtkarten().remove(eingabe - 1);
			System.out.println("Sie haben ihre Bahnhofskarte verkauft.");

		} else if (b.istStrassenBesitzrechtkarte()) {

			Strasse s = (Strasse) b.getSpielfeld();
			if (s.getHotel() != null) {
				vermoegen = spieler.getGuthaben() + s.getKaufpreis() + Hotel.getKaufpreis() / 2;
				spieler.setGuthaben(vermoegen);
				spieler.getBesitzrechtkarten().remove(eingabe - 1);
			} else if (s.getHaus1() != null && s.getHaus2() != null) {
				vermoegen = spieler.getGuthaben() + s.getKaufpreis() + Haus.getKaufpreis() / 2
						+ Haus.getKaufpreis() / 2;
				spieler.setGuthaben(vermoegen);
				spieler.getBesitzrechtkarten().remove(eingabe - 1);
			} else if (s.getHaus1() != null) {
				vermoegen = spieler.getGuthaben() + s.getKaufpreis() + Haus.getKaufpreis() / 2;
				spieler.setGuthaben(vermoegen);
				spieler.getBesitzrechtkarten().remove(eingabe - 1);
			} else {
				vermoegen = spieler.getGuthaben() + s.getKaufpreis();
				spieler.setGuthaben(vermoegen);
				spieler.getBesitzrechtkarten().remove(eingabe - 1);
			}
			for (Besitzrechtkarten i : spieler.getBesitzrechtkarten()) {
				if (i.istStrassenBesitzrechtkarte()) {
					Strasse a = (Strasse) i.getSpielfeld();
					if (a.getStrassenID() == s.getStrassenID() && a != s) {
						if (a.getHotel() != null) {
							vermoegen = spieler.getGuthaben() + Hotel.getKaufpreis() / 2;
							spieler.setGuthaben(vermoegen);
							a.setHotel(null);
						} else if (a.getHaus1() != null && a.getHaus2() != null) {
							vermoegen = spieler.getGuthaben() + Haus.getKaufpreis() / 2 + Haus.getKaufpreis() / 2;
							spieler.setGuthaben(vermoegen);
							a.setHaus1(null);
							a.setHaus2(null);
						} else if (a.getHaus1() != null) {
							vermoegen = spieler.getGuthaben() + Haus.getKaufpreis() / 2;
							spieler.setGuthaben(vermoegen);
							a.setHaus1(null);
						}
					}
				}
			}

			System.out.println("Sie haben ihre Strassekarte verkauft.");

		} else if (b.istVersorgungswerkBesitzrechtkarte()) {
			vermoegen = spieler.getGuthaben() + Versorgungswerk.getKaufpreis();
			spieler.setGuthaben(vermoegen);
			spieler.getBesitzrechtkarten().remove(eingabe - 1);
			System.out.println("Sie haben ihre Versorgungswerkskarte verkauft.");

		}

	}

	@Override
	public void verkaufenGUI(Spielfeld sf) {
		Besitzrechtkarten b;
		if (sf.getClass().equals(Versorgungswerk.class) || sf.getClass().equals(Bahnhof.class)
				|| sf.getClass().equals(Strasse.class)) {
			b = new Besitzrechtkarten(sf, spielerAmZug);
		} else {
			System.out.println("Nicht verkaufbar");
			return;
		}
		if (istBesitzrechtkarteVonSpieler(b, spielerAmZug)) {
			if (b.istVersorgungswerkBesitzrechtkarte()) {
				b.getBesitzer().setGuthaben(b.getBesitzer().getGuthaben() + Versorgungswerk.getKaufpreis());
				b.getBesitzer().getBesitzrechtkarten().remove(b);
				System.out.println("Sie haben ihre Versorgungswerkskarte verkauft.");
			}
			if (b.istBahnhofBesitzrechtkarte()) {
				System.out.println("Sie haben ihr " + b.getSpielfeld().getName() + " verkauft.");
				b.getBesitzer().setGuthaben(b.getBesitzer().getGuthaben() + Bahnhof.getKaufpreis());
				b.getBesitzer().getBesitzrechtkarten().remove(b);
			}
			if (b.istStrassenBesitzrechtkarte()) {
				if (!(macheHotelsUndHaueserPlatt(b.getBesitzer(), b))) {
					Strasse s = (Strasse) b.getSpielfeld();
					System.out.println("Sie haben ihr " + b.getSpielfeld().getName() + " verkauft.");
					b.getBesitzer().setGuthaben(b.getBesitzer().getGuthaben() + s.getKaufpreis());
					b.getBesitzer().getBesitzrechtkarten().remove(b);
				}
			}
		} else {
			System.out.println("Das Spielfeld gehört ihnen nicht");
		}

	}

	/**
	 * Berechnet das Vermoegen eines Spielers. Dabei wird sein Barguthaben
	 * miteinbezogen. Pro Hotel bekommt er +900, pro Haus +200 und pro Strasse,
	 * Bahnhof oder Versorgungswerk, das Geld das er bezahlt hat.
	 * 
	 * @param spieler
	 *            desen vermoegen ermittelt werden soll.
	 * @return das aktuelle vermoegen des spielers
	 */
	private double vermoegenBerechnen(Spieler spieler) {
		double vermoegen = spieler.getGuthaben();
		for (Besitzrechtkarten bes : spieler.getBesitzrechtkarten()) {
			if (bes.istBahnhofBesitzrechtkarte()) {
				vermoegen += Bahnhof.getKaufpreis();
			}
			if (bes.istVersorgungswerkBesitzrechtkarte()) {
				vermoegen += Versorgungswerk.getKaufpreis();
			}
			if (bes.istStrassenBesitzrechtkarte()) {
				Strasse s = (Strasse) bes.getSpielfeld();
				vermoegen += s.getKaufpreis();
				if (s.getHotel() != null) {
					vermoegen += 900;
				} else {
					if (s.getHaus1() != null) {
						vermoegen += 200;
					} else {
						if (s.getHaus2() != null) {
							vermoegen += 200;
						}
					}
				}
			}
		}
		return (((double) ((int) (vermoegen * 100))) / 100);
	}

	/**
	 * Legt den Spiele-Thread fuer eine Sekunde schlafen.
	 */
	private void warten() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode speichert nach einem zuende gespielten Spiel die neue
	 * Spiele Anzahl in der spielCounter.log datei.
	 * 
	 * @return
	 */
	private boolean writeSpielerLogCount() {
		spieleInfosLogCount = spieleInfosLogCount + 1;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(new InputStreamReader(ResourceLoader.load("spielCounter.log"))+""));
			writer.print(spieleInfosLogCount);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			writer.flush();
			writer.close();
		}
	}

	/**
	 * Simuliert einen Wurf zweier Wuerfel.
	 * 
	 * @return Array welches an der ersten Stelle Augenzahl1, an zweiter
	 *         Augenzahl2 und an dritter die Summe haelt.
	 */
	@Override
	public int[] wuerfeln() {
		int augen1 = wuerfel1.wuerfeln();
		int augen2 = wuerfel2.wuerfeln();
		wurf[0] = augen1;
		wurf[1] = augen2;
		wurf[2] = augen1 + augen2;
		setEreigniskarte(ereigniskartenmanager.zieheEreigniskarte());
		return wurf;

	}

	/**
	 * Diese Methode würfelt für den aktuellen Spieler und behandelt die darauf
	 * folgende Bewegung des Spielers auf die Felder: Strasse..................
	 * Bahnhof.................. Versorgungswerk..........
	 * Ereignisfeld............. FreiParken...............
	 * GeheinsGefängnis......... Los......................
	 * Steuer................... Gefängnis................
	 * 
	 * @param spielerAmZug
	 *            der gerade am Zug ist
	 */
	@Override
	public void zug() {

		wurf = wuerfeln();
		int position = spielerAmZug.getPosition();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesFeld = spielfeld[position];

		System.out.println("\n"+spielerAmZug.getName() + " am Zug.");
		System.out.println(spielerInfos(spielerAmZug));
		// verkaufen(spieler);

		System.out.println("Sie befinden sich auf \"" + aktuellesFeld.getName() + "\". Position "
				+ aktuellesFeld.getPosition() + ".");

		gesamtZuegeInc();
		logCountOfFields(position);

		// ist im gefaengnis?
		 if (position == 10) {
		 if (!aktionGefaengnis(spielerAmZug)) {
		 return;
		 }
		 }

		// wurf = wuerfeln();
		//
		// System.out.println("Druecken Sie eine beliebige Zahl zum
		// wuerfeln.");
		// if(!spielerAmZug.isIstKi()){
		// beliebigeZahlDruecken();
		// }
		//
		// System.out.println("Sie haben: " + wurf[0] + " und " + wurf[1] +
		// " = " + wurf[2] + " gewuerfelt.");
		// warten();
		// }

		System.out.println("Druecken Sie eine beliebige Zahl zum wuerfeln.");
		if (!spielerAmZug.isIstKi()) {
//			beliebigeZahlDruecken();
		}
		System.out.println("Sie haben: " + wurf[0] + " und " + wurf[1] + " = " + wurf[2] + " gewuerfelt.");
		warten();

		int neuePosition = spielerAmZug.getPosition() + wurf[0] + wurf[1];
		// +500 wenn ueber los
		if (neuePosition >= 40) {
			neuePosition = neuePosition % 10;
			spielerAmZug.setGuthaben(spielerAmZug.getGuthaben() + 500);
			System.out.println("Sie kommen ueber Los und streichen 500 ein.");
		}

		spielerAmZug.setPosition(neuePosition);
		Spielfeld neuesFeld = spielfeld[neuePosition];

		System.out.println(
				"Sie befinden sich nun auf \"" + neuesFeld.getName() + "\". Position " + neuesFeld.getPosition() + ".");

		aktionSpielfeld(spielerAmZug, neuesFeld);
		// Spieler verändert seine position
		// ACHTUNG WAS PASSIERT WENN
		// POSITION UEBER 40 ETC... HIER
		// IMPLEMENTIEREN

		// spieler pos wird verändert (ist wurf pasch? -> ja nochmal wüerfeln ,
		// aber feldaktion davor ausführbar sein) , drittes mal pasch ->
		// gefaeangnis)
		// feldaktionen (je nach dem was fuer ein feld erreicht wird sind
		// aktionen moeglich)
		// über 40(los) + 500
		// aktueller spieler muss gändert werdaen
		// spieler kommen zyklisch an die reihe (1-2-3-4-5 --- 1-2-3-4-5 z.B.)
		
		if(spielerAmZug.getGuthaben() <= 0){
			loescheSpielerAusSpiel(spielerAmZug);
		}
		
		if(spielerAmZug.isIstKi()){
			((Ki) spielerAmZug).kontrolleAbgebenKI();
		}

	}

	@Override
	public void zug1() {

		wurf = wuerfeln();
		int position = spielerAmZug.getPosition();
		Spielfeld[] spielfeld = spielbrett.getSpielfeld();
		Spielfeld aktuellesFeld = spielfeld[position];

		System.out.println("\nSpieler " + spielerAmZug.getID() + " am Zug.");
		System.out.println(spielerInfos(spielerAmZug));
		// verkaufen(spieler);

		System.out.println("Sie befinden sich auf \"" + aktuellesFeld.getName() + "\". Position "
				+ aktuellesFeld.getPosition() + ".");

		gesamtZuegeInc();
		logCountOfFields(position);

		// ist im gefaengnis?
		// if (position == 10) {
		// if (!aktionGefaengnis(spielerAmZug)) {
		// return;
		// }

//		if (spielerAmZug.isIstKi()) {
//
//			aktionSpielfeld(spielerAmZug, aktuellesFeld);
//		}

		// wurf = wuerfeln();
		//
		// System.out.println("Druecken Sie eine beliebige Zahl zum
		// wuerfeln.");
		// if(!spielerAmZug.isIstKi()){
		// beliebigeZahlDruecken();
		// }
		//
		// System.out.println("Sie haben: " + wurf[0] + " und " + wurf[1] +
		// " = " + wurf[2] + " gewuerfelt.");
		// warten();
		// }

		int neuePosition = spielerAmZug.getPosition() + wurf[0] + wurf[1];
		// +500 wenn ueber los
		if (neuePosition >= 40) {
			neuePosition = neuePosition % 10;
			spielerAmZug.setGuthaben(spielerAmZug.getGuthaben() + 500);
			System.out.println("Sie kommen ueber Los und streichen 500 ein.");
		}

		spielerAmZug.setPosition(neuePosition);
		Spielfeld neuesFeld = spielfeld[neuePosition];

		System.out.println("Sie befinden sich nun auf \"" + neuesFeld.getName() + "\". Position.");

		// Spieler verändert seine position
		// ACHTUNG WAS PASSIERT WENN
		// POSITION UEBER 40 ETC... HIER
		// IMPLEMENTIEREN

		// spieler pos wird verändert (ist wurf pasch? -> ja nochmal wüerfeln ,
		// aber feldaktion davor ausführbar sein) , drittes mal pasch ->
		// gefaeangnis)
		// feldaktionen (je nach dem was fuer ein feld erreicht wird sind
		// aktionen moeglich)
		// über 40(los) + 500
		// aktueller spieler muss gändert werdaen
		// spieler kommen zyklisch an die reihe (1-2-3-4-5 --- 1-2-3-4-5 z.B.)

	}

	@Override
	public void zug2() {
		if (feldHatBesitzer()) {
			kaufGrundStueckEins();
		}
		// if(spielerAmZug.getPosition()==30){
		// aktionSpielfeld(spielerAmZug,
		// spielbrett.getSpielfeld()[spielerAmZug.getPosition()]);
		// }

		aktionSpielfeld();

	}
	@Override
	public void MailSenden(String an, String betreff, String text, String anhangPfad1, 
			String anhangName1, String anhangPfad2,String anhangName2) {
		new Mail(an, betreff, text, anhangPfad1, anhangName1,anhangPfad2, anhangName2);
		
	}
	@Override
	public void laden() {
		// TODO Auto-generated method stub
		
	}

}
