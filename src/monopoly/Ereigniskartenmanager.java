package monopoly;

import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import monopolyGui.ResourceLoader;

public class Ereigniskartenmanager implements IDatenzugriff, Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = -118677537407224405L;

	/**
	 * Eine ArrayList von Ereigniskarten, die alle Ereigniskarten haelt.
	 */
	private ArrayList<Ereigniskarte> ereigniskarten = null;

	/**
	 * Der Konstruktor Ereigniskartenmanager. Die Methode kartenEinlesen wird
	 * ausgefuert.
	 * 
	 */
	public Ereigniskartenmanager() {
		laden();
	}

	/**
	 * Gibt die Ereigniskarten aus. Wenn keine vorhanden sind wird die Methode
	 * kartenEinlesen() ausgeführt.
	 * 
	 * @return Liste der Ereigniskarten
	 */
	public ArrayList<Ereigniskarte> getEreigniskarten() {
		if (ereigniskarten == null) {
			laden();
		}
		return ereigniskarten;
	}

	/**
	 * Liest die CSV ein und teilt diese in ein ArrayList ein. CSV Text,
	 * Guthabenänderung, Vorrücken od. Zurück, Alle Spieler Zahlung, Häuser und
	 * Hotel bezahlen.
	 * 
	 * @exception java.lang.NullPointerException
	 *                Die Datei wurde nicht gefunden.
	 */
	@Override
	public void laden() {
		ereigniskarten = new ArrayList<Ereigniskarte>();
		String all;
		String[] arrString = null;
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(ResourceLoader.load("Ereigniskarten.csv")));
			all = bufferedReader.readLine();
			arrString = all.split(";");

		} catch (IOException e) {
			System.out.println(e.getMessage());
			ereigniskarten = null;
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < arrString.length; i += 6) {
			ereigniskarten.add(new Ereigniskarte(arrString[i], Integer.parseInt(arrString[i + 1]),
					Integer.parseInt(arrString[i + 2]), Integer.parseInt(arrString[i + 3]),
					Integer.parseInt(arrString[i + 4]), Integer.parseInt(arrString[i + 5])));
		}

	}

	
	/**
	 * Gibt eine Zufällige Ereigniskarte aus
	 * 
	 * @return Ereigniskarte
	 */
	public Ereigniskarte zieheEreigniskarte() {
		if (ereigniskarten == null) {
			laden();
		}
		Random r = new Random();
		int rand = r.nextInt(ereigniskarten.size());
		return ereigniskarten.get(rand);
	}

	@Override
	public void MailSenden(String an, String betreff, String text, String anhangPfad1, String anhangName1,
			String anhangPfad2, String anhangName2) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public Spiel laden(String pfad) {
		return null;
		
	}

	@Override
	public void speichern(String string) {
		// TODO Auto-generated method stub
		
	}

}
