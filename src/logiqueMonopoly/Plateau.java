package logiqueMonopoly;
/** Le plateau du Monopoly
 * @author : pisento
**/
import java.util.ArrayList;
import java.util.List;
import interfacegraphique.*;

public class Plateau {
	/** Liste des cases qui implique une action.*/
	private List<CaseFonctionnelle> listeCaseFonctionnelle = new ArrayList<CaseFonctionnelle>();

  /** Le plateau graphique.*/
  public final PlateauGraphique plateauGraphique = new PlateauGraphique();

  /** Construire un plateau par défaut.*/
  public Plateau() {

    for (int i = 0; i < 20; i++) {
      CaseProprieteGraphique caseG = new CaseProprieteGraphique(
          i,0,0,i,1,0.2
          );
      CaseLibre newCaseLibre = new CaseLibre(i, 300, "maison " + i, this, caseG);
      listeCaseFonctionnelle.add(newCaseLibre);
      caseG.ajouter(plateauGraphique);


    	// if ((i+1)%5 == 0) {
    	//   CaseChance newCaseChance = new CaseChance(i);
      //     listeCaseFonctionnelle.add(newCaseChance);
    	// }
    	// else {  
      //   // IL FAUT CHANGER LE NULL PAR LE TABLEAU DES JOUEURS !!!
    	//   CaseLibre newCaseLibre = new CaseLibre(i, 10, "maison " + i, this);
      //     listeCaseFonctionnelle.add(newCaseLibre);
    	// }

    }

    plateauGraphique.plateau.afficher(InterfaceGraphique.glThread);
    InterfaceGraphique.glThread.ajouterEcouteur(plateauGraphique);
    plateauGraphique.plateau.setCamera(0,0,0,3);
  }

  /** Modifier la i-eme case fonctionnelle.
   * @param indice l’indice de la case à remplacer (commence à 0)
   * @param caseF case fonctionnelle qui va remplacer
   */
  public void changerCaseFonctionnelle(int indice, CaseFonctionnelle caseF) {
    listeCaseFonctionnelle.set(indice, caseF);
  }

	/** Obtenir la i-eme case du plateau.
   * @param position indice de la case, 0 étant la case départ
   */
	public CaseFonctionnelle getCase(int position) {
		return listeCaseFonctionnelle.get(position % 20);
	}

}
