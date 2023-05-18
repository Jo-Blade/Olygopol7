/** Le sprite d'un pion en vue isométrique.
 * @author : pisento
**/
package interfacegraphique;
import logiqueMonopoly.*;
import moteurGraphique.drawable.instance.*;

public class Pion {

  private Plateau plateau;

  private IsoImgInstance pion;

  private int position;

  public Pion(Plateau plateau) {
    this.plateau = plateau;
    this.pion = new IsoImgInstance(0, 0, 0, 4);
    this.position = 0;
  }

  public void afficher() {
    plateau.plateauGraphique.plateau.ajouter(pion);
  }

  public void avancer(int deplacement) {
    for (int i = 0; i < deplacement; i++) {
      CaseGraphique depart = plateau.getCase(position).getCaseGraphique();
      position ++;
      CaseGraphique arrivee = plateau.getCase(position).getCaseGraphique();
      double dx = arrivee.getX() - depart.getX();
      double dy = arrivee.getY() - depart.getY();
      double dz = arrivee.getZ() - depart.getZ();

      for (int j = 1; j <= 10; j++) {
        plateau.plateauGraphique.plateau.retirer(pion);
        pion = new IsoImgInstance((float) (depart.getX() + dx/10. * j),
            (float) (depart.getY() + dy/10. * j), (float) (depart.getZ() + dz/10. * j), 4);
        plateau.plateauGraphique.plateau.ajouter(pion);

        plateau.plateauGraphique.plateau.setCamera((float) (depart.getX() + dx/10. * j),
            (float) (depart.getY() + dy/10. * j), (float) (depart.getZ() + dz/10. * j), 3);

        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
