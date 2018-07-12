package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Besitzrechtkarten;
import monopoly.FarbEnum;
import monopoly.Haus;
import monopoly.Hotel;
import monopoly.Spiel;
import monopoly.Spieler;
import monopoly.Strasse;

public class StrasseJUnit {

	Spiel spiel = null;

	Spieler s1 = null;
	Spieler s2 = null;

	Besitzrechtkarten bs1 = null;

	// Tests fuer Aktion-Strasse

	Strasse st1 = null;

	// Test 1 : Der Spieler ist Besitzer der Strasse.
	// Erwartetes Ergebnis: Besitzerbaudialog kommt.

	@Test
	public void testStrasse1() {
		System.err.println("TEST 1:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ag = s1.getGuthaben();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[1];
		s1.setPosition(st1.getPosition());

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s1);
		assertTrue(s1.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 2 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// keinen anderen Besitzer. Ausserdem hat der Spieler nicht mehr genug Geld
	// um die Strasse zu kaufen.

	@Test
	public void testStrasse2() {
		System.err.println("TEST 2:");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s1.setGuthaben(0);
		double ag = s1.getGuthaben();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[1];
		s1.setPosition(st1.getPosition());

		spiel.aktionStrasse(s1);
		assertTrue(s1.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 3 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// keinen anderen Besitzer. Ausserdem hat der Besiter Geld um die Strasse zu
	// kaufen aber kauft nicht.

	@Test
	public void testStrasse3() {
		System.err.println("TEST 3:");
		System.err.println("GEBEN SIE NEIN EIN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ag = s1.getGuthaben();

		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[1];
		s1.setPosition(st1.getPosition());

		spiel.aktionStrasse(s1);
		assertTrue(s1.getGuthaben() == ag);
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 4 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// keinen anderen Besitzer. Ausserdem hat der Besiter Geld um die Strasse zu
	// kaufen und kauft letztendlich.

	@Test
	public void testStrasse4() {
		System.err.println("TEST 4:");
		System.err.println("GEBEN SIE JA EIN");
		spiel = new Spiel();
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		double ag = s1.getGuthaben();

		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[1];
		s1.setPosition(st1.getPosition());

		spiel.aktionStrasse(s1);
		assertTrue(s1.getGuthaben() == (ag - st1.getKaufpreis()));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 5 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler Geld um die Miete zu
	// zahlen.(Grundstück)

	@Test
	public void testStrasse5() {
		spiel = new Spiel();
		System.err.println("TEST 5:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();

		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag - (st1.getKaufpreis() / 10)));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 6 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler Geld um die Miete zu
	// zahlen.(1 Haus)

	@Test
	public void testStrasse6() {
		spiel = new Spiel();
		System.err.println("TEST 6:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();
		Haus h = new Haus();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());
		st1.setHaus1(h);

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag - ((st1.getKaufpreis() / 10) * 2)));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 7 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler Geld um die Miete zu
	// zahlen.(2 Haus)

	@Test
	public void testStrasse7() {
		spiel = new Spiel();
		System.err.println("TEST 7:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();
		Haus h = new Haus();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());
		st1.setHaus1(h);
		st1.setHaus2(h);

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag - ((st1.getKaufpreis() / 10) * 4)));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}
	// Test 8 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler Geld um die Miete zu
	// zahlen.(1 Hotel)

	@Test
	public void testStrasse8() {
		spiel = new Spiel();
		System.err.println("TEST 8:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		double ag = s2.getGuthaben();
		Hotel h = new Hotel();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());
		st1.setHotel(h);

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag - ((st1.getKaufpreis() / 10) * 8)));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 9 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler Kein Geld um die Miete
	// zu zahlen.(Grundstück)

	@Test
	public void testStrasse9() {
		spiel = new Spiel();
		System.err.println("TEST 9:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 10 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler kein Geld um die Miete
	// zu zahlen.(1 Haus)

	@Test
	public void testStrassee10() {
		spiel = new Spiel();
		System.err.println("TEST 10:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		Haus h = new Haus();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());
		st1.setHaus1(h);

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

	// Test 11 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler kein Geld um die Miete
	// zu zahlen.(2 Haus)

	@Test
	public void testStrassee11() {
		spiel = new Spiel();
		System.err.println("TEST 11:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		Haus h = new Haus();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());
		st1.setHaus1(h);
		st1.setHaus2(h);

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}
	// Test 12 : Der Spieler ist nicht Besitzer der Strasse und die Strasse hat
	// einen anderen Besitzer. Ausserdem hat der Spieler kein Geld um die Miete
	// zu zahlen.(1 Hotel)

	@Test
	public void testStrassee12() {
		spiel = new Spiel();
		System.err.println("TEST 12:");
		s1 = new Spieler(1, FarbEnum.BLAU, null);
		spiel.getSpieler().add(s1);
		s2 = new Spieler(2, FarbEnum.ROT, null);
		spiel.getSpieler().add(s2);
		s2.setGuthaben(0);
		double ag = s2.getGuthaben();
		Hotel h = new Hotel();
		st1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		s2.setPosition(st1.getPosition());
		st1.setHotel(h);

		bs1 = new Besitzrechtkarten(st1, s1);
		s1.getBesitzrechtkarten().add(bs1);

		spiel.aktionStrasse(s2);
		assertTrue(s2.getGuthaben() == (ag));
		// ----------------------------------------------
		spiel = null;
		s1 = null;
		s2 = null;
		st1 = null;
		bs1 = null;
	}

}
