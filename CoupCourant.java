import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;


public class CoupCourant{
private ArrayList <CaseCourante> coup;
	
	public boolean verificateur(ArrayList <CaseCourante> coup) {
		this.coup=coup;
		if (Plateau.plateau[7][7].equals("*"))	{
			if (!verificationEtoile()) {
			JOptionPane.showMessageDialog(null, "le mot doit etre sur l'étoile et compter deux lettres");	
			return false;			
			}
		}	
			
		if (!verifierAdjacence()) {
			JOptionPane.showMessageDialog(null, "coup non valide");					
			return false;
		}
		
		if ((!verifierAdgacenceAvecAncienMots(Plateau.plateau, coup)) && (!Plateau.plateau[7][7].equals("*"))){
			JOptionPane.showMessageDialog(null, "coup non valide2");	
			return false;
		}
				
		return true;
	}
	
	public boolean verificationEtoile() {
		if (coup.size()<2) return false;
		for(var i:coup) if (i.bonus.equals("*"))return true;
		return false;
		}

	
	
	


		public boolean verifierAdjacence() {			
			if (coup.size()==0) return false;			
			ArrayList<Integer> listelignes = new ArrayList<Integer>();
			ArrayList<Integer> listecolonnes = new ArrayList<Integer>();		
			for (var i:coup) {
				listelignes.add(i.ligne);
				listecolonnes.add(i.colonne);
			}
			
			int minLigne = Collections.min(listelignes);// Retourner la plus petite valeur des lignes
			int maxLigne = Collections.max(listelignes);
			int minColonne = Collections.min(listecolonnes);
			int maxColonne = Collections.max(listecolonnes);
			
			if (maxColonne-minColonne==0) return verifFinalColonne(minLigne,maxLigne, minColonne); //alignement sur une colonne			
			if (maxLigne-minLigne==0) return verifFinalLigne(minColonne, maxColonne, minLigne); //alignement sur une ligne
			
			return false;			
		}

		public boolean verifFinalColonne( int min, int max, int k) {
			for (int count = min; count<max+1;count++) {
				if ((retrouveCaseDansCoup(count,k)==-1)&& (Plateau.plateau)[count][k].length()<3)return false;
			}
			return true;
		}

		
		public  boolean verifFinalLigne(int min, int max, int k) {
			for (int count = min; count<max+1;count++) {				
				if ((retrouveCaseDansCoup(k,count)==-1)&& (Plateau.plateau)[k][count].length()<3)return false;
			}
			return true;
		}	
		
		int retrouveCaseDansCoup(int ligne, int colonne) {  //methode en double: il faudra trouve où est la meilleure place
			for (var i : coup) {
				if (i.ligne ==ligne && i.colonne ==colonne) return coup.indexOf(i);	
			}
			 return -1;
		}
		
		
		
		
		/** 
		* Methode pour verifier si les lettres à inserer sont adjacentes avec les anciennes lettres
		* 
		*
		* @param lettres	{@link List} des lettres {@link CaseCourante}) a inserer dans la grille
		* @return <code>true</code> si les lettre sont adjecentes avec les anciennes lettres;
		* <code>false</code> sinon.
		*/
		public boolean verifierAdgacenceAvecAncienMots(String[][] tableau, List<CaseCourante> lettres) {
			if(lettres == null || lettres.isEmpty() || tableau == null) {
				return false;
			}
			int positionVerticale, positionHorizontale, n = tableau.length;
			for(CaseCourante lettre : lettres) {
				if(lettre == null) {
					return false;
				}
				positionVerticale = lettre.getLigne();
				positionHorizontale = lettre.getColonne();
				if(tableau[positionVerticale][positionHorizontale] != null 
						&& !tableau[positionVerticale][positionHorizontale].equals("")
						&& !tableau[positionVerticale][positionHorizontale].equals("MT")
						&& !tableau[positionVerticale][positionHorizontale].equals("MD")
						&& !tableau[positionVerticale][positionHorizontale].equals("LT")						
						&& !tableau[positionVerticale][positionHorizontale].equals("LD")
						&& !tableau[positionVerticale][positionHorizontale].equals("*")) {
					return false;
				}
				else if((positionVerticale > 0 
						&& tableau[positionVerticale - 1][positionHorizontale] != null 
						&& !tableau[positionVerticale - 1][positionHorizontale].equals("")
						&& !tableau[positionVerticale - 1][positionHorizontale].equals("MT")
						&& !tableau[positionVerticale - 1][positionHorizontale].equals("MD")
						&& !tableau[positionVerticale - 1][positionHorizontale].equals("LT")						
						&& !tableau[positionVerticale - 1][positionHorizontale].equals("LD")
						&& !tableau[positionVerticale - 1][positionHorizontale].equals("*"))
						|| (positionVerticale < n - 1 &&
						tableau[positionVerticale + 1][positionHorizontale] != null 
						&& !tableau[positionVerticale + 1][positionHorizontale].equals("")
						&& !tableau[positionVerticale + 1][positionHorizontale].equals("MT")
						&& !tableau[positionVerticale + 1][positionHorizontale].equals("MD")
						&& !tableau[positionVerticale + 1][positionHorizontale].equals("LT")						
						&& !tableau[positionVerticale + 1][positionHorizontale].equals("LD")
						&& !tableau[positionVerticale + 1][positionHorizontale].equals("*"))
						|| (positionHorizontale > 0 
						&& tableau[positionVerticale][positionHorizontale - 1] != null 
						&& !tableau[positionVerticale][positionHorizontale - 1].equals("")
						&& !tableau[positionVerticale][positionHorizontale - 1].equals("MT")
						&& !tableau[positionVerticale][positionHorizontale - 1].equals("MD")
						&& !tableau[positionVerticale][positionHorizontale - 1].equals("LT")						
						&& !tableau[positionVerticale][positionHorizontale - 1].equals("LD")
						&& !tableau[positionVerticale][positionHorizontale - 1].equals("*"))
						|| (positionHorizontale < n - 1 
						&& tableau[positionVerticale][positionHorizontale + 1] != null 
						&& !tableau[positionVerticale][positionHorizontale + 1].equals("")
						&& !tableau[positionVerticale][positionHorizontale + 1].equals("MT")
						&& !tableau[positionVerticale][positionHorizontale + 1].equals("MD")
						&& !tableau[positionVerticale][positionHorizontale + 1].equals("LT")						
						&& !tableau[positionVerticale][positionHorizontale + 1].equals("LD")
						&& !tableau[positionVerticale][positionHorizontale + 1].equals("*"))) {
					return true;
				}
			}
			return false;
		}
		
		
		
}	
	
	

