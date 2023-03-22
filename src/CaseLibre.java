/** Case qui n’a été achetée par personne pour le moment.
 * @author : pisento
**/

// SUPPRIMER LES ABSTRACT
public class CaseLibre implements CaseFonctionnelle, CaseGraphique {

  /** Numéro de la case.*/
  private int position;

  @Override
  public void executer(Joueur joueur){

  }

  @Override
  public void afficher(StringBuffer buffer) {

    // [ ]-[ ]-[ ]-[ ] -- exemple de plateau vide
    // on doit donc mettre '[' à l’indice (position*4)
    // et ']' à l’indice (position*4) + 2
    buffer.setCharAt(position*4, '[');
    buffer.setCharAt(position*4 + 2, ']');

  }

}
