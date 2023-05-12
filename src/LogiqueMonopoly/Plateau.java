package LogiqueMonopoly;
/** Le plateau du Monopoly
 * @author : pisento
**/
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// REMPLACER LES MÉTHODES ABSTRAITES
public class Plateau {
	/** Liste des cases qui implique une action.*/
	private List<CaseFonctionnelle> listeCaseFonctionnelle = new ArrayList<CaseFonctionnelle>();

	/** Liste des cases qui implique une action.*/
	private List<CaseGraphique> listeCaseGraphique = new ArrayList<CaseGraphique>();

	/** Représentation textuelle du plateau.*/
	private StringBuffer plateau; 

  /** Construire un plateau par défaut.*/
  public Plateau() {

    // ATTENTION, ON DOIT AVOIR UN SEUL SCANNER POUR TOUT LE PROGRAMME !!
    Scanner testScan = new Scanner(System.in);

    for (int i = 0; i < 20; i++) {
      // IL FAUT CHANGER LE NULL PAR LE TABLEAU DES JOUEURS !!!
      CaseLibre newCaseLibre = new CaseLibre(i, 10, this, testScan, null);
      listeCaseFonctionnelle.add(newCaseLibre);
      listeCaseGraphique.add(newCaseLibre);
    }
  }

  /** Modifier la i-eme case fonctionnelle.
   * @param indice l’indice de la case à remplacer (commence à 0)
   * @param caseF case fonctionnelle qui va remplacer
   */
  public void changerCaseFonctionnelle(int indice, CaseFonctionnelle caseF) {
    listeCaseFonctionnelle.set(indice, caseF);
  }

  /** Modifier la i-eme case graphique.
   * @param indice l’indice de la case à remplacer (commence à 0)
   * @param caseG case graphique qui va remplacer
   */
  public void changerCaseGraphique(int indice, CaseGraphique caseG) {
    listeCaseGraphique.set(indice, caseG);
  }

	// Obtenir la case du plateau
	public CaseFonctionnelle getCase(int position) {
		return listeCaseFonctionnelle.get(position);
	}

	// Affiche le plateau
	public void afficherPlateau(Joueur[] joueurs) {	
    plateau = new StringBuffer("-------------------------------------------------------------------------------------------------");

    // afficher les cases
	for (CaseGraphique c : listeCaseGraphique) {
		  c.afficher(plateau);
	  }

    // afficher les joueurs
    for (Joueur joueur : joueurs)
      plateau.setCharAt(joueur.getPosition()*4 + 1, joueur.getAvatar());

	  System.out.println(plateau);
  }
}
