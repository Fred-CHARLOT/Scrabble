import java.io.Serializable;

public class CaseCourante implements Serializable {
public int ligne, colonne;
public String bonus,lettre;
	
	CaseCourante(int ligne, int colonne, String bonus, String lettre){
		this.ligne=ligne;
		this.colonne=colonne;
		this.bonus=bonus;
		this.lettre=lettre;
	}
}