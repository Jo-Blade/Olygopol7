package logiqueMonopoly;

// On fait cette classe parce qu'on a besoin d'accèder au propriété des gens dans le menue propriété sans parcourire tout le plateau à chaque fois

public class Proprietes {
	
	// 4 ArrayList qui contiens les CaseHotel des 4 joueurs ( justes leurs position ) 
	
	public Proprietes(Joueur joueur) {
		// On initialise les 4 listes avec rien 
	}
	public void Ajouter(Joueur joueur,int casehotel) {
		// On ajoute la positon de la case hotel à la liste de son joueur quand il a acheter un hotel
	}
	public void Hypothequer(Joueur joueur,int casehotel) {
		// On supprime la positon de la case hotel de la liste de son joueur quand il a l'hypotheque
	}
}
