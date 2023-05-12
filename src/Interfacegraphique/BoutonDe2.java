package Interfacegraphique;

import MoteurGraphique.DrawableImage;
import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;

public class BoutonDe2 implements WindowListener {
	
	final private DrawableImage De;
	
	 public BoutonDe2() {
		 this.De = new DrawableImage("de2.png");
     updateWindowTaille(600, 300);
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			De.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			De.redimensionner(10, windowHeight - 74, 53, 58);
		}

}
