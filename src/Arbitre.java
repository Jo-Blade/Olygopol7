import java.util.Scanner;
public class Arbitre {
	
	Joueur[] tabJoueur;
	Boolean finDuJeu =false;  
	Scanner scanner = new Scanner(System.in)
	
	public Arbitre (Joueur[4] tJoueur) {
		tabJoueur = tJoueur;
	}

	public void arbitrer() {
		while (finDuJeu) {
			for (i=1; i<5; i++) {
				jouerTour(J[i]);
		}
	}
	
	public void jouerTour(Joueur J) {
		afficherPlateau();
		de = J.lancerDe();
		System.out.println("Vous avez fait" + de);
		J.avancer(de);
		afficherPlateau();
		CaseFonctionelle Case = Plateau.getCase(J.getPos);
		executer(Case);
		System.out.println("Voulez vous ajouter gerer vos propriété ?");
		try {
			String reponse = scanner.nextLine()
			if (reponse.equals("oui")) {
				//Accees au menu propriete
			}
			else if (!reponse.equals("non")) {
				throw InvalideEntreeException
			} 
		} catch (InvalideEntreeException e) {
			system.out.println("On répond generalement à une question fermé par oui ou par non.");
		}

	}

	
  }

}

