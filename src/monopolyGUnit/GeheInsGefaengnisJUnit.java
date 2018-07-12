package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.FarbEnum;
import monopoly.GeheInsGefaengnis;
import monopoly.Spiel;
import monopoly.Spieler;

/**
 * 
 * Tests fuer Aktion-GeheInsGefaengnis
 *
 */
public class GeheInsGefaengnisJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = null;
	Spieler s1 = null;
	GeheInsGefaengnis gig = null;

	/**
	 * Test 1: Der Spieler kommt auf das GeheInsGefaengnis-Feld. Erwartetes
	 * Ergebnis: Der Spieler wird ins Gefaengnis verschoben.
	 */
	@Test
	public void testGeheInsGefaengnis1() {
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		gig = (GeheInsGefaengnis) spiel.getSpielbrett().getSpielfeld()[30];
		s1.setPosition(gig.getPosition() - 1);
		spiel.aktionGeheInsGefaengnis(s1);
		assertTrue(s1.getPosition() == (spiel.getSpielbrett().getSpielfeld()[10].getPosition() - 1));
	}

}
