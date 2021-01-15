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
	 * deroulement de la partie. Reception du r�sultat du tirage au sort pour d�signer celui qui commence,
	 * puis jeu � tour de r�le jusqu'� ce que le joueur ait fini la partie ou re�oive un message de fin
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
	 * g�re le cas o� ce joueur joue en premier.
	 * Coincide au tirage au sort de 1. (0 si c'est le serveur)
	**/
	private void premierTour() {		
		jeJoue();			
	}
	
	
	/**
	 * Pour recevoir un objet du serveur
	 * @return {@link Object} l'objet re�u. 
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
	 * @param objetEnvoy� {@link Object}
	 */
	private void envoyerObjet(Object objetEnvoy�) {
		try { 		
		client.out.writeObject(objetEnvoy�);
		client.out.flush();  
		} catch (IOException e) {
	           e.printStackTrace();
		}
	}
	
	/**
	 * g�re tout ce qu'il faut envoyer au serveur.
	 * Il faut envoyer:
	 * Le plateau mis � jour. 
	 *  le score .
	 *  le bool�en pour indiquer si la partie est finie .
	 */
	private void envoyer () {
		envoyerObjet(Plateau.plateau); 
		envoyerObjet(scoreJoueur1);	
		envoyerObjet(joueurAfini);						
	}
	
	
	
	
	
	
	/**
	 * C'est au serveur de jouer.
	 * r�ception(et mise � jour) du plateau, du score, du nombre de jetons restants et r�ception de l'�tat de la partie.
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
	 * 3 cas: il passe(envoie du message), il �change ses jetons, ou il pose des jetons.
	 * Dans ce cas: apr�s extraction des mots et calcul du score: mise � jour de l'affichage, 
	 * envoie des informations au serveur et r�cup�ration des nouvelles lettres.
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
					envoyerObjet(Plateau.plateau);  //sans �a �a marche pas: ma foi.....
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
					//sinon on r�cup�re de nouveaux jetons
					envoyerObjet(chevalet.getJetonsAChanger());	     	
					reglette=(String [])recevoirObjet();					
					chevalet.majReglette(reglette);					
					break;
	     	}
	     }		
	}
	
	/**
	 * Gestion de l'�change de lettres contre de nouvelles : Envoie, reception et mise � jour.
	 */
	void changeJetons() {					
		envoyerObjet((String[])chevalet.echange.lettresAChanger);		
		reglette=(String[] )recevoirObjet();		
		chevalet.majReglette(reglette);		
		chevalet.joueurAchange=false;
	}
	
	
	/**
	 * 
	 * @return true si le joueur a termin� la partie
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
	 * fermeture du socket et des flux d'�change
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
		
			
		