package interfacegraphique;
import moteurGraphique.drawable.DrawableBox;
import moteurGraphique.drawable.DrawableText;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.vecteur.FloatVec2;
import moteurGraphique.window.Button;
import moteurGraphique.window.WindowListener;

public class BoutonFinTour extends Button implements WindowListener {
	
	final private DrawableText texte;
	final private DrawableBox boxe; 
	 
	
	public BoutonFinTour() {
    super(new FloatVec2(0,0), new FloatVec2(0,0));
		this.boxe = new DrawableBox(0,0,0,0,1,0,0,1);
		this.texte = new DrawableText("Fin du tour",1,0,0,1);
    updateWindowTaille(600, 300);
	}
	
	
	public void afficher(OpenglThread aff) {
		boxe.afficher(aff);
		texte.afficher(aff);
	}

  private boolean isActive = true;

  @Override
  public void executer() {
    isActive = !isActive;
    if (isActive) {
      boxe.changerCouleur(0,0,0,0,1,0,0,1);
      texte.changerCouleur(1,0,0,1);
    } else {
      System.out.println("Bouton Fin Tour");
      boxe.changerCouleur(0,0,0,0,0,0,0,0.2);
      texte.changerCouleur(0,0,0,0.2);
    }
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight ) {
    boxe.redimensionner(windowWidth - 170, windowHeight - 37, 160, 33, 2, 15);
    texte.redimensionner(windowWidth - 160, windowHeight - 37, .5);

    // repositionner la hitbox du bouton
    point1 = new FloatVec2(windowWidth - 170, windowHeight - 37);
    point2 = new FloatVec2(windowWidth - 170 + 160, windowHeight - 37 + 33);
  }

}
