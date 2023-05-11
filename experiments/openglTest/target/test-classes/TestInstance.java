/** Un exemple de type d’instance pour un modèle dont chaque
 * objet doit préciser la position du centre et un angle.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

public class TestInstance extends ModelInstance {

  /** Le centre de l’objet à instancier.*/
  private final FloatVec2 centre;

  /** L’angle de l’objet à instancier.*/
  private final float angle;

  /** Le facteur d’échelle de l’objet à instancier.*/
  private final float echelle;

  /** Créer un objet d’instance par ses propriétés.
   * @param centreX la coordonnée x du centre
   * @param centreY la coordonnée y du centre
   * @param angle le angle de rotation
   * @param echelle le facteur d’échelle
   */
  public TestInstance(float centreX, float centreY, float angle, float echelle) {
    this.centre = new FloatVec2(centreX, centreY);
    this.angle = angle;
    this.echelle = echelle;
  }

  @Override
  public Map<Integer, Vbo<?>> initVbos () {

    Map<Integer, Vbo<?>> map = new HashMap<Integer, Vbo<?>>();
    map.put(2, new FloatVec2Vbo());
    map.put(3, new FloatVec1Vbo());
    map.put(4, new FloatVec1Vbo());
    return map;
  }

  @Override
  public void appendVbos (Map<Integer, Vbo<?>> Vbos) {

    for (Map.Entry<Integer, Vbo<?>> entry : Vbos.entrySet()) {

      switch (entry.getKey()) {

        case 2:
          ((FloatVec2Vbo) entry.getValue()).push(centre);
          break;
        case 3:
          ((FloatVec1Vbo) entry.getValue()).push(angle);
          break;
        case 4:
          ((FloatVec1Vbo) entry.getValue()).push(echelle);
          break;
      }
    }
  }

}