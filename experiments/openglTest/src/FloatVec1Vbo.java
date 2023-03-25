/** Vertex Buffer Object opengl qui contient un tableau de vec2.
 * @author : pisento
 **/
import static org.lwjgl.opengl.GL20.*;
import java.util.List;
import java.util.LinkedList;
import org.lwjgl.*;
import java.nio.*;


public class FloatVec1Vbo implements Vbo<Float> {

  /** Liste des données à mettre dans le Vbo.*/
  private List<Float> data;

  /** Id opengl du vbo.*/
  final private int id;

  /** Générer un vbo de vec2 vide.*/
  public FloatVec1Vbo() {
    data = new LinkedList<Float>();
    id = glGenBuffers();
  }

  @Override
  public void push(Float newValue) {
    data.add(newValue);
  }

  @Override
  public void uploadToGpu() {
    // On convertit les données en un FloatBuffer
    FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.size());

    for (Float value: data) {
      dataBuffer.put(value);
    }

    dataBuffer.flip();

    glBindBuffer(GL_ARRAY_BUFFER, id); // bind the vbo
    glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done
  }

  @Override
  public void setLocation(int location) {
    glBindBuffer(GL_ARRAY_BUFFER, id); // bind the vbo
    glVertexAttribPointer(location, 1,
        GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done
  
    System.out.println("Set vbo to location " + location);
  }

}
