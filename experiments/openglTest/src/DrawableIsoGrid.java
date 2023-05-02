/** Un texte affichable à l’écran.
 * @author : pisento
**/

public class DrawableIsoGrid implements WindowListener {

  /** Code du vertex shader.*/
  private final static String vertCode =
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

    "const int a = 10;\n" +

    "void main()\n" +
    "{\n" +

    "mat2x3 chbase = mat2x3(a, -a, 0,   a, a, a);\n" +
    "vec2 pos1 = vertex*mat2(10.) + (positionCentre - vec3(cameraX, cameraY, cameraZ)) * chbase;\n" +
    "pos1 *= zoom;\n" +

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

  private final static float[] vertices = new float[]
  {
    -1.0f, +1.0f,    // Top-left coordinate
      -1.0f,  -1.0f,    // Bottom-left coordinate
      +1.0f, -1.0f,    // Bottom-right coordinate

      +1.0f, +1.0f,    // Top-right
      -1.0f, +1.0f,    // Top-left
      +1.0f, -1.0f     // Bottom-right
  };

  private final static float[] uvs = new float[]
  {
    +0.0f, +1.0f,
      +0.0f, +0.0f,
      +1.0f, +0.0f,
      +1.0f, +1.0f,
      +0.0f, +1.0f,
      +1.0f, +0.0f,
  };



  /** L’instantiator du texte.*/
  private ModelInstantiator<IsoImgInstance> drawer;

  /** Le modèle de la grille qui contient une texture
   * avec tous les éléments affichables sur une même ligne.*/
  private Model2d modele;

  /** Le vertex Shader après compilation.*/
  private static VertexShader vertShader = new VertexShader(vertCode);

  /** Le fragment Shader après compilation.*/
  private static FragmentShader fragShader = new FragmentShader(fragCode);

  /** Le programme opengl pour afficher la grille.*/
  private static OpenglProgram oglProg = new OpenglProgram(vertShader, fragShader);

  /** Construire une boite de texte.
   * @param ressourceName le nom de l'image qui contient tous les éléments affichables
   * ils doivent tous être de même hauteur, de même largeur et collés
   * les uns à côté des autres sur l'image
   * @param nombreElements le nombre d'éléments sur la texture
   */
  public DrawableIsoGrid(String ressourceName, int nombreElements) {
    modele = new Model2d(DrawableIsoGrid.vertices, DrawableIsoGrid.uvs, new TextureImage(ressourceName));

    oglProg.setUniformFloat("isoGridWidth", 300);
    oglProg.setUniformFloat("isoGridHeight", 300);
    oglProg.setUniformFloat("isoGridPosX", 0);
    oglProg.setUniformFloat("isoGridPosY", 0);
    oglProg.setUniformFloat("cameraX", 0);
    oglProg.setUniformFloat("cameraY", 0);
    oglProg.setUniformFloat("cameraZ", 0);
    oglProg.setUniformFloat("widthRatioTex", 1);
    oglProg.setUniformFloat("zoom", 10);

    drawer = new ModelInstantiator<>(oglProg, modele);
    drawer.addObjet(new IsoImgInstance(2,0,0,0));
    drawer.addObjet(new IsoImgInstance(1,0,0,0));
    drawer.addObjet(new IsoImgInstance(0,0,0,0));
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

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
  }
}
