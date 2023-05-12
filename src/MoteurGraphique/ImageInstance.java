package MoteurGraphique;
/** Une instance d'une boite avec des bordures.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

public class ImageInstance extends ModelInstance {

  /** Le coin supérieur gauche de l’objet à instancier.*/
  private final FloatVec2 coin;

  /** Les dimensions (largeur, hauteur) de la boite.*/
  private final FloatVec2 dimensions;

  /** Créer un objet d’instance par ses propriétés.
   * @param coin coin suppérieur gauche de la boite
   * @param dimensions taille de la boite
   */
  public ImageInstance(FloatVec2 coin, FloatVec2 dimensions) {
    this.coin = coin;
    this.dimensions = dimensions;
  }

  @Override
  public Map<Integer, Vbo<?>> initVbos () {

    Map<Integer, Vbo<?>> map = new HashMap<Integer, Vbo<?>>();
    map.put(2, new FloatVec2Vbo());
    map.put(3, new FloatVec2Vbo());
    return map;
  }

  @Override
  public void appendVbos (Map<Integer, Vbo<?>> Vbos) {

    for (Map.Entry<Integer, Vbo<?>> entry : Vbos.entrySet()) {

      switch (entry.getKey()) {

        case 2:
          ((FloatVec2Vbo) entry.getValue()).push(coin);
          break;
        case 3:
          ((FloatVec2Vbo) entry.getValue()).push(dimensions);
          break;
      }
    }
  }

}
