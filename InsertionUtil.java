import java.util.ArrayList;
import java.util.List;

public class InsertionUtil {
	
	/** 
	* API pour l'insertion d'une liste de mots dans la grille.
	* <p>
	* Si l'insertion n'est pas valide, le tableau ne subit aucun
	* changement.
	*
	* @param tableau	La grille du jeu
	* @param lettres	Les lettres a inserer dans la grille
	* @param dictionnaire	Le dictionnaire utilise pour valider les mots dans la grille
	* 						apres l'insertion
	*                  of the destination rectangle in pixels
	*/
	public static boolean put(String[][] tableau, List<List<String>> lettres, List<String> dictionnaire){
		if(lettres == null || lettres.isEmpty()
			|| dictionnaire == null || dictionnaire.isEmpty()
			|| tableau == null) {
			return false;
		}
		int nbLettres = lettres.size();
		int positionVerticale, positionHorizontale;
		List<List<List<String>>> mots;
		if(nbLettres < 1 || nbLettres > 7) {
			return false;
		}
		
		if(verifierLigneAdjacence(lettres)
			&& ((verifierTableauVide(tableau) && verifierMotOccupeCaseCentrale(tableau, lettres))
			|| (verifierAdgacenceAvecAncienMots(tableau, lettres) && verifierCasesVides(tableau, lettres)) )) {
			
			for(List<String> lettre : lettres) {
				positionVerticale = Integer.valueOf(lettre.get(1));
				positionHorizontale = Integer.valueOf(lettre.get(2));
				tableau[positionVerticale][positionHorizontale] = lettre.get(0);
			}
			mots = extraireMots(tableau);
			if(!verifierMots(mots, dictionnaire)) {
				enleverLettres(tableau, lettres);
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
	
	/** 
	* Methode pour verifier si les lettres a inserer sont adjacentes
	* et dans une ligne.
	*
	* @param lettres	{@link List} des lettres ({@link List} de {@link String}) a inserer dans la grille
	* @return <code>true</code> si les lettre sont adjecentes et dans la ligne;
	* <code>false</code> sinon.
	*/
	public static boolean verifierLigneAdjacence(List<List<String>> lettres) {
		if(lettres == null || lettres.isEmpty()) {
			return false;
		}
		int i, n = lettres.size();
		boolean adjacenceVerticale = true, adjacenceHorizontale = true;
		Integer positionVerticale, positionHorizontale, positionVerticalePrecedente, positionHorizontalePrecedente;
		if(lettres.size() < 2) {
			return true;
		}
		
		for(i = 1 ; i < n ; i++) {
			if(lettres.get(i) == null || lettres.get(i).size() != 3) {
				return false;
			}
			positionVerticale = Integer.valueOf(lettres.get(i).get(1));
			positionHorizontale = Integer.valueOf(lettres.get(i).get(2));
			positionVerticalePrecedente = Integer.valueOf(lettres.get(i - 1).get(1));
			positionHorizontalePrecedente = Integer.valueOf(lettres.get(i - 1).get(2));
			
			adjacenceVerticale = !positionVerticale.equals(positionVerticalePrecedente)
									&& adjacenceVerticale;
			adjacenceHorizontale = !positionHorizontale.equals(positionHorizontalePrecedente)
									&& adjacenceHorizontale;
			
			if(adjacenceVerticale && Math.abs(positionVerticale - positionVerticalePrecedente) > 1) {
				return false;
			}
			if(adjacenceHorizontale && Math.abs(positionHorizontale - positionHorizontalePrecedente) > 1) {
				return false;
			}
		}
		return adjacenceVerticale || adjacenceHorizontale;
	}
	
	public static boolean verifierTableauVide(String[][] tableau) {
		if(tableau == null) {
				return false;
		}
		int i, j, n = tableau.length, m;
		for(i = 0 ; i < n ; i++) {
			m = tableau[i].length;
			for(j = 0 ; j < m ; j++) {
				if(tableau[i][j] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean verifierMotOccupeCaseCentrale(String[][] tableau, List<List<String>> lettres) {
		if(lettres == null || lettres.isEmpty() || tableau == null) {
			return false;
		}
		int n = tableau.length;
		int positionVerticale, positionHorizontale;
		int centerIndex = n/2;
		
		if(n % 2 == 0) {
			return false;
		}
		
		for(List<String> lettre : lettres) {
			if(lettre == null || lettre.size() != 3) {
				return false;
			}
			positionVerticale = Integer.valueOf(lettre.get(1));
			positionHorizontale = Integer.valueOf(lettre.get(2));
			if(positionVerticale == centerIndex 
				&& positionHorizontale == centerIndex) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean verifierAdgacenceAvecAncienMots(String[][] tableau, List<List<String>> lettres) {
		if(lettres == null || lettres.isEmpty() || tableau == null) {
			return false;
		}
		int positionVerticale, positionHorizontale, n = tableau.length;
		for(List<String> lettre : lettres) {
			if(lettre == null || lettre.size() != 3) {
				return false;
			}
			positionVerticale = Integer.valueOf(lettre.get(1));
			positionHorizontale = Integer.valueOf(lettre.get(2));
			if(tableau[positionVerticale][positionHorizontale] != null) {
				return false;
			}
			else if((positionVerticale > 0 && tableau[positionVerticale - 1][positionHorizontale] != null)
					|| (positionVerticale < n - 1 && tableau[positionVerticale + 1][positionHorizontale] != null)
					|| (positionHorizontale > 0 && tableau[positionVerticale][positionHorizontale - 1] != null)
					|| (positionHorizontale < n - 1 && tableau[positionVerticale][positionHorizontale + 1] != null)) {
				return true;
			}
		}
		return false;
	}
	
	/** 
	* Verifier si les cases ou les mots doivent etres inseres sont vides.
	*
	* @param tableau	La grille du jeu
	* @param lettres	{@link List} des lettres ({@link List} de {@link String}) a inserer dans la grille
	* @return <code>true</code> si les cases sont vides, et donc pas de conflit;
	* <code>false</code> sinon.
	*/
	public static boolean verifierCasesVides(String[][] tableau, List<List<String>> lettres) {
		if(lettres == null || lettres.isEmpty() || tableau == null) {
			return false;
		}
		int positionVerticale, positionHorizontale;
		for(List<String> lettre : lettres) {
			if(lettre == null || lettre.size() != 3) {
				return false;
			}
			positionVerticale = Integer.valueOf(lettre.get(1));
			positionHorizontale = Integer.valueOf(lettre.get(2));
			if(tableau[positionVerticale][positionHorizontale] != null) {
				return false;
			}
		}
		return true;
	}
	
	/** 
	* Extraction des mots presents dans la grille.
	*
	* @param tableau	La grille du jeu
	* @return {@Link List<List<List<String>>>} contenant les mots extraits de la grille
	*/
	public static List<List<List<String>>> extraireMots(String[][] tableau) {
		if(tableau == null) {
			return null;
		}
		int i, j, k, n = tableau.length;
		List<String> lettre;
		List<List<String>> mot;
		List<List<List<String>>> mots = new ArrayList<List<List<String>>>();
		for(i = 0 ; i < n ; i++) {
			for(j = 0 ; j < n ; j++) {
				if(tableau[i][j] != null) {
					if(i == 0 || tableau[i - 1][j] == null) {
						mot = new ArrayList<List<String>>();
						k = i;
						while(k < n && tableau[k][j] != null) {
							lettre = new ArrayList<String>();
							lettre.add(tableau[k][j]);
							lettre.add(String.valueOf(k));
							lettre.add(String.valueOf(j));
							mot.add(lettre);
							k++;
						}
						if(mot.size() > 1) {
							mots.add(mot);
						}
						
					}
					if(j == 0 || tableau[i][j - 1] == null) {
						mot = new ArrayList<List<String>>();
						k = j;
						while(k < n && tableau[i][k] != null) {
							lettre = new ArrayList<String>();
							lettre.add(tableau[i][k]);
							lettre.add(String.valueOf(i));
							lettre.add(String.valueOf(k));
							mot.add(lettre);
							k++;
						}
						if(mot.size() > 1) {
							mots.add(mot);
						}
					}
				}
			}
		}
		return mots;
	}
	
	public static List<List<CaseCourante>> motsEnCaseCourante(List<List<List<String>>> mots){
		if(mots == null || mots.isEmpty()) {
			return null;
		}
		int positionVerticale, positionHorizontale;
		List<List<CaseCourante>> motsEnCaseCourante = new ArrayList<List<CaseCourante>>();
		for(List<List<String>> mot : mots) {
			if(mot == null || mot.isEmpty()) {
				return null;
			}
			List<CaseCourante> motEnCaseCourante = new ArrayList<CaseCourante>();
			for(List<String> lettre : mot) {
				if(lettre == null || lettre.isEmpty()) {
					return null;
				}
				positionVerticale = Integer.valueOf(lettre.get(1));
				positionHorizontale = Integer.valueOf(lettre.get(2));
				CaseCourante caseCourante = new CaseCourante(positionVerticale, positionHorizontale, lettre.get(0));
				motEnCaseCourante.add(caseCourante);
				
			}
			motsEnCaseCourante.add(motEnCaseCourante);
		}
		
		return motsEnCaseCourante;
	}
	
	public static boolean verifierMots(List<List<List<String>>> mots, List<String> dictionnaire) {
		if(mots == null || mots.isEmpty()
			|| dictionnaire == null || dictionnaire.isEmpty()) {
			return false;
		}
		for(List<List<String>> mot : mots) {
			if(mot == null) {
				return false;
			}
			if(!verifierMot(mot, dictionnaire)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean verifierMot(List<List<String>> mot, List<String> dictionnaire) {
		if(mot == null || mot.isEmpty()
			|| dictionnaire == null || dictionnaire.isEmpty()) {
			return false;
		}
		String motString = "";
		for(List<String> lettre : mot) {
			if(lettre == null || lettre.size() != 3) {
				return false;
			}
			motString += lettre.get(0);
		}
		for(String motDictionnaire : dictionnaire) {
			if(motDictionnaire.equals(motString)) {
				return true;
			}
		}
		return false;
	}
	
	/** 
	* Enlever les lettres de la grille.
	*
	* @param tableau	La grille du jeu
	* @param lettres	Lettres a enlever de la grille
	*/
	public static void enleverLettres(String[][] tableau, List<List<String>> lettres) {
		if(lettres == null || lettres.isEmpty() || tableau == null) {
			return;
		}
		int positionVerticale, positionHorizontale;
		for(List<String> lettre : lettres) {
			if(lettre == null || lettre.size() != 3) {
				return;
			}
			positionVerticale = Integer.valueOf(lettre.get(1));
			positionHorizontale = Integer.valueOf(lettre.get(2));
			tableau[positionVerticale][positionHorizontale] = null;
		}
	}
}
