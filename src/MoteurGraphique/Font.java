package MoteurGraphique;
/** Créer une texture qui contient tous les caractère
 * d’une police de caractères.
 * @author : pisento
 **/
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.nio.*;
import org.lwjgl.system.MemoryUtil;


public class Font extends Texture {

  /** La largeur de la texture générée.*/
  public int textureWidth;

  /** La hauteur de la texture générée.*/
  public int textureHeight;

  /** L’image avec tous les caractères.*/
  private BufferedImage image;

  // --- Définir un glyph pour dessiner des caractères
  private Map<Character, Glyph> glyphs = new HashMap<>(); // associe a chaque lettre son glyph
                                                          // (= position dans la texture)

  /** Classe qui représente les coordonnées
   * d’un caractère dans la texture.*/
  public class Glyph {

    /** Largeur du dessin du caractère.*/
    public final int width;
    /** Hauteur du dessin du caractère.*/
    public final int height;
    /** Coordonnée x du dessin du caractère.*/
    public final int x;
    /** Coordonnée y du dessin du caractère.*/
    public final int y;

    /** Construire un glyph par ses caractéristiques.*/
    public Glyph(int width, int height, int x, int y) {
      this.width = width;
      this.height = height;
      this.x = x;
      this.y = y;
    }

  }

  /** Récupérer le glyph du caractère.
   * @param c caractère demandé
   */
  public Glyph getCaractere(char c) {
    return glyphs.get(c);
  }


  /** Créer une nouvelle texture avec
   * le nom d’une image dans le dossier res. */
  public Font(java.awt.Font font) {
    super();

    int imageWidth = 0;
    int imageHeight = 0;

    for (int i = 32; i < 256; i++) {
      if (i == 127) {
        continue;
      }
      char c = (char) i;
      BufferedImage ch = createCharImage(font, c);

      imageWidth += ch.getWidth();
      imageHeight = Math.max(imageHeight, ch.getHeight());
    }

    // --- Creer la texture ---
    image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();

    // --- Dessiner chaque lettre dans la texture ---
    int x = 0;

    /* Create image for the standard chars, again we omit ASCII 0 to 31
     * because they are just control codes */
    for (int i = 32; i < 256; i++) {
      if (i == 127) {
        /* ASCII 127 is the DEL control code, so we can skip it */
        continue;
      }
      char c = (char) i;
      BufferedImage charImage = createCharImage(font, c);
      if (charImage == null) {
        /* If char image is null that font does not contain the char */
        continue;
      }

      int charWidth = charImage.getWidth();
      int charHeight = charImage.getHeight();

      /* Create glyph and draw char on image */
      Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight);
      g.drawImage(charImage, x, 0, null);
      x += ch.width;
      glyphs.put(c, ch);
    }

    /* Get charWidth and charHeight of image */
    textureWidth = image.getWidth();
    textureHeight = image.getHeight();
  }


  @Override
  final protected void creerTexture() {
    // --- Créer la texture opengl ---
    /* Flip image Horizontal to get the origin to bottom left */
    AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
    transform.translate(0, -image.getHeight());
    AffineTransformOp operation = new AffineTransformOp(transform,
        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    image = operation.filter(image, null);

    /* Get pixel data of image */
    int[] pixels = new int[textureWidth * textureHeight];
    image.getRGB(0, 0, textureWidth, textureHeight, pixels, 0, textureWidth);

    /* Put pixel data into a ByteBuffer */
    ByteBuffer buffer = MemoryUtil.memAlloc(textureWidth * textureHeight * 4);
    for (int i = 0; i < textureHeight; i++) {
      for (int j = 0; j < textureWidth; j++) {
        /* Pixel as RGBA: 0xAARRGGBB */
        int pixel = pixels[i * textureWidth + j];
        /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
        buffer.put((byte) ((pixel >> 16) & 0xFF));
        /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
        buffer.put((byte) ((pixel >> 8) & 0xFF));
        /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
        buffer.put((byte) (pixel & 0xFF));
        /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
        buffer.put((byte) ((pixel >> 24) & 0xFF));
      }
    }
    /* Do not forget to flip the buffer! */
    buffer.flip();

    uploadImageBuffer(buffer, textureWidth, textureHeight);
  }


  /**
   * Creates a char image from specified AWT font and char.
   *
   * @param font      The AWT font
   * @param c         The char
   * @param antiAlias Wheter the char should be antialiased or not
   *
   * @return Char image
   */
  private static BufferedImage createCharImage(java.awt.Font font, char c) {
    /* Creating temporary image to extract character size */
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();

    // Antialiased font
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g.setFont(font);
    FontMetrics metrics = g.getFontMetrics();
    g.dispose();

    /* Get char charWidth and charHeight */
    int charWidth = metrics.charWidth(c);
    int charHeight = metrics.getHeight();

    /* Check if charWidth is 0 */
    if (charWidth == 0) {
      return null;
    }

    /* Create image for holding the char */
    image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
    g = image.createGraphics();

    // Antialiased font
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g.setFont(font);
    g.setPaint(java.awt.Color.WHITE);
    g.drawString(String.valueOf(c), 0, metrics.getAscent());
    g.dispose();
    return image;
  }



}
