package Interfacegraphique;

import MoteurGraphique.OpenglThread;
import MoteurGraphique.WindowListener;
import MoteurGraphique.DrawableImage;
import MoteurGraphique.FloatVec2;
import MoteurGraphique.Button;

public class BoutonPropriete extends Button implements WindowListener {

	final private DrawableImage Image;
	
	 public BoutonPropriete() {
     super(new FloatVec2(0,0), new FloatVec2(0,0));
		 this.Image = new DrawableImage("propriete.png");
     updateWindowTaille(600, 300);
	 }
		
	 
	 public void afficher(OpenglThread aff) {
			Image.afficher(aff);
		}

   @Override
   public void executer() {
     System.out.println("Ouvrir le menu des propriétés");
   }
	 
	 @Override
		public void updateWindowTaille(int windowWidth, int windowHeight ) {
			Image.redimensionner(windowWidth - 180, windowHeight/2 - 30, 169, 122);
      
      // mettre a jour la hitbox du bouton
      point1 = new FloatVec2(windowWidth - 180, windowHeight/2 - 30);
      point2 = new FloatVec2(windowWidth - 180 + 169, windowHeight/2 - 30 + 122);
		}
}
