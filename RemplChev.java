import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

import java.util.Arrays;
import java.util.List;

public class RemplChev {

	String [] reglette = new String[7];
	
	
	RemplChev(String[] reglette) {
		
		this.reglette=reglette;
		
		
	}
	
		
	String[] ModifAleat() {
		
		ArrayList <String> a = new ArrayList(Arrays.asList(this.reglette));
		String[] b = new String[7];
		
		for (int i=0;i<7;i++) {
			Random tirage= new Random();
			int d = tirage.nextInt(7-i);
			b[i] = a.get(d);
			a.remove(d);
			}
		
		return b;
		}

	/*void vidage(String coup) {*/
	
	
	
	void remplissage(String[] coup, SacJeton sac) {
		System.out.println(coup.length);
		System.out.println((sac.sac_jeton).size());
		if (coup.length < (sac.sac_jeton).size()) { 
			String[] b = new String[coup.length] ; 
			b = sac.tirage(coup.length);
			/*for (int i=0;i<b.length;i++) {System.out.println(b[i]);}*/
		
			int j = 0;
		
			for (int i=0;i<7;i++) {
				System.out.println(this.reglette[i]);
				if (this.reglette[i].equals("")) 
					{ 
					this.reglette[i] = b[j];
					/*System.out.println(i);*/
					j=j+1;
				}
			}
		}
	
			
		if ((sac.sac_jeton).size()<=coup.length) {
			String[] b = new String[(sac.sac_jeton).size()];
			int n = (sac.sac_jeton).size();
			b  = sac.tirage(n);
			/*for (int i=0;i<b.length;i++) {System.out.println(b[i]);}*/
			int k = 0;
			for (int i=0; i<7 && k<n ;i++) { 
				System.out.println(i);
				if (this.reglette[i].equals("")) /*&& (j<(sac.sac_jeton).size()))*/ 
					{System.out.println(i);
					this.reglette[i] = b[k];
					k=k+1;
				}
			}
		}
		
	}
		 
			
			
			
		
			
	
	
		
	
		
	}





	
