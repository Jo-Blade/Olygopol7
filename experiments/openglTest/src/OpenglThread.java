/** Thread principal opengl pour le rafraichissement de l’écran.
 * @author : pisento
**/
import java.util.Collections;
import java.util.List;
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

  /** Nombre d’images rendues depuis le début du programme.*/
  public int compteurFrames = 0;

  /** L’ensemble des éléments à afficher actuellement.*/
  private List<ModelInstantiator<?>> afficheurs =
    Collections.synchronizedList(new ArrayList<>());

  /** Le gc pour les resources opengl.*/
  private OpenglGC gc;

  public OpenglThread(OpenglGC gc) {
    this.gc = gc;
  }

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

  /** Boucle infinie qui met à jour la fenêtre donnée.*/
  public void run() {
    fenetre = new Window(false, false, new Vec2Int(600, 300), "testWindow");
    fenetre.show();

    for (Button b : listeBoutons)
      fenetre.listeBoutons.add(b);

    Instant instant = Instant.now();
    while ( !glfwWindowShouldClose(fenetre.getHandle()) ) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      synchronized(afficheurs) {

        for(ModelInstantiator<?> afficheur : afficheurs)
          afficheur.dessinerObjets();
      }
      glfwSwapBuffers(fenetre.getHandle()); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();
      compteurFrames++;

      Instant newInstant = Instant.now();
      long timeElapsed = Duration.between(instant, newInstant).toMillis();
      if (timeElapsed > 20) {
        gc.gc();
        instant = newInstant;
      }
    }
  }
}
