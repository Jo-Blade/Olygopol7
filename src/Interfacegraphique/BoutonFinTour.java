package Interfacegraphique;
import MoteurGraphique.*;

public class BoutonFinTour implements WindowListener {
	
	final private DrawableText texte;
	final private DrawableBox boxe; 
	 
	
	public BoutonFinTour() {
		this.boxe = new DrawableBox();
		this.texte = new DrawableText("Fin du tour");
	
	}
	
	
	public void afficher(OpenglThread aff) {
		boxe.afficher(aff);
		texte.afficher(aff);
		
	}



	@Override
	public void updateWindowTaille(int windowWidth, int windowHeight) {
		//box.redimensioner(windowWitdth-40, windowHeight-25, windowWitdth-10, windowHeight-10);
		
	}
	

}
