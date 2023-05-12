package Interfacegraphique;

import MoteurGraphique.OpenglThread;

public class BoutonDe2 {
	
	final private DrawableImage De;
	
	 public BoutonDe2() {
		 this.De = new DrawableImage("de2");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			De.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			De.redimensionner(20, windowHeight - 20, 30, windowHeight - 10);
		}

}
