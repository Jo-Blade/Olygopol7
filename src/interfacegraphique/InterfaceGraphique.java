/** Classe utilitaire pour lancer l'interface graphique.
 * @author : pisento
**/
package interfacegraphique;
import moteurGraphique.glThread.*;
import logiqueMonopoly.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class InterfaceGraphique {

  /** Le thread opengl de l'interface graphique.*/
  public final static OpenglThread glThread = new OpenglThread();

  /** Le bouton pour lancer le dé.*/
  public final BoutonDe boutonDe;

  /** Le bouton pour finir son tour.*/
  public final BoutonFinTour boutonFinTour;

  /** Les scores des joueurs.*/
  public final JoueurScore[] scores = new JoueurScore[4];

  /** Liste des joueurs.*/
  public final List<Joueur> listeJoueurs = new ArrayList<>();

  public InterfaceGraphique(Joueur[] joueurs) {
    this.boutonDe = new BoutonDe();
    boutonDe.afficher();

    this.boutonFinTour = new BoutonFinTour();
    boutonFinTour.afficher();

    new BoutonPropriete().afficher();

    int i = 0;
    for (Joueur j : joueurs) {
      listeJoueurs.add(j);
      scores[i] = new JoueurScore(j, i);
      scores[i].afficher();
      i++;
    }
  }

  /** Actualiser les scores des joueurs affichés.*/
  public void actualiserScores() {
    Collections.sort(listeJoueurs);
    int i = 3;
    for (Joueur j : listeJoueurs)
      scores[i--].changerJoueur(j);
  }

  /** Changer le joueur dont c'est le tour dans l'affichage.*/
  public void changerTour(Joueur joueurCourant) {
    for (JoueurScore score : scores)
      score.select(joueurCourant);
  }

}
