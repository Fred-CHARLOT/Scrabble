import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;


public class Verificateur {
private ArrayList <CaseCourante> coup;
	
	public boolean verificateur(ArrayList <CaseCourante> coup) {
		this.coup=coup;
		if (Plateau.plateau[7][7]=="*")	{
			if (!verificationEtoile()) {
			JOptionPane.showMessageDialog(null, "le mot doit etre sur l'étoile et compter deux lettres");	
			return false;
			}
		}	
			
		if (!verifierAdjacence()) {
			JOptionPane.showMessageDialog(null, "coup non valide");	
			System.out.println("coup non valide");	
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
				System.out.println(count);
				if ((retrouveCaseDansCoup(count,k)==-1)&& (Plateau.plateau)[count][k].length()<3)return false;
			}
			return true;
		}

		public  boolean verifFinalLigne(int min, int max, int k) {
			for (int count = min; count<max+1;count++) {
				System.out.println(count);
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
		
		/*
		public  boolean verifAlignementPremierCoup ( ) {

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

			return false;}
		
		*/
		
		
		
		
		}	
	
	

