public class Arbitre {
	
	Joueur[] tabJoueur;
	Boolean finDuJeu =false;  
	private static scan = new Scanner(system)
	
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
		//a voir plus tard. 		
	}

	
  }

}

