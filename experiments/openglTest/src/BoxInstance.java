/** Une instance d'une boite avec des bordures.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

// infos à renseigner: position centre, largeur, hauteur, couleurs, épaisseur bordure, rayon bordure
//
public class BoxInstance implements ModelInstance {

  /** Le centre de l’objet à instancier.*/
  private final FloatVec2 centre;

  /** Les dimensions (largeur, hauteur) de la boite.*/
  private final FloatVec2 dimensions;

  /** La couleur de bordure.*/
  private final FloatVec4 couleurBordure;

  /** La couleur de l'intérieur.*/
  private final FloatVec4 couleurFond;

  /** Le rayon du coin arrondi.*/
  private final float rayonBordure;

  /** L'épaisseur de la bordure.*/
  private final float epaisseurBordure;

  /** Créer un objet d’instance par ses propriétés.
   * @param centre centre de la boite
   * @param dimensions taille de la boite
   * @param couleurBordure couleur de la bordure
   * @param couleurFond couleur du fond
   * @param rayonBordure border-radius
   * @param epaisseurBordure épaisseur de bordure
   */
  public BoxInstance(FloatVec2 centre, FloatVec2 dimensions,
      FloatVec4 couleurBordure, FloatVec4 couleurFond, float rayonBordure,
      float epaisseurBordure) {
    this.centre = centre;
    this.dimensions = dimensions;
    this.couleurBordure = couleurBordure;
    this.couleurFond = couleurFond;
    this.rayonBordure = rayonBordure;
    this.epaisseurBordure = epaisseurBordure;
  }

  @Override
  public Map<Integer, Vbo<?>> initVbos () {

    Map<Integer, Vbo<?>> map = new HashMap<Integer, Vbo<?>>();
    map.put(2, new FloatVec2Vbo());
    map.put(3, new FloatVec2Vbo());
    map.put(4, new FloatVec4Vbo());
    map.put(5, new FloatVec4Vbo());
    map.put(6, new FloatVec1Vbo());
    map.put(7, new FloatVec1Vbo());
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
          ((FloatVec2Vbo) entry.getValue()).push(dimensions);
          break;
        case 4:
          ((FloatVec4Vbo) entry.getValue()).push(couleurBordure);
          break;
        case 5:
          ((FloatVec4Vbo) entry.getValue()).push(couleurFond);
          break;
        case 6:
          ((FloatVec1Vbo) entry.getValue()).push(rayonBordure);
          break;
        case 7:
          ((FloatVec1Vbo) entry.getValue()).push(epaisseurBordure);
          break;
      }
    }
  }

}
