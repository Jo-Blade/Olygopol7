package logiqueMonopoly;

import interfacegraphique.*;

public class Arbitre {

  Joueur[] tabJoueur;
  Plateau plateau;
  Boolean finDuJeu =false;  
  public Arbitre (Joueur[] tJoueur, Plateau plateau) {
    tabJoueur = tJoueur;
    this.plateau=plateau;
  }

  private InterfaceGraphique interfaceG = new InterfaceGraphique();

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

    String[] message = {"Veillez fermer la fenetre"};
    Popup pop = new Popup("Fin de la partie", message);
    pop.afficher();
    System.out.println("Fin de la partie");
  }

  public void jouerTour(Joueur J) throws BanquerouteException {
    CaseGraphique cGJ = plateau.getCase(J.getPosition()).getCaseGraphique();
    plateau.plateauGraphique.plateau.setCamera(cGJ.getX(), cGJ.getY(), cGJ.getZ(), 3);
    int de = interfaceG.boutonDe.tirer(J);
    System.out.println("Tour de : " + J.getNom());
    System.out.println("Vous avez" + J.getSolde() + "k$");
    System.out.println("Vous avez fait" + de );
    J.avancer(de);
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

    interfaceG.boutonFinTour.attendreFinTour();
  }


}



