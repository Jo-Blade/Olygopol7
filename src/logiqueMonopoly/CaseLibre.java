/** Case qui n’a été achetée par personne pour le moment.
 * @author : pisento
**/
package logiqueMonopoly;
import interfacegraphique.*;

public class CaseLibre implements CaseFonctionnelle {

  /** Numéro de la case.*/
  private final int position;
  
  /** Prix d'achat de la propriété (case).*/
  private final int prix;

  /** Le nom de la propriété.*/
  private final String nom;

  /** Plateau sur lequel est situé la case.*/
  private final Plateau plateau;

  /** La case graphique associée.*/
  private final CaseProprieteGraphique caseGraphique;

  public CaseLibre(int position, int prix, String nom, Plateau plateau, CaseProprieteGraphique caseGraphique) {
	  this.position = position;
	  this.prix = prix;
    this.plateau = plateau;
    this.nom = nom;
    this.caseGraphique = caseGraphique;
  }

  public String getNom() {
    return nom;
  }

  public int getPrix() {
    return prix;
  }

  @Override
  public void executer(Joueur joueur) {

    PopupAchat popup = new PopupAchat(this);
    if(popup.afficher()) {
      // creer CaseHotel et crediter
      try {
        joueur.debiter(prix);

        CaseHotel newCaseHotel = new CaseHotel(caseGraphique, joueur, prix);
        plateau.changerCaseFonctionnelle(position, newCaseHotel);
        caseGraphique.achat(plateau.plateauGraphique);

      } catch (BanquerouteException exception) {
        System.out.println("Vous n’avez pas assez d’argent pour cette transaction");
        String[] messageErreur = {"Vous n'avez pas", "assez d'argent !"};
        Popup erreur = new Popup("erreur", messageErreur);
        erreur.afficher();
      }
    }

  }

  @Override
  public CaseGraphique getCaseGraphique() {
    return caseGraphique;
  }

  // @Override
  // public void executer(Joueur joueur){
  //   System.out.println("Voulez-vous achetez la propriété ? Oui : o ; Non : n");
  //   String e = entree.nextLine();

  //   if (e.equals("o")) {
  // 	  // creer CaseHotel et crediter
  //     try {
  //       joueur.debiter(prix);

  //       CaseHotel newCaseHotel = new CaseHotel(position, joueur, prix);
  //       plateau.changerCaseFonctionnelle(position, newCaseHotel);

  //     } catch (BanquerouteException exception) {
  //       System.out.println("Vous n’avez pas assez d’argent pour cette transaction");
  //       joueur.crediter(prix);
  //     }

  //   }else if (e.equals("n")) {
  // 	  int[] encheres = new int[4];
  // 	  // Proposer à chaque joueur et recupérer leur enchères
  // 	  for (int i = 0; i < tabJoueur.length; i++) {
  // 		  System.out.println("Voulez-vous enchérir, si oui, proposez un prix.");
  // 		  String string = entree.nextLine();
  // 		  if (string.equals("n")) {
  // 			  encheres[i] = 0;
  // 			  i++;
  // 		  } else {
  // 			  try {
  // 				  int n = Integer.parseInt(e);
  // 				  encheres[i] = n;
  // 				  i++;
  // 			  }catch(NumberFormatException e2) {
  // 				  System.out.println("Vous devez donnez un entier.");
  // 			  }
  // 			  
  // 			  }
  // 		  }
  // 
  // 		  
  // 	  }
  // 	  
  //   }

}
