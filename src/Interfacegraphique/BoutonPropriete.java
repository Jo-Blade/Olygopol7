package Interfacegraphique;

import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;
import MoteurGraphique.DrawableImage;

public class BoutonPropriete implements WindowListener {

	final private DrawableImage Image;
	
	 public BoutonPropriete() {
		 this.Image = new DrawableImage("propriete.png");
     updateWindowTaille(600, 300);
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Image.afficher(aff);
		}
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Image.redimensionner(windowWidth - 180, windowHeight/2 - 30, 169, 122);
		}
}
