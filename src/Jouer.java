public class Jouer{

  /** Point d’entrée du programme.
   * @param args arguments de la ligne de commande
   */
private int solde = 1000;
public static void main(String[] args) {
	if (args.equals("-r")) {
		afficherRegle();
	}
	else {
		try {
		tJoueur = creationJoueur(args);
		Arbitrer(tJoueur);
		}

	 catch (arrayoutofboundsexception e) {
		System.out.println("Merci de rentrer Moins de 4 Joueurs.");
	

  }
}
}
private Joueur[] creationJoueur(String[] str) {
	Joueur[] tJoueur;
	for (i=0;i<str.length(); i++){
		tJoueur[i]= Joueur(0, str[i], solde);

	}
	return(tJoueur);
	}



private void afficherRegle() {
	System.out.println("Bienvenue sur notre jeu de monopoly.");
	System.out.println("Le jeu se déroule avec 4 joueurs, pour le lancer, executer le programme en ajoutant les noms des joueurs séparé par des @");
}
}
