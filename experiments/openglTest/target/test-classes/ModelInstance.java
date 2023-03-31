/** Une instance d’un modèle,
 * pour instancier plusieurs modèles en un seul call à draw.
 * @author : pisento
**/
import java.util.Map;

public interface ModelInstance {

  /** Crée de nouvelles Vbo vides avec les locations associées
   * nécéssaires pour instancier un élément du modèle.
   * @return Map de Vbo, de clé: la location dans le Vao
   */
  public Map<Integer, Vbo<?>> initVbos ();

  /** Ajoute les données de l’élément d’instance aux Vbos pour pouvoir
   * l’instancier en même temps que les autres.
   * @param Vbos l’ensemble des Vbos nécéssaire à l’instance et leurs
   * locations associées.
   */
  public void appendVbos (Map<Integer, Vbo<?>> Vbos);
}
