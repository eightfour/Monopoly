package monopolyGUnit;

import org.junit.Test;

import monopoly.FarbEnum;
import monopoly.FreiParken;
import monopoly.Spiel;
import monopoly.Spieler;

/**
 * 
 * Tests fuer Aktion-FreiParken
 *
 */
public class FreiParkenJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = null;
	Spieler s1 = null;
	FreiParken fp = null;

	/**
	 * Test 1: Der Spieler kommt auf das FreiParken-Feld.
	 */
	@Test
	public void testFreiParken1() {
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		fp = (FreiParken) spiel.getSpielbrett().getSpielfeld()[20];
		s1.setPosition(fp.getPosition() - 1);
		spiel.aktionFreiParken(s1);
	}

}
