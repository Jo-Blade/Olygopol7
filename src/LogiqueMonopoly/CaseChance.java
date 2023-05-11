package LogiqueMonopoly;

public class CaseChance implements CaseFonctionnelle, CaseGraphique{

	/** Numéro de la case.*/
	private int position;
	
	public CaseChance(int position) {
		 this.position = position;
	}

	public void executer(Joueur j) throws BanquerouteException {
		CarteChance CarteChanceTire = new CarteChance(j);
		System.out.println(CarteChanceTire.getMessage());
	}

	public void afficher(StringBuffer buffer) {
		// C'est bon IDE de merde on va utiliser position ici
		int mabite = position;
	}

}
