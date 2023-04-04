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
    int i = 1;

    while (!finDuJeu) {
      try {
        jouerTour(tabJoueur[i]);
      }
      catch (BanquerouteException e) {
        System.out.println(e.getMessage());
        finDuJeu = true;
      }
      i = i % 4 + 1;
    }

    System.out.println("Fin de la partie");
  }

  public void jouerTour(Joueur J) throws BanquerouteException {
    plateau.afficherPlateau(tabJoueur);
    int de = J.lancerDe();
    System.out.println("Vous avez fait" );
    J.avancer(de);
    plateau.afficherPlateau(tabJoueur);
    CaseFonctionnelle Case = plateau.getCase(J.getPosition());
    Case.executer(J);
    System.out.println("Voulez vous ajouter gerer vos propriété ?");
    try {
      String reponse = scanner.nextLine();
      if (reponse.equals("oui")) {
        //Accees au menu propriete
      }
      else if (!reponse.equals("non")) {
        throw new InvalideEntreeExcpetion();
      } 
    } catch (InvalideEntreeExcpetion e) {
      System.out.println("On répond generalement à une question fermé par oui ou par non.");
    }

  }


}



