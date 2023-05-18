package logiqueMonopoly;
import interfacegraphique.*;

public class CaseChance implements CaseFonctionnelle {

	/** Numéro de la case.*/
	private int position;
	
	public CaseChance(int position) {
		 this.position = position;
	}

  @Override
	public void executer(Joueur j) throws BanquerouteException {
		CarteChance CarteChanceTire = new CarteChance(j);
		System.out.println(CarteChanceTire.getMessage());
	}

  @Override
  public CaseGraphique getCaseGraphique() {
    return null;
  }

}
