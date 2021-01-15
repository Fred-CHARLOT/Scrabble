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

/**
 * 
 * 2e chevalet, avec moins d'options,
 *  permettant d'échanger des lettres avec de nouvelles lettres provenant du sac de Jetons..
 *
 */

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
	
	
	/**
	 * Gestion des boutons du 2e Chevalet.
	 * 
	 * 1) gestion des échanges entre les 2 chevalets. 
	 * 	caseCourante est la case sélectionnée sur le chevalet
	 * . Si elle est affectée à 7, c'est qu'aucune case ,n'est sélectionnée.
	 * 
	 *  2) Valider (s'il y a des lettres posées sur le 2e chevalet) on rempli le 
	 *  {@link String[]}   lettresAchanger avec les lettres posées sur le 2e chevalet
	 *  et on le désactive.
	 *  
	 *  3) annuler qui remet les lettres sur le chevalet et désactive le 2e chevalet
	 */
	
	
	public void actionPerformed(ActionEvent événement)  { 
		
		JButton leBouton = (JButton) événement.getSource(); 
		
		for (int colonne=0;colonne<7; colonne++) { //gestion des échanges entre les 2 chevalets
			if (leBouton==cases[colonne])  {
				if ((chevalet.caseCourante!=7)&& cases[colonne].getText().equals("")) {	//pour enlever une lettre du chevalet 				
					cases[colonne].setText(chevalet.cases[chevalet.caseCourante].getText() );//
					chevalet.cases[chevalet.caseCourante].setText("");					
					chevalet.cases[chevalet.caseCourante].setBackground(null);
					chevalet.reglette[chevalet.caseCourante]="";
					chevalet.caseCourante=7;
				}
				
				else if ((chevalet.caseCourante==7)&& !cases[colonne].getText().equals("")) {//pour remettre une lettre
					int i=chevalet.caseVide();
					chevalet.reglette[i]=cases[colonne].getText();
					chevalet.cases[i].setText(cases[colonne].getText());
					cases[colonne].setText("");
				}			
			}
		}
		
		if ((leBouton==valider) && (isNotVide()!=0)){                           
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
			chevalet.joueurAjoue=true;chevalet.joueurAchange=true;
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
	/**
	 * Compte combien il y a de cases non vides sur le 2e chevalet.
	 * @return {@link int} le nombre de cases non vides sur le 2e chevalet, 
	 * donc combien de lettre il faut échanger
	 */
	public int isNotVide() {
		int count=0;
		for (int i=0;i<7;i++) {if (!cases[i].getText().equals(""))count++;}		
		return count;
	}
	
}
