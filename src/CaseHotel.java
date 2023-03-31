/** Case sur laquelle un joueur a placé un hotel
 * @author : pisento
**/

public class CaseHotel implements CaseFonctionnelle, CaseGraphique {

  /** Numéro de la case.*/
  private int position;

  /** Propriétaire de la case.*/
  private Joueur proprietaire;
  
  public CaseHotel(int position, Joueur proprietaire) {
	  this.proprietaire = proprietaire;
	  this.position = position;
  }

  @Override
  public void executer(Joueur joueur){
	  if (joueur != this.proprietaire) {
		  // crediter du loyer (A mettre en place)
	  }
  }

  @Override
  public void afficher(StringBuffer buffer) {

    String symboleAAfficher = proprietaire.getSymbolePropriete();
    buffer.replace(position*4, position*4 + symboleAAfficher.length(),
        symboleAAfficher);

  }

}
