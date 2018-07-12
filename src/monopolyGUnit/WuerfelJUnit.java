package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Wuerfel;

/**
 * @author Tim
 *@version 0.1
 */

/**
 * JUnit Testklasse fuer die Wuerfelklasse.
 */
// TODO Eventuell noch mehr nicht triviale tests implementieren.(auch in
// hinsicht ob die maximalzahl der wuerfel auf 2 begrenzt wird.)
public class WuerfelJUnit {

	/**
	 * Wuerfel als Testobjekt.
	 */
	Wuerfel w1;

	/**
	 * Es wird getestet ob bei 1000000 wuerfen eine Zahl nicht in der erwarteten
	 * Range liegt.
	 */
	@Test
	public void testStatistik() {
		boolean stat = true;
		Wuerfel[] wuerfelArray = new Wuerfel[1000000];
		for (int i = 0; i < wuerfelArray.length; i++) {
			wuerfelArray[i] = new Wuerfel();
			if (wuerfelArray[i].getRandomNumber() < 1 | wuerfelArray[i].getRandomNumber() > 6) {
				stat = false;
			}
			wuerfelArray[i].wuerfeln();
			if (wuerfelArray[i].getRandomNumber() < 1 | wuerfelArray[i].getRandomNumber() > 6) {
				stat = false;
			}
		}
		assertTrue(stat);

	}

	/**
	 * Es wird der Triviale Test durchgefuehrt ob die Zufallszahl innerhalb des
	 * gueltigen Bereichs zwischen inklusive 1 und 6 liegt. Es wird erwartet das
	 * die Zufallszahl innerhalb des Bereichs liegt.
	 */
	@Test
	public void testZahlInRange1() {
		w1 = new Wuerfel();
		assertTrue(w1.getRandomNumber() > 0 & w1.getRandomNumber() < 7);
		w1 = null;
	}

	/**
	 * Es wird der Triviale Test durchgefuehrt ob die Zufallszahl innerhalb des
	 * gueltigen Bereichs zwischen inklusive 1 und 6 liegt. Es wird erwartet das
	 * die Zufallszahl auserhalb des Bereichs liegt.
	 */
	@Test(expected = AssertionError.class)
	public void testZahlInRange2() {
		w1 = new Wuerfel();
		assertTrue(w1.getRandomNumber() < 0 & w1.getRandomNumber() < 7);
		w1 = null;
	}

	/**
	 * Es wird der Triviale Test durchgefuehrt ob die Zufallszahl innerhalb des
	 * gueltigen Bereichs zwischen inklusive 1 und 6 liegt. Es wird erwartet das
	 * die Zufallszahl auserhalb des Bereichs liegt.
	 */
	@Test(expected = AssertionError.class)
	public void testZahlInRange3() {
		w1 = new Wuerfel();
		assertTrue(w1.getRandomNumber() > 0 & w1.getRandomNumber() > 7);
		w1 = null;
	}

	/**
	 * Es wird der Triviale Test durchgefuehrt ob die Zufallszahl innerhalb des
	 * gueltigen Bereichs zwischen inklusive 1 und 6 liegt. Es wird erwartet das
	 * die Zufallszahl auserhalb des Bereichs liegt.
	 */
	@Test(expected = AssertionError.class)
	public void testZahlInRange4() {
		w1 = new Wuerfel();
		assertTrue(w1.getRandomNumber() < 0 & w1.getRandomNumber() > 7);
		w1 = null;
	}

}
