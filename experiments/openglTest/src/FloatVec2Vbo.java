/** Vertex Buffer Object opengl qui contient un tableau de vec2.
 * @author : pisento
 **/
import static org.lwjgl.opengl.GL20.*;
import java.util.List;
import java.util.LinkedList;
import org.lwjgl.*;
import java.nio.*;


public class FloatVec2Vbo implements Vbo<FloatVec2> {

  /** Liste des données à mettre dans le Vbo.*/
  private List<FloatVec2> data;

  /** Id opengl du vbo.*/
  final private int id;

  /** Générer un vbo de vec2 vide.*/
  public FloatVec2Vbo() {
    data = new LinkedList<FloatVec2>();
    id = glGenBuffers();
  }

  @Override
  public void push(FloatVec2 newValue) {
    data.add(newValue);
  }

  @Override
  public void uploadToGpu() {
    // On convertit les données en un FloatBuffer
    FloatBuffer dataBuffer = 
      BufferUtils.createFloatBuffer(FloatVec2.getDimension() * data.size());

    for (FloatVec2 value: data) {
      dataBuffer.put(value.x);
      dataBuffer.put(value.y);
    }

    dataBuffer.flip();

    glBindBuffer(GL_ARRAY_BUFFER, id); // bind the vbo
    glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done
  }

  @Override
  public void setLocation(int location) {
    glBindBuffer(GL_ARRAY_BUFFER, id); // bind the vbo
    glVertexAttribPointer(location, FloatVec2.getDimension(),
        FloatVec2.getGlType(), false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done
  }

}
