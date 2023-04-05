
public class BanquerouteException extends Exception{
	/**
	 * @attribut joueur joueur sur lequel s'applique l'exception.
	 */
	private Joueur joueur;
	
	/**
	 * Exception levé en cas de solde nul d'un joueur. 
	 * Cela entraîne son élimination.
	 * @param joueur joueur sur lequel s'applique l'exception
	 */
	public  BanquerouteException(Joueur joueur) {
		super(joueur.getNom() + " est eliminé pour cause de solde nul !");
		this.joueur = joueur;
	}

	/**
	 * constructeur de joueur.
	 * @return le joueur sur lequel s'applique l'exception
	 */
	public Joueur getJoueur() {
		return this.joueur;	
	}
	

}
