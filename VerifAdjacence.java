import java.util.ArrayList;
import java.util.Collections;


public class VerifAdjacence {

	public static boolean verifierAdjacence(CaseCourante[] coup) {

		if (coup.length==0) return false;
		
		ArrayList<Integer> listelignes = new ArrayList<Integer>();
		ArrayList<Integer> listecolonnes = new ArrayList<Integer>();		
		for (int i=0;i<coup.length;i++) {
			listelignes.add(coup[i].ligne);
			listecolonnes.add(coup[i].colonne);
		}
		
		int minligne = Collections.min(listelignes);// Retourner la plus petite valeur des lignes
		int maxligne = Collections.max(listelignes);
		int mincolonne = Collections.min(listecolonnes);
		int maxcolonne = Collections.max(listecolonnes);
		
		if (maxligne-minligne==coup.length-1 && maxcolonne-mincolonne==0) return true; //alignement sur une colonne
		
		if (maxcolonne-mincolonne==coup.length-1 && maxligne-minligne==0) return true; //alignement sur une ligne
		
		return false;
		
		
	}
		
	
	public static void main(String[] Args) {
		CaseCourante case0 = new CaseCourante(3,4,"LT","S",1);
		CaseCourante case1 = new CaseCourante(4,2,"","U",1);
		CaseCourante case2 = new CaseCourante(3,3,"MD","K",10);
		/*CaseCourante case3 = new CaseCourante(5,3,"LD","E",1);
		CaseCourante case4 = new CaseCourante(6,3,"","F",4);
		CaseCourante case5 = new CaseCourante(7,3,"","G",2);
		CaseCourante case6 = new CaseCourante(8,3,"LD","",0);*/
		
		CaseCourante[] coup= new CaseCourante[3] ;
		coup[0]=case0;
		coup[1]=case1;
		coup[2]=case2;
		/*coup[3]= case3;
		coup[4]=case4;
		coup[5]=case5;
		coup[6]=case6;*/
		
		System.out.println(verifierAdjacence(coup));
		
		
		
	}
		 
		 
		 
		 
	   
	
	
	}
	
	
	
	
		
		
	
	
	

