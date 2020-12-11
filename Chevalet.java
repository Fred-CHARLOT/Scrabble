
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;   /// EVENENEMENT




public class Chevalet  implements ActionListener {
	JButton cases [];  //creation du tableau de 7 lettres
	JButton valider, chrono, échange;
	JFrame	fenetre1;
	JPanel panneau1,panneau2;
	GridLayout disposition1, disposition2;
	public static int caseCourante=7;
	
	Chevalet (){
	cases = new JButton [7];
	fenetre1=new JFrame("Scrabble by JB and Fred");
	fenetre1.setSize(800, 100) ;
	fenetre1.setLocationRelativeTo(null);//pour centrer la fenetre
	fenetre1.setVisible(true);
	fenetre1.setAlwaysOnTop(true);
	panneau1 = new JPanel();
	disposition1 = new GridLayout(1, 7); 
	panneau1.setLayout(disposition1);
	fenetre1.add("Center",panneau1);
	
	
	
		for ( int colonne=0  ; colonne< 7;colonne++) {		
			cases[colonne]= new JButton();
			panneau1.add(cases[colonne]);
			cases[colonne].addActionListener(this);
		}			
	
		cases[0].setText("G"); // cette partie est à modifier: j'ai mis 7 lettres au hasard, il faudra les tirer dans le sac.
		cases[1].setText("S");
		cases[2].setText("I");
		cases[3].setText("P");
		cases[4].setText("A");
		cases[5].setText("D");	
		cases[6].setText("E");
		
		panneau2 = new JPanel();
		disposition2 = new GridLayout(1, 3); 
		panneau2.setLayout(disposition2);
		fenetre1.add("South",panneau2);
		valider = new JButton("valider");
		chrono = new JButton("je passe");
		échange = new JButton("échange");
		panneau2.add(valider);
		valider.addActionListener(this);
		panneau2.add(chrono);
		panneau2.add(échange);
		échange.addActionListener(this);
		
		
	}
	
		public void actionPerformed(ActionEvent événement)  { /// EVENENEMENTactionPerformed
		
				JButton leBouton = (JButton) événement.getSource(); /// EVENENEMENT 
		
		
		if (leBouton==valider) {			
		System.out.println("et c'est parti");	//a modifier	

		}		
		if (leBouton==échange) {
		System.out.println("rhooo, t'as rien trouvé?");		// à modifier aussi

		}
		
		
		
		//echange de cases. par des permutations. A faire plsu tard aussi en intercalant.
				
			for (int i = 0; i<7;i++) {
				if (leBouton==cases[i]){
					if (caseCourante==7) {cases[i].setBackground(Color.CYAN);caseCourante=i;}
					else {
						if (caseCourante==i){caseCourante=7;cases[i].setBackground(null);}
						else {permute(i,caseCourante);caseCourante=7;}
					}
					
				}	
				
			}		
		}	
	
	void permute(int i,int j) {
		String temp;
		cases[i].setBackground(null);cases[j].setBackground(null);
		temp=cases[i].getText();
		cases[i].setText(cases[j].getText())	;
		cases[j].setText(temp);	
		}
		
}	
	