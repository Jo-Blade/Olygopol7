/** Une case qui permet d'acheter un terrain et son terrain associé.
 * @author : pisento
**/
package interfacegraphique;
import moteurGraphique.drawable.instance.*;

public class CaseChanceGraphique implements CaseGraphique {

  /** L'instance représentant la case atteignable par le pion du joueur.*/
  private final IsoImgInstance caseAtteignable;

  private final double posX;
  private final double posY;
  private final double posZ;

  /** Créer une CaseProprieteGraphique avec les coordonnée de la case 
   * représentant le terrain et de la case que peut atteindre le joueur
   * pour acheter la propriété.
   * @param posX coordonnéeX de la case atteignable
   * @param posY coordonnéeY de la case atteignable
   * @param posZ coordonnéeZ de la case atteignable
   * @param terrainX coordonnéeX de la case représentant le terrain
   * @param terrainY coordonnéeY de la case représentant le terrain
   * @param terrainZ coordonnéeZ de la case représentant le terrain
   */
  public CaseChanceGraphique(double posX, double posY, double posZ) {

    this.caseAtteignable = new IsoImgInstance((float) posX, (float) posY, (float) posZ, 10);

    this.posX = posX;
    this.posY = posY;
    this.posZ = posZ;
  }

  private PlateauGraphique plat = null;

  /** Joue une animation pour tirer une carte chance.*/
  public void animation() {
    // rajouter une animation sympa
    IsoImgInstance confettis = new IsoImgInstance((float) posX, (float) posY, (float) posZ, 5);
    plat.plateau.ajouter(confettis);
    for (int i = 6; i < 10; i++) {
      try {
        Thread.sleep(60);
      } catch (InterruptedException e) {
      }
      plat.plateau.retirer(confettis);
      confettis = new IsoImgInstance((float) posX, (float) posY, (float) posZ, i);
      plat.plateau.ajouter(confettis);
    }
    try {
      Thread.sleep(60);
    } catch (InterruptedException e) {
    }
    plat.plateau.retirer(confettis);
  }

  @Override
  public void ajouter(PlateauGraphique plateau) {
    plateau.plateau.ajouter(caseAtteignable);
    plat = plateau;
  }

  @Override
  public void retirer(PlateauGraphique plateau) {
    plateau.plateau.retirer(caseAtteignable);
  }

  @Override
  public double getX() {
    return posX;
  }

  @Override
  public double getY() {
    return posY;
  }

  @Override
  public double getZ() {
    return posZ;
  }

}
