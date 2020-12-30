import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.management.StringValueExp;

public class PartieClient {
	boolean partieEnCours;		
	static boolean joueurAjoue;
	static boolean joueurAPasse;
	static boolean joueurAChange;
	final boolean OnAttendQueLeJoueurJoue=true;
	static int scoreJoueur1=0;
	static int scoreServeur=0;
	private int tirage;
	int JetonsRestant;
	public String nomServeur, nomJoueur1="JB";
	Chevalet chevalet;
	GestionGrille grille; 
	EvalCoup eval;
	static Plateau plateauPartie;					
	Client client;
	String reglette[];	
	
		PartieClient(){
		client = new Client();	
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
	
	
	public void deroulement() {
		partieEnCours =true;			 
		tirage=(int)recevoirObjet();
		if (tirage==1)premierTour();
		
		while (partieEnCours == true) {	    				
			ilJoue() ;		
			
			jeJoue();			 
			if ((partieEnCours==false)||joueurAfini()) {
				chevalet.videCoup();
				envoyerObjet(chevalet.reglette);
				finDePartie();				
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
	
	private Object recevoirObjet() {
		Object O=new Object();
		try {
			O =client.in.readObject();					
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();	
		}
		return O;
	};
	
	private void envoyer () {
		envoyerObjet(Plateau.plateau); 
		envoyerObjet(scoreJoueur1);	
		envoyerObjet(joueurAfini());		
				
	}
	
	
	private void envoyerObjet(Object objetEnvoyé) {
		try { 		
		client.out.writeObject(objetEnvoyé);
		client.out.flush();  
		} catch (IOException e) {
	           e.printStackTrace();
		}
	}
	
	private void premierTour() {	//a verifier	
		jeJoue();			 
		envoyer();
		chevalet.coup.clear();
		chevalet.majReglette((String [])recevoirObjet());	
	}
	
	
	private void ilJoue() {
		joueurAjoue=true;		
		try {			
				
			Plateau.plateau=(String [] [])client.in.readObject();		  	
			if (chevalet.coup.size()!=0)chevalet.videCoup();                  
			grille.remplirCases(Plateau.plateau);      
			scoreServeur=(int) client.in.readObject();		  
			grille.score2.setText(nomServeur + " : " + String.valueOf(scoreServeur));
			JetonsRestant=(int) client.in.readObject();        
			grille.jetonsRestant.setText("il reste " + JetonsRestant + " jetons");
			partieEnCours=(boolean) client.in.readObject();
            joueurAjoue=false;
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
	
	
	
	private void jeJoue() {
		joueurAjoue=false;
		joueurAPasse=false;
		joueurAChange=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
			chevalet.valider.setBackground(Color.green);		   
			if (joueurAjoue==true) { 	    	
				envoyerObjet(joueurAPasse);
					if (joueurAPasse) {
					grille.remplirCases(Plateau.plateau);
					envoyerObjet(Plateau.plateau);  //sans ça ça marche pas: ma foi.....
					partieEnCours=(boolean) recevoirObjet();					
					break;	
				}	    	
				
				envoyerObjet(joueurAChange);		
				if (joueurAChange){
					//chevalet.videCoup();
					//grille.remplirCases(Plateau.plateau);
					envoyerObjet(Plateau.plateau);
					changeJetons();
					
					break;}	
	     	
	     	
				// Dans les autres cas :
				//extraction de la Array liste  de tableau de coups joués.
				scoreJoueur1=scoreJoueur1+eval.scoreCoup(test(chevalet.coup));//il faudra remplacer chavalet.coup par la Aray liste.
				miseAJourTableau();
				grille.remplirCases(Plateau.plateau);//(pour enlever les couleurs)
				grille.score1.setText(nomJoueur1 + " : "+ String.valueOf(scoreJoueur1));
				if (JetonsRestant-chevalet.jetonsAChanger>=0)grille.jetonsRestant.setText("il reste " + (JetonsRestant-chevalet.jetonsAChanger) + " jetons");
				envoyer();
				chevalet.coup.clear();
				if (joueurAfini())   break;
					//sinon on récupère de nouveaux jetons
					envoyerObjet(chevalet.getJetonsAChanger());	     	
					reglette=(String [])recevoirObjet();
					//System.out.println(reglette.length);
					chevalet.majReglette(reglette);
					for (int i =0; i<chevalet.reglette.length;i++)System.out.println(chevalet.reglette[i]);
					break;
	     	}
	     }		
	}
	
	void changeJetons() {					
		envoyerObjet((String[])chevalet.echange.lettresAChanger);		
		reglette=(String[] )recevoirObjet();		
		chevalet.majReglette(reglette);		
		joueurAChange=false;
	}
	
	
	
	private boolean joueurAfini() {
		chevalet.videCoup();
		return ((JetonsRestant==0)&& chevalet.isVide(chevalet.reglette));
	}
	
	
	
	private void finDePartie() {
	
		try { 	     			
			 Thread.sleep (5000);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		fermeture();
	}
	
	
	
	
	
	void miseAJourTableau() {
		for (var i:chevalet.coup) {		
			Plateau.plateau[i.ligne][i.colonne]=i.affichage;}
	}
	
	void fermeture () {
		try { 		
			client.out.close();
		   	client.in.close();
		   	client.clientSocket.close();
		   	System.exit(0);
			} catch (Exception e) {
			     e.printStackTrace();
			    }			
		  System.out.println("fin3");
	}
	
	
	}
		
			
		