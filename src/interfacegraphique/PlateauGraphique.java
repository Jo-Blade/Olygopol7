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
    this.plateau = new DrawableIsoGrid("isoTex.png", 5);
    updateWindowTaille(600,300);
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    plateau.redimensionner(70,0,windowWidth - 100,windowHeight);
  }

}
