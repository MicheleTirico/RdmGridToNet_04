package run;

import RD.RD;
import RD.RDGrid;

public class Test {
	public static void main(String [] args) {
		
		// width, height, dA, dB, f, k.
		// La grille est initialis�e avec des 0 dans A et des 1 dans B.

		RD rd = new RD(1024, 1024, 1f, 0.5f, 0.0545f, 0.062f, true/*Visualisation*/);
				
		// Remplit un carr� de valeurs B al�atoires au centre de la grille.
		
		RDGrid grid = rd.grid();

		grid.map(() -> {	
			// Map obligatoire pour acceder � la m�moire graphique.
			int w = grid.getWidth();
			int h = grid.getHeight();
			
			for(int y = h/3; y < h * 0.66; y++) {
				for(int x = w/3; x < w * 0.66; x++) {
					grid.setB(x, y, Math.random() > 0.9 ? 1 : 0);
				}
			}
		});

		// Boucle principale.

		long T0 = System.currentTimeMillis();
		long StepMax = 10000;
		int step = 0;

		for(step = 0; step < StepMax && ! rd.shouldClose(); step++) {
			//setParams(dA, dB, f, k);
			rd.step();
		}

		long T = System.currentTimeMillis() - T0;

		System.out.printf("Time %d ms = %.2f s -> %.2f steps/s -> %d steps%n", T, T/1000f, step / (T/1000f), step);

		// Sauvegarde le r�sultat dans un PNG....

		//grid.savePNG("/Users/antoine/Desktop/RD");
		//grid.savePNG("/home/antoine/Bureau/RD");

		// Enfin on lib�re la m�moire.
		rd.clean();
	}
}