import java.util.ArrayList;
import java.util.Collections;

public class Verificateur {

	public  static boolean verifAlignementPremierCoup (ArrayList<CaseCourante> coup) {

		if (coup.size()==0) return false;

		ArrayList<Integer> listelignes = new ArrayList<Integer>();
		ArrayList<Integer> listecolonnes = new ArrayList<Integer>();
		for (int i=0;i<coup.size();i++) {
		listelignes.add(coup.get(i).ligne);
		listecolonnes.add(coup.get(i).colonne);
		}

		int minligne = Collections.min(listelignes);// Retourner la plus petite valeur des lignes
		int maxligne = Collections.max(listelignes);
		int mincolonne = Collections.min(listecolonnes);
		int maxcolonne = Collections.max(listecolonnes);

		if (maxcolonne==7 && mincolonne==7 && maxligne-minligne==coup.size() - 1 && minligne<=7 && maxligne >=7) return true; //alignement sur colonne centrale

		if (maxligne==7 && minligne==7 && maxcolonne-mincolonne ==coup.size() - 1 && mincolonne<=7 && maxcolonne >=7) return true; //alignement sur ligne centrale

		return false;


		public static boolean verifierAdjacence(ArrayList<CaseCourante> coup) {

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
			
			if (maxColonne-minColonne==0) return verifFinalColonne(coup, minLigne,maxLigne, minColonne); //alignement sur une colonne
			
			if (maxLigne-minLigne==0) return verifFinalLigne(coup, minColonne, maxColonne, minLigne); //alignement sur une ligne
			
			return false;
			
			
		}

		public static boolean verifFinalColonne(ArrayList<CaseCourante> coup, int min, int max, int k) {
			for (int count = min; count<max+1;count++) {
				System.out.println(count);
				if ((retrouveCaseDansCoup(count,k)==-1)&& (Plateau.plateau)[count][k].length()<3)return false;
			}
			return true;
		}

		public static boolean verifFinalLigne(int min, int max, int k) {
			for (int count = min; count<max+1;count++) {
				System.out.println(count);
				if ((retrouveCaseDansCoup(k,count)==-1)&& (Plateau.plateau)[k][count].length()<3)return false;
			}
			return true;
		}	
		
		
		
		
		
		}	
	
	

