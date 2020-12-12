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
static boolean joueurAjou�;
static boolean joueurAPass�;
	Partie(int nombreDeJoueur){
		joueurAjou� = false;
		joueurAPass�=false;
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
		    } catch (Exception e) {  //pour r�cup�rer la main dans ce thread
		        e.printStackTrace();
		    }
			if (joueurAjou�==true) {
				if (joueurAPass�)break;
				miseAJourTableau();   //met � jour le plateau � partir du coup courant
				break;
			}
		}	
		
			
			
			
			try {									
				serveur.out[0].writeObject(Plateau.plateau); 
				serveur.out[0].flush();	//envoyer le plateau mis � jour au joueur suivant	
				chevalet.coup.clear();//pour ne pas perdre des lettres
				grille.remplirCases(Plateau.plateau);
				
				
				Object objetRe�u = serveur.in[0].readObject();  //r�ceptionner le coup du joueur suivant
				if (chevalet.coup.size()!=0) chevalet.videCoup();
				Plateau.plateau =(String[][]) objetRe�u;			
				grille.remplirCases(Plateau.plateau);
				joueurAjou�=false;
				joueurAPass�=false;
			} catch (ClassNotFoundException e) {		
			e.printStackTrace();
			} 
			catch (IOException e) {	
				e.printStackTrace();
			}	
		
		}		
		
		System.out.println("fin de la partie"); //pour tester le lieu o� fermer le socket
		fermeture();
		
		
	}//fin de d�roulement
	
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
