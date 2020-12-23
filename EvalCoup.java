import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvalCoup {
	
					
//suppression du constructeur	
	
	
	private int sommeLettres (CaseCourante[] coup) {   
		
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
	
		
	
	private int scoreMot(CaseCourante[] coup) {
			
			int score = sommeLettres(coup);
			int Dcoeff = 1;
			int Tcoeff = 1;
			
			for (var k:coup) {
				
				String c = k.bonus;
				if (c.equals("MD")||(c.equals("*"))) {Dcoeff=2*Dcoeff;}
				if (c.equals("MT")) {Tcoeff=3*Tcoeff;}				
			}
			
				score = score * Dcoeff * Tcoeff;
				if (coup.length==7) {score = score + 50;}
			
			return score;
		
		}
	
//ajout de la liste en parametre				
	public int scoreCoup(ArrayList <CaseCourante[]> ensMots) {
		
		int score = 0;
			for (int k=0;k<ensMots.size();k++) {
				CaseCourante[] mot = ensMots.get(k);
				score = score + scoreMot(mot);}
		return score;
		
	}
			   
		
			
	public  int miseAJourScore (int score, ArrayList <CaseCourante[]> ensMots) {
		score = score + scoreCoup(ensMots);
		System.out.println(ensMots.get(0)[1].valeur);
		return score;
	}
	
	
	public  int scoreFinal(int score, CaseCourante[] chevalet) {
		
		score=score - sommeLettres(chevalet);
		return score;
	}
	
	
	
		
		}
	
	
		
		
		
		
		
		
		
		
	
	
				
				
				
			
			
			
			
			
	