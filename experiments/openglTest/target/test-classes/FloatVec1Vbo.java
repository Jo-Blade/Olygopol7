/** Vertex Buffer Object opengl qui contient un tableau de vec2.
 * @author : pisento
 **/
import static org.lwjgl.opengl.GL20.*;
import java.util.List;
import java.util.LinkedList;
import org.lwjgl.*;
import java.nio.*;

import org.lwjgl.system.MemoryUtil;


public class FloatVec1Vbo implements Vbo<Float> {

  /** Liste des données à mettre dans le Vbo.*/
  private List<Float> data;

  /** Id opengl du vbo.*/
  private int id;

  /** Le gc opengl.*/
  final private OpenglGC gc;

  /** Générer un vbo de vec2 vide.*/
  public FloatVec1Vbo() {
    data = new LinkedList<Float>();

    this.gc = OpenglThread.gc;
    this.id = -1; // signifie que le vbo n’est pas encore créé
  }

  private int getId() {

    if (id == -1) {
      id = glGenBuffers();
      gc.new GlVbo(id, this);
    }

    return id;
  }

  @Override
  public void clear() {
    data = new LinkedList<Float>();
  }

  @Override
  public void push(Float newValue) {
    data.add(newValue);
  }

  @Override
  public void uploadToGpu() {
    // On convertit les données en un FloatBuffer
    //FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.size());

    FloatBuffer dataBuffer = MemoryUtil.memAllocFloat(data.size());

    for (Float value: data) {
      dataBuffer.put(value);
    }

    dataBuffer.flip();

    glBindBuffer(GL_ARRAY_BUFFER, getId()); // bind the vbo
    glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_DYNAMIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done

    MemoryUtil.memFree(dataBuffer);
  }

  @Override
  public void setLocation(int location) {
    glBindBuffer(GL_ARRAY_BUFFER, getId()); // bind the vbo
    glVertexAttribPointer(location, 1,
        GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done
  }

}
