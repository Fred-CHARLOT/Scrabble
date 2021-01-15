import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 *Gestion de la partie, version serveur
 */

public class Partie {
Serveur serveur;
Plateau plateau;		
SacJeton sac;
Chevalet chevalet;
GestionGrille grille;
EvalCoup eval;
String reglette [];
String t[] [];
String monNom, nomJoueur1;
private boolean partieEnCours;
private boolean joueurClientApasse;
private boolean joueurClientAchange;
private boolean joueurClientAFini=false;
private final boolean OnAttendQueLeJoueurJoue=true;
private int scoreServeur=0;
private int scoreJoueur0,tirage;
private int compteur=0;

	Partie(int nombreDeJoueur, String nom){
		monNom=nom;
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
	
	
	/**
	 * D�roulement de la partie. 
	 * Le joueur qui commence est tir� au sort. 
	 */
	
	public	void deroulement() {
		partieEnCours = true;
		tirage=tirage();		
		envoyerObjetA(tirage, 0);
		if (tirage==1)premierTour();
		
		while (partieEnCours==true) {  			
			jeJoue()	;			
			envoyerJoueur(0);			
			if (testVictoire()) finDePartie( ) ; 
			ilJoue(0); //le joueur 0 joue			
			if (testVictoire()) finDePartie( );			
		}				
	}
	
	
	
	/**
	 * Gestion du 1er coup quand c'est le client qui commence.(joueur 0)
	 */
	private void premierTour() {	
		ilJoue(0); //le joueur 0 joue		
	}
	
	/**
	 * Le serveur joue : 3 cas: il passe, il �change ses jetons ou il pose des lettres et cr�e des coups valides
	 * Si le joueur passe, on incremente le compteur.
	 * Si le joueur change ses jetons, on effectue l'echange et on remet le compteur � z�ro.
	 * Sinon, � partie des mots extraits, on calcule le score, on met le compteur � z�ro,
	 * on met � jour l'affichage, on r�cup�re les nouveaux jetons et on met le chevalet � jour. 
	 */
	private void jeJoue() {
		chevalet.joueurAjoue=false;
		chevalet.joueurAPasse=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
		chevalet.valider.setBackground(Color.green);	   	
	     	if (chevalet.joueurAjoue==true) {	
	     		if (chevalet.joueurAPasse) {
	     			compteur++;	//compte combien de fois les joueurs passent cons�cutivement
	     			grille.remplirCases(Plateau.plateau);
	     			break;
	     		}	
	     		if (chevalet.joueurAchange) {
	     			changeJetons();	     			
	     			compteur=0;	     			
	     			break;
	     		}
	     		//Si le joueur  a pos� des mots valides
	     		ArrayList <CaseCourante[]> listeFinale = conversion(chevalet.nouveauxMots);
	     		scoreServeur=scoreServeur+eval.scoreCoup(listeFinale);	
	     		grille.score2.setText(monNom + " : "  +String.valueOf(scoreServeur));
	     		compteur=0;
	     		grille.remplirCases(Plateau.plateau);   	     			     		
	     		reglette=sac.tirage(chevalet.getJetonsAChanger());
	     		grille.jetonsRestant.setText("il reste " + sac.sac_jeton.size() + " jetons");
	     		chevalet.coup.clear();	    	     		
	     		chevalet.majReglette(reglette);	
	     		break;
	     	}
	     }		
	}
	
	/**
	 * Le joueur dont le num�ro est en param�tre joue.
	 * Trois cas: il passe, il �change ses jetons ou il pose des lettres et cr�e des coups valides
	 * Si le joueur passe, on incremente le compteur et on pr�vient le joueur qu'on n'a pas pass� 6 fois.
	 * Si le joueur change ses jetons, on effectue l'echange, on envoie les nouveaux jetons et on remet le compteur � z�ro.
	 * Sinon, on re�oit le nouveau plateau, le nouveau score et un booleen indiquant si la partie est finie ou pas.
	 * On met � jour le score et l'affichage et on envoie les nouvelles lettres au joueur.
	 * @param joueur indique le num�ro du joueur qui joue.
	 */
	private void ilJoue(int joueur) {
		chevalet.joueurAjoue=true; //pour ne pas jouer quand ce n'est pas son tour
		while (OnAttendQueLeJoueurJoue) {
		try {		
			
			joueurClientApasse=(Boolean)recevoirObjet(0);
			if (joueurClientApasse==true) {
				Plateau.plateau =(String[][]) serveur.in[0].readObject(); //�a marche pas sans �a
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
			Plateau.plateau =(String[][]) serveur.in[0].readObject();  //r�ceptionner le coup du joueur suivant et maj du plateau	 
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
	
	
	
	/**
	 * Gestion de l'�change de jetons contre de nouveaux jetons.
	 * On echange les lettres de  {@link String[]} lettresAChanger par de nouvelles lettres.
	 * On les mets dans la reglette et on met � jour l'affichage
	 */
	void changeJetons() {
		reglette=sac.echangeJetons(chevalet.echange.lettresAChanger);
		chevalet.majReglette(reglette);
		compteur=0;
		chevalet.joueurAchange=false;
	}
	
	
	/**
	 * Pour recevoir un objet venant d'un joueur.
	 * @param joueur  num�ro du joueur envoyant l'objet.
	 * @return renvoie l'objet re�u.
	 */
	private Object recevoirObjet(int joueur) {
		Object O=new Object();
		try {
			O =serveur.in[joueur].readObject();	
		} catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();	
		}
		return O;
	}
	
	
	/**
	 * Envoie une s�rie d'informations au joueur indiqu�.
	 * Envoie le plateau, le score du Serveur, le nombre de jetons restants et 
	 * le parametre {@link boolean} testVictoire qui indique si la partie est encore en cours
	 * @param joueur indique � qui est destin� l'envoi.
	 */
	private void envoyerJoueur(int joueur) {		
		envoyerObjetA(Plateau.plateau, joueur);	
		envoyerObjetA(scoreServeur, joueur);
		envoyerObjetA(sac.sac_jeton.size(), joueur);		
		envoyerObjetA(!testVictoire(), joueur);
	}


	/**
	 * Pour envoyer un objet au joueur indiqu�.
	 * @param  objetEnvoye	{@link Object}
	 * @param joueur {@link int}  entier d�signant le num�ro du joueur � qui est destin� l'envoi
	 */	
	private void envoyerObjetA(Object objetEnvoye, int joueur) {
		try { 
			serveur.out[joueur].writeObject(objetEnvoye); 
			serveur.out[joueur].flush();				
			}
			catch (IOException e) {	
				e.printStackTrace();
			}
	}
	
	
	/**
	 * Met � jour le plateau � l'aide du {@link ArrayList } de {@link <CaseCourante>} coup.
	 *  C'est � dire le dernier coup jou�.
	 */
	void miseAJourTableau() {							
		for (var i:chevalet.coup) {		
			Plateau.plateau[i.ligne][i.colonne]=i.affichage;}		// A supprimer
	}

	
/**
 * tirage au sort du joueur d�butant. 
 * @return 1 si le joueur 1 d�bute, 0 si c'est le serveur.
 */
	private int tirage() {
		Random tirage = new Random();
		return tirage.nextInt(2);
	}
	
/**
 * Calcule le score final � partir des scores et des lettres restantes dans les chevalets.
 */
	private void calculScoreFinal() {
			scoreJoueur0=sac.scoreFinalMoins(scoreJoueur0, reglette);			
			scoreServeur=sac.scoreFinalMoins(scoreServeur, chevalet.reglette);
			if(serveurAFini())sac.scoreFinalPlus(scoreServeur, reglette);
			if(joueurClientAFini)sac.scoreFinalPlus(scoreServeur, chevalet.reglette);					
			}
		
	/**
	 * Affiche qui a  gagn� et envoie l'information au client
	 */
	
	public void AffichageVainqueur() {
		String message;
		if (scoreJoueur0== scoreServeur) message="Match nul!!! Vous avez tous les deux " + scoreServeur + "points" ;
		if (scoreJoueur0> scoreServeur)	message = "Bravo � " + nomJoueur1 +" il gagne "+ scoreJoueur0 + " � " + scoreServeur;
		else message= "Bravo � " + monNom +" il gagne " + scoreServeur + " � " + scoreJoueur0;
		JOptionPane.showMessageDialog(null, message);
		envoyerObjetA(message, 0);		
	}
	
	
	/**
	 * 
	 * @return true si le serveur a gagn�
	 */
	private boolean serveurAFini() {
		return ((chevalet.isVide(chevalet.reglette)) && (sac.sac_jeton.size()==0));
	}
	
	/**
	 * 
	 * @return true si tout le monde a pass� 3 fois cons�cutivement en fin de partie(moins de 7 jetons)
	 */
		private boolean toutLeMondePAsse() {
			return (compteur==6 && sac.sac_jeton.size()<7);
		}
		
		/**
		 * 
		 * @return true si la partie est termin�e (un vainqueur ou tout le monde a pass� 3 fois)
		 */
	private boolean testVictoire() {
	return toutLeMondePAsse()||serveurAFini()||joueurClientAFini;
		}
	
	
	/**
	 * Transforme une {@link List}   de {@link List}  de {@link CaseCourante}   en {@link List}   de {@link CaseCourante[]}
	 * @param mot {@link List}   de {@link List}  de {@link CaseCourante}
	 * @return  {@link List}   de {@link CaseCourante[]}
	 */
	private   ArrayList<CaseCourante[]> conversion(List<List<CaseCourante>> mot) {

		ArrayList<CaseCourante[]> a = new ArrayList<CaseCourante[]>();

		for (int i=0;i<mot.size();i++) {
		int n = mot.get(i).size();
		CaseCourante[] c = new CaseCourante[n];
		for (int j=0;j<n;j++) {
		c[j]=mot.get(i).get(j);
		}
		a.add(c);
		}
		return a;

		}
	
	
	/**
	 * Gere la fin de la partie: 
	 * reception des jetons restants sur les chevalets,  calculs des scores et affichage du vainqueur
	 */
		private void finDePartie() {
			reglette=(String[]) recevoirObjet(0);
			chevalet.videCoup();
			calculScoreFinal();
			AffichageVainqueur();			
			//demander si on continue---->methode reboot
			//ou si on arrete ---->fermeture
			fermeture();
		}
		
/**
 * Fermeture des sockets et des flux d'entr�e et de sortie
 */
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
