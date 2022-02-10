package com.mygdx.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameInput;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Vaisseau  extends GameObject  {
	
	@ToString.Exclude
	private Polygon hitbox;
	
	//TO DO: Pixel par secondes? avec delta
	private int speed = 6;
	private int vie = 3;
	private int nbMissileSimultane;
	//Va servir de reference
	private float delaiSecondeEntreMissileFixe;
	//Va évoluer et servir a tirer les missiles
	private float delaiSecondeEntreMissileVariable;
	private boolean isInvicible;
	private float dureeInvicibilite;
	private float dureeInvicibiliteActuelle;
	private Texture frameInvincible;

	
	public Vaisseau(SpriteBatch batch, ShapeRenderer sr, int x, int y){
		
		super(batch,sr, x, y);
		texture = new Texture("spaceship.png");
		frameInvincible = new Texture("spaceshipInvincible.png");
		
		FloatArray vertices = new FloatArray(new float[] {
				3,0,
				99,0,
				99,32,
				68,32,  
				52,90,
				32,32,   
				3,32,
				
				
				});  
		
		hitbox = new Polygon(vertices.toArray());
		hitbox.setPosition(x,y);
		nbMissileSimultane = 3;
		delaiSecondeEntreMissileFixe = 0.4f;
		dureeInvicibilite = 0.3f;
		

	}
	
	// TO DO implémenté le delta
	@Override
	public void update(double deltaTime) {

   	 	x += GameInput.KeyForce.x * speed;
   	 	y += GameInput.KeyForce.y * speed ;
   	 	
   	 	hitbox.translate(GameInput.KeyForce.x * speed, GameInput.KeyForce.y * speed);
   	 	
   	 	delaiSecondeEntreMissileVariable -= deltaTime;
   	 	if (isInvicible) {
   	 		dureeInvicibiliteActuelle -= deltaTime;
   	   	 	if (dureeInvicibiliteActuelle <= 0) isInvicible = false;
   	 	}

   	 
		
//		 if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//    		 x=200;
//    	 }
	}
	
	public Missile shoot() {
		delaiSecondeEntreMissileVariable = delaiSecondeEntreMissileFixe;
		return new Missile(spriteBatchHandle, shapeRenderer, x+42, y+80);
	}
	
	@Override
	public void draw() {
		if(!isInvicible) {
		spriteBatchHandle.draw(super.getTexture(), x-10, y-35,128,128);
		}else {
		spriteBatchHandle.draw(frameInvincible, x-10, y-35,128,128);	
		}
	}
	
	public void getHurt() {
		if (!isInvicible) {
			vie --;
			isInvicible = true;
			dureeInvicibiliteActuelle = dureeInvicibilite;
		}
	}
	
	@Override
	public void showHitbox() {
		if (Gdx.input.isKeyPressed(Input.Keys.H)) {
	   		shapeRenderer.setColor(1,1,0,1);
	   		shapeRenderer.polygon(hitbox.getTransformedVertices());
	   	 }
	}
	
	public void setX(int x) {
		this.x = x;
		hitbox.setPosition(this.x, this.y);
	}
	
	public void setY(int y) {
		this.y = y;
		hitbox.setPosition(this.x, this.y);
	}
	
	public void reinitialise() {
		vie =3;
		setX(350);
		setY(50);
		speed = 6;
		
	}
	
	@ToString.Include
	public String infoHitBox() {
		return hitbox.getX() + " " + hitbox.getY();
	}
	

}
