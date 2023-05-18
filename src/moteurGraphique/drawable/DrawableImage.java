package moteurGraphique.drawable;

import moteurGraphique.drawable.instance.ImageInstance;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.glType.FragmentShader;
import moteurGraphique.glType.OpenglProgram;
import moteurGraphique.glType.TextureImage;
import moteurGraphique.glType.VertexShader;
import moteurGraphique.vecteur.FloatVec2;

/** Une image quelconque qui peut être affichée à l'écran.
 * @author : pisento
**/
public class DrawableImage {
public final static String vertCode =
    "#version 330 core\n" +

    "in vec2 position;\n" +
    "in vec2 uvs;\n" +
    "in vec2 point1;\n" +
    "in vec2 dimensions;\n" +

    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "out vec2 fragUvs;\n" +

    "void main()\n" +
    "{\n" +

    "mat2 resize = 2*mat2(dimensions.x / float(windowWidth), 0.0, 0.0, dimensions.y / float(windowHeight));\n" +

    "gl_Position = vec4(vec2(-1., 1.) + position*resize + vec2(2. / float(windowWidth), -2. / float(windowHeight))*point1, 0.0, 1.0);\n" +
    "fragUvs = uvs;\n" +
    "}";


  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fragUvs;\n" +
    "uniform sampler2D textureSampler;\n" +

    "void main() {\n" +
    "fragColor = texture(textureSampler, fragUvs);\n" +
    "}\n";
public final static float[] vertices = new float[]
  {
    +0.0f, +0.0f,    // Top-left coordinate
      +0.0f, -1.0f,    // Bottom-left coordinate
      +1.0f, -1.0f,    // Bottom-right coordinate

      +1.0f, +0.0f,    // Top-right
      +0.0f, +0.0f,    // Top-left
      +1.0f, -1.0f     // Bottom-right

  };
public final static float[] uvs = new float[]
  {
    +0.0f, +0.0f,    // Top-left coordinate
      +0.0f, +1.0f,    // Bottom-left coordinate
      +1.0f, +1.0f,    // Bottom-right coordinate

      +1.0f, +0.0f,    // Top-right
      +0.0f, +0.0f,    // Top-left
      +1.0f, +1.0f     // Bottom-right
  };

  private ModelInstantiator<ImageInstance> drawer;
public static OpenglProgram glProg = new OpenglProgram(
      new VertexShader(vertCode), new FragmentShader(fragCode)
      );

  /** Créer un DrawableImage à partir du nom de l'image.
   * @param resourceName nom de l'image (dans le dossier res/)
   */
  public DrawableImage(String resourceName) {

    Model2d modele = new Model2d(vertices, uvs,
        new TextureImage(resourceName));
    drawer = new ModelInstantiator<>(glProg, modele);
  }

  /** Afficher l'image à l'écran.
   * @param openglThread le thread opengl
   */
  public void afficher(OpenglThread openglThread) {

    openglThread.ajouterAffichage(drawer);
  }

  /** Cacher l'image de l'écran.
   * @param openglThread le thread opengl
   */
  public void cacher(OpenglThread openglThread) {
    openglThread.retirerAffichage(drawer);
  }

  /** Redimensionner et repositionner l'image à l'écran.
   * @param positionX la distance au bord gauche de l'écran (en pixels)
   * @param positionY la distance au bord haut de l'écran (en pixels)
   * @param largeur la largeur du rectangle (en pixels)
   * @param hauteur la hauteur du rectangle (en pixels)
   */
  public void redimensionner(int positionX, int positionY, int largeur, int hauteur) {
    drawer.clear();
    drawer.addObjet(new ImageInstance(
          new FloatVec2(positionX, positionY),
          new FloatVec2(largeur, hauteur)
          ));
    // ne pas oublier de valider la modifs
    // des objets affichés pour actualiser
    drawer.valider();
  }

}
