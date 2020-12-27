import java.util.ArrayList;
import java.util.Random;

public class SacJeton {
	ArrayList <String> sac_jeton;
	
	
	SacJeton (ArrayList <String> Sac) {
		this.sac_jeton=Sac;
	}
	
	
	
	public static String AffChev(char a, int b) {
		
		String c = Character. toString(a);
		String d = Integer. toString(b);
		
		return "<html><font size = 7><b>" + c   +  "</b></font>"  + "<font size = 4><sub>" + " "  + d + "</sub></font></html>";
	}
	
	
	static ArrayList <String> Sac_Initial() {
	
	ArrayList <String> sac = new ArrayList<String>() ;
	
		
			for (int i=0;i<=14;i++) {sac.add(SacJeton.AffChev('E', 1));}
			for (int i=15;i<=23;i++){sac.add(SacJeton.AffChev('A', 1));}
			for (int i=24;i<=31;i++) {sac.add(SacJeton.AffChev('I', 1));}
			for (int i=32;i<=37;i++) {sac.add(SacJeton.AffChev('N', 1));}
			for (int i=38;i<=43;i++) {sac.add(SacJeton.AffChev('O', 1));}
			for (int i=44;i<=49;i++) {sac.add(SacJeton.AffChev('R', 1));}
			for (int i=50;i<=55;i++) {sac.add(SacJeton.AffChev('S', 1));}
			for (int i=56;i<=61;i++) {sac.add(SacJeton.AffChev('T', 1));}
			for (int i=62;i<=67;i++) {sac.add(SacJeton.AffChev('U', 1));}
			for (int i=68;i<=72;i++) {sac.add(SacJeton.AffChev('L', 1));}
			for (int i=73;i<=75;i++) {sac.add(SacJeton.AffChev('D', 2));}
			for (int i=76;i<=78;i++) {sac.add(SacJeton.AffChev('M', 2));}
			for (int i=79;i<=80;i++) {sac.add(SacJeton.AffChev('G', 2));}
			for (int i=81;i<=82;i++) {sac.add(SacJeton.AffChev('B', 3));}
			for (int i=83;i<=84;i++) {sac.add(SacJeton.AffChev('C', 3));}
			for (int i=85;i<=86;i++) {sac.add(SacJeton.AffChev('P', 3));}
			for (int i=87;i<=88;i++) {sac.add(SacJeton.AffChev('F', 4));}
			for (int i=89;i<=90;i++) {sac.add(SacJeton.AffChev('H', 4));}
			for (int i=91;i<=92;i++) {sac.add(SacJeton.AffChev('V', 4));}
			for (int i=93;i<=94;i++) {sac.add(SacJeton.AffChev(' ', 0));}
			sac.add(SacJeton.AffChev('J', 8));
			sac.add(SacJeton.AffChev('Q', 8));
			sac.add(SacJeton.AffChev('K', 10));
			sac.add(SacJeton.AffChev('W', 10));
			sac.add(SacJeton.AffChev('X', 10));
			sac.add(SacJeton.AffChev('Y', 10));
			sac.add(SacJeton.AffChev('Z', 10));
		
			for (int i=0;i<sac.size();i++) {System.out.println(sac.get(i));}
			return sac;
		}
	
	public void Sac_décalage(int a) {
				
		int d = (this.sac_jeton).size();
		
		if (a<d) (this.sac_jeton).remove(a);
	}
		
			
		
	public String[] tirage(int x) {
		int d = (this.sac_jeton).size();
		String[] a = new String[x];
		if (x>d) x=d;
		
		if (x >0) {
			for (int k =0; k<x ; k++) {
				Random tirage= new Random();
				int b = tirage.nextInt(d);
				a[k] = (this.sac_jeton).get(b); 
				this.Sac_décalage(b);
				d=d-1;
			}
		
		}
		return a;
		}
		
	
	public void conversion(String[] a, Chevalet reglette) {
		if (a.length==7) {
		for (int i=0; i<7; i++) { reglette.cases[i].setText(a[i]);}
		}
		
	}
	
	public static String[] AffichageToLettre(String affichage) {
		
		/*"<html><font size = 7><b>" + c   +  "</b></font>"  + "<font size = 4><sub>" + " "  + d + "</sub></font></html>";*/
		String[] a = new String[2];
		a[0]= Character. toString(affichage.charAt(24));
		a[1]=Character. toString(affichage.charAt(57));
		if (affichage.charAt(58)!='<') {a[1]=a[1]+Character. toString(affichage.charAt(58));}
		System.out.print(a[0]+" "+a[1]);
		
		return a;
	}	
	
	
public String[] recupjetons(int n) { /* n est le nombre de jeton à remplacer */
		
		n=((this.sac_jeton).size()<=n)?n:(this.sac_jeton).size();
		String[] b = new String[n];
		b  = this.tirage(n);
		return b;
	}
		
		
public boolean débutdepartie() {
	
	Random tirage = new Random();
	int b = tirage.nextInt(1);
	return b==1; /* si b=1 le joueur 1 commence */
}

public String[] EchangeJetons(String[] jetons) {
	/*assert this.sac_jeton.size()>=7;*/
	int n = jetons.length; 
	String[] b = new String[n];
	b  = this.tirage(n); // nouvelles lettres
	for (int i=0;i<n;i++) {
		this.sac_jeton.add(jetons[i]); //remise des jetons échangés dans le sac
	}
	return b;
}
	
/*public static void main(String[] Args) {
	
	SacJeton.AffichageToLettre("<html><font size = 7><b>" + "K"   +  "</b></font>"  + "<font size = 4><sub>" + " "  + "10" + "</sub></font></html>");
	
	
}*/



}




	
	
	
	
	
	
	

