package Interfacegraphique;
import LogiqueMonopoly.*;
import MoteurGraphique.DrawableText;
import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;

public class JoueurScore implements WindowListener{
	 private Joueur joueur;
	 private DrawableText nom;
	 private DrawableText solde;
	 final private int  numeroLigne;
	 final private OpenglThread aff;

   private int windowWidth = 600;
	 
	 public JoueurScore(Joueur j, int l, OpenglThread aff) {
		 this.joueur= j;
		 this.numeroLigne = l;
		 this.aff = aff;
     afficher();
	 }
	 
	 public void changerJoueur(Joueur j) {
		 this.joueur = j;
		 this.nom.cacher(aff);
		 this.solde.cacher(aff);
     afficher();
	 }

   private void afficher() {
		 this.nom = new DrawableText(joueur.getNom(),0.5,0.5,0.5,1);
     this.solde = new DrawableText(joueur.getSolde() / 1000. + "k",0,1,0,1);
     nom.afficher(aff);
     solde.afficher(aff);
     redimensionner();
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
