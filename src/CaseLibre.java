/** Case qui n’a été achetée par personne pour le moment.
 * @author : pisento
**/
import java.util.Scanner;
// SUPPRIMER LES ABSTRACT
public class CaseLibre implements CaseFonctionnelle, CaseGraphique {

  /** Numéro de la case.*/
  private int position;
  
  /** Prix d'achat de la propriété (case).*/
  private int prix;

  public CaseLibre(int position, int prix) {
	  this.position = position;
	  this.prix = prix;
  }
  
  @Override
  public void executer(Joueur joueur){
	  Scanner entree = new Scanner(System.in);
	  System.out.print("Voulez-vous achetez la propriété ? Oui : o ; Non : n");
	  String e = entree.nextChar();
	  if (e.equals("o")) {
		  // creer CaseHotel et crediter
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
