/** Une case qui permet d'acheter un terrain et son terrain associé.
 * @author : pisento
**/
package interfacegraphique;
import moteurGraphique.drawable.instance.*;
import java.util.Random;

public class CaseProprieteGraphique implements CaseGraphique {

  /** L'instance représentant la case atteignable par le pion du joueur.*/
  private final IsoImgInstance caseAtteignable;

  /** L'instance représentant la case où on place la maison du joueur.*/
  private IsoImgInstance terrain;

  private final double posX;
  private final double posY;
  private final double posZ;
  private final double terrainX;
  private final double terrainY;
  private final double terrainZ;

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
  public CaseProprieteGraphique(double posX, double posY, double posZ,
      double terrainX, double terrainY, double terrainZ) {

    this.caseAtteignable = new IsoImgInstance((float) posX, (float) posY, (float) posZ, 3);
    this.terrain = new IsoImgInstance((float) terrainX, (float) terrainY, (float) terrainZ, 0);

    this.posX = posX;
    this.posY = posY;
    this.posZ = posZ;
    this.terrainX = terrainX;
    this.terrainY = terrainY;
    this.terrainZ = terrainZ;
  }

  /** Achat de la case par un joueur.*/
  public void achat(PlateauGraphique plateau) {
    plateau.plateau.retirer(terrain);
    Random random = new Random();
    /* On met au hasard un hotel ou une maison pour de la diversité.*/
    this.terrain = new IsoImgInstance((float) terrainX, (float) terrainY, (float) terrainZ,
        random.nextInt(2) + 1);
    plateau.plateau.ajouter(terrain);
  }

  @Override
  public void ajouter(PlateauGraphique plateau) {
    plateau.plateau.ajouter(caseAtteignable);
    plateau.plateau.ajouter(terrain);
  }

  @Override
  public void retirer(PlateauGraphique plateau) {
    plateau.plateau.retirer(caseAtteignable);
    plateau.plateau.retirer(terrain);
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
