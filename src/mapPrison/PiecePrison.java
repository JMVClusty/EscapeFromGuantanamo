package mapPrison;
import java.util.*;
import java.util.LinkedHashMap;
import exe.Utilitaire;
import personnage.*;

public class PiecePrison {
	/*************************************Attribut des Pièces ***********************************/
	private LinkedHashMap<String, PiecePrison> branchSud;
	private Creature creature; // il peut y avoir des personnes
	private String namePiece, narration;
	private TypePiece type;
	private Objet item;
	private LinkedHashMap<String, PiecePrison> branchNord;
	private boolean pieceVisite=false;
	private boolean locked = false;
	
	
	/***********************************************************************************************/


	/**************************************Constructeurs des Pièces********************************/
	public PiecePrison() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public PiecePrison(TypePiece type, String nomPiece ) { //Initialisation des pièces en fonction des types de pièces passées dans l'appel
		super();
		Utilitaire outil = new Utilitaire();
		this.type = type;
		this.namePiece=nomPiece;
		switch (this.type) {
			case cellule:
				//possibilité prisonnier 80%
				if(nomPiece!="celluleDepart"){
					int creationPrisonnier = outil.lancerDes(101);
					if (creationPrisonnier<=80) {
						creature = new Prisonnier();
						creature.setLocalisation(this);
						creature.ajout(new Objet("sandwich"));
					}
				}
				break;
			case bureau:
				//possibilité 80% assistant social
				int creationAssistantsocial = outil.lancerDes(101);
				if (creationAssistantsocial<=80) {
					creature = new AssistantSocial();
					creature.setLocalisation(this);
					creature.ajout(new Objet("sandwich"));
					}
				break;
				
				
			case toilette:
				//15% assistant social
				int creationAssistantsocial2 = outil.lancerDes(101);
				if (creationAssistantsocial2<=15) {
					creature = new AssistantSocial();
					creature.setLocalisation(this);
					creature.ajout(new Objet("sandwich"));
					}
				break;
			case CentralSecurite: 
				//100% un gardien
				creature = new Gardiens();
				creature.setLocalisation(this);
				creature.ajout(new Objet("matraque"));
				break;
			case douches:
				//20%prisonnier
			case couloir:
				//Génération aléatoire d'un gardien
				int creationgardien = outil.lancerDes(101);
				if (creationgardien<=30) {
					creature = new Gardiens();
					creature.setLocalisation(this);
					int creationArme = outil.lancerDes(101);
					if (creationArme>80) {
						creature.ajout(new Objet("matraque"));
					}
				}
				break;
			case cours:
				// 50% chiens
			case refectoire:
				//50% prisonnier
			default: 
				break;
		}		
				
	}
	/***********************************************************************************************/
	
	/****************************************Accesseurs********************************/
	
	public TypePiece getType() {
		return type;
	}


	public void setType(TypePiece type) {
		this.type = type;
	}


	public Objet getItem() {
		return item;
	}


	public void setItem(Objet item) {
		this.item = item;
	}


	public LinkedHashMap<String,PiecePrison>getBranchNord() {
		return branchNord;
	}


	public void setBranchNord(List<PiecePrison> listePieceNord) {
		this.branchNord=new LinkedHashMap<String, PiecePrison>();
		for(PiecePrison piece :listePieceNord) {			
			this.branchNord.put(piece.getNamePiece(), piece);
		}
	
	}


	public LinkedHashMap<String,PiecePrison> getBranchSud() {
		return branchSud;
	}


	public void setBranchSud(List<PiecePrison> listePieceSud) {
		this.branchSud=new LinkedHashMap<String, PiecePrison>();
		for(PiecePrison piece :listePieceSud) {			
			this.branchSud.put(piece.getNamePiece(), piece);
		}
	}


	public Creature getCreature() {
		return creature;
	}


	public void setCreature(Creature creature) {
		this.creature = creature;
	}


	public String getNamePiece() {
		return namePiece;
	}


	public void setNamePiece(String namePiece) {
		this.namePiece = namePiece;
	}
	
	
	public boolean aeteVisite() {
		return pieceVisite;
	}


	public void setPieceVisite(boolean pieceVisite) {
		this.pieceVisite = pieceVisite;
	}
	
	public boolean isLocked() {
		return locked;
	}


	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}

	



	
	
