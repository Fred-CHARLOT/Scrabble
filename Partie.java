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
String t[] [];
String monNom="Raoul", nomJoueur1;
private boolean partieEnCours;

//static boolean joueurAjoue;
//static boolean joueurAPasse;
//static boolean joueurAchange;


private boolean joueurClientApasse;
private boolean joueurClientAchange;
private boolean joueurClientAFini=false;
private final boolean OnAttendQueLeJoueurJoue=true;
private int scoreServeur=0;
private int scoreJoueur0,tirage;
private int compteur=0;

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
			System.out.println("c" + compteur);
			if (testVictoire()) finDePartie( ) ; 
			ilJoue(0); //le joueur 0 joue	
			System.out.println("c" + compteur);
			if (testVictoire()) finDePartie( );			
		}				
	}
	
	private void jeJoue() {
		chevalet.joueurAjoue=false;
		chevalet.joueurAPasse=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
		chevalet.valider.setBackground(Color.green);	   	
	     	if (chevalet.joueurAjoue==true) {	
	     		if (chevalet.joueurAPasse) {compteur++;grille.remplirCases(Plateau.plateau);break;}	
	     		if (chevalet.joueurAchange) {
	     			changeJetons();
	     			chevalet.majReglette(reglette);
	     			compteur=0;
	     			System.out.println("a changé");
	     			break;
	     		}
	     		compteur=0;
	     		miseAJourTableau();//met à jour le plateau à partir du coup courant
	     		scoreServeur=scoreServeur+eval.scoreCoup(test(chevalet.coup));															//ici il faudra evaluer le score
	     		grille.score2.setText(monNom + " : "  +String.valueOf(scoreServeur));	     		
	     		reglette=sac.tirage(chevalet.getJetonsAChanger());
	     		grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");
	     		chevalet.coup.clear();	    	     		
	     		chevalet.majReglette(reglette);	
	     		//System.out.println("apres");for (int i=0; i<7;i++)System.out.println(i + chevalet.reglette[i]);
	     		break;
	     	}
	     }		
	}
	
	private void ilJoue(int joueur) {
		chevalet.joueurAjoue=true;//pour ne pas jouer quand ce n'est pas son tour
		while (OnAttendQueLeJoueurJoue) {
		try {		
			joueurClientApasse=(Boolean)recevoirObjet(0);
			if (joueurClientApasse==true) {
				Plateau.plateau =(String[][]) serveur.in[0].readObject(); //ça marche pas sans ça
				compteur++;	
				envoyerObjetA(compteur!=6, 0);
				break;}
			joueurClientAchange=(Boolean)recevoirObjet(0);	
			if (joueurClientAchange==true) {
				Plateau.plateau =(String[][]) serveur.in[0].readObject();
				reglette=sac.echangeJetons((String[])recevoirObjet(joueur));
				 envoyerObjetA(reglette, 0);
	        	compteur=0;
				 break;
			}
			//dans les autres cas:
			compteur=0;  
			Plateau.plateau =(String[][]) serveur.in[0].readObject();  //réceptionner le coup du joueur suivant et maj du plateau	 
			if (chevalet.coup.size()!=0)chevalet.videCoup(); //pour ne pas perdre de jetons             
			grille.remplirCases(Plateau.plateau);           //affichage plateau
			scoreJoueur0=(int)serveur.in[0].readObject();
			grille.score1.setText(nomJoueur1+ "  : " +String.valueOf(scoreJoueur0));//Affichage score  
			joueurClientAFini=(boolean)recevoirObjet(0);			
			if (joueurClientAFini)break;
			//sinon on envoie les nouveaux jetons
			reglette= sac.tirage((int)serveur.in[0].readObject());			         
            grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");            
            envoyerObjetA(reglette, 0);
            break;
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
	}
	}
	
	//Creer une array liste de tableau de  cases courantes pour tester Evalcoup. Attention la valeur est à zéro.
		private ArrayList <CaseCourante[]> test (ArrayList<CaseCourante> liste){
			ArrayList <CaseCourante[]> listeFinale=new ArrayList <CaseCourante[]>();
			CaseCourante t[]= new CaseCourante [liste.size()];
			int i=0;
			for (var k: liste) { t[i]=k;i++;}
			listeFinale.add(t);	
			return listeFinale;
		}
	
	
	
	
	
	
	void changeJetons() {
		reglette=sac.echangeJetons(chevalet.echange.lettresAChanger);
		compteur=0;
		chevalet.joueurAchange=false;
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
		envoyerObjetA(!testVictoire(), joueur);
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
			Plateau.plateau[i.ligne][i.colonne]=i.affichage;}		
	}

	

	private int tirage() {
		return 0;
	}
	

	private void calculScoreFinal() {
			scoreJoueur0=sac.scoreFinalMoins(scoreJoueur0, reglette);			
			scoreServeur=sac.scoreFinalMoins(scoreServeur, chevalet.reglette);
			if(serveurAFini())sac.scoreFinalPlus(scoreServeur, reglette);
			if(joueurClientAFini)sac.scoreFinalPlus(scoreServeur, chevalet.reglette);
			System.out.println(scoreJoueur0);System.out.println(scoreServeur);
		}
	
	private boolean serveurAFini() {
		return ((chevalet.isVide(chevalet.reglette)) && (sac.sac_jeton.size()==0));
	}
	
		private boolean toutLeMondePAsse() {
			return (compteur==6 && sac.sac_jeton.size()<7);
		}
			
	private boolean testVictoire() {
	return toutLeMondePAsse()||serveurAFini()||joueurClientAFini;
		}
	
	
		private void finDePartie() {
			reglette=(String[]) recevoirObjet(0);
			chevalet.videCoup();
			calculScoreFinal();
			
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
