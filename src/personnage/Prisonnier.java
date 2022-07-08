package personnage;

public class Prisonnier extends Creature {

	public Prisonnier() {
		super();
		this.type =TypeCreature.PRISONNIER;
		setPointDeVie(this.type.getPointDeVie());
	}

}
