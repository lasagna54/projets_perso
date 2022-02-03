package com.mygdx.gameobject;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Setter
public class Asteroide extends GameObject{
	
	//hitbox
	private Circle hitbox;
	
	//TO DO: Pixel par secondes? avec delta
	private int speed;
	private int rayon;
	private int vie;
	
	//Type de l'asteroide
	
	private typesAsteroides type;
	
	
	public Asteroide(SpriteBatch batch, ShapeRenderer sr, float posX, float posY, typesAsteroides type) {
		super(batch, sr , posX, posY);
		this.type = type;
		speed = type.vitessePixel;
		rayon = type.rayon;
		vie = type.vie;
		hitbox = new Circle(posX,posY,rayon);
	}

	@Override
	public void update(double deltaTime) {
		y -= speed;
		hitbox.setPosition(x, y);
		
	}

	@Override
	public void draw() {
		//spriteBatchHandle.draw(super.getTexture(), x, y,64,64);
		
	}

	@Override
	public void showHitbox() {
		shapeRenderer.setColor(1,1,0,1);
		shapeRenderer.circle(hitbox.x, hitbox.y, hitbox.radius);
		
	}
	
	public Circle getHitbox() {
		return hitbox;
	}


}

