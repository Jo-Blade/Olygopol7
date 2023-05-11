/** Case qui n’a été achetée par personne pour le moment.
 * @author : pisento
**/
import java.util.Scanner;
// SUPPRIMER LES ABSTRACT
public class CaseLibre implements CaseFonctionnelle, CaseGraphique {

  /** Le Scanner pour lire l’entrée utilisateur.*/
  Scanner entree;

  /** Numéro de la case.*/
  private final int position;
  
  /** Prix d'achat de la propriété (case).*/
  private final int prix;

  /** Plateau sur lequel est situé la case.*/
  private final Plateau plateau;
  private final Joueur[] tabJoueur;

  public CaseLibre(int position, int prix, Plateau plateau, Scanner entree, Joueur[] tJoueur) {
	  this.position = position;
	  this.prix = prix;
    this.plateau = plateau;
    this.entree = entree;
    this.tabJoueur=tJoueur;
  }
  
  @Override
  public void executer(Joueur joueur){
	  System.out.println("Voulez-vous achetez la propriété ? Oui : o ; Non : n");
	  String e = entree.nextLine();

	  if (e.equals("o")) {
		  // creer CaseHotel et crediter
      try {
        joueur.debiter(prix);

        CaseHotel newCaseHotel = new CaseHotel(position, joueur, prix);
        plateau.changerCaseFonctionnelle(position, newCaseHotel);
        plateau.changerCaseGraphique(position, newCaseHotel);

      } catch (BanquerouteException exception) {
        System.out.println("Vous n’avez pas assez d’argent pour cette transaction");
        joueur.crediter(prix);
      }

	  }else if (e.equals("n")) {
		  // Proposer à chaque joueur et recupérer leur enchères
		  for (int i = 0; i < tabJoueur.length; i++) {
			  System.out.println("Voulez-vous enchérir, si oui, proposez un prix.");
			  // if un chiffre entrée avec bouton "valider", enregistrer le prix associé au joueur
			  // sinon, i++
			  
		  }
		  
	  }
  }

  @Override
  public void afficher(StringBuffer buffer) {

    // [ ]-[ ]-[ ]-[ ] -- exemple de plateau vide
    // on doit donc mettre '[' à l’indice (position*4)
    // et ']' à l’indice (position*4) + 2
    buffer.setCharAt(position*4, '[');
    buffer.setCharAt(position*4 + 1, ' ');
    buffer.setCharAt(position*4 + 2, ']');

  }

}
