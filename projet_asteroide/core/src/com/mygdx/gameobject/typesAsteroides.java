package com.mygdx.gameobject;

import java.util.Random;

public enum typesAsteroides {
	
	PETIT(20,8,1,10),
	MOYEN(35,6,2,30),
	GRAND(60,4,4,60);
	
	public final int rayon;
	public final int vitessePixel;
	public final int vie;
	public final int pointRapporté;

	typesAsteroides(int rayon, int vitessePixel, int vie, int points) {
		this.rayon = rayon;
		this.vitessePixel = vitessePixel;
		this.vie = vie;
		this.pointRapporté = points;
	}
	
	public static typesAsteroides getRandomType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

}
