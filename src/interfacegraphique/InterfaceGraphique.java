/** Classe utilitaire pour lancer l'interface graphique.
 * @author : pisento
**/
package interfacegraphique;
import moteurGraphique.glThread.*;

public class InterfaceGraphique {

  /** Le thread opengl de l'interface graphique.*/
  public final static OpenglThread glThread = new OpenglThread();

  /** Le bouton pour lancer le dé.*/
  public final BoutonDe boutonDe;

  /** Le bouton pour finir son tour.*/
  public final BoutonFinTour boutonFinTour;

  public InterfaceGraphique() {
    this.boutonDe = new BoutonDe();
    boutonDe.afficher();

    this.boutonFinTour = new BoutonFinTour();
    boutonFinTour.afficher();

    new BoutonPropriete().afficher();
  }

}
