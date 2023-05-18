package moteurGraphique.drawable;

import moteurGraphique.drawable.instance.IsoImgInstance;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.glType.FragmentShader;
import moteurGraphique.glType.OpenglProgram;
import moteurGraphique.glType.TextureImage;
import moteurGraphique.glType.VertexShader;
import moteurGraphique.window.WindowListener;

/** Un texte affichable à l’écran.
 * @author : pisento
 **/

public class DrawableIsoGrid {

  /** Code du vertex shader.*/
  public final static String vertCode =
    "#version 330 core\n" +

    "in vec2 vertex;\n" +
    "in vec2 uvs;\n" +
    "in vec3 positionCentre;\n" +
    "in float indiceTex;\n" +

    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "uniform float isoGridWidth;\n" +
    "uniform float isoGridHeight;\n" +
    "uniform float isoGridPosX;\n" +
    "uniform float isoGridPosY;\n" +

    "uniform float cameraX;\n" +
    "uniform float cameraY;\n" +
    "uniform float cameraZ;\n" +

    "uniform float widthRatioTex;\n" +
    "uniform float zoom;\n" +

    "out vec2 fUvs;\n" +
    "out float fIndiceTex;\n" +

    "const float a = 9.8;\n" +
    "const float b = 5.6;\n" +

    "void main()\n" +
    "{\n" +

    "mat2x3 chbase = mat2x3(a, -a, 0.,   b, b, b);\n" +
    "vec2 pos1 = vertex*mat2(10.) + (positionCentre - vec3(cameraX, cameraY, cameraZ)) * chbase;\n" +
    "pos1 *= zoom;\n" +
    "float afficher = step(-isoGridWidth/2. + 10.*zoom, ((positionCentre - vec3(cameraX, cameraY, cameraZ)) * chbase * zoom).x);\n" +
    "afficher *= step(-isoGridHeight/2. + 20.*zoom, ((positionCentre - vec3(cameraX, cameraY, cameraZ)) * chbase * zoom).y);\n" +
    "afficher *= step(((positionCentre - vec3(cameraX, cameraY, cameraZ)) * chbase * zoom).x, isoGridWidth/2. - 10.*zoom);\n" +
    "afficher *= step(((positionCentre - vec3(cameraX, cameraY, cameraZ)) * chbase * zoom).y, isoGridHeight/2. - 20.*zoom);\n" +
    "pos1 *= afficher;\n" +

    "mat2 resize = mat2(2./windowWidth, 0, 0, 2./windowHeight);\n" +
    "gl_Position = vec4(vec2(-1., 1.) + resize * (pos1 + vec2(isoGridPosX, - isoGridPosY) + vec2(isoGridWidth / 2., - isoGridHeight / 2.)), 0.0, 1.0);\n" +

    "fUvs = uvs;\n" +
    "fIndiceTex = indiceTex;\n" +
    "}";

  /** Code du fragment shader.*/
  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fUvs;\n" +
    "in float fIndiceTex;\n" +

    "uniform sampler2D textureSampler;\n" +
    "uniform float widthRatioTex;\n" +

    "void main()\n" +
    "{\n" +
    "fragColor = texture(textureSampler, (vec2(fIndiceTex, 0) + fUvs)*mat2(widthRatioTex, 0., 0., 1.));\n" +
    "}";
  public final static float[] vertices = new float[]
  {
    -1.0f, +2.0f,    // Top-left coordinate
      -1.0f,  -2.0f,    // Bottom-left coordinate
      +1.0f, -2.0f,    // Bottom-right coordinate

      +1.0f, +2.0f,    // Top-right
      -1.0f, +2.0f,    // Top-left
      +1.0f, -2.0f     // Bottom-right
  };
  public final static float[] uvs = new float[]
  {
    +0.0f, +0.0f,
      +0.0f, +1.0f,
      +1.0f, +1.0f,
      +1.0f, +0.0f,
      +0.0f, +0.0f,
      +1.0f, +1.0f,
  };



  /** L’instantiator du texte.*/
  private ModelInstantiator<IsoImgInstance> drawer;

  /** Le modèle de la grille qui contient une texture
   * avec tous les éléments affichables sur une même ligne.*/
  private Model2d modele;

  /** Le vertex Shader après compilation.*/
  private static VertexShader vertShader = new VertexShader(vertCode);

  /** Le fragment Shader après compilation.*/
  public static FragmentShader fragShader = new FragmentShader(fragCode);

  /** Le programme opengl pour afficher la grille.*/
  public static OpenglProgram oglProg = new OpenglProgram(vertShader, fragShader);

  /** Construire une boite de texte.
   * @param ressourceName le nom de l'image qui contient tous les éléments affichables
   * ils doivent tous être de même hauteur, de même largeur et collés
   * les uns à côté des autres sur l'image
   * @param nombreElements le nombre d'éléments sur la texture
   */
  public DrawableIsoGrid(String ressourceName, int nombreElements) {
    modele = new Model2d(DrawableIsoGrid.vertices, DrawableIsoGrid.uvs, new TextureImage(ressourceName));

    oglProg.setUniformFloat("isoGridWidth", 0);
    oglProg.setUniformFloat("isoGridHeight", 0);
    oglProg.setUniformFloat("isoGridPosX", 0);
    oglProg.setUniformFloat("isoGridPosY", 0);
    oglProg.setUniformFloat("cameraX", 0);
    oglProg.setUniformFloat("cameraY", 0);
    oglProg.setUniformFloat("cameraZ", 0);
    oglProg.setUniformFloat("widthRatioTex", 1f / (float) nombreElements);
    oglProg.setUniformFloat("zoom", 0);

    drawer = new ModelInstantiator<>(oglProg, modele);
  }

  /** Ajouter un objet à afficher.
   * @param objet le nouvel objet à afficher
   * @return True si un objet a été ajouté
   */
  public boolean ajouter(IsoImgInstance objet) {
    boolean test = drawer.addObjet(objet);
    drawer.valider();
    return test;
  }

  /** Retirer un objet à afficher.
   * @param objet le nouvel objet à afficher
   * @return True si un objet a été retiré
   */
  public boolean retirer(IsoImgInstance objet) {
    boolean test = drawer.delObjet(objet);
    drawer.valider();
    return test;
  }

  /** Donner l’instruction d’affichage.
   * @param openglThread le thread chargé de l’affichage
   */
  public void afficher(OpenglThread openglThread) {
    openglThread.ajouterAffichage(drawer);
  }

  /** Donner l’instruction d’affichage.
   * @param openglThread le thread chargé de l’affichage
   */
  public void cacher(OpenglThread openglThread) {
    openglThread.retirerAffichage(drawer);
  }

  /** Redimensionner la grille à l'écran.
   * @param isoGridPosX la distance au bord gauche de l'écran
   * @param isoGridPosY la distance au bord haut de l'écran
   * @param isoGridWidth la largeur de la grille à l'écran
   * @param isoGridHeight la hauteur de la grille à l'écran
   */
  public void redimensionner(int isoGridPosX, int isoGridPosY,
      int isoGridWidth, int isoGridHeight) {
    oglProg.setUniformFloat("isoGridPosX", isoGridPosX);
    oglProg.setUniformFloat("isoGridPosY", isoGridPosY);
    oglProg.setUniformFloat("isoGridWidth", isoGridWidth);
    oglProg.setUniformFloat("isoGridHeight", isoGridHeight);
  }

  /** Repositionner la caméra de l'espace isométrique.
   * @param posX coordonnée X centrée
   * @param posY coordonnée Y centrée
   * @param posZ coordonnée Z centrée
   * @param zoom le zoom utilisé
   */
  public void setCamera(double posX, double posY, double posZ, double zoom) {
    oglProg.setUniformFloat("cameraX", (float) posX);
    oglProg.setUniformFloat("cameraY", (float) posY);
    oglProg.setUniformFloat("cameraZ", (float) posZ);
    oglProg.setUniformFloat("zoom", (float) zoom);
  }
}
