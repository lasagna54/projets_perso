package com.mygdx.gameobject.bonus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameScreen;
import com.mygdx.game.JeuAsteroide;
import com.mygdx.gameobject.GameObject;
import com.mygdx.gameobject.Vaisseau;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bonus extends GameObject{

	private int speed;
	private int tailleRect;	
	private Rectangle hitbox;
	private enumBonus typeBonus;
	
	
	public Bonus(SpriteBatch batch, ShapeRenderer sr, float posX, float posY, enumBonus typeBonus) {
		super(batch, sr, posX, posY);
		this.typeBonus = typeBonus;
		tailleRect = 15;
		speed = 2;
		hitbox = new Rectangle(x,y,tailleRect, tailleRect);
	}
	
	public void activation(Vaisseau vaisseau, GameScreen jeu) {
		enumBonus.activationBonus(typeBonus, vaisseau, jeu);
	}

	@Override
	public void update(double deltaTime) {
		y -= speed;
		hitbox.setPosition(x, y);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showHitbox() {
		shapeRenderer.setColor(0,1,0,1);
		shapeRenderer.rect(hitbox.x, hitbox.y, tailleRect, tailleRect);
		
	}

}
