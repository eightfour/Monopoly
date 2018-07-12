package monopolyGui;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import monopoly.Besitzrechtkarten;
import monopoly.Ereignisfeld;
import monopoly.FarbEnum;

import monopoly.Strasse;

public class EventHandler implements ActionListener, ComponentListener, MouseListener, ChangeListener {

	Gui gui;

	private Strasse tempStrasse;
	private boolean verkaufenklicked = false;
	private boolean bauenklicked = false;
	private JButtonSF klicked = null;
	private Boolean janein = false;
	private int anzahlVonSpielerAufFeld;

	public EventHandler(Gui gui) {
		this.gui = gui;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		actionExit(obj);
		actionVerkaufen(obj);
		actionBauen(obj);
		actionLaden(obj);
		actionSpeichern(obj);
		actionSpielfeldButton(obj);
		actionKontrolleAbgeben(obj);
		actionWurfeln(obj);
		actionKaufen(obj);
		actionDialogJaNein(obj);
		actionEreignisfeld(obj);
		actionSpielstandSenden(obj);
	}

	private void actionSpielstandSenden(Object obj) {
		if (obj.equals(gui.mailButton)) {
			JTextField adresse = new JTextField();
			JOptionPane.showConfirmDialog(null, new Object[] { "Bitte E-Mail Adresse eingeben! ", adresse },
					"Willkommen bei Monopoly", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (adresse.getText().length() < 5) {
				System.out.println("Nicht gesendet...");
				return;
			}
			Gui.bed.speichern("spiel.ser");
			Gui.bed.speichern();
			Gui.bed.createPdf("datei.pdf");
			System.out.println("Bitte warten... Wird gesendet...");
			Gui.bed.MailSenden(adresse.getText(), "Monopoly SaveGame MTBT", "Monopoly SaveGame MTBT", "spiel.ser",
					"spiel.ser", "datei.pdf", "datei.pdf");
		}

	}

	/**
	 * Bauen button bauenklicken wird aktiviert
	 * 
	 */
	public void actionBauen(Object obj) {
		if (obj.equals(gui.bauenButton)) {
			System.out.println("Klicken sie auf die Besitzrechkarte auf der sie ein Haus oder Hotel bauen wollen");
			bauenklicked = true;
		}
	}

	/**
	 * Ruft ein Fenster mit dem Dialog Ja/Nein auf
	 * 
	 * @return false or true
	 * 
	 */
	public boolean actionDialogJaNein(Object obj) {
		if (obj.equals(gui.ja)) {
			Gui.bed.eingabe(true);
			Window w = SwingUtilities.getWindowAncestor(gui.ja);
			w.setVisible(false);
			janein = true;
			return true;
		}
		if (obj.equals(gui.nein)) {
			Gui.bed.eingabe(false);
			Window w = SwingUtilities.getWindowAncestor(gui.nein);
			w.setVisible(false);
			janein = false;
			return false;
		}
		janein = false;
		return false;

	}

	/**
	 * Exit Button Spiel wird beendet
	 * 
	 */
	public void actionExit(Object obj) {
		if (obj.equals(gui.exitButton)) {
			Gui.bed.deletefiles();
			System.exit(0);
		}

	}

	/**
	 * Aktion Kaufen Button
	 * 
	 */
	public void actionKaufen(Object obj) {
		if (obj.equals(gui.kaufenButton)) {
			if (Gui.bed.kaufGrundStueckEins()) {
				gui.initYNDialog("Wollen Sie " + gui.feld[Gui.bed.getSpielerAmZug().getPosition()].getFeld().getName()
						+ " kaufen?");
				if (janein) {
					Gui.bed.kaufGrundStueckZwei();
					if (Gui.bed.feldHatBesitzer()) {
						gui.kaufenButton.setEnabled(false);
					}
					janein = false;
				}
			}
			gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
			for (Besitzrechtkarten a : Gui.bed.getSpielerAmZug().getBesitzrechtkarten()) {
				if (a.getSpielfeld().getPosition() == Gui.bed.getSpielerAmZug().getPosition()) {
					gui.feld[Gui.bed.getSpielerAmZug().getPosition()]
							.setBorder(new LineBorder(getColor(Gui.bed.getSpielerAmZug().getID()),
									(int) Math.round(0.0021961932650073 * gui.dim.getWidth())));
				}
			}
		}
	}

	/**
	 * Methode kontorolle Abgeben Gibt die kontrolle ab Oder fängt
	 * aktiongefängnis ab(wenn true ist) Oder wenn das Spiel zu ende ist -
	 * Spielende
	 * 
	 * 
	 */
	public void actionKontrolleAbgeben(Object obj) {
		if (obj.equals(gui.kontrolleButton)) {
			if (Gui.bed.letzteRundeErreicht() || Gui.bed.irregulaeresEnde()) {
				initSpielfeld();
				gui.bauenButton.setEnabled(false);
				gui.kaufenButton.setEnabled(false);
				gui.kontrolleButton.setEnabled(false);
				gui.safeButton.setEnabled(false);
				gui.verkaufenButton.setEnabled(false);
				gui.wurfelnButton.setEnabled(false);
				gui.safeButton.setEnabled(false);
				gui.lblRunde.setText("Runde " + Gui.bed.getAktuelleRunde() + " / " + Gui.bed.getMaximaleRundenAnzahl());
				gui.lblSpN.setText("Spiel beendet");
				gui.lblSpGh.setText("Spiel beendet");
			} else {
				Gui.bed.kontrolleAbgeben();
				gui.loadButton.setEnabled(true);
				gui.lblRunde.setText("Runde " + Gui.bed.getAktuelleRunde() + " / " + Gui.bed.getMaximaleRundenAnzahl());
				gui.lblSpN.setText("" + gui.namen[Gui.bed.getSpielerAmZug().getID() - 1]);
				gui.panelSpieler.setBorder(new LineBorder(getColor(Gui.bed.getSpielerAmZug().getID()), 3));
				gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
				gui.wurfelnButton.setText("Wurf");
				gui.hauptfenster.setBorder(new LineBorder(getColor(Gui.bed.getSpielerAmZug().getID()),
						(int) (Math.round(0.0016603221083455 * gui.dim.getWidth()))));
				if (Gui.bed.getSpielerAmZug().isIstKi()) {
					gui.Kigame();
				} else {
					gui.bauenButton.setEnabled(true);
					gui.verkaufenButton.setEnabled(true);
					boolean rauskommen = false;
					if (Gui.bed.getSpielerAmZug().getPosition() == 10) {
						if (Gui.bed.getSpielerAmZug().getGefaengnisCounter() > 0) {
							gui.initYNDialog("Versuchen auszubrechen? Nein drücken um 80% Kaution zu zahlen...");
							rauskommen = Gui.bed.aktionGefaengnis2(janein);
							gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
						} else {
							rauskommen = Gui.bed.aktionGefaengnis2(true);
						}
						gui.wurfelnButton.setEnabled(false);
					} else {
						gui.wurfelnButton.setEnabled(true);
						gui.kontrolleButton.setEnabled(false);
						gui.kaufenButton.setEnabled(false);
					}
					if (rauskommen) {
						gui.wurfelnButton.setEnabled(true);
						gui.kontrolleButton.setEnabled(false);
						gui.kaufenButton.setEnabled(false);
						gui.wurfelnButton.setEnabled(true);
					}
				}

			}
		}
	}

	/**
	 * Laden Button laden() wird aufgerufen
	 * 
	 */
	public void actionLaden(Object obj) {
		if (obj.equals(gui.loadButton)) {
			gui.initYNDialog("Wollen Sie eine externe Datei laden?");
			if (janein) {
				String pfad = System.getProperty("user.home");
				JFileChooser chooser = new JFileChooser(pfad);
				FileNameExtensionFilter markUpFilter = new FileNameExtensionFilter("Markup: SER", "ser");
				chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
				chooser.setFileFilter(markUpFilter);
				chooser.showOpenDialog(null);
				try {
					Gui.bed = Gui.bed.laden(chooser.getSelectedFile().getPath());
					gui.loadButton.setEnabled(false);
				} catch (Exception e) {
					System.out.println("Datei konnte nicht geladen werden.");
					gui.loadButton.setEnabled(true);
					return;
				}

			} else {
				Gui.bed = Gui.bed.laden("spiel.ser");
				gui.loadButton.setEnabled(false);
			}
			for (int i = 0; i < gui.feld.length; i++) {
				gui.feld[i].feld = Gui.bed.getSpielbrett().geteinSpielfeld(i);
			}
			gui.lblRunde.setText("Runde " + Gui.bed.getAktuelleRunde() + " / " + Gui.bed.getMaximaleRundenAnzahl());
			gui.lblSpN.setText("" + gui.namen[Gui.bed.getSpielerAmZug().getID() - 1]);
			gui.panelSpieler.setBorder(new LineBorder(getColor(Gui.bed.getSpielerAmZug().getID()), 3));
			gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
			gui.wurfelnButton.setEnabled(false);
			gui.kontrolleButton.setEnabled(true);
			gui.ereignisButton.setEnabled(false);
			gui.ereignisButton.setOpaque(true);
			gui.ereignisButton.setContentAreaFilled(true);
			initSpielfeld();
			if (Gui.bed.getSpielerAmZug().isIstKi()) {
				gui.Kigame();
			}
			// if
			// (gui.feld[gui.bed.getSpielerAmZug().getPosition()].getFeld().getClass().equals(Ereignisfeld.class))
			// {
			// gui.ereignisButton.setOpaque(false);
			// gui.wurfelnButton.setEnabled(false);
			// }

		}
	}

	/**
	 * Speichern Button ruft speichern() auf
	 * 
	 */
	public void actionSpeichern(Object obj) {
		if (obj.equals(gui.safeButton)) {
			String pfad = null;
			JFileChooser chooser;
			gui.initYNDialog("Wollen Sie eine externe Datei speichern?");
			if (janein) {
				try{
					pfad = System.getProperty("user.home");

					chooser = new JFileChooser(pfad);
					chooser.setDialogType(JFileChooser.SAVE_DIALOG);
					FileNameExtensionFilter markUpFilterSer = new FileNameExtensionFilter("Ser", "ser");
					FileNameExtensionFilter markUpFilterPdf = new FileNameExtensionFilter("PDF", "pdf");
					chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
					chooser.setFileFilter(markUpFilterSer);
					chooser.setFileFilter(markUpFilterPdf);
					chooser.setDialogTitle("Speichern unter...");
					chooser.setSelectedFile(new File("user.home\\Desktop\\monopoly.pdf"));
					chooser.setVisible(true);

					chooser.showSaveDialog(null);

					if (chooser.getFileFilter().getDescription().equals(markUpFilterPdf.getDescription())) {
						
							Gui.bed.speichern(chooser.getSelectedFile().getParent() + "\\spiel.ser");
							Gui.bed.speichern();
							Gui.bed.createPdf(chooser.getSelectedFile().getPath());
						
					} else {
							Gui.bed.speichern(chooser.getSelectedFile().getPath());
							Gui.bed.speichern();
							Gui.bed.createPdf(chooser.getSelectedFile().getParent() + "\\PdfDatei.pdf");
					}

					chooser.setVisible(false);

				} catch (Exception e) {
					System.out.println("Spiel nicht gespeichert...");
				}
			} else {
				// speichert intern ser-Datei
				Gui.bed.speichern("spiel.ser");

			}

		}
	}

	/**
	 * Spielfeld Button
	 */
	public void actionSpielfeldButton(Object obj) {
		if (obj.getClass().equals(JButtonSF.class)) {
		}
	}

	/**
	 * Verkaufen button verkaufenklicked wird aktiviert
	 * 
	 */
	public void actionVerkaufen(Object obj) {
		if (obj.equals(gui.verkaufenButton)) {
			System.out.println("Klicken sie auf die Besitzrechtkarte die sie verkaufen wollen");
			verkaufenklicked = true;
		}

	}

	public void actionEreignisfeld(Object obj) {
		if (obj.equals(gui.ereignisButton)) {
			if (Gui.bed.letzteRundeErreicht() || Gui.bed.irregulaeresEnde()) {
				initSpielfeld();
				gui.bauenButton.setEnabled(false);
				gui.kaufenButton.setEnabled(false);
				gui.kontrolleButton.setEnabled(false);
				gui.safeButton.setEnabled(false);
				gui.verkaufenButton.setEnabled(false);
				gui.wurfelnButton.setEnabled(false);
				gui.safeButton.setEnabled(false);
				gui.lblRunde.setText("Runde " + Gui.bed.getAktuelleRunde() + " / " + Gui.bed.getMaximaleRundenAnzahl());
				gui.lblSpN.setText("Spiel beendet");
				gui.lblSpGh.setText("Spiel beendet");
			} else {
				JOptionPane.showConfirmDialog(null, new Object[] { Gui.bed.getEreigniskarte().getName(), null },
						"Ereigniskarte", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
				Gui.bed.aktionEreignisfeld();
				if (!Gui.bed.istBesitzbaresFeldAktuell() || Gui.bed.feldHatBesitzer()) {
					gui.kaufenButton.setEnabled(false);
				} else {
					gui.kaufenButton.setEnabled(true);
				}
				initSpielfeld();
				gui.lblbwStrasse.setText(gui.feld[Gui.bed.getSpielerAmZug().getPosition()].getFeld().getName());
				gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
				gui.ereignisButton.setEnabled(false);
				gui.ereignisButton.setOpaque(true);
				gui.ereignisButton.setContentAreaFilled(true);
				gui.kontrolleButton.setEnabled(true);
				gui.safeButton.setEnabled(false);

			}
		}
	}

	/**
	 * Methode aktionwürfeln - Der spieler würfelt
	 */
	public void actionWurfeln(Object obj) {
		if (obj.equals(gui.wurfelnButton)) {
			if (!Gui.bed.getSpielerAmZug().isIstKi()) {
				Gui.bed.zug1();
				gui.loadButton.setEnabled(true);
				gui.safeButton.setEnabled(false);
				gui.mailButton.setEnabled(false);
				gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
				if (gui.feld[Gui.bed.getSpielerAmZug().getPosition()].getFeld().getClass().equals(Ereignisfeld.class)) {
					gui.kontrolleButton.setEnabled(false);
					gui.ereignisButton.setEnabled(true);
					gui.ereignisButton.setOpaque(false);
					gui.ereignisButton.setContentAreaFilled(false);
				} else {
					gui.kontrolleButton.setEnabled(true);
					gui.ereignisButton.setEnabled(false);
					gui.ereignisButton.setOpaque(true);
					gui.ereignisButton.setContentAreaFilled(true);
				}
				if (!Gui.bed.istBesitzbaresFeldAktuell() || Gui.bed.feldHatBesitzer()) {
					gui.kaufenButton.setEnabled(false);
				} else {
					gui.kaufenButton.setEnabled(true);
				}
				Gui.bed.zug2();
				gui.wurf = Gui.bed.getWurf();
				gui.btnWurfel1.setIcon(gui.wurfelIcon[gui.wurf[0] - 1]);
				gui.btnWurfel2.setIcon(gui.wurfelIcon[gui.wurf[1] - 1]);
				gui.wurfelnButton.setText("" + gui.wurf[2]);
				gui.wurfelnButton.setEnabled(false);

				// System.out.println(gui.bed.getSpielerAmZug());

				gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
				if (!Gui.bed.istBesitzbaresFeldAktuell() || Gui.bed.feldHatBesitzer()) {
					gui.kaufenButton.setEnabled(false);
				} else {
					gui.kaufenButton.setEnabled(true);
				}
			}
		}
		initSpielfeld();
		gui.lblbwStrasse.setText(gui.feld[Gui.bed.getSpielerAmZug().getPosition()].getFeld().getName());

	}

	/**
	 * bauenklicked wenn Spieler besitzer vom feld ist wird bauen() aufgerufen
	 * wenn nicht wird bauenklicked = false
	 */
	public void bauenklicked(Object obj) {
		JButtonSF sf = (JButtonSF) obj;
		if (bauenklicked) {
			if (sf.feld.getClass() == Strasse.class) {
				if (Gui.bed.darfbauen((Strasse) sf.feld)) {
					int wert = Gui.bed.bauen((Strasse) sf.feld);
					if (wert >= 0) {
						gui.feldo[sf.feld.getPosition()].setIcon(gui.hausIcon[wert]);
						bauenklicked = false;
						klicked = null;
						gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
					}

				}
				bauenklicked = false;
				klicked = null;
				// if(Gui.bed.darfbauen(aktSpieler,(Strasse) sf.getFeld())){
				//
				// Besitzrechtkarten b = new
				// Besitzrechtkarten(sf.getFeld(),aktSpieler);
				// if(Gui.bed.bauen(b)){
				// int haus=0;
				// if(((Strasse)klicked.getFeld()).getHotel()==null){
				// if(((Strasse)klicked.getFeld()).getHaus2()==null){
				// if(((Strasse)klicked.getFeld()).getHaus1()!=null){
				// haus=0;
				// }
				// }else{haus=1;}
				// }else{haus=2;}
				// gui.feldo[((Strasse)klicked.getFeld()).getPosition()].setIcon(gui.hausIcon[haus]);
				// bauenklicked = false;
				// klicked=null;
				// gui.lblSpGh.setText(gui.bed.getSpielerAmZug().getGuthaben() +
				// " €");
				// }else{
				// bauenklicked = false;
				// klicked=null;
				// gui.lblSpGh.setText(gui.bed.getSpielerAmZug().getGuthaben() +
				// " €");
				// }
				// }
				// }else{
				// System.out.println("Das ist keine Karte auf der sie ein Haus
				// bauen können ");
				// klicked=null;
			} else {
				System.out.println("Nicht bebaubar!");
				bauenklicked = false;
				klicked = null;
			}

		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * getColor Methode
	 * 
	 * @return gibt die jeweilige Farbe zurück
	 * 
	 */
	public Color getColor(int a) {
		switch (a) {
		case 1:
			return Color.RED;

		case 2:
			return Color.BLUE;

		case 3:
			return Color.GREEN;

		case 4:
			return Color.YELLOW;

		case 5:
			return Color.BLACK;

		default:
			return Color.WHITE;
		}
	}

	/**
	 * zählt die Spieler auf feld und setzt die Spieler auf die entsprechende
	 * Felder(optisch also bilder)
	 * 
	 */
	public void initSpielfeld() {
		anzahlVonSpielerAufFeld = 0;
		for (int i = 0; i < Gui.bed.getSpielbrett().getSpielfeld().length; i++) {
			anzahlVonSpielerAufFeld = Gui.bed.getAnzahlSpielerAufFeld(i);
			gui.feld[i].setBorder(null);
			gui.feldo[i].setBorder(null);
			if (Gui.bed.getSpielbrett().getSpielfeld()[i].getClass().equals(Strasse.class)) {
				tempStrasse = (Strasse) Gui.bed.getSpielbrett().getSpielfeld()[i];
				if (tempStrasse.getHaus1() != null) {
					gui.feldo[i].setIcon(gui.hausIcon[0]);
				}
				if (tempStrasse.getHaus2() != null) {
					gui.feldo[i].setIcon(gui.hausIcon[1]);
				}
				if (tempStrasse.getHotel() != null) {
					gui.feldo[i].setIcon(gui.hausIcon[2]);
				}
				if (tempStrasse.getHaus1() == null && tempStrasse.getHaus2() == null
						&& tempStrasse.getHotel() == null) {
					gui.feldo[i].setIcon(null);
				}

			}

			if (anzahlVonSpielerAufFeld != 0) {
				for (int a = 0; a < gui.spielerIconNumInt.length; a++) {
					if (gui.spielerIconNumInt[a] == anzahlVonSpielerAufFeld) {
						gui.feld[i].setIcon(gui.spielerIcon[a]);
					}
				}
			} else {
				gui.feld[i].setIcon(null);
			}
		}
		for (int i = 0; i < Gui.bed.getSpieler().size(); i++) {
			for (int j = 0; j < Gui.bed.getSpieler().get(i).getBesitzrechtkarten().size(); j++) {
				gui.feld[Gui.bed.getSpieler().get(i).getBesitzrechtkarten().get(j).getSpielfeld().getPosition()]
						.setBorder(new LineBorder(
								getColor(FarbEnum.getFarbe(Gui.bed.getSpieler().get(i).getBesitzrechtkarten().get(j)
										.getBesitzer().getFarbe())),
								(int) Math.round(0.0021961932650073 * gui.dim.getWidth())));

			}
		}
		if (gui.ereignisButton.isEnabled() || Gui.bed.getSpielerAmZug().getID() != 1) {
			gui.safeButton.setEnabled(false);
			gui.mailButton.setEnabled(false);
		} else {
			if (Gui.bed.getSpielerAmZug().getID() == 1) {
				gui.safeButton.setEnabled(true);
				gui.mailButton.setEnabled(true);
			}
		}
	}

	/**
	 * moudeclicked ruft bauenklicked oder verkaufenklicked auf
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getComponent().getClass() == JButtonSF.class) {
			JButtonSF temp = (JButtonSF) arg0.getComponent();
			System.out.println(temp.getFeld());
			if (bauenklicked) {
				klicked = temp;
				bauenklicked(arg0.getComponent());
			}
			if (verkaufenklicked) {
				klicked = temp;
				verkaufenklicked(arg0.getComponent());
			}
		}

	}

	/**
	 * mouseEntered gibt Text aus jenachdem wo die maus drüber geführt wird
	 * 
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getComponent().getClass() == JButtonSF.class) {
			JButtonSF temp = (JButtonSF) arg0.getComponent();
			gui.lblbwStrasse.setText(temp.getFeld().getPosition() + ": " + temp.getFeld().getName());

		}

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (arg0.getComponent().getClass() == JButtonSF.class) {
			gui.lblbwStrasse.setText(gui.feld[Gui.bed.getSpielerAmZug().getPosition()].getFeld().getName());
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public void startGame() {
		if (Gui.bed.getSpielerAmZug().isIstKi()) {
			gui.Kigame();
			gui.kontrolleButton.setEnabled(true);
		} else {
			if (!Gui.bed.istBesitzbaresFeldAktuell() || Gui.bed.feldHatBesitzer()) {
				gui.kaufenButton.setEnabled(false);
			} else {
				gui.kaufenButton.setEnabled(true);
			}
		}
		gui.safeButton.setEnabled(true);
		gui.loadButton.setEnabled(true);
		initSpielfeld();
		gui.panelSpieler.setBorder(new LineBorder(getColor(Gui.bed.getSpielerAmZug().getID()),
				(int) (Math.round(0.0021961932650073 * gui.dim.getWidth()))));
		gui.hauptfenster.setBorder(new LineBorder(getColor(Gui.bed.getSpielerAmZug().getID()),
				(int) (Math.round(0.0016603221083455 * gui.dim.getWidth()))));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (gui.sliderrundenanzahl.getValue() == 101) {
			gui.lblsliderrundenanzahl.setText("Runden: Max");
			gui.runden = 2147483647;
		} else {
			gui.lblsliderrundenanzahl.setText("Runden: " + gui.sliderrundenanzahl.getValue());
			gui.runden = gui.sliderrundenanzahl.getValue();
		}

	}

	/**
	 * verkaufenklicked Wenn Spieler besitzer vom Feld ist wird verkaufen()
	 * aufgerufen wenn nicht verkaufenklicked = false
	 * 
	 */
	public void verkaufenklicked(Object obj) {
		JButtonSF sf = (JButtonSF) obj;
		if (Gui.bed.istBesitzrechtkarteVonSpieler(sf.getFeld())) {
			gui.initYNDialog("Wollen Sie wirklich verkaufen?");
			if (janein) {
				Gui.bed.verkaufenGUI(klicked.getFeld());
				sf.setBorder(null);
				Strasse temp1 = null;
				gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
				if (klicked.getFeld().getClass().equals(Strasse.class)) {
					temp1 = (Strasse) klicked.feld;
				}
				for (int i = 0; i < gui.feld.length; i++) {
					if (gui.feld[i].feld.getClass().equals(Strasse.class) && temp1 != null) {
						Strasse temp2 = (Strasse) gui.feld[i].feld;
						if (temp1.getStrassenID() == temp2.getStrassenID()) {
							gui.feldo[i].setIcon(null);
						}
					}

				}
				verkaufenklicked = false;
				klicked = null;
				gui.lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
				janein = false;
				if (!Gui.bed.feldHatBesitzer()) {
					gui.kaufenButton.setEnabled(true);
				}
			} else {
				System.out.println("Nicht verkauft!");
				verkaufenklicked = false;
				klicked = null;
				janein = false;
			}
		} else {
			System.out.println("Gehört Ihnen nicht");
			verkaufenklicked = false;
			klicked = null;
			janein = false;
		}

	}

}
