
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
/// EVENENEMENT
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GestionGrille  implements ActionListener {
	JButton cases [][];  //creation du tableau de bouttons
	JFrame	fenetre1;
	JPanel panneau1;
	GridLayout disposition1;
	int serveurOuClient;
	String blanc;	
	Chevalet chevalet;
	JButton score2;
	JButton score1,jetonsRestant;

	
	GestionGrille (int serveurOuclient,Chevalet chevalet){   
		this.chevalet=chevalet;	
		affichage(serveurOuclient);
	}
		
	/**
	 * Gestion de l'affichage de la grille
	 * @param serveurOuclient pour différentier l'affichage du serveur  et du client
	 */
	public void affichage(int serveurOuclient) {
		cases = new JButton [15][15];
		if (serveurOuclient==0) fenetre1=new JFrame("Scrabble Serveur by Houssem, Fred & JB ");	
		else fenetre1=new JFrame("Scrabble Client by Houssem, Fred & JB " );	
		fenetre1.setSize(1200, 750) ;
		fenetre1.setLocationRelativeTo(null);//pour centrer la fenetre
		fenetre1.setVisible(true);
		fenetre1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		//Grille
		panneau1 = new JPanel();
		disposition1 = new GridLayout(15, 15); 
		panneau1.setLayout(disposition1);
		fenetre1.add(panneau1);
				
		//Scores
		JPanel panneau3 = new JPanel();
		GridLayout disposition3 = new GridLayout(1, 3); 
		panneau3.setLayout(disposition3);
		panneau3.setSize(850, 750);
		fenetre1.add("North",panneau3);
		score1 = new JButton("0");
		jetonsRestant=new JButton("il reste : 88 jetons");
		score2 = new JButton("0");
		panneau3.add(score1);		
		panneau3.add(jetonsRestant);
		panneau3.add(score2);		
		tableauBoutons();
		
		remplirCases(Plateau.plateau);
		
	}
	
	/**
	 * Creation du tableau 15*15 de JButton
	 */
	public void tableauBoutons() {
			for ( int ligne=0  ; ligne< 15;ligne++) {
			for (int colonne=0;colonne<15; colonne++) {
				cases[ligne][colonne]= new JButton();
				panneau1.add(cases[ligne][colonne]);
				cases[ligne][colonne].addActionListener(this);
			}			
		}	
			
	}
	
	/**
	 * Pour gerer l'affichege de la case centrale
	 * @param i
	 */
	public void fontCaseCentrale(int i) {
		Font font=new Font("Arial", Font.BOLD,i);
		cases[7][7].setFont(font); 
	}
	
	
	/**
	 * Affichage de la grille avec les bonnus.
	 * @param plateau le contenu de la grille. Soit des cases vides sans bonus(""), soit des bonus(MD,MT,LD,LT,*),
	 * soit les lettres posées sous forme html
	 */
	void remplirCases(String plateau[][]) {			
		if (plateau[7][7].equals("*"))fontCaseCentrale(50); else fontCaseCentrale(15);
		for (int i=0; i<15 ; i++){
		for (int j=0; j<15; j++){				
			this.cases[i][j].setText(plateau[i][j]);this.cases[i][j].setBackground(null);
			if (plateau[i][j].equals("*")) {this.cases[i][j].setBackground(Color.pink);continue;}
			if (plateau[i][j].equals("LT")) { this.cases[i][j].setBackground(Color.CYAN);continue;}
			if (plateau[i][j].equals("LD")) {this.cases[i][j].setBackground(Color.yellow);continue;}
			if (plateau[i][j].equals("MD")) { this.cases[i][j].setBackground(Color.pink);continue;}
			if (plateau[i][j].equals("MT")) { this.cases[i][j].setBackground(Color.red);continue;}				
		}
	}
}	
	
	/**
	 * Gestion des actions des boutons de la grille. Interaction avec le chevalet.
	 */
			public void actionPerformed(ActionEvent événement)  { 
			 
					JButton leBouton = (JButton) événement.getSource(); 
					for ( int ligne=0  ; ligne< 15;ligne++) {
						for (int colonne=0;colonne<15; colonne++) {	
							if (leBouton==cases[ligne][colonne])  {							
								if (caseChevaletSelectionnee(ligne,colonne)) { 	//on veut poser sur la grille					
									creationCaseCourante(ligne, colonne);
									if (isCaseCentrale(ligne, colonne))fontCaseCentrale(15); 	// gererla police de la case centrale						
									if (isJoker(chevalet.coup.size()-1)) joker(ligne, colonne);
									affichageGrille(ligne, colonne,chevalet.coup.get(chevalet.coup.size()-1).affichage);
									affichageChevalet();								
													}
									else if (isCaseGrilleSelectionnee(ligne,colonne)) { //on veut remettre dans le chevalet
										int position = 	chevalet.retrouveCaseDansCoup(ligne,colonne); 
										if (isOldLetter(position)) break;  // c'est un coup joué un tour précédent															
										int caseVideChevalet=chevalet.caseVide();		
										if (isJoker(position)) 	remiseDansChevalet(caseVideChevalet,SacJeton.AffChev(' ', 0));
														   else remiseDansChevalet(caseVideChevalet,cases[ligne][colonne].getText());	
										if (isCaseCentrale(ligne, colonne))fontCaseCentrale(50);  															
										affichageGrille(ligne, colonne, chevalet.coup.get(position).bonus);
										chevalet.coup.remove(position);//on efface le coup de la lise de coups courants									
									}
								}					
						}	
					}				
			}

// predicats et fonctions de gestion des actions sur la grille
	

	private boolean caseChevaletSelectionnee(int ligne,int colonne) {
		return ((chevalet.caseCourante!=7)&& isCaseLibre(ligne,colonne));
	}
	private boolean isCaseCentrale(int ligne,int colonne) {
		return ((ligne==7)&&(colonne==7));
	}
	
	private boolean isJoker(int indice) {
		return (chevalet.coup.get(indice).valeur==0);
	}	
	
	/**
	 * 	Gestion des jokers: choix de la lettre et affichage en majuscule.
	 */		
	private void joker(int ligne, int colonne) {
		blanc =JOptionPane.showInputDialog("Quelle lettre veux tu mettre?").toUpperCase();
		chevalet.coup.get(chevalet.coup.size()-1).lettre=blanc;
		chevalet.coup.get(chevalet.coup.size()-1).affichage=SacJeton.AffChev(blanc.charAt(0), 0);
	}
	
	/**
	 * Affichage d'une lettre dans le chevalet
	 */
	private void affichageChevalet() {
		chevalet.cases[chevalet.caseCourante].setText("");
		chevalet.cases[chevalet.caseCourante].setBackground(null);
		chevalet.reglette[chevalet.caseCourante]="";
		chevalet.caseCourante=7;		
	}
	
	/**
	 * Création d'une {@link CaceCourante} à partir des coordonnées sur la grilleen parametre.
	 *  On y stocke le bonus éventuel  et l'affichage courant du chevalet.
	 * @param ligne coordonnées du Jbutton sur lequel on veut poser la lettre.
	 * @param colonne
	 */
	private void creationCaseCourante(int ligne, int colonne) {
		chevalet.coup.add(new CaseCourante(ligne, colonne, cases[ligne][colonne].getText(),chevalet.cases[chevalet.caseCourante].getText()));
	}
	
	private void affichageGrille(int ligne, int colonne, String texte) {
		cases[ligne][colonne].setText(texte);	
	}
	
	private boolean isCaseGrilleSelectionnee(int ligne,int colonne) {
		return ((chevalet.caseCourante==7)&& !isCaseLibre(ligne,colonne));
	}
	
	boolean isCaseLibre(int ligne, int colonne) { 
		return (isCaseText(ligne, colonne, "") || isBonus(ligne,colonne));
	}

	 boolean isBonus(int ligne, int colonne) {
		return (isCaseText(ligne, colonne, "MT")||isCaseText(ligne, colonne, "LT")||isCaseText(ligne, colonne, "LD")
				||isCaseText(ligne, colonne, "MD")||isCaseText(ligne, colonne, "*"));
	}	
	
	 boolean isCaseText(int ligne, int colonne, String texte) {
		return cases[ligne][colonne].getText().equals(texte);
	}
	
	
	 private boolean isOldLetter(int position) {
		return(position ==-1);
	}
	
	
	private void remiseDansChevalet(int position, String lettre) {
		chevalet.reglette[position]=lettre; 	//on met dans la reglette									
		chevalet.cases[position].setText(lettre);//  on met dans le chevalet		
	}
		
}


		
		
		
	