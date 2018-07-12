package monopolyGui;

import javax.swing.JButton;

import monopoly.Spieler;
import monopoly.Spielfeld;

public class JButtonSF extends JButton {

	/**
	 * SerialisierungsID
	 */
	private static final long serialVersionUID = -2629968296963613365L;
	
	Spielfeld feld = null;
	Spieler[] sp = new Spieler[5];

	public JButtonSF(Spielfeld feld) {
		setFeld(feld);
	}

	public Spielfeld getFeld() {
		return feld;
	}

	public Spieler[] getSp() {
		return sp;
	}

	public void setFeld(Spielfeld feld) {
		this.feld = feld;
	}

	public void setSp(Spieler[] sp) {
		this.sp = sp;
	}

}
