package moteurGraphique.vecteur;
/** Vecteur de deux flottants.
 * @author : pisento
**/

public class FloatVec4 {

  /** Première coordonnée du vecteur.*/
  final public float r;

  /** Deuxième coordonnée du vecteur.*/
  final public float g;

  /** Deuxième coordonnée du vecteur.*/
  final public float b;

  /** Deuxième coordonnée du vecteur.*/
  final public float a;

  /** Créer un nouveau vecteur avec ses coordonnées.
   * @param r coordonnée 1
   * @param g coordonnée 2
   * @param b coordonnée 3
   * @param a coordonnée 4
   */
  public FloatVec4(float r, float g, float b, float a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

}
