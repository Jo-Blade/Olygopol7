package Interfacegraphique;
import MoteurGraphique.*;

public class BoutonFinTour implements WindowListener {
	
	final private DrawableText texte;
	final private DrawableBox boxe; 
	 
	
	public BoutonFinTour() {
		this.boxe = new DrawableBox(0,0,0,0,1,0,0,1);
		this.texte = new DrawableText("Fin du tour",0,0,0,1);
	
	}
	
	
	public void afficher(OpenglThread aff) {
		boxe.afficher(aff);
		texte.afficher(aff);
		
	}



	@Override
	public void updateWindowTaille(int windowWidth, int windowHeight ) {
		boxe.redimensionner(windowWidth-40, windowHeight-25, windowWidth-10, windowHeight-10, 1,1.);
		texte.redimensionner(windowHeight-25, windowHeight-17,1.);
	}
	

}
