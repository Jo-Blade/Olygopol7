/** Case sur laquelle un joueur a placé un hotel
 * @author : pisento
**/

public class CaseHotel implements CaseFonctionnelle, CaseGraphique {

  /** Numéro de la case.*/
  private int position;

  /** Propriétaire de la case.*/
  private Joueur proprietaire;

  /** Prix d'achat de la propriété (case).*/
  private final int prix;
  
  public CaseHotel(int position, Joueur proprietaire, int prix) {
	  this.proprietaire = proprietaire;
	  this.position = position;
    this.prix = prix;
  }

  @Override
  public void executer (Joueur joueur) throws BanquerouteException {
	  if (joueur != this.proprietaire) {
		  // crediter du loyer (A mettre en place)
      proprietaire.crediter(prix); // le choix a été fait de toujours
                                   // donner le prix demandé par le proprio, quitte
                                   // à ce que le joueur soit dans le négatif
      joueur.debiter(prix); // peut lever une exception
	  }
  }

  @Override
  public void afficher(StringBuffer buffer) {

    String symboleAAfficher = proprietaire.getSymbolePropriete();
    buffer.replace(position*4, position*4 + symboleAAfficher.length(),
        symboleAAfficher);

  }

}
