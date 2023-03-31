/** Le plateau du Monopoly
 * @author : pisento
**/
import java.util.ArrayList;
import java.util.List;

// REMPLACER LES MÉTHODES ABSTRAITES
public class Plateau {
	// Liste des cases qui implique une action
	private List<CaseFonctionnelle> listeCaseFonctionnelle = new ArrayList<CaseFonctionnelle>();
	// Liste des cases qui implique une action
	private List<CaseGraphique> listeCaseGraphique = new ArrayList<CaseGraphique>();
	private StringBuffer plateau; 
	// en abstract car pas encore coder pour les autres 
	// mettre la javadoc
	public CaseFonctionnelle getCase(int position) {
		return listeCaseFonctionnelle.get(position);
	}
	// mettre la javadoc
	public void afficherPlateau() {	
	  for (CaseGraphique c : listeCaseGraphique) {
		  c.afficher(plateau);
	  }
	  System.out.println(plateau);
  }
}
