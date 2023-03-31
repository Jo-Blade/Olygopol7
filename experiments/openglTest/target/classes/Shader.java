/** Shader Opengl/GLSL.
 * @author : pisento
**/

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;


abstract public class Shader {

  /** Erreur de compilation du Shader.*/
  public class CompilationFailedException extends RuntimeException {
    /** Initaliser une CompilationFailedException
     * @param message le message explicatif
     */
    public CompilationFailedException(String message) {
      super(message);
    }
  }


  /** ID opengl du shader.*/
  private final int id;


  /** Créer un nouveau shader.*/
  abstract protected int creerShader(String codeSource);


  /** Créer un shader opengl.
   * @param codeSource code source GLSL sous forme de string
   * @param shaderType type de shader opengl (vertex, frag…) */
  public Shader(String codeSource) {

    id = this.creerShader(codeSource);
    glShaderSource(id, codeSource);
    this.compiler();
  }


  /** Compiler le shader.*/
  private void compiler() {
    glCompileShader(id);

    if(GL20.glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
      throw new Shader.CompilationFailedException(glGetShaderInfoLog(id, 512));
  }


  /** Obtenir l’id opengl du shader.*/
  public int getId() {
    return id;
  }

}
