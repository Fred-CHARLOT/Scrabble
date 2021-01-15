import javax.swing.JOptionPane;

/**
 * 
 * 
 *Il s'agit du programme principal
 *Permet de choisir si on sera  le serveur et le client(dans ce cas il faut rentrer l'ip du serveur),
 *de remplir son nom et de lancer la partie.
 */

public class Scrabble {
	
	public static void main(String[] args) {
		String choix[]={"serveur", "Client"};
		final int nombreDeJoueur =2; //plus tard à mettre en argument et poser la question
		String nom =JOptionPane.showInputDialog("Bonjour, comment t'appelles-tu?");
		int serveurOuClient=JOptionPane.showOptionDialog(null, "Veux-tu être le serveur ou le client?", "option réseau", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, choix,choix[1]);
		if (serveurOuClient==1) { 
			String ip =JOptionPane.showInputDialog("Quelle est l'adresse ip du serveur?");
			PartieClient partie = new PartieClient(nom, ip);
			partie.deroulement();	
								}
		else {			
			Partie partie = new Partie(nombreDeJoueur,nom);
			partie.deroulement();		
		}
		
		
		
		
	}
}

