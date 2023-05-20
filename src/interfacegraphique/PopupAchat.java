/** Pop up qui s'affiche à l'écran pour demander si on veut acheter une propriété.
 * @author : pisento
**/
package interfacegraphique;

import logiqueMonopoly.*;
import moteurGraphique.drawable.*;
import moteurGraphique.window.*;
import moteurGraphique.vecteur.FloatVec2;

public class PopupAchat implements WindowListener {

  /** Si la popup a été fermée.*/
  private int reponseUtilisateur;

  /** L'encadré de la popup.*/
  private DrawableBox encadre;

  /** Les lignes de texte affichés par la popup.*/
  private DrawableText[] lignes = new DrawableText[3];

  /** La reponse "oui".*/
  private reponse boutonOui;

  /** La reponse "non".*/
  private reponse boutonNon;

  /** Les boutons pour répondre à la popup (oui, non).*/
  private class reponse extends Button {
    /** Largeur du bouton.*/
    private final static int largeur = 80;

    /** Hauteur du bouton.*/
    private final static int hauteur = 40;

    /** Le label du bouton.*/
    private DrawableText label;

    /** L'encadre du bouton.*/
    private DrawableBox encadre;

    /** Le texte de la réponse.*/
    private String reponse;

    /** La valeur associée à la réponse.*/
    private int reponseValue;

    /** Créer un bouton réponse avec sa couleur et son texte.
     * @param label le texte du bouton
     * @param couleurR la composante rouge de la couleur (entre 0 et 1)
     * @param couleurG la composante verte de la couleur (entre 0 et 1)
     * @param couleurB la composante bleue de la couleur (entre 0 et 1)
     */
    public reponse(String label, int reponseValue, double couleurR, double couleurG, double couleurB) {
      super(new FloatVec2(0,0), new FloatVec2(0,0));

      this.encadre = new DrawableBox(couleurR,couleurG,couleurB,1,
          couleurR,couleurG,couleurB,0);
      this.reponse = label;
      this.label = new DrawableText(label,1,1,1,1);
      this.reponseValue = reponseValue;
    }

    /** Repositionner le bouton à l'écran.
     * @param positionX distance au bord gauche de l'écran (en pixels)
     * @param positionY distance au bord haut de l'écran (en pixels)
     */
    public void repositionner(int positionX, int positionY) {
      encadre.redimensionner(positionX, positionY, largeur, hauteur, 2, 20);
      label.redimensionner(positionX + largeur / 2 - (reponse.length()*13)/2,
          positionY + 3, .5);

      // repositionner la hitbox du bouton
      point1 = new FloatVec2(positionX, positionY);
      point2 = new FloatVec2(positionX + largeur, positionY + hauteur);
    }

    @Override
    public void executer() {
      PopupAchat.this.cacher();
      reponseUtilisateur = reponseValue;
    }

    public void afficher() {
      encadre.afficher(InterfaceGraphique.glThread);
      label.afficher(InterfaceGraphique.glThread);
      InterfaceGraphique.glThread.ajouterBouton(this);
    }

    public void cacher() {
      encadre.cacher(InterfaceGraphique.glThread);
      label.cacher(InterfaceGraphique.glThread);
      InterfaceGraphique.glThread.retirerBouton(this);
    }
  }

  /** Créer une nouvelle popup d'achat à partir d'une case que l'on peut acheter.
   * @param caseAchetable la case à acheter
   */
  public PopupAchat(CaseLibre terrain) {
    this.encadre = new DrawableBox(0.8,0.8,0.8,0.8,1,1,1,0.6);
    this.lignes[0] = new DrawableText("Voulez-vous acheter ?",0,0,0,1);
    this.lignes[1] = new DrawableText("nom  : " + terrain.getNom(),1,0,0,1);
    this.lignes[2] = new DrawableText("prix : " + terrain.getPrix(),1,0,0,1);

    this.boutonOui = new reponse("oui", 1, 0.1, 0.5, 0);
    this.boutonNon = new reponse("non", 2, 1, 0, 0);

    this.reponseUtilisateur = 0;
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    encadre.redimensionner(windowWidth / 2 - 200, windowHeight / 2, 400, 140, 2, 15);

    lignes[0].redimensionner(windowWidth / 2 - 140, windowHeight / 2, .5);
    for (int i = 1; i < lignes.length; i++)
      lignes[i].redimensionner(windowWidth / 2 - 180, windowHeight / 2 + 30*i, .5);

    boutonOui.repositionner(windowWidth / 2 - 120, windowHeight / 2 + 90);
    boutonNon.repositionner(windowWidth / 2 + 50, windowHeight / 2 + 90);
  }

  public void cacher() {
    encadre.cacher(InterfaceGraphique.glThread);
    for (DrawableText l : lignes)
      l.cacher(InterfaceGraphique.glThread);

    boutonOui.cacher();
    boutonNon.cacher();
  }

  public boolean afficher() {
    InterfaceGraphique.glThread.ajouterEcouteur(this);
    reponseUtilisateur = 0;

    encadre.afficher(InterfaceGraphique.glThread);
    for (DrawableText l : lignes)
      l.afficher(InterfaceGraphique.glThread);

    boutonOui.afficher();
    boutonNon.afficher();

    /* Attendre cliqué ou fenetre fermée.*/
    while (reponseUtilisateur == 0 && InterfaceGraphique.glThread.isAlive()) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
      }
    }

    return reponseUtilisateur == 1;
  }

}
