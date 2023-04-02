/** Un rectangle avec une bordure.
 * @author : pisento
**/

public class DrawableBox {

  private final static String vertCode =
    "#version 330 core\n" +

    "in vec2 position;\n" +
    "in vec2 uvs;\n" +
    "in vec2 positionCentre;\n" +
    "in float angle;\n" +
    "in float echelle;\n" +

    "out vec2 fragUvs;\n" +
    "out float test;\n" +

    "void main()\n" +
    "{\n" +

    "float cos_t = cos(angle);\n" +
    "float sin_t = sin(angle);\n" +
    "mat2 rotation = mat2(cos_t, sin_t, -sin_t, cos_t);\n" +
    //"mat2 resize = mat2(echelle, 0.0, 0.0, echelle);\n" +
    "mat2 resize = mat2(0.7, 0.0, 0.0, 0.2);\n" +

    "gl_Position = vec4(position*rotation*resize + positionCentre, 0.0, 1.0);\n" +
    "fragUvs = uvs;\n" +
    "}";


  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fragUvs;\n" +

"const vec2 u_resolution = vec2(1920, 1080);\n" +

"const float radius = 49.;\n" +
"const float border_size = 20.;\n" +
"const float width = 600.0;\n" +
"const float height = 100.0;\n" +

"const vec3 fond = vec3(0.550,0.723,1.000);\n" +
"const vec3 border = vec3(0.159,0.376,1.000);\n" +


"void main() {\n" +
    "vec2 st = abs(fragUvs);\n" +

    "st.x *= width;\n" +
    "st.y *= height;\n" +

    "float lg = length(st - vec2(width/2. - radius, height/2. - radius));\n" +
    "float aa = max(width/u_resolution.x, height/u_resolution.y);\n" +

    "float alpha = smoothstep(lg - aa, lg, radius);\n" +
    "alpha += step(radius, length(st - vec2(width/2.,height/2.)));\n" +

    "float radius2 = radius - border_size;\n" +
    "float choisir = smoothstep(radius2 - aa, radius2 + aa, lg);\n" +

    "vec2 d = vec2(width/2., height/2.) - st;\n" +
    "choisir -= step(border_size, d.x)*step(radius, d.y) + step(radius, d.x)*step(border_size, d.y);\n" +
    "choisir = max(choisir, 0.);\n" +

    "fragColor = vec4(border*choisir + (1. - choisir) * fond, alpha);\n" +
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

  private ModelInstantiator<TestInstance> drawer;

  public DrawableBox(OpenglGC gc) {

    VertexShader testVertSh = new VertexShader(vertCode);
    FragmentShader testFragSh = new FragmentShader(fragCode);
    OpenglProgram testProg = new OpenglProgram(testVertSh, testFragSh);

    Model2d testModel = new Model2dNoTex(gc, vertices, uvs);
    drawer = new ModelInstantiator<TestInstance>(gc, testProg, testModel);


    TestInstance obTest = new TestInstance(gc, 0, 0, 0, 0.5f);
    drawer.addObjet(obTest);
  }

  /** Afficher le rectangle.*/
  public void dessiner() {

    drawer.dessinerObjets();
  }

}
