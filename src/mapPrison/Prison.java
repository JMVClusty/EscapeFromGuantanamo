package mapPrison;
import java.util.*;
import java.util.LinkedHashMap;
import personnage.*;


public class Prison {
	/********************************Private Fields*************************************/
	
	private Map<String,PiecePrison> cartePrison =new LinkedHashMap<String,PiecePrison>();
	private PiecePrison SalleDeFin;
	
	/*********************************Methods********************************************/
	public PiecePrison getSalleDeFin() {
		return SalleDeFin;
	}
	public Map<String, PiecePrison> getCartePrison() {
		return cartePrison;
	}



	/**************************************** Constructeur****************************************/
	public Prison(){
		
		//création des objets
		Objet cle = new Objet();		cle.setNom("clé");
		Objet sandwich = new Objet();	sandwich.setNom("sandwich");
		Objet matraque = new Objet();	matraque.setNom("matraque");
		Objet steak = new Objet();		steak.setNom("steak");
		
		//init de la prison, les pieces font parties de la prison => composition
		
		PiecePrison celluleDepart = new PiecePrison(TypePiece.cellule,"celluleDepart");
		PiecePrison couloir1 = new PiecePrison(TypePiece.couloir, "couloir1");
		PiecePrison couloir2 = new PiecePrison(TypePiece.couloir,"couloir2");
		PiecePrison couloir3 = new PiecePrison(TypePiece.couloir,"couloir3");
		PiecePrison couloir4 = new PiecePrison(TypePiece.couloir,"couloir4");
		PiecePrison couloir5 = new PiecePrison(TypePiece.couloir,"couloir5");
		PiecePrison cellule1 = new PiecePrison(TypePiece.cellule,"cellule1");
		PiecePrison cellule2 = new PiecePrison(TypePiece.cellule,"cellule2");
		PiecePrison douches = new PiecePrison(TypePiece.douches,"douches");
		PiecePrison bureau1 = new PiecePrison(TypePiece.bureau,"bureau1");
		PiecePrison bureau2 = new PiecePrison(TypePiece.bureau,"bureau2");bureau2.setItem(cle); // On place la clé dans le bureau2
		PiecePrison toilette = new PiecePrison(TypePiece.toilette,"toilette");toilette.setItem(matraque);// On place un matraque dans les toilettes
		PiecePrison refectoire = new PiecePrison(TypePiece.bureau,"réfectoire");refectoire.setItem(steak);// On place un Steack dans le réfectoire
		PiecePrison cours = new PiecePrison(TypePiece.cours,"cours");
		PiecePrison centralSecu = new PiecePrison(TypePiece.CentralSecurite,"Central de Securité");
		SalleDeFin = new PiecePrison(TypePiece.sortie,"Sortie");SalleDeFin.setLocked(true);
		
		
	
		//le couloir principal
		cartePrison.put("celluleDepart", celluleDepart);
		cartePrison.put("couloir1", couloir1);
		cartePrison.put("couloir2", couloir2);
		cartePrison.put("couloir3", couloir3);
		cartePrison.put("couloir4", couloir4);
		cartePrison.put("couloir5", couloir5);
		cartePrison.put("cours", cours);
		cartePrison.put("fin", SalleDeFin);
		
		/*Preparation de liste de pieces pour populer les bifurcations des pieces */
		ArrayList<PiecePrison> listePiecesNord = new ArrayList<PiecePrison>();
		ArrayList<PiecePrison> listePiecesSud = new ArrayList<PiecePrison>();
		
		/********************Branche Nord du couloir1********************/
		listePiecesNord.add(couloir1);listePiecesNord.add(cellule1);
		couloir1.setBranchNord(listePiecesNord);
		listePiecesNord.clear();
		
		/********************Branche Nord et Sud du Couloir2********************/
		listePiecesNord.add(couloir2);listePiecesNord.add(cellule2);
		listePiecesSud.add(couloir2);listePiecesSud.add(toilette);
		couloir2.setBranchNord(listePiecesNord);
		couloir2.setBranchSud(listePiecesSud);
		listePiecesNord.clear();listePiecesSud.clear();
		
		/********************Branche Nord et Sud du Couloir3********************/
		listePiecesNord.add(couloir3);listePiecesNord.add(douches);
		listePiecesSud.add(couloir3);listePiecesSud.add(bureau1);listePiecesSud.add(bureau2);
		couloir3.setBranchNord(listePiecesNord);
		couloir3.setBranchSud(listePiecesSud);
		listePiecesNord.clear();listePiecesSud.clear();
		
		/*********************Branche Nord du couloir5*************************/
		listePiecesNord.add(couloir5);listePiecesNord.add(refectoire);
		couloir5.setBranchNord(listePiecesNord);
		listePiecesNord.clear();
		
		/********************Branche Sud de la cours***************************/
		listePiecesSud.add(cours);listePiecesSud.add(centralSecu);
		cours.setBranchSud(listePiecesSud);
		listePiecesSud.clear();	
		
	}
/*******************************************************************************************************/	
	

}
