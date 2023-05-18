/** Une case du plateau affichable et atteignable par le joueur.
 * @author : pisento
**/
package interfacegraphique;

public interface CaseGraphique {

  /** Renvoie les coordonnées de la case ateignable
   * par le joueur dans le repère isométrique.
   */
  public double getX();
  public double getY();
  public double getZ();

  /** Ajouter la/les cases graphiques de l'objet
   * dans le plateau graphique. */
  public void ajouter(PlateauGraphique plateau);

  /** Retirer la/les cases graphiques de l'objet
   * dans le plateau graphique. */
  public void retirer(PlateauGraphique plateau);

}

