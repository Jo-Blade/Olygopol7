package projetlong;


import java.util.Random;

public class Joueur {
	int SOLDEjoueur;
	private int POSITIONjoueur; //normalment on la tire de la classe plateau, ce n'est donc pas un entier. J'écris ca au début pour ne pas avcoir d'erreur
	private String NOMjoueur;
	private int maxAVANCER = 6;
	
	//définir un constructeur, j'attend que la classe plateau soit ouverte dans le git 
	
	/**
	 * Choix du nom du joueur.
	 * @return renvoie le nom du joueur
	 */
	public String getNom() {
		return this.NOMjoueur;
	}
	/**
	 * Position du joueur sur le plateau.
	 * @return la position du joueur selectionné
	 */
	public int getPosition() {
		return this.POSITIONjoueur;
	}
	/**
	 * Solde du joueur selectionné.
	 * @return le solde du joueur
	 */
	public int getSolde() {
		return this.SOLDEjoueur;
	}
	/**
	 * valeur du dé compris entre 1 et 6.
	 * @return la valeur du dé lancé par le joueur 
	 */
	public int lancerDe() {
		Random random = new Random();
		int nombreALEATOIRE = random.nextInt(maxAVANCER) + 1;
		return nombreALEATOIRE;
	}
	
	/**
	 * procédure quifait avancer le joueur.
	 * @param dcase nombre de case dont le joueur avance
	 */
	public void  avancer(int dcase) {
		this.POSITIONjoueur += dcase;
		
		
	}
	/**
	 * Augmante le solde du joueur.
	 * @param monnaie valeur que l'on souhaite ajouter au solde du jouer
	 */
	public void crediter(double monnaie) {
		this.SOLDEjoueur += monnaie;
	}
	/**
	 * Réduit le solde du joueur.
	 * @param monnaie valeur que l'on souhaite retirer au solde du joueur.
	 */
	public void debiter(double monnaie) {
		this.SOLDEjoueur -= monnaie;
	}
}
