package graphik;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import lib.Karte;

public class Spieler extends JPanel {
	//Buttons mit Bildern der Karten
	private ArrayList<KartenButton> karten;
	
	//speichert die gespielte Karte
	private Karte gespielt;
	
	public Spieler() {
		super(new FlowLayout());
		
		karten = new ArrayList<KartenButton>();		
	}
	
	/**
	 * Aktualisiert die Karten des Spielers
	 * @param model
	 */
	public void update(ArrayList<Karte> model) {
		ArrayList<Karte> spielerkarten = model;
		
		for(int i = 0; i < spielerkarten.size(); i++) {
			if(spielerkarten.get(i) == null) {
				//Fügt einen neuen Knopf hinzu
				karten.add(new KartenButton(spielerkarten.get(i)));
				this.add(karten.get(i));
				//Passt die Größe der Karten an
				karten.get(i).setSize(this.getWidth() / 6, this.getHeight());
				karten.get(i).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						karteSpielen(evt);
					}
				});
				//Die Karten können nicht gewählt werden, wenn man nicht an der Reihe ist
				karten.get(i).setEnabled(false);
			} else
				karten.get(i).setzeKarte(spielerkarten.get(i));
			
		}
	}	
	
	private void karteSpielen(ActionEvent evt) {
		//Das Signal kommt auf jeden Fall von einem KartenButton
		KartenButton k = (KartenButton) evt.getSource();
		
		//Speichert die gewählte Karte und lässt keine neue Auswahl zu
		gespielt = k.gibKarte();
		aktiviert(false);
	}
	
	public Karte spiel() {
		gespielt = null;
		aktiviert(true);
		while(gespielt == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return gespielt;
	}
	
	private void aktiviert(boolean an) {
		for(int i = 0; i < karten.size(); i++) {
			karten.get(i).setEnabled(an);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = 0; i < karten.size(); i++) {
			karten.get(i).setSize(this.getWidth() / 6, this.getHeight());
		}
	}
	
}
