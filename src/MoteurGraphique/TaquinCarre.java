package MoteurGraphique;
/** Un exemple de type d’instance pour un modèle dont chaque
 * objet doit préciser la position du centre et un angle.
 * @author : pisento
**/
import java.util.Map;
import java.util.HashMap;

public class TaquinCarre extends ModelInstance {

  /** Grille à laquelle appartient l’objet.*/
  private TaquinGrille grille;

  /** Le centre de l’objet à instancier.*/
  private FloatVec2 centre;

  /** Le centre des uvs sur la textue.*/
  private final FloatVec2 centreTex;

  /** Créer un objet d’instance par ses propriétés.
   * @param centreX la coordonnée x du centre
   * @param centreY la coordonnée y du centre
   * @param centreTexX la coordonnée uv x du centre sur la texture
   * @param centreTexY la coordonnée uv y du centre sur la texture
   */
  public TaquinCarre(float centreX, float centreY, float centreTexX, float centreTexY, TaquinGrille grille) {
    this.centre = new FloatVec2(centreX, centreY);
    this.centreTex = new FloatVec2(centreTexX, centreTexY);
    this.grille = grille;
    grille.addPiece(this);
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
          ((FloatVec2Vbo) entry.getValue()).push(centre);
          break;
        case 3:
          ((FloatVec2Vbo) entry.getValue()).push(centreTex);
          break;
      }
    }
  }

  private class Animation extends Thread {

    /** Le déplacement à animer.*/
    private FloatVec2 deplacement;

    public Animation(FloatVec2 deplacement) {
      this.deplacement = deplacement;
    }

    @Override
    public void run() {
      try {
        for (int i = 0; i < 100; i++) {
          centre = new FloatVec2(centre.x + deplacement.x / 100f, centre.y + deplacement.y / 100f);
          grille.valider();
          Thread.sleep(1);
        }
      }
      catch (InterruptedException e) {
      }
    }
  }

  /** Pousser la case de 1 dans une direction.
   * @param direction la direction dans laquelle on pousse la case 
   */
  public void pousser(Direction direction) {

    FloatVec2 dir;
    switch (direction) {
      case HAUT:
        dir = new FloatVec2(0, -1);
        break;
      case BAS:
        dir = new FloatVec2(0, +1);
        break;
      case GAUCHE:
        dir = new FloatVec2(-1, 0);
        break;
      default:
        dir = new FloatVec2(1, 0);
        break;
    }

    new Animation(dir).start();
  }

  public int getX() {
    return (int) (centre.x + 0.2); // +0.2 pour éviter les erreurs d’arrondi
  }

  public int getY() {
    return (int) (centre.y + 0.2);
  }

}
