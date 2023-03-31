/** Un texte affichable à l’écran.
 * @author : pisento
**/

public class DrawableText {

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

    "out vec2 fUvs;\n" +
    "out vec2 fGlyphPos;\n" +
    "out vec2 fGlyphTaille;\n" +


    "void main()\n" +
    "{\n" +

    "float cos_t = cos(angle);\n" +
    "float sin_t = sin(angle);\n" +
    "mat2 rotation = mat2(cos_t, sin_t, -sin_t, cos_t);\n" +
    "mat2 resize = mat2(echelle*glyphTaille.x, 0.0, 0.0, echelle*glyphTaille.y);\n" +

    "vec4 pos = vec4(vertex*rotation*resize + positionLettre, 0.0, 1.0);\n" +
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
  private static Model2d fontModel = new Model2d(DrawableText.vertices, DrawableText.uvs, DrawableText.font);

  /** Construire une boite de texte.
   * @param font la font pour écrire le texte
   * @param texte le texte à écrire
   */
  public DrawableText(String texte) {

    VertexShader vertShader = new VertexShader(vertCode);
    FragmentShader fragShader = new FragmentShader(fragCode);
    OpenglProgram fontProg = new OpenglProgram(vertShader, fragShader);

    fontProg.setUniformFloat("texWidth", font.textureWidth);
    fontProg.setUniformFloat("texHeight", font.textureHeight);

    drawer = new ModelInstantiator<GlyphInstance>(fontProg, fontModel);

    // On calcule la longueur totale du texte pour le centrer
    int x = 0;
    for (char c : texte.toCharArray())
      x += font.getCaractere(c).width;

    x = - x / 2;
    int y = - font.textureHeight / 2; // centrer le texte verticalement
    for (char c : texte.toCharArray()) {
      Font.Glyph g = font.getCaractere(c);
      drawer.addObjet(new GlyphInstance(0.003f * x, 0.003f * y, g, 0, 0.003f));
      x += g.width;
    }
  }

  /** Afficher le texte.*/
  public void dessiner() {

    drawer.dessinerObjets();
  }

}
