package Interfacegraphique;

import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;

public class ReprsentationPlateau implements WindowListener{
	
	final private DrawableImage Plateau;
	
	 public BoutonPlateau2() {
		 this.Plateau = new DrawableImage("Isotext");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Plateau.afficher(aff);
		}
	 
	 
	 @OverriPlateau
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Plateau.redimensionner(50,  30, windowWidth-50 , windowHeight - 30);
		}

}
