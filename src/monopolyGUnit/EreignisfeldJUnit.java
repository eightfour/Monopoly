package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Besitzrechtkarten;
import monopoly.Ereigniskarte;
import monopoly.Ereigniskartenmanager;
import monopoly.FarbEnum;
import monopoly.Spiel;
import monopoly.Spieler;
import monopoly.Strasse;

public class EreignisfeldJUnit {

	Spieler spielerKi = new Spieler(1500, FarbEnum.SCHWARZ, null);
	Spiel s1 = new Spiel();
	Spieler spielerO = new Spieler(1500, FarbEnum.SCHWARZ, null);
	Ereigniskarte ereigniskarte = new Ereigniskartenmanager().zieheEreigniskarte();

	@Test
	public void test() {
		@SuppressWarnings("unused")
		boolean pos = false;
		spielerKi.setPosition(0);
		spielerO.setPosition(50);
		System.out.println(ereigniskarte.toString());

		// Gefaengnis
		if (ereigniskarte.getPos() - 1 == 10) {
			s1.aktionGeheInsGefaengnis(spielerKi);
			assertTrue(spielerKi.getPosition() == 10);
		}
		if (ereigniskarte.getPos() < 0 || ereigniskarte.getPos() > 0 || ereigniskarte.getPos() != 10
				|| ereigniskarte.getBetrag() != 0 || ereigniskarte.getHaus() != 0 || ereigniskarte.getHotel() != 0
				|| ereigniskarte.getZahlenSpieler() != 0) {

			// Betraege zahlen oder bekommen
			if (ereigniskarte.getBetrag() != 0) {
				double zahl = spielerKi.getGuthaben();
				if (ereigniskarte.getPos() == 0) {
					spielerKi.setGuthaben((spielerKi.getGuthaben() + ereigniskarte.getBetrag()));

				}
				// Wenn man über Los kommt Bezahlung (wenn in der csv
				// vorhanden)!
				if (ereigniskarte.getPos() == 1) {
					spielerKi.setGuthaben((spielerKi.getGuthaben() + ereigniskarte.getBetrag()));
				}
				if (ereigniskarte.getPos() > 1 && spielerKi.getPosition() > ereigniskarte.getPos()) {
					spielerKi.setGuthaben((spielerKi.getGuthaben() + ereigniskarte.getBetrag()));
				}
				zahl = ((spielerKi.getGuthaben() - zahl));
				if (ereigniskarte.getPos() == 0) {
					assertTrue(ereigniskarte.getBetrag() == zahl);
				}

			}

			// An allen Zahlen oder bekommen
			if (ereigniskarte.getZahlenSpieler() != 0) {
				for (Spieler s : s1.getSpieler()) {
					if (s.getFarbe() != spielerKi.getFarbe()) {
						spielerKi.setGuthaben(spielerKi.getGuthaben() + ereigniskarte.getZahlenSpieler());
						s.setGuthaben(s.getGuthaben() + ((ereigniskarte.getZahlenSpieler() * -1)));
					}
				}
				assertTrue(spielerKi.getGuthaben() == spielerO.getGuthaben());
			}

			// Zahlen od. Bekommen für Haeuser
			if (ereigniskarte.getHaus() != 0) {
				int haeuser = 0;
				if (spielerKi.getBesitzrechtkarten() != null) {
					for (Besitzrechtkarten a : spielerKi.getBesitzrechtkarten()) {
						if (a.istStrassenBesitzrechtkarte()) {
							if (((Strasse) a.getSpielfeld()).getHaus1() != null) {
								haeuser += 1;
							}
							if (((Strasse) a.getSpielfeld()).getHaus2() != null) {
								haeuser += 1;
							}
						}
					}
				}
				spielerKi.setGuthaben((spielerKi.getGuthaben() + (haeuser * ereigniskarte.getHaus())));
				assertTrue(spielerKi.getGuthaben() == spielerO.getGuthaben());
			}

			// Zahlen od. Bekommen für Hotels
			if (ereigniskarte.getHotel() != 0) {
				int hotels = 0;
				if (spielerKi.getBesitzrechtkarten() != null) {
					for (Besitzrechtkarten a : spielerKi.getBesitzrechtkarten()) {
						if (a.istStrassenBesitzrechtkarte()) {
							if (((Strasse) a.getSpielfeld()).getHotel() != null) {
								hotels += 1;
							}
						}
					}
				}
				spielerKi.setGuthaben((spielerKi.getGuthaben() + (hotels * ereigniskarte.getHotel())));
				assertTrue(spielerKi.getGuthaben() == spielerO.getGuthaben());
			}
			// Positionswechsel vorwärts
			if (ereigniskarte.getPos() > 0) {
				if (ereigniskarte.getPos() > 99) {
					if (spielerKi.getPosition() + (ereigniskarte.getPos() / 100) > 39) {
						spielerKi.setPosition((spielerKi.getPosition()
								+ ((ereigniskarte.getPos() / 100) - s1.getSpielbrett().getSpielfeld().length - 1)));
					} else {
						spielerKi.setPosition(spielerKi.getPosition() + (ereigniskarte.getPos() / 100));
					}

				}
				if (ereigniskarte.getPos() > 0 && ereigniskarte.getPos() < 100) {
					spielerKi.setPosition(ereigniskarte.getPos() - 1);
				}
				if (ereigniskarte.getPos() != 1) {

				}
				assertTrue(spielerKi.getPosition() != spielerO.getPosition());
			}

			// Positionswechsel rückwärts
			if (ereigniskarte.getPos() < 0) {
				if ((spielerKi.getPosition() + (ereigniskarte.getPos() / 100)) < 0) {
					// Wenn die Position hinter Los liegt wird der Rest von
					// Schlossallee zurück gesetzt.
					spielerKi.setPosition(s1.getSpielbrett().getSpielfeld().length - 1
							+ (spielerKi.getPosition() + (ereigniskarte.getPos() / 100)));
					if (ereigniskarte.getBetrag() != 0) {
						spielerKi.setGuthaben(spielerKi.getGuthaben() + ereigniskarte.getBetrag());
					}
				} else {
					// Wenn die Position nicht hinter Los liegt wird normal
					// berechnet.
					spielerKi.setPosition((spielerKi.getPosition() + (ereigniskarte.getPos() / 100)));
				}
				if (ereigniskarte.getPos() != 1) {
				}
				assertTrue(spielerKi.getPosition() != spielerO.getPosition());
			}

		}
		// Checke ob ein Spieler pleite geht nach einer Ereigniskarte
		for (int i = 0; i < s1.getSpieler().size(); i++) {
			if (s1.getSpieler().get(i).getGuthaben() <= 0) {
				s1.loescheSpielerAusSpiel(s1.getSpieler().get(i));
			}
		}

	}

}
