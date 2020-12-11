import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import javax.swing.*;


public class Plateau {
	String [][] plateau ;
	AffichageGrille  f;
	
	
	Plateau(){
	
	plateau= new String [15][15];

	for (int i=0; i<15 ; i++)
	{for (int j=0; j<15; j++)
	{plateau[i][j]="";}
	;}

	plateau [7][7]="*";

	plateau[0][0] = "MT";
	plateau[0][7] = "MT";
	plateau[7][0] = "MT";
	

	for (int i=0; i<=7 ; i++)
		{for (int j=0; j<=7; j++)
			{ if (plateau[i][j].equals("MT"))
			{plateau[14-i][j]="MT";
			plateau[i][14-j]="MT";
			plateau[14-i][14-j]="MT";}
			}
		}
	

	

	for (int i=1; (i>=1)&&(i<=4);i++)
	{ plateau[i][i]="MD" ;
	plateau[i][14-i]="MD";
	plateau[14-i][i]="MD";
	plateau[14-i][14-i]="MD";}

	
	
	plateau[1][5]="LT";
	plateau[5][1] = "LT";
	plateau[5][5] = "LT";
	

	for (int i=0; i<=7 ; i++)
		{for (int j=0; j<=7; j++)
			{ if (plateau[i][j].equals("LT"))
				{plateau[14-i][j]="LT";
				plateau[i][14-j]="LT";
				plateau[14-i][14-j]="LT";}
			}
		}
	
	
	plateau [0][3] = "LD";
	plateau [2][6] = "LD";
	plateau [3][0] = "LD";
	plateau [3][7] = "LD";
	plateau [6][2] = "LD";
	plateau [6][6] = "LD";
	plateau [7][3] = "LD";

	for (int i=0; i<=7 ; i++)
		{for (int j=0; j<=7; j++)
			{ if (plateau[i][j].equals("LD"))
				{plateau[14-i][j]="LD";
				plateau[i][14-j]="LD";
				plateau[14-i][14-j]="LD";
				}
			}
		}
	
  f= new AffichageGrille();
  remplirCases();
	
	
	}
	
	void remplirCases() {
		for (int i=0; i<15 ; i++)
		{for (int j=0; j<15; j++)
		{
			f.cases[i][j].setText(plateau[i][j]);
			if (plateau[i][j]== "LT") f.cases[i][j].setBackground(Color.CYAN);
			if (plateau[i][j]== "LD") f.cases[i][j].setBackground(Color.yellow);
			if (plateau[i][j]== "MD") f.cases[i][j].setBackground(Color.pink);
			if (plateau[i][j]== "MT") f.cases[i][j].setBackground(Color.red);
			
		}
		;}
		f.cases[7][7].setBackground(Color.pink);}
	
	void sacJetons() {
		ArrayList<String>sac = new ArrayList<String>();
		
		for (int i=0;i<=14;i++) {sac.add("E");}
		for (int i=15;i<=23;i++){sac.add("A");}
		for (int i=24;i<=31;i++) {sac.add("I");}
		for (int i=32;i<=37;i++) {sac.add("N");}
		for (int i=38;i<=43;i++) {sac.add("O");}
		for (int i=44;i<=49;i++) {sac.add("R");}
		for (int i=50;i<=55;i++) {sac.add("S");}
		for (int i=56;i<=61;i++) {sac.add("T");}
		for (int i=62;i<=66;i++) {sac.add("U");}
		for (int i=68;i<=72;i++) {sac.add("L");}
		for (int i=73;i<=75;i++) {sac.add("D");}
		for (int i=76;i<=78;i++) {sac.add("M");}
		for (int i=79;i<=80;i++) {sac.add("G");}
		for (int i=81;i<=82;i++) {sac.add("B");}
		for (int i=83;i<=84;i++) {sac.add("C");}
		for (int i=85;i<=86;i++) {sac.add("P");}
		for (int i=87;i<=88;i++) {sac.add("F");}
		for (int i=89;i<=90;i++) {sac.add("H");}
		for (int i=91;i<=92;i++) {sac.add("V");}
		for (int i=93;i<=94;i++) {sac.add(" ");}
		sac.add("J");
		sac.add("Q");
		sac.add("K");
		sac.add("W");
		sac.add("X");
		sac.add("Y");
		sac.add("Z");
		System.out.println(sac);
	}
		
				
	void remplirSac() {
		ArrayList<Character>sac = new ArrayList<Character>();
		
		for (int i=0;i<=14;i++) {sac.add('E');}
		for (int i=15;i<=23;i++){sac.add('A');}
		for (int i=24;i<=31;i++) {sac.add('I');}
		for (int i=32;i<=37;i++) {sac.add('N');}
		for (int i=38;i<=43;i++) {sac.add('O');}
		for (int i=44;i<=49;i++) {sac.add('R');}
		for (int i=50;i<=55;i++) {sac.add('S');}
		for (int i=56;i<=61;i++) {sac.add('T');}
		for (int i=62;i<=66;i++) {sac.add('U');}
		for (int i=68;i<=72;i++) {sac.add('L');}
		for (int i=73;i<=75;i++) {sac.add('D');}
		for (int i=76;i<=78;i++) {sac.add('M');}
		for (int i=79;i<=80;i++) {sac.add('G');}
		for (int i=81;i<=82;i++) {sac.add('B');}
		for (int i=83;i<=84;i++) {sac.add('C');}
		for (int i=85;i<=86;i++) {sac.add('P');}
		for (int i=87;i<=88;i++) {sac.add('F');}
		for (int i=89;i<=90;i++) {sac.add('H');}
		for (int i=91;i<=92;i++) {sac.add('V');}
		for (int i=93;i<=94;i++) {sac.add(' ');}
		sac.add('J');
		sac.add('Q');
		sac.add('K');
		sac.add('W');
		sac.add('X');
		sac.add('Y');
		sac.add('Z');
		System.out.println(sac);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		Plateau p = new Plateau();

		/*ArrayList<String>sac = new ArrayList<String>();
		
		for (int i=0;i<=14;i++) {sac.add("E");}
		for (int i=15;i<=23;i++){sac.add("A");}
		for (int i=24;i<=31;i++) {sac.add("I");}
		for (int i=32;i<=37;i++) {sac.add("N");}
		for (int i=38;i<=43;i++) {sac.add("O");}
		for (int i=44;i<=49;i++) {sac.add("R");}
		for (int i=50;i<=55;i++) {sac.add("S");}
		for (int i=56;i<=61;i++) {sac.add("T");}
		for (int i=62;i<=66;i++) {sac.add("U");}
		for (int i=68;i<=72;i++) {sac.add("L");}
		for (int i=73;i<=75;i++) {sac.add("D");}
		for (int i=76;i<=78;i++) {sac.add("M");}
		for (int i=79;i<=80;i++) {sac.add("G");}
		for (int i=81;i<=82;i++) {sac.add("B");}
		for (int i=83;i<=84;i++) {sac.add("C");}
		for (int i=85;i<=86;i++) {sac.add("P");}
		for (int i=87;i<=88;i++) {sac.add("F");}
		for (int i=89;i<=90;i++) {sac.add("H");}
		for (int i=91;i<=92;i++) {sac.add("V");}
		for (int i=93;i<=94;i++) {sac.add(" ");}
		sac.add("J");
		sac.add("Q");
		sac.add("K");
		sac.add("W");
		sac.add("X");
		sac.add("Y");
		sac.add("Z");
		System.out.println(sac);*/
		
		/*ArrayList<String> sachet = new ArrayList<String>() ;
		
		sachet.add("A");
		sachet.add("A");
		sachet.add("E");
		sachet.add("E");
		sachet.add("I");
		sachet.add("O");
		sachet.add("U");
		sachet.add("Y");*/
		
		
		
		/*SacJeton Sac= new SacJeton();
		Sac.sac_jeton=SacJeton.Sac_Initial();
		
		System.out.println((Sac.sac_jeton).size());
		String a = (Sac.tirage(1));
		System.out.println(a);
		System.out.println((Sac.sac_jeton).size());*/
		
		JFrame fenetre =new JFrame("test lettres ");
	    fenetre.setSize(30, 150) ;
	    fenetre.setLocationRelativeTo(null);//pour centrer la fenetre
	    fenetre.setVisible(true);
	    JButton lettre = new JButton("<html>A  <font size = \"5\"><sub>1</sub></font><br /></html>");
	    Font f=new Font("Arial", Font.BOLD, 80);
	    lettre.setFont(f);
	    fenetre.add(lettre);

		
		RemplChev Regle = new RemplChev();
		Regle.reglette[0]= "A";
		Regle.reglette[1]= "";
		Regle.reglette[2]="";
		Regle.reglette[3]="";
		Regle.reglette[4]= "";
		Regle.reglette[5]= "E";
		Regle.reglette[6]= "";
		
		SacJeton Sac= new SacJeton();
		
		/*ArrayList <String> sac1 = new ArrayList<String>() ;
		for (int i=50;i<=55;i++) {sac1.add("S");}
		for (int i=56;i<=61;i++) {sac1.add("T");}
		for (int i=62;i<=67;i++) {sac1.add("U");}
		for (int i=68;i<=72;i++) {sac1.add("L");}
		for (int i=73;i<=75;i++) {sac1.add("D");}
		for (int i=76;i<=78;i++) {sac1.add("M");}
		for (int i=79;i<=80;i++) {sac1.add("G");}
		for (int i=81;i<=82;i++) {sac1.add("B");}
		
		Sac.sac_jeton = sac1;*/
		
		/*for (int i=0;i<(Sac.sac_jeton).size();i++) {System.out.println((Sac.sac_jeton).get(i));}*/
		
		Sac.sac_jeton=SacJeton.Sac_Initial();
		
		/*RemplChev cheval = new RemplChev();*/
		
		String[] mot = {"C","A","S","E","R"};
		
		Regle.remplissage(mot,Sac);
		for (int i=0;i<7;i++) { System.out.print(Regle.reglette[i]);}
		
		
		
		
		
		
		/*String[] b = Regle.ModifAleat();*/
		
		
		
		
		
		
		
		
		
		
		
	}
	
}

