
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
/// EVENENEMENT
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GestionGrille  implements ActionListener {
	JButton cases [][];  //creation du tableau de bouttons
	JFrame	fenetre1;
	JPanel panneau1;
	GridLayout disposition1;
	int serveurOuClient;
	PartieClient partieClient;
	Partie partie;
	Chevalet chevalet;
	
//Constructeurs	
	GestionGrille (int serveurOuclient,Chevalet chevalet){   
		this.chevalet=chevalet;	
		affichage(serveurOuclient);
			
		}
	
	
	
	//Cas où on veut transmettre la partie
/*	
	GestionGrille (int serveurOuclient,PartieClient partie){
		partieClient=partie;	
		affichage(serveurOuclient);
			
		}
	
	GestionGrille (int serveurOuclient,Partie partie){
	this.partie=partie;	
	affichage(serveurOuclient);
		
	}
	
	*/
	
	public void affichage(int serveurOuclient) {
		cases = new JButton [15][15];
		if (serveurOuclient==0) fenetre1=new JFrame("Scrabble Serveur by Houssem, Fred & JB ");	
		else fenetre1=new JFrame("Scrabble Client by Houssem, Fred & JB ");	
		fenetre1.setSize(850, 750) ;
		fenetre1.setLocationRelativeTo(null);//pour centrer la fenetre
		fenetre1.setVisible(true);
		
		//Grille
		panneau1 = new JPanel();
		disposition1 = new GridLayout(15, 15); 
		panneau1.setLayout(disposition1);
		fenetre1.add(panneau1);
		
		
		//Scores
		JPanel panneau3 = new JPanel();
		GridLayout disposition3 = new GridLayout(1, 2); 
		panneau3.setLayout(disposition3);
		panneau3.setSize(850, 1000);
		fenetre1.add("North",panneau3);
				JButton score1 = new JButton("Joueur 1 : 230");
		JButton score2 = new JButton("Joueur 2 : 145");
		panneau3.add(score1);
		panneau3.add(score2);
				
		tableauBoutons();
		
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
	
	
		
	
		public void actionPerformed(ActionEvent événement)  { /// EVENENEMENT
		 
				JButton leBouton = (JButton) événement.getSource(); /// EVENENEMENT 
				
				for ( int ligne=0  ; ligne< 15;ligne++) {
				for (int colonne=0;colonne<15; colonne++) {	
					if (leBouton==cases[ligne][colonne])  {							
						if ((chevalet.caseCourante!=7)&& caseLibre(ligne,colonne)) { //on a cliqué sur une lettre du chevalet	
						if ((ligne==7)&&(colonne==7))fontCaseCentrale(15); //gestion de la taille de la police de la case centrale
						chevalet.coup.add(new CaseCourante(ligne, colonne, cases[ligne][colonne].getText(),chevalet.cases[chevalet.caseCourante].getText()));	
						cases[ligne][colonne].setText(chevalet.cases[chevalet.caseCourante].getText() );							
						chevalet.cases[chevalet.caseCourante].setText("");chevalet.cases[chevalet.caseCourante].setBackground(null);
						chevalet.caseCourante=7;
						
						}
						else if ((chevalet.caseCourante==7)&& !caseLibre(ligne,colonne)) { //on n'a pas cliqué sur une lettre du chevalet
						int position = 	retrouveCase(ligne,colonne);				//est ce un coup courant et si oui en quelle position
						if (position ==-1) break;  //si c'est un coup joué un tour précédent
							for (int i=0; i<7;i++) { //si c'est un coup courant, on remet la lettre dans le chevalet
								if (chevalet.cases[i].getText().equals("")){ /// à la premiere case vide
									chevalet.cases[i].setText(cases[ligne][colonne].getText());   //  on met dans le chevalet
									if ((ligne==7)&&(colonne==7))fontCaseCentrale(50);    // gerer le probleme de la police de la case centrale
									cases[ligne][colonne].setText(chevalet.coup.get(position).bonus);//on met sur la grille
									chevalet.coup.remove(retrouveCase(ligne, colonne));//on efface le coup de la lise de coups courants
									break;
								}
							}
						}
					}
				}	
				}
				
		}
		//
		
	

boolean caseLibre(int ligne, int colonne) { 
	return (cases[ligne][colonne].getText().equals("") || isBonus(ligne,colonne));
}


 boolean isBonus(int ligne, int colonne) {
	return (cases[ligne][colonne].getText().equals("MT")||cases[ligne][colonne].getText().equals("LT")||cases[ligne][colonne].getText().equals("LD")||cases[ligne][colonne].getText().equals("MD")
			||cases[ligne][colonne].getText().equals("*"));
}

int retrouveCase(int ligne, int colonne) {
	for (var i : chevalet.coup) {
		if (i.ligne ==ligne && i.colonne ==colonne) return chevalet.coup.indexOf(i);	
	}
	 return -1;
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
		
		
}


		
		
		
	