package MoteurGraphique;
/** Vecteur de deux flottants.
 * @author : pisento
**/
public class FloatVec2 {

  /** Première coordonnée du vecteur.*/
  final public float x;

  /** Deuxième coordonnée du vecteur.*/
  final public float y;

  /** Créer un nouveau vecteur avec ses coordonnées.
   * @param x coordonnée sur x
   * @param y coordonnée sur y
   */
  public FloatVec2(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "FloatVec2(" + x + ", " + y + ")";
  }

}
