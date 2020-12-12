
import java.awt.Color;
import java.awt.GridLayout;
/// EVENENEMENT
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Chevalet  implements ActionListener {
	JButton cases [];  //creation du tableau de 7 lettres
	ArrayList <CaseCourante> coup= new ArrayList <CaseCourante>() ;
	JButton valider, passer, �changer,permuter;
	JFrame	fenetre1;
	JPanel panneau1,panneau2;
	GridLayout disposition1, disposition2;
	int caseCourante=7;
	int serveurOuClient;
	
	Chevalet (int ServeurClient){
		serveurOuClient=ServeurClient;
		cases = new JButton [7];
		if ( serveurOuClient==0) fenetre1=new JFrame("Scrabble Serveur by Houssem, Fred and JB");	
		else fenetre1=new JFrame("Scrabble Client by Houssem, Fred and JB");
		fenetre1.setSize(800, 100) ;
		fenetre1.setLocationRelativeTo(null);//pour centrer la fenetre
		fenetre1.setVisible(true);
		fenetre1.setAlwaysOnTop(true);
		panneau1 = new JPanel();
		disposition1 = new GridLayout(1, 7); 
		panneau1.setLayout(disposition1);
		fenetre1.add("Center",panneau1);



		for ( int colonne=0  ; colonne< 7;colonne++) {		
			cases[colonne]= new JButton();
			panneau1.add(cases[colonne]);
			cases[colonne].addActionListener(this);
		}			

		cases[0].setText("P"); // cette partie est � modifier: j'ai mis 7 lettres au hasard, il faudra les tirer dans le sac.
		cases[1].setText("A");
		cases[2].setText("T");
		cases[3].setText("S");
		cases[4].setText("S");
		cases[5].setText("I");	
		cases[6].setText("A");
		
		
		
		
		panneau2 = new JPanel();
		disposition2 = new GridLayout(1, 4); 
		panneau2.setLayout(disposition2);
		fenetre1.add("South",panneau2);
		valider = new JButton("valider");
		passer = new JButton("passer");
		�changer = new JButton("�change");
		permuter = new JButton("permuter");
		panneau2.add(valider);
		panneau2.add(passer);
		panneau2.add(�changer);
		panneau2.add(permuter);
		�changer.addActionListener(this);
		permuter.addActionListener(this);
		valider.addActionListener(this);
		passer.addActionListener(this);
		
		
	}
	
/*  Si besoin d'utiliser chevalet dans "partie"
	Chevalet(int serveurOuClient,Partie partie ){
		this.partie=partie;
		affichage(serveurOuClient);
	}
	
		Chevalet(int serveurOuClient,PartieClient partie ){
		this.partieClient=partie;
		affichage(serveurOuClient);
}
	
public void affichage(int serveurOuClient) {
	
	
	
}
	*/	
	
	
	
	
	
	

	
	
	public void actionPerformed(ActionEvent �v�nement)  { /// EVENENEMENTactionPerformed
		
		JButton leBouton = (JButton) �v�nement.getSource(); /// EVENENEMENT 
		
		
		if ((leBouton==valider)&& (Partie.joueurAjou�==false)&&(serveurOuClient==0)  ){			
			 //� rajouter  : et que le positionnement est valide
				//extraire les mots
				//valider les mots
				//calculer le score
				//envoyer le tableau: pour l'instant c'est en static: pas g�nant ici mais g�nant quand on passe
				
				Partie.joueurAjou�=true;
				valider.setBackground(null); //changer la couleur du bouton pour passer la main
		}	
		
		if ((leBouton==valider)&& (PartieClient.joueurAjou�==false)&& (serveurOuClient==1)) { 
			PartieClient.joueurAjou�=true;  
			valider.setBackground(null);
		 }
			
		
		
		if ((leBouton==passer)&& (Partie.joueurAjou�==false)&&(serveurOuClient==0)  ) {			
			Partie.joueurAPass�=true;
			valider.setBackground(null);
			Partie.joueurAjou�=true;
		}	
		
		if ((leBouton==passer)&& (PartieClient.joueurAjou�==false)&&(serveurOuClient==1)  ) {			
			PartieClient.joueurAPass�=true;
			valider.setBackground(null);
			PartieClient.joueurAjou�=true;
		}	
		
		
		
		
		if (leBouton==�changer) {
		System.out.println("rhooo, t'as rien trouv�?");		// � modifier aussi
		}
		
		if (leBouton==permuter) {
			System.out.println("on permute");
				
			
			// � modifier aussi
			}
		
		
		
		
		
		//echange de cases. par des permutations. A faire plsu tard aussi en intercalant.
				
			for (int i = 0; i<7;i++) {
				if (leBouton==cases[i]){
					if (caseCourante==7) {cases[i].setBackground(Color.CYAN);caseCourante=i;}
					else {
						if (caseCourante==i){caseCourante=7;cases[i].setBackground(null);}
						else {permute(i,caseCourante);caseCourante=7;}
					}
					
				}	
				
			}		
		}		
	
	void permute(int i,int j) {
		String temp;
		cases[i].setBackground(null);cases[j].setBackground(null);
		temp=cases[i].getText();
		cases[i].setText(cases[j].getText())	;
		cases[j].setText(temp);	
		}
	
	
	
	
	
void videCoup()	{
		int compteur=0;
		for (var i : coup) {
			while (!cases[compteur].getText().equals(""))compteur++;
			cases[compteur].setText(i.lettre);
		}
	coup.clear();
	}
	
	
	
}	
	