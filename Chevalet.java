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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.List;


public class Chevalet  implements ActionListener {
	JButton cases [];  //creation du tableau de 7 lettres
	ArrayList <CaseCourante> coup= new ArrayList <CaseCourante>() ;	
	CoupCourant verificateur= new CoupCourant();
	JButton valider, passer, echanger,permuter;
	JFrame	fenetre1;
	JPanel panneau1,panneau2;
	GridLayout disposition1, disposition2;
	int caseCourante=7,jetonsAChanger=0;
	int serveurOuClient;
	String reglette[];
	
	List<List<CaseCourante>> nouveauxMots ;
	
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
		
		
		//création du tableau de JButton
		for ( int colonne=0  ; colonne< 7;colonne++) {		
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
	
	/**
	 * 	 * gestion des actions sur les boutons crées.
	 * Un bouton permet de valider le coup.
	 * un bouton permet de passer,
	 * un bouton permet d'échanger ses jetons,
	 * un bouton permet de permuter les jetons du chevalet
	 * 
	 * LA gestions des cases du chevalet se fait dans la classe GestionGrille
	 * Ici il est juste géré la permutation de ces cases
	 * 
	 */
		
	public void actionPerformed(ActionEvent événement)  { 		
		JButton leBouton = (JButton) événement.getSource();  		
		
		if ((leBouton==valider)&& (joueurAjoue==false) && (coup.size()!=0) ){						
			if (verificateur.verificateur(coup)) {				
				nouveauxMots = ExtractionMotsValides.put(Plateau.plateau, coup);								
				if(nouveauxMots != null && !nouveauxMots.isEmpty()) {					
					for(List<CaseCourante> mot : nouveauxMots) {
						ExtractionMotsValides.afficherMot(mot);
					}
					jetonsAChanger=coup.size();
					joueurAjoue=true;
					valider.setBackground(null);//changer la couleur du bouton pour passer la main	
					}
				else {
					//TODO: Traitement a faire quand insertion non valide.					
					JOptionPane.showMessageDialog(null, "ce mot n existe pas");
				}
			}										
		}	
		
			
		
		if ((leBouton==passer)&& (joueurAjoue==false) ) {	
			videCoup();			
			joueurAPasse=true;			
			joueurAjoue=true;
			valider.setBackground(null);
		}	
		
	
		if ((leBouton==echanger)&& (joueurAjoue==false)) {		
		echange.setVisible(true);
		valider.setEnabled(false);
		passer.setEnabled(false);	
		valider.setBackground(null);
		}
		
		if (leBouton==permuter) {				
			this.reglette=modifAleat();
			afficheReglette();
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
	
	
	
	/**
	 * 
	 * @return le nombre de jetons à changer apres un coup joué sous forme de {@link int}
	 */	
	public int getJetonsAChanger() {
		return jetonsAChanger;
	}
	
	
	
	
	/**
	 * Cette méthode permet d'échanger deux boutons du chevalet, et donc les deux lettres qui y sont affichées .
	 * @param i indice de la première case à échanger
	 * @param j indice de la 2e case à échanger
	 */	
	void permute(int i,int j) {
		String temp;
		cases[i].setBackground(null);cases[j].setBackground(null);
		temp=cases[i].getText();
		cases[i].setText(cases[j].getText())	;
		reglette[i]=reglette[j];
		cases[j].setText(temp);	
		reglette[j]=temp;		
		}
	
	
	/**
	 * Permet d'ajouter de nouvelles lettres au chevalet en fusionnant  le {@link String[]} nouvellesLettres
	 * et le {@link String[]} reglette.
	 *	 
	 * @param nouvellesLettres 	{@link String[]} Les chaines de ce tableau vont être affichées dans le chevalet
	 *  dans les cases vides. Les lettre sont sous forme html.
	 *  Les cases vides du chevalet contiennent "".
	 */
	void majReglette(String nouvellesLettres[]) {
		int indReglette=0;
		for (int indNL=0; indNL<nouvellesLettres.length; indNL++) {
			while (!reglette[indReglette].equals(""))indReglette++;
			reglette[indReglette]=nouvellesLettres[indNL];			
		}
		afficheReglette();
	}
	
	/**
	 * met à jour l'affichage du chevalet à partir du {@link String[]} réglette.
	 */
	void afficheReglette() {
		for (int i=0; i<7;i++)cases[i].setText(reglette[i]);		
	}
	/**
	 * Vide la {@link ArrayList } de {@link <CaseCourante>} coup ,
	 * en remettant les jetons posés sur la grille dans le chevalet, et effaçant coup
	 * La reglette est aussi mise à jour.
	 */
	void videCoup()	{
		int compteur=0;
		for (var i : coup) {
			while (!cases[compteur].getText().equals(""))compteur++;
			cases[compteur].setText(i.affichage);
			reglette[compteur]=i.affichage;
		}
	coup.clear();	
	}

	/**
	 * Permute aléatoire les lettres sur le chevalet en permutant les lettres de la reglette.
	 * @return String []
	 */
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

	
	
	/**
	 * cherche une case vide dans le chevalet et retourne son indice.
	 * @return {@link int} indice de la premiere case vide
	 */
	int caseVide() {
		int i;
		for (i=0; i<7;i++) {
			if (cases[i].getText().equals(""))break;
		}
		return i;
	}
	
	
	
	/**
	 * booleen qui renvoie true si un tableau est vide (ne contient que des  "")
	 * @param t	{@link String []} 
	 * 
	 * @return	true si le tableau est vide(ne contient que des "")
	 */
	boolean isVide(String t []) {
		for (int i=0; i<t.length;i++) {if (!t[i].equals(""))return false;}
		return true;
	}

	
	/**
	 * permet de retrouver de retrouver l'indice d'une lettre posée sur la grille dans la 
	 * {@link ArrayList } de {@link <CaseCourante>} coup.
	 * 
	 * @param   ligne  {@link int} :indice de la ligne sur laquelle est placée la lettre.
	 * @param colonne {@link int} :indice de la ligne sur laquelle est placée la lettre.
	 * 
	 * @return {@link int} indice de la lettre dans coup.
	 */
	
	int retrouveCaseDansCoup(int ligne, int colonne) {
		for (var i : coup) {
			if (i.ligne ==ligne && i.colonne ==colonne) return coup.indexOf(i);	
		}
		 return -1;
	}



}	
	