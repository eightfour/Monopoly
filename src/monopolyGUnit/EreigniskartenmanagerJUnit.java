package monopolyGUnit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import monopoly.Ereigniskarte;
import monopoly.Ereigniskartenmanager;

public class EreigniskartenmanagerJUnit {

	Ereigniskartenmanager a = new Ereigniskartenmanager();

	@Test
	public void testEreigniskartenmanager() {
		boolean te = false;
		ArrayList<Ereigniskarte> b = a.getEreigniskarten();
		if (b == null) {
			te = false;
		} else {
			te = true;
		}
		assertTrue(te);
	}

	@Test
	public void testKarteZiehen() {
		boolean te = false;
		Ereigniskarte b = a.zieheEreigniskarte();
		if (b instanceof Ereigniskarte) {
			te = true;
		} else {
			te = false;
		}
		assertTrue(te);
	}

	@Test
	public void testKarteZiehen2() {
		boolean te = true;
		for (int i = 0; i <= 1000; i++) {
			Ereigniskarte b = a.zieheEreigniskarte();
			if (b instanceof Ereigniskarte && b != null) {
				if (te == true) {

				}
				;
			} else {
				te = false;
			}
		}
		assertTrue(te);
	}

}
