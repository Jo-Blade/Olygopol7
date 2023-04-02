/** Vertex Buffer Object opengl qui contient un tableau de vec2.
 * @author : pisento
 **/
import static org.lwjgl.opengl.GL20.*;
import java.util.List;
import java.util.ArrayList;
import org.lwjgl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;


public class FloatVec2Vbo implements Vbo<FloatVec2> {

  /** Liste des données à mettre dans le Vbo.*/
  private List<FloatVec2> data;

  /** Id opengl du vbo.*/
  private final int id;

  /** Générer un vbo de vec2 vide.*/
  public FloatVec2Vbo(OpenglGC gc) {
    data = new ArrayList<FloatVec2>();
    id = glGenBuffers();

    gc.new GlVbo(id, this);
  }

  @Override
  public void clear() {
    data = new ArrayList<FloatVec2>();
  }

  @Override
  public void push(FloatVec2 newValue) {
    data.add(newValue);
  }

  @Override
  public void uploadToGpu() {
    // On convertit les données en un FloatBuffer
    // FloatBuffer dataBuffer = 
    //   BufferUtils.createFloatBuffer(2 * data.size());

    FloatBuffer dataBuffer = MemoryUtil.memAllocFloat(2 * data.size());

    for (FloatVec2 value: data) {
      dataBuffer.put(value.x);
      dataBuffer.put(value.y);
    }

    dataBuffer.flip();

    glBindBuffer(GL_ARRAY_BUFFER, id); // bind the vbo
    glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_DYNAMIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done

    MemoryUtil.memFree(dataBuffer);
  }

  @Override
  public void setLocation(int location) {
    glBindBuffer(GL_ARRAY_BUFFER, id); // bind the vbo
    glVertexAttribPointer(location, 2,
        GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0); // unbind the vbo when done
  }

}
