import java.util.ArrayList;
import java.util.Collections;


public class VerifAlignement {

	public static boolean verifAlignement(ArrayList<CaseCourante> coup) {

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
		
		if (maxcolonne-mincolonne==0) return true; //alignement sur une colonne
		
		if (maxligne-minligne==0) return true; //alignement sur une ligne
		
		return false;
		
		
	}
	
	
	public static boolean verifAlignementPremierCoup (ArrayList<CaseCourante> coup) {

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
		
		if (maxcolonne==7 && mincolonne==7 && maxligne-minligne==coup.size() - 1 && minligne<=7 && maxligne >=7) return true; //alignement sur une colonne
		
		if (maxligne==7 && minligne==7 && maxcolonne-mincolonne ==coup.size() - 1 && mincolonne<=7 && maxcolonne >=7) return true; //alignement sur une ligne
		
		return false;
		
		
	}
	
	
	
	
	public static void main(String[] Args) {
		CaseCourante case0 = new CaseCourante(7,5,"LT","S",1);
		CaseCourante case1 = new CaseCourante(7,6,"","U",1);
		CaseCourante case2 = new CaseCourante(7,4,"MD","K",10);
		/*CaseCourante case3 = new CaseCourante(5,3,"LD","E",1);
		CaseCourante case4 = new CaseCourante(6,3,"","F",4);
		CaseCourante case5 = new CaseCourante(7,3,"","G",2);
		CaseCourante case6 = new CaseCourante(8,3,"LD","",0);*/
		
		ArrayList <CaseCourante> coup= new ArrayList <CaseCourante>() ;
		coup.add(case0);
		coup.add(case1);
		coup.add(case2);
		/*coup[3]= case3;
		coup[4]=case4;
		coup[5]=case5;
		coup[6]=case6;*/
		
		System.out.println(verifAlignement(coup));
		
		
		
	}
		 
		 
		 
		 
	   
	
	
	}
	
	
	
	
		
		
	
	
	

