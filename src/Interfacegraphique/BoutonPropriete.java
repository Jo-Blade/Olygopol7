package Interfacegraphique;

import LogiqueMonopoly.Joueur;
import MoteurGraphique.DrawableText;
import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;

public class BoutonPropriete implements WindowListener {

	final private DrawableImage Image;
	
	 public BoutonPropriete() {
		 this.Image = new DrawableImage("propriété");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Image.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Image.redimensionner(windowWidth-60, windowHeight/2-55, windowWidth-20, windowHeight/2-25 );
		}
}
