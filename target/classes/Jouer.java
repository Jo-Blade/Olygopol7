import logiqueMonopoly.Arbitre;
import logiqueMonopoly.Joueur;
import logiqueMonopoly.Plateau;
import moteurGraphique.glThread.*;
import interfacegraphique.*;

public class Jouer{

  /** Point d’entrée du programme.
   * @param args arguments de la ligne de commande
   */
public final static int  solde = 1000;
public static Plateau plat;
  private static Arbitre arbitre;

  public static void main(String[] args) {
    if (args[0].equals("-r")) {
      afficherRegle();
    }
    else {
      try {
        plat= new Plateau();
        Joueur[] tabJoueur = creationJoueur(args, plat);
        arbitre = new Arbitre(tabJoueur, plat);
        InterfaceGraphique.glThread.start();
        arbitre.arbitrer();
      }

      catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Merci de rentrer Moins de 4 Joueurs.");
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
