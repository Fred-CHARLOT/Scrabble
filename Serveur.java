import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
	int nombreDeStream;
	ServerSocket sSock;
	ObjectOutputStream out [];
	ObjectInputStream in[];
	
	Serveur(int nombreDeJoueur){
		this.nombreDeStream=nombreDeJoueur-1;
			
				
		//Cr�action du r�seau
		int compteurSocket=0;
		boolean connexionTermin�e=false;
		try {
			sSock = new ServerSocket(1800);//cr�ation du server sur le port1800
			System.out.println("en attente de connection");
						
			while(!connexionTermin�e) {				//
				Socket socket = sSock.accept(); //cree un socket lors d'une connection. 1er point d'arret du programme					
				System.out.println("Un client vient de se connecter");							
				out=new ObjectOutputStream[nombreDeStream]; 
				in=new ObjectInputStream[nombreDeStream];		
				out[compteurSocket]= new ObjectOutputStream(socket.getOutputStream());//creation des flux '�change
				out[compteurSocket].flush(); // vider le buffer??
				in[compteurSocket]= new ObjectInputStream(socket.getInputStream());									
				compteurSocket++; //incrementer le nombre de Socket	
				if (compteurSocket==nombreDeStream) {connexionTermin�e=true;} //fermer la fenetre de connexion
			}

		} catch (IOException e) {
			e.printStackTrace();
		  }
	
	}
	
	
	
	
}