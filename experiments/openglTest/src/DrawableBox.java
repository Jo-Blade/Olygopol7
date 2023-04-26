/** Un rectangle avec une bordure.
 * @author : pisento
**/

public class DrawableBox implements WindowListener {

  private final static String vertCode =
    "#version 330 core\n" +

    "in vec2 position;\n" +
    "in vec2 uvs;\n" +
    "in vec2 positionCentre;\n" +
    "in vec2 dimensions;\n" +
    "in vec4 couleurBordure;\n" +
    "in vec4 couleurFond;\n" +
    "in float rayon;\n" +
    "in float epaisseur;\n" +

    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "out vec2 fragUvs;\n" +

    "out float width;\n" +
    "out float height;\n" +
    "out vec4 border;\n" +
    "out vec4 fond;\n" +
    "out float radius;\n" +
    "out float border_size;\n" +

    "void main()\n" +
    "{\n" +

    "mat2 resize = mat2(dimensions.x / float(windowWidth), 0.0, 0.0, dimensions.y / float(windowHeight));\n" +

    "gl_Position = vec4(position*resize + positionCentre, 0.0, 1.0);\n" +
    "fragUvs = uvs;\n" +

    "width = dimensions.x;\n" +
    "height = dimensions.y;\n" +
    "border = couleurBordure;\n" +
    "fond = couleurFond;\n" +
    "radius = rayon;\n" +
    "border_size = epaisseur;\n" +
    "}";


  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fragUvs;\n" +

    "in float width;\n" +
    "in float height;\n" +
    "in vec4 border;\n" +
    "in vec4 fond;\n" +
    "in float radius;\n" +
    "in float border_size;\n" +

    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "void main() {\n" +
    "vec2 st = abs(fragUvs);\n" +

    "st.x *= width;\n" +
    "st.y *= height;\n" +

    "float lg = length(st - vec2(width/2. - radius, height/2. - radius));\n" +
    "float aa = max(width/windowWidth, height/windowHeight);\n" +

    "float alpha = smoothstep(lg - aa, lg, radius);\n" +
    "alpha += step(radius, length(st - vec2(width/2.,height/2.)));\n" +

    "float radius2 = radius - border_size;\n" +
    "float choisir = smoothstep(radius2 - aa, radius2 + aa, lg);\n" +

    "vec2 d = vec2(width/2., height/2.) - st;\n" +
    "choisir -= step(border_size, d.x)*step(radius, d.y) + step(radius, d.x)*step(border_size, d.y);\n" +
    "choisir = max(choisir, 0.);\n" +

    "vec4 color = border*choisir + (1. - choisir) * fond;\n" +
    "fragColor = vec4(color.rgb, min(color.a, alpha));\n" +
    "}\n";


  private final static float[] vertices = new float[]
  {
    -1.0f, +1.0f,    // Top-left coordinate
      -1.0f, -1.0f,    // Bottom-left coordinate
      +1.0f, -1.0f,    // Bottom-right coordinate

      +1.0f, +1.0f,    // Top-right
      -1.0f, +1.0f,    // Top-left
      +1.0f, -1.0f     // Bottom-right
  };

  private final static float[] uvs = new float[]
  {
    -0.5f, +0.5f,    // Top-left coordinate
      -0.5f, -0.5f,    // Bottom-left coordinate
      +0.5f, -0.5f,    // Bottom-right coordinate

      +0.5f, +0.5f,    // Top-right
      -0.5f, +0.5f,    // Top-left
      +0.5f, -0.5f     // Bottom-right
  };

  private ModelInstantiator<BoxInstance> drawer;

  private static OpenglProgram glProg = new OpenglProgram(
      new VertexShader(vertCode), new FragmentShader(fragCode)
      );

  private OpenglGC gc;
  private BoxInstance obTest;

  public DrawableBox(OpenglGC gc) {
    this.gc = gc;

    Model2d modele = new Model2dNoTex(gc, vertices, uvs);
    drawer = new ModelInstantiator<>(gc, glProg, modele);

    obTest = new BoxInstance(
        gc,
        new FloatVec2(0,0),
        new FloatVec2(400, 80),
        new FloatVec4(1, 0, 0, 0.8f),
        new FloatVec4(1, 1, 1, 0.5f),
        30, 10
        );

    drawer.addObjet(obTest);
    // on indique qu’on a fini de modifier le drawer
    drawer.valider();
  }

  /** Donner l’instruction d’affichage.
   * @param openglThread le thread chargé de l’affichage
   */
  public void afficher(OpenglThread openglThread) {

    openglThread.ajouterAffichage(drawer);
  }

  @Override
  public void updateWindowTaille(int windowWidth, int windowHeight) {
    drawer.delObjet(obTest);

    obTest = new BoxInstance(
        gc,
        new FloatVec2(-1 + 270f / (float) windowWidth, 1 - 70f / windowHeight),
        new FloatVec2(250, 60),
        new FloatVec4(1, 0, 0, 0.8f),
        new FloatVec4(1, 1, 1, 0.5f),
        15, 8
        );
    drawer.addObjet(obTest);
    drawer.valider();
  }

}
