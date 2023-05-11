package MoteurGraphique;
/** Instance d’un caractère.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

public class IsoImgInstance extends ModelInstance {

  /** Le centre de l’objet à instancier.*/
  private final FloatVec3 positionCentre;

  /** Position dans la texture.*/
  private final float indiceTex;

  /** Créer un objet d’instance par ses propriétés.
   * @param centreX la coordonnée x du centre
   * @param centreY la coordonnée y du centre
   * @param centreZ la coordonnée z du centre
   * @param indiceTex l'indice du sprite sur la texture
   */
  public IsoImgInstance(float centreX, float centreY, float centreZ, float indiceTex) {
    this.positionCentre = new FloatVec3(centreX, centreY, centreZ);
    this.indiceTex = indiceTex;
    setZIndex(- Math.floor(centreX) - Math.floor(centreY) + centreZ);
  }

  @Override
  public Map<Integer, Vbo<?>> initVbos () {

    Map<Integer, Vbo<?>> map = new HashMap<Integer, Vbo<?>>();
    map.put(2, new FloatVec3Vbo());
    map.put(3, new FloatVec1Vbo());
    return map;
  }

  @Override
  public void appendVbos (Map<Integer, Vbo<?>> Vbos) {

    for (Map.Entry<Integer, Vbo<?>> entry : Vbos.entrySet()) {

      switch (entry.getKey()) {

        case 2:
          ((FloatVec3Vbo) entry.getValue()).push(positionCentre);
          break;
        case 3:
          ((FloatVec1Vbo) entry.getValue()).push(indiceTex);
          break;
      }
    }
  }
}
