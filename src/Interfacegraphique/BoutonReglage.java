package Interfacegraphique;

import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;
import MoteurGraphique.DrawableImage;

public class BoutonReglage implements WindowListener{
	
	final private DrawableImage Reglage;
	
	 public BoutonReglage() {
		 this.Reglage = new DrawableImage("réglage");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Reglage.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Reglage.redimensionner(10, 10, 20, 20 );
		}

}
