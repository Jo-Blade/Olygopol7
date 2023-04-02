/** Instantier un ensemble d’objets ayant le même modèle
 * et les même shaders.
 * @author : pisento
**/
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import static org.lwjgl.opengl.GL40.*;

public class ModelInstantiator<T extends ModelInstance> {

  /** Id opengl du vertex array object.*/
  private final int vaoId;

  /** Indique si les valeurs envoyées au gpu sont à jour.*/
  private boolean aJour;

  /** Le modèle à afficher.*/
  private final Model2d modele;

  /** Le programme opengl avec les shaders compilés.*/
  private final OpenglProgram afficheur;

  /** La liste des objets à afficher.*/
  private List<T> objets;

  /** La liste des locations des objets.*/
  private Set<Integer> objetsLocations;

  /** La liste des vbos et des locations associées.*/
  private Map<Integer, Vbo<?>> objetsVbos = null;

  /** Créer un instancieur à partir de son modèle et de ses shadeurs.
   * @param afficheur programme opengl avec les shaders
   * @param modele le modèle 2d avec la texture
   * et les vbos communs à tous les objets
   */
  public ModelInstantiator(OpenglGC gc, OpenglProgram afficheur, Model2d modele) {
    this.afficheur = afficheur;
    this.modele = modele;
    this.vaoId = glGenVertexArrays();
    this.objets = new ArrayList<T>();
    this.objetsLocations = new HashSet<Integer>();

    gc.new GlVao(vaoId, this);

    // Ajouter les vertices + uvs au vao
    glBindVertexArray(vaoId);
    modele.getVerticesVbo().setLocation(0);
    modele.getUvsVbo().setLocation(1);
    glBindVertexArray(0); // unbind the vao when done

  }

  /** Ajouter un objet à la liste des objets à dessiner.
   * @param objet une instance d’un objet à dessiner
   */
  public boolean addObjet (T objet) {
    if (objets.add(objet)) {
      aJour = false;
      return true;
    }
    return false;
  }

  /** Supprimer un objet de la liste des objets à dessiner.
   * @param objet l’objet à supprimer
   */
  public boolean delObjet (T objet) {
    if (objets.remove(objet)) {
      aJour = false;
      return true;
    }
    return false;
  }

  /** Supprimer tous les objets de la liste des objets à dessiner.*/
  public void clear() {
    objets = new ArrayList<T>();
    aJour = false;
  }


  /** Actualiser les données du gpu.*/
  private void actualiser() {

    if (objetsVbos == null)
      objetsVbos = objets.get(0).initVbos();

    for (Map.Entry<Integer, Vbo<?>> entry : objetsVbos.entrySet()) {
      entry.getValue().clear();
    }

    for (T objet: objets) {
      objet.appendVbos(objetsVbos);
    }

    objetsLocations.clear();
    this.objetsLocations = new HashSet<Integer>();

    // Bind the vertex array
    glBindVertexArray(vaoId);
    for (Map.Entry<Integer, Vbo<?>> entry : objetsVbos.entrySet()) {
      entry.getValue().uploadToGpu();
      entry.getValue().setLocation(entry.getKey());
      objetsLocations.add(entry.getKey()); // mettre à jour la liste des
                                           // locations à activer
    }
    glBindVertexArray(0); // unbind the vao when done

    aJour = true;
  }

  public void dessinerObjets() {

    // Si aucun objet à dessiner, on ne fait rien
    if (objets.isEmpty())
      return;

    if (!aJour)
      actualiser();

    // Use the correct program
    afficheur.utiliser();
    // Bind the vertex array
    glBindVertexArray(vaoId);
    // Utiliser le modèle pour dessiner
    modele.utiliser();

    // les zones 0 et 1 sont réservées respectivement
    // pour les vertices et les uvs.
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glVertexAttribDivisor(0, 0); // réutilisation des vertices
    glVertexAttribDivisor(1, 0); // réutilisation des vertices

    for (int location : objetsLocations) {
      glEnableVertexAttribArray(location);
      glVertexAttribDivisor(location, 1); // 1 car on change de ligne
                                          // à chaque nouvelle instantiation
    }

    // dessiner plusieurs instances en un seul appel à draw
    glDrawArraysInstanced(GL_TRIANGLES, 0, modele.getVertices().length, objets.size());

    // Disable our locations
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);

    for (int location : objetsLocations)
      glDisableVertexAttribArray(location);

    glBindVertexArray(0); // unbind the vao when done
  }

}
