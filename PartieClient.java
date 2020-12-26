import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.management.StringValueExp;

public class PartieClient {
	boolean partieEnCours;		
	static boolean joueurAjoué;
	static boolean joueurAPassé;
	final boolean OnAttendQueLeJoueurJoue=true;
	static int scoreJoueur1=0;
	static int scoreServeur=0;
	public String nomServeur, nomJoueur1="JB";
	Chevalet chevalet;
	GestionGrille grille; 
	EvalCoup eval;
	static Plateau plateau;					
	Client client;
	String reglette[];	
	PartieClient(){
		client = new Client();
		 plateau= new Plateau();	                  
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
		while (partieEnCours == true) {	    				
			ilJoue() ;		
			//tester si c'est la fin de la partie
			jeJoue();			 
			envoyer();
			chevalet.coup.clear();
			chevalet.majReglette((String [])recevoirObjet());
			//tester si c'est la fin de la partie
			
		
	
		}	
	}
	
	//Creer une array liste de tableau de  cases courantes pour tester. Attention la valeur est à zéro.
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
		envoyerObjet(chevalet.getJetonsAChanger());
	}
	
	
	private void envoyerObjet(Object objetEnvoyé) {
		try { 		
		client.out.writeObject(objetEnvoyé);
		client.out.flush();  
		} catch (IOException e) {
	           e.printStackTrace();
		}
	}
	
	
	
	
	private void ilJoue() {
		joueurAjoué=true;		
		try {
		Plateau.plateau =(String[][]) client.in.readObject();
    	if (chevalet.coup.size()!=0)chevalet.videCoup();                  
        grille.remplirCases(Plateau.plateau);      
        scoreServeur=(int) client.in.readObject();
        grille.score2.setText(nomServeur + " : " + String.valueOf(scoreServeur));
        int JetonsRestant=(int) client.in.readObject();
        grille.jetonsRestant.setText("il reste " + JetonsRestant + " jetons");
        //il faut aussi envoyer le nombre de lettres restantes et l'état de la partie(si le serveur a gagné ou pas)
        joueurAjoué=false;    
        if (testVictoire()) finDePartie();
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
	}
	
	
	
	private void jeJoue() {
		joueurAjoué=false;
		joueurAPassé=false;
		while (OnAttendQueLeJoueurJoue) {	     		  
		chevalet.valider.setBackground(Color.green);		   
	     	if (joueurAjoué==true) {		     	
	     	if (joueurAPassé)break;	
	     	//extraction de la Array liste  de tableau de coups joués.
	     	scoreJoueur1=scoreJoueur1+eval.scoreCoup(test(chevalet.coup));//il faudra remplacer chavalet.coup par la Aray liste.
	     	miseAJourTableau();
	     	grille.remplirCases(Plateau.plateau);//(pour enlever les couleurs)
	     	grille.score1.setText(nomJoueur1 + " : "+ String.valueOf(scoreJoueur1));
	     	
	     	break;
	     	}
	     }		
	}
	
	private boolean testVictoire() {
		return (scoreJoueur1==15);
	}
	
	private void finDePartie() {
		fermeture();
	}
	
	
	
	
	
	void miseAJourTableau() {
		for (var i:chevalet.coup) {		
			Plateau.plateau[i.ligne][i.colonne]=i.lettre;}
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
		
			
		