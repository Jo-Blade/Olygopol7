package interfacegraphique;
import moteurGraphique.drawable.*;
import moteurGraphique.window.*;
import moteurGraphique.vecteur.*;
import logiqueMonopoly.Joueur;

public class BoutonDe extends Button implements WindowListener {
  /** Image du dé lorsqu'on peut cliquer dessus.*/
	final private DrawableImage de;

  /** Image du dé lorsqu'on ne peut pas cliquer dessus.*/
	final private DrawableImage deBloque;

  /** Si le dé a été cliqué.*/
  private boolean estClique = false;

  public BoutonDe() {
    super(new FloatVec2(0,0), new FloatVec2(0,0));
    this.de = new DrawableImage("de.png");
    this.deBloque = new DrawableImage("deBloque.png");
  }

  /** Afficher le de bloque jusqu'a qu'on demande a tirer le dé.*/
  public void afficher() {
    InterfaceGraphique.glThread.ajouterEcouteur(this);
    deBloque.afficher(InterfaceGraphique.glThread);
  }

  public void executer() {
    estClique = true;
    System.out.println("lancer dé");
  }

  /** Tirer le dé, méthode bloquante.*/
  public int tirer(Joueur jCourant) {
    /* On débloque le dé.*/
    deBloque.cacher(InterfaceGraphique.glThread);
    de.afficher(InterfaceGraphique.glThread);

    /* On le rend cliquable.*/
    InterfaceGraphique.glThread.ajouterBouton(this);

    /* On attend jusqu'à que le joueur clique sur le dé.*/
    estClique = false;
    while (!estClique && InterfaceGraphique.glThread.isAlive()) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
      }
    }

    int nombreTire = jCourant.lancerDe();
    /* On peut éventuellement jouer une animation ici.*/
    animationClic(nombreTire);

    /* On rebloque le dé.*/
    InterfaceGraphique.glThread.retirerBouton(this);
    de.cacher(InterfaceGraphique.glThread);
    deBloque.afficher(InterfaceGraphique.glThread);
    return nombreTire;
  }

  /** Jouer une animation qui affiche le numéro tiré
   * @param numero le numéro tiré par le dé
   */
  private void animationClic(int numero) {
    /* Secouer le dé.*/
    for (int i = -5; i < 5; i++) {
      de.redimensionner((int) point1.x + i, (int) point1.y - i, 47, 54);
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
      }
    }
    for (int i = 5; i >= 0; i--) {
      de.redimensionner((int) point1.x + i, (int) point1.y - i, 47, 54);
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
      }
    }

    /* On affiche le numéro.*/
    DrawableText numeroAffiche = new DrawableText("" + numero, 1, 0, 0, 1);
    numeroAffiche.redimensionner((int) point1.x, (int) point1.y - 50, 1);

    numeroAffiche.afficher(InterfaceGraphique.glThread);
    for (double i = 0.0; i < 1.; i += 0.1) {
      numeroAffiche.changerCouleur(1,0,0,i);
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
      }
    }

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
    }

    for (double i = 1.; i > 0.; i -= 0.1) {
      numeroAffiche.changerCouleur(1,0,0,i);
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
      }
    }
    numeroAffiche.cacher(InterfaceGraphique.glThread);
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight ) {

    de.redimensionner(20, windowHeight - 64, 47, 54);
    deBloque.redimensionner(20, windowHeight - 64, 47, 54);

    // mettre a jour la hitbox du bouton
    point1 = new FloatVec2(23, windowHeight - 64);
    point2 = new FloatVec2(70, windowHeight - 10);
  }
}
