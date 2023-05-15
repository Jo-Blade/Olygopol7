/**
 * @author : pisento
 **/
import interfacegraphique.*;
import logiqueMonopoly.*;
import moteurGraphique.drawable.DrawableIsoGrid;
import moteurGraphique.glThread.OpenglThread;

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


    DrawableIsoGrid testGrid = new DrawableIsoGrid("isoTex.png", 5);
    openglThread.ajouterEcouteur(testGrid);
    testGrid.afficher(openglThread);

    BoutonFinTour bFinTour = new BoutonFinTour();
    openglThread.ajouterEcouteur(bFinTour);
    bFinTour.afficher(openglThread);
    openglThread.ajouterBouton(bFinTour);

    JoueurScore j1Score = new JoueurScore(new Joueur(10000, 0, "j1", 'x', "< >"), 0, openglThread);
    openglThread.ajouterEcouteur(j1Score);

    JoueurScore j2Score = new JoueurScore(new Joueur(8000, 0, "joueur2", 'x', "< >"), 1, openglThread);
    openglThread.ajouterEcouteur(j2Score);

    JoueurScore j3Score = new JoueurScore(new Joueur(1000, 0, "Téo", 'x', "< >"), 2, openglThread);
    openglThread.ajouterEcouteur(j3Score);

    JoueurScore j4Score = new JoueurScore(new Joueur(230, 0, "Emile", 'x', "< >"), 3, openglThread);
    openglThread.ajouterEcouteur(j4Score);

    BoutonPropriete bProp = new BoutonPropriete();
    bProp.afficher(openglThread);
    openglThread.ajouterEcouteur(bProp);
    openglThread.ajouterBouton(bProp);

    BoutonDe1 bDe1 = new BoutonDe1();
    bDe1.afficher(openglThread);
    openglThread.ajouterEcouteur(bDe1);

    BoutonDe2 bDe2 = new BoutonDe2();
    bDe2.afficher(openglThread);
    openglThread.ajouterEcouteur(bDe2);

    while ( openglThread.isAlive() ) {
      // on met un délai pour ne pas bouffer tout le temps processeur
      try {
        Thread.sleep(1);
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
  }
}
