package personnage;

public class Gardiens extends Creature{
	

	public Gardiens() {
		super();
		this.type=TypeCreature.GARDIEN;
		setPointDeVie(this.type.getPointDeVie());
		
	}
	public String toString() {
		return "un gardien";
	}

}
