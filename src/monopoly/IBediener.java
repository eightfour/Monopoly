package monopoly;

import java.util.ArrayList;

/**
 * 
 * @author Thomas Dieses Interface ist zur Interkation eines Spielers mit dem
 *         Spiel gedacht.
 */
public interface IBediener {

	boolean aktionGefaengnis2(boolean ent);

	/**
	 * @param bewegt
	 *            die Spielfigur des aktuellen Spielers um die Summe der beiden
	 *            geworfenen Würfeln. geht er ueber das Los-Spielfeld bekommt
	 *            der jeweilige Spieler das Los-Geld.
	 */
	// void ziehen ();

	/**
	 * 
	 * setzt den Spieler ins gefaengnis ohne das Los-Geld einzustreichen
	 */
	// void insGefaengnisZiehen();
	/**
	 * gibt die infos über den aktuellen spieler aus
	 * 
	 * @return gibt die Informationen Geld, Farbe, Besitzrechtkarten
	 */
	// FarbEnum infoaktSpieler();

	/**
	 * @return gibt das aktuelle Spielbrett in CSV-Notation als String zurück
	 * 
	 */
	// String ausgabeSpielBrett();

	/**
	 * Löst die Aktion des Felds auf dem der aktuelle Spieler steht aus: Steht
	 * er z.B auf einem fremden Grundstueck muss er miete bezahlen. Steht er auf
	 * dem gehe ins Gefaengnis Feld wird er ins Gefaengnis gesetzt. Steht er auf
	 * einem eigenen Grundstueck oder unverkauftem Grundstueck passiert nichts.
	 * (Falls wir später eine bewegungsanimation einbauen, sollten wir die
	 * bewegungsanimation unabhängig von der Spielfeldaktion haben.)
	 */
	// void aktionSpielfeld();

	int bauen(Strasse s);

	/**
	 * Gibt am Ende des Zuges des aktuellen Spielers die kontrolle an den
	 * naechesten Spieler in der reihe weiter.
	 */
	// void kontrolleAbgeben(Spieler aktuellerSpieler);

	/**
	 * Gibt die moeglichkeit ein Haus auf eine gewaehlte Besitzrechtkarte zu
	 * platzieren. Wenn das Haus nicht gebaut werden kann wird ein Fehler
	 * geworfen.
	 * 
	 * @param die
	 *            besitzrechtkarte auf die das Haus gebaut werden soll
	 * 
	 */
	// boolean bauen(Besitzrechtkarten b);
	/**
	 * 
	 * @param s
	 * @return
	 */
	boolean darfbauen(Strasse s);
	/**
	 * 
	 * @param b
	 * @return
	 */
	String eingabe(Boolean b);
	/**
	 * 
	 * @return
	 */
	boolean feldHatBesitzer();
	/**
	 * 
	 * @return
	 */
	int getAktuelleRunde();

	// void spielen(int spieleranzahl,int maxrundenanzahl, int
	// kianzahl/*,String[]namen*/);

	public int getAnzahlSpielerAufFeld(int index);

	/**
	 * Wenn die methode aufgerufen wird zieht der aktuelle spieler eine
	 * Ereigniskarte. Die aktion wird direkt ausgefuehrt.
	 */
	// void ereigniskarteZiehen();
	/**
	 * Je nachdem welche Entscheidung der Spieler trifft gibt es true für ja und
	 * false für nein zurück
	 * 
	 * @return gibt die Entscheidung des Spielers zurück
	 */
	// boolean entscheidung();

	// void aktionSpielfeld(Spieler spieler, Spielfeld neuesFeld);

	// void verkaufen(Spieler spieler);

	// boolean entscheidungKi(Spieler s);

	Spielbrett getSpielbrett();

	Spieler getSpielerAmZug();

	int[] getWurf();

	boolean irregulaeresEnde();

	public boolean istBesitzbaresFeldAktuell();

	public boolean istBesitzrechtkarteVonSpieler(Spielfeld fd);

	/**
	 * Kauft wenn möglich das Grundstueck auf dem der aktuelle Spieler steht.
	 * 
	 * @return
	 */
	boolean kaufGrundStueckEins();

	// public boolean istBesitzbaresFeld(Spielfeld f);

	void kaufGrundStueckZwei();

	// public ArrayList<Spieler> getSpieler();

	void kontrolleAbgeben();

	boolean letzteRundeErreicht();

	void spielen(int value, int runden, int i, String[] namen);

	void verkaufenGUI(Spielfeld sf);

	/**
	 * 
	 * @return liefert 2 zufällige int-Werte zwischen 1 und 6 zurück was
	 *         passiert bei einem Pasch? Wird noch extra gelassen falls wir
	 *         spaeter den Spielverlauf entschleunigt gestalten wollen (Wuerfeln
	 *         muss separat von ziehen existieren da im Gefaengnis gewuerfelt
	 *         aber nicht unbedingt gezogen wird)
	 */
	int[] wuerfeln();

	void zug1();

	void zug2();
	
	Ereigniskarte getEreigniskarte();
	
	void aktionEreignisfeld();

	void zug();
	
	ArrayList<Spieler> getSpieler();
	
	long getMaximaleRundenAnzahl();

	void speichern(String file);
	
	Spiel laden(String pfad);
	
	void laden();
	
	public void MailSenden(String an, String betreff, String text, String anhangPfad1, String anhangName1,
			String anhangPfad2, String anhangName2);
	
	public void createPdf(String pfad);
	
	public void speichern();
	
	public void deletefiles();

}
