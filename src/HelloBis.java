/**
 * @author : pisento
 **/
import interfacegraphique.*;
import logiqueMonopoly.*;
import moteurGraphique.drawable.DrawableIsoGrid;
import moteurGraphique.glThread.OpenglThread;

public class HelloBis {

  public class Compteur {
    public int compteur = 0;

    public void inc() {
      compteur++;
    }
  }

  public static void main(String[] args) {
//     System.out.println("Hello Bis !");
// 
//     InterfaceGraphique.glThread.start();
// 
//     DrawableIsoGrid testGrid = new DrawableIsoGrid("isoTex.png", 5);
//     testGrid.afficher(InterfaceGraphique.glThread);
// 
//     BoutonFinTour bFinTour = new BoutonFinTour();
//     bFinTour.afficher();
// 
//     Joueur j1 = new Joueur(10000, 0, "j1", 'x', "< >");
//     Joueur j2 = new Joueur(8000, 0, "joueur2", 'x', "< >");
//     Joueur j3 = new Joueur(1000, 0, "Téo", 'x', "< >");
//     Joueur j4 = new Joueur(230, 0, "Emile", 'x', "< >");
// 
//     JoueurScore score1 = new JoueurScore(j1, 0);
//     JoueurScore score2 = new JoueurScore(j2, 1);
//     JoueurScore score3 = new JoueurScore(j3, 2);
//     JoueurScore score4 = new JoueurScore(j4, 3);
// 
//     score1.afficher();
//     score2.afficher();
//     score3.afficher();
//     score4.afficher();
// 
//     BoutonPropriete bProp = new BoutonPropriete();
//     bProp.afficher();
//     openglThread.ajouterEcouteur(bProp);
//     openglThread.ajouterBouton(bProp);
// 
//     BoutonDe bDe = new BoutonDe();
//     bDe.afficher(openglThread);
// 
//     PopupAchat popup = new PopupAchat();
//     popup.afficher(openglThread);
// 
//     String[] texte = {"bonjour je suis Téo Pisenti", "et je suis développeur"};
//     Popup testPopup = new Popup("exemple", texte);
//     testPopup.afficher(openglThread);
// 
//     score1.changerJoueur(j2);
//     score2.changerJoueur(j1);
// 
//     while ( openglThread.isAlive() ) {
//       try {
//         Thread.sleep(1000);
//       }
//       catch(Exception e) {
//         System.out.println(e);
//       }
//       System.out.println("chiffre dé : " + bDe.tirer(j1));
//     }
//     // while ( openglThread.isAlive() ) {
//     //   // on met un délai pour ne pas bouffer tout le temps processeur
//     //   try {
//     //     Thread.sleep(1);
//     //   }
//     //   catch(Exception e) {
//     //     System.out.println(e);
//     //   }
//     // }
  }
}
