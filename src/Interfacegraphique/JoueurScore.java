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
	 
	 public JoueurScore(Joueur j, int l, OpenglThread aff) {
		 this.joueur= j;
		 this.numeroLigne = l;
		 this.aff = aff;
		 this.nom = new DrawableText(joueur.getNom(),0.5,0.5,0.5,1);
		 this.solde = new DrawableText("" + joueur.getSolde() ,0,1,0,1);
		 nom.afficher(aff);
		 solde .afficher(aff);
	 }
	 
	 public void changerJoueur(Joueur j) {
		 this.joueur = j;
		 this.nom.cacher(aff);
		 this.nom = new DrawableText(joueur.getNom(),0.5,0.5,0.5,1);
		 this.solde.cacher(aff);
		 this.solde = new DrawableText("joueur.getSolde()",0,1,0,1);
		 nom.afficher(aff);
		 solde .afficher(aff);
			
	 }
	 
	
		@Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			
			nom.redimensionner(windowWidth - 40, -(25 - numeroLigne*5), 1.);
			solde.redimensionner(windowWidth - 40, (-17 - numeroLigne*5), 1.);
		}
		
}
