
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;   /// EVENENEMENT




public class AffichageGrille  implements ActionListener {
	JButton cases [][];  //creation du tableau de boutons
	JFrame	fenetre1;
	JPanel panneau1;
	GridLayout disposition1;
	boolean testBouton1; 
	boolean testBouton2;
	
	AffichageGrille (){
	cases = new JButton [15][15];
	fenetre1=new JFrame("Reseau Scrabble by Fred & JB ");
	fenetre1.setSize(800, 850) ;
	fenetre1.setLocationRelativeTo(null);//pour centrer la fenetre
	fenetre1.setVisible(true);
	
	panneau1 = new JPanel();
	disposition1 = new GridLayout(15, 15); 
	panneau1.setLayout(disposition1);
	fenetre1.add(panneau1);
	
		for ( int ligne=0  ; ligne< 15;ligne++) {
		for (int colonne=0;colonne<15; colonne++) {
			cases[ligne][colonne]= new JButton();
			panneau1.add(cases[ligne][colonne]);
			cases[ligne][colonne].addActionListener(this);
		}			
	}
	
		cases[7][7].setText("*");
	
	
	
	
	
	
	}
	
		public void actionPerformed(ActionEvent événement)  { /// EVENENEMENT
		
				JButton leBouton = (JButton) événement.getSource(); /// EVENENEMENT 
		
		if (leBouton==cases[0][0]) {			
		testBouton1=true;			
		//AffichageGrille .sortie.println("j'ai cliqué sur le bouton 1");		
		//ClientScr.sortie.flush();
		}		
		if (leBouton==cases[0][1]) {
		testBouton2=true;		
		//ClientScr.sortie.println("2");
		//ClientScr.sortie.flush();
		}
	}
		
		
	public void dessinerGrille() {
		
	
		
		
		
		
		
	}
	

}
