import logiqueMonopoly.Arbitre;
import logiqueMonopoly.Joueur;
import logiqueMonopoly.Plateau;
import moteurGraphique.glThread.*;
import interfacegraphique.*;

public class Jouer{

  /** Point d’entrée du programme.
   * @param args arguments de la ligne de commande
   */
public final static int  solde = 1200;
public static Plateau plat;
  private static Arbitre arbitre;

  public static void main(String[] args) {
    if (args.length == 1 && args[0].equals("-r")) {
      afficherRegle();
    }
    else {
      if (args.length == 4) {
        plat= new Plateau();
        Joueur[] tabJoueur = creationJoueur(args, plat);
        arbitre = new Arbitre(tabJoueur, plat);
        InterfaceGraphique.glThread.start();
        arbitre.arbitrer();
      }
      else {
        System.out.println("usage : java -jar monopoly.jar pseudo1 pseudo2 pseudo3 pseudo4");
        System.out.println("règles: java -jar monopoly.jar -r");
      }
    }
  }

  private static Joueur[] creationJoueur(String[] str, Plateau plateau) {
    Joueur[] tJoueur = {null, null,null,null} ;
    String[] symbolePropriete = {"< >", "{ }", "| |", "( )"};

    for (int i=0;i<str.length; i++){
      tJoueur[i]= new Joueur(solde, 0, str[i], new Pion(plateau), symbolePropriete[i]);
    }
    return(tJoueur);
  }

  private static void afficherRegle() {
    System.out.println("Bienvenue sur notre jeu de monopoly.");
    System.out.println("Le jeu se déroule avec 4 joueurs, pour le lancer, executer le programme en ajoutant les noms des joueurs séparé par des @");
  }
}
