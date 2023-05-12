package Interfacegraphique;
import Interfacegraphique.DrawableImage;
import MoteurGraphique.OpenglThread;

public class BoutonDe1 {
	final private DrawableImage De;
	
	 public BoutonDe() {
		 this.De = new DrawableImage("de1");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			De.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			De.redimensionner(30, windowHeight - 20, 40, windowHeight - 10);
		}
}
