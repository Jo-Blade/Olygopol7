import java.util.scanner
public class Arbitre {
	
	Joueur[] tabJoueur;
	Boolean finDuJeu =false;  
	Scanner scanner = new Scanner(System.in)
	
	public Arbitre (Joueur[4] tJoueur) {
		tabJoueur = tJoueur;
	}

	public void arbitrer() {
		while finDuJeu {
			for (i=1; i<5; i++) {
				jouerTour(J[i]);
		}
	}
	
	public void jouerTour(Joueur J) {
		afficherPlateau();
		de = J.lancerDe();
		system.out.println("Vous avez fait" + de);
		J.avancer(de);
		afficherPlateau();
		CaseFonctionelle Case = Plateau.getCase(J.getPos);
		executer(Case);
		system.out.println("Voulez vous ajouter gerer vos propriété ?")
		try {
			String reponse = scanner.nextLine()
			if (reponse == "oui") {
				//Accees au menu propriete
			}
			else if (reponse != "non") {
				throw InvalideEntreeException
			} 
		} catch (InvalideEntreeException e) {
			system.out.println("On répond generalement à une question fermé par oui ou par non.")
		}

	}

	
  }

}

