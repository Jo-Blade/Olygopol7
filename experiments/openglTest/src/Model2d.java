/** Modèle 2d: vertices + texture.
 * @author : pisento
**/

public class Model2d {

  /** A MODIFIER !!*/
  private final float[] vertices;

  /** La texture du modèle.*/
  private final Texture texture;

  /** Le vbo avec les points du modèle.*/
  private final FloatVec2Vbo vbo;

  /** Nouveau modèle 2d par ses points et le nom d’une image.
   * @param vertices coordonnées des points du modèles dans l’ordre x puis y
   * @param resourceName nom d’une image pour créer une nouvelle texture
   */
  public Model2d(float[] vertices, String resourceName) {
    this.vertices = vertices;

    vbo = new FloatVec2Vbo();
    for (int i = 0; i < vertices.length; i += 2) {
      vbo.push(new FloatVec2(vertices[i], vertices[i + 1]));
    }

    vbo.uploadToGpu();
    texture = new Texture(resourceName);
  }

  /** Obtenir la texture du modèle.
   * @return la texture
   */
  public Texture getTexture() {
    return texture;
  }

  /** Obtenir les coordonnées des points.
   * @return le tableau de vertices
   */
  public float[] getVertices() {
    return vertices;
  }

  /** Récupérer le vbo avec les vertices.
   * @return le vbo
   */
  public FloatVec2Vbo getVbo() {
    return vbo;
  }
}
