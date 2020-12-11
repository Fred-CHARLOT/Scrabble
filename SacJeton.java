import java.util.ArrayList;
import java.util.Random;

public class SacJeton {
	ArrayList <String> sac_jeton;
	
	
	static ArrayList <String> Sac_Initial() {
	
	ArrayList <String> sac = new ArrayList<String>() ;
	
		
			for (int i=0;i<=14;i++) {sac.add("<html>E<sub>1</sub></html>");}
			for (int i=15;i<=23;i++){sac.add("<html>A<sub>1</sub></html>");}
			for (int i=24;i<=31;i++) {sac.add("<html>I<sub>1</sub></html>");}
			for (int i=32;i<=37;i++) {sac.add("html>N<sub>1</sub></html>");}
			for (int i=38;i<=43;i++) {sac.add("<html>O<sub>1</sub></html>");}
			for (int i=44;i<=49;i++) {sac.add("<html>R<sub>1</sub></html>");}
			for (int i=50;i<=55;i++) {sac.add("<html>S<sub>1</sub></html>");}
			for (int i=56;i<=61;i++) {sac.add("<html>T<sub>1</sub></html>");}
			for (int i=62;i<=67;i++) {sac.add("<html>U<sub>1</sub></html>");}
			for (int i=68;i<=72;i++) {sac.add("<html>L<sub>1</sub></html>L");}
			for (int i=73;i<=75;i++) {sac.add("<html>D<sub>2</sub></html>");}
			for (int i=76;i<=78;i++) {sac.add("<html>M<sub>2</sub></html>");}
			for (int i=79;i<=80;i++) {sac.add("<html>G<sub>2</sub></html>");}
			for (int i=81;i<=82;i++) {sac.add("<html>B<sub>3</sub></html>");}
			for (int i=83;i<=84;i++) {sac.add("html>C<sub>3</sub></html>");}
			for (int i=85;i<=86;i++) {sac.add("<html>P<sub>3</sub></html>");}
			for (int i=87;i<=88;i++) {sac.add("<html>F<sub>4</sub></html>");}
			for (int i=89;i<=90;i++) {sac.add("<html>H<sub>4</sub></html>");}
			for (int i=91;i<=92;i++) {sac.add("<html>V<sub>4</sub></html>");}
			for (int i=93;i<=94;i++) {sac.add(" ");}
			sac.add("html>J<sub>8</sub></html>");
			sac.add("<html>Q<sub>8</sub></html>");
			sac.add("<html>K<sub>10</sub></html>");
			sac.add("<html>W<sub>10</sub></html>");
			sac.add("<html>X<sub>10</sub></html>");
			sac.add("<html>Y<sub>10</sub></html>");
			sac.add("<html>Z<sub>10</sub></html>");
		
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
		
	
	
	
	
	
	
	
	
	
	
	
	
	
}
