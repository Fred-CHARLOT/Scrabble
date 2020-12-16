import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvalCoup {
	
	ArrayList <CaseCourante[]> mots;
	
	
		
	/*public static boolean MotEnLigne (String[] mot) {
		if ((mot[0]).charAt(0) ==(mot[1]).charAt(0)) return true;
		else return false;
	}*/
				
	EvalCoup(ArrayList <CaseCourante[]> EnsMots) {
		
		this.mots=EnsMots;
		
	}
	
	
	public static int SommeLettres (CaseCourante[] coup) {
		
			int somme = 0;
			/*for (int k=0; k<coup.size();k++)*/
			
			for (var k:coup)	{
				
				int b = k.valeur;
				/*System.out.println(b);*/
				String c = k.bonus;
				/*System.out.println(c)*/
				if (c.equals("LD")) {somme = somme + 2*b;}
				if (c.equals("LT")) {somme = somme + 3*b;}
				if (!c.equals("LD") && !c.equals("LT")) {somme = somme + b;}
												 
			}
		return somme;
	}
	
		
	
	public static int ScoreMot(CaseCourante[] coup) {
			
			int score = SommeLettres(coup);
			int Dcoeff = 1;
			int Tcoeff = 1;
			
			for (var k:coup) {
				
				String c = k.bonus;
				if (c.equals("MD")) {Dcoeff=2*Dcoeff;}
				if (c.equals("MT")) {Tcoeff=3*Tcoeff;}
			}
			
				score = score * Dcoeff * Tcoeff;
				if (coup.length==7) {score = score + 50;}
			
			return score;
		
		}
			
	
	/*public ArrayList <CaseCourante[]> EnsMots(ArrayList <String[]> coup) {
			ArrayList <CaseCourante[]> mot = new ArrayList <CaseCourante[]>();
		
			for (int k=0;k<coup.size(); k++) {
			System.out.println(k);
			
				CaseCourante[] extrait = new CaseCourante[coup.get(k).length];
			
			for (int j=0; j<coup.get(k).length;j++) {
				
				int ligne = Character.getNumericValue(coup.get(k)[j].charAt(0));
				System.out.print(ligne);
				int colonne = Character.getNumericValue(coup.get(k)[j].charAt(1));
				System.out.print(colonne);
				String bonus = coup.get(k)[j].substring(2,4);
				System.out.print(bonus);
				String lettre = coup.get(k)[j].substring(4,5);
				System.out.print(lettre);
				int valeur;
				if (coup.get(k)[j].length()==6) {valeur = Character.getNumericValue(coup.get(k)[j].charAt(5));}
					else {valeur=Integer.parseInt(coup.get(k)[j].substring(5,7));}
				System.out.println(valeur);
				CaseCourante mot1 = new CaseCourante(ligne,colonne,bonus,lettre,valeur);
				extrait[j]=mot1;
			  }
			mot.add(extrait);
			}
		
		return mot;
						
	}*/
	
				
	public int ScoreCoup() {
		
		int Score = 0;
			for (int k=0;k<this.mots.size();k++) {
				CaseCourante[] mot = (this.mots).get(k);
				Score = Score + ScoreMot(mot);}
		return Score;
		
	}
			   
		
			
	public static int MiseAJourScore (int score, int scorecoup) {
		score = score + scorecoup;
	return score;
		
	}
	
	
	public static int ScoreFinal(int score, CaseCourante[] chevalet) {
		
		score=score - SommeLettres(chevalet);
		return score;
	}
	
	
	
		
		}
	
	
		
		
		
		
		
		
		
		
	
	
				
				
				
			
			
			
			
			
			
			
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	


