package Interfacegraphique;
import Interfacegraphique.DrawableImage;
import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;

public class BoutonDe1 implements WindowListener {
	final private DrawableImage De;
	
	 public BoutonDe1() {
		 this.De = new DrawableImage("de1");
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			De.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			De.redimensionner(10, windowHeight - 20, 20, windowHeight - 10);
		}
}
