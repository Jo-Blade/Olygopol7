/**
 * @author : pisento
 **/
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

public class HelloBis {

  private final static OpenglGC openglGc = new OpenglGC();

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
    "mat2 resize = mat2(echelle, 0.0, 0.0, echelle);\n" +

    "gl_Position = vec4(position*rotation*resize + positionCentre, 0.0, 1.0);\n" +
    "fragUvs = uvs;\n" +
    "}";


  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in vec2 fragUvs;\n" +

    "uniform sampler2D textureSampler;\n" +

    "void main()\n" +
    "{\n" +
    "vec4 color = texture(textureSampler,fragUvs);\n" +
    "fragColor = color;\n" +
    "}";


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


  public class Compteur {
    public int compteur = 0;

    public void inc() {
      compteur++;
    }
  }

  public static void main(String[] args) {
    System.out.println("Hello Bis !");



    VertexShader testVertSh = new VertexShader(vertCode);
    FragmentShader testFragSh = new FragmentShader(fragCode);
    OpenglProgram testProg = new OpenglProgram(testVertSh, testFragSh);

    Model2d testModel = new Model2d(openglGc, vertices, uvs, new TextureImage("texture.png"));
    ModelInstantiator<TestInstance> testDrawer = new ModelInstantiator<TestInstance>(openglGc, testProg, testModel);

    float time = 0.0f;
    Instant instant = Instant.now();


    OpenglThread openglThread = new OpenglThread(openglGc);
    openglThread.start();

    TaquinGrille grille = new TaquinGrille(openglGc, 3, "texture.png");

    new TaquinCarre(openglGc, 2, 0, 1, 0, grille);
    new TaquinCarre(openglGc, 2, 1, 2, 0, grille);
    new TaquinCarre(openglGc, 1, 0, 0, 1, grille);
    new TaquinCarre(openglGc, 2, 2, 1, 1, grille);
    new TaquinCarre(openglGc, 1, 1, 2, 1, grille);
    new TaquinCarre(openglGc, 0, 1, 0, 2, grille);
    new TaquinCarre(openglGc, 1, 2, 1, 2, grille);
    new TaquinCarre(openglGc, 0, 2, 2, 2, grille);

    Compteur compteurClics = new HelloBis().new Compteur();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        TaquinButton bouton = 
            new TaquinButton( grille,
            new FloatVec2(1f/3f* (float) j, 1f/3f* (float) i),
            new FloatVec2(1f/3f* (float) (j+1), 1f/3f* (float) (i+1)),
            new Vec2Int(j, i),
            compteurClics);

        openglThread.ajouterBouton(bouton);
        openglThread.ajouterEcouteur(bouton);
      }
    }

    grille.afficher(openglThread);

    DrawableBox testBox = new DrawableBox(openglGc);
    openglThread.ajouterEcouteur(testBox);
    testBox.afficher(openglThread);

    int compteur = 0;
    DrawableText texte = new DrawableText(openglGc, "clics : 0");
    openglThread.ajouterEcouteur(texte);
    texte.afficher(openglThread);

    while ( openglThread.isAlive() ) {
      if (compteur != compteurClics.compteur) {
        texte.changer("clics : " + compteurClics.compteur);
        compteur = compteurClics.compteur;
      }
    }

    /*
       DrawableText texte = new DrawableText(openglGc, "fps : " + 0);
       DrawableBox testBox = new DrawableBox(openglGc);

       openglThread.ajouterAffichage(testDrawer);
       testBox.afficher(openglThread);
       texte.afficher(openglThread);

       Map<Integer, TestInstance> fleurs = new HashMap<>();

       for (int i = 0; i < 100; i++) {
       TestInstance fleur = new TestInstance(openglGc, -0.9f + 0.2f*((int) i % 10), -0.9f + 0.2f*((int) i / 10), (2*(i%2) - 1)*time, 0.1f);
       testDrawer.addObjet(fleur);
       fleurs.put(i, fleur);
       }


    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while ( openglThread.isAlive() ) {

    Instant newInstant = Instant.now();
    long timeElapsed = Duration.between(instant, newInstant).toMillis();
    if (timeElapsed > 50) {
    texte.cacher(openglThread);
    texte = new DrawableText(openglGc, "fps : " + 100*openglThread.compteurFrames);
    texte.afficher(openglThread);
    instant = newInstant;
    openglThread.compteurFrames = 0;
    // System.gc();
    newInstant = null;

    time += 0.01f;
    testDrawer.clear();
    for (int i = 0; i < 100; i++) {
    TestInstance fleur = new TestInstance(openglGc, -0.9f + 0.2f*((int) i % 10), -0.9f + 0.2f*((int) i / 10), (2*(i%2) - 1)*time, 0.1f);
    TestInstance oldFleur = fleurs.get(i);
    testDrawer.delObjet(oldFleur);
    fleurs.remove(i);
    testDrawer.addObjet(fleur);
    fleurs.put(i, fleur);
    }
    }
    }
    */
  }
}
