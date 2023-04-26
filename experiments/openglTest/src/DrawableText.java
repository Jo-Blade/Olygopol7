/** Un texte affichable à l’écran.
 * @author : pisento
**/

public class DrawableText implements WindowListener {

  /** Code du vertex shader.*/
  private final static String vertCode =
    "#version 330 core\n" +

    "in vec2 vertex;\n" +
    "in vec2 uvs;\n" +
    "in vec2 positionLettre;\n" +
    "in vec2 glyphPos;\n" +
    "in vec2 glyphTaille;\n" +
    "in float angle;\n" +
    "in float echelle;\n" +

    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "out vec2 fUvs;\n" +
    "out vec2 fGlyphPos;\n" +
    "out vec2 fGlyphTaille;\n" +

    "void main()\n" +
    "{\n" +

    "float cos_t = cos(angle);\n" +
    "float sin_t = sin(angle);\n" +
    "mat2 rotation = mat2(cos_t, sin_t, -sin_t, cos_t);\n" +
    "mat2 winResize = mat2(600. / float(windowWidth), 0.0, 0.0, 600. / float(windowHeight));\n" +
    "mat2 resize = mat2(echelle*glyphTaille.x, 0.0, 0.0, echelle*glyphTaille.y);\n" +

    "vec4 pos = vec4(vertex*rotation*resize*winResize + positionLettre*winResize, 0.0, 1.0);\n" +
    "gl_Position = pos;\n" +

    "fUvs = uvs;\n" +
    "fGlyphPos = glyphPos;\n" +
    "fGlyphTaille = glyphTaille;\n" +
    "}";

  /** Code du fragment shader.*/
  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fUvs;\n" +
    "in vec2 fGlyphPos;\n" +
    "in vec2 fGlyphTaille;\n" +

    "uniform float texWidth;\n" +
    "uniform float texHeight;\n" +
    "uniform sampler2D textureSampler;\n" +

    "void main()\n" +
    "{\n" +
    "mat2 gliphResize = mat2(fGlyphTaille.x / texWidth, 0.0, 0.0, fGlyphTaille.y / texHeight);\n" +
    "vec2 translation = vec2(fGlyphPos.x / texWidth, fGlyphPos.y / texHeight);\n" +
    "vec4 color = texture(textureSampler, translation + fUvs*gliphResize);\n" +
    "fragColor = vec4(0.0, 0.0, 0.0, color.a);\n" +
    "}";

  private final static float[] vertices = new float[]
  {
      0.0f, +1.0f,    // Top-left coordinate
      0.0f, 0.0f,    // Bottom-left coordinate
      +1.0f, 0.0f,    // Bottom-right coordinate

      +1.0f, +1.0f,    // Top-right
      0.0f, +1.0f,    // Top-left
      +1.0f, 0.0f     // Bottom-right
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
  private ModelInstantiator<GlyphInstance> drawer;

  /** Font utilisée.*/
  private static Font font = new Font(new java.awt.Font(
        java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 64));

  /** Le modèle de la font.*/
  private Model2d fontModel;

  /** Le vertex Shader après compilation.*/
  private static VertexShader vertShader = new VertexShader(vertCode);

  /** Le fragment Shader après compilation.*/
  private static FragmentShader fragShader = new FragmentShader(fragCode);

  /** Le programme opengl pour afficher du texte.*/
  private static OpenglProgram fontProg = new OpenglProgram(vertShader, fragShader);

  /** Le texte qui est affiché.*/
  private String texte;

  /** Le gc opengl.*/
  private OpenglGC gc;

  private int windowWidth = 600;
  private int windowHeight = 300;

  /** Construire une boite de texte.
   * @param font la font pour écrire le texte
   * @param texte le texte à écrire
   */
  public DrawableText(OpenglGC gc, String texte) {
    this.texte = texte;
    this.gc = gc;
    fontModel = new Model2d(gc, DrawableText.vertices, DrawableText.uvs, DrawableText.font);

    fontProg.setUniformFloat("texWidth", font.textureWidth);
    fontProg.setUniformFloat("texHeight", font.textureHeight);

    drawer = new ModelInstantiator<GlyphInstance>(gc, fontProg, fontModel);
    changer(texte);
  }

  /** Changer le texte affiché.
   * @param texte le nouveau texte à afficher
   */
  public void changer(String texte) {
    this.texte = texte;
    drawer.clear();

    // On calcule la longueur totale du texte pour le centrer
    int x = 0;
    // for (char c : texte.toCharArray())
    //   x += font.getCaractere(c).width;

    x =  (int) ((50 - windowWidth) / 600.0 / 0.002);
    int y = - font.textureHeight + (int) (-15 + (windowHeight) / 600.0 / 0.002); // centrer le texte verticalement
    // int y = - font.textureHeight / 2;
    for (char c : texte.toCharArray()) {
      Font.Glyph g = font.getCaractere(c);
      drawer.addObjet(new GlyphInstance(gc, 0.002f * x, 0.002f * y, g, 0, 0.002f));
      x += g.width;
    }

    drawer.valider();
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
  public String toString() {
    return "DrawableTexte : " + texte;
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
    changer(texte);
  }
}
