package Interfacegraphique;
import MoteurGraphique.*;

public class BoutonFinTour implements WindowListener {
	
	final private DrawableText texte;
	final private DrawableBox boxe; 
	 
	
	public BoutonFinTour() {
		this.boxe = new DrawableBox(0,0,0,0,1,0,0,1);
		this.texte = new DrawableText("Fin du tour",1,0,0,1);
    updateWindowTaille(600, 300);
	}
	
	
	public void afficher(OpenglThread aff) {
		boxe.afficher(aff);
		texte.afficher(aff);
	}



	@Override
	public void updateWindowTaille(int windowWidth, int windowHeight ) {
		boxe.redimensionner(windowWidth - 210, windowHeight - 37, 200, 33, 2, 10);
		texte.redimensionner(windowWidth - 200, windowHeight - 37, .5);
	}
	

}
