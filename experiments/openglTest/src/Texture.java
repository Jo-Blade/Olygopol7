/** Texture (matrice de pixels) que l’on peut appliquer
 * à un modèle 2d.
 * @author : pisento
**/
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.File;
import org.lwjgl.stb.STBImage;


public class Texture {

  /** Id opengl de la texture.*/
  final private int id = glGenTextures();

  /** Largeur de la texture (en pixels).*/
  private int width;

  /** Hauteur de la texture (en pixels).*/
  private int height;

  public Texture(String resourceName){
    ByteBuffer buffer;


    try (MemoryStack stack = MemoryStack.stackPush()){
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer channels = stack.mallocInt(1);

      File file = new File("res/"+resourceName);
      String filePath = file.getAbsolutePath();
      buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
      if(buffer ==null) {
        throw new Exception("Can't load file "+resourceName+" "+STBImage.stbi_failure_reason());
      }
      width = w.get();
      height = h.get();

      glBindTexture(GL_TEXTURE_2D, id);
      glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

      // filtrage linéaire pour réduire les artifacts
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

      glGenerateMipmap(GL_TEXTURE_2D);
      STBImage.stbi_image_free(buffer);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  /** Charger la texture pour l’utiliser dans un shader.*/
  void loadTexture() {

      glBindTexture(GL_TEXTURE_2D, id);
  }

}
