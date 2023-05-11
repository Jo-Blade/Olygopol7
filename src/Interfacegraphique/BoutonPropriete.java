package Interfacegraphique;

import LogiqueMonopoly.Joueur;
import MoteurGraphique.DrawableText;
import MoteurGraphique.OpenglThread;

public class BoutonPropriete {

	final private DrawableImage Image;
	
	 public JoueurScore() {
		 this.Image = new DrawableImage("propriété");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Image.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Image.redimensionner(windowWidth-60, windowHeight/2-25, windowWidth-20, windowHeight/2-55 );
		}
}
