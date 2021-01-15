import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.management.StringValueExp;
import javax.swing.JOptionPane;

public class PartieClient {
	private int ip;
	boolean partieEnCours;		
	final boolean OnAttendQueLeJoueurJoue=true;
	private boolean joueurAfini=false;
	static int scoreJoueur1=0;
	static int scoreServeur=0;
	private int tirage;
	int JetonsRestant=88;
	public String nomServeur, nomJoueur1;
	Chevalet chevalet;
	GestionGrille grille; 
	EvalCoup eval;
	static Plateau plateauPartie;					
	Client client;
	String reglette[];	
	
		PartieClient(String nom, String ip){
		nomJoueur1=nom;
		client = new Client( ip);	
		plateauPartie= new Plateau();
         eval = new  EvalCoup();
         try {								
				reglette =(String [])client.in.readObject();
				chevalet=new Chevalet(1,reglette);
				grille= new GestionGrille(1,chevalet);
				nomServeur =(String) client.in.readObject();						
				grille.score2.setText( nomServeur + " : " + String.valueOf(scoreServeur));
				grille.score1.setText( nomJoueur1 + " : "+ String.valueOf(scoreJoueur1));		
				envoyerObjet(nomJoueur1);
				} catch (IOException | ClassNotFoundException e) {
			           e.printStackTrace();
		}     
	}	
	
	/**
	 * deroulement de la partie. Reception du résultat du tirage au sort pour désigner celui qui commence,
	 * puis jeu à tour de rôle jusqu'à ce que le joueur ait fini la partie ou reçoive un message de fin
	 * 
	 */
	public void deroulement() {
		partieEnCours =true;			 
		tirage=(int)recevoirObjet();		
		if (tirage==1)premierTour();
		
		while (partieEnCours == true) {	    				
			ilJoue() ;				
			jeJoue();			 
			if ((partieEnCours==false)||joueurAfini) {
				chevalet.videCoup();
				envoyerObjet(chevalet.reglette);
				finDePartie();				
			}
		}	
	}
	
	
	/**
	 * gère le cas où ce joueur joue en premier.
	 * Coincide au tirage au sort de 1. (0 si c'est le serveur)
	**/
	private void premierTour() {		
		jeJoue();			
	}
	
	
	/**
	 * Pour recevoir un objet du serveur
	 * @return {@link Object} l'objet reçu. 
	 * 
	 */
	private Object recevoirObjet() {
		Object O=new Object();
		try {
			O =client.in.readObject();					
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();	
		}
		return O;
	};
	
	
	/**
	 * Pour renvoyer un {@link Object}au serveur.
	 * @param objetEnvoyé {@link Object}
	 */
	private void envoyerObjet(Object objetEnvoyé) {
		try { 		
		client.out.writeObject(objetEnvoyé);
		client.out.flush();  
		} catch (IOException e) {
	           e.printStackTrace();
		}
	}
	
	/**
	 * gère tout ce qu'il faut envoyer au serveur.
	 * Il faut envoyer:
	 * Le plateau mis à jour. 
	 *  le score .
	 *  le booléen pour indiquer si la partie est finie .
	 */
	private void envoyer () {
		envoyerObjet(Plateau.plateau); 
		envoyerObjet(scoreJoueur1);	
		envoyerObjet(joueurAfini);						
	}
	
	
	
	
	
	
	/**
	 * C'est au serveur de jouer.
	 * réception(et mise à jour) du plateau, du score, du nombre de jetons restants et réception de l'état de la partie.
	 * Envvoie des jetons restants en fin de partie.
	 */
	private void ilJoue() {
		chevalet.joueurAjoue=true;		
		try {					
			Plateau.plateau=(String [] [])client.in.readObject();		  	
			if (chevalet.coup.size()!=0)chevalet.videCoup();                  
			grille.remplirCases(Plateau.plateau);      
			scoreServeur=(int) client.in.readObject();		  
			grille.score2.setText(nomServeur + " : " + String.valueOf(scoreServeur));
			JetonsRestant=(int) client.in.readObject();        
			grille.jetonsRestant.setText("il reste " + JetonsRestant + " jetons");
			partieEnCours=(boolean) client.in.readObject();
            chevalet.joueurAjoue=false;
            if (partieEnCours==false) {
            	System.out.println("partie fini");
            	chevalet.videCoup();
            	envoyerObjet(chevalet.reglette);
            	finDePartie();
            } 
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
	}
	
	
	
	/**
	 * gestion du client qui joue. 
	 * 3 cas: il passe(envoie du message), il échange ses jetons, ou il pose des jetons.
	 * Dans ce cas: après extraction des mots et calcul du score: mise à jour de l'affichage, 
	 * envoie des informations au serveur et récupération des nouvelles lettres.
	 */
	private void jeJoue() {
		chevalet.joueurAjoue=false;
		chevalet.joueurAPasse=false;
		chevalet.joueurAchange=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
			chevalet.valider.setBackground(Color.green);		   
			if (chevalet.joueurAjoue==true) { 	    	
				
				envoyerObjet(chevalet.joueurAPasse);
					if (chevalet.joueurAPasse) {
					grille.remplirCases(Plateau.plateau);
					envoyerObjet(Plateau.plateau);  //sans ça ça marche pas: ma foi.....
					partieEnCours=(boolean) recevoirObjet();					
					break;	
				}	    	
				
				envoyerObjet(chevalet.joueurAchange);		
				if (chevalet.joueurAchange){
					//chevalet.videCoup();
					//grille.remplirCases(Plateau.plateau);
					envoyerObjet(Plateau.plateau);
					changeJetons();					
					break;}	
	     	
	     	// Dans les autres cas :
				
				ArrayList <CaseCourante[]> listeFinale = conversion(chevalet.nouveauxMots);
				
				scoreJoueur1=scoreJoueur1+eval.scoreCoup(listeFinale);
				//miseAJourTableau();
				grille.remplirCases(Plateau.plateau);//(pour enlever les couleurs)
				grille.score1.setText(nomJoueur1 + " : "+ String.valueOf(scoreJoueur1));
				if (JetonsRestant-chevalet.jetonsAChanger>=0)grille.jetonsRestant.setText("il reste " + (JetonsRestant-chevalet.jetonsAChanger) + " jetons");
				chevalet.coup.clear();
				joueurAfini=joueurAfini();
				envoyer();
				
				if (joueurAfini)   break;
					//sinon on récupère de nouveaux jetons
					envoyerObjet(chevalet.getJetonsAChanger());	     	
					reglette=(String [])recevoirObjet();					
					chevalet.majReglette(reglette);					
					break;
	     	}
	     }		
	}
	
	/**
	 * Gestion de l'échange de lettres contre de nouvelles : Envoie, reception et mise à jour.
	 */
	void changeJetons() {					
		envoyerObjet((String[])chevalet.echange.lettresAChanger);		
		reglette=(String[] )recevoirObjet();		
		chevalet.majReglette(reglette);		
		chevalet.joueurAchange=false;
	}
	
	
	/**
	 * 
	 * @return true si le joueur a terminé la partie
	 */
	private boolean joueurAfini() {
		return ((JetonsRestant==0)&& chevalet.isVide(chevalet.reglette));
	}
	
	
	/**
	 * Transforme une {@link List}   de {@link List}  de {@link CaseCourante}   en {@link List}   de {@link CaseCourante[]}
	 * @param mot {@link List}   de {@link List}  de {@link CaseCourante}
	 * @return  {@link List}   de {@link CaseCourante[]}
	 */
	public  ArrayList<CaseCourante[]> conversion(List<List<CaseCourante>> coup) {
		ArrayList<CaseCourante[]> a = new ArrayList<CaseCourante[]>();
		for (int i=0;i<coup.size();i++) {
		int n = coup.get(i).size();
		CaseCourante[] c = new CaseCourante[n];
		for (int j=0;j<n;j++) {
		c[j]=coup.get(i).get(j);
		}
		a.add(c);
		}
		return a;

		}
	
	
	/**
	 * gere la fin de la partie: affichage du vainqueur et lancement de la fermeture
	 */
	private void finDePartie() {	
		try { 	     			
			JOptionPane.showMessageDialog(null,(String) recevoirObjet( ) ); 
			Thread.sleep (5000);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }		
		fermeture();
	}
	
	
	
	/**
	 * fermeture du socket et des flux d'échange
	 */
	void fermeture () {
		try { 		
			client.out.close();
		   	client.in.close();
		   	client.clientSocket.close();
		   	System.exit(0);
			} catch (Exception e) {
			     e.printStackTrace();
			    }		
	}
	
	
}
		
			
		