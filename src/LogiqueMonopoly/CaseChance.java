package LogiqueMonopoly;
/** Case qui n’a été achetée par personne pour le moment.
 * @author : pisento
**/
import java.util.Scanner;

public class CaseChance implements CaseFonctionnelle, CaseGraphique {

  /** Le Scanner pour lire l’entrée utilisateur.*/
  Scanner entree;

  /** Numéro de la case.*/
  private final int position;
  
  /** Prix d'achat de la propriété (case).*/
  private final int prix;

  /** Plateau sur lequel est situé la case.*/
  private final Plateau plateau;

  public CaseChance(int position, Plateau plateau) {
	    this.position = position;
        this.plateau = plateau;
        this.prix = 0;
  }
  
  @Override
  public void executer(Joueur joueur){
	  System.out.println("Voulez-vous achetez la propriété ? Oui : o ; Non : n");
	  
  }

  @Override
  public void afficher(StringBuffer buffer) {

    // = =-= =-= =-= = -- exemple de plateau vide
    // on doit donc mettre '[' à l’indice (position*4)
    // et ']' à l’indice (position*4) + 2
    buffer.setCharAt(position*4, '=');
    buffer.setCharAt(position*4 + 1, ' ');
    buffer.setCharAt(position*4 + 2, '=');

  }

}
