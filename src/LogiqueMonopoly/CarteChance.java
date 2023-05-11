package LogiqueMonopoly;
import java.util.Random;

public class CarteChance implements Carte{

    //Message associé à la carte chance
private String m;


public CarteChance(Joueur j){
    try{
        this.m = executer(j);
    }
    catch(BanquerouteException e){
        this.m = "Vous n'avez pas suffisament d'argent, vous avez perdu";
    }
} 

public String getMessage(){
    return(m);
}

public String executer(Joueur j) throws BanquerouteException {
    Random random = new Random();
    //Génération d'un nombre aléatoire entre 0 et 10
    int n_carte = random.nextInt(10);
    String message = "Chance";

    if (n_carte ==0) {
        message="Vous arrivez en retard en cours, rendez vous a la case prison";
    }
    if (n_carte==1) {
        message="Vous n'avez pas revisé le partiel d'integration. Vous decidez de soudoyer monsieur Cots. Vous perdez 100";
        j.debiter(100.0);
    }
    if (n_carte==2){
        message="Vous avez credité votre compte Lydia hier, vous offrez un cafe au foy à tout vos collegues. Payez 50.";
        j.debiter(50.0);
    }
    if (n_carte==3){
        message="Vous avez gagné le prix du plus gros fayot de Vinci Energie! Vous recever 100.";
        j.crediter(100.0);
    }
    if (n_carte==4){
        message="L'ENSEEIHT se transforme en centrale toulouse! Payez 1000 de frais d'inscriptions suplementaire";
        j.debiter(1000.0);
    }
    if (n_carte==5){
        message="C'est le retour du WEI et vous avez cassé une fenetre avec votre crane. Bye bye la caution. Vous perdez 180";
        j.debiter(180.0);
    }
    if (n_carte==6){
        message="Votre mauvaise algorithme de max itération à fait cramer un ordinateur au lancement. Les réparations vous coute 300.";
        j.debiter(300.0);
    }
    if (n_carte==7){
        message="Vous faite partie de l'hélite, la JE. Vos missions vous rapport 500 mais allez laver ce blouzon qui pue !";
        j.crediter(500.0);
    }
    if (n_carte==8){
        message="Vous aidez tout vos collèges grâce à chat GPT mais les faites quand même payer. Recevez 300, voleur !";
        j.crediter(300.0);
    }
    if (n_carte==9){
        message="Vos collèges vous rembourse les tournées de la soirées houleuse au Danu la semaine passée. Recevez 300.";
        j.crediter(300.0);
    }
    if (n_carte==10){
        message="David Guetta adore vos transition en tant que DJ au soirée foy et vous recrute. Recevez 300.";
        j.crediter(300.0);
    }
    return(message);

}

}