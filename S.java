import javax.swing.JOptionPane;

/**
 * 
 * @author jb
 *Pour tester sans avoir à rentrer le nom et l'ip
 */

public class S  {
	public static void main(String[] args) {
		String choix[]={"serveur", "Client"};
		final int nombreDeJoueur =2; //plus tard à mettre en argument ou poser la question		
		int serveurOuClient=JOptionPane.showOptionDialog(null, "Veux-tu être le serveur ou le client?", "option réseau", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, choix,choix[1]);
		if (serveurOuClient==1) { 
			PartieClient partie = new PartieClient("client", "192.168.1.154");
			partie.deroulement();	
								}
		else {			
			Partie partie = new Partie(nombreDeJoueur,"Serveur");
			partie.deroulement();		
		}
	
	}
}