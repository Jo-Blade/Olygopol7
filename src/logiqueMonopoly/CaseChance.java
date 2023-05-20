package logiqueMonopoly;
import interfacegraphique.*;

public class CaseChance implements CaseFonctionnelle {

  private final CaseChanceGraphique caseG;

	public CaseChance(CaseChanceGraphique caseG) {
    this.caseG = caseG;
	}

  @Override
	public void executer(Joueur j) throws BanquerouteException {
    caseG.animation();
		CarteChance CarteChanceTire = new CarteChance(j);
		System.out.println(CarteChanceTire.getMessage());

    String[] message = {"Votre mauvais algorithme de", "max itération a fait cramer", "un ordinateur au lancement.", "Payez 300k$ de réparations."};
    new Popup("Chance", message).afficher();
	}

  @Override
  public CaseGraphique getCaseGraphique() {
    return caseG;
  }

}
