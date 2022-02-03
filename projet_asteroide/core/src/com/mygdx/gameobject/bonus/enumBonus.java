package com.mygdx.gameobject.bonus;

import java.util.Random;

import com.mygdx.game.GameScreen;
import com.mygdx.game.TagText;
import com.mygdx.game.TextManager;
import com.mygdx.gameobject.Vaisseau;
import com.mygdx.gameobject.typesAsteroides;

public enum enumBonus {

	VITESSE,
	MULTIPLICATEUR_SCORE,
	VIE_SUPP,
	MISSILE_SUPP,
	SCORE_FLAT,
	DELAI_MISSILE;
	

	enumBonus(){}
	
	public static enumBonus getRandomType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
	
	public static void activationBonus(enumBonus typeBonus, Vaisseau vaisseauCible, GameScreen ecranJeu) {
		
		switch(typeBonus) {
			case MISSILE_SUPP:
				if (vaisseauCible.getNbMissileSimultane() < 5) {
					activationMissileSup(vaisseauCible);
				} else {
					activationScoreFlat(ecranJeu);
				}
				break;
			case MULTIPLICATEUR_SCORE:
				activationMultiplicateurScore(ecranJeu);
				break;
			case SCORE_FLAT:
				activationScoreFlat(ecranJeu);
				break;
			case VIE_SUPP:
				if (vaisseauCible.getVie() < 6) {
					activationVieSupplementaire(vaisseauCible);
				}else {
					activationScoreFlat(ecranJeu);
				}
				break;
			case VITESSE:
				if (vaisseauCible.getSpeed() <= 12) {
					activationVitesse(vaisseauCible);
				}else {
					activationScoreFlat(ecranJeu);
				}
				break;
			case DELAI_MISSILE:
				if (vaisseauCible.getDelaiSecondeEntreMissileFixe() > 0.1f) {
					activationDelaiMissile(vaisseauCible);
				}else {
					activationScoreFlat(ecranJeu);
				}
				break;
			default:
				break;
		
		}
		
	}
		
	
		private static void activationVitesse(Vaisseau vaisseauCible){		
			vaisseauCible.setSpeed(vaisseauCible.getSpeed()+2);
		}
		
		private static void activationMultiplicateurScore(GameScreen ecranJeuCible) {
			ecranJeuCible.setMultiplicateurScore(ecranJeuCible.getMultiplicateurScore()+0.5f);
		}
		
		private static int activationScoreFlat(GameScreen ecranJeuCible) {
			int pointRapporte = (int)(1000*ecranJeuCible.getMultiplicateurScore());
			ecranJeuCible.setScore(ecranJeuCible.getScore()+pointRapporte);
			return pointRapporte;
			
		}
		
		private static void activationMissileSup(Vaisseau vaisseauCible) {
			vaisseauCible.setNbMissileSimultane(vaisseauCible.getNbMissileSimultane()+1);
		}
		
		private static void activationVieSupplementaire(Vaisseau vaisseauCible) {
			vaisseauCible.setVie(vaisseauCible.getVie()+1);
		}
		
		private static void activationDelaiMissile(Vaisseau vaisseauCible) {
			vaisseauCible.setDelaiSecondeEntreMissileFixe(vaisseauCible.getDelaiSecondeEntreMissileFixe()-0.1f);
		}
		
		
	
}
