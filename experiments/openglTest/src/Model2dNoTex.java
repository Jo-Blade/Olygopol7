/** Un modèle 2d sans texture associée.
 * @author : pisento
**/

public class Model2dNoTex extends Model2d {

  /** Créer un modèle 2d par ses vertices et ses uvs. */
  public Model2dNoTex(float[] vertices, float[] uvs) {
    super(vertices, uvs, null);
  }

  @Override
  public void utiliser() {

    if (!uploaded) {
      vboVert.uploadToGpu();
      vboUvs.uploadToGpu();
      uploaded = true;
    }

    // On ne fait rien car pas de texture
  }

}
