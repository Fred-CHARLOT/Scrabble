import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class Partie {
Serveur serveur;
Plateau plateau;		
SacJeton sac;
Chevalet chevalet;
GestionGrille grille;
String reglette [];
String monNom="Raoul", nomJoueur1;
boolean partieEnCours;
static boolean joueurAjoué;
static boolean joueurAPassé;
static boolean joueurAchangé;
final boolean OnAttendQueLeJoueurJoue=true;
private int score=0;
private int scoreJoueur0,tirage;
static int compteur=0;

	Partie(int nombreDeJoueur){
		serveur = new Serveur(nombreDeJoueur);
		plateau=new Plateau();
		sac= new SacJeton();
		reglette=sac.tirage(7);
		envoyerObjetA(reglette, 0);
		reglette=sac.tirage(7);
		chevalet = new Chevalet(0,reglette);
		grille = new GestionGrille(0,chevalet);
		envoyerObjetA(monNom, 0);		
		nomJoueur1=(String)recevoirObjet(0);
		grille.score2.setText(monNom + " : "  +String.valueOf(score));
		grille.score1.setText(nomJoueur1+ "  : " +String.valueOf(scoreJoueur0));	
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
			envoyerObjetA(reglette, 0);
			if (testVictoire()) finDePartie( );			
		}				
	}
	
	private boolean testVictoire() {		
	return ((sac.sac_jeton.size()==0)||(compteur==4 && sac.sac_jeton.size()<7));
	}
	
	private void finDePartie() {
	
		fermeture();
	}
	
	

private Object recevoirObjet(int joueur) {
	Object O=new Object();
	try {
		O =serveur.in[joueur].readObject();					
	} catch (IOException | ClassNotFoundException e) {
           e.printStackTrace();	
	}
	return O;
};
	
	
private void envoyerJoueur(int joueur) {
	envoyerObjetA(Plateau.plateau, joueur);			
	envoyerObjetA(score, joueur);	
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

	
	private void premierTour() {	
		ilJoue(0); //le joueur 0 joue	
		envoyerObjetA(reglette, 0);
	}
	
	private int tirage() {
		return 0;
	}
	
void changeJetons() {
		reglette=sac.changeJetons(chevalet.echange.lettresAChanger);
		compteur=0;//il faudra tester que le coup n'est pas vide
		joueurAchangé=false;
	}
	
void changeJetonsClients(int joueur) {
	reglette=sac.changeJetons((String[])recevoirObjet(joueur));
	envoyerObjetA(reglette, joueur);
	if (reglette.length!=0)compteur=0; else compteur=compteur++;
}



	private void jeJoue() {
		joueurAjoué=false;
		joueurAPassé=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
		chevalet.valider.setBackground(Color.green);	   	
	     	if (joueurAjoué==true) {	
	     	if (joueurAPassé) break;	
	     	if (joueurAchangé) {changeJetons();chevalet.majReglette(reglette);break;}
	     	miseAJourTableau();//met à jour le plateau à partir du coup courant
	     	score++;compteur=0;																//ici il faudra evaluer le score
	     	
	     	grille.score2.setText(monNom + " : "  +String.valueOf(score));
	     	reglette=sac.tirage(chevalet.getJetonsAChanger());
	     	grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");
			chevalet.coup.clear();
			chevalet.majReglette(reglette);
	     	break;
	     	}
	     }		
	}

	private void ilJoue(int joueur) {
		joueurAjoué=true;//pour ne pas jouer quand ce n'est pas son tour
		try {		
		Plateau.plateau =(String[][]) serveur.in[0].readObject();  //réceptionner le coup du joueur suivant et maj du plateau	 
    	if (chevalet.coup.size()!=0)chevalet.videCoup(); //pour ne pas perdre de jetons             
        grille.remplirCases(Plateau.plateau);           //affichage plateau
        scoreJoueur0=(int)serveur.in[0].readObject();
        grille.score1.setText(nomJoueur1+ "  : " +String.valueOf(scoreJoueur0));//Affichage score
        if (PartieClient.joueurAChangé==true) reglette=sac.changeJetons((String[])recevoirObjet(joueur));
        else reglette= sac.tirage((int)serveur.in[0].readObject());
        if (reglette.length==0)compteur++ ; else compteur=0;
        grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");
       
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
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
