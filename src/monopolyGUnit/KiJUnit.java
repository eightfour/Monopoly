package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Besitzrechtkarten;
import monopoly.FarbEnum;
import monopoly.Haus;
import monopoly.Hotel;
import monopoly.Ki;
import monopoly.KiEasy;
import monopoly.Spiel;
import monopoly.Strasse;

/**
 * Tests fuer Ki-Verhalten.
 */
public class KiJUnit {

	/**
	 * Benoetigte Komponenten
	 */
	Spiel spiel = new Spiel();
	Ki ki = null;
	Ki ki2 = null;
	Besitzrechtkarten b = null;
	Besitzrechtkarten b2 = null;
	Besitzrechtkarten b3 = null;
	Besitzrechtkarten b4 = null;
	int wurf[] = { 2, 3, 5 };

	/**
	 * In folgenden Tests fuer Das Verhalten auf einem Versorgungswerkfeld.
	 */

	/**
	 * Die Ki besitzt einen vollstaendigen 3er Strassenabschnitt und hat noch
	 * keine haeuser.
	 */
	@Test
	public void testBauenKI01() {
		System.out.println("Test33");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[6], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[8], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[9], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.getBesitzrechtkarten().add(b3);
		ki.setGuthaben(1000000);
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();	
		spiel.kontrolleAbgebenKI();		
		spiel.kontrolleAbgebenKI();		
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();		
		assertTrue((((Strasse) (ki.getBesitzrechtkarten().get(0).getSpielfeld())).getHotel() != null)
				&& (((Strasse) (ki.getBesitzrechtkarten().get(1).getSpielfeld())).getHotel() != null)
				&& (((Strasse) (ki.getBesitzrechtkarten().get(2).getSpielfeld())).getHotel() != null));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
		b3 = null;
	}

	/**
	 * Die Ki besitzt einen vollstaendigen 2er Strassenabschnitt und hat noch
	 * keine haeuser.
	 */
	@Test
	public void testBauenKI02() {
		System.out.println("Test34");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[1], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[3], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.setGuthaben(1000000);
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();	
		spiel.kontrolleAbgebenKI();	
		spiel.kontrolleAbgebenKI();
		spiel.kontrolleAbgebenKI();
		assertTrue((((Strasse) (ki.getBesitzrechtkarten().get(0).getSpielfeld())).getHotel() != null)
				&& (((Strasse) (ki.getBesitzrechtkarten().get(1).getSpielfeld())).getHotel() != null));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
		b3 = null;
	}

	/**
	 * In folgenden Tests fuer Das Verhalten auf einem FreiParkenFeld.
	 */
	@Test
	public void testFreiParkenFeldKiEasy() {
		System.out.println("Test30");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(20);
		ki.setGuthaben(499);
		spiel.aktionFreiParken(ki);
		assertTrue(ki.getPosition() == 20);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * In folgenden Tests fuer Das Verhalten auf einem GeheInsGefaengnisFeld.
	 */
	@Test
	public void testGeheInsGefaengnisKiEasy() {
		System.out.println("Test29");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(30);
		ki.setGuthaben(499);
		spiel.aktionGeheInsGefaengnis(ki);
		assertTrue(ki.getPosition() == 10);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Die Ki besitzt keinen Bahnhof und hat mehr als 700. Erwartetes Ergebnis:
	 * Die KI kauft den Bahnhof.
	 */
	@Test
	public void testKiBahnhof1() {
		System.out.println("Test12");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		double g1 = ki.getGuthaben();
		spiel.aktionBahnhof(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		assertTrue(ki.getGuthaben() == (g1 - 200));
		assertTrue(ki.getBesitzrechtkarten().get(0).equals(b));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
	}

	/**
	 * Die Ki besitzt keinen Bahnhof und hat weniger als 700 Erwartetes
	 * Ergebnis: Die KI kauft den Bahnhof nicht.
	 */
	@Test
	public void testKiBahnhof2() {
		System.out.println("Test13");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(699);
		double g1 = ki.getGuthaben();
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == g1);
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
	}

	/**
	 * Die Ki besitzt einen Bahnhof und hat mehr als 600 und weniger als 700
	 * Erwartetes Ergebnis: Die KI kauft den Bahnhof.
	 */
	@Test
	public void testKiBahnhof3() {
		System.out.println("Test14");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(699);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		ki.getBesitzrechtkarten().add(b);
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == (g1 - 200));
		assertTrue(ki.getBesitzrechtkarten().get(1).equals(b2));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
	}

	/**
	 * Die Ki besitzt einen Bahnhof und hat weniger als 600 Erwartetes Ergebnis:
	 * Die KI kauft den Bahnhof nicht.
	 */
	@Test
	public void testKiBahnhof4() {
		System.out.println("Test15");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(599);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		ki.getBesitzrechtkarten().add(b);
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == g1);
		assertTrue(ki.getBesitzrechtkarten().size() == 1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
	}

	/**
	 * Die Ki besitzt zwei Bahnhoefe und hat mehr als 500 und weniger als 600
	 * Erwartetes Ergebnis: Die KI kauft den Bahnhof.
	 */
	@Test
	public void testKiBahnhof5() {
		System.out.println("Test16");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(599);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[25], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == (g1 - 200));
		assertTrue(ki.getBesitzrechtkarten().get(2).equals(b3));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
		b3 = null;
	}

	/**
	 * Die Ki besitzt zwei Bahnhoefe und hat weniger als 500 Erwartetes
	 * Ergebnis: Die KI kauft den Bahnhof nicht.
	 */
	@Test
	public void testKiBahnhof6() {
		System.out.println("Test17");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(499);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[25], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == g1);
		assertTrue(ki.getBesitzrechtkarten().size() == 2);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
		b3 = null;
	}

	/**
	 * Die Ki besitzt drei Bahnhoefe und hat mehr als 400 und weniger als 500
	 * Erwartetes Ergebnis: Die KI kauft den Bahnhof.
	 */
	@Test
	public void testKiBahnhof7() {
		System.out.println("Test18");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(499);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[25], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[35], ki);
		b4 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.getBesitzrechtkarten().add(b3);
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == (g1 - 200));
		assertTrue(ki.getBesitzrechtkarten().get(3).equals(b4));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
		b3 = null;
		b4 = null;
	}

	/**
	 * In folgenden Tests wird das Verhalten der Ki getestet, wenn sie auf ein
	 * Bahnhofsfeld kommt.
	 */

	/**
	 * Die Ki besitzt drei Bahnhoefe und hat weniger als 400 Erwartetes
	 * Ergebnis: Die KI kauft den Bahnhof nicht.
	 */
	@Test
	public void testKiBahnhof8() {
		System.out.println("Test19");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(5);
		ki.setGuthaben(399);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[25], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[35], ki);
		b4 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[ki.getPosition()], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.getBesitzrechtkarten().add(b3);
		spiel.aktionBahnhof(ki);
		assertTrue(ki.getGuthaben() == g1);
		assertTrue(ki.getBesitzrechtkarten().size() == 3);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
		b3 = null;
		b4 = null;
	}

	/**
	 * In folgendem ein Test für das Verhalten auf einem Gefängnisfeld
	 * Erwartetes Ergebnis die KI wird sich nicht freikaufen sondern würfeln
	 */
	@Test
	public void testKiGefaengnis() {
		System.out.println("Test28");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(10);
		ki.setGuthaben(399);

		spiel.aktionGefaengnis(ki);
		assertTrue(ki.getGuthaben() >= 300);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;

	}

	/**
	 * Die Ki kommt auf auf die badstrasse, besitzt die turmstrasse nicht. Und
	 * hat weniger als 860. Erwartetes Ergebnis: Die KI kauft die Strasse nicht.
	 */
	@Test
	public void testKiStrasse1() {
		System.out.println("Test20");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(1);
		ki.setGuthaben(859);
		double g1 = ki.getGuthaben();
		spiel.aktionStrasse(ki);
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		assertTrue(ki.getGuthaben() == g1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
	}

	/**
	 * Die Ki kommt auf auf die badstrasse, besitzt die turmstrasse nicht. Und
	 * hat mehr als 860. Erwartetes Ergebnis: Die KI kauft die Strasse.
	 */
	@Test
	public void testKiStrasse2() {
		System.out.println("Test21");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(1);
		ki.setGuthaben(860);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
	}

	/**
	 * Die Ki kommt auf auf die badstrasse, besitzt die turmstrasse. Und hat
	 * mehr als 460 und weniger als 500 Erwartetes Ergebnis: Die KI kauft die
	 * Strasse.
	 */
	@Test
	public void testKiStrasse3() {
		System.out.println("Test22");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(1);
		ki.setGuthaben(460);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[3], ki);
		ki.getBesitzrechtkarten().add(b);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 2);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * Die Ki kommt auf auf die badstrasse, besitzt die turmstrasse. Und hat
	 * weniger als 460. Erwartetes Ergebnis: Die KI kauft die Strasse nicht
	 */
	@Test
	public void testKiStrasse4() {
		System.out.println("Test23");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(1);
		ki.setGuthaben(459);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[3], ki);
		ki.getBesitzrechtkarten().add(b);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * Die Ki kommt auf auf die chausseestrasse, besitzt die elissensstrasse.
	 * Und hat mehr als 700. Erwartetes Ergebnis: Die KI kauft die Strasse.
	 */
	@Test
	public void testKiStrasse5() {
		System.out.println("Test24");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(6);
		ki.setGuthaben(700);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[8], ki);
		ki.getBesitzrechtkarten().add(b);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 2);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * Die Ki kommt auf auf die chausseestrasse, besitzt die elissensstrasse.
	 * Und hat weniger als 700. Erwartetes Ergebnis: Die KI kauft die Strasse
	 * nicht.
	 */
	@Test
	public void testKiStrasse6() {
		System.out.println("Test25");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(6);
		ki.setGuthaben(699);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[8], ki);
		ki.getBesitzrechtkarten().add(b);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * In folgenden Tests fuer das Verhalten auf einem Strassenfeld.
	 */

	/**
	 * Die Ki kommt auf auf die chausseestrasse, besitzt die elissensstrasse und
	 * die poststrasse. Und hat mehr als 500. Erwartetes Ergebnis: Die KI kauft
	 * die Strasse.
	 */
	@Test
	public void testKiStrasse7() {
		System.out.println("Test26");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(6);
		ki.setGuthaben(500);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[8], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[9], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 3);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Die Ki kommt auf auf die chausseestrasse, besitzt die elissensstrasse und
	 * die poststrasse. Und hat weniger als 500. Erwartetes Ergebnis: Die KI
	 * kauft die Strasse nicht.
	 */
	@Test
	public void testKiStrasse8() {
		System.out.println("Test27");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(6);
		ki.setGuthaben(499);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[8], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[9], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		spiel.aktionStrasse(ki);
		System.out.println(ki.getBesitzrechtkarten());
		assertTrue(ki.getBesitzrechtkarten().size() == 2);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Die KI steht auf dem Elektizitaetswerk hat mehr als 500 und besitzt das
	 * Wasserwerk nicht. Erwartetes Ergebnis: Die Ki kauft das Versorgungswerk.
	 */
	@Test
	public void testKiVersorgungswerk1() {
		System.out.println("Test01");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(12);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		assertTrue(b.equals(ki.getBesitzrechtkarten().get(0)));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * Die Ki muss einfache Miete zahlen
	 */
	@Test
	public void testKiVersorgungswerk10() {
		System.out.println("Test10");
		int[] wurf = { 2, 3, 5 };
		spiel.setWurf(wurf);
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		ki2 = new KiEasy(2, FarbEnum.ROT, null, spiel);
		spiel.getSpieler().add(ki);
		spiel.getSpieler().add(ki2);
		ki.setPosition(12);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki2);
		ki2.getBesitzrechtkarten().add(b);
		int vier = 4;
		spiel.aktionVersorgungswerk(ki);
		assertTrue(ki.getGuthaben() == (g1 - (vier * wurf[2])));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
	}

	/**
	 * Die Ki muss doppelte Miete zahlen
	 */
	@Test
	public void testKiVersorgungswerk11() {
		System.out.println("Test11");
		int[] wurf = { 2, 3, 5 };
		spiel.setWurf(wurf);
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		ki2 = new KiEasy(2, FarbEnum.ROT, null, spiel);
		spiel.getSpieler().add(ki);
		spiel.getSpieler().add(ki2);
		ki.setPosition(12);
		double g1 = ki.getGuthaben();
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki2);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki2);
		ki2.getBesitzrechtkarten().add(b);
		ki2.getBesitzrechtkarten().add(b2);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		int zehn = 10;
		assertTrue(ki.getGuthaben() == (g1 - (zehn * wurf[2])));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		ki2 = null;
		b2 = null;
	}

	/**
	 * Die KI steht auf dem Elektizitaetswerk hat weniger als 500 und besitzt
	 * das Wasserwerk nicht. Erwartetes Ergebnis: Die Ki kauft das
	 * Versorgungswerk nicht.
	 */
	@Test
	public void testKiVersorgungswerk2() {
		System.out.println("Test02");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(12);
		ki.setGuthaben(499);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * Die KI steht auf dem Elektizitaetswerk hat mehr als 300 und weniger als
	 * 500 und besitzt das Wasserwerk. Erwartetes Ergebnis: Die Ki kauft das
	 * Versorgungswerk.
	 */
	@Test
	public void testKiVersorgungswerk3() {
		System.out.println("Test03");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(12);
		ki.setGuthaben(499);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		ki.getBesitzrechtkarten().add(b2);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		assertTrue(ki.getBesitzrechtkarten().get(1).equals(b));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Die KI steht auf dem Elektizitaetswerk weniger als 300 und und besitzt
	 * das Wasserwerk. Erwartetes Ergebnis: Die Ki kauft das Versorgungswerk
	 * nicht.
	 */
	@Test
	public void testKiVersorgungswerk4() {
		System.out.println("Test04");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(12);
		ki.setGuthaben(299);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		ki.getBesitzrechtkarten().add(b2);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		assertTrue(ki.getBesitzrechtkarten().size() == 1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Analoge Tests, die Ki steht auf dem Wasserwerk.
	 */

	@Test
	public void testKiVersorgungswerk5() {
		System.out.println("Test05");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(28);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		assertTrue(b.equals(ki.getBesitzrechtkarten().get(0)));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	@Test
	public void testKiVersorgungswerk6() {
		System.out.println("Test06");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(28);
		ki.setGuthaben(499);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	@Test
	public void testKiVersorgungswerk7() {
		System.out.println("Test07");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(28);
		ki.setGuthaben(499);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		ki.getBesitzrechtkarten().add(b2);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		assertTrue(ki.getBesitzrechtkarten().get(1).equals(b));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	@Test
	public void testKiVersorgungswerk8() {
		System.out.println("Test08");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(28);
		ki.setGuthaben(299);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		ki.getBesitzrechtkarten().add(b2);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		assertTrue(ki.getBesitzrechtkarten().size() == 1);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Die Ki besitzt nicht genug Geld um ein Versorgungswerk zu kaufen. Ohne
	 * Beschraenktheit der Allgemeinheit wird die Ki auf das Elektizitaetswerk
	 * gesetzt.
	 */
	@Test
	public void testKiVersorgungswerk9() {
		System.out.println("Test09");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setPosition(12);
		ki.setGuthaben(1);
		// spiel.aktionVersorgungswerk(ki, wurf);
		spiel.aktionVersorgungswerk(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
	}

	/**
	 * In folgenden Tests wird das verhalten einer Ki beim bauen getestet.
	 */

	/**
	 * In folgenden Tests fuer Das Verhalten auf einem Losfeld.
	 */
	@Test
	public void testLosFeldKiEasy() {
		System.out.println("Test31");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setGuthaben(499);
		ki.setPosition(0);
		spiel.aktionLos(ki);

		assertTrue(ki.getGuthaben() == 999);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * In folgenden Tests fuer Das Verhalten auf einem Steuerfeld. Erwartetes
	 * Ergebnis: der ki wird 30% ihres Vermögens abgezogen
	 * 
	 */
	@Test
	public void testSteuerfeldKiEasy() {
		System.out.println("Test32");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setGuthaben(100);
		ki.setPosition(4);
		spiel.aktionSteuer(ki);
		assertTrue(ki.getGuthaben() == 70);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * In folgenden Tests wird das verhalten einer Ki beim verkaufen getestet.
	 */

	/**
	 * Die Ki besitzt zwei Versorgungswerke, und einen Bahnhof. Erwartetes
	 * Ergebnis: verkauft beide Versorgungswerke und behaelt den Bahnhof
	 */
	@Test
	public void testVerkaufenKI01() {
		System.out.println("Test34");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[28], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[5], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.getBesitzrechtkarten().add(b3);
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		assertTrue(ki.getBesitzrechtkarten().get(0).equals(b3));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
		b3 = null;
		;
	}

	/**
	 * Die Ki besitzt ein Versorgungswerk, und einen Bahnhof. Erwartetes
	 * Ergebnis: verkauft ein Versorgungswerke und behaelt den Bahnhof
	 */
	@Test
	public void testVerkaufenKI02() {
		System.out.println("Test35");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[5], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b3);
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		assertTrue(ki.getBesitzrechtkarten().get(0).equals(b3));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
		b3 = null;
		;
	}

	/**
	 * Die Ki besitzt zwei bahnhoefe und eine Strasse Erwartets Ergebnis:
	 * Verkauft Beide Bahnhoefe und behaelt die Strasse.
	 */
	@Test
	public void testVerkaufenKI03() {
		System.out.println("Test36");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[5], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[15], ki);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[8], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.getBesitzrechtkarten().add(b3);
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		assertTrue(ki.getBesitzrechtkarten().get(0).equals(b3));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
		b3 = null;
		;
	}

	/**
	 * Die Ki besitzt zwei Strassen Erwartets Ergebnis: Verkauft die eine
	 * Strasse.
	 */
	@Test
	public void testVerkaufenKI04() {
		System.out.println("Test37");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[16], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[21], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		assertTrue(ki.getBesitzrechtkarten().get(0).equals(b2));
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
	}

	/**
	 * Die Ki besitzt eine Strassen mit hotel Erwartets Ergebnis: Verkauft die
	 * eine Strasse.(simulierte zwei runden)
	 */
	@Test
	public void testVerkaufenKI05() {
		System.out.println("Test38");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[16], ki);
		Strasse s = (Strasse) spiel.getSpielbrett().getSpielfeld()[16];
		s.setHotel(new Hotel());
		ki.getBesitzrechtkarten().add(b);
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		ki.setGuthaben(99);
		spiel.kontrolleAbgebenKI();
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;

	}

	/**
	 * Die Ki besitzt nichts Erwartets Ergebnis: guthaben am ende gleich
	 */
	@Test
	public void testVerkaufenKI06() {
		System.out.println("Test39");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		ki.setGuthaben(99);
		double ag = ki.getGuthaben();
		spiel.kontrolleAbgebenKI();
		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		assertTrue(ki.getGuthaben() == ag);
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;

	}

	/**
	 * Die Ki besitzt mehrere Strasseb und Bahnhöfe. Es werden so lange runden
	 * simuliert bis ki nihcts mehr hat Erwartets Ergebnis: Besitzrechtkarte am
	 * ende leer (lustigerweise hat er wenn er das Hotel bei der Sclossalle
	 * verkauft genug geld um noch ein haus zu bauen) :)
	 */
	@Test
	public void testVerkaufenKI07() {
		System.out.println("Test40");
		ki = new KiEasy(1, FarbEnum.BLAU, null, spiel);
		spiel.getSpieler().add(ki);
		Besitzrechtkarten b5 = null;
		Besitzrechtkarten b6 = null;
		b = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[16], ki);
		b2 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[21], ki);
		ki.getBesitzrechtkarten().add(b);
		ki.getBesitzrechtkarten().add(b2);
		b3 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[37], ki);
		b4 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[39], ki);
		Strasse s = (Strasse) spiel.getSpielbrett().getSpielfeld()[39];
		Strasse s1 = (Strasse) spiel.getSpielbrett().getSpielfeld()[37];
		s1.setHaus2(new Haus());
		s.setHotel(new Hotel());
		ki.getBesitzrechtkarten().add(b3);
		ki.getBesitzrechtkarten().add(b4);
		b5 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[12], ki);
		b6 = new Besitzrechtkarten(spiel.getSpielbrett().getSpielfeld()[5], ki);
		ki.getBesitzrechtkarten().add(b5);
		ki.getBesitzrechtkarten().add(b6);
		ki.setGuthaben(99);
		for (int i = 0; i <= 8; i++) {
			spiel.kontrolleAbgebenKI();
			ki.setGuthaben(99);
			System.out.println(ki);
		}

		assertTrue(ki.getBesitzrechtkarten().isEmpty());
		// ----------------------------------------------------------------------
		spiel = null;
		ki = null;
		b = null;
		b2 = null;
		b3 = null;
		b4 = null;

	}

}
