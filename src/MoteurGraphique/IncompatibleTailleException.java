/** Exception lancée si on a des arguments en paramètre de fonction
   de taille incorrecte.
 * @author : pisento
**/

public class IncompatibleTailleException extends RuntimeException {

  public IncompatibleTailleException(String err) {
    super(err);
  }

}
