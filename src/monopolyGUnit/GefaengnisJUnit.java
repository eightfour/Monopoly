package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.FarbEnum;
import monopoly.Gefaengnis;
import monopoly.Spiel;
import monopoly.Spieler;

/**
 * 
 * Tests fuer Aktion-Gefaengnis
 *
 */
public class GefaengnisJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = null;
	Spieler s1 = null;
	Gefaengnis gf = null;

	/**
	 * Test 1: Der Spieler kommt frisch auf das Gefaengnis-Feld, und zahlt 80%
	 * seines Vermoegens.
	 */
	@Test
	public void testGefaengnis1() {
		System.err.println("TEST1:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition());

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGefaengnisCounter() == 2);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 2: Der Spieler ist bereits eine Runde auf dem Gefaengnis-Feld und
	 * zahlt.
	 */
	@Test
	public void testGefaengnis2() {
		System.err.println("TEST2:");
		System.err.println("SIE MUESSEN JA UND DANNACH NEIN EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ng = (s1.getGuthaben() - ((s1.getGuthaben() / 100) * 80));
		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(2);

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGuthaben() == ng);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 3: Der Spieler ist bereits eine Runde auf dem Gefaengnis-Feld und
	 * zahlt nicht.
	 */
	@Test
	public void testGefaengnis3() {
		System.err.println("TEST3:");
		System.err.println("SIE MUESSEN NEIN EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(2);
		int gc = s1.getGefaengnisCounter();

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGefaengnisCounter() == gc - 1);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 4: Der Spieler ist bereits eine Runde auf dem Gefaengnis-Feld und
	 * würfelt.
	 */
	@Test
	public void testGefaengnis4() {
		System.err.println("TEST4:");
		System.err.println("SIE MUESSEN 2x JA EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);

		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(2);
		int gc = s1.getGefaengnisCounter();

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGefaengnisCounter() != gc);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 5: Der Spieler ist bereits zwei Runden auf dem Gefaengnis-Feld und
	 * zahlt nicht.
	 */
	@Test
	public void testGefaengnis5() {
		System.err.println("TEST5:");
		System.err.println("SIE MUESSEN NEIN EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(1);
		int gc = s1.getGefaengnisCounter();

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGefaengnisCounter() == gc - 1);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 6: Der Spieler ist bereits zwei Runden auf dem Gefaengnis-Feld und
	 * zahlt.
	 */
	@Test
	public void testGefaengnis6() {
		System.err.println("TEST6:");
		System.err.println("SIE MÜSSEN JA UND NEIN EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ng = (s1.getGuthaben() - ((s1.getGuthaben() / 100) * 80));
		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(1);

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGuthaben() == ng);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 7: Der Spieler ist bereits zwei Runden auf dem Gefaengnis-Feld und
	 * würfelt.
	 */
	@Test
	public void testGefaengnis7() {
		System.err.println("TEST7:");
		System.err.println("SIE MUESSEN 2x JA EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);

		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(1);
		int gc = s1.getGefaengnisCounter();

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGefaengnisCounter() != gc);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 8: Der Spieler ist bereits drei Runden auf dem Gefängnis-Feld und
	 * Spieler will im Gefängnis bleiben.
	 */
	@Test
	public void testGefaengnis8() {
		System.err.println("TEST8:");
		System.err.println("SIE MUESSEN JA EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);

		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(0);

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getGefaengnisCounter() == 0);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

	/**
	 * Test 9: Der Spieler ist bereits drei Runden auf dem Gefängnis-Feld und
	 * Spieler will nicht mehr im Gefängnis bleiben.
	 */
	@Test
	public void testGefaengnis9() {
		System.err.println("TEST8:");
		System.err.println("SIE MUESSEN JA EINGEBEN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);

		gf = (Gefaengnis) spiel.getSpielbrett().getSpielfeld()[10];
		s1.setPosition(gf.getPosition() - 1);
		s1.setGefaengnisCounter(0);

		spiel.aktionGefaengnis(s1);
		assertTrue(s1.getPosition() != 10);
		// ----------------------------------------------------------
		spiel = null;
		s1 = null;
		gf = null;
	}

}
