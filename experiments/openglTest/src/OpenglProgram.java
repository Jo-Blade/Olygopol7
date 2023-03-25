/** Programme opengl qui contient plusieurs
 * shaders prets à l’emploi.
 * @author : pisento
 **/

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;

public class OpenglProgram {

  /** Id opengl du programme.*/
  private final int id = GL20.glCreateProgram();

  /** Créer un programme opengl avec son vertex et son fragment shader.
   * @param vertShader vertex shader du programme
   * @param fragShader fragment shader du programme
   */
  public OpenglProgram(VertexShader vertShader, FragmentShader fragShader) {

    GL20.glAttachShader(id, vertShader.getId());
    GL20.glAttachShader(id, fragShader.getId());
    GL20.glLinkProgram(id);
    GL20.glValidateProgram(id);
  }


  /** Utiliser maintenant ce programme opengl.*/
  public void utiliser() {
    glUseProgram(id);
  }


}
