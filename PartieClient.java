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
	public String nom;
	Chevalet chevalet;
	GestionGrille grille; 
	static Plateau plateau;					
	Client client;
	String reglette[];	
	PartieClient(){
		client = new Client();
		 plateau= new Plateau();	
         
         grille= new GestionGrille(1,chevalet);
         
         
         try {								
				reglette =(String [])client.in.readObject();
				chevalet=new Chevalet(1,reglette);
				nom =(String) client.in.readObject();						
				grille.score2.setText( nom + " : " + String.valueOf(scoreServeur));
				grille.score1.setText( " Client : " + String.valueOf(scoreServeur));					
				} catch (IOException | ClassNotFoundException e) {
			           e.printStackTrace();
		}     
	}	
	
	
	public void deroulement() {
		partieEnCours =true;			 
		while (partieEnCours == true) {	    				
			ilJoue() ;				
			jeJoue();			 
			envoyerObjet(Plateau.plateau); 
			envoyerObjet(scoreJoueur1);
			//envoyer nombre de jetons à changer
			//recevoir nouveaux jetons.
			//afficher les nouveaux jetons.
		}	
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
        grille.score2.setText(nom + " : " + String.valueOf(scoreServeur));
        joueurAjoué=false;    
        if (scoreServeur==3) fermeture();
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
	     	scoreJoueur1++;
	     	miseAJourTableau();
	     	grille.remplirCases(Plateau.plateau);//(pour enlever les couleurs)
	     	grille.score1.setText(String.valueOf("mon score : " +scoreJoueur1));
	     	chevalet.coup.clear();
	     	break;
	     	}
	     }		
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
		
			
		