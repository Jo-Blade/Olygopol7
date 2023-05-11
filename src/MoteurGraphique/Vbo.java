package MoteurGraphique;
/** Interface d’un vertex buffer object.
 * @author : pisento
**/

public interface Vbo<T> {

  /** Add data to the vbo, but not upload it yet.
   * @param data the new data to be pushed
   */
  void push(T data);

  /** Upload the data to the vbo in the gpu.*/
  void uploadToGpu();

  /** Assign to a location in the current vertex array object.
   * @param location the location in the current vao
   */
  void setLocation(int location);

  /** Vider les données.*/
  void clear();
}
