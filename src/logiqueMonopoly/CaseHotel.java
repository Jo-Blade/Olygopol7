/** Case sur laquelle un joueur a placé un hotel
 * @author : pisento
**/
package logiqueMonopoly;
import interfacegraphique.*;

public class CaseHotel implements CaseFonctionnelle {

  /** Propriétaire de la case.*/
  private Joueur proprietaire;

  /** Prix d'achat de la propriété (case).*/
  private final int prix;

  private final CaseProprieteGraphique caseG;
  
  public CaseHotel(CaseProprieteGraphique caseG, Joueur proprietaire, int prix) {
	  this.proprietaire = proprietaire;
    this.prix = prix;
    this.caseG = caseG;
  }

  @Override
  public void executer (Joueur joueur) throws BanquerouteException {
	  if (joueur != this.proprietaire) {
		  // crediter du loyer (A mettre en place)
      proprietaire.crediter(prix); // le choix a été fait de toujours
                                   // donner le prix demandé par le proprio, quitte
                                   // à ce que le joueur soit dans le négatif
      String[] message = {"Vous payez " + prix + "k$", "à " + proprietaire.getNom()};
      new Popup("evenement", message).afficher();
      joueur.debiter(prix); // peut lever une exception
	  }
    else {
      String[] message = {"Vous êtes chez vous,", "reposez vous :)"};
      new Popup("evenement", message).afficher();
    }
  }

  @Override
  public CaseGraphique getCaseGraphique() {
    return caseG;
  }

}
