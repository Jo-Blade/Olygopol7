package moteurGraphique.drawable;
/** Une instance d’un modèle,
 * pour instancier plusieurs modèles en un seul call à draw.
 * @author : pisento
**/
import java.util.Map;

import moteurGraphique.glType.Vbo;

abstract public class ModelInstance implements Comparable<ModelInstance> {

  /** Indice qui indique quel objet doit être affiché au-dessus des autres.
   * Plus il est grand, plus l'objet est au premier plan.
   */
  private double zIndex = 0;

  /** Changer la valeur du ZIndex.
   * @param zIndex la nouvelle valeur du zIndex
   */
  protected void setZIndex(double zIndex) {
    this.zIndex = zIndex;
  }

  @Override
  public int compareTo(ModelInstance modelInstance) {
    return ((Double) zIndex).compareTo(modelInstance.zIndex);
  }

  /** Crée de nouvelles Vbo vides avec les locations associées
   * nécéssaires pour instancier un élément du modèle.
   * @return Map de Vbo, de clé: la location dans le Vao
   */
  abstract public Map<Integer, Vbo<?>> initVbos ();

  /** Ajoute les données de l’élément d’instance aux Vbos pour pouvoir
   * l’instancier en même temps que les autres.
   * @param Vbos l’ensemble des Vbos nécéssaire à l’instance et leurs
   * locations associées.
   */
  abstract public void appendVbos (Map<Integer, Vbo<?>> Vbos);
}
