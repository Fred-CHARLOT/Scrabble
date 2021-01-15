public class CaseCourante {
public int ligne, colonne,valeur;
public String bonus,affichage,lettre;
	
/**
    * Constructeur d'une case courante
    *@param bonus {@link String} : bonification de la case "MD"; "MT" ; "".......
    *@param affichage {@link String} : syntaxe HTML qui permet d'afficher la lettre en gros et la valeur en indice et en petit dans la case
    */   
    CaseCourante(int ligne, int colonne, String bonus, String affichage){
        this.ligne=ligne;
        this.colonne=colonne;
        this.bonus=bonus;
        this.affichage=affichage;
        this.valeur=AffichageToValeur(affichage);
        this.lettre=affichageToLettre(affichage);
    }
   
   
   
    /**
    * permet de récupérer la lettre issue de l'affichage {@link String}
    * @return String
    */

    private String affichageToLettre(String affichage) {
        return Character. toString(affichage.charAt(24));
    }
       
    /**
    * permet de récupérer la valeur de la lettre à partir de l'affichage @link{String} sous la forme @link{String}
    * @return int
    */

	private String AffichageToValeurString(String affichage) {
        String a=Character. toString(affichage.charAt(57));
        if (affichage.charAt(58)!='<') {a=a+Character. toString(affichage.charAt(58));}   
        return a;
    }

	/**
* permet de convertir le string issu de @link{AffichageToValeurString} en entier
*@return int
*/

    private  int AffichageToValeur(String affichage) {
        return Integer.parseInt(AffichageToValeurString(affichage),10);   
    }   
   
   
    


    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getLettre() {
        return lettre;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

}	