
import java.awt.Color;
import java.awt.GridLayout;
/// EVENENEMENT
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Chevalet  implements ActionListener {
	JButton cases [];  //creation du tableau de 7 lettres
	ArrayList <CaseCourante> coup= new ArrayList <CaseCourante>() ;
	String reglette []= new String [7] ;
	JButton valider, passer, échanger,permuter;
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

		cases[0].setText("<html>P<sub>     3</sub></html>"); // cette partie est à modifier: j'ai mis 7 lettres au hasard, il faudra les tirer dans le sac.
		cases[1].setText("<html>A<sub>     1</sub></html>");
		cases[2].setText("<html>T<sub>     1</sub></html>");
		cases[3].setText("<html>R<sub>     1</sub></html>");
		cases[4].setText("<html>R<sub>     1</sub></html>");
		cases[5].setText("<html>I<sub>     1</sub></html>");	
		cases[6].setText("<html>E<sub>     1</sub></html>");
		
		
		
		
		panneau2 = new JPanel();
		disposition2 = new GridLayout(1, 4); 
		panneau2.setLayout(disposition2);
		fenetre1.add("South",panneau2);
		valider = new JButton("valider");
		passer = new JButton("passer");
		échanger = new JButton("échange");
		permuter = new JButton("permuter");
		panneau2.add(valider);
		panneau2.add(passer);
		panneau2.add(échanger);
		panneau2.add(permuter);
		échanger.addActionListener(this);
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
	
	
	
	
	
	

	
	
	public void actionPerformed(ActionEvent événement)  { /// EVENENEMENTactionPerformed
		
		JButton leBouton = (JButton) événement.getSource(); /// EVENENEMENT 
		
		
		if ((leBouton==valider)&& (Partie.joueurAjoué==false)&&(serveurOuClient==0)  ){			
			 //à rajouter  : et que le positionnement est valide
				//extraire les mots
				//valider les mots
				//calculer le score
				//envoyer le tableau: pour l'instant c'est en static: pas gênant ici mais gênant quand on passe
				
				Partie.joueurAjoué=true;
				valider.setBackground(null); //changer la couleur du bouton pour passer la main
		}	
		
		if ((leBouton==valider)&& (PartieClient.joueurAjoué==false)&& (serveurOuClient==1)) { 
			PartieClient.joueurAjoué=true;  
			valider.setBackground(null);
		 }
			
		
		
		if ((leBouton==passer)&& (Partie.joueurAjoué==false)&&(serveurOuClient==0)  ) {			
			videCoup();
			Partie.joueurAPassé=true;
			valider.setBackground(null);
			Partie.joueurAjoué=true;
		}	
		
		if ((leBouton==passer)&& (PartieClient.joueurAjoué==false)&&(serveurOuClient==1)  ) {			
			videCoup();
			PartieClient.joueurAPassé=true;
			valider.setBackground(null);
			PartieClient.joueurAjoué=true;
		}	
		
		
		
		
		if (leBouton==échanger) {
		System.out.println("rhooo, t'as rien trouvé?");		// à modifier aussi
		}
		
		if (leBouton==permuter) {
							
			
			// à modifier aussi
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

String[] ModifAleat() {
	
	ArrayList <String> a = new ArrayList(Arrays.asList(this.cases));
	String[] b = new String[7];
	
	for (int i=0;i<7;i++) {
		Random tirage= new Random();
		int d = tirage.nextInt(7-i);
		b[i] = a.get(d);
		a.remove(d);
		}
	
	return b;
	}





	
	
}	
	