package logiqueMonopoly;
import java.util.Scanner;

public class Arbitre {

  Joueur[] tabJoueur;
  Plateau plateau;
  Boolean finDuJeu =false;  
  Scanner scanner = new Scanner(System.in);
  public Arbitre (Joueur[] tJoueur, Plateau plateau) {
    tabJoueur = tJoueur;
    this.plateau=plateau;
  }

  public void arbitrer() {
    int i = 0;

    while (!finDuJeu) {
      try {
        jouerTour(tabJoueur[i]);
      }
      catch (BanquerouteException e) {
        System.out.println(e.getMessage());
        finDuJeu = true;
      }
      i = (i + 1) % 4;
    }

    System.out.println("Fin de la partie");
  }

  public void jouerTour(Joueur J) throws BanquerouteException {
    plateau.afficherPlateau(tabJoueur);
    int de = J.lancerDe();
    System.out.println("Tour de : " + J.getNom());
    System.out.println("Vous avez" + J.getSolde() + "k$");
    System.out.println("Vous avez fait" + de );
    J.avancer(de);
    plateau.afficherPlateau(tabJoueur);
    CaseFonctionnelle Case = plateau.getCase(J.getPosition());
    Case.executer(J);
    /* JE MET EN COMMENTAIRE CAR IMPLÉMENTATION NON TERMINÉE
     * System.out.println("Voulez vous gerer vos propriété ?");
    try {
      String reponse = scanner.nextLine();
      if (reponse.equals("oui")) {
        //Appel de Menu propriete
      }
      else if (!reponse.equals("non")) {
        throw new InvalideEntreeExcpetion();
      } 
    } catch (InvalideEntreeExcpetion e) {
      System.out.println("On répond generalement à une question fermé par oui ou par non.");
    }
    */

  }


}



