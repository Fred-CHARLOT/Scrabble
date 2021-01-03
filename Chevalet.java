import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
/// EVENENEMENT
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Chevalet  implements ActionListener {
	JButton cases [];  //creation du tableau de 7 lettres
	ArrayList <CaseCourante> coup= new ArrayList <CaseCourante>() ;	
	JButton valider, passer, echanger,permuter;
	JFrame	fenetre1;
	JPanel panneau1,panneau2;
	GridLayout disposition1, disposition2;
	int caseCourante=7,jetonsAChanger=0;
	int serveurOuClient;
	String reglette[];
	Echange echange;
	boolean joueurAchange=false;
	boolean joueurAPasse=false;
	boolean joueurAjoue=false;
	
	Chevalet (int ServeurClient,String reglette [] ){
		serveurOuClient=ServeurClient;
		echange=new Echange(this);
		cases = new JButton [7];
		this.reglette=reglette;
		if ( serveurOuClient==0) fenetre1=new JFrame("Scrabble Serveur by Houssem, Fred and JB");	
		else fenetre1=new JFrame("Scrabble Client by Houssem, Fred and JB");
		fenetre1.setSize(800, 130) ;
		fenetre1.setLocationRelativeTo(null);//pour centrer la fenetre
		fenetre1.setVisible(true);
		fenetre1.setAlwaysOnTop(true);
		panneau1 = new JPanel();
		disposition1 = new GridLayout(1, 7); 
		panneau1.setLayout(disposition1);
		fenetre1.add("Center",panneau1);
		fenetre1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		//Font f=new Font("Arial", Font.BOLD, 120);
		
		
		for ( int colonne=0  ; colonne< 7;colonne++) {		//création du tableau de JButton
			cases[colonne]= new JButton();
			//cases[colonne].setFont(f);
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
		echanger = new JButton("échange");
		permuter = new JButton("permuter");
		panneau2.add(valider);
		panneau2.add(passer);
		panneau2.add(echanger);
		panneau2.add(permuter);
		echanger.addActionListener(this);
		permuter.addActionListener(this);
		valider.addActionListener(this);
		passer.addActionListener(this);
		
		
	}
	
	
		
	public void actionPerformed(ActionEvent événement)  { /// EVENENEMENTactionPerformed
		
		JButton leBouton = (JButton) événement.getSource(); /// EVENENEMENT 
		
		
		if ((leBouton==valider)&& (joueurAjoue==false) && (coup.size()!=0) ){		//	&&(serveurOuClient==0)
			 	// à partir de la Array list "coup" :
				//vérifier que le positionnement est valide.
			//Si c'est bon :
				//Extraire les mots de coup, les verifier dans un dictionnaire(message d'erreur avec le mot faux sinon?)
				//et si c'est bon, les mettre dans une array list de tableaux cases courantes.(chaque tableau est un mot)
				//calculer le score à l'aide de cette liste et d'EvalCoup.
			//sinon: renvoyer les jetons sur le chevalets(pour mettre coup.size à zéro)
			//peut être prévoir un message d'erreur, style les lettres ne sont pas alignées.
				//Partie.compteur=0;
				jetonsAChanger=coup.size();
				joueurAjoue=true;
				valider.setBackground(null); //changer la couleur du bouton pour passer la main
				if (verifierAdjacence() ) System.out.println("toto");
		}	
		
	/*	if ((leBouton==valider)&& (joueurAjoue==false)&& (serveurOuClient==1)&&(coup.size()!=0)) { 			
			jetonsAChanger=coup.size();
			joueurAjoue=true;  
			valider.setBackground(null);
			if (verifierAdjacence() ) System.out.println("toto");
		 }*/
			
		
		
		if ((leBouton==passer)&& (joueurAjoue==false) ) {	//	&&(serveurOuClient==0) 	
			videCoup();			
			joueurAPasse=true;			
			joueurAjoue=true;
			valider.setBackground(null);
		}	
		
	/*	if ((leBouton==passer)&& (joueurAjoue==false)&&(serveurOuClient==1)  ) {					
			videCoup();
			joueurAPasse=true;
			joueurAjoue=true;
			valider.setBackground(null);
		}	
		*/
		
		if ((leBouton==echanger)&& (joueurAjoue==false)) {		
		echange.setVisible(true);
		valider.setEnabled(false);
		passer.setEnabled(false);	
		valider.setBackground(null);
		}
		
		if (leBouton==permuter) {				
			//this.reglette=modifAleat();
			//afficheReglette();
			
			InsertionUtil.afficheMots(InsertionUtil.extraireMots(Plateau.plateau));
			
			}
	
		
		//echange de cases. par des permutations. A faire plus tard aussi en intercalant.
				
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
			cases[compteur].setText(i.affichage);
			reglette[compteur]=i.affichage;
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

	int caseVide() {
		int i;
		for (i=0; i<7;i++) {
			if (cases[i].getText().equals(""))break;
		}
		return i;
	}

boolean isVide(String t []) {
	for (int i=0; i<t.length;i++) {if (!t[i].equals(""))return false;}
	return true;
}

int retrouveCaseDansCoup(int ligne, int colonne) {
	for (var i : coup) {
		if (i.ligne ==ligne && i.colonne ==colonne) return coup.indexOf(i);	
	}
	 return -1;
}

public boolean verifierAdjacence() {

	if (coup.size()==0) return false;
	
	ArrayList<Integer> listelignes = new ArrayList<Integer>();
	ArrayList<Integer> listecolonnes = new ArrayList<Integer>();		
	for (var i:coup) {
		listelignes.add(i.ligne);
		listecolonnes.add(i.colonne);
	}
	
	int minLigne = Collections.min(listelignes);// Retourner la plus petite valeur des lignes
	int maxLigne = Collections.max(listelignes);
	int minColonne = Collections.min(listecolonnes);
	int maxColonne = Collections.max(listecolonnes);
	
	if (maxColonne-minColonne==0) return verifFinal(minLigne,maxLigne, minColonne); //alignement sur une colonne
	
	if (maxLigne-minLigne==0) { return true;} //alignement sur une ligne
	
	return false;
	
	
}

public boolean verifFinal(int min, int max, int k) {
	for (int count = min; count<max+1;count++) {
		System.out.println(count);
		if ((retrouveCaseDansCoup(count,k)==-1)&& (Plateau.plateau)[count][k].charAt(0)!='<')return false;
	}
	return true;
}




}	
	