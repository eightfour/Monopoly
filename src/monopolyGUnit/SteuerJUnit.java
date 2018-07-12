package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.FarbEnum;
import monopoly.Spiel;
import monopoly.Spieler;
import monopoly.Steuer;

/**
 * 
 * Tests fuer Aktion-Steuer
 *
 */
public class SteuerJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = null;
	Spieler s1 = null;
	Steuer sf = null;

	/**
	 * Test 1: Der Spieler kommt auf das Einkommenssteuer-Feld.
	 */
	@Test
	public void testSteuer1() {
		System.err.println("TEST1:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double g1 = s1.getGuthaben();
		sf = (Steuer) spiel.getSpielbrett().getSpielfeld()[4];
		s1.setPosition(sf.getPosition());
		spiel.aktionSteuer(s1);
		double st = (g1 / 10) * 3;
		assertTrue(s1.getGuthaben() == g1 - st);
		// ----------------------------------------------------
		spiel = null;
		s1 = null;
		sf = null;
	}

	/**
	 * Test 3: Der Spieler kommt auf das Zusatzsteuer-Feld.
	 */
	@Test
	public void testSteuer3() {
		System.err.println("TEST3:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double g1 = s1.getGuthaben();
		sf = (Steuer) spiel.getSpielbrett().getSpielfeld()[38];
		s1.setPosition(sf.getPosition());
		spiel.aktionSteuer(s1);
		double st = g1 / 10;
		assertTrue(s1.getGuthaben() == g1 - st);
		// ----------------------------------------------------
		spiel = null;
		s1 = null;
		sf = null;
	}

}
