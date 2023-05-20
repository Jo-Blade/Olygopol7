package logiqueMonopoly;
import java.util.Random;

public class CarteChance implements Carte{

  private Joueur j;

  public CarteChance(Joueur j){
    this.j = j;
  } 

  public String[] getMessage() throws BanquerouteException {
    return(executer(j));
  }

  public String[] executer(Joueur j) throws BanquerouteException {
    Random random = new Random();
    //Génération d'un nombre aléatoire entre 0 et 10
    int n_carte = random.nextInt(10);

    switch (n_carte) {
      case 0:
        String[] mes0 = {"Vous arrivez en retard", "en cours, rendez vous", "a la case prison"};
        return mes0;
      case 1:
        String[] mes1 = {"Vous n'avez pas revisé", "le partiel d'integration.", "Vous decidez de soudoyer", "monsieur Cots. Payez 100k$"};
        j.debiter(100.0);
        return mes1;
      case 2:
        String[] mes2 = {"Vous avez credité votre", "compte Lydia hier, vous", "offrez un cafe au foy", "à tout vos collegues.", "Payez 50k$"};
        j.debiter(50.0);
        return mes2;
      case 3:
        String[] mes3 = {"Vous avez gagné le prix", "du plus gros fayot", "de Vinci Energie !", "Vous recevez 100k$"};
        j.crediter(100.0);
        return mes3;
      case 4:
        String[] mes4 = {"L'ENSEEIHT devient centrale", "toulouse ! Payez 1000k$", "pour l'inscription en plus"};
        j.debiter(1000.0);
        return mes4;
      case 5:
         String[] mes5 = {"Vous avez cassé une fenetre", "avec votre crane au WEI.", "Perdez 180k$ de caution"};
         j.debiter(180.0);
         return mes5;
      case 6:
        String[] mes6 = {"Votre mauvaise algo de max", "itération a fait cramer", "un ordinateur. au lancement.", "Payez 300k$ de réparations"};
        j.debiter(300.0);
        return mes6;
      case 7:
        String[] mes7 = {"Vous faites partie de la JE.", "Recevez 500 pour votre mission,", "mais lavez ce blouzon qui pue !"};
        j.crediter(500.0);
        return mes7;
      case 8:
        String[] mes8 = {"Vous aidez les autres grâce", "à chat GPT, mais faites", "quand même payer.", "Recevez 300k$, voleur !"};
        j.crediter(300.0);
        return mes8;
      case 9:
        String[] mes9 = {"Vous vous faites rembourser vos", "dernières tournées au Danu.", "Recevez 300k$"};
        j.crediter(300.0);
        return mes9;
      default:
        String[] mesD = {"David Guetta adore vos presta", "de DJ aux soirées foy !", "Recevez 300k$"};
        j.crediter(300.0);
        return mesD;

    }
  }

}
