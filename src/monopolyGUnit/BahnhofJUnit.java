package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Bahnhof;
import monopoly.Besitzrechtkarten;
import monopoly.FarbEnum;
import monopoly.Spiel;
import monopoly.Spieler;

public class BahnhofJUnit {

	Spiel spiel = null;

	Spieler s1 = null;
	Spieler s2 = null;

	Besitzrechtkarten bs1 = null;
	Besitzrechtkarten bs2 = null;
	Besitzrechtkarten bs3 = null;
	Besitzrechtkarten bs4 = null;
	Bahnhof bhf1 = null;
	Bahnhof bhf2 = null;
	Bahnhof bhf3 = null;
	Bahnhof bhf4 = null;

	// TEST 1: Spieler ist Besitzer vom Bahnhof

	@Test
	public void testBahnhof1() {
		System.err.println("TEST 1:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ag = s1.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		s1.setPosition(bhf1.getPosition());

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionBahnhof(s1);
		assertTrue(s1.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bs1 = null;
	}

	// TEST 2: Bahnhof gehört keinem, und Spieler hat kein Geld zum Kaufen

	@Test
	public void testBahnhof2() {
		System.err.println("TEST 2:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s1.setGuthaben(0);
		double ag = s1.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		s1.setPosition(bhf1.getPosition());

		spiel.aktionBahnhof(s1);
		assertTrue(s1.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bs1 = null;
	}

	// TEST 3: Bahnhof gehört keinem, und Spieler hat Geld zum Kaufen und kauft

	@Test
	public void testBahnhof3() {
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		System.err.println("TEST 3:");
		System.err.println("GEBEN SIE JA EIN.");
		double ag = s1.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		s1.setPosition(bhf1.getPosition());

		spiel.aktionBahnhof(s1);
		assertTrue(s1.getGuthaben() == (ag - Bahnhof.getKaufpreis()));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bs1 = null;
	}

	// TEST 4: Bahnhof gehört keinem, und Spieler hat Geld zum Kaufen, kauft
	// aber nicht

	@Test
	public void testBahnhof4() {
		System.err.println("TEST 4:");
		System.err.println("GEBEN SIE NEIN EIN.");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ag = s1.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		s1.setPosition(bhf1.getPosition());

		spiel.aktionBahnhof(s1);
		assertTrue(s1.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bs1 = null;
	}
	// TEST 5: Besitzer hat nur einen Bahnhof. Spieler aber kein Geld zum zahlen

	@Test
	public void testBahnhof5() {
		System.err.println("TEST 5:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		s2.setPosition(bhf1.getPosition());
		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bs1 = null;
	}
	// TEST 6: Besitzer hat nur einen Bahnhof. Spieler hat Geld zum zahlen

	@Test
	public void testBahnhof6() {
		System.err.println("TEST 6:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		s2.setPosition(bhf1.getPosition());
		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == (ag - 25));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bs1 = null;
	}
	// TEST 7: Besitzer hat zwei Bahnhöfe. Spieler aber kein Geld zum zahlen

	@Test
	public void testBahnhof7() {
		System.err.println("TEST 7:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		bhf2 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[15];

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		bs2 = new Besitzrechtkarten(bhf2, s1);
		s1.getBesitzrechtkarten().add(bs2);

		s2.setPosition(bhf1.getPosition());
		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bhf2 = null;
		bs1 = null;
		bs2 = null;
	}
	// TEST 8: Besitzer hat zwei Bahnhöfe. Spieler hat Geld zum zahlen

	@Test
	public void testBahnhof8() {
		System.err.println("TEST 8:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		bhf2 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[15];

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		bs2 = new Besitzrechtkarten(bhf2, s1);
		s1.getBesitzrechtkarten().add(bs2);

		s2.setPosition(bhf1.getPosition());
		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == (ag - 50));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bhf2 = null;
		bs1 = null;
		bs2 = null;
	}

	// TEST 9: Besitzer hat drei Bahnhöfe. Spieler aber kein Geld zum zahlen

	@Test
	public void testBahnhof9() {
		System.err.println("TEST 9:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		bhf2 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[15];
		bhf3 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[25];

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		bs2 = new Besitzrechtkarten(bhf2, s1);
		s1.getBesitzrechtkarten().add(bs2);
		bs3 = new Besitzrechtkarten(bhf3, s1);
		s1.getBesitzrechtkarten().add(bs3);

		s2.setPosition(bhf1.getPosition());
		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bhf2 = null;
		bs1 = null;
		bs2 = null;
	}
	// TEST 10: Besitzer hat drei Bahnhöfe. Spieler hat Geld zum zahlen

	@Test
	public void testBahnhofe10() {
		System.err.println("TEST 10:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		bhf2 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[15];
		bhf3 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[25];

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		bs2 = new Besitzrechtkarten(bhf2, s1);
		s1.getBesitzrechtkarten().add(bs2);
		bs3 = new Besitzrechtkarten(bhf3, s1);
		s1.getBesitzrechtkarten().add(bs3);

		s2.setPosition(bhf1.getPosition());
		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == (ag - 100));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bhf2 = null;
		bs1 = null;
		bs2 = null;
	}
	// TEST 11: Besitzer hat vier Bahnhöfe. Spieler aber kein Geld zum zahlen

	@Test
	public void testBahnhofe11() {
		System.err.println("TEST 11:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		bhf2 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[15];
		bhf3 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[25];
		bhf4 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[35];

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		bs2 = new Besitzrechtkarten(bhf2, s1);
		s1.getBesitzrechtkarten().add(bs2);
		bs3 = new Besitzrechtkarten(bhf3, s1);
		s1.getBesitzrechtkarten().add(bs3);
		bs4 = new Besitzrechtkarten(bhf4, s1);
		s1.getBesitzrechtkarten().add(bs4);

		s2.setPosition(bhf1.getPosition());
		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bhf2 = null;
		bs1 = null;
		bs2 = null;
	}
	// TEST 12: Besitzer hat vier Bahnhöfe. Spieler hat Geld zum zahlen

	@Test
	public void testBahnhofe12() {
		System.err.println("TEST 12:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();

		bhf1 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[5];
		bhf2 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[15];
		bhf3 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[25];
		bhf4 = (Bahnhof) spiel.getSpielbrett().getSpielfeld()[35];

		bs1 = new Besitzrechtkarten(bhf1, s1);
		s1.getBesitzrechtkarten().add(bs1);
		bs2 = new Besitzrechtkarten(bhf2, s1);
		s1.getBesitzrechtkarten().add(bs2);
		bs3 = new Besitzrechtkarten(bhf3, s1);
		s1.getBesitzrechtkarten().add(bs3);
		bs4 = new Besitzrechtkarten(bhf4, s1);
		s1.getBesitzrechtkarten().add(bs4);

		s2.setPosition(bhf1.getPosition());
		spiel.aktionBahnhof(s2);
		assertTrue(s2.getGuthaben() == (ag - 200));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		bhf1 = null;
		bhf2 = null;
		bs1 = null;
		bs2 = null;
	}

}
