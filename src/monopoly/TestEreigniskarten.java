package monopoly;

import java.io.Serializable;

/**
 *
 * Diese Klasse ist zum testen der Ereigniskarten gedacht.
 */
public class TestEreigniskarten implements Serializable {

	/**
	 * Versionsnummer fuer das Serialisieren.
	 */
	private static final long serialVersionUID = 3026516881454301109L;

	/**
	 * Statische Main in der getestet wird.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * Ein Ereigniskartenmanager wird erstellt.
		 */
		Ereigniskartenmanager a = new Ereigniskartenmanager();

		/**
		 * Der Ereigniskartenmanager wird initalisiert.
		 */
		a.laden();

		// for(Ereigniskarte b : a.getEreigniskarten()){
		// System.out.println(b.toStringo());
		// }

		/**
		 * Die Karten werden ausgegeben.
		 */
		for (int i = 0; i < 10; i++) {
			System.out.println(a.zieheEreigniskarte().toStringo());
		}

	}

}
