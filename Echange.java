import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Echange extends JFrame implements ActionListener {
Chevalet chevalet;
JButton cases [],annuler, valider;
String lettresAChanger [];
	
	Echange(Chevalet chevalet){
		this.chevalet=chevalet;	
		setTitle("place dans cette fenêtre les lettres à échanger");
		cases = new JButton [7];
		setSize(800,100);
		setLocationRelativeTo(null);
		setVisible(false);
		setAlwaysOnTop(true);
		JPanel panneau = new JPanel();
		GridLayout disposition = new GridLayout(1, 7); 
		panneau.setLayout(disposition);
		add("Center",panneau);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		for ( int colonne=0  ; colonne< 7;colonne++) {		//création du tableau de JButton
			cases[colonne]= new JButton();
			panneau.add(cases[colonne]);
			cases[colonne].addActionListener(this);
		}	
			JPanel panneau2 = new JPanel();
			GridLayout disposition2 = new GridLayout(1, 2); 
			panneau2.setLayout(disposition2);
			add("South",panneau2);
			valider = new JButton("valider");
			annuler = new JButton("annuler");
			panneau2.add(valider);
			panneau2.add(annuler);	
			valider.addActionListener(this);
			annuler.addActionListener(this);		
	}
	
	public void actionPerformed(ActionEvent événement)  { /// EVENENEMENTactionPerformed
		
		JButton leBouton = (JButton) événement.getSource(); 
		
		for (int colonne=0;colonne<7; colonne++) {
			if (leBouton==cases[colonne])  {
				if ((chevalet.caseCourante!=7)&& cases[colonne].getText().equals("")) {					
					cases[colonne].setText(chevalet.cases[chevalet.caseCourante].getText() );
					chevalet.cases[chevalet.caseCourante].setText("");
					chevalet.cases[chevalet.caseCourante].setBackground(null);
					chevalet.reglette[chevalet.caseCourante]="";
					chevalet.caseCourante=7;
				}
				
				else if ((chevalet.caseCourante==7)&& !cases[colonne].getText().equals("")) {
					int i=chevalet.caseVide();
					chevalet.reglette[i]=cases[colonne].getText();
					chevalet.cases[i].setText(cases[colonne].getText());
					cases[colonne].setText("");
				}			
			}
		}
		
		if ((leBouton==valider) && (isNotVide()!=0)){                            //ne marche que pour le serveur
			setVisible(false);	
			chevalet.valider.setEnabled(true);
			chevalet.passer.setEnabled(true);
			
			lettresAChanger=new String[isNotVide()];					
			int count=0;
			for (int i=0;i<7;i++) {
				if (!cases[i].getText().equals("")) {
					lettresAChanger[count]=cases[i].getText();
					count++;
					cases[i].setText("");
				}
			}
			for (int i=0;i<lettresAChanger.length;i++) System.out.println(lettresAChanger[i]);                          
			if (chevalet.serveurOuClient==0) {Partie.joueurAjoue=true;Partie.joueurAchange=true;}
			else {PartieClient.joueurAjoue=true;PartieClient.joueurAChange=true;}
			chevalet.valider.setBackground(null);
			
		}
		
		if (leBouton==annuler) {
			setVisible(false);	
			chevalet.valider.setEnabled(true);
			chevalet.passer.setEnabled(true);
			for ( int colonne=0  ; colonne< 7;colonne++) {
				if (!cases[colonne].getText().equals("")){
					int i=chevalet.caseVide();
					chevalet.reglette[i]=cases[colonne].getText();
					chevalet.cases[i].setText(cases[colonne].getText());
					cases[colonne].setText("");					
				}
			}
			
		}
		
		
	}	
	
	public int isNotVide() {
		int count=0;
		for (int i=0;i<7;i++) {if (!cases[i].getText().equals(""))count++;}		
		return count;
	}
	
}
