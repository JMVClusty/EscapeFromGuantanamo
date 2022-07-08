package exe;


import mapPrison.Prison;
import personnage.Hero.Actions;
import personnage.*;

public class Appli {
		
	public static void main(String[] args) {
		System.out.println("Bienvenue � Guantanamo.\nPour controler le fugitif et r�ussir � vous �chapper, "
				+ "il faudra taper le chiffre ou le caract�re correspondant � l'action propos�e.\n");
				
		Utilitaire outil = new Utilitaire();
		Prison guantanamo = new Prison(); // instanciation Prison
		Hero hero1 = new Hero(guantanamo.getCartePrison()); // on cr�� le h�ro, on lui passe la carte de prison en param�tre
	
		
		
		while(!(hero1.getLocalisation().equals(guantanamo.getSalleDeFin()))){// tant que le h�ro n'est pas � la salle de fin
			
			Actions actionHero=null;

		/*******************************Ev�nement entrer dans une pi�ce******************************/
			
		 	//v�rifier si elle est sombre ou lumineuse...non impl�ment�
		
			if(hero1.getLocalisation().aeteVisite()==true){	// v�rifier si on est deja pass� dans cette pi�ce
				if (hero1.getLocalisation().getCreature() != null) {// on verifie si il y a une cr�ature
					

						if(hero1.getLocalisation().getCreature().getPointDeVie() > 0){
							if (outil.combat(hero1, hero1.getLocalisation().getCreature())==false) break;
							// On lance le combat et on v�rifie l'issue de la confrontation	
							//false => le h�ro n'a plus de vie, on sort de la boucle
						}
				}
				try {
					actionHero = outil.affiche( new Actions[] {Actions.SE_DEPLACER,Actions.OBSERVER,Actions.UTILISER});
					hero1.faire(outil, actionHero,null);
				} catch (Exception e) {
					System.out.println("***************\nErreur de saisi.\n*************\n");
					continue;
				}
			} else if (hero1.getLocalisation().aeteVisite() == false) { //Premi�re fois dans la pi�ce
				// on affiche la narration de la piece ... non impl�ment�
				hero1.getLocalisation().setPieceVisite(true);
				if (hero1.getLocalisation().getCreature() != null) {// on v�rifie si il y a une cr�ature
					System.out.println("Cette pi�ce contient un(e) " + hero1.getLocalisation().getCreature().getType()+
							(hero1.getLocalisation().getCreature().getPointDeVie() > 0? "\n":" inconscient.\n"));
					if(hero1.getLocalisation().getCreature().getPointDeVie() > 0){
						if (outil.combat(hero1, hero1.getLocalisation().getCreature())==false) break;
						// On lance le combat et on v�rifie l'issue de la confrontation	
						//false => le h�ro n'a plus de vie, on sort de la boucle
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
			System.out.println("Des images vagues, des mains qui vous menottent..un cliquetis de cl�, le grincement d'une porte se referme.");
		}else {
			System.out.println("Vous etes parvenu � vous enfuir!.\n*************\\O/*FIN*\\O/****************");
		}
	}
}
