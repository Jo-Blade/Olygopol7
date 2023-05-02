/**
 * @author : pisento
 **/

public class HelloBis {

  public class Compteur {
    public int compteur = 0;

    public void inc() {
      compteur++;
    }
  }

  public static void main(String[] args) {
    System.out.println("Hello Bis !");

    OpenglThread openglThread = new OpenglThread();
    openglThread.start();

    TaquinGrille grille = new TaquinGrille(3, "texture.png");

    new TaquinCarre(2, 0, 1, 0, grille);
    new TaquinCarre(2, 1, 2, 0, grille);
    new TaquinCarre(1, 0, 0, 1, grille);
    new TaquinCarre(2, 2, 1, 1, grille);
    new TaquinCarre(1, 1, 2, 1, grille);
    new TaquinCarre(0, 1, 0, 2, grille);
    new TaquinCarre(1, 2, 1, 2, grille);
    new TaquinCarre(0, 2, 2, 2, grille);

    Compteur compteurClics = new HelloBis().new Compteur();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        TaquinButton bouton = 
          new TaquinButton( grille,
              new FloatVec2(1f/3f* (float) j, 1f/3f* (float) i),
              new FloatVec2(1f/3f* (float) (j+1), 1f/3f* (float) (i+1)),
              new Vec2Int(j, i),
              compteurClics);

        openglThread.ajouterBouton(bouton);
        openglThread.ajouterEcouteur(bouton);
      }
    }

    grille.afficher(openglThread);

    DrawableBox testBox = new DrawableBox();
    openglThread.ajouterEcouteur(testBox);
    testBox.afficher(openglThread);

    int compteur = 0;
    DrawableText texte = new DrawableText("clics : 0");
    openglThread.ajouterEcouteur(texte);
    texte.afficher(openglThread);

    DrawableIsoGrid testGrid = new DrawableIsoGrid("texture.png", 1);
    testGrid.afficher(openglThread);

    while ( openglThread.isAlive() ) {
      if (compteur != compteurClics.compteur) {
        texte.changer("clics : " + compteurClics.compteur);
        compteur = compteurClics.compteur;
      }
    }
  }
}
