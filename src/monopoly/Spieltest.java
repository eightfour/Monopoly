package monopoly;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * In dieser Klasse wird ein Spiel getestet, bzw gespielt.
 *
 */
public class Spieltest implements IBediener, Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 4832705609579808367L;

	/**
	 * Statische Main Methode in der das Spielen bzw. testen stattfindet.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

//		Spiel spiel = new Spiel();
		// spiel.spielen();

	}

	@Override
	public boolean aktionGefaengnis2(boolean ent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int bauen(Strasse s) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean darfbauen(Strasse s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String eingabe(Boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean feldHatBesitzer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAktuelleRunde() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAnzahlSpielerAufFeld(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Spielbrett getSpielbrett() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spieler getSpielerAmZug() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getWurf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean irregulaeresEnde() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean istBesitzbaresFeldAktuell() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean istBesitzrechtkarteVonSpieler(Spielfeld fd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean kaufGrundStueckEins() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void kaufGrundStueckZwei() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kontrolleAbgeben() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean letzteRundeErreicht() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void spielen(int value, int runden, int i, String[] namen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verkaufenGUI(Spielfeld sf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] wuerfeln() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void zug1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zug2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ereigniskarte getEreigniskarte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aktionEreignisfeld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Spieler> getSpieler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMaximaleRundenAnzahl() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void speichern(String file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spiel laden(String pfad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void laden() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MailSenden(String an, String betreff, String text, String anhangPfad1, String anhangName1,
			String anhangPfad2, String anhangName2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPdf(String pfad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void speichern() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletefiles() {
		// TODO Auto-generated method stub
		
	}



	

}
