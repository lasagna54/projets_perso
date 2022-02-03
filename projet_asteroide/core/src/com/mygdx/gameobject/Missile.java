package com.mygdx.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Missile extends GameObject{

	private Rectangle hitbox;
	private int speed = 8;
	@ToString.Exclude
	private Sprite sprite;
	
	public Missile(SpriteBatch batch, ShapeRenderer sr, float posX, float posY) {
		super(batch, sr, posX, posY);
		texture = new Texture("Missile.png");
		x = posX;
		y= posY;
		hitbox = new Rectangle(x, y, 16, 34);
		sprite = new Sprite(texture);
		sprite.setCenter(x+9, y+10);
	}

	@Override
	public void update(double deltaTime) {
		y += speed;
		hitbox.setPosition(x, y);
		sprite.translateY(speed);
	}
	@Override
	public void draw() {

		sprite.draw(spriteBatchHandle);
		//spriteBatchHandle.draw(sprite, x, y, 130, 140);
	}
	

	@Override
	public void showHitbox() {
		shapeRenderer.rect(hitbox.x,hitbox.y,20,30);
		
	}

	public void dispose() {
		texture.dispose();
	}
	
	public String infoTexture() {
		return String.valueOf(texture.getHeight()) + "  " + String.valueOf(texture.getWidth());
	}

	@ToString.Include
	public String infoSprite() {
		//return String.valueOf(sprite);
		return ""+sprite.getWidth();
	}

	
	
}
