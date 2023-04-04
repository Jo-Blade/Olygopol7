import java.util.Scanner;
import static java.lang.System.*;

public class Arbitre {
	
	Joueur[] tabJoueur;
	Plateau plateau;
	Boolean finDuJeu =false;  
	Scanner scanner = new Scanner(System.in);
	public Arbitre (Joueur[] tJoueur, Plateau plateau) {
		tabJoueur = tJoueur;
		this.plateau=plateau;
	}

	public void arbitrer() {
		while (finDuJeu) {
			for (int i=1; i<5; i++) {
				jouerTour(tabJoueur[i]);
		}
	}
	}
	
	public void jouerTour(Joueur J) {
		plateau.afficherPlateau();
		int de = J.lancerDe();
		System.out.println("Vous avez fait" );
		J.avancer(de);
		plateau.afficherPlateau();
		CaseFonctionnelle Case = plateau.getCase(J.getPosition());
		Case.executer(J);
		System.out.println("Voulez vous ajouter gerer vos propriété ?");
		try {
			String reponse = scanner.nextLine();
			if (reponse.equals("oui")) {
				//Accees au menu propriete
			}
			else if (!reponse.equals("non")) {
				throw new InvalideEntreeExcpetion();
			} 
		} catch (InvalideEntreeExcpetion e) {
			System.out.println("On répond generalement à une question fermé par oui ou par non.");
		}

	}

	
  }



