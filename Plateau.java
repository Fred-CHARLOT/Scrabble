import java.io.Serializable;

public class Plateau implements Serializable{
	static String [][] plateau ;										///static
	
/** Construction du plateau de jeu sous la forme @link{String [15][15]} avec utilisation des symétries pour les cases bonus
*/ 	
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
  
	}
	
}
	