/** Un exemple de type d’instance pour un modèle dont chaque
 * objet doit préciser la position du centre et un angle.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

public class TestInstance implements ModelInstance {

  /** Le centre de l’objet à instancier.*/
  private final FloatVec2 centre;

  /** L’angle de l’objet à instancier.*/
  private final float angle;

  public TestInstance(float centreX, float centreY, float angle) {
    this.centre = new FloatVec2(centreX, centreY);
    this.angle = angle;
  }

  @Override
  public Map<Integer, Vbo<?>> initVbos () {

    Map<Integer, Vbo<?>> map = new HashMap<Integer, Vbo<?>>();
    map.put(1, new FloatVec2Vbo());
    map.put(2, new FloatVec1Vbo());
    return map;
  }

  @Override
  public void appendVbos (Map<Integer, Vbo<?>> Vbos) {

    for (Map.Entry<Integer, Vbo<?>> entry : Vbos.entrySet()) {

      switch (entry.getKey()) {

        case 1:
          ((FloatVec2Vbo) entry.getValue()).push(centre);
          break;
        case 2:
          ((FloatVec1Vbo) entry.getValue()).push(angle);
          break;
      }
    }
  }
}
