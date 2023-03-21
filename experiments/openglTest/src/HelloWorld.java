/**
 * @author : pisento
 * @created : 2023-03-07
**/

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.File;
import org.lwjgl.stb.STBImage;
import java.util.HashMap;



public class HelloWorld {

  float windowWidth;
  float windowHeight;

  private final String vertShaderSrc =
"#version 330 core\n" +

"in vec2 position;\n" +
"in vec2 uvs;\n" +
"out vec2 test;\n" +
"in vec2 positionCentre;\n" +
"uniform float time;\n" +
"uniform float windowWidth;\n" +
"uniform float windowHeight;\n" +

"void main()\n" +
"{\n" +
    "float cos_t = cos(time);\n" +
    "float sin_t = sin(time);\n" +
    "float b = 0.3;\n" +
    "float c = windowHeight / windowWidth;\n" +
    "float d = windowWidth / windowHeight;\n" +
    "mat2 rotation = mat2(cos_t, sin_t, -sin_t, cos_t);\n" +
    "mat2 resize = mat2(b*min(1.0, c), 0.0, 0.0, b*min(1.0,d));\n" +

    "vec2 newCentre = 2.0*positionCentre*resize;\n" +
    "vec2 spriteDemiWidthHeigth = vec2(1.0)*resize;\n" +
    "float overflowX = min(newCentre.x - spriteDemiWidthHeigth.x + 1.0, 1.0 - newCentre.x - spriteDemiWidthHeigth.x);\n" +
    "float overflowY = min(newCentre.y - spriteDemiWidthHeigth.y + 1.0, 1.0 - newCentre.y - spriteDemiWidthHeigth.y);\n" +
    "float overflow = min(overflowX, overflowY);\n" +

    "gl_Position = vec4(step(0.0, overflow)*((position * rotation) * resize + 2.0*positionCentre*resize), 0.0, 1.0);\n" +
    "test = uvs;\n" +
"}";


  private final String fragShaderSrc =
"#version 330 core\n" +

"out vec4 fragColor;\n" +

"in vec2 test;\n" +

"uniform sampler2D textureSampler;\n" +

"void main()\n" +
"{\n" +
  "vec4 color = texture(textureSampler,test);\n" +
  "fragColor = color;\n" +
"}";


  // The window handle
  private long window;

  public void run() {
    System.out.println("Hello LWJGL " + Version.getVersion() + "!");

    init();
    loop();

    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(window);
    glfwDestroyWindow(window);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  private void init() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW");

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
    glfwWindowHint(GLFW_SAMPLES, 3); // NECESSAIRE POUR ANTIALIASING

    // Create the window
    window = glfwCreateWindow(600, 300, "Hello World!", NULL, NULL);
    if ( window == NULL )
      throw new RuntimeException("Failed to create the GLFW window");

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
    });

    // changer l'echelle quand on redimensionne la fenetre
    glfwSetWindowRefreshCallback(window, (window) -> {

      try ( MemoryStack stack = stackPush() ) {
        IntBuffer pWidth = stack.mallocInt(1); // int*
        IntBuffer pHeight = stack.mallocInt(1); // int*
                                                // Get the window size
        glfwGetWindowSize(window, pWidth, pHeight);

        glViewport(0, 0, pWidth.get(0), pHeight.get(0));

        windowWidth = pWidth.get(0);
        windowHeight = pHeight.get(0);
      }
    });

    // Get the thread stack and push a new frame
    try ( MemoryStack stack = stackPush() ) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(window, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
          window,
          (vidmode.width() - pWidth.get(0)) / 2,
          (vidmode.height() - pHeight.get(0)) / 2
          );


        windowWidth = pWidth.get(0);
        windowHeight = pHeight.get(0);
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);
  }

private void loop() {
  // This line is critical for LWJGL's interoperation with GLFW's
  // OpenGL context, or any context that is managed externally.
  // LWJGL detects the context that is current in the current thread,
  // creates the GLCapabilities instance and makes the OpenGL
  // bindings available for use.
  GL.createCapabilities();

  // Set the clear color
  glClearColor(.97f, 0.1f, 0.36f, 0.0f);

  // Enable antialiasing
  glEnable(GL_MULTISAMPLE);

  // Enable transparency
  glEnable(GL_BLEND);
  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);



  final int vertShaderId = glCreateShader(GL20.GL_VERTEX_SHADER); 
  final int fragShaderId = glCreateShader(GL20.GL_FRAGMENT_SHADER); 

  glShaderSource(vertShaderId, vertShaderSrc);
  glShaderSource(fragShaderId, fragShaderSrc);

  glCompileShader(vertShaderId);

  if(GL20.glGetShaderi(vertShaderId, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE) {
    System.out.println(GL20.glGetShaderInfoLog(vertShaderId, 512));
    System.out.println("peut pas compiler le vertex shader");
  }
  else
    System.out.println("all good !");


  glCompileShader(fragShaderId);

  if(GL20.glGetShaderi(fragShaderId, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE) {
    System.out.println(GL20.glGetShaderInfoLog(fragShaderId, 512));
    System.out.println("peut pas compiler le fragment shader");
  }
  else
    System.out.println("all good !");


  final int programID = GL20.glCreateProgram();
  GL20.glAttachShader(programID, vertShaderId);
  GL20.glAttachShader(programID, fragShaderId);
  //bindAttributes();
  GL20.glLinkProgram(programID);
  GL20.glValidateProgram(programID);
  // getAllUniformLocations();


  glUseProgram(programID);


  // Generate and bind a Vertex Array
  final int vaoID = GL30.glGenVertexArrays();
  GL30.glBindVertexArray(vaoID);

  // The vertices of our Triangle
  float[] vertices = new float[]
  {
      -1.0f, +1.0f,    // Top-left coordinate
      -1.0f, -1.0f,    // Bottom-left coordinate
      +1.0f, -1.0f,    // Bottom-right coordinate

      +1.0f, +1.0f,    // Top-right
      -1.0f, +1.0f,    // Top-left
      +1.0f, -1.0f     // Bottom-right
  };

  // Create a FloatBuffer of vertices
  FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
  verticesBuffer.put(vertices).flip();

  // Create a Buffer Object and upload the vertices buffer
  final int vboID = glGenBuffers();
  glBindBuffer(GL_ARRAY_BUFFER, vboID);
  glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

  // Point the buffer at location 0, the location we set
  // inside the vertex shader. You can use any location
  // but the locations should match
  glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
  GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unloads the current VBO when done.



  float[] uvs = new float[]
  {
    +0.0f, +1.0f,
    +0.0f, +0.0f,
    +1.0f, +0.0f,
    +1.0f, +1.0f,
    +0.0f, +1.0f,
    +1.0f, +0.0f
  };

  // Create a FloatBuffer of uvs
  FloatBuffer uvsBuffer = BufferUtils.createFloatBuffer(uvs.length);
  uvsBuffer.put(uvs).flip();

  // Create a Buffer Object and upload the uvs buffer
  final int vboID2 = glGenBuffers();
  glBindBuffer(GL_ARRAY_BUFFER, vboID2);
  glBufferData(GL_ARRAY_BUFFER, uvsBuffer, GL_STATIC_DRAW);

  // Point the uvs buffer at location 1, the location we set
  // inside the vertex shader. You can use any location
  // but the locations should match
  glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
  GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unloads the current VBO when done.



  // la liste des positions des centres
  float[] centres = new float[]
  {
    -1.0f, -3.0f,
    -1.0f, -2.0f,
    -1.0f, -1.0f,
    -1.0f, +0.0f,
    -1.0f, +1.0f,
    -1.0f, +2.0f,
    -1.0f, +3.0f,
    -2.0f, -3.0f,
    -2.0f, -2.0f,
    -2.0f, -1.0f,
    -2.0f, +0.0f,
    -2.0f, +1.0f,
    -2.0f, +2.0f,
    -2.0f, +3.0f,
    -3.0f, -3.0f,
    -3.0f, -2.0f,
    -3.0f, -1.0f,
    -3.0f, +0.0f,
    -3.0f, +1.0f,
    -3.0f, +2.0f,
    -3.0f, +3.0f,
    +0.0f, -3.0f,
    +0.0f, -2.0f,
    +0.0f, -1.0f,
    +0.0f, +0.0f,
    +0.0f, +1.0f,
    +0.0f, +2.0f,
    +0.0f, +3.0f,
    +1.0f, -3.0f,
    +1.0f, -2.0f,
    +1.0f, -1.0f,
    +1.0f, +0.0f,
    +1.0f, +1.0f,
    +1.0f, +2.0f,
    +1.0f, +3.0f,
    +2.0f, -3.0f,
    +2.0f, -2.0f,
    +2.0f, -1.0f,
    +2.0f, +0.0f,
    +2.0f, +1.0f,
    +2.0f, +2.0f,
    +2.0f, +3.0f,
    +3.0f, -3.0f,
    +3.0f, -2.0f,
    +3.0f, -1.0f,
    +3.0f, +0.0f,
    +3.0f, +1.0f,
    +3.0f, +2.0f,
    +3.0f, +3.0f,
  };

  // Create a FloatBuffer of centres
  FloatBuffer centresBuffer = BufferUtils.createFloatBuffer(centres.length);
  centresBuffer.put(centres).flip();

  // Create a Buffer Object and upload the centres buffer
  final int vboID3 = glGenBuffers();
  glBindBuffer(GL_ARRAY_BUFFER, vboID3);
  glBufferData(GL_ARRAY_BUFFER, centresBuffer, GL_STATIC_DRAW);

  // Point the centres buffer at location 2, the location we set
  // inside the vertex shader. You can use any location
  // but the locations should match
  glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
  GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unloads the current VBO when done.




  GL30.glBindVertexArray(0);


  loadTexture();


  int uniformTimeLocation = GL20.glGetUniformLocation(programID, "time");
  int uniformWidthLocation = GL20.glGetUniformLocation(programID, "windowWidth");
  int uniformHeightLocation = GL20.glGetUniformLocation(programID, "windowHeight");
  float time = 0.0f;


  // Run the rendering loop until the user has attempted to close
  // the window or has pressed the ESCAPE key.
  while ( !glfwWindowShouldClose(window) ) {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


    time += 0.01f;
    GL20.glUniform1f(uniformTimeLocation, time);
    GL20.glUniform1f(uniformWidthLocation, windowWidth);
    GL20.glUniform1f(uniformHeightLocation, windowHeight);


    // Bind the vertex array and enable our location
    GL30.glBindVertexArray(vaoID);
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glEnableVertexAttribArray(2);

    GL40.glVertexAttribDivisor(0, 0); // réutilisation des vertices
    GL40.glVertexAttribDivisor(1, 0); // réutilisation des uvs
    GL40.glVertexAttribDivisor(2, 1); // changer les centres

    // Draw a triangle of 3 vertices
    //glDrawArrays(GL_TRIANGLES, 0, 6);
    // dessiner plusieurs instances de 2 triangles
    GL40.glDrawArraysInstanced(GL_TRIANGLES, 0, 6, centres.length / 2);

    // Disable our location
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    GL30.glBindVertexArray(0);


    glfwSwapBuffers(window); // swap the color buffers

    // Poll for window events. The key callback above will only be
    // invoked during this call.
    glfwPollEvents();




    // // modifier le premier centre
    // glBindBuffer(GL_ARRAY_BUFFER, vboID3);
    // centres[0] = (float) Math.sin(time);
    // centres[3] = (float) Math.cos(time);
    // //centresBuffer = BufferUtils.createFloatBuffer(centres.length);
    // centresBuffer.put(centres).flip();
    // glBufferData(GL_ARRAY_BUFFER, centresBuffer, GL_STATIC_DRAW);
    // glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unloads the current VBO when done.
  }
}

public static void main(String[] args) {
  new HelloWorld().run();
}


private final static String resourceName = "texture.png";
private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();

public static int loadTexture(){
  int width;
  int height;
  ByteBuffer buffer;


  try (MemoryStack stack = MemoryStack.stackPush()){
    IntBuffer w = stack.mallocInt(1);
    IntBuffer h = stack.mallocInt(1);
    IntBuffer channels = stack.mallocInt(1);

    File file = new File("res/"+resourceName);
    String filePath = file.getAbsolutePath();
    buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
    if(buffer ==null) {
      throw new Exception("Can't load file "+resourceName+" "+STBImage.stbi_failure_reason());
    }
    width = w.get();
    height = h.get();

    int id = GL11.glGenTextures();
    idMap.put(resourceName, id);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

    // filtrage linéaire pour réduire les artifacts
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
    STBImage.stbi_image_free(buffer);
    return id;
  } catch(Exception e) {
    e.printStackTrace();
  }
  return 0;
}

}
