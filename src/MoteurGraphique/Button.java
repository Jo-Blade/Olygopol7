package MoteurGraphique;
/** Un bouton qui peut être cliqué.
 * Attention, la classe run sera lancée dans le thread opengl
 * @author : pisento
**/

abstract public class Button {

  /** Le point en haut à gauche de la hitbox du bouton. */
  protected FloatVec2 point1;

  /** Le point en bas à droite de la hitbox du bouton. */
  protected FloatVec2 point2;

  /** Créer un bouton par ses deux extrémités.
   *  @param point1 point en haut à gauche de la hitbox du bouton
   *  @param point2 point en bas à droite de la hitbox du bouton
   */
  public Button(FloatVec2 point1, FloatVec2 point2) {
    this.point1 = point1;
    this.point2 = point2;
  }

  /** Indique si ce bouton est sélectionné par la souris.
   * @param coordonneesSouris les coordonnées de la souris en pixels
   * @return True si le bouton est sélectionné
   */
  public boolean isMouseSelected(FloatVec2 coordonneesSouris) {
    boolean conditionX = (point1.x <= coordonneesSouris.x) && (coordonneesSouris.x <= point2.x);
    boolean conditionY = (point1.y <= coordonneesSouris.y) && (coordonneesSouris.y <= point2.y);
    return conditionX && conditionY;
  }

  /** Action associée au bouton.*/
  abstract public void executer();

  /** Cliquer sur le bouton (lance un nouveau thread).*/
  public void cliquer() {
    new ButtonThread().start();
  }

  private class ButtonThread extends Thread {
    @Override
    public void run() {
      executer();
    }
  }

}
