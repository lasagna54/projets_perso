package com.mygdx.gameobject;

import com.mygdx.game.GameInput;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

@ToString
@Getter
@Setter
public abstract class GameObject {
	 public float x = 0;
     public float y = 0;
     
     @ToString.Exclude
     protected Texture texture;
     @ToString.Exclude
     protected SpriteBatch spriteBatchHandle;
     @ToString.Exclude
     protected ShapeRenderer shapeRenderer;
     
     public GameObject(SpriteBatch batch, ShapeRenderer sr, float posX, float posY) {
    	 spriteBatchHandle = batch;
    	 this.shapeRenderer = sr;
    	 
    	 x=posX;
    	 y=posY;
    	 
     }
     
     //TO DO implementé le delta
     public abstract void update(double deltaTime);
     
     public abstract void draw();
     
     public abstract void showHitbox();
     
     //Getters & setters

	public Texture getTexture() {
		return texture;
	}


}
