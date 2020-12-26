public class CaseCourante {
public int ligne, colonne,valeur;
public String bonus,lettre;
	
	CaseCourante(int ligne, int colonne, String bonus, String lettre){
		this.ligne=ligne;
		this.colonne=colonne;
		this.bonus=bonus;
		this.lettre=lettre;
		if (!this.lettre.equals(" "))   this.valeur=AffichageToValeur(lettre);
	}
	
	CaseCourante(int ligne, int colonne, String bonus, String lettre,int i){
		this.ligne=ligne;
		this.colonne=colonne;
		this.bonus=bonus;
		this.lettre=lettre;
		this.valeur=i;
	}
	
	
	
	
	
	
	
	
	public static String[] AffichageToLettre(String affichage) {
		/*"<html>A  <font size = 5><sub>1</sub></font><br /></html>"*/
		
		String[] a = new String[2];
		a[0]= Character. toString(affichage.charAt(6));
		a[1]=AffichageToValeurString(affichage);
		System.out.println(a[0]+" "+a[1]);		
		return a;
	}
	
	public static int AffichageToValeur(String affichage) {
		return Integer.parseInt(AffichageToValeurString(affichage),10);	
	}	

	
	public static String AffichageToValeurString(String affichage) {
		String a=Character. toString(affichage.charAt(29));
		if (affichage.charAt(30)!='<') {a=a+Character. toString(affichage.charAt(30));}	
		return a;
	}
	
	
	
}