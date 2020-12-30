public class CaseCourante {
public int ligne, colonne,valeur;
public String bonus,affichage,lettre;
	
	CaseCourante(int ligne, int colonne, String bonus, String affichage){
		this.ligne=ligne;
		this.colonne=colonne;
		this.bonus=bonus;
		this.affichage=affichage;
		this.valeur=AffichageToValeur(affichage);
		this.lettre=AffichageToLettre(affichage)[0];
	}
	
	
	//test
	
	
	
	
	
	
	
	public  String[] AffichageToLettre(String affichage) {
		/*"<html>A  <font size = 5><sub>1</sub></font><br /></html>"*/
		
		String[] a = new String[2];
		a[0]= Character. toString(affichage.charAt(24));
		a[1]=AffichageToValeurString(affichage);			
		return a;
	}
	
	public  int AffichageToValeur(String affichage) {
		return Integer.parseInt(AffichageToValeurString(affichage),10);	
	}	
	
	
	
	public  String AffichageToValeurString(String affichage) {
		String a=Character. toString(affichage.charAt(57));
		if (affichage.charAt(58)!='<') {a=a+Character. toString(affichage.charAt(58));}	
		return a;
	}
	
	
	
}