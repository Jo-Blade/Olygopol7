/**
 * @author : pisento
 **/
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.HashSet;

import java.time.Duration;
import java.time.Instant;

public class HelloBis {

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



  public static void main(String[] args) {
    System.out.println("Hello Bis !");

    Window testWindow = new Window(false, false, new Vec2Int(600, 300), "testWindow");
    testWindow.show();


    VertexShader testVertSh = new VertexShader(vertCode);
    FragmentShader testFragSh = new FragmentShader(fragCode);
    OpenglProgram testProg = new OpenglProgram(testVertSh, testFragSh);

    Model2d testModel = new Model2d(vertices, uvs, new TextureImage("texture.png"));
    ModelInstantiator<TestInstance> testDrawer = new ModelInstantiator<TestInstance>(testProg, testModel);

    float time = 0.0f;
    Instant instant = Instant.now();
    int frame = 0;

    DrawableText texte = new DrawableText("fps : " + frame);

    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while ( !glfwWindowShouldClose(testWindow.getHandle()) ) {

      Instant newInstant = Instant.now();
      long timeElapsed = Duration.between(instant, newInstant).toMillis();
      if (timeElapsed > 1000) {
        texte = new DrawableText("fps : " + frame);
        instant = newInstant;
        frame = 0;
        // System.gc();
      }
      newInstant = null;
      frame++;

      time += 0.01f;
      testDrawer.clear();
      for (int i = 0; i < 100; i++) {
        TestInstance fleur = new TestInstance(-0.9f + 0.2f*((int) i % 10), -0.9f + 0.2f*((int) i / 10), (2*(i%2) - 1)*time, 0.1f);
        testDrawer.addObjet(fleur);
      }

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      testDrawer.dessinerObjets();
      texte.dessiner();


      glfwSwapBuffers(testWindow.getHandle()); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();
    }
  }
}
