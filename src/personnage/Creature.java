package personnage;

import java.util.HashMap;
import java.util.Map;

import exe.Utilitaire;
import mapPrison.*;

public class Creature {
	protected PiecePrison localisation;
	protected Map<String,Objet> items; // inventaire d'objets
	protected int pointDeVie;
	protected int degat;

	public Creature() {
		super();
		items=new HashMap<String,Objet>();

	}

	public Map<String,Objet> getItem() {
		return items;
	}

	public void ajout(Objet item) {
	
		if(!items.containsKey(item.getNom())) { //Si l'inventaire ne contient pas l'objet
			items.put(item.getNom(),item);		// On l'ajoute
		}else {
			int i=items.get(item.getNom()).getQuantite(); // Si le heros a deja un objet de ce type
			items.get(item.getNom()).setQuantite(i++);	// on ajoute à la propriété quantitée 
		}
		
	}
	public void retirer(Objet item) { 
		int i=items.get(item.getNom()).getQuantite();
		if(i>1) { //si il y a plus d'un objet de ce type
			items.get(item.getNom()).setQuantite(i--); //on enleve 1 à la quantité
		}else items.remove(item.getNom()); //Sinon on supprime cette valeur
	}
	


	TypeCreature type;
	public TypeCreature getType() {
		return type;
	}

	public void setType(TypeCreature type) {
		this.type = type;
	}


	public PiecePrison getLocalisation() {
		return localisation;
	}

	public void setLocalisation(PiecePrison localisation) {
		this.localisation = localisation;
	}

	public void interagir() {
	}

	public int infligeDegat() {
		return degat;
	}
	
	public void attaque(int forceCoup) {
		this.degat=forceCoup;
	}
	
	public int getPointDeVie() {
		return pointDeVie;
	}

	public void setPointDeVie(int pointDeVie) {
		this.pointDeVie = pointDeVie;
	}
	

}
