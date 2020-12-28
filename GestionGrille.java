
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
	//PartieClient partieClient;
	//Partie partie;
	Chevalet chevalet;
	JButton score2;
	JButton score1,jetonsRestant;

	//Constructeur	
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
	
	
	//
	
		public void actionPerformed(ActionEvent �v�nement)  { /// EVENENEMENT
		 
				JButton leBouton = (JButton) �v�nement.getSource(); /// EVENENEMENT 
				
				for ( int ligne=0  ; ligne< 15;ligne++) {
				for (int colonne=0;colonne<15; colonne++) {	
					if (leBouton==cases[ligne][colonne])  {							
						if ((chevalet.caseCourante!=7)&& caseLibre(ligne,colonne)) { //on a cliqu� sur une lettre du chevalet							
							if ((ligne==7)&&(colonne==7))fontCaseCentrale(15); //gestion de la taille de la police de la case centrale							
							chevalet.coup.add(new CaseCourante(ligne, colonne, cases[ligne][colonne].getText(),chevalet.cases[chevalet.caseCourante].getText()));
							cases[ligne][colonne].setText(chevalet.cases[chevalet.caseCourante].getText() );
							if (chevalet.coup.get(chevalet.coup.size()-1).valeur==0) {
								blanc =JOptionPane.showInputDialog("Quelle lettre veux tu mettre?");
								cases[ligne][colonne].setText(blanc);
								chevalet.coup.get(chevalet.coup.size()-1).lettre=blanc;
							}							
						chevalet.cases[chevalet.caseCourante].setText("");
						chevalet.cases[chevalet.caseCourante].setBackground(null);
						chevalet.reglette[chevalet.caseCourante]="";
						chevalet.caseCourante=7;
						
					}
						else if ((chevalet.caseCourante==7)&& !caseLibre(ligne,colonne)) { //on n'a pas cliqu� sur une lettre du chevalet
						int position = 	retrouveCase(ligne,colonne);				//est ce un coup courant et si oui en quelle position
						if (position ==-1) break;  // c'est un coup jou� un tour pr�c�dent
							for (int i=0; i<7;i++) { //si c'est un coup courant, on va remettre la lettre dans le chevalet
								if (chevalet.cases[i].getText().equals("")){ /// � la premiere case vide									
									if (chevalet.coup.get(position).valeur==0) {//cas d'un joker
										chevalet.reglette[i]=chevalet.coup.get(position).lettre;
										chevalet.cases[i].setText(chevalet.coup.get(position).lettre);
									}
									else {			
										chevalet.reglette[i]=cases[ligne][colonne].getText();//on met dans la reglette
										chevalet.cases[i].setText(cases[ligne][colonne].getText());   //  on met dans le chevalet
									}
									
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
		
	public int caseVideChevalet() {
		int i;
		for (i=0; i<7;i++) {
			if (chevalet.cases[i].getText().equals(""))break;
		}
		return i;
	}

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


		
		
		
	