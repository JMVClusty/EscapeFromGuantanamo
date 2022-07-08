package exe;


import mapPrison.Prison;
import personnage.Hero.Actions;
import personnage.*;

public class Appli {
		
	public static void main(String[] args) {
		System.out.println("Bienvenue à Guantanamo.\nPour controler le fugitif et réussir à vous échapper, "
				+ "il faudra taper le chiffre ou le caractère correspondant à l'action proposée.\n");
				
		Utilitaire outil = new Utilitaire();
		Prison guantanamo = new Prison(); // instanciation Prison
		Hero hero1 = new Hero(guantanamo.getCartePrison()); // on créé le héro, on lui passe la carte de prison en paramètre
	
		
		
		while(!(hero1.getLocalisation().equals(guantanamo.getSalleDeFin()))){// tant que le héro n'est pas à la salle de fin
			
			Actions actionHero=null;

		/*******************************Evènement entrer dans une pièce******************************/
			
		 	//vérifier si elle est sombre ou lumineuse...non implémenté
		
			if(hero1.getLocalisation().aeteVisite()==true){	// vérifier si on est deja passé dans cette pièce
				if (hero1.getLocalisation().getCreature() != null) {// on verifie si il y a une créature
					

						if(hero1.getLocalisation().getCreature().getPointDeVie() > 0){
							if (outil.combat(hero1, hero1.getLocalisation().getCreature())==false) break;
							// On lance le combat et on vérifie l'issue de la confrontation	
							//false => le héro n'a plus de vie, on sort de la boucle
						}
				}
				try {
					actionHero = outil.affiche( new Actions[] {Actions.SE_DEPLACER,Actions.OBSERVER,Actions.UTILISER});
					hero1.faire(outil, actionHero,null);
				} catch (Exception e) {
					System.out.println("***************\nErreur de saisi.\n*************\n");
					continue;
				}
			} else if (hero1.getLocalisation().aeteVisite() == false) { //Première fois dans la pièce
				// on affiche la narration de la piece ... non implémenté
				hero1.getLocalisation().setPieceVisite(true);
				if (hero1.getLocalisation().getCreature() != null) {// on vérifie si il y a une créature
					System.out.println("Cette pièce contient un(e) " + hero1.getLocalisation().getCreature().getType()+
							(hero1.getLocalisation().getCreature().getPointDeVie() > 0? "\n":" inconscient.\n"));
					if(hero1.getLocalisation().getCreature().getPointDeVie() > 0){
						if (outil.combat(hero1, hero1.getLocalisation().getCreature())==false) break;
						// On lance le combat et on vérifie l'issue de la confrontation	
						//false => le héro n'a plus de vie, on sort de la boucle
					}
				}
				try {
					actionHero = outil.affiche( new Actions[] {Actions.SE_DEPLACER,Actions.OBSERVER,Actions.UTILISER});
					hero1.faire(outil, actionHero,null);
				} catch (Exception e) {
					System.out.println("***************\nErreur de saisi.\n*************\n");
					
					continue;
					
				}
			}
		}
		if(hero1.getPointDeVie()==0) {
			System.out.println("Des images vagues, des mains qui vous menottent..un cliquetis de clé, le grincement d'une porte se referme.");
		}else {
			System.out.println("Vous etes parvenu à vous enfuir!.\n*************\\O/*FIN*\\O/****************");
		}
	}
}
