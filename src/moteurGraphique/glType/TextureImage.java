package moteurGraphique.glType;
/** Texture créé à partir d’une image.
 * @author : pisento
 **/
import org.lwjgl.system.*;
import java.nio.*;
import java.io.File;
import org.lwjgl.stb.STBImage;

public class TextureImage extends Texture {

  /** Le nom de la resource.*/
  final private String resourceName;

  @Override
  final protected void creerTexture() {
    ByteBuffer buffer;

    try (MemoryStack stack = MemoryStack.stackPush()){
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer channels = stack.mallocInt(1);

      File file = new File("res/"+resourceName);
      String filePath = file.getAbsolutePath();
      buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
      if(buffer ==null) {
        throw new Exception("Can't load file " + resourceName + " "
            + STBImage.stbi_failure_reason());
      }

      uploadImageBuffer(buffer, w.get(), h.get());

    } catch(Exception e) {
      e.printStackTrace();
    }

  }

  /** Créer une nouvelle texture avec
   * le nom d’une image dans le dossier res. */
  public TextureImage(String resourceName) {
    super();
    this.resourceName = resourceName;
  }

}
