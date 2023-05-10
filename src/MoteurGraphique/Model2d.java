/** Modèle 2d: vertices + texture.
 * @author : pisento
**/

public class Model2d {

  /** Les points du modèle.*/
  private final float[] vertices;

  /** La texture du modèle.*/
  private final Texture texture;

  /** Le vbo avec les points du modèle.*/
  protected final FloatVec2Vbo vboVert;

  /** Le vbo avec les coordonnées uvs du modèle.*/
  protected final FloatVec2Vbo vboUvs;

  /** true si les vbos ont déjà été upload sur le gpu.*/
  protected boolean uploaded = false;

  /** Nouveau modèle 2d par ses points et le nom d’une image.
   * @param vertices coordonnées des points du modèles dans l’ordre x puis y
   * @param resourceName nom d’une image pour créer une nouvelle texture
   */
  public Model2d(float[] vertices, float[] uvs, Texture texture) {
    this.vertices = vertices;
    this.texture = texture;

    if (vertices.length % 2 != 0)
      throw new IncompatibleTailleException(
          "La taille du tableau de vertices doit être un multiple de 2.");

    if (vertices.length != uvs.length)
      throw new IncompatibleTailleException(
          "Il doit y avoir autant de coordonnées uvs que de points.");

    vboVert = new FloatVec2Vbo();
    vboUvs  = new FloatVec2Vbo();
    for (int i = 0; i < vertices.length; i += 2) {
      vboVert.push(new FloatVec2(vertices[i], vertices[i + 1]));
      vboUvs.push(new FloatVec2(uvs[i], uvs[i + 1]));
    }
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
  public FloatVec2Vbo getVerticesVbo() {
    return vboVert;
  }

  /** Récupérer le vbo avec les coordonnées uvs.
   * @return le vbo
   */
  public FloatVec2Vbo getUvsVbo() {
    return vboUvs;
  }

  /** Utiliser le modèle (load la texture si besoin).*/
  void utiliser() {

    if (!uploaded) {
      vboVert.uploadToGpu();
      vboUvs.uploadToGpu();
      uploaded = true;
    }

    texture.loadTexture();
  }
}
