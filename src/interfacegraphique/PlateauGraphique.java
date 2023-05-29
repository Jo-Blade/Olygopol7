/** Le plateau affiché à l'écran
 * @author : pisento
**/
package interfacegraphique;
import moteurGraphique.window.*;
import moteurGraphique.drawable.*;

public class PlateauGraphique implements WindowListener {

  /** Le plateau isométrique affiché.*/
  public final DrawableIsoGrid plateau;

  public PlateauGraphique() {
    this.plateau = new DrawableIsoGrid("isoTex.png", 11);
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    plateau.redimensionner(60,0,windowWidth - 180 - 60,windowHeight);
  }

}
