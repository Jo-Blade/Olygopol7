package moteurGraphique.vecteur;
/** Exception lancée si on a des arguments en paramètre de fonction
   de taille incorrecte.
 * @author : pisento
**/

public class IncompatibleTailleException extends RuntimeException {

  private static final long serialVersionUID = 3L;

public IncompatibleTailleException(String err) {
    super(err);
  }

}
