package personnage;

public enum TypeCreature {
	GARDIEN(60),CHIEN(15),PRISONNIER(40),HERO(200),ASSISTANTSOCIAL(40);
	
	private int pointDeVie;
	private TypeCreature(int pointDeVie) {
		this.pointDeVie=pointDeVie;
	}
	public int getPointDeVie() {
		return pointDeVie;
	}
	public void setPointDeVie(int pointDeVie) {
		this.pointDeVie = pointDeVie;
	}
	
	
	

}
