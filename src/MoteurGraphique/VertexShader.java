package MoteurGraphique;
/** Vertex Shader GLSL.
 * @author : pisento
**/
import static org.lwjgl.opengl.GL20.*;

public class VertexShader extends Shader {

  @Override
  final protected int creerShader(String codeSource) {
    return glCreateShader(GL_VERTEX_SHADER);
  }

  /** Créer un nouveau fragment shader
   * @param codeSource code source du nouveau shader
   */
  public VertexShader(String codeSource) {
    super(codeSource);
  }

}
