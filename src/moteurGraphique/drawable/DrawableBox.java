package moteurGraphique.drawable;

import moteurGraphique.drawable.instance.BoxInstance;
import moteurGraphique.glThread.OpenglThread;
import moteurGraphique.glType.FragmentShader;
import moteurGraphique.glType.OpenglProgram;
import moteurGraphique.glType.VertexShader;
import moteurGraphique.vecteur.FloatVec2;
import moteurGraphique.vecteur.FloatVec4;

/** Un rectangle avec une bordure.
 * @author : pisento
**/

public class DrawableBox {
public final static String vertCode =
    "#version 330 core\n" +

    "layout (location = 0) in vec2 position;\n" +
    "layout (location = 1) in vec2 uvs;\n" +
    "layout (location = 2) in vec2 point1;\n" +
    "layout (location = 3) in vec2 dimensions;\n" +
    "layout (location = 4) in vec4 couleurBordure;\n" +
    "layout (location = 5) in vec4 couleurFond;\n" +
    "layout (location = 6) in float rayon;\n" +
    "layout (location = 7) in float epaisseur;\n" +

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

    "mat2 resize = 2*mat2(dimensions.x / float(windowWidth), 0.0, 0.0, dimensions.y / float(windowHeight));\n" +

    "gl_Position = vec4(vec2(-1., 1.) + position*resize + vec2(2. / float(windowWidth), -2. / float(windowHeight))*point1, 0.0, 1.0);\n" +
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
    // "float aa = max(6/windowWidth, 6/windowHeight);\n" +
    // "float aa = 1.5;\n" +
    // "float aa = 0;\n" +

    "float alpha = smoothstep(lg - 1, lg + 1, radius);\n" +
    "alpha += step(radius, length(st - vec2(width/2.,height/2.)));\n" +

    "float radius2 = radius - border_size;\n" +
    "float choisir = smoothstep(radius2 - 1.5, radius2, lg);\n" +

    "vec2 d = vec2(width/2., height/2.) - st;\n" +
    "choisir -= step(border_size + 1, d.x)*step(radius2, d.y) + step(radius2, d.x)*step(border_size + 1, d.y);\n" +
    "choisir = max(choisir, 0.);\n" +

    "vec4 color = border*choisir + (1. - choisir) * fond;\n" +
    "fragColor = vec4(color.rgb, min(color.a, alpha));\n" +
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
    -0.5f, +0.5f,    // Top-left coordinate
      -0.5f, -0.5f,    // Bottom-left coordinate
      +0.5f, -0.5f,    // Bottom-right coordinate

      +0.5f, +0.5f,    // Top-right
      -0.5f, +0.5f,    // Top-left
      +0.5f, -0.5f     // Bottom-right
  };

  private ModelInstantiator<BoxInstance> drawer;
public static OpenglProgram glProg = new OpenglProgram(
      new VertexShader(vertCode), new FragmentShader(fragCode)
      );

  /** Point suppérieur gauche du rectangle. */
  private FloatVec2 coin;

  /** Dimensions du rectangle. */
  private FloatVec2 dimensions;

  /** Couleur du fond. */
  private FloatVec4 couleurFond;

  /** Couleur du bord. */
  private FloatVec4 couleurBord;

  /** Epaisseur de la bordure. */
  private float epaisseurBord;

  /** Rayon du bord arrondi. */
  private float rayonBord;

  /** Créer un rectangle affichable.
   * @param couleurFondR composante rouge du fond (entre 0 et 1)
   * @param couleurFondG composante rouge du fond (entre 0 et 1)
   * @param couleurFondB composante rouge du fond (entre 0 et 1)
   * @param couleurFondA composante rouge du fond (entre 0 et 1)
   * @param couleurBordR composante rouge du fond (entre 0 et 1)
   * @param couleurBordG composante rouge du fond (entre 0 et 1)
   * @param couleurBordB composante rouge du fond (entre 0 et 1)
   * @param couleurBordA composante rouge du fond (entre 0 et 1)
   */
  public DrawableBox(double couleurFondR, double couleurFondG, double couleurFondB, double couleurFondA,
      double couleurBordR, double couleurBordG, double couleurBordB, double couleurBordA) {

    Model2d modele = new Model2dNoTex(vertices, uvs);
    drawer = new ModelInstantiator<>(glProg, modele);

    this.couleurFond = new FloatVec4((float) couleurFondR, (float) couleurFondG,
        (float) couleurFondB, (float) couleurFondA);

    this.couleurBord = new FloatVec4((float) couleurBordR, (float) couleurBordG,
        (float) couleurBordB, (float) couleurBordA);
  }

  /** Changer les couleurs de la boite
   * @param couleurFondR composante rouge du fond (entre 0 et 1)
   * @param couleurFondG composante rouge du fond (entre 0 et 1)
   * @param couleurFondB composante rouge du fond (entre 0 et 1)
   * @param couleurFondA composante rouge du fond (entre 0 et 1)
   * @param couleurBordR composante rouge du fond (entre 0 et 1)
   * @param couleurBordG composante rouge du fond (entre 0 et 1)
   * @param couleurBordB composante rouge du fond (entre 0 et 1)
   * @param couleurBordA composante rouge du fond (entre 0 et 1)
   */
  public void changerCouleur(double couleurFondR, double couleurFondG, double couleurFondB, double couleurFondA,
      double couleurBordR, double couleurBordG, double couleurBordB, double couleurBordA) {

    this.couleurFond = new FloatVec4((float) couleurFondR, (float) couleurFondG,
        (float) couleurFondB, (float) couleurFondA);

    this.couleurBord = new FloatVec4((float) couleurBordR, (float) couleurBordG,
        (float) couleurBordB, (float) couleurBordA);

    changer();
  }

  /** Redimensionner et repositionner le rectangle à l'écran
   * @param positionX la distance au bord gauche de l'écran (en pixels)
   * @param positionY la distance au bord haut de l'écran (en pixels)
   * @param largeur la largeur du rectangle (en pixels)
   * @param hauteur la hauteur du rectangle (en pixels)
   * @param epaisseurBord l'epaisseur de la bordure
   * @param rayon le rayon du bord arrondi
   */
  public void redimensionner(int positionX, int positionY, int largeur, int hauteur,
      double epaisseurBord, double rayon) {

    this.coin = new FloatVec2(positionX, positionY);
    this.dimensions = new FloatVec2(largeur, hauteur);
    this.epaisseurBord = (float) epaisseurBord;
    this.rayonBord = (float) rayon;

    changer();
  }

  /** Donner l’instruction d’affichage.
   * @param openglThread le thread chargé de l’affichage
   */
  public void afficher(OpenglThread openglThread) {

    openglThread.ajouterAffichage(drawer);
  }

  /** Ne plus afficher.
   * @param openglThread le thread chargé de l’affichage
   */
  public void cacher(OpenglThread openglThread) {

    openglThread.retirerAffichage(drawer);
  }


  /** Changer le rectangle dessiné. */
  private void changer() {
    drawer.clear();

    drawer.addObjet(new BoxInstance(
        coin,
        dimensions,
        couleurBord,
        couleurFond,
        rayonBord, epaisseurBord 
        ));

    // on indique qu’on a fini de modifier le drawer
    drawer.valider();
  }

}
