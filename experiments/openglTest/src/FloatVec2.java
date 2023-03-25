/** Vecteur de deux flottants.
 * @author : pisento
**/
import static org.lwjgl.opengl.GL20.*;

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

  public static int getGlType() {
    return GL_FLOAT;
  }

  public static int getDimension() {
    return 2;
  }
}
