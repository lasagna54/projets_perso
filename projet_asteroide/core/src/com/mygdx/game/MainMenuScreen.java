package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen{
	
	JeuAsteroide jeu;
	
	//Textures
	
	Texture playButtonActive;
	Rectangle rectanglePlayButton;
	
	Texture playButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;

	 private TextureRegionDrawable myTexRegionDrawable;
	
	Stage stage;
	ImageButton boutonTest;
	
	public MainMenuScreen(final JeuAsteroide jeu) {
		this.jeu = jeu;
		
		myTexRegionDrawable = quickDrawable("RectangleRouge.png");
		
		stage = new Stage();
		boutonTest = new ImageButton(myTexRegionDrawable);
		boutonTest.setPosition(300, 400);
		
		boutonTest.addListener(new ClickListener(Buttons.LEFT)
		{
		    @Override
		    public void clicked(InputEvent event, float x, float y)
		    {
		    	System.out.println("click");
		        jeu.setScreen(new GameScreen(jeu));
		    }
		});
		
		stage.addActor(boutonTest);
		Gdx.input.setInputProcessor(stage);
		
	}
	
	//Prend le string d'une texture renvoi un regiontexturedrawable
	public TextureRegionDrawable quickDrawable(String textureString) {
		return new TextureRegionDrawable(new TextureRegion(new Texture(textureString)));
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void render(float delta) {
		
		update();
		stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
		
	}
	
	public void update() {
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	

}
