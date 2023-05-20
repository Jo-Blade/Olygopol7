/** Pop up qui s'affiche à l'écran pour demander si on veut acheter une propriété.
 * @author : pisento
**/
package interfacegraphique;

import moteurGraphique.drawable.*;
import moteurGraphique.window.*;
import moteurGraphique.vecteur.FloatVec2;

public class Popup implements WindowListener {

  /** Si la popup a été fermée.*/
  private boolean exited;

  /** L'encadré de la popup.*/
  private DrawableBox encadre;

  /** Le titre affiché par la popup.*/
  private DrawableText title;

  /** Le texte du titre affiché par la popup.*/
  private String titleString;

  /** Les lignes de texte affichés par la popup.*/
  private DrawableText[] lignes;

  /** La reponse "ok".*/
  private reponse boutonOK;

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

    /** Créer un bouton réponse avec sa couleur et son texte.
     * @param label le texte du bouton
     * @param couleurR la composante rouge de la couleur (entre 0 et 1)
     * @param couleurG la composante verte de la couleur (entre 0 et 1)
     * @param couleurB la composante bleue de la couleur (entre 0 et 1)
     */
    public reponse(String label, double couleurR, double couleurG, double couleurB) {
      super(new FloatVec2(0,0), new FloatVec2(0,0));

      this.encadre = new DrawableBox(couleurR,couleurG,couleurB,1,
          couleurR,couleurG,couleurB,0);
      this.reponse = label;
      this.label = new DrawableText(label,1,1,1,1);
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
      System.out.println("réponse : " + label);
      Popup.this.cacher();
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
  public Popup(String title, String[] texte) {
    this.encadre = new DrawableBox(0.8,0.8,0.8,0.8,1,1,1,0.6);
    this.title = new DrawableText(title, 0.1, 0.5, 0, 1);
    this.titleString = title;
    this.lignes = new DrawableText[texte.length];
    for (int i = 0; i < texte.length; i++)
      this.lignes[i] = new DrawableText(texte[i],0,0,0,1);

    this.boutonOK = new reponse("OK", 0.1, 0.5, 0);

    this.exited = false;
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    encadre.redimensionner(windowWidth / 2 - 200, windowHeight / 2, 400,
        30*(1 + lignes.length) + Popup.reponse.hauteur + 2, 2, 15);

    title.redimensionner(windowWidth / 2  - (10*titleString.length()) / 2,
        windowHeight / 2, .5);

    for (int i = 0; i < lignes.length; i++)
      lignes[i].redimensionner(windowWidth / 2 - 180, windowHeight / 2 + 30*(1 + i), .5);

    boutonOK.repositionner(windowWidth / 2  - Popup.reponse.largeur / 2,
        windowHeight / 2 + 30*(1 + lignes.length));
  }

  public void cacher() {
    encadre.cacher(InterfaceGraphique.glThread);
    title.cacher(InterfaceGraphique.glThread);
    for (DrawableText l : lignes)
      l.cacher(InterfaceGraphique.glThread);

    boutonOK.cacher();
    exited = true;
  }

  public void afficher() {
    InterfaceGraphique.glThread.ajouterEcouteur(this);
    exited = false;

    encadre.afficher(InterfaceGraphique.glThread);
    title.afficher(InterfaceGraphique.glThread);
    for (DrawableText l : lignes)
      l.afficher(InterfaceGraphique.glThread);

    boutonOK.afficher();

    /* Attendre cliqué ou fenetre fermée.*/
    while (!exited && InterfaceGraphique.glThread.isAlive()) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
      }
    }
  }

}
