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
		this.boxe = new DrawableBox(0,0,0,0,1,0,0,0.2);
		this.texte = new DrawableText("Fin du tour",1,0,0,0.2);
    updateWindowTaille(600, 300);
	}
	
	
	public void afficher() {
    InterfaceGraphique.glThread.ajouterEcouteur(this);

		boxe.afficher(InterfaceGraphique.glThread);
		texte.afficher(InterfaceGraphique.glThread);
	}

  /** Attendre la fin du tour, méthode bloquante.*/
  public void attendreFinTour() {
    /* On débloque le bouton.*/
    boxe.changerCouleur(0,0,0,0,1,0,0,1);
    texte.changerCouleur(1,0,0,1);

    /* On le rend cliquable.*/
    InterfaceGraphique.glThread.ajouterBouton(this);

    /* On attend jusqu'à que le joueur clique sur le bouton.*/
    estClique = false;
    while (!estClique && InterfaceGraphique.glThread.isAlive()) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
      }
    }

    /* On rebloque le bouton.*/
    InterfaceGraphique.glThread.retirerBouton(this);
    boxe.changerCouleur(0,0,0,0,1,0,0,0.2);
    texte.changerCouleur(1,0,0,0.2);
  }

  private boolean estClique = false;

  @Override
  public void executer() {
    estClique = true;
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
