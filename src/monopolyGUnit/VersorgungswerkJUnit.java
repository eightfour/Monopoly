package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Besitzrechtkarten;
import monopoly.FarbEnum;
import monopoly.Spiel;
import monopoly.Spieler;
import monopoly.Versorgungswerk;

/**
 * 
 * Tests fuer Aktion-Versorgungswerk
 *
 */
public class VersorgungswerkJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = null;
	Spieler s1 = null;
	Spieler s2 = null;
	Besitzrechtkarten bs1 = null;
	Besitzrechtkarten bs2 = null;
	Versorgungswerk vw1 = null;
	Versorgungswerk vw2 = null;
	int wurf[] = { 2, 3 };

	/**
	 * Test 1: Der Spieler ist Besitzer des Versorgungswerkes
	 */
	@Test
	public void testVersorgungswerk1() {
		System.err.println("TEST1:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12]; // 12
																			// oder
																			// 28
		s1.setPosition(vw1.getPosition());
		bs1 = new Besitzrechtkarten(vw1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		spiel.aktionVersorgungswerk(s1);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		bs1 = null;
	}

	/**
	 * Test 2 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat keinen anderen Besitzer. Ausserdem hat der Besiter
	 * nicht mehr genug Geld um das Versorgungswerk zu kaufen.
	 */
	@Test
	public void testVersorgungswerk2() {
		System.err.println("TEST2:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		s1.setGuthaben(1);
		spiel.getSpieler().add(s1);
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12]; // 12
																			// oder
																			// 28
		s1.setPosition(vw1.getPosition());
		spiel.aktionVersorgungswerk(s1);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
	}

	/**
	 * Test 3 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat keinen anderen Besitzer. Ausserdem hat der Besiter
	 * Geld um das Versorgungswerk zu kaufen und kauft es.
	 */
	@Test
	public void testVersorgungswerk3() {
		System.err.println("TEST3:");
		System.err.println("GEBEN SIE JA EIN.");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12]; // 12
																			// oder
																			// 28
		s1.setPosition(vw1.getPosition());
		bs1 = new Besitzrechtkarten(vw1, s1);
		spiel.aktionVersorgungswerk(s1);
		assertTrue(bs1.equals(s1.getBesitzrechtkarten().get(0)));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		bs1 = null;
	}

	/**
	 * Test 4 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat keinen anderen Besitzer. Ausserdem hat der Besiter
	 * Geld um das Versorgungswerk zu kaufen und kauft es nicht.
	 */
	@Test
	public void testVersorgungswerk4() {
		System.err.println("TEST4:");
		System.err.println("GEBEN SIE NEIN EIN.");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12]; // 12
																			// oder
																			// 28
		s1.setPosition(vw1.getPosition());
		bs1 = new Besitzrechtkarten(vw1, s1);
		spiel.aktionVersorgungswerk(s1);
		assertTrue(s1.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		bs1 = null;
	}

	/**
	 * Test 5 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat einen anderen Besitzer, dieser Besitzer besitzt nur
	 * dieses Versorgungswerk. Ausserdem hat der Spieler Geld um die Miete zu
	 * zahlen.
	 */
	@Test
	public void testVersorgungswerk5() {
		System.err.println("TEST5:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s1);
		spiel.getSpieler().add(s2);
		double g1 = s1.getGuthaben();
		double g2 = s2.getGuthaben();
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12]; // 12
																			// oder
																			// 28
		s1.setPosition(vw1.getPosition());
		bs2 = new Besitzrechtkarten(vw1, s2);
		s2.getBesitzrechtkarten().add(bs2);
		spiel.setWurf(wurf);
		spiel.aktionVersorgungswerk(s1);
		double unterschied = g1 - s1.getGuthaben();
		assertTrue(s2.getGuthaben() == unterschied + g2);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		bs2 = null;
		s2 = null;
	}

	/**
	 * Test 6 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat einen anderen Besitzer, dieser Besitzer besitzt nur
	 * dieses Versorgungswerk. Ausserdem hat der Besiter kein Geld um die Miete
	 * zu zahlen.
	 */
	@Test
	public void testVersorgungswerk6() {
		System.err.println("TEST6:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s1);
		spiel.getSpieler().add(s2);
		s1.setGuthaben(1);
		double g1 = s1.getGuthaben();
		double g2 = s2.getGuthaben();
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12]; // 12
																			// oder
																			// 28
		s1.setPosition(vw1.getPosition());
		bs2 = new Besitzrechtkarten(vw1, s2);
		s2.getBesitzrechtkarten().add(bs2);
		spiel.setWurf(wurf);
		spiel.aktionVersorgungswerk(s1);
		assertTrue((spiel.getSpieler().size() == 1) & (spiel.getSpieler().get(0).getID() == s2.getID()));
		assertTrue(s2.getGuthaben() == g2 + g1);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		bs2 = null;
		s2 = null;
	}

	/**
	 * Test 7 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat einen anderen Besitzer, dieser Besitzer besitzt beide
	 * Versorgungswerke. Ausserdem hat der Besiter Geld um die Miete zu zahlen.
	 */
	@Test
	public void testVersorgungswerk7() {
		System.err.println("TEST7:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s1);
		spiel.getSpieler().add(s2);
		double g1 = s1.getGuthaben();
		double g2 = s2.getGuthaben();
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12];
		vw2 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[28];
		s1.setPosition(vw1.getPosition());
		bs2 = new Besitzrechtkarten(vw1, s2);
		bs1 = new Besitzrechtkarten(vw2, s2);
		s2.getBesitzrechtkarten().add(bs2);
		s2.getBesitzrechtkarten().add(bs1);
		spiel.setWurf(wurf);
		spiel.aktionVersorgungswerk(s1);
		double unterschied = g1 - s1.getGuthaben();
		assertTrue(s2.getGuthaben() == unterschied + g2);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		vw2 = null;
		bs2 = null;
		bs1 = null;
		s2 = null;
	}

	/**
	 * Test 8 : Der Spieler ist nicht Besitzer des Versorgungswerkes und das
	 * Versorgungswerk hat einen anderen Besitzer, dieser Besitzer besitzt beide
	 * Versorgungswerke. Ausserdem hat der Besiter kein Geld um die Miete zu
	 * zahlen.
	 */
	@Test
	public void testVersorgungswerk8() {
		System.err.println("TEST6:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s1);
		spiel.getSpieler().add(s2);
		s1.setGuthaben(1);
		double g1 = s1.getGuthaben();
		double g2 = s2.getGuthaben();
		vw1 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[12];
		vw2 = (Versorgungswerk) spiel.getSpielbrett().getSpielfeld()[28];
		s1.setPosition(vw1.getPosition());
		bs2 = new Besitzrechtkarten(vw1, s2);
		bs1 = new Besitzrechtkarten(vw2, s2);
		s2.getBesitzrechtkarten().add(bs2);
		s2.getBesitzrechtkarten().add(bs1);
		spiel.setWurf(wurf);
		spiel.aktionVersorgungswerk(s1);
		assertTrue((spiel.getSpieler().size() == 1) & (spiel.getSpieler().get(0).getID() == s2.getID()));
		assertTrue(s2.getGuthaben() == g2 + g1);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		vw1 = null;
		vw2 = null;
		bs2 = null;
		bs1 = null;
		s2 = null;
	}
}
