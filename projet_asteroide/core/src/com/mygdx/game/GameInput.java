package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class GameInput {
	public static Vector2 KeyForce= new Vector2();
	
	public static void update() {
		
		KeyForce.x = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			KeyForce.x -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			KeyForce.x += 1;
		}
		
		KeyForce.y = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			KeyForce.y -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			KeyForce.y += 1;
		}
	}
	
	

}
