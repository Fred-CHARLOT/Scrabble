import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class Partie {
Serveur serveur;
Plateau plateau;		
SacJeton sac;
Chevalet chevalet;
GestionGrille grille;
EvalCoup eval;
String reglette [];
String monNom="Raoul", nomJoueur1;
boolean partieEnCours;
static boolean joueurAjou�;
static boolean joueurAPass�;
static boolean joueurAchang�;
boolean joueurClientAchang�;
boolean joueurClientAGagn�=false;
final boolean OnAttendQueLeJoueurJoue=true;
private int scoreServeur=0;
private int scoreJoueur0,tirage;
static int compteur=0;

	Partie(int nombreDeJoueur){
		serveur = new Serveur(nombreDeJoueur);
		plateau=new Plateau();
		sac= new SacJeton();
		eval=new EvalCoup();
		reglette=sac.tirage(7);
		envoyerObjetA(reglette, 0);
		reglette=sac.tirage(7);
		chevalet = new Chevalet(0,reglette);
		grille = new GestionGrille(0,chevalet);
		envoyerObjetA(monNom, 0);		
		nomJoueur1=(String)recevoirObjet(0);
		grille.score2.setText(monNom + " : "  +String.valueOf(scoreServeur));
		grille.score1.setText(nomJoueur1+ "  : " +String.valueOf(scoreJoueur0));	
	}
	
	
	private void premierTour() {	
		ilJoue(0); //le joueur 0 joue	
		envoyerObjetA(reglette, 0);
	}
	
	public	void deroulement() {
		partieEnCours = true;
		tirage=tirage();
		envoyerObjetA(tirage, 0);
		if (tirage==1)premierTour();
		
		while (partieEnCours==true) {  			
			jeJoue()	;			
			envoyerJoueur(0);			
			if (testVictoire()) finDePartie( ) ; //
			ilJoue(0); //le joueur 0 joue	
			
			if (testVictoire()) finDePartie( );			
		}				
	}
	
	private void jeJoue() {
		joueurAjou�=false;
		joueurAPass�=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
		chevalet.valider.setBackground(Color.green);	   	
	     	if (joueurAjou�==true) {	
	     	if (joueurAPass�) break;	
	     	if (joueurAchang�) {changeJetons();chevalet.majReglette(reglette);break;}
	     	miseAJourTableau();//met � jour le plateau � partir du coup courant
	     	scoreServeur++;compteur=0;													//ici il faudra evaluer le score
	     	grille.score2.setText(monNom + " : "  +String.valueOf(scoreServeur));
	     	reglette=sac.tirage(chevalet.getJetonsAChanger());
	     	grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");
			chevalet.coup.clear();
			chevalet.majReglette(reglette);
	     	break;
	     	}
	     }		
	}
	
	private void ilJoue(int joueur) {
		joueurAjou�=true;//pour ne pas jouer quand ce n'est pas son tour
		
		try {		
		joueurClientAchang�=(Boolean)recevoirObjet(0);
		Plateau.plateau =(String[][]) serveur.in[0].readObject();  //r�ceptionner le coup du joueur suivant et maj du plateau	 
    	if (chevalet.coup.size()!=0)chevalet.videCoup(); //pour ne pas perdre de jetons             
        grille.remplirCases(Plateau.plateau);           //affichage plateau
        scoreJoueur0=(int)serveur.in[0].readObject();
        grille.score1.setText(nomJoueur1+ "  : " +String.valueOf(scoreJoueur0));//Affichage score       
        joueurClientAGagn�=(boolean)recevoirObjet(0);
        
        if (joueurClientAchang�==true) {
        	reglette=sac.EchangeJetons((String[])recevoirObjet(joueur));
        	joueurClientAchang�=false;
        }        
        else { reglette= sac.tirage((int)serveur.in[0].readObject());}               
        if (reglette.length==0)compteur++ ; else compteur=0;        
        grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");  
        envoyerObjetA(reglette, 0);
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
	}
	
	
	void changeJetons() {
		reglette=sac.EchangeJetons(chevalet.echange.lettresAChanger);
		if (reglette.length==0) compteur++; else compteur=0;
		joueurAchang�=false;
	}
	
	void changeJetonsClients(int joueur) {									//� effacer il me semble
	reglette=sac.EchangeJetons((String[])recevoirObjet(joueur));
	envoyerObjetA(reglette, joueur);
	if (reglette.length!=0)compteur=0; else compteur=compteur++;
	}

	private Object recevoirObjet(int joueur) {
		Object O=new Object();
		try {
			O =serveur.in[joueur].readObject();					
		} catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();	
		}
		return O;
	}
	
	
	private void envoyerJoueur(int joueur) {
		envoyerObjetA(Plateau.plateau, joueur);			
		envoyerObjetA(scoreServeur, joueur);	
		envoyerObjetA(sac.sac_jeton.size(), joueur);
	}


	private void envoyerObjetA(Object objetEnvoye, int joueur) {
		try { 
			serveur.out[joueur].writeObject(objetEnvoye); 
			serveur.out[joueur].flush();				
			}
			catch (IOException e) {	
				e.printStackTrace();
			}
	}
	
	
	
	void miseAJourTableau() {							
		for (var i:chevalet.coup) {		
			Plateau.plateau[i.ligne][i.colonne]=i.lettre;}		
	}

	

	private int tirage() {
		return 0;
	}
	

	
	private boolean serveurAFini() {
	return ((chevalet.reglette.length==0) && (sac.sac_jeton.size()==0));
}
	
	private boolean testVictoire() {		
		if (compteur==6 && sac.sac_jeton.size()<7) {
			//il faut s'assurer qu'il n y a pas des jetons sur la grille
			eval.scoreFinal(scoreJoueur0, reglette);
			chevalet.videCoup();
			eval.scoreFinal(scoreServeur, chevalet.reglette);
			return true;
		}
		if (serveurAFini()) {
			//il faut s'assurer qu'il n y a pas des jetons sur la grille
			eval.scoreFinal(scoreJoueur0, reglette);
			return true;
		}
		if (joueurClientAGagn�) {
			chevalet.videCoup();
			eval.scoreFinal(scoreServeur, chevalet.reglette);
			return true;
		}		
		return false;
	}
		
	
	
		
	
	
		private void finDePartie() {
			//ouvrir une fenetre avec les scores et le gagnant
			//demander si on continue---->methode reboot
			//ou si on arrete ---->fermeture
			fermeture();
		}
		

	private void fermeture() {		
		try {
		for (int i=0;i<serveur.nombreDeStream; i++) {			
			serveur.out[i].close();			
			serveur.in[i].close();			
		}
		serveur.sSock.close();
		System.exit(0);
		} 
		catch (IOException e) {	
			e.printStackTrace();
		}
	}
	
}
