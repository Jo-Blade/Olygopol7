/**
 * @author : pisento
 **/
import java.time.Duration;
import java.time.Instant;
import MoteurGraphique.*;
import Interfacegraphique.*;
import LogiqueMonopoly.*;

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

    JoueurScore j1Score = new JoueurScore(new Joueur(10, 0, "j1", 'x', "< >"), 0, openglThread);
    openglThread.ajouterEcouteur(j1Score);

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
