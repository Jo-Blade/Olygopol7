/** Un bouton du jeu du taquin.
 * @author : pisento
**/

public class TaquinButton extends Button {

  private Vec2Int position;

  private TaquinGrille grille;

  private HelloBis.Compteur compteurClics;

  /** Créer un bouton par ses deux extrémités.
   *  @param grille la grille du jeu de taquin
   *  @param point1 point en haut à gauche de la hitbox du bouton
   *  @param point2 point en bas à droite de la hitbox du bouton
   *  @param position la position sur le taquin où on a cliqué
   *  @param compteurClics le compteur de clics
   */
  public TaquinButton(TaquinGrille grille, FloatVec2 point1,
      FloatVec2 point2, Vec2Int position, HelloBis.Compteur compteurClics) {
    super(point1, point2);
    this.position = position;
    this.grille = grille;
    this.compteurClics = compteurClics;
  }

  @Override
  public void executer() {
    compteurClics.inc();
    grille.deplacerClic(position);
  }

}
