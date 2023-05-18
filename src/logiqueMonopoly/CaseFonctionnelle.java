/** Case du plateau qui est atteignable par le joueur
 *  et peut réaliser un événement quand il arrive dessus.
 * @author : pisento
**/
package logiqueMonopoly;
import interfacegraphique.*;

public interface CaseFonctionnelle {
  // case chance et caisse de communauté
	
  // mettre la javadoc
  public void executer(Joueur joueur) throws BanquerouteException;
  	// lancer un évènement depuis une pile d'évènement ?

  /** Obtenir la case graphique associée à la case fonctionnelle.*/
  public CaseGraphique getCaseGraphique();
}
