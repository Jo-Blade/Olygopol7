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
		boxe.redimensionner(windowWidth - 170, windowHeight - 37, 160, 33, 2, 15);
		texte.redimensionner(windowWidth - 160, windowHeight - 37, .5);
	}
	

}
