package interfacegraphique;
import logiqueMonopoly.*;
import moteurGraphique.drawable.DrawableText;
import moteurGraphique.window.WindowListener;

public class JoueurScore implements WindowListener{
	 private DrawableText nom;
	 private DrawableText solde;
	 final private int  numeroLigne;

   private Joueur j;

   private int windowWidth = 600;
	 
	 public JoueurScore(Joueur joueur, int l) {
		 this.numeroLigne = l;
     this.j = joueur;

		 this.nom = new DrawableText(joueur.getNom(),0.5,0.5,0.5,1);
     this.solde = new DrawableText(joueur.getSolde() + "k$",0,1,0,1);
	 }
	 
	 public void changerJoueur(Joueur joueur) {
     this.j = joueur;

     nom.changer(joueur.getNom());
     solde.changer(joueur.getSolde() + "k$");
	 }

   public void afficher() {
     nom.afficher(InterfaceGraphique.glThread);
     solde.afficher(InterfaceGraphique.glThread);
     redimensionner();
     InterfaceGraphique.glThread.ajouterEcouteur(this);
   }

   private void redimensionner() {

     nom.redimensionner(windowWidth - 180, numeroLigne*25, .5);
     solde.redimensionner(windowWidth - 80, numeroLigne*25, .5);
   }

   /** Change la couleur pour montré que c'est le joueur
    * qui joue son tour si j est le joueur courant.
    */
   public void select(Joueur j) {
     if (this.j == j) {
       nom.changerCouleur(0.7,0.5,0,1);
       solde.changerCouleur(0.7,0.5,0,1);
     } else {
       nom.changerCouleur(0.5,0.5,0.5,1);
       solde.changerCouleur(0,1,0,1);
     }
   }

   @Override
   public void updateWindowTaille(int windowWidth, int windowHeight ) {

     this.windowWidth = windowWidth;
     redimensionner();
   }

}
