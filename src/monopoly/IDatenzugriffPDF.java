package monopoly;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Klasse modeliert das Datenzugriffsinterface, durch das PDFs gespeichert und
 * geladen werden.
 */
public class IDatenzugriffPDF implements IDatenzugriff, Serializable {

	/**
	 * Die serialVersionUID der Klasse
	 */
	private static final long serialVersionUID = -4119332427649329742L;

	/**
	 * Hier wird die Pdf datei erstellt
	 * 
	 * @param pfad
	 */
	public void createPdf(String pfad) {
		Document document = new Document();
		PdfWriter writer;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(pfad));

			document.setPageSize(PageSize.A4);
			document.open();
			document.setPageSize(PageSize.A4.rotate());
			writer.setPageSize(PageSize.A4.rotate());

			PdfContentByte cb = writer.getDirectContent();
			setImage(cb, "screenshot.png", 40);
		} catch (Exception e) {
			System.out.println("Fehler beim speichern.");
			e.printStackTrace();
		} finally {
			document.close();
		}

	}

	/**
	 * Diese Methode speichert das Bild in die Pdf Datei ein.
	 * 
	 * @param cb
	 * @param imgPath
	 * @param scalePercent
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void setImage(PdfContentByte cb, String imgPath, float scalePercent)
			throws MalformedURLException, IOException, DocumentException {
		Image img = Image.getInstance(imgPath);
		img.scalePercent(scalePercent - 9);
		img.setAbsolutePosition(cb.getXTLM(), cb.getYTLM() + 280);
		cb.addImage(img);
	}

	/**
	 * Diese Methode speichert ein Screenshot intern ab.
	 */
	public void speichern() {
		BufferedImage image;
		try {
			image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File("screenshot.png"));
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode die Files löscht.
	 */
	public void deletefiles() {
		File file = new File("screenshot.png");
		if (file.exists()) {
			file.delete();
			System.out.print("...");
		}
		file = new File("datei.pdf");
		if (file.exists()) {
			file.delete();
			System.out.println("...");
		}
	}

	/**
	 * @param pfad
	 *            der Ort an dem das PDF gespeichert werden soll.
	 * @param o
	 */
	public void speichern(String pfad, Object o) {

	}

	@Override
	public Spiel laden(String pfad) {
		return null;
	}

	@Override
	public void MailSenden(String an, String betreff, String text, String anhangPfad1, String anhangName1,
			String anhangPfad2, String anhangName2) {
	}

	@Override
	public void laden() {
	}

	@Override
	public void speichern(String string) {
	}

}