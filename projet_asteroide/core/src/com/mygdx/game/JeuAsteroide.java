package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JeuAsteroide extends Game {

	public SpriteBatch batch;
	public ShapeRenderer sr;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		this.setScreen(new MainMenuScreen(this));
		
		// test
	}

	@Override
	public void render () {
		super.render();
	}
	}
