


public class CaseCourante {
public int ligne, colonne, valeur;
public String bonus,lettre;
	
	CaseCourante(int ligne, int colonne, String bonus, String lettre, int valeur){
		this.ligne=ligne;
		this.colonne=colonne;
		this.bonus=bonus;
		this.lettre=lettre;
		this.valeur=valeur;
	}

	public void MiseAJour() {
		if (!this.bonus.equals("")) this.bonus="";
	}

	
	
}