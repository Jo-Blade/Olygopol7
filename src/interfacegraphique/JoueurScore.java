package interfacegraphique;
import logiqueMonopoly.*;
import moteurGraphique.drawable.DrawableText;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.window.WindowListener;

public class JoueurScore implements WindowListener{
	 private DrawableText nom;
	 private DrawableText solde;
	 final private int  numeroLigne;
	 final private OpenglThread aff;

   private int windowWidth = 600;
	 
	 public JoueurScore(Joueur joueur, int l, OpenglThread aff) {
		 this.numeroLigne = l;
		 this.aff = aff;

		 this.nom = new DrawableText(joueur.getNom(),0.5,0.5,0.5,1);
     this.solde = new DrawableText(joueur.getSolde() / 1000. + "k",0,1,0,1);
	 }
	 
	 public void changerJoueur(Joueur joueur) {
     nom.changer(joueur.getNom());
     solde.changer(joueur.getSolde() / 1000. + "k");
	 }

   public void afficher() {
     nom.afficher(aff);
     solde.afficher(aff);
     redimensionner();
     aff.ajouterEcouteur(this);
   }

   private void redimensionner() {

     nom.redimensionner(windowWidth - 180, numeroLigne*25, .5);
     solde.redimensionner(windowWidth - 80, numeroLigne*25, .5);
   }


   @Override
   public void updateWindowTaille(int windowWidth, int windowHeight ) {

     this.windowWidth = windowWidth;
     redimensionner();
   }

}
