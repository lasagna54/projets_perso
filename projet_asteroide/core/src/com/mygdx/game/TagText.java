package com.mygdx.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagText {

	private final String texte;
	private int x;
	private int y;
	private double dureeVieMilli;
	private double dureeVieRestanteMilli;
	
	public TagText(String texte, int x, int y, double dureeVie) {
		this.texte = texte;
		this.x = x;
		this.y = y;
		dureeVieMilli = dureeVie;
		dureeVieRestanteMilli = dureeVie;

	}
	
	public float getGamma() {
		if (dureeVieRestanteMilli <= 0) return 0;
		
		return (float) (dureeVieMilli * dureeVieRestanteMilli);
	}
	
	public void decrementDureeVie(double x) {
		this.dureeVieRestanteMilli -= x;
	}
	
	
}
