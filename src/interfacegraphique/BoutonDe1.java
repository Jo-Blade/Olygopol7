package interfacegraphique;
import moteurGraphique.drawable.DrawableImage;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.window.WindowListener;

public class BoutonDe1 implements WindowListener {
	final private DrawableImage De;
	
	 public BoutonDe1() {
		 this.De = new DrawableImage("de1.png");
     updateWindowTaille(600, 300);
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			De.afficher(aff);
		}
	 
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			De.redimensionner(63, windowHeight - 64, 47, 54);
		}
}
