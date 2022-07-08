package mapPrison;

public class Objet {
	private String nom;
	private int quantite;
	
	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Objet() {
		super();
	}

	public Objet(String nom) {
		setNom(nom);
		quantite=1;
	}
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public String toString() {
		String objet = new String();
		if (nom=="clé") {
			objet= "une clé";
		}else if(nom=="matraque") {
			objet=  "une matraque";
		}else if(nom=="steak") {
			objet= "un morceau de steak";
		}else if(nom=="sandwich") {
			objet="un sandwich";
		}
		return objet;
	}
	
}
