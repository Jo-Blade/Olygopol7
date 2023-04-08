/** Fenêtre Opengl où l’on peut dessiner.
 * @author : pisento
**/

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import java.nio.IntBuffer;
import java.nio.DoubleBuffer;
import java.util.List;
import java.util.ArrayList;

public class Window {

  /** Opengl window handle.*/
  private long windowHandle;

  /** Liste de boutons sur la fenetre.*/
  public List<Button> listeBoutons = new ArrayList<>();

  /** Créer une fenêtre opengl.
   * @param AA activer ou non l’antialising
   * @param vSync activer ou non la v-sync
   * @param dimensions largeur et hauteur de la nouvelle fenêtre
   * @param titre titre de la nouvelle fenêtre
   */
  public Window(boolean AA, boolean vSync, Vec2Int dimensions, String titre) {



    // --- DEVRAIT PAS ETRE FAIT ICI ---

    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW");





    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    // Activer l’antialising si demandé
    if (AA)
      glfwWindowHint(GLFW_SAMPLES, 3); // NECESSAIRE POUR ANTIALIASING

    // Create the window
    windowHandle = glfwCreateWindow(dimensions.x, dimensions.y, titre, NULL, NULL);
    if ( windowHandle == NULL )
      throw new RuntimeException("Failed to create the GLFW window");




    // --- DEVRAIT SUPPRIMER LA LAMBDA EXPRESSION ET FAIRE UN VRAI ÉCOUTEUR D’ÉVÉNEMENTS ---
    // changer l'echelle quand on redimensionne la fenetre
    glfwSetWindowRefreshCallback(windowHandle, (window) -> {

      Vec2Int newDimensions = this.getDimensions();
      glViewport(0, 0, newDimensions.x, newDimensions.y);
    });


    // Setup a mouse callback.
    glfwSetMouseButtonCallback(windowHandle, (windowHandle, button, action, mods) -> {
      if ( button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE ) {
        try ( MemoryStack stack = stackPush() ) {
          DoubleBuffer px_pos = stack.mallocDouble(1); // double*
          DoubleBuffer py_pos = stack.mallocDouble(1); // double*

          glfwGetCursorPos(windowHandle, px_pos, py_pos);

          Vec2Int currentDim = getDimensions();
          FloatVec2 mouseCoords = new FloatVec2((float) px_pos.get(0) / (float) currentDim.x,
              (float) py_pos.get(0) / (float) currentDim.y);

          // System.out.println("x: " + mouseCoords.x + " - y: " + mouseCoords.y);
          for (Button b : listeBoutons) {
            if (b.isMouseSelected(mouseCoords))
              b.cliquer();
          }

        }
      }
    });


    // --- VRAIMENT UTILE DE CENTRER LA FENETRE ? ---
    // Get the resolution of the primary monitor
    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    // Center the window
    glfwSetWindowPos(
        windowHandle,
        (vidmode.width() - dimensions.x) / 2,
        (vidmode.height() - dimensions.y) / 2
        );



    // --- PAS SUR DE DEVOIR LE FAIRE ICI ---
    // Make the OpenGL context current
    glfwMakeContextCurrent(windowHandle);

    // Enable v-sync
    if (vSync)
      glfwSwapInterval(1);
    else
      glfwSwapInterval(0);



    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    // Set the clear color to white
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    // Enable antialiasing
    if (AA)
      glEnable(GL_MULTISAMPLE);

    // Enable transparency
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


  }


  /** Afficher la fenêtre à l’écran.*/
  public void show() {
    glfwShowWindow(windowHandle);
  }


  /** Obtenir les dimensions de la fenêtre.
   * @return couple d’entiers (width, height)
   */
  public Vec2Int getDimensions() {

    try ( MemoryStack stack = stackPush() ) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size
      glfwGetWindowSize(windowHandle, pWidth, pHeight);

      return new Vec2Int(pWidth.get(0), pHeight.get(0));
    }
  }


  /** Obtenir la poignée opengl sur la fenêtre.
   * @return la poignée
   */
  public long getHandle() {
    return windowHandle;
  }


}
