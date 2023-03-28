/** Programme opengl qui contient plusieurs
 * shaders prets à l’emploi.
 * @author : pisento
 **/
import static org.lwjgl.opengl.GL20.*;

public class OpenglProgram {

  /** Id opengl du programme.*/
  private final int id = glCreateProgram();

  /** Créer un programme opengl avec son vertex et son fragment shader.
   * @param vertShader vertex shader du programme
   * @param fragShader fragment shader du programme
   */
  public OpenglProgram(VertexShader vertShader, FragmentShader fragShader) {

    glAttachShader(id, vertShader.getId());
    glAttachShader(id, fragShader.getId());
    glLinkProgram(id);
    glValidateProgram(id);
  }


  /** Utiliser maintenant ce programme opengl.*/
  public void utiliser() {
    glUseProgram(id);
  }


  /** Modifier une variable uniforme des shaders.*/
  public void setUniformFloat(String uniformName, float value) {

    glUseProgram(id); // il faut utiliser le programme avant de pouvoir
                      // changer une variable uniforme
    int uniformId = glGetUniformLocation(id, uniformName);
    glUniform1f(uniformId, value);
  }

}
