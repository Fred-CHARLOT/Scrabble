
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
		//ImageIcon image = new ImageIcon("etoile.png");
		//cases[0][1].setIcon(image);
	}
		
	public void affichage(int serveurOuclient) {
		cases = new JButton [15][15];
		if (serveurOuclient==0) fenetre1=new JFrame("Scrabble Serveur by Houssem, Fred & JB ");	
		else fenetre1=new JFrame("Scrabble Client by Houssem, Fred & JB " +chevalet.caseCourante);	
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
		
		//plateau
		remplirCases(Plateau.plateau);
		
	}
	
	
	public void tableauBoutons() {
			for ( int ligne=0  ; ligne< 15;ligne++) {
			for (int colonne=0;colonne<15; colonne++) {
				cases[ligne][colonne]= new JButton();
				panneau1.add(cases[ligne][colonne]);
				cases[ligne][colonne].addActionListener(this);
			}			
		}	
			
	}
	
	public void fontCaseCentrale(int i) {
		Font font=new Font("Arial", Font.BOLD,i);
		cases[7][7].setFont(font); 
	}
	
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
	
	//gestion de la grille	
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
									else if (caseGrilleSelectionnee(ligne,colonne)) { //on veut remettre dans le chevalet
										int position = 	retrouveCaseDansCoup(ligne,colonne); 
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

// predicat et fonctions de gestion des actions sur la grille
	private boolean caseChevaletSelectionnee(int ligne,int colonne) {
		return ((chevalet.caseCourante!=7)&& caseLibre(ligne,colonne));
	}
	private boolean isCaseCentrale(int ligne,int colonne) {
		return ((ligne==7)&&(colonne==7));
	}
	
	private boolean isJoker(int indice) {
		return (chevalet.coup.get(indice).valeur==0);
	}	
	
	private void joker(int ligne, int colonne) {
		blanc =JOptionPane.showInputDialog("Quelle lettre veux tu mettre?");
		chevalet.coup.get(chevalet.coup.size()-1).lettre=blanc;
		chevalet.coup.get(chevalet.coup.size()-1).affichage=SacJeton.AffChev(blanc.charAt(0), 0);
	}
	
	private void affichageChevalet() {
		chevalet.cases[chevalet.caseCourante].setText("");
		chevalet.cases[chevalet.caseCourante].setBackground(null);
		chevalet.reglette[chevalet.caseCourante]="";
		chevalet.caseCourante=7;		
	}
	
	private void creationCaseCourante(int ligne, int colonne) {
		chevalet.coup.add(new CaseCourante(ligne, colonne, cases[ligne][colonne].getText(),chevalet.cases[chevalet.caseCourante].getText()));
	}
	
	private void affichageGrille(int ligne, int colonne, String texte) {
		cases[ligne][colonne].setText(texte);	
	}
	
	private boolean caseGrilleSelectionnee(int ligne,int colonne) {
		return ((chevalet.caseCourante==7)&& !caseLibre(ligne,colonne));
	}
	
	boolean caseLibre(int ligne, int colonne) { 
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
	
	
	int retrouveCaseDansCoup(int ligne, int colonne) {
		for (var i : chevalet.coup) {
			if (i.ligne ==ligne && i.colonne ==colonne) return chevalet.coup.indexOf(i);	
		}
		 return -1;
	}
	
	private void remiseDansChevalet(int position, String lettre) {
		chevalet.reglette[position]=lettre; 	//on met dans la reglette									
		chevalet.cases[position].setText(lettre);//  on met dans le chevalet		
	}
		
}


		
		
		
	