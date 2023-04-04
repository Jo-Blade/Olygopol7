/** Programme opengl qui contient plusieurs
 * shaders prets à l’emploi.
 * @author : pisento
 **/
import static org.lwjgl.opengl.GL20.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class OpenglProgram {

  /** Id opengl du programme.*/
  private int id;

  /** Le vertex shader.*/
  private final VertexShader vertShader;

  /** Le fragment shader.*/
  private final FragmentShader fragShader;

  /** Map des variables Uniforms à mettre à jour.*/
  private final Map<String, Float> mapUniforms = Collections.synchronizedMap(new HashMap<>());

  /** Créer un programme opengl avec son vertex et son fragment shader.
   * @param vertShader vertex shader du programme
   * @param fragShader fragment shader du programme
   */
  public OpenglProgram(VertexShader vertShader, FragmentShader fragShader) {
    
    this.vertShader = vertShader;
    this.fragShader = fragShader;
    this.id = -1; // pour dire que le programme n’est pas encore créé
  }


  /** Utiliser maintenant ce programme opengl.*/
  public void utiliser() {

    if (id == -1) {
      id = glCreateProgram();
      glAttachShader(id, vertShader.getId());
      glAttachShader(id, fragShader.getId());
      glLinkProgram(id);
      glValidateProgram(id);
    }

    glUseProgram(id);

    // load les variables uniforms en attente
    synchronized(mapUniforms) {
      for (Map.Entry<String, Float> entry : mapUniforms.entrySet()) {
        int uniformId = glGetUniformLocation(id, entry.getKey());
        glUniform1f(uniformId, entry.getValue());
      }

      mapUniforms.clear();
    }
  }


  /** Modifier une variable uniforme des shaders.*/
  public void setUniformFloat(String uniformName, float value) {

    mapUniforms.put(uniformName, value);
  }

}
