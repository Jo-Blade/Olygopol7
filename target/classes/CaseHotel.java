/** Case sur laquelle un joueur a placé un hotel
 * @author : pisento
**/

public class CaseHotel implements CaseFonctionnelle, CaseGraphique {

  /** Numéro de la case.*/
  private int position;

  /** Propriétaire de la case.*/
  private Joueur proprietaire;

  @Override
  public void executer(Joueur joueur){

  }

  @Override
  public void afficher(StringBuffer buffer) {

    String symboleAAfficher = proprietaire.getSymbolePropriete();
    buffer.replace(position*4, position*4 + symboleAAfficher.length(),
        symboleAAfficher);

  }

}
