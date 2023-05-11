import java.util.Random;

public class carteChance implements carte{

    //Message associé à la carte chance
private String m;


public carteChance(Joueur j){
    try{
        this.m=executer(j);
    }
    catch(BanquerouteException e){
        this.m="Vous n'avez pas suffisament d'argent, vous avez perdu";
    }
} 

public String getMessage(){
    return(m);
}

public String executer(Joueur j) throws BanquerouteException {
    Random random = new Random();
    //Génération d'un nombre aléatoire entre 0 et 5
    int n_carte = random.nextInt(6);
    String message = "Chance";

    if (n_carte ==0) {
        message="Vous arrivez en retard en cours, rendez vous a la case prison";
    }
    if (n_carte==1) {
        message="Vous n'avez pas revise le partiel d'integration. Vous decidez de soudoyer monsieur Cots. Vous perdez 100";
        j.debiter(100.0);
    }
    if (n_carte==2){
        message="Vous avez crediter votre compte lydia hier, vous offrez un cafe au foy à tout vos collegues. Payez 50.";
        j.debiter(50.0);
    }
    if (n_carte==3){
        message="Vous avez gagner le prix du plus gros fayot de  vinci energie! Vous recever 100.";
        j.crediter(100.0);
    }
    if (n_carte==4){
        message="l'ENSEEIHT se transforme en centrale toulouse! Payez 1000 de frais d'inscriptions suplementaire";
        j.debiter(1000.0);
    }
    if (n_carte==5){
        message="C'est le retourn du WEI est vous avez casser une fenetre avec votre crane. bye bye la caution. vous perdez 180";
        j.debiter(180.0);
    }
    

    return(message);

}

}