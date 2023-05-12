package Interfacegraphique;

import MoteurGraphique.OpenglThread;

public class BoutonSon {

	final private DrawableImage Son;
	
	 public BoutonSon() {
		 this.Son = new DrawableImage("son");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Son.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Son.redimensionner(30, 10, 40, 20 );
		}
}
