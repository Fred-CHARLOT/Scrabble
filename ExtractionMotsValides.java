import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExtractionMotsValides {


	public static List<String> dictionnaire;
	
	/** 
	* API pour l'insertion d'une liste de mots dans la grille.
	* <p>
	* Si l'insertion n'est pas valide, le tableau ne subit aucun
	* changement.
	*
	* @param tableau	La grille du jeu
	* @param lettres	{@link List} des lettres {@link CaseCourante} a inserer dans la grille
	* @param grille		L'objet {@link GestionGrille} du jeu
	* 
	* @return 			Les mots nouvellement insérés sous forme de {@link List} de {@link List}
	* 					contenant les {@link CaseCourante} de chaque mot.
	*/
	public static List<List<CaseCourante>> put(String[][] tableau, List<CaseCourante> lettres){
		if(lettres == null || lettres.isEmpty()
			|| tableau == null) {
			return null;
		}
		if(dictionnaire == null || dictionnaire.isEmpty()) {
			setDictionnaire(null);
		}
		int nbLettres = lettres.size();
		int positionVerticale, positionHorizontale;
		List<List<CaseCourante>> mots;
		if(nbLettres < 1 || nbLettres > 7) {
			return null;
		}
		//Les variables suivantes sont uniquement utilisees pour le debogage
//		boolean verifierLigneAdjacence = verifierLigneAdjacence(lettres);
//		boolean verifierTableauVide = verifierTableauVide(tableau);
//		boolean verifierMotOccupeCaseCentrale = verifierMotOccupeCaseCentrale(tableau, lettres);
//		boolean verifierAdgacenceAvecAncienMots = verifierAdgacenceAvecAncienMots(tableau, lettres);
//		boolean verifierCasesVides = verifierCasesVides(tableau, lettres);
		/*if(verifierLigneAdjacence(lettres)
			&& ((verifierTableauVide(tableau) && verifierMotOccupeCaseCentrale(tableau, lettres))
			|| (verifierAdgacenceAvecAncienMots(tableau, lettres) && verifierCasesVides(tableau, lettres)) )) {
			*/
			for(CaseCourante lettre : lettres) {
				positionVerticale = lettre.getLigne();
				positionHorizontale = lettre.getColonne();
				tableau[positionVerticale][positionHorizontale] = lettre.affichage;				
			}
			mots = extraireMots(tableau, lettres);//, grille
			if(!verifierMots(mots)) {
			enleverLettres(tableau, lettres);
				
			return null;
			}
		 
			return mots;
			
		//}
		//return null;
	}
	
	/** 
	* Extraction des mots presents dans la grille.
	*
	* @param tableau	La grille du jeu
	* @return {@Link List<List<CaseCourante>>} contenant les mots extraits de la grille
	*/
	
	public static List<List<CaseCourante>> extraireMots(String[][] tableau, List<CaseCourante> lettres) {//, GestionGrille grille
		if(tableau == null) {
			return null;
		}
		int i, j, k, n = tableau.length;
		List<CaseCourante> mot;
		List<List<CaseCourante>> mots = new ArrayList<List<CaseCourante>>();
		boolean nouveauMot, nouvelleLettre = false;
		for(i = 0 ; i < n ; i++) {
			for(j = 0 ; j < n ; j++) {
				if(tableau[i][j] != null 
				&& !tableau[i][j].equals("")
				&& !tableau[i][j].equals("MT")
				&& !tableau[i][j].equals("MD")
				&& !tableau[i][j].equals("LT")				
				&& !tableau[i][j].equals("LD")
				&& !tableau[i][j].equals("*")) {
					if(i == 0 || tableau[i - 1][j] == null 
							|| tableau[i - 1][j].equals("")
							|| tableau[i - 1][j].equals("MT")
							|| tableau[i - 1][j].equals("MD")
							|| tableau[i - 1][j].equals("LT")							
							|| tableau[i - 1][j].equals("LD")
							|| tableau[i - 1][j].equals("*")) {
						mot = new ArrayList<CaseCourante>();
						k = i;
						nouveauMot = false;
						while(k < n && tableau[k][j] != null 
								&& !tableau[k][j].equals("")
								&& !tableau[k][j].equals("MT")
								&& !tableau[k][j].equals("MD")
								&& !tableau[k][j].equals("LT")								
								&& !tableau[k][j].equals("LD")
								&& !tableau[k][j].equals("*")) {
							for(CaseCourante caseCourante : lettres) {
								nouvelleLettre = false;
								if(caseCourante.getLigne() == k && caseCourante.getColonne() == j) {
									nouveauMot = true;
									nouvelleLettre = true;
									mot.add(caseCourante);
									break;
								}
							}
							if(!nouvelleLettre) {
								mot.add(new CaseCourante(k, j,"" , tableau[k][j]));//grille.cases[k][j].getText()
								
							}
							k++;
						}
						if(mot.size() > 1 && nouveauMot) {
							mots.add(mot);
						}
					}
					if(j == 0 || tableau[i][j - 1] == null
							|| tableau[i][j - 1].equals("")
							|| tableau[i][j - 1].equals("MT")
							|| tableau[i][j - 1].equals("MD")
							|| tableau[i][j - 1].equals("LT")							
							|| tableau[i][j - 1].equals("LD")
							|| tableau[i][j - 1].equals("*")) {
						mot = new ArrayList<CaseCourante>();
						k = j;
						nouveauMot = false;
						while(k < n && tableau[i][k] != null 
								&& !tableau[i][k].equals("")
								&& !tableau[i][k].equals("MT")
								&& !tableau[i][k].equals("MD")
								&& !tableau[i][k].equals("LT")								
								&& !tableau[i][k].equals("LD")
								&& !tableau[i][k].equals("*")) {
							for(CaseCourante caseCourante : lettres) {
								nouvelleLettre = false;
								if(caseCourante.getLigne() == i && caseCourante.getColonne() == k) {
									nouveauMot = true;
									nouvelleLettre = true;
									mot.add(caseCourante);
									break;
								}
							}
							if(!nouvelleLettre) {
								mot.add(new CaseCourante(i, k, "", tableau[i][k]));// grille.cases[i][k].getText()
							}
							k++;
						}
						if(mot.size() > 1 && nouveauMot) {
							mots.add(mot);
							//afficherMot(mot);
						}
					}
				}
			}
		}
		return mots;
	}
	
	
	
	
	public static void afficherMot(List<CaseCourante> lettres) {
		System.out.println("Affichage mot: ");
		for(CaseCourante lettre : lettres) {
			System.out.print(lettre.lettre);
		}
		System.out.println();
	}
	
	public static boolean verifierMots(List<List<CaseCourante>> mots) {
		if(mots == null || mots.isEmpty()) {
			return false;
		}
		if(dictionnaire == null || dictionnaire.isEmpty()) {
			setDictionnaire(null);
		}
		for(List<CaseCourante> mot : mots) {
			if(mot == null) {
				return false;
			}
			if(!verifierMot(mot)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean verifierMot(List<CaseCourante> mot) {
		if(mot == null || mot.isEmpty()
			|| dictionnaire == null || dictionnaire.isEmpty()) {
			return false;
		}
		if(dictionnaire == null || dictionnaire.isEmpty()) {
			setDictionnaire(null);
		}
		String motString = "";
		for(CaseCourante lettre : mot) {
			if(lettre == null) {
				return false;
			}
			motString += lettre.getLettre();
		}
		for(String motDictionnaire : dictionnaire) {
			if(motDictionnaire.toLowerCase().equals(motString.toLowerCase())) {
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
	
	
	
	
	public static void enleverLettres(String[][] tableau, List<CaseCourante> lettres) {
		if(lettres == null || lettres.isEmpty() || tableau == null) {
			return;
		}		
		int positionVerticale, positionHorizontale;
		for(CaseCourante lettre : lettres) {
			if(lettre == null) {
				return;
			}
			positionVerticale = lettre.getLigne();
			positionHorizontale = lettre.getColonne();			
			tableau[positionVerticale][positionHorizontale]=lettre.bonus;			
		}
	}
	
	public static void setDictionnaire(String emplacement) {
		
		if(emplacement == null) {
			emplacement = "resources/francais.txt";
		}
		
	    ArrayList<String> listOfLines = new ArrayList<>();

	    String line;
		try {
			BufferedReader bufReader = new BufferedReader(new FileReader(emplacement));
			line = bufReader.readLine();
			while (line != null) {
				listOfLines.add(line);
				line = bufReader.readLine();
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dictionnaire = listOfLines;
	}
	
}	
	
	
	