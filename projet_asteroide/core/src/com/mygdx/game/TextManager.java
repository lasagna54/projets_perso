package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextManager {

	private static BitmapFont bfont = new BitmapFont();
	private static SpriteBatch spriteBatchHandle;
	
	public static void setSpriteBatch(SpriteBatch batch) {
		spriteBatchHandle = batch;
	}
	
	public static void draw(java.lang.CharSequence msg, int x, int y) {
		bfont.draw(spriteBatchHandle, msg, x,y);
	}
	
	//Méthode pour afficher un Tag (c'est un objet a temps limité qui fini par fade out)
	public static void drawTag(TagText tag) {
		bfont.setColor(1, 1, 1, tag.getGamma());
		draw(tag.getTexte(), tag.getX(), tag.getY());
		bfont.setColor(1, 1, 1, 1);
	}

}

