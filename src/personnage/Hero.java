package personnage;

import java.util.*;

import exe.Utilitaire;

import mapPrison.*;


public class Hero extends Creature {
	
	private PiecePrison dernierePieceVisite;
	private Map<String,PiecePrison> carte;
	public enum Actions {SE_DEPLACER,ATTAQUER,FUIR,INTERAGIR,OBSERVER,UTILISER,PRENDRE} //Actions possible du Hero
	private Boolean enCombat=false;
	


	public PiecePrison getDernierePieceVisite() {
		return dernierePieceVisite;
	}

	public Hero( Map<String,PiecePrison> branchePrincipale) { // initialisation du héro
		super();
		this.type = TypeCreature.HERO;
		this.carte = branchePrincipale;
		dernierePieceVisite=localisation = carte.get("celluleDepart");
		localisation.setPieceVisite(true);
		setPointDeVie(type.getPointDeVie());
	}
	public void setEnCombat(Boolean enCombat) {
		this.enCombat = enCombat;
	}
	
	public void faire(Utilitaire outil, Actions choix, Objet o) throws Exception {
		Scanner lire = new Scanner(System.in);
		switch (choix) {
			case OBSERVER:
				localisation.setPieceVisite(true);
				System.out.println("Vous êtes dans "+localisation.getNamePiece());
				if (localisation.getCreature()==null && localisation.getItem()==null) {
					System.out.println("Un recoin de la prison comme un autre\n");
				}
				if(localisation.getCreature()!=null) {
					System.out.println("Vous apercevez "+localisation.getCreature().getType()+(localisation.getCreature().getPointDeVie() > 0? "":" inconscient.\n"));
					if (!localisation.getCreature().getItem().isEmpty()) {
						System.out.print("Il a en sa possession: ");
						for (Objet k:localisation.getCreature().getItem().values()){
							if (k!=null) {
								System.out.println(k.toString());
								System.out.println("Voulez-vous le prendre? O/N");
								
								char reponse = lire.next().charAt(0);
								if (reponse=='O') {
									faire(null,Actions.PRENDRE,k);
								}else if(reponse!='O'||reponse!='N') throw new Exception();
							}
							
						}
						System.out.println(".");
					}
						
				}
				if(localisation.getItem()!=null) {
						System.out.println("vous distinguer "+localisation.getItem());
						System.out.println("Voulez vous prendre cette objet ?");
						try {
							Actions choixObservation = outil.affiche(new Actions[] {Actions.UTILISER,Actions.PRENDRE});
							faire(outil,choixObservation,localisation.getItem());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("***************\nErreur de saisi.\n**************\n");
						}
						
					}

				
				break;
			case SE_DEPLACER: 
				localisation.setPieceVisite(true);
				Map<String,PiecePrison>choixDestination =
						outil.analyseDestinationPossible(carte,localisation);	//analyse des choix possibles
				String reponse = outil.affiche(choixDestination);		//affichage et récupération des choix possibles
				seDeplacer(choixDestination,reponse);				//deplacement du héro
				System.out.println("Vous etes dans "+localisation.getNamePiece()+"\n");
				break;				
			case PRENDRE:
				prendre(o);
				break;
			case ATTAQUER:
				this.degat=outil.lancerDes(11);
				System.out.println("Vous infligez "+ degat +" point de dégats\n");
				break;
			case UTILISER:
				if (super.items.size() > 0) { // si Il y a des objets dans l'inventaire
					List<String> objetInventaire = new ArrayList<>();
					int nbreObjet=0;
					System.out.println("Quel objet voulez-vous utiliser:");
					for (Objet obj : super.items.values()) {
						nbreObjet++;
						System.out.println(nbreObjet+"- " + obj.toString());	//affiche les objets
						objetInventaire.add(nbreObjet+"- "+obj.toString());
					}
					int res= lire.nextInt();
					String key = objetInventaire.get(res-1).substring(objetInventaire.get(res-1).lastIndexOf(" ")+1);//récupère la clé de l'objet choisi
					utiliser(key,outil);
					

				} else
					System.out.println("Vous n'avez pas d'objet\n");

				break;
			case INTERAGIR:
				break;
			case FUIR:
				if(outil.lancerDes(100)>70) {
					System.out.println("Vous réussissez à fuire");
					outil.setFinCombat(true);
					this.faire(outil, Actions.SE_DEPLACER,null);
				}else System.out.println("Echec de la fuite");
					
				
				break;
		}
	}

	public void seDeplacer(Map<String, PiecePrison> destinationPossible, String choixJoueur) { // déplacement du héros sur la pièce choisi
		this.dernierePieceVisite = this.localisation;
		
		try {
			if (destinationPossible.size() == 1) {
				this.localisation = destinationPossible.values().iterator().next(); 
			} else if (destinationPossible.size() == 2) {
				if (choixJoueur.equals("1")) {
					this.localisation = Objects.requireNonNull(destinationPossible.get("1-Piece Suivante"));
				} else if (choixJoueur.equals("2")) {
					this.localisation = Objects.requireNonNull(destinationPossible.get("2-Piece Précedente"));
				}
			} else if (choixJoueur.equals("1")) {
				if(destinationPossible.get("1-Piece Est").isLocked()==false) {
					this.localisation = Objects.requireNonNull(destinationPossible.get("1-Piece Est"));
				}else System.out.println("La sortie est bloquée. Il me faut une clé.");
				
			} else if (choixJoueur.equals("2")) {
				this.localisation = Objects.requireNonNull(destinationPossible.get("2-Piece Ouest"));
			} else if (choixJoueur.equals("3")) {
				this.localisation = Objects.requireNonNull(destinationPossible.get("3-Piece Nord"));
			} else if (choixJoueur.equals("4")) {
				this.localisation = Objects.requireNonNull(destinationPossible.get("4-Piece Sud"));
			} else {
				System.out.println("Veuillez tapez un chiffre correspondant à la direction");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("****************\nERREUR DE SAISI\n****************");
		}
	}
	
	
	public void prendre(Objet objet){ // prendre un Objet sur un personnage ou une piece
		Objet copieItem = new Objet();
		copieItem=objet; // on recupère l'inventaire dans une variable
		System.out.println("Vous prenez "+copieItem.toString());
		this.ajout(copieItem); // On place dans l'inventaire du héro l'objet 
		if(this.localisation.getItem()!=null) {
			if(this.localisation.getItem().equals(objet)) {
				this.localisation.setItem(null); // On efface l'objet de la pièce
			}
		}
		if(this.localisation.getCreature()!=null) {
			if(this.localisation.getCreature().getItem().containsValue(objet)) {
				this.localisation.getCreature().retirer(objet);//On retire l'objet de la creature 
			}
		}
		

	}
	
	public void utiliser( String cleItem, Utilitaire outil  ) {
		
		switch(cleItem) {
		
			case "clé":
				if (localisation.getNamePiece() == "cours") {
					carte.get("fin").setLocked(false);
					System.out.println("La serrure s'actionne");
				}else System.out.println("La clé n'ouvre rien ici");
				break;
			case "sandwich":
				//récupere 50 pdv
				this.pointDeVie+=50;
				System.out.println("Vous reprenez des forces (+50pdv)");
				if (items.get("sandwich").getQuantite()==1) {
					super.items.remove("sandwich");
				}else {
					int i= items.get("sandwich").getQuantite();
					items.get("sandwich").setQuantite(i--);
				}
				
				break;
			case "matraque":
				//Ajout des degats aux attaques
				if(enCombat==true) {
					this.degat=outil.lancerDes(11)+10;
					System.out.println("Cette matraque inflige "+ degat +" point de dégats\n");
					System.out.println("Cette matraque n'est plus utilisable");
					this.retirer(items.get("matraque"));
				}else System.out.println("Aucun effet ici");
			
				break;
			case "steak":
				//les chiens detourne l'attention
				break;
		}

	}
	
}
