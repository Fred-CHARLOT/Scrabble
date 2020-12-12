import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;



public class Partie {
Serveur serveur;
Plateau plateau;		
SacDeJetons sac;
Chevalet chevalet;
GestionGrille grille;
boolean partieEnCours;
static boolean joueurAjoué;
static boolean joueurAPassé;
	Partie(int nombreDeJoueur){
		joueurAjoué = false;
		joueurAPassé=false;
		serveur = new Serveur(nombreDeJoueur);
		plateau=new Plateau();
		sac= new SacDeJetons();
		chevalet = new Chevalet(0);
		grille = new GestionGrille(0,chevalet);
		
	}
	
	
	public 	void deroulement() {
		partieEnCours = true;
		boolean OnAttendQueLeJoueurJoue=true;
		while (partieEnCours==true) {  
			
			chevalet.valider.setBackground(Color.green);
			while (OnAttendQueLeJoueurJoue) {  //le joueur du serveur joue
			
			
				try { 
		        Thread.sleep (1);
		    } catch (Exception e) {  //pour récupérer la main dans ce thread
		        e.printStackTrace();
		    }
			if (joueurAjoué==true) {
				if (joueurAPassé)break;
				miseAJourTableau();   //met à jour le plateau à partir du coup courant
				break;
			}
		}	
		
			
			
			
			try {									
				serveur.out[0].writeObject(Plateau.plateau); 
				serveur.out[0].flush();	//envoyer le plateau mis à jour au joueur suivant	
				chevalet.coup.clear();//pour ne pas perdre des lettres
				grille.remplirCases(Plateau.plateau);
				
				
				Object objetReçu = serveur.in[0].readObject();  //réceptionner le coup du joueur suivant
				if (chevalet.coup.size()!=0) chevalet.videCoup();
				Plateau.plateau =(String[][]) objetReçu;			
				grille.remplirCases(Plateau.plateau);
				joueurAjoué=false;
				joueurAPassé=false;
			} catch (ClassNotFoundException e) {		
			e.printStackTrace();
			} 
			catch (IOException e) {	
				e.printStackTrace();
			}	
		
		}		
		
		System.out.println("fin de la partie"); //pour tester le lieu où fermer le socket
		fermeture();
		
		
	}//fin de déroulement
	
	void miseAJourTableau() {							
		for (var i:chevalet.coup) {		
			Plateau.plateau[i.ligne][i.colonne]=i.lettre;}
		
	}

	






	void fermeture() {
		try {
		for (int i=0;i<serveur.nombreDeStream; i++) {
			serveur.out[i].close();
			serveur.in[i].close();
		}
		serveur.sSock.close();
		} 
		catch (IOException e) {	
			e.printStackTrace();
		}

	}
	
	
	
}
