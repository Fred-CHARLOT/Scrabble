import java.util.ArrayList;
import java.util.List;

public class TestInsertionMot {
	public static void main(String[] args) {
		int i, j, n = 15;
		
		//Definition du dictionnaire
		List<String> dictionnaire = new ArrayList<String>();
		dictionnaire.add("ABC");
		dictionnaire.add("DEF");
		dictionnaire.add("AD");
		dictionnaire.add("BE");
		dictionnaire.add("CF");
		dictionnaire.add("GHIBE");
		dictionnaire.add("CFXW");
		dictionnaire.add("XL");
		dictionnaire.add("XLABCDE");
		
		//Nouveau plateau de jeu
		String[][] tableau = new String[n][n];
		
		List<List<String>> lettres1 = new ArrayList<List<String>>();
		List<List<String>> lettres2 = new ArrayList<List<String>>();
		List<List<String>> lettres3 = new ArrayList<List<String>>();
		List<List<String>> lettres4 = new ArrayList<List<String>>();
		List<List<String>> lettres5 = new ArrayList<List<String>>();
		List<List<String>> lettres6 = new ArrayList<List<String>>();
		List<List<String>> lettres7 = new ArrayList<List<String>>();
		
		//Insertion des mots (lettre par lettre)
		List<String> a = new ArrayList<String>();
		a.add(0, "A");
		a.add(1, "7");
		a.add(2, "6");
		List<String> b = new ArrayList<String>();
		b.add(0, "B");
		b.add(1, "7");
		b.add(2, "7");
		List<String> c = new ArrayList<String>();
		c.add(0, "C");
		c.add(1, "7");
		c.add(2, "8");
		
		lettres1.add(a);
		lettres1.add(b);
		lettres1.add(c);
		
		List<String> d = new ArrayList<String>();
		d.add(0, "D");
		d.add(1, "8");
		d.add(2, "6");
		List<String> e = new ArrayList<String>();
		e.add(0, "E");
		e.add(1, "8");
		e.add(2, "7");
		List<String> f = new ArrayList<String>();
		f.add(0, "F");
		f.add(1, "8");
		f.add(2, "8");
		
		lettres2.add(d);
		lettres2.add(e);
		lettres2.add(f);
		
		List<String> g = new ArrayList<String>();
		g.add(0, "G");
		g.add(1, "4");
		g.add(2, "7");
		List<String> h = new ArrayList<String>();
		h.add(0, "H");
		h.add(1, "5");
		h.add(2, "7");
		List<String> ii = new ArrayList<String>();
		ii.add(0, "I");
		ii.add(1, "6");
		ii.add(2, "7");
		
		lettres3.add(g);
		lettres3.add(h);
		lettres3.add(ii);
		
		List<String> x = new ArrayList<String>();
		x.add(0, "X");
		x.add(1, "9");
		x.add(2, "8");
		List<String> w = new ArrayList<String>();
		w.add(0, "W");
		w.add(1, "10");
		w.add(2, "8");
		
		lettres4.add(x);
		lettres4.add(w);
		
		List<String> l = new ArrayList<String>();
		l.add(0, "L");
		l.add(1, "9");
		l.add(2, "9");
		
		lettres5.add(l);
		
		List<String> a2 = new ArrayList<String>();
		a2.add(0, "A");
		a2.add(1, "9");
		a2.add(2, "10");
		List<String> b2 = new ArrayList<String>();
		b2.add(0, "B");
		b2.add(1, "9");
		b2.add(2, "11");
		List<String> c2 = new ArrayList<String>();
		c2.add(0, "C");
		c2.add(1, "9");
		c2.add(2, "12");
		List<String> d2 = new ArrayList<String>();
		d2.add(0, "D");
		d2.add(1, "9");
		d2.add(2, "13");
		List<String> e2 = new ArrayList<String>();
		e2.add(0, "E");
		e2.add(1, "9");
		e2.add(2, "14");
		
		lettres6.add(a2);
		lettres6.add(b2);
		lettres6.add(c2);
		lettres6.add(d2);
		lettres6.add(e2);
		
		List<String> d3 = new ArrayList<String>();
		d3.add(0, "A");
		d3.add(1, "8");
		d3.add(2, "13");
		
		lettres7.add(d3);
		
		//Insertion des mots dans le plateau (Avec validation)
		System.out.println(InsertionUtil.put(tableau, lettres1, dictionnaire));
		System.out.println(InsertionUtil.put(tableau, lettres2, dictionnaire));
		System.out.println(InsertionUtil.put(tableau, lettres3, dictionnaire));
		System.out.println(InsertionUtil.put(tableau, lettres4, dictionnaire));
		System.out.println(InsertionUtil.put(tableau, lettres5, dictionnaire));
		System.out.println(InsertionUtil.put(tableau, lettres6, dictionnaire));
		System.out.println(InsertionUtil.put(tableau, lettres7, dictionnaire));
		
		//Affichage du tableau
		for(i = 0 ; i < n ; i++) {
			for(j = 0 ; j < n ; j++) {
				if(tableau[i][j] == null) {
					System.out.print("* ");
				}
				else {
					System.out.print(tableau[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
}
