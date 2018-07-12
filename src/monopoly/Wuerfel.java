package monopoly;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Tim
 *@version 0.2
 */

/**
 * Diese Klasse modeliert einen Wuerfel der eine Zufallszahl zwischen 1 und 6
 * erzeugen kann.
 */
public class Wuerfel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7699086181241804086L;
	/**
	 * Zufallszahl
	 */
	private int randomNumber;

	/**
	 * Konstruktor. Ein Wuerfel wird immer mit einer Zufallszahl initialisiert.
	 */
	public Wuerfel() {
		setzeRand();
		setzeRand();
	}

	/**
	 * Getter fuer die aktuelle Zufallszahl.
	 * 
	 * @return die aktuelle Zufallszahl die der Wuerfel haelt.
	 */
	public int getRandomNumber() {
		return randomNumber;
	}

	/**
	 * Setter fuer die aktuelle Zufallszahl.
	 * 
	 * @param randomNumber
	 *            erwartet eine Zahl kleiner sieben und groesser null.
	 */
	private void setRandomNumber(int randomNumber) {
		if (randomNumber > 6 | randomNumber < 1) {
			try {
				setzeRand();
			} catch (Exception e) {
				System.err.println("Fehler in setzeRand Funktion.");
				e.printStackTrace();
			}
		} else {
			this.randomNumber = randomNumber;
		}
	}

	/**
	 * Die Methode setzeRand() erzeugt eine Zufallszahl und weisst diese dem
	 * aktuellen Wuerfel zu.
	 */
	private void setzeRand() {
		Random r = new Random();
		int rand = r.nextInt(6) + 1;
		setRandomNumber(rand);
	}

	/**
	 * toString() fuer ein Wuerfelobjekt. Gibt einen Wuerfel auf der Konsole
	 * aus.
	 */
	// TODO Muss eventuell noch "optisch" angepasst werden.
	@Override
	public String toString() {
		return "Wuerfel [randomNumber=" + randomNumber + "]";
	}

	/**
	 * Die Methode wuerfeln() erzeugt eine neue Zufallszahl und gibt die
	 * aktuelle Zufallszahl aus.
	 * 
	 * @return die aktuelle Zufallszahl der Würfelinstanz.
	 */
	public int wuerfeln() {
		setzeRand();
		setzeRand();
		int rand = this.randomNumber;
		return rand;
	}

}
