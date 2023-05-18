package interfacegraphique;

import moteurGraphique.drawable.DrawableImage;
import moteurGraphique.vecteur.FloatVec2;
import moteurGraphique.window.Button;
import moteurGraphique.window.WindowListener;

public class BoutonPropriete extends Button implements WindowListener {

	final private DrawableImage Image;
	
	 public BoutonPropriete() {
     super(new FloatVec2(0,0), new FloatVec2(0,0));
		 this.Image = new DrawableImage("propriete.png");
     updateWindowTaille(600, 300);
	 }
		
	 public void afficher() {
			Image.afficher(InterfaceGraphique.glThread);
      InterfaceGraphique.glThread.ajouterBouton(this);
		}

   @Override
   public void executer() {
     /* Desactiver le clic.*/
     InterfaceGraphique.glThread.retirerBouton(this);
     String[] texte = {"Fonctionnalité non disponible !", "En cours de développement.", ":("};
     Popup pop = new Popup("Gestion des propriétés", texte);
     pop.afficher();
     /* Reactiver le clic.*/
     InterfaceGraphique.glThread.ajouterBouton(this);
   }
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			Image.redimensionner(windowWidth - 180, windowHeight/2 - 30, 169, 122);
      
      // mettre a jour la hitbox du bouton
      point1 = new FloatVec2(windowWidth - 180, windowHeight/2 - 30);
      point2 = new FloatVec2(windowWidth - 180 + 169, windowHeight/2 - 30 + 122);
		}
}
