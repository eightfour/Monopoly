package monopolyGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import monopoly.Bahnhof;
import monopoly.Besitzrechtkarten;
import monopoly.Ereignisfeld;
import monopoly.FarbEnum;
import monopoly.FreiParken;
import monopoly.GeheInsGefaengnis;
import monopoly.IBediener;
import monopoly.Spiel;
import monopoly.Strasse;
import monopoly.Versorgungswerk;

public class Gui extends JFrame {
	/**
	 * @author Burak, Tim, Mehmet, Thomas
	 * 
	 */
	public static IBediener bed;
	
	/**
	 * ID zum serialisieren
	 */
	private static final long serialVersionUID = 4378095560951364373L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Gui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	EventHandler evh = new EventHandler(this);
	JPanel hauptfenster;
	JTextArea southText = new JTextArea();
	int runden = 0;
	JPanel bottom = new JPanel();
	JPanel panelSpieler;
	JButton verkaufenButton, kaufenButton, bauenButton, kontrolleButton, wurfelnButton,ereignisButton, btnWurfel1, btnWurfel2, mailButton;
	JButton exitButton = new JButton();
	JLabel lblbwStrasse, lblSpN, lblSpGh, lblsliderrundenanzahl, lblRunde, center;
	JButtonSF[] feld;
	JButton[] feldo;
	ImageIcon[] wurfelIcon;
	Integer[] spielerIconNumInt = { 1, 10, 11, 100, 101, 110, 111, 1000, 1001, 1010, 1011, 1100, 1101, 1110, 1111,
			10000, 10001, 10010, 10011, 10100, 10101, 10110, 10111, 11000, 11001, 11010, 11011, 11100, 11101, 11110,
			11111 };
	ImageIcon[] spielerIcon = new ImageIcon[31];
	ImageIcon[] hausIcon;
	String namen[];
	String namenvorgaben[] = { "Misset", "Klicker", "Tellerwäscher", "Millionär", "Franki-Danki", "Nestea", "Joda",
			"Ayran", "Lahmacun", "Harald", "Dope", "Naturalis", "Stalker", "Think-Pad", "Black-Bull", "SilverSurfer",
			"Samson", "Lupi", "Becks", "DrunkenBoy", "MissGermany", "Marco-Polo", "Affe" };
	JSlider slider;
	JSlider sliderrundenanzahl;
	JSlider maxrundenanzahl;
	JSlider kiAnzahlSlider;
	JOptionPane spielerAnzahl;
	JOptionPane anzahl;
	JOptionPane spieler;
	JOptionPane isKiAnzahl;
	JButton ja = new JButton("Ja");
	JButton nein = new JButton("Nein");
	Dimension dim;
	int wurf[]=new int[3];
	JTextField name = new JTextField();
	private JPanel bottom_east = new JPanel();
	private JPanel bottom_west = new JPanel();
	@SuppressWarnings("unused")
	private boolean kiauto=false;
	final double nx = 0.0607613469985359;
	final double nxx = 0.0368711566617862;
	final double dy = 0.7408666666666667;
	final double dx = 0.4082957540263543;
	double fx,fxx,x,y;


	private JPanel bottom_center = new JPanel();
	JButton safeButton = new JButton();

	JButton loadButton = new JButton();

	/**
	 * Constructor fuer die Gui in dem groesse und die möglichen Fenster (z.B die Slider fuer anzahl der Spieler erstellt wird)
	 */
	public Gui() {
		
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = new Dimension((int) Math.round((0.0846354166666667 * dim.getHeight())),
				(int) Math.round((0.0846354166666667 * dim.getHeight())));
		Font fFeld = new Font("Calibri", Font.PLAIN, (int) Math.round((0.0057291666666667 * dim.getWidth())));
		Font f = new Font("Calibri", Font.PLAIN, (int) Math.round((0.0095168374816984 * dim.getWidth())));

		spielerAnzahl = new JOptionPane();
		isKiAnzahl = new JOptionPane();
		anzahl = new JOptionPane();
		spieler = new JOptionPane();
		name.setText(null);

		slider = new JSlider(2, 5, 2);
		sliderrundenanzahl = new JSlider(1, 101, 15);

		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);

		lblsliderrundenanzahl = new JLabel("Runden: " + sliderrundenanzahl.getValue());
		sliderrundenanzahl.add(lblsliderrundenanzahl);

		sliderrundenanzahl.addChangeListener(evh);

		spielerAnzahl.setFont(f);
		spieler.setFont(f);

		// Spieler

		JOptionPane.showConfirmDialog(null, new Object[] { "Wie viele Spieler sollen Teilnehmen?", slider },
				"Willkommen bei Monopoly", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
		anzahl.setFont(f);
		namen = new String[slider.getValue()];
		isKiAnzahl.setFont(f);
		kiAnzahlSlider = new JSlider(0, slider.getValue(), 0);
		kiAnzahlSlider.setMajorTickSpacing(1);
		kiAnzahlSlider.setPaintLabels(true);
		runden = 15;
		// kianzahl

		 JOptionPane.showConfirmDialog(null, new Object[] {"Wieviele Spieler sollen eine KI sein?", 
				 kiAnzahlSlider}, "Willkommen bei Monopoly", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
		 
			if(kiAnzahlSlider.getValue()==5){
				kiauto=true;
			}
		 
//		 name des Spielers
		for (int i = 0; i < slider.getValue(); i++) {
			do {
				Random r = new Random();
				name.setText(namenvorgaben[r.nextInt(namenvorgaben.length)]);
				JOptionPane.showConfirmDialog(null,new Object[] 
						{ "Wie heißt Spieler " + (i + 1) + "? (Farbe: " + FarbEnum.getFarbe(i + 1) + ")",name },
						"Willkommen bei Monopoly", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
				namen[i] = name.getText();
				name.setText(null);
			} while (namen[i].length() < 2 || namen[i].length() > 15);

		}

		// runden
		JOptionPane.showConfirmDialog(null,
				new Object[] { "Wieviele Runden wollen sie spielen?", sliderrundenanzahl, lblsliderrundenanzahl },
				"Willkommen bei Monopoly", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);

		
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setTitle("MTBT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setConsole();
		bed = new Spiel();
		//dat = (IDatenzugriff) bed;
		bed.spielen(slider.getValue(), runden, kiAnzahlSlider.getValue(), namen);
		
		fx = (nx * dim.getWidth());
		fxx = (nxx * dim.getWidth());
		x = (dx * dim.getWidth());
		y = (dy * dim.getHeight());
		
		
		if (dim.getWidth() > 1366) {
			x += ((dim.getWidth() - 1366) * 0.0234657039711191);
		}
		if (dim.getWidth() < 1366) {
			x += ((dim.getHeight() - 768) * 0.0416666666666667) * -1;
		}
		if(dim.getHeight()==1440 && dim.getWidth()==2160){
			x += -12;
			y += -68;
		}	

		createFields();

		ladeWurfelImage(d);
		ladeSpielerImage(dim);
		ladeHausImage(dim);

		hauptfenster = new JPanel();
		hauptfenster.setBorder(new LineBorder(Color.red, (int) (Math.round(0.0016603221083455 * dim.getWidth()))));
		hauptfenster.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(hauptfenster);
		hauptfenster.setLayout(new BorderLayout(0, 0));

		// JPanel top = new JPanel();
		// top.setBackground(Color.BLUE);
		// top.setForeground(Color.BLUE);
		// hauptfenster.add(top, BorderLayout.NORTH);
		//
		// JLabel lblTitel = new JLabel("Monopoly - MTBT");
		// lblTitel.setForeground(Color.WHITE);
		// lblTitel.setBackground(Color.BLUE);
		// top.add(lblTitel);

		JPanel left = new JPanel();
		left.setBackground(Color.LIGHT_GRAY);
		hauptfenster.add(left, BorderLayout.WEST);
		left.setLayout(new BorderLayout(0, 0));

		JPanel lpanel = new JPanel();
		lpanel.setBackground(Color.LIGHT_GRAY);
		left.add(lpanel, BorderLayout.CENTER);
		lpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Component horizontalStrut = Box.createHorizontalStrut((int) Math.round((0.0933382137628111 * dim.getWidth())));
		lpanel.add(horizontalStrut);

		Box verticalBox = Box.createVerticalBox();
		lpanel.add(verticalBox);

		Component verticalStrut = Box.createVerticalStrut((int) Math.round((0.2604166666666667 * dim.getHeight())));
		verticalBox.add(verticalStrut);

		JPanel panel1wurfel = new JPanel();
		panel1wurfel.setBackground(Color.LIGHT_GRAY);
		verticalBox.add(panel1wurfel);
		panel1wurfel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnWurfel1 = new JButton();
		btnWurfel1.setPreferredSize(d);
		btnWurfel1.setBorder(null);
		btnWurfel1.setBackground(Color.LIGHT_GRAY);
		btnWurfel1.setIcon(wurfelIcon[1]);
		panel1wurfel.add(btnWurfel1);
		Component verticalStrut_1 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox.add(verticalStrut_1);

		JPanel panel2wurfel = new JPanel();
		panel2wurfel.setBackground(Color.LIGHT_GRAY);
		verticalBox.add(panel2wurfel);
		panel2wurfel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnWurfel2 = new JButton();
		btnWurfel2.setPreferredSize(d);
		btnWurfel2.setBorder(null);
		btnWurfel2.setBackground(Color.LIGHT_GRAY);
		btnWurfel2.setIcon(wurfelIcon[5]);
		panel2wurfel.add(btnWurfel2);

		Component verticalStrut_2 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox.add(verticalStrut_2);

		Component verticalStrut_4 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox.add(verticalStrut_4);

		Component horizontalStrut_2 = Box
				.createHorizontalStrut((int) Math.round((0.0933382137628111 * dim.getWidth())));
		lpanel.add(horizontalStrut_2);

		initBottom(dim, f);

		JPanel right = new JPanel();
		right.setBackground(Color.LIGHT_GRAY);
		hauptfenster.add(right, BorderLayout.EAST);
		right.setLayout(new BorderLayout(0, 0));

		JPanel rpanel = new JPanel();
		rpanel.setBackground(Color.LIGHT_GRAY);
		right.add(rpanel, BorderLayout.NORTH);
		rpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Component horizontalStrut_3 = Box
				.createHorizontalStrut((int) Math.round((0.0622254758418741 * dim.getWidth())));
		rpanel.add(horizontalStrut_3);

		Box verticalBox_1 = Box.createVerticalBox();
		rpanel.add(verticalBox_1);

		Component verticalStrut_3 = Box.createVerticalStrut((int) Math.round((0.0953125 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_3);

		panelSpieler = new JPanel();
		panelSpieler.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panelSpieler);

		lblSpN = new JLabel();
		lblSpN.setFont(f);
		lblSpN.setText(bed.getSpielerAmZug().getName());
		panelSpieler.add(lblSpN);

		Component verticalStrut_12 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_12);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panel_1);

		lblSpGh = new JLabel(bed.getSpielerAmZug().getGuthaben() + " €");
		lblSpGh.setFont(f);
		panel_1.add(lblSpGh);

		Component verticalStrut_13 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_13);

		JPanel panel_5 = new JPanel();
		verticalBox_1.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblSpGh.setFont(f);
		lblRunde = new JLabel("Runde " + bed.getAktuelleRunde() + " / " + runden);
		panel_5.setBackground(Color.LIGHT_GRAY);
		panel_5.add(lblRunde);

		Component verticalStrut_8 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_8);

		Component verticalStrut_9 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_9);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		kaufenButton = new JButton("Kaufen");
		kaufenButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		kaufenButton.setForeground(Color.BLACK);
		kaufenButton.setFont(f);
		kaufenButton.setBackground(Color.LIGHT_GRAY);
		kaufenButton.addActionListener(evh);
		panel.add(kaufenButton);

		Component verticalStrut_11 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_11);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		bauenButton = new JButton("Bauen");
		bauenButton.setFont(f);
		bauenButton.setForeground(Color.BLACK);
		bauenButton.setBackground(Color.LIGHT_GRAY);
		bauenButton.addActionListener(evh);
		bauenButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		panel_2.add(bauenButton);

		Component verticalStrut_5 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_5);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		verkaufenButton = new JButton("Verkaufen");
		verkaufenButton.setFont(f);
		verkaufenButton.setForeground(Color.BLACK);
		verkaufenButton.setBackground(Color.LIGHT_GRAY);
		verkaufenButton.addActionListener(evh);
		verkaufenButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		panel_3.add(verkaufenButton);

		Component verticalStrut_5w = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_5w);

		JPanel panel3wurfel = new JPanel();
		panel3wurfel.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panel3wurfel);
		panel3wurfel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		wurfelnButton = new JButton("Wurf");
		wurfelnButton.setFont(f);
		wurfelnButton.setBackground(Color.LIGHT_GRAY);
		wurfelnButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())), d.height));
		wurfelnButton.addActionListener(evh);
		panel3wurfel.add(wurfelnButton);
		
	
		Component verticalStrut_6 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_6);

		Component verticalStrut_10 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_10);

		Component verticalStrut_7 = Box.createVerticalStrut((int) Math.round((0.0260416666666667 * dim.getHeight())));
		verticalBox_1.add(verticalStrut_7);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		verticalBox_1.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

//		JPanel panel_w = new JPanel();
//		panel_w.setBackground(Color.LIGHT_GRAY);
//		verticalBox_1.add(panel_w);
//		panel_w.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		kontrolleButton = new JButton("Spielzug beenden");
		kontrolleButton.setFont(f);
		kontrolleButton.setForeground(Color.BLACK);
		kontrolleButton.setBackground(Color.LIGHT_GRAY);
		kontrolleButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		kontrolleButton.addActionListener(evh);
		kontrolleButton.setEnabled(false);
		panel_4.add(kontrolleButton);
		
		
		
			

		Component horizontalStrut_1 = Box
				.createHorizontalStrut((int) Math.round((0.0622254758418741 * dim.getWidth())));
		rpanel.add(horizontalStrut_1);

		JPanel centeredPane = new JPanel();
		centeredPane.setBackground(Color.LIGHT_GRAY);
		hauptfenster.add(centeredPane, BorderLayout.CENTER);

		center = new JLabel();
		Dimension cd = new Dimension((int) Math.round((0.4516837481698389 * dim.getWidth())),
				(int) Math.round((0.4516837481698389 * dim.getWidth())));
//		center.setPreferredSize(cd);
		Image mpjpg;
		try {
			mpjpg = ImageIO.read(ResourceLoader.load("img/monopoly.png"));
			mpjpg = mpjpg.getScaledInstance((int) cd.getHeight(), (int) cd.getWidth(), java.awt.Image.SCALE_SMOOTH);
			center.setIcon(new ImageIcon(mpjpg));
		} catch (Exception e) {
			System.err.println("Spielfeldhintergrund  konnten nicht geladen werden!");
		}

		center.setBackground(Color.LIGHT_GRAY);

		center.setHorizontalAlignment(SwingConstants.CENTER);

		for (Integer i = 0; i < feld.length; i++) {
			// setJButtonSFText(feld[i]);
			feld[i].setFont(fFeld);
			centeredPane.add(feld[i]);
			centeredPane.add(feldo[i]);
			feld[i].addActionListener(evh);
			feld[i].addMouseListener(evh);
		}
		
		double a=0;
		ereignisButton = new JButton();
		if(dim.getHeight()==1440 && dim.getWidth()==2160){
			a=32;
		}	
		ereignisButton.setBounds((int) Math.round((x-(dim.getWidth()*0.1361639824304539))), (int) Math.round((y-dim.getHeight()*0.1432291666666667)+a),
				(int) Math.round(dim.getWidth()*0.0812591508052709), (int) Math.round(dim.getHeight()*0.0924479166666667-a*0.5));
		ereignisButton.setBackground(Color.lightGray);
		ereignisButton.setBorder(null);
		ereignisButton.addActionListener(evh);
		centeredPane.add(ereignisButton);
		

		centeredPane.setLayout(new BorderLayout(0, 0));
		centeredPane.add(center);

		evh.startGame();
		if(bed.getSpielerAmZug().isIstKi()){
			wurfelnButton.setEnabled(false);
			kontrolleButton.setEnabled(true);
		}else{
			wurfelnButton.setEnabled(true);
			kontrolleButton.setEnabled(false);
		}
		if(kiAnzahlSlider.getValue()==5){
			boolean play = true;
			while(play){
				play = Kigame();
				bed.kontrolleAbgeben();
			}
		}
	}
	
	/**
	 * Zug vom KI die automatisch Durchgeführt wird
	 */
	boolean Kigame(){
			if (Gui.bed.letzteRundeErreicht() || Gui.bed.irregulaeresEnde()) {
				bauenButton.setEnabled(false);
				kaufenButton.setEnabled(false);
				kontrolleButton.setEnabled(false);
				safeButton.setEnabled(false);
				verkaufenButton.setEnabled(false);
				wurfelnButton.setEnabled(false);
				lblRunde.setText("Runde " + Gui.bed.getAktuelleRunde() + " / " + bed.getMaximaleRundenAnzahl());
				lblSpN.setText("Spiel beendet");
				lblSpGh.setText("Spiel beendet");
				evh.initSpielfeld();
				return false;
			}else{
				bauenButton.setEnabled(false);
				verkaufenButton.setEnabled(false);
				kaufenButton.setEnabled(false);
				wurfelnButton.setEnabled(false);
				kontrolleButton.setEnabled(true);
				Gui.bed.zug();
			
				for (Besitzrechtkarten b : Gui.bed.getSpielerAmZug().getBesitzrechtkarten()) {
					if (b.getSpielfeld().getPosition() == Gui.bed.getSpielerAmZug().getPosition()) {
						feld[Gui.bed.getSpielerAmZug().getPosition()]
								.setBorder(new LineBorder(evh.getColor(Gui.bed.getSpielerAmZug().getID()),
										(int) Math.round(0.0021961932650073 * dim.getWidth())));
					}
				}				
					loadButton.setEnabled(true);
					lblRunde.setText("Runde " + Gui.bed.getAktuelleRunde() + " / " + bed.getMaximaleRundenAnzahl());
					lblSpN.setText("" + namen[Gui.bed.getSpielerAmZug().getID() - 1]);
					panelSpieler.setBorder(new LineBorder(evh.getColor(Gui.bed.getSpielerAmZug().getID()), 3));
					lblSpGh.setText(Gui.bed.getSpielerAmZug().getGuthaben() + " €");
					wurf = Gui.bed.getWurf();
					btnWurfel1.setIcon(wurfelIcon[(wurf[0] - 1)]);
					btnWurfel2.setIcon(wurfelIcon[(wurf[1] - 1)]);
					wurfelnButton.setText("" + wurf[2]);
					hauptfenster.setBorder(new LineBorder(evh.getColor(Gui.bed.getSpielerAmZug().getID()),
							(int) (Math.round(0.0016603221083455 * dim.getWidth()))));
					evh.initSpielfeld();
					return true;
			}
			
	}
	
	/**
	 * 
	 * @param x Angabe fuer die Schleife in der wir fuer das Spielfeld die Buttons setzen(x position auf dem Spielfeld)	
	 * @param y Angabe fuer die Schleife in der wir fuer das Spielfeld die Buttons setzen(y position auf dem Spielfeld)
	 * @param fx Angabe fuer die Schleife in der wir fuer das Spielfeld die Buttons setzen(breite für den Button)
	 * @param fxx Angabe fuer die Schleife in der wir fuer das Spielfeld die Buttons setzen(hoehe fuer den Button)
	 * Setzt die Buttons auf x und y auf dem Spielfeld und mit der angegebenen groesse
	 */

	void createFields() {
		feld = new JButtonSF[40];
		feldo = new JButton[40];
		// Color[] crStr = {
		// new Color(139,69,19),
		// new Color(102,178,255),
		// new Color(204,0,204),
		// new Color(255,128,0),
		// new Color(255,0,0),
		// new Color(255,255,0),
		// new Color(0,255,0),
		// new Color(0,0,255),
		// };

		for (int i = 0; i < feld.length; i++) {
			@SuppressWarnings("unused")
			Strasse temp = null;
			feld[i] = new JButtonSF(bed.getSpielbrett().geteinSpielfeld(i));
			if (bed.getSpielbrett().geteinSpielfeld(i) instanceof Strasse) {
				setJButtonSFText(feld[i]);
			}
			feld[i].setOpaque(false);
			feld[i].setContentAreaFilled(false);
			feld[i].setBorder(null);
			feld[i].addActionListener(evh);
			feldo[i] = new JButton();
			feldo[i].setContentAreaFilled(false);
			feldo[i].setBorder(null);

			if (i <= 10) {
				if (i == 0 || i == 10) {
					if (i == 0) {
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fx),
								(int) Math.round(fx));
						x = x - fxx;
					}
					if (i == 10) {
						x = x - fx;
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fx),
								(int) Math.round(fx));
					}
				} else {
					if (i == 9) {
						if (feld[i].getFeld().getClass().equals(Strasse.class)) {
							feld[i].setBounds((int) Math.round(x), (int) Math.round(y - 1) + (int) Math.round(fx / 4),
									(int) Math.round(fxx), (int) Math.round(fx + 1) - (int) Math.round(fx / 4));
							feldo[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
									(int) Math.round(fx / 4));
							temp = (Strasse) feld[i].getFeld();
							// feldo[i].setBackground(crStr[StrassenID.getFarbe(temp.getStrassenID())]);
						} else {

							feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
									(int) Math.round(fx) + 100);
						}
					} else {
						if (feld[i].getFeld().getClass().equals(Strasse.class)) {
							feld[i].setBounds((int) Math.round(x), (int) Math.round(y - 1) + (int) Math.round(fx / 4),
									(int) Math.round(fxx), (int) Math.round(fx + 1) - (int) Math.round(fx / 4));
							feldo[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
									(int) Math.round(fx / 4));
							temp = (Strasse) feld[i].getFeld();
							// feldo[i].setBackground(crStr[StrassenID.getFarbe(temp.getStrassenID())]);
						} else {
							feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
									(int) Math.round(fx));
						}
						x = x - fxx;
					}

				}

			}
			if (i > 10 && i <= 20) {
				if (i == 20) {
					y = y - fx;
					feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fx),
							(int) Math.round(fx));
				} else {
					y = y - fxx;
					if (feld[i].getFeld().getClass().equals(Strasse.class)) {
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round((fx / 4) * 3 + 3),
								(int) Math.round(fxx));
						feldo[i].setBounds((int) Math.round(x) + (int) Math.round((fx / 4) * 3), (int) Math.round(y),
								(int) Math.round((fx / 4)), (int) Math.round(fxx));
						temp = (Strasse) feld[i].getFeld();
						// feldo[i].setBackground(crStr[StrassenID.getFarbe(temp.getStrassenID())]);
					} else {
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fx),
								(int) Math.round(fxx));
					}
				}
			}
			if (i > 20 && i <= 30) {
				if (i == 30 || i == 21) {
					if (i == 30) {
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fx),
								(int) Math.round(fx));
						y = y + fx;
					}
					if (i == 21) {
						x = x + fx;
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
								(int) Math.round(fx + 2) - (int) Math.round(fx / 4));
						feldo[i].setBounds((int) Math.round(x), (int) Math.round(y) + (int) Math.round(fx / 4 * 3),
								(int) Math.round(fxx), (int) Math.round(fx / 4));
						temp = (Strasse) feld[i].getFeld();
						// feldo[i].setBackground(crStr[StrassenID.getFarbe(temp.getStrassenID())]);
						x = x + fxx;
					}

				} else {
					if (feld[i].getFeld().getClass().equals(Strasse.class)) {
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
								(int) Math.round(fx + 2) - (int) Math.round(fx / 4));
						feldo[i].setBounds((int) Math.round(x), (int) Math.round(y) + (int) Math.round(fx / 4 * 3),
								(int) Math.round(fxx), (int) Math.round(fx / 4));
						temp = (Strasse) feld[i].getFeld();
						// feldo[i].setBackground(crStr[StrassenID.getFarbe(temp.getStrassenID())]);
					} else {
						feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fxx),
								(int) Math.round(fx));
					}
					x = x + fxx;
				}
			}
			if (i > 30) {
				if (feld[i].getFeld().getClass().equals(Strasse.class)) {
					feld[i].setBounds((int) Math.round(x) + (int) Math.round(fx / 4 - 3), (int) Math.round(y),
							(int) Math.round((fx / 4) * 3 + 3), (int) Math.round(fxx));
					feldo[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round((fx / 4)),
							(int) Math.round(fxx));
					temp = (Strasse) feld[i].getFeld();
					// feldo[i].setBackground(crStr[StrassenID.getFarbe(temp.getStrassenID())]);
				} else {
					feld[i].setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(fx),
							(int) Math.round(fxx));
				}
				y = y + fxx;
			}
		}
	}

	public IBediener getBed() {
		return bed;
	}

	/**
	 * 
	 * @param dim die Angegebene groesse des felds
	 * @param f schriftart zum anpassen  der Schrift
	 * initalisiert den Sued berreich der Gui
	 */
	private void initBottom(Dimension dim, Font f) {

		bottom.setPreferredSize(new Dimension(0, (int) Math.round((0.1041666666666667 * dim.getHeight()))));
		bottom.setBackground(Color.CYAN);
		bottom.setOpaque(true);
		BorderLayout bottomLayout = new BorderLayout();
		bottom.setLayout(bottomLayout);

		initBottomPannels(dim, f);

		hauptfenster.add(bottom, BorderLayout.SOUTH);

	}

	/**
	 * 
	 * @param dim Auflösung des Bildschirms
	 * @param f Schriftart
	 * Der mittlere Feld für die Textausgabe wird erstellt mit Anpassung für Auflösung und wird hinzugefügt
	 */
	private void initBottomPannelCenter(Dimension dim, Font f) {

		FlowLayout fLayoutBottomCenter = new FlowLayout(FlowLayout.LEFT);
		bottom_center.setLayout(fLayoutBottomCenter);

		southText.setEditable(false);
		southText.setFont(f);

		DefaultCaret caret = (DefaultCaret) southText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scroll = new JScrollPane(southText);
		scroll.setPreferredSize(new Dimension((int) Math.round((0.4743777452415813 * dim.getWidth())),
				(int) Math.round((0.09765625 * dim.getHeight()))));

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		bottom_center.add(scroll);

	}
	/**
	 * 
	 * @param dim angegebene groesse des Bildschirms
	 * @param f schriftart
	 * initalisiert den east berreich des bottompanels (Speichern laden beenden button)
	 */
	private void initBottomPannelEast(Dimension dim, Font f) {

		safeButton.setText("Spiel speichern");
		safeButton.setFont(f);
		safeButton.setForeground(Color.BLACK);
		safeButton.setBackground(Color.LIGHT_GRAY);
		safeButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		safeButton.addActionListener(evh);
		safeButton.setEnabled(false);

		loadButton.setText("Spiel laden");
		loadButton.setFont(f);
		loadButton.setForeground(Color.BLACK);
		loadButton.setBackground(Color.LIGHT_GRAY);
		loadButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		loadButton.addActionListener(evh);
		loadButton.setEnabled(false);

		exitButton.setText("Spiel Beenden");
		exitButton.setFont(f);
		exitButton.setForeground(Color.BLACK);
		exitButton.setBackground(Color.GRAY);
		exitButton.addActionListener(evh);
		exitButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));

		mailButton = new JButton("Spielstand senden");
		mailButton.setFont(f);
		mailButton.setForeground(Color.BLACK);
		mailButton.setBackground(Color.LIGHT_GRAY);
		mailButton.setPreferredSize(new Dimension((int) Math.round((0.109809663250366 * dim.getWidth())),
				(int) Math.round((0.0390625 * dim.getHeight()))));
		mailButton.addActionListener(evh);	
		
		bottom_east.add(safeButton);
		bottom_east.add(loadButton);
		bottom_east.add(mailButton);
		bottom_east.add(exitButton);
	}
	/**
	 * 
	 * @param dim angegebene groesse des Bildschirms
	 * @param f die Schriftart
	 * Passt die Schriftart auf die Bildschrimgröße an
	 */
	private void initBottomPannels(Dimension dim, Font f) {

		
		bottom_east.setPreferredSize(new Dimension((int) Math.round((0.2562225475841874 * dim.getWidth())), 0));
		bottom_west.setPreferredSize(new Dimension((int) Math.round((0.2562225475841874 * dim.getWidth())), 0));

		bottom_east.setBackground(Color.LIGHT_GRAY);
		bottom_west.setBackground(Color.LIGHT_GRAY);
		bottom_center.setBackground(Color.LIGHT_GRAY);

		bottom.add(bottom_east, BorderLayout.EAST);
		bottom.add(bottom_west, BorderLayout.WEST);

		lblbwStrasse = new JLabel("Strasse");
		lblbwStrasse.setFont(new Font(f.getFamily(), f.getStyle(), f.getSize() + 3));
		lblbwStrasse.setHorizontalAlignment(SwingConstants.CENTER);
		bottom_west.add(lblbwStrasse);
		
		
		bottom.add(bottom_center, BorderLayout.CENTER);
		

		initBottomPannelCenter(dim, f);
		initBottomPannelEast(dim, f);

	}
	/**
	 * 
	 * @param event
	 * Dialog für bestimmte Bestätigungen aufrufen
	 */
	public void initYNDialog(String event) {
		ja.addActionListener(evh);
		nein.addActionListener(evh);
		JButton[] options = { ja, nein };
		JOptionPane.showOptionDialog(hauptfenster, event, "Bestätigen", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	/**
	 * 
	 * @param dSpieler dimension des zu ladenden Spielers
	 * Dient zum laden der Bilder für die Häuser
	 */
	private void ladeHausImage(Dimension dSpieler) {
		hausIcon = new ImageIcon[3];
		Image temp;
		for (int i = 0; i < hausIcon.length; i++) {
			temp = null;
			String src = "img/haus" + i + ".png";
			int size = (int) Math.round(0.0124450951683748 * dSpieler.getWidth());
			try {
				temp = ImageIO.read(ResourceLoader.load(src));
				temp = temp.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
				hausIcon[i] = new ImageIcon(temp);
			} catch (Exception e) {
				System.err.println("Haus Icons konnten nicht geladen werden!");
			}
		}

	}
	/**
	 * 
	 * @param dSpieler dimension des zu ladenden Spielers
	 * 
	 * Dient zum laden der Spielericons
	 */
	private void ladeSpielerImage(Dimension dSpieler) {
		spielerIcon = new ImageIcon[31];
		Image temp;
		for (int i = 0; i < spielerIconNumInt.length; i++) {
			temp = null;
			String src = "img/spieler" + spielerIconNumInt[i] + ".png";
			int size = (int) Math.round((dSpieler.getWidth() * 0.0607613469985359) / 2);
			try {
				temp = ImageIO.read(ResourceLoader.load(src));
				temp = temp.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
				spielerIcon[i] = new ImageIcon(temp);
			} catch (Exception e) {
				System.err.println("Spieler Icons konnten nicht geladen werden!");
			}
		}
	}
	/**
	 * 
	 * @param dimension des zu ladenden Wuerfels
	 * Dient zum laden der Wuerfelbilder
	 */
	private void ladeWurfelImage(Dimension d) {
		wurfelIcon = new ImageIcon[6];
		Image temp;
		for (int i = 0; i < wurfelIcon.length; i++) {
			temp = null;
			String src = "img/wurfel" + i + ".png";
			try {
				temp = ImageIO.read(ResourceLoader.load(src));
				temp = temp.getScaledInstance(d.width, d.height, java.awt.Image.SCALE_SMOOTH);
				wurfelIcon[i] = new ImageIcon(temp);
			} catch (Exception e) {
				System.err.println("Wuerfeln konnten nicht geladen werden!");
			}
		}
	}
	/**
	 * 
	 * @param das Interface welches gesetzt werden soll
	 */
	public void setBed(IBediener bed) {
		Gui.bed = bed;
	}
	/**
	 * dient zum festlegen der Konsole (auf Loggingfeld umleiten)
	 */
	private void setConsole() {
		PrintStream out = new PrintStream(new TextAreaOutputStream(southText));
		System.setOut(out);
		System.setErr(out);
	}

	/**
	 * S
	 * @param sf
	 * Die Texte der Felder auf die Buttongröße anpassen
	 */
	public void setJButtonSFText(JButtonSF sf) {
		if (sf.getFeld().getName().length() > 9) {
			int trenner = 0;
			if (sf.getFeld().getClass().equals(Strasse.class) || sf.getFeld().getClass().equals(Bahnhof.class)
					|| sf.getFeld().getClass().equals(FreiParken.class)) {
				trenner = 7;
			}
			if (sf.getFeld().getClass().equals(Ereignisfeld.class)
					|| sf.getFeld().getClass().equals(Versorgungswerk.class)) {
				trenner = 4;
			}
			if (sf.getFeld().getName().equals("Einkommenssteuer")) {
				trenner = 9;
			}
			if (sf.getFeld().getClass().equals(GeheInsGefaengnis.class)) {
				trenner = 10;
			}
			if (sf.getFeld().getName().equals("Rathausplatz") || sf.getFeld().getName().equals("Opernplatz")
					|| sf.getFeld().getName().equals("Schlossallee")) {
				trenner = 5;

			}
			String anfang = sf.getFeld().getName();
			char[] mitte = anfang.toCharArray();
			char[] fertig1 = new char[mitte.length - trenner];
			char[] fertig2 = new char[trenner];

			int b = 0;
			for (int a = 0; a < mitte.length; a++) {
				if (a < mitte.length - trenner) {
					fertig1[a] = mitte[a];
				} else {
					fertig2[b] = mitte[a];
					b++;
				}
			}
			String temp1 = new String(fertig1);
			String temp2 = new String(fertig2);
			sf.setText("<html><center>" + temp1 + "<br>" + temp2 + "</center></html>");
			sf.setHorizontalTextPosition(SwingConstants.CENTER);
			sf.setVerticalTextPosition(SwingConstants.CENTER);
		} else {
			sf.setText(sf.getFeld().getName());
			sf.setHorizontalTextPosition(SwingConstants.CENTER);
			sf.setVerticalTextPosition(SwingConstants.CENTER);
		}
	}

}