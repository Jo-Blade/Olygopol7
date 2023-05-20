package moteurGraphique.glThread;
/** Thread principal opengl pour le rafraichissement de l’écran.
 * @author : pisento
**/
import java.util.Collections;
import java.util.List;

import moteurGraphique.drawable.ModelInstantiator;
import moteurGraphique.vecteur.Vec2Int;
import moteurGraphique.window.Button;
import moteurGraphique.window.Window;
import moteurGraphique.window.WindowListener;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.glfw.GLFW.*;
import java.time.Duration;
import java.time.Instant;

public class OpenglThread extends Thread {

  /** La fenêtre à mettre à jour.*/
  private Window fenetre;

  /** Liste de boutons sur la fenetre.*/
  public List<Button> listeBoutons = new ArrayList<>();

  /** Liste des écouteurs de la taille de la fenêtre.*/
  public List<WindowListener> listeEcouteurs = new ArrayList<>();

  /** Nombre d’images rendues depuis le début du programme.*/
  public int compteurFrames = 0;

  /** L’ensemble des éléments à afficher actuellement.*/
  private List<ModelInstantiator<?>> afficheurs =
    Collections.synchronizedList(new ArrayList<>());

  /** Le gc pour les resources opengl.*/
  public static OpenglGC gc = new OpenglGC();

  /** Ajouter un afficheur à la liste des éléments à afficher.
   * @param afficheur un instancieur de modèle à afficher
   */
  public void ajouterAffichage(ModelInstantiator<?> afficheur) {
    synchronized(afficheurs) {
      afficheurs.add(afficheur);
    }
  }

  /** Retirer un afficheur à la liste des éléments à afficher.
   * @param afficheur un instancieur de modèle à afficher
   */
  public void retirerAffichage(ModelInstantiator<?> afficheur) {
    afficheurs.remove(afficheur);
  }

  /** Ajouter un bouton cliquable à la fenêtre.
   * @param bouton le bouton à ajouter
   */
  public void ajouterBouton(Button bouton) {
    if (fenetre != null)
      fenetre.listeBoutons.add(bouton);

    listeBoutons.add(bouton);
  }

  /** Retirer un bouton cliquable à la fenêtre.
   * @param bouton le bouton à ajouter
   */
  public void retirerBouton(Button bouton) {
    if (fenetre != null)
      fenetre.listeBoutons.remove(bouton);

    listeBoutons.remove(bouton);
  }

  /** Ajouter un objet à la liste des objets qui
   * dépendent de la taille de la fenêtre.
   * @param objet l'objet à ajouter
   */
  public void ajouterEcouteur(WindowListener objet) {
    if (fenetre != null) {
      fenetre.listeEcouteurs.add(objet);
      Vec2Int dimensions = fenetre.getDimensions();
      objet.updateWindowTaille(dimensions.x, dimensions.y);
    }
    else {
      objet.updateWindowTaille(600, 300);
    }

    listeEcouteurs.add(objet);
  }

  /** Boucle infinie qui met à jour la fenêtre donnée.*/
  public void run() {
    fenetre = new Window(false, false, new Vec2Int(600, 300), "testWindow");
    fenetre.show();

    for (Button b : listeBoutons)
      fenetre.listeBoutons.add(b);

    for (WindowListener e : listeEcouteurs)
      fenetre.listeEcouteurs.add(e);

    Instant instant = Instant.now();
    while ( !glfwWindowShouldClose(fenetre.getHandle()) ) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      Vec2Int fenetreTaille = fenetre.getDimensions();
      synchronized(afficheurs) {

        for(ModelInstantiator<?> afficheur : afficheurs)
          afficheur.dessinerObjets(fenetreTaille.x, fenetreTaille.y);
      }
      glfwSwapBuffers(fenetre.getHandle()); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();
      compteurFrames++;

      Instant newInstant = Instant.now();
      long timeElapsed = Duration.between(instant, newInstant).toMillis();
      if (timeElapsed > 50) {
        gc.gc();
        instant = newInstant;
      }

      try {
        Thread.sleep(1);
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
  }
}
