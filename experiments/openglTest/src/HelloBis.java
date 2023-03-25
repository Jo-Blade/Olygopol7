/**
 * @author : pisento
 **/
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;

public class HelloBis {

  private final static String vertCode =
    "#version 330 core\n" +

    "in vec2 position;\n" +
    "in vec2 positionCentre;\n" +
    "in float angle;\n" +
    "out float test;\n" +

    "void main()\n" +
    "{\n" +
    "gl_Position = vec4(position + positionCentre, 0.0, 1.0);\n" +
    "test = angle;\n" +
    "}";


  private final static String fragCode =
    "#version 330 core\n" +

    "out vec4 fragColor;\n" +

    "in float test;\n" +

    "void main()\n" +
    "{\n" +
    "fragColor = vec4(test, 1.0, 1.0, 1.0);\n" +
    "}";


  private static float[] vertices = new float[]
  {
      -0.1f, +1.0f,    // Top-left coordinate
      -0.1f, -1.0f,    // Bottom-left coordinate
      +0.1f, -1.0f,    // Bottom-right coordinate

      +0.1f, +1.0f,    // Top-right
      -0.1f, +1.0f,    // Top-left
      +0.1f, -1.0f     // Bottom-right
  };



  public static void main(String[] args) {
    System.out.println("Hello Bis !");

    Window testWindow = new Window(true, true, new Vec2Int(600, 300), "testWindow");
    testWindow.show();


    VertexShader testVertSh = new VertexShader(vertCode);
    FragmentShader testFragSh = new FragmentShader(fragCode);
    OpenglProgram testProg = new OpenglProgram(testVertSh, testFragSh);

    Model2d testModel = new Model2d(vertices, "texture.png");
    ModelInstantiator<TestInstance> testDrawer = new ModelInstantiator<TestInstance>(testProg, testModel);

    TestInstance ob1 = new TestInstance(0.0f, 0.0f, 0.9f);
    TestInstance ob2 = new TestInstance(0.5f, 0.0f, 0.9f);
    TestInstance ob3 = new TestInstance(-0.5f, 0.5f, 0.0f);
    testDrawer.addObjet(ob1);
    testDrawer.addObjet(ob2);
    testDrawer.addObjet(ob3);

    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while ( !glfwWindowShouldClose(testWindow.getHandle()) ) {

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
      testDrawer.DessinerObjets();
      glfwSwapBuffers(testWindow.getHandle()); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();
    }
  }
}
