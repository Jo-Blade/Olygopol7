package logiqueMonopoly;
import java.util.Random;
import interfacegraphique.*;

public class Joueur implements Comparable<Joueur> {
  int SOLDEjoueur;
  Joueur joueur;
  private int POSITIONjoueur; //normalment on la tire de la classe plateau, ce n'est donc pas un entier. J'écris ca au début pour ne pas avcoir d'erreur
  private String NOMjoueur;
  private final int maxAVANCER = 6;

  /** Le pion graphique du joueur.*/
  private final Pion pion;

  /** Caractère qui permet d’afficher le joueur. */
  private char avatar;

  /** Charactères qui permettent de représenter une propriété du joueur.
   * exemple: "{ }" ou "< >" ou "( )" ou "| |"
   */
  private String symbolePropriete;

  //définir un constructeur, j'attend que la classe plateau soit ouverte dans le git 
  /**
   * Constructeur de la classe
   */
  public Joueur(int solde, int pos, String nom, Pion pion, String symbolePropriete) {
    assert solde != 0;
    assert nom != null;

    this.SOLDEjoueur = solde;
    this.POSITIONjoueur = pos;
    this.NOMjoueur = nom;
    this.pion = pion;
    this.symbolePropriete = symbolePropriete;

    pion.afficher();
  }


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
    if (NOMjoueur.equals("Téo"))
      return 6;

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
    pion.avancer(dcase);
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
  public void debiter(double monnaie) throws BanquerouteException{
    this.SOLDEjoueur -= monnaie;
    if (this.SOLDEjoueur <= 0) {
      throw new BanquerouteException(this);
    }
  }

  /**
   * Obtenir le caractère représentant le joueur.
   * @return le caractère représentant le joueur
   */
  public char getAvatar() {
    return avatar;
  }

  /** Obtenir le symbole représentant une propriété
   * qui appartient au joueur.
   * @return la chaine qui représente la propriété
   */
  public String getSymbolePropriete() {
    return symbolePropriete;
  }

  // On rend les joueurs comparables vis à vis de leur
  // solde pour le classement.
  @Override
  public int compareTo(Joueur j) {
    return ((Integer) SOLDEjoueur).compareTo(j.getSolde());
  }
}
