import java.util.ArrayList;
import java.util.Collections;


public class VerifAdjacence {

	public static boolean verifierAdjacence(ArrayList<CaseCourante> coup) {

		if (coup.size()==0) return false;
		
		ArrayList<Integer> listelignes = new ArrayList<Integer>();
		ArrayList<Integer> listecolonnes = new ArrayList<Integer>();		
		for (var i:coup) {
			listelignes.add(i.ligne);
			listecolonnes.add(i.colonne);
		}
		
		int minligne = Collections.min(listelignes);// Retourner la plus petite valeur des lignes
		int maxligne = Collections.max(listelignes);
		int mincolonne = Collections.min(listecolonnes);
		int maxcolonne = Collections.max(listecolonnes);
		
		if (maxligne-minligne==coup.size()-1 && maxcolonne-mincolonne==0) return true; //alignement sur une colonne
		
		if (maxcolonne-mincolonne==coup.size()-1 && maxligne-minligne==0) return true; //alignement sur une ligne
		
		return false;
		
		
	}
	
	
	
}
		
	
	
	