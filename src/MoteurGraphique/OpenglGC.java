/** Création d’un garbage collector pour les objets opengl.
 * @author : pisento
**/
import static org.lwjgl.opengl.GL40.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.ref.WeakReference;
import java.util.Collections;

/* Le but est d’utiliser des WeakReference pour détecter quand
 * un objet est libéré par le GC de java, et alors de le libérer également
 * dans opengl. */

public class OpenglGC {

  /** Liste des Vbos actuels. */
  private List<GlVbo> listeVbos = Collections.synchronizedList(new ArrayList<>());

  /** Liste des Vaos actuels. */
  private List<GlVao> listeVaos = Collections.synchronizedList(new ArrayList<>());

  /** Objet qui permet de libérer automatiquement le buffer occupé par
   * un vbo. Ne pas oublier de maintenir l’id à jour !!!!!!! (a changer)*/
  public class GlVbo {

    /** Id opengl du vbo.*/
    public int id;

    /** WeakReference vers l’objet vbo associé.*/
    private WeakReference<Vbo<?>> vbo;

    /** Libérer intelligemment la mémoire.
     * @return true si la mémoire a été libérée
     */
    public boolean gc() {
      if (vbo.get() == null) {
        glDeleteBuffers(id);
        // System.out.println("test");
        return true;
      }
      return false;
    }

    public GlVbo(int id, Vbo<?> vbo) {
      this.id = id;
      this.vbo = new WeakReference<Vbo<?>>(vbo);
      listeVbos.add(this);
    }

  }


  /** Objet qui permet de libérer automatiquement le buffer occupé par
   * un vao. Ne pas oublier de maintenir l’id à jour !!!!!!! (a changer)*/
  public class GlVao {

    /** Id opengl du vbo.*/
    public int id;

    /** WeakReference vers l’objet vbo associé.*/
    private WeakReference<ModelInstantiator<?>> vao;

    /** Libérer intelligemment la mémoire.
     * @return true si la mémoire a été libérée
     */
    public boolean gc() {
      if (vao.get() == null) {
        glDeleteVertexArrays(id);
        // System.out.println("test");
        return true;
      }
      return false;
    }

    public GlVao(int id, ModelInstantiator<?> vao) {
      this.id = id;
      this.vao = new WeakReference<>(vao);
      listeVaos.add(this);
    }

  }


  /** Libérer automatiquement les buffers opengl.*/
  public void gc() {
    List<GlVbo> VboAGarder = Collections.synchronizedList(new ArrayList<>());
    List<GlVao> VaoAGarder = Collections.synchronizedList(new ArrayList<>());

    synchronized(listeVbos) {
      for (OpenglGC.GlVbo vbo : listeVbos) {
        if (!vbo.gc())
          VboAGarder.add(vbo);
      }

      listeVbos = VboAGarder;
    }

    synchronized(listeVaos) {
      for (OpenglGC.GlVao vao : listeVaos) {
        if (!vao.gc())
          VaoAGarder.add(vao);
      }

      listeVaos = VaoAGarder;
    }
  }

}
