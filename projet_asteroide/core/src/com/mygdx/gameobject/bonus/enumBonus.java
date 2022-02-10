package com.mygdx.gameobject.bonus;

import java.util.Random;

import com.mygdx.game.GameScreen;
import com.mygdx.game.TagText;
import com.mygdx.game.TextManager;
import com.mygdx.gameobject.Vaisseau;
import com.mygdx.gameobject.typesAsteroides;

public enum EnumBonus {

	VITESSE("Bonus de vitesse!"),
	MULTIPLICATEUR_SCORE("Score x "),  //TO DO: faire en sorte que la mult soit calculé ici
	VIE_SUPP("Vie supplémentaire!"),
	MISSILE_SUPP("Missile supplémentaire!"),
	SCORE_FLAT(""), //TO DO faire en sorte que le bonus de score soit calculé ici
	DELAI_MISSILE("Vitesse de tir augmentée!");
	

	String message;
	
	EnumBonus(String message){
		this.message = message;
	}
	
	public static EnumBonus getRandomType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
	
	public static String activationBonus(EnumBonus typeBonus, Vaisseau vaisseauCible, GameScreen ecranJeu) {
		
		switch(typeBonus) {
			case MISSILE_SUPP:
				if (vaisseauCible.getNbMissileSimultane() < 5) {
					return activationMissileSup(vaisseauCible);
				} else {
					return activationScoreFlat(ecranJeu);
				}
			case MULTIPLICATEUR_SCORE:
				return activationMultiplicateurScore(ecranJeu);
			case SCORE_FLAT:
				return activationScoreFlat(ecranJeu);
			case VIE_SUPP:	
				if (vaisseauCible.getVie() < 6) {
					return activationVieSupplementaire(vaisseauCible);
				}else {
					return activationScoreFlat(ecranJeu);
				}
			case VITESSE:
				if (vaisseauCible.getSpeed() <= 12) {
					return activationVitesse(vaisseauCible);
				}else {
					return activationScoreFlat(ecranJeu);
				}
			case DELAI_MISSILE:
				if (vaisseauCible.getDelaiSecondeEntreMissileFixe() > 0.1f) {
					return activationDelaiMissile(vaisseauCible);
				}else {
					return activationScoreFlat(ecranJeu);
				}
			default: 
				System.out.println("Type de bonus non reconnu");
				return ("Erreur");
		
		}
		
	}
		
	
		private static String activationVitesse(Vaisseau vaisseauCible){		
			vaisseauCible.setSpeed(vaisseauCible.getSpeed()+2);
			return VITESSE.message;
		}
		
		private static String activationMultiplicateurScore(GameScreen ecranJeuCible) {
			ecranJeuCible.setMultiplicateurScore(ecranJeuCible.getMultiplicateurScore()+0.5f);
			return MULTIPLICATEUR_SCORE.message + ecranJeuCible.getMultiplicateurScore();
		}
		
		private static String activationScoreFlat(GameScreen ecranJeuCible) {
			int pointRapporte = (int)(1000*ecranJeuCible.getMultiplicateurScore());
			ecranJeuCible.setScore(ecranJeuCible.getScore()+pointRapporte);
			return pointRapporte+"";
			
		}
		
		private static String activationMissileSup(Vaisseau vaisseauCible) {
			vaisseauCible.setNbMissileSimultane(vaisseauCible.getNbMissileSimultane()+1);
			return MISSILE_SUPP.message;
		}
		
		private static String activationVieSupplementaire(Vaisseau vaisseauCible) {
			vaisseauCible.setVie(vaisseauCible.getVie()+1);
			return VIE_SUPP.message;
		}
		
		private static String activationDelaiMissile(Vaisseau vaisseauCible) {
			vaisseauCible.setDelaiSecondeEntreMissileFixe(vaisseauCible.getDelaiSecondeEntreMissileFixe()-0.1f);
			return DELAI_MISSILE.message;
		}
		
	
}
