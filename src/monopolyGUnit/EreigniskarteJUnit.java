package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monopoly.Ereigniskarte;
import monopoly.Ereigniskartenmanager;

public class EreigniskarteJUnit {

	Ereigniskarte a = (new Ereigniskartenmanager().zieheEreigniskarte());

	@Test
	public void testVariablen() {
		boolean te = true;
		Integer i = a.getBetrag();
		if (i instanceof Integer) {
		} else {
			te = false;
		}
		i = a.getHaus();
		if (i instanceof Integer) {
		} else {
			te = false;
		}
		i = a.getHotel();
		if (i instanceof Integer) {
		} else {
			te = false;
		}
		i = a.getPos();
		if (i instanceof Integer) {
		} else {
			te = false;
		}
		if (a.getName() instanceof String) {
		} else {
			te = false;
		}
		assertTrue(te);
	}

}
