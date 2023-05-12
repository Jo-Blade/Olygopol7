package Interfacegraphique;

import MoteurGraphique.OpenglThread;

public class ReprsentationPlateau {
	
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
