package moteurGraphique.glType;
/** Fragment Shader GLSL.
 * @author : pisento
**/
import static org.lwjgl.opengl.GL20.*;

public class FragmentShader extends Shader {

  @Override
  final protected int creerShader(String codeSource) {
    return glCreateShader(GL_FRAGMENT_SHADER);
  }

  /** Créer un nouveau fragment shader
   * @param codeSource code source du nouveau shader
   */
  public FragmentShader(String codeSource) {
    super(codeSource);
  }

}
