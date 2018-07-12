package monopoly;

/**
 * @author bisci Dieses Interface ist fuer das Speichern,Laden (Serialisieren,
 *         De-Serialisieren) eines Spiels zustaendig.
 */
public interface IDatenzugriff {

	/**
	 * Laedt aus einer Datei
	 * 
	 * @return
	 */
	Spiel laden(String pfad);

	void laden();

	/**
	 * Speichert in eine Datei.
	 * 
	 * @param string
	 */
	void speichern(String string);

	/**
	 * Methode zum Mail Senden.
	 * 
	 * @param an
	 * @param betreff
	 * @param text
	 * @param anhangPfad1
	 * @param anhangName1
	 * @param anhangPfad2
	 * @param anhangName2
	 */
	public void MailSenden(String an, String betreff, String text, String anhangPfad1, String anhangName1,
			String anhangPfad2, String anhangName2);

}
