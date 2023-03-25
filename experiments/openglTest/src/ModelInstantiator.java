/** Instantier un ensemble d’objets ayant le même modèle
 * et les même shaders.
 * @author : pisento
**/
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
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

  /** Créer un instancieur à partir de son modèle et de ses shadeurs.
   * @param afficheur programme opengl avec les shaders
   * @param modele le modèle 2d avec la texture
   * et les vbos communs à tous les objets
   */
  public ModelInstantiator(OpenglProgram afficheur, Model2d modele) {
    this.afficheur = afficheur;
    this.modele = modele;
    this.vaoId = glGenVertexArrays();
    this.objets = new LinkedList<T>();
  }

  /** Ajouter un objet à la liste des objets à dessiner.
   * @param objet une instance d’un objet à dessiner
   */
  public void addObjet (T objet) {
    objets.add(objet);
    aJour = false;
  }

  /** Actualiser les données du gpu.*/
  private void Actualiser() {

    Map<Integer, Vbo<?>> objetsVbos = objets.get(0).initVbos();
    for (T objet: objets) {
      objet.appendVbos(objetsVbos);
    }

    // -----------------------------------------
    for (Map.Entry<Integer, Vbo<?>> entry : objetsVbos.entrySet()) {
      entry.getValue().uploadToGpu();
      entry.getValue().setLocation(entry.getKey());
    }

    modele.getVbo().setLocation(0);
    // -----------------------------------------


    aJour = true;
  }

  public void DessinerObjets() {
    // Bind the vertex array
    glBindVertexArray(vaoId);
    // Use the correct program
    afficheur.utiliser();

    if (!aJour)
      Actualiser();


    // A MODIFIER, POUR L’INSTANT JE HARD CODE POUR DES TESTS
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glEnableVertexAttribArray(2);

    glVertexAttribDivisor(0, 0); // réutilisation des vertices
    glVertexAttribDivisor(1, 1); // changer les centres
    glVertexAttribDivisor(2, 1); // changer les angles

    // Draw a triangle of 3 vertices
    //glDrawArrays(GL_TRIANGLES, 0, 6);
    // dessiner plusieurs instances de 2 triangles
    glDrawArraysInstanced(GL_TRIANGLES, 0, modele.getVertices().length, objets.size());

    // Disable our location
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glDisableVertexAttribArray(2);
    glBindVertexArray(0);
  }

}
