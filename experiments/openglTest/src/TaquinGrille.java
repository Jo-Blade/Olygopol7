/** Grille du jeu de taquin.
 * @author : pisento
**/
import java.util.Set;
import java.util.HashSet;

public class TaquinGrille {

  /** La case vide du taquin.*/
  private Vec2Int caseLibre = new Vec2Int(0,0);

  /** L’ensemble des cases du taquin.*/
  private Set<TaquinCarre> listCases = new HashSet<>();

  private static float[] vertices = new float[]
  {
      -1.0f, +1.0f,    // Top-left coordinate
      -1.0f, -1.0f,    // Bottom-left coordinate
      +1.0f, -1.0f,    // Bottom-right coordinate

      +1.0f, +1.0f,    // Top-right
      -1.0f, +1.0f,    // Top-left
      +1.0f, -1.0f     // Bottom-right
  };

  private static float[] uvs = new float[]
  {
      +0.0f, +0.0f,
      +0.0f, +1.0f,
      +1.0f, +1.0f,
      +1.0f, +0.0f,
      +0.0f, +0.0f,
      +1.0f, +1.0f,
  };

  private final static String vertexShaderCode =
    "#version 330 core\n" +

    "in vec2 position;\n" +
    "in vec2 uvs;\n" +
    "in vec2 positionCentre;\n" +
    "in vec2 centreUv;\n" +

    "out vec2 fragUvs;\n" +

    "uniform float nbrCote;\n" +
    "uniform int windowWidth;\n" +
    "uniform int windowHeight;\n" +

    "void main()\n" +
    "{\n" +
    "int winMin = min(windowWidth, windowHeight);\n" +
    "float echelle = 1. / nbrCote;\n" +
    "mat2 resize = mat2(winMin * echelle / windowWidth, 0.0, 0.0, winMin * echelle / windowHeight);\n" +
    "vec2 centre = vec2(-1,1) + vec2(positionCentre.x, - positionCentre.y);\n" +
    "gl_Position = vec4(position*resize + 2.*resize*centre, 0.0, 1.0);\n" +
    "fragUvs = echelle*uvs + echelle*centreUv;\n" +
    "}";


  private final static String fragmentShaderCode =
    "#version 330 core\n" +
    "out vec4 fragColor;\n" +
    "in vec2 fragUvs;\n" +
    "uniform sampler2D textureSampler;\n" +
    "void main()\n" +
    "{\n" +
    "fragColor = texture(textureSampler,fragUvs);\n" +
    "}";

  private final static VertexShader vertexShader = new VertexShader(vertexShaderCode);

  private final static FragmentShader fragmentShader = new FragmentShader(fragmentShaderCode);

  /** Est-ce que la grille est déjà occupée.*/
  private boolean estOccupe;

  /** Le modèle instantiator de la grille.*/
  private ModelInstantiator<TaquinCarre> drawer;

  /** Créer une grille de jeu du taquin vide.
   * @param nombreCote nombre de pieces du puzzle par coté
   * @param image nom de l’image à reconstruire
   */
  public TaquinGrille(OpenglGC gc, int nombreCote, String image) {
    this.estOccupe = false;

    Model2d modele = new Model2d(gc, TaquinGrille.vertices, TaquinGrille.uvs,
        new TextureImage(image));
    OpenglProgram programme = new OpenglProgram(vertexShader, fragmentShader);
    programme.setUniformFloat("nbrCote", nombreCote);

    drawer = new ModelInstantiator<>(gc, programme, modele);
  }

  /** Deplacer les pieces après le clic sur une case.*/
  public void deplacerClic(Vec2Int positionClic) {
    if (estOccupe)
      return;

    estOccupe = true;

    if (positionClic.x == caseLibre.x) {
      if (positionClic.y < caseLibre.y) {

        for (TaquinCarre c : listCases) {
          if ((c.getY() < caseLibre.y) && (c.getY() >= positionClic.y) && (c.getX() == caseLibre.x))
            c.pousser(Direction.BAS);
        }

      }
      else if (positionClic.y > caseLibre.y) {

        for (TaquinCarre c : listCases) {
          if ((c.getY() > caseLibre.y) && (c.getY() <= positionClic.y) && (c.getX() == caseLibre.x))
            c.pousser(Direction.HAUT);
        }

      }
      caseLibre = new Vec2Int(positionClic.x, positionClic.y);
    }
    else if (positionClic.y == caseLibre.y) {
     if (positionClic.x < caseLibre.x) {

        for (TaquinCarre c : listCases) {
          if ((c.getX() < caseLibre.x) && (c.getX() >= positionClic.x) && (c.getY() == caseLibre.y))
            c.pousser(Direction.DROITE);
        }

      }
      else if (positionClic.x > caseLibre.x) {

        for (TaquinCarre c : listCases) {
          if ((c.getX() > caseLibre.x) && (c.getX() <= positionClic.x) && (c.getY() == caseLibre.y))
            c.pousser(Direction.GAUCHE);
        }

      }
      caseLibre = new Vec2Int(positionClic.x, positionClic.y);
    }


    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {}

    estOccupe = false;
  }

  /** Ajouter une pièce du puzzle à la grille.
   * @param piece la pièce à ajouter
   */
  public void addPiece(TaquinCarre piece) {
    drawer.addObjet(piece);
    listCases.add(piece);

    // on valide qu’on a fini la modification pour mettre à jour l’affichage
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

  /** Valider une modification de la grille et mettre à jour l’affichage.*/
  public void valider() {
    drawer.valider();
  }
}
