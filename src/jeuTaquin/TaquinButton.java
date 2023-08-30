package jeuTaquin;

import moteurGraphique.window.*;
import moteurGraphique.vecteur.*;

/** Un bouton du jeu du taquin.
 * @author : pisento
**/

public class TaquinButton extends Button implements WindowListener {

  private Vec2Int position;

  private TaquinGrille grille;

  private JouerTaquin.Compteur compteurClics;

  /** Le point en haut à gauche de la hitbox du bouton. */
  protected FloatVec2 point1;

  /** Le point en bas à droite de la hitbox du bouton. */
  protected FloatVec2 point2;

  /** Créer un bouton par ses deux extrémités.
   *  @param grille la grille du jeu de taquin
   *  @param point1 point en haut à gauche de la hitbox du bouton
   *  @param point2 point en bas à droite de la hitbox du bouton
   *  @param position la position sur le taquin où on a cliqué
   *  @param compteurClics le compteur de clics
   */
  public TaquinButton(TaquinGrille grille, FloatVec2 point1,
      FloatVec2 point2, Vec2Int position, JouerTaquin.Compteur compteurClics) {
    super(point1, point2);
    this.position = position;
    this.grille = grille;
    this.compteurClics = compteurClics;
    this.point1 = point1;
    this.point2 = point2;
  }

  @Override
  public void executer() {
    compteurClics.inc();
    grille.deplacerClic(position);
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    int winMin = Integer.min(windowWidth, windowHeight);
    float echelleX = (float) winMin;
    float echelleY = (float) winMin;
    float decalageX = (float) (windowWidth - winMin) / 2f;
    float decalageY = (float) (windowHeight - winMin) / 2f;

    super.point1 = new FloatVec2(point1.x * echelleX + decalageX, point1.y * echelleY + decalageY);
    super.point2 = new FloatVec2(point2.x * echelleX + decalageX, point2.y * echelleY + decalageY);
  }

}
