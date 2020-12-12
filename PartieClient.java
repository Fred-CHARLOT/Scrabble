import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;

public class PartieClient {
	boolean partieEnCours = true;		
	static boolean joueurAjoué;
	static boolean joueurAPassé;
	static int scoreJoueur1;
	Chevalet chevalet;
	GestionGrille grille; 
	static Plateau plateau;					//Static
	Client client;
		
	PartieClient(){
		client = new Client();
		 plateau= new Plateau();	
         chevalet=new Chevalet(1);
         grille= new GestionGrille(1,chevalet);
		
	}	
		
		
	public void deroulement() {
		boolean OnAttendQueLeJoueurJoue=true;
		joueurAjoué=true;
		joueurAPassé=false;
		try {  	
		while (partieEnCours == true) {	    	  	
	    	//on attend le coup du serveur
	    	Object objetReçu = client.in.readObject();
	    	if (chevalet.coup.size()!=0)chevalet.videCoup();
	         Plateau.plateau =(String[][]) objetReçu;	         
	         grille.remplirCases(Plateau.plateau);
	         joueurAjoué=false;
	        //Le client joue   
	        chevalet.valider.setBackground(Color.green);
	     	while (OnAttendQueLeJoueurJoue) {	     		  
	     		 try { 	     			
	     			 Thread.sleep (1);
	     			 } catch (Exception e) {
	     		     e.printStackTrace();
	     		    }	     		
	     		if (joueurAjoué==true) {	
	     		if (joueurAPassé)break;	
	     		miseAJourTableau();
	     		break;
	     		}
	     	} 
	     	
	     	//if (joueurAjoué==true){   //pourquoi la condition?
	     	
	     	client.out.writeObject(Plateau.plateau);
			client.out.flush();   	 
			chevalet.coup.clear();//pour ne pas perdre des lettres
			grille.remplirCases(Plateau.plateau);
			joueurAjoué=true;
			joueurAPassé=false;
	     	//}
			     
	    System.out.println("fin1");
		}
		 
		System.out.println("fin2");//a priori mal placé
		client.out.close();
	   	client.in.close();
	   	client.clientSocket.close();
		
		} catch (IOException | ClassNotFoundException e) {
	           e.printStackTrace();
	      }
	     
	  System.out.println("fin3");
	}
	
	
	
	
	
	
	void miseAJourTableau() {
		for (var i:chevalet.coup) {		
			Plateau.plateau[i.ligne][i.colonne]=i.lettre;}
	}
	
	
	
}
		
			
		