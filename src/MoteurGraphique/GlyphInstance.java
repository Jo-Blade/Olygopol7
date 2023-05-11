package MoteurGraphique;
/** Instance d’un caractère.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

public class GlyphInstance extends ModelInstance {

  /** Position dans la texture.*/
  private final FloatVec2 glyphPos;

  /** Taille du glyph dans la texture.*/
  private final FloatVec2 glyphTaille;

  /** Le centre de l’objet à instancier.*/
  private final FloatVec2 positionLettre;

  /** Le facteur d’échelle de l’objet à instancier.*/
  private final float size;

  /** Créer un objet d’instance par ses propriétés.
   * @param lettreX la coordonnée x de la lettre
   * @param lettreY la coordonnée y de la lettre
   * @param glyph le glyph de la lettre
   * @param size le facteur d’échelle
   */
  public GlyphInstance(float lettreX, float lettreY, Font.Glyph glyph, float size) {
    this.positionLettre = new FloatVec2(lettreX, lettreY);
    this.glyphPos = new FloatVec2(glyph.x, glyph.y);
    this.glyphTaille = new FloatVec2(glyph.width, glyph.height);
    this.size = size;
  }

  @Override
  public Map<Integer, Vbo<?>> initVbos () {

    Map<Integer, Vbo<?>> map = new HashMap<Integer, Vbo<?>>();
    map.put(2, new FloatVec2Vbo());
    map.put(3, new FloatVec2Vbo());
    map.put(4, new FloatVec2Vbo());
    map.put(5, new FloatVec1Vbo());
    return map;
  }

  @Override
  public void appendVbos (Map<Integer, Vbo<?>> Vbos) {

    for (Map.Entry<Integer, Vbo<?>> entry : Vbos.entrySet()) {

      switch (entry.getKey()) {

        case 2:
          ((FloatVec2Vbo) entry.getValue()).push(positionLettre);
          break;
        case 3:
          ((FloatVec2Vbo) entry.getValue()).push(glyphPos);
          break;
        case 4:
          ((FloatVec2Vbo) entry.getValue()).push(glyphTaille);
          break;
        case 5:
          ((FloatVec1Vbo) entry.getValue()).push(size);
          break;
      }
    }
  }
}
