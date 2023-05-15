package interfacegraphique;

import moteurGraphique.drawable.DrawableImage;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.window.WindowListener;

public class RepresentationPlateau implements WindowListener{
	
	final private DrawableImage Plateau;
	
	 public RepresentationPlateau() {
		 this.Plateau = new DrawableImage("Isotext");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Plateau.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			Plateau.redimensionner(50,  30, windowWidth-100 , windowHeight - 60);
		}

}
