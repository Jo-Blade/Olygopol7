package jeuTaquin;
import moteurGraphique.glThread.*;
import moteurGraphique.vecteur.*;
import moteurGraphique.drawable.*;

import java.util.*;

/**
 * @author : pisento
 **/

public class JouerTaquin {

  public class Compteur {
    public int compteur = 0;

    public void inc() {
      compteur++;
    }
  }

  public static void main(String[] args) {
    System.out.println("Hello Bis !");

    int N = 3;

    if (args.length >= 1) {
      Scanner scan = new Scanner(args[0]);
      N = scan.nextInt();
      scan.close();
    }

    OpenglThread openglThread = new OpenglThread();
    openglThread.start();

    TaquinGrille grille = new TaquinGrille(N, "texture.png");

    List<Vec2Int> all_pos = new ArrayList<>();
    List<Vec2Int> all_uvs = new ArrayList<>();

    for (int i = 1; i < N * N; i++) {
      all_pos.add(new Vec2Int(i % N, i / N));
      all_uvs.add(new Vec2Int(i % N, i / N));
    }

    Collections.shuffle(all_pos);

    for (int i = 1; i < N * N; i++) {
      Vec2Int pos = all_pos.remove(0);
      Vec2Int uvs = all_uvs.remove(0);
      new TaquinCarre(pos.x, pos.y,
          uvs.x, uvs.y, grille);
    }

    Compteur compteurClics = new JouerTaquin().new Compteur();

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        TaquinButton bouton = 
          new TaquinButton( grille,
              new FloatVec2(1f/((float) N) * (float) j, 1f/((float) N) * (float) i),
              new FloatVec2(1f/((float) N) * (float) (j+1), 1f/((float) N) * (float) (i+1)),
              new Vec2Int(j, i),
              compteurClics);

        openglThread.ajouterBouton(bouton);
        openglThread.ajouterEcouteur(bouton);
      }
    }

    grille.afficher(openglThread);

    DrawableBox testBox = new DrawableBox(0, 0, 0, 0, 1, 0, 0, 1);
    testBox.redimensionner(5, 5, 150, 30, 2, 15);
    testBox.afficher(openglThread);

    int compteur = 0;
    DrawableText texte = new DrawableText("clics : 0", 0, 0, 0, 1);
    texte.redimensionner(10, 5, 0.5);
    texte.afficher(openglThread);

    while ( openglThread.isAlive() ) {

      /* on met un délai pour ne pas bouffer tout le temps processeur */
      try {
        Thread.sleep(1);
      }
      catch(Exception e) {
        System.out.println(e);
      }
      if (compteur != compteurClics.compteur) {
        texte.changer("clics : " + compteurClics.compteur);
        compteur = compteurClics.compteur;
      }
    }
  }
}
