package personnage;

public class AssistantSocial extends Creature{

	public AssistantSocial() {
		super();
		this.type=TypeCreature.ASSISTANTSOCIAL;
		setPointDeVie(this.type.getPointDeVie());
	}

}
