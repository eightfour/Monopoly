package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.FarbEnum;
import monopoly.Los;
import monopoly.Spiel;
import monopoly.Spieler;

/**
 * 
 * Tests fuer Aktion-Los
 *
 */
public class LosJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = null;
	Spieler s1 = null;
	Los los = null;

	/**
	 * Test 1: Der Spieler kommt auf das Los-Feld. Erwartetes Ergebnis: Das
	 * Guthaben des Spielers wird um 500 erhoeht.
	 */
	@Test
	public void testLos1() {
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double g1 = s1.getGuthaben();
		los = (Los) spiel.getSpielbrett().getSpielfeld()[0];
		s1.setPosition(los.getPosition() - 1);
		spiel.aktionLos(s1);
		assertTrue(s1.getGuthaben() == (g1 + 500));
	}
}
