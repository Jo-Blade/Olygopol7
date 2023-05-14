package MoteurGraphique;
/** Un texte affichable à l’écran.
 * @author : pisento
**/

public class DrawableText {

  /** La distance au bord gauche de l'écran. */
  private int positionX;

  /** La distance au bord haut de l'écran. */
  private int positionY;

  /** La taille du texte. */
  private double fontSize;

  /** La couleur du texte.*/
  private FloatVec4 couleur;

  /** Code du vertex shader.*/
  private final static String vertCode =
    "#version 330 core\n" +

    "in vec2 vertex;\n" +
    "in vec2 uvs;\n" +
    "in vec2 positionLettre;\n" +
    "in vec2 glyphPos;\n" +
    "in vec2 glyphTaille;\n" +
    "in float echelle;\n" +
    "in vec4 couleur;\n" +

    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "out vec2 fUvs;\n" +
    "out vec2 fGlyphPos;\n" +
    "out vec2 fGlyphTaille;\n" +
    "out vec4 fCouleur;\n" +

    "void main()\n" +
    "{\n" +

    "mat2 resize = echelle * mat2(50. / float(windowWidth), 0.0, 0.0, (50. * glyphTaille.y) / (float(windowHeight) * glyphTaille.x));\n" +
    "mat2 chbase = mat2(2. / float(windowWidth), 0., 0., - 2. / float(windowHeight));\n" +
    "gl_Position = vec4(resize*vertex + chbase*positionLettre + vec2(-1.0, 1.0), 0.0, 1.0);\n" +

    "fUvs = uvs;\n" +
    "fGlyphPos = glyphPos;\n" +
    "fGlyphTaille = glyphTaille;\n" +
    "fCouleur = couleur;\n" +

    "}";

  /** Code du fragment shader.*/
  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fUvs;\n" +
    "in vec2 fGlyphPos;\n" +
    "in vec2 fGlyphTaille;\n" +
    "in vec4 fCouleur;\n" +

    "uniform float texWidth;\n" +
    "uniform float texHeight;\n" +
    "uniform sampler2D textureSampler;\n" +

    "void main()\n" +
    "{\n" +
    "mat2 gliphResize = mat2(fGlyphTaille.x / texWidth, 0.0, 0.0, fGlyphTaille.y / texHeight);\n" +
    "vec2 translation = vec2(fGlyphPos.x / texWidth, fGlyphPos.y / texHeight);\n" +
    "vec4 color = texture(textureSampler, translation + fUvs*gliphResize);\n" +
    "fragColor = vec4(fCouleur.r, fCouleur.g, fCouleur.b, min(fCouleur.a, color.a));\n" +
    "}";

  private final static float[] vertices = new float[]
  {
    0.0f, 0.0f,    // Top-left coordinate
      0.0f,  -1.0f,    // Bottom-left coordinate
      +1.0f, -1.0f,    // Bottom-right coordinate

      +1.0f, 0.0f,    // Top-right
      0.0f, 0.0f,    // Top-left
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

  /** Construire un texte affichable
   * @param texte le texte à écrire
   * @param couleurR composante rouge de la couleur (entre 0 et 1)
   * @param couleurG composante vert de la couleur
   * @param couleurB composante bleue de la couleur
   * @param couleurA composante alpha de la couleur
   */
  public DrawableText(String texte, double couleurR, double couleurG,
      double couleurB, double couleurA) {
    this.texte = texte;
    fontModel = new Model2d(DrawableText.vertices, DrawableText.uvs, DrawableText.font);

    fontProg.setUniformFloat("texWidth", font.textureWidth);
    fontProg.setUniformFloat("texHeight", font.textureHeight);

    drawer = new ModelInstantiator<GlyphInstance>(fontProg, fontModel);
    this.couleur = new FloatVec4((float) couleurR, (float) couleurG, (float) couleurB, (float) couleurA);
    changer(texte);
  }

  /** Changer le texte affiché.
   * @param texte le nouveau texte à afficher
   */
  public void changer(String texte) {
    this.texte = texte;
    drawer.clear();

    // On calcule la longueur totale du texte pour le centrer
    float x = this.positionX;
    // for (char c : texte.toCharArray())
    //   x += font.getCaractere(c).width;

    int y = this.positionY;
    // int y = - font.textureHeight / 2;
    for (char c : texte.toCharArray()) {
      Font.Glyph g = font.getCaractere(c);
      drawer.addObjet(new GlyphInstance(x, y, g, (float) this.fontSize,
            couleur.r, couleur.g, couleur.b, couleur.a));
      x += fontSize * (g.width - 13);
    }

    drawer.valider();
  }


  /** Construire un texte affichable
   * @param couleurR composante rouge de la couleur (entre 0 et 1)
   * @param couleurG composante vert de la couleur
   * @param couleurB composante bleue de la couleur
   * @param couleurA composante alpha de la couleur
   */
  public void changerCouleur(double couleurR, double couleurG,
      double couleurB, double couleurA) {

    this.couleur = new FloatVec4((float) couleurR, (float) couleurG, (float) couleurB, (float) couleurA);
    changer(texte);
  }

  /** Redimensionner et repositionner le texte à l'écran
   * @param positionX la distance au bord gauche de l'écran (en pixels)
   * @param positionY la distance au bord haut de l'écran (en pixels)
   * @param fontSize la taille de la police
   */
  public void redimensionner(int positionX, int positionY, double fontSize) {
    this.positionX = positionX;
    this.positionY = positionY;
    this.fontSize = fontSize;

    changer(texte);
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

}
