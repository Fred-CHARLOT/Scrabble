import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Partie {
Serveur serveur;
Plateau plateau;		
SacJeton sac;
Chevalet chevalet;
GestionGrille grille;
String reglette []= {"Z","A","Z","A","Z","A","X"} ;
boolean partieEnCours;
static boolean joueurAjou�;
static boolean joueurAPass�;
final boolean OnAttendQueLeJoueurJoue=true;
private int score=0;
private int scoreJoueur0;

	Partie(int nombreDeJoueur){
		serveur = new Serveur(nombreDeJoueur);
		plateau=new Plateau();
		sac= new SacJeton();
		chevalet = new Chevalet(0);
		grille = new GestionGrille(0,chevalet);
		envoyerObjetA(reglette, 0);
		envoyerObjetA("Raoul", 0);		
		grille.score2.setText("Raoul : " +String.valueOf(score));
		grille.score1.setText("client :" +String.valueOf(scoreJoueur0));
	}
	
		
	public 	void deroulement() {
		partieEnCours = true;		
		while (partieEnCours==true) {  			
			jeJoue()	;
	
			envoyerObjetA(Plateau.plateau, 0);			
			envoyerObjetA(score, 0);			
			if (score==3) fermeture();
						
			ilJoue(0); //le joueur 0 joue			
		}				
	}
	
	
	public void envoyerObjetA(Object objetEnvoye, int joueur) {
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

	
	private void jeJoue() {
		joueurAjou�=false;
		joueurAPass�=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
		chevalet.valider.setBackground(Color.green);	   	
	     	if (joueurAjou�==true) {	
	     	if (joueurAPass�)break;	
	     	miseAJourTableau();//met � jour le plateau � partir du coup courant
	     	score++;
	     	grille.score2.setText("Raoul : " +String.valueOf(score));
	     	chevalet.coup.clear();
	     	break;
	     	}
	     }		
	}

	private void ilJoue(int joueur) {
		joueurAjou�=true;//pour ne pas jouer quand ce n'est pas son tour
		try {
		Plateau.plateau =(String[][]) serveur.in[0].readObject();  //r�ceptionner le coup du joueur suivant et maj du plateau	 
    	if (chevalet.coup.size()!=0)chevalet.videCoup(); //pour ne pas perdre de jetons             
        grille.remplirCases(Plateau.plateau);           //affichage plateau
        scoreJoueur0=(int)serveur.in[0].readObject();
        grille.score1.setText("Client:" +String.valueOf(scoreJoueur0));//Affichage score
         } catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
	}
	
	

	void fermeture() {		
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
