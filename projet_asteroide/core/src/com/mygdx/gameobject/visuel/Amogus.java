package com.mygdx.gameobject.visuel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.gameobject.GameObject;

public class Amogus extends GameObject {
	
	int speed = 2;
	Texture texture;
	Sprite sprite;

	public Amogus(SpriteBatch batch, ShapeRenderer sr, float posX, float posY) {
		super(batch, sr, posX, posY);
		this.texture = new Texture("amogus.png");
		sprite = new Sprite(texture);
		sprite.setPosition(x, y);
		sprite.setSize(25, 25);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}

	@Override
	public void update(double deltaTime) {
		if (x<=850) {
			x += speed;
			sprite.translateX(speed);
			sprite.rotate(2);
		}
	}

	@Override
	public void draw() {
		sprite.draw(spriteBatchHandle);
	}

	
	@Override
	public void showHitbox() {
		// TODO Auto-generated method stub
		
	}
	
	public void setPosition(int x, int y) {
		this.x=  x;
		this.y = y;
		sprite.setPosition(x, y);
	}
	
	public void dispose() {
		texture.dispose();
	}

}
