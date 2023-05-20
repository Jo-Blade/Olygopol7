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
    new Popup("Chance", CarteChanceTire.getMessage()).afficher();
	}

  @Override
  public CaseGraphique getCaseGraphique() {
    return caseG;
  }

}
