/** Texture (matrice de pixels) que l’on peut appliquer
 * à un modèle 2d.
 * @author : pisento
**/
import java.nio.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.stb.STBImage;

abstract public class Texture {

  /** Id opengl de la texture.*/
  final protected int id = glGenTextures();

  /** Enregistrer l’image de la texture.
   * @param buffer le buffer des pixels de l’image
   * @param width la largeur de l’image
   * @param height la hauteur de l’image
   */
  public void storeImage(ByteBuffer buffer, int width, int height) {

    glBindTexture(GL_TEXTURE_2D, id);
    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
        GL_UNSIGNED_BYTE, buffer);

    // filtrage linéaire pour réduire les artifacts
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
        GL_LINEAR_MIPMAP_LINEAR);

    glGenerateMipmap(GL_TEXTURE_2D);
    STBImage.stbi_image_free(buffer);
  }

  /** Charger la texture pour l’utiliser dans un shader.*/
  public void loadTexture() {

    glBindTexture(GL_TEXTURE_2D, id);
  }

}
