import logiqueMonopoly.Arbitre;
import logiqueMonopoly.Joueur;
import logiqueMonopoly.Plateau;

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
        Joueur[] tabJoueur = creationJoueur(args);
        plat= new Plateau();
        arbitre = new Arbitre(tabJoueur, plat);
        arbitre.arbitrer();
      }

      catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Merci de rentrer Moins de 4 Joueurs.");
      }
    }
  }

  private static Joueur[] creationJoueur(String[] str) {
    Joueur[] tJoueur = {null, null,null,null} ;
    char[] avatar = {'a', 'b', 'c', 'd'};
    String[] symbolePropriete = {"< >", "{ }", "| |", "( )"};

    for (int i=0;i<str.length; i++){
      tJoueur[i]= new Joueur(solde, 0, str[i], avatar[i], symbolePropriete[i]);
    }
    return(tJoueur);
  }

  private static void afficherRegle() {
    System.out.println("Bienvenue sur notre jeu de monopoly.");
    System.out.println("Le jeu se déroule avec 4 joueurs, pour le lancer, executer le programme en ajoutant les noms des joueurs séparé par des @");
  }
}
