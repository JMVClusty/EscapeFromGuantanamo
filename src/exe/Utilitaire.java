package exe;

import java.util.*;
import mapPrison.PiecePrison;

import personnage.*;
import personnage.Hero.Actions;


public class Utilitaire {
	
	Scanner lire = new Scanner(System.in);
	private	Boolean finCombat = false;
	


/****************************************Constructeurs*********************************************************/
	public Utilitaire() {
		super();
		// TODO Auto-generated constructor stub
	}

/****************************************************************************************************************/

	public void setFinCombat(Boolean finCombat) {
		this.finCombat = finCombat;
	}
/***********************Méthodes d'affichages des Actions ou desinations possible sur la console*********************/
	public  Actions affiche(Actions[] listechoix) throws Exception { // affiche les choix d'actions possible et recup la reponse du joueur
		boolean saisiValide = false;
		System.out.println("Actions:");
		for( Actions a:listechoix) {
			System.out.println("\t"+a.ordinal()+"- "+a.toString());
		}
		
			int reponse = lire.nextInt();
			//vérification de la saisi
			for(int i =0;i<listechoix.length;i++) {
				if (Actions.values()[reponse] == listechoix[i]) {
					saisiValide = true;
				}
			}
			if (saisiValide == false) {throw new Exception();}
			return Actions.values()[reponse];
			
	}
	
	public int affiche(String Actions)  { // affiche les choix d'actions possible et recup la reponse du joueur		
		System.out.println(Actions);
		int reponse = lire.nextInt();
		return reponse;
	}

	public String affiche(Map<String, PiecePrison> destinationPossible) { // affiche les destinations sur la console et
		// retourne la sélection
		String reponse = "";
		if (destinationPossible.size() != 1) {
			System.out.println("Se Déplacer vers:");
			for (Map.Entry<String, PiecePrison> entry : destinationPossible.entrySet()) {
				System.out.println("\t" + entry.getKey() +" ("+(destinationPossible.get(entry.getKey()).getType())+")");
			}
			lire.nextLine();
			reponse = lire.nextLine();
		}
		
		return reponse;
	}
	
/**********************************************************************************************************************/
	
/*********************************************************************************************************************
							 * Parcours de la prison et de toutes les ramifications
							 * Parcours d'un arbre par récursivité
*********************************************************************************************************************/

	public Map<String, PiecePrison> analyseDestinationPossible(Map<String, PiecePrison> carte,
			PiecePrison localisation) {
		Map<String, PiecePrison> brancheCible = carte; // variable qui va renvoyer la branche ou se trouve le Hero
		Map<String, PiecePrison> destinationPossible; // variable qui renvoie les desinations de la piece courante
		PiecePrison pieceAnterieur = null;
		PiecePrison pieceSuivante = null;
		PiecePrison pieceNord = null;
		PiecePrison pieceSud = null;
		Iterator<Map.Entry<String, PiecePrison>> it = carte.entrySet().iterator(); // Objet pour parcourir la carte
		Map.Entry<String, PiecePrison> temp = it.next(); // Objet qui va recupérer la pair clé,valeur de la
															// piececourante

		while (it.hasNext()) { // Parcours

			/*********************************************************
			 * Cas ou le héro est sur une branche annexe
			 **********************************************************/
			if (!carte.containsValue(localisation)) {
				if (temp.getValue().getBranchNord() != null
						&& (temp.getValue().getBranchNord().containsValue(localisation))) {
					brancheCible = temp.getValue().getBranchNord();
					destinationPossible = analyseDestinationPossible(brancheCible, localisation);// récursivité
					return destinationPossible;
				}

				if (temp.getValue().getBranchSud() != null
						&& temp.getValue().getBranchSud().containsValue(localisation)) {
					brancheCible = temp.getValue().getBranchSud();
					destinationPossible = analyseDestinationPossible(brancheCible, localisation);// récursivité

					return destinationPossible;
				}
			}

			/********************************************************************************************************/

			if (!temp.getValue().equals(localisation)) { //si on ne se trouve pas sur la pièce du héro
				pieceAnterieur = temp.getValue();
				temp = it.next();						//On itère
			}

			if (temp.getValue().equals(localisation)) { //On est sur la pièce ou se trouve le hero 

				if (localisation.getBranchSud() != null) {
					Iterator<Map.Entry<String, PiecePrison>> iteratorBrancheSud = localisation.getBranchSud().entrySet()
							.iterator();
					iteratorBrancheSud.next();
					pieceSud = iteratorBrancheSud.next().getValue();
				}
				if (localisation.getBranchNord() != null) {
					Iterator<Map.Entry<String, PiecePrison>> iteratorBrancheNord = localisation.getBranchNord()
							.entrySet().iterator();
					iteratorBrancheNord.next();
					pieceNord = iteratorBrancheNord.next().getValue();
				}
			
				destinationPossible = new LinkedHashMap<>();	// On récupère les pièces adjacentes dans cette Map
				if (it.hasNext()) {
					pieceSuivante = it.next().getValue();
				}
				
				if (pieceSuivante != null&&(pieceNord != null||pieceSud != null)) {
					destinationPossible.put("1-Piece Est", pieceSuivante);
				}else if(pieceSuivante != null) {
					destinationPossible.put("1-Piece Suivante", pieceSuivante);
				}
				if (pieceAnterieur != null&&(pieceNord != null||pieceSud != null)) {
					destinationPossible.put("2-Piece Ouest", pieceAnterieur);
				}else if(pieceAnterieur != null) {
					destinationPossible.put("2-Piece Précedente", pieceAnterieur);
				}
				if (pieceNord != null) {
					destinationPossible.put("3-Piece Nord", pieceNord);
				}
				if (pieceSud != null) {
					destinationPossible.put("4-Piece Sud", pieceSud);
				}
				return destinationPossible;
			}
		}
		return brancheCible;
	}

	/**************************************************************************************************/

	public int lancerDes(int face) {		
		Random de = new Random();
		return de.nextInt(face);
	}

	public Boolean combat(Hero hero, Creature adversaire) {
		Boolean IsAlive = true;
		hero.setEnCombat(true);
		
		
		do {
			int HeroInitiative =this.lancerDes(101);
			if(HeroInitiative>40) { // si le jet d'initiative est réussi on choisit l'action
				System.out.println("Que voulez-vous faire?");
				try {
					Actions choixCombat=this.affiche( new Actions[] {Actions.ATTAQUER,Actions.FUIR,Actions.UTILISER});
					hero.faire(this, choixCombat,null);
					if(choixCombat == Actions.FUIR && finCombat ==true){
						hero.setEnCombat(false);
						return IsAlive ;//fuite fin de Combat
					} 
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("***************\nErreur de saisi.\n*************\n");
					continue;	
				}
				adversaire.setPointDeVie(adversaire.getPointDeVie()-hero.infligeDegat());
				if (finCombat=coupFinal(hero,adversaire)==true) {
					hero.setEnCombat(false);
					break;
				}
				
				adversaire.attaque(this.lancerDes(11));
				System.out.print("l'adversaire répond et vous inflige  "+ adversaire.infligeDegat() +" point de dégats");
				hero.setPointDeVie(hero.getPointDeVie()-adversaire.infligeDegat());
				if (finCombat=coupFinal(hero,adversaire)==true) {
					hero.setEnCombat(false);
					break;
				}
				System.out.println("\t\t\t\t\t\tPoint de Vie: "+hero.getPointDeVie()+"\n");				
			}else { // Si le jet d'initiative est raté, l'adversaire attaque
				adversaire.attaque(this.lancerDes(11));
				System.out.print("l'adversaire attaque et vous inflige  "+ adversaire.infligeDegat() +" point de dégats");
				hero.setPointDeVie(hero.getPointDeVie()- adversaire.infligeDegat());
				if (finCombat=coupFinal(hero,adversaire)==true) {
					hero.setEnCombat(false);
					break;
				}
				System.out.println("\t\t\t\t\tPoint de Vie: "+hero.getPointDeVie()+"\n");
				System.out.println("Que voulez-vous faire?");
				try {
					Actions choixCombat=this.affiche( new Actions[] {Actions.ATTAQUER,Actions.FUIR,Actions.UTILISER});
					hero.faire(this, choixCombat,null);
					if(choixCombat == Actions.FUIR && finCombat ==true){
						hero.setEnCombat(false);
						return IsAlive ;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("***************\nErreur de saisi.\n*************\n");
					continue;
				}	
				adversaire.setPointDeVie(adversaire.getPointDeVie()-hero.infligeDegat());
				if (finCombat=coupFinal(hero,adversaire)==true) {
					hero.setEnCombat(false);
					break;
				}
				
			}

		}while(finCombat==false);
		hero.setEnCombat(false);
		String issuConfrontation= adversaire.getPointDeVie()>hero.getPointDeVie() ? "\nVous tombez inconscient.":"le "+adversaire.getType()+ " s'éffondre inconscient...La voie est libre\n";
		IsAlive=adversaire.getPointDeVie()>hero.getPointDeVie() ? false:true;
		System.out.println(issuConfrontation);
		return IsAlive;
		
	}
	
	private Boolean coupFinal(Hero hero,Creature adversaire) {
		
		if (hero.getPointDeVie()<=0) {
			hero.setPointDeVie(0);
			return true;
		}else if(adversaire.getPointDeVie()<=0){
			adversaire.setPointDeVie(0);
			return true;
		}
		return false;
		
	}
	

}

