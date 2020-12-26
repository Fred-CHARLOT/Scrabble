
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
	JButton valider, passer, �changer,permuter;
	JFrame	fenetre1;
	JPanel panneau1,panneau2;
	GridLayout disposition1, disposition2;
	int caseCourante=7,jetonsAChanger=0;
	int serveurOuClient;
	String reglette[];
	
	Chevalet (int ServeurClient,String reglette [] ){
		serveurOuClient=ServeurClient;
		cases = new JButton [7];
		this.reglette=reglette;
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
		fenetre1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 


		for ( int colonne=0  ; colonne< 7;colonne++) {		//cr�ation du tableau de JButton
			cases[colonne]= new JButton();
			panneau1.add(cases[colonne]);
			cases[colonne].addActionListener(this);
		}			
		
		afficheReglette();
		
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
	
		
	public void actionPerformed(ActionEvent �v�nement)  { /// EVENENEMENTactionPerformed
		
		JButton leBouton = (JButton) �v�nement.getSource(); /// EVENENEMENT 
		
		
		if ((leBouton==valider)&& (Partie.joueurAjou�==false)&&(serveurOuClient==0)  ){			
			 	// � partir de la Array list "coup" :
				//v�rifier que le positionnement est valide.
			//Si c'est bon :
				//Extraire les mots de coup, les verifier dans un dictionnaire(message d'erreur avec le mot faux sinon?)
				//et si c'est bon, les mettre dans une array list de tableaux cases courantes.(chaque tableau est un mot)
				//calculer le score � l'aide de cette liste et d'EvalCoup.
			//sinon: renvoyer les jetons sur le chevalets(pour mettre coup.size � z�ro)
			//peut �tre pr�voir un message d'erreur, style les lettres ne sont pas align�es.
			
				jetonsAChanger=coup.size();
				Partie.joueurAjou�=true;
				valider.setBackground(null); //changer la couleur du bouton pour passer la main
		}	
		
		if ((leBouton==valider)&& (PartieClient.joueurAjou�==false)&& (serveurOuClient==1)) { 
		
			jetonsAChanger=coup.size();
			PartieClient.joueurAjou�=true;  
			valider.setBackground(null);
		 }
			
		
		
		if ((leBouton==passer)&& (Partie.joueurAjou�==false)&&(serveurOuClient==0)  ) {			
			videCoup();
			Partie.joueurAPass�=true;
			valider.setBackground(null);
			Partie.joueurAjou�=true;
		}	
		
		if ((leBouton==passer)&& (PartieClient.joueurAjou�==false)&&(serveurOuClient==1)  ) {			
			videCoup();
			PartieClient.joueurAPass�=true;
			valider.setBackground(null);
			PartieClient.joueurAjou�=true;
		}	
		
		
		
		
		if (leBouton==�changer) {
		System.out.println("rhooo, t'as rien trouv�?");		// � modifier aussi
		
		}
		
		if (leBouton==permuter) {				
			this.reglette=modifAleat();
			afficheReglette();
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
	
	public int getJetonsAChanger() {
		return jetonsAChanger;
	}
	
	void permute(int i,int j) {
		String temp;
		cases[i].setBackground(null);cases[j].setBackground(null);
		temp=cases[i].getText();
		cases[i].setText(cases[j].getText())	;
		reglette[i]=reglette[j];
		cases[j].setText(temp);	
		reglette[j]=temp;		
		}
	
	void majReglette(String nouvellesLettres[]) {
		int indReglette=0;
		for (int indNL=0; indNL<nouvellesLettres.length; indNL++) {
			while (!reglette[indReglette].equals(""))indReglette++;
			reglette[indReglette]=nouvellesLettres[indNL];			
		}
		afficheReglette();
	}
	
	void afficheReglette() {
		for (int i=0; i<7;i++)cases[i].setText(reglette[i]);		
	}
	
	void videCoup()	{
		int compteur=0;
		for (var i : coup) {
			while (!cases[compteur].getText().equals(""))compteur++;
			cases[compteur].setText(i.lettre);
		}
	coup.clear();
	}

	String [] modifAleat() {	
	ArrayList <String> a = new ArrayList(Arrays.asList(this.reglette));
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
	