package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.enums.StateGameScreen;
import com.mygdx.gameobject.Asteroide;
import com.mygdx.gameobject.Missile;
import com.mygdx.gameobject.Vaisseau;
import com.mygdx.gameobject.typesAsteroides;
import com.mygdx.gameobject.bonus.Bonus;
import com.mygdx.gameobject.bonus.EnumBonus;
import com.mygdx.gameobject.visuel.Amogus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameScreen implements Screen{
	
	
	SpriteBatch batch;
	ShapeRenderer sr;
	JeuAsteroide jeu;
	//GameObjects
	Vaisseau vaisseau;
	Array<Asteroide> asteroides;
	Array<Missile> missiles;
	Array<Bonus> listeBonus;
	Amogus amogus;
	
	//Pour g�rer spawn asteroide
	long deltaAsteroide = 1000000000;  //Temps en nano secondes entre 2 ast�roides
	long lastAsteroideSpawnTime;	//TimeStamp du dernier asteroide spawn�
	long deltaBonus = 10000000000l; //Temps entre 2 bonus (nano secondes)
	long lastBonusSpawnTime;	//TimeStamp du dernier bonus spawn�
	//son...
	Music musiqueEpique;
	Sound sonPew;
	Sound sonExplosionMissile;
	Sound sonBonk;
	
	//Score
	int score = 0;
	float multiplicateurScore = 1;
	// milliseconde?
	double tempsEcoule = 0;
	//Affichage vari�
	Texture textureCoeur;	//Texture du coeur qui sert a afficher le nombre de pv du vaisseau
	Array<TagText> listeTagTextePourAfficher;	//Liste des tags a affich� a chaque render
	//Pour �tats:
	StateGameScreen etatActuel;	
	

	public GameScreen(JeuAsteroide jeu) {
		
		this.jeu = jeu;
		batch = jeu.batch;
		sr = jeu.sr;
		etatActuel = StateGameScreen.EN_COURS;
		TextManager.setSpriteBatch(batch);
		vaisseau = new Vaisseau(batch, sr,350,50);
		
		//missile
		missiles = new Array<Missile>();
		
		//ast�roides
		asteroides = new Array<Asteroide>();
		
		//Liste de bonus
		listeBonus = new Array<Bonus>();
		
		//Texture UI
		textureCoeur = new Texture("heart.png");
		
		//pour musique
		musiqueEpique = Gdx.audio.newMusic(Gdx.files.internal("star_wars_shitty.mp3"));
		
		//Sons
		sonPew = Gdx.audio.newSound(Gdx.files.internal("pewGab.mp3"));
		sonExplosionMissile = Gdx.audio.newSound(Gdx.files.internal("explosion_missile.mp3"));
		sonBonk = Gdx.audio.newSound(Gdx.files.internal("bonk.mp3"));
		
	    // start the playback of the background music immediately
		musiqueEpique.setLooping(true);
		musiqueEpique.setVolume(0.1f);
		musiqueEpique.play();
		
		//amogus
		amogus = new Amogus(batch,sr,0,400);
		
		listeTagTextePourAfficher = new Array<TagText>();
		
	}
	
	@Override
	public void render(float delta) {
		switch (etatActuel) {
		case DEFAITE:
			updateDefaite(delta);
			renderDefaite(delta);
			break;
		case EN_COURS:
			updateEnCours(delta);
			renderEnCours(delta);
			break;
		case EN_PAUSE:
			updateEnPause(delta);
			renderEnCours(delta);
			break;
		case DEBUG:
			break;
		default:
			break;
		
		}
	}
	
	
	public void updateEnCours(float delta) {
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {  //Mets le jeux en pause
			etatActuel = StateGameScreen.EN_PAUSE;
			musiqueEpique.stop();
			return;
		} // Fin if pour mettre en pause
		
		amogus.update(Gdx.graphics.getDeltaTime());
		
		if(TimeUtils.nanoTime() - lastAsteroideSpawnTime > deltaAsteroide ) {
			if (deltaAsteroide > 200000000) deltaAsteroide*=0.99; //Diminution progresse du deltaAsteroide
			spawnAsteroide();
		}
		
		if(TimeUtils.nanoTime() - lastBonusSpawnTime >  deltaBonus) {
			spawnBonus();
		}
		
		GameInput.update(); //Regarde les touches appuy�e pour donner de la vitesse au vaisseau
		vaisseau.update(Gdx.graphics.getDeltaTime()); //Update le vaisseau
		remetVaisseauDansLimite(); //Repositionne le vaisseau
		checkCollision(); //Regarde si le vaisseau est en contact avec un asteroide
		
		//Tire les missiles
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !(missiles.size >= vaisseau.getNbMissileSimultane()) && vaisseau.getDelaiSecondeEntreMissileVariable() <= 0 ) {
			sonPew.play();
			missiles.add(vaisseau.shoot());
		}
		
		updateAsteroides(); //Update tous les asteroides
		updateBonus(); //update tous les bonus
		updateMissiles(); //update les missiles

		//Met fin a la partie si le vaisseau est mourrue
		if (vaisseau.getVie() <= 0) {
			etatActuel = StateGameScreen.DEFAITE;
		}
		 tempsEcoule += Gdx.graphics.getDeltaTime(); //Met a jour la variable de temps �coul�
	}
	
	public void renderEnCours(float delta) {
	
		ScreenUtils.clear(0, 0, 0, 1); //Clear l'�cran avec du noir
		
		batch.begin(); //Debut du batch
			amogus.draw();  //Affiche le petit amogus de d�but de partie
			vaisseau.draw(); //Affiche le vaisseau
			for (Missile miss: missiles) { //Affiche tous les missiles
				miss.draw();
			}
			//Elements d'UI
			TextManager.draw("Temps : "+(int)Math.floor(tempsEcoule),10,20);
			TextManager.draw(""+score,10,790);
			affichageVie();		
			affichageTag();
		batch.end();  //Fin du batch

		sr.begin(ShapeType.Line); 		//debut shaperender
			vaisseau.showHitbox();	
			//dessine les asterodies
			for (Asteroide asteo: asteroides) { //Hitbox des ast�roides
				asteo.showHitbox();
			}
			for (Bonus bonus: listeBonus) {		//Hitbox de missiles
				bonus.showHitbox();
			}
		sr.end(); //Fin du shaperenderer
			
	}

	public void updateEnPause(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			etatActuel = StateGameScreen.EN_COURS;
			musiqueEpique.play();
			return;
		}
	}
	
	public void updateDefaite(float delta) {
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			reinitialisationAll();
			etatActuel = StateGameScreen.EN_COURS;
		}
	}
	
	public void renderDefaite(float delta) {
		
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		TextManager.draw("fin de partie. appuyer sur entr� pour recommencer. Score : " + score,200,400);
		batch.end();
	}
	
	//Spaw un asteroide avec un type random
	public void spawnAsteroide(){	
		typesAsteroides typeChoisi = typesAsteroides.getRandomType();  //Selection du type
		int randomPos = MathUtils.random(typeChoisi.rayon,800-typeChoisi.rayon); //Positionnement sur l'axe x al�atoire en fonction du rayon
		Asteroide toto = new Asteroide(batch,sr,randomPos,832, typeChoisi); 
		asteroides.add(toto);
		lastAsteroideSpawnTime = TimeUtils.nanoTime();  //Enregistre le timestamp
	}
	
	//Deplace tous les asteroides et les supprimes si il sorte de l'�cran.
	public void updateAsteroides() {
		for (Iterator<Asteroide> iter = asteroides.iterator(); iter.hasNext();) {
			Asteroide asteo = iter.next();
			asteo.update(Gdx.graphics.getDeltaTime());
			if (asteo.y<0){
				iter.remove();
			}			
		}
	}
	
	//Spawn un bonus avec un type al�atoire
	public void spawnBonus() {
		EnumBonus typeBonus = EnumBonus.getRandomType();   //Selection du type
		int randomPos = MathUtils.random(110,690);		//Position al�atoire sur l'axe X
		Bonus nouveauBonus = new Bonus(batch,sr,randomPos,832, typeBonus);   //Spawn
		listeBonus.add(nouveauBonus);
		lastBonusSpawnTime = TimeUtils.nanoTime();   //Enregistre le timeStamp
	}
	
	public void updateBonus() {
		//Update les bonus
		for (Iterator<Bonus> iter = listeBonus.iterator(); iter.hasNext();) {
			Bonus bonus = iter.next();
			bonus.update(Gdx.graphics.getDeltaTime());
			if (overlapsRect(vaisseau.getHitbox(), bonus.getHitbox())) {	
				String str = bonus.activation(vaisseau, this);
				listeTagTextePourAfficher.add(new TagText(str,(int)bonus.getX(),(int)bonus.getY(),2.0));
				iter.remove();
			}
		}
	}
	
	public void updateMissiles() {
		//Update les missiles
		for (Iterator<Missile> iter = missiles.iterator(); iter.hasNext();) {
			Missile miss = iter.next();
			miss.update(Gdx.graphics.getDeltaTime());
			if (miss.y>800 || checkCollisionMissileAsteroide(miss)){
				miss.dispose();
				iter.remove();
			} 
		}
	}
	

	public boolean checkCollisionMissileAsteroide(Missile missile) {
		
		for (Iterator<Asteroide> iter = asteroides.iterator(); iter.hasNext();) {
			Asteroide asteo = iter.next();
			if (Intersector.overlaps(asteo.getHitbox(), missile.getHitbox())){
				asteo.setVie(asteo.getVie()-1);
				if (asteo.getVie() <= 0){
					this.sonExplosionMissile.play();
					int pointRapporte = (int) (asteo.getType().pointRapporte * multiplicateurScore);
					score += pointRapporte;
					this.listeTagTextePourAfficher.add(new TagText(""+pointRapporte, (int)asteo.x, (int)asteo.y, 2.0));
					iter.remove();
				}
				return true;
			}			
		}
		return false;
		
	}

	//Return true si le vaisseau est entr� en collsion avec un asteroide
	public boolean checkCollision() {
		for (Asteroide asteo: asteroides) {
			if (overlaps(vaisseau.getHitbox(), asteo.getHitbox())){
				asteroides.removeValue(asteo, false);
				this.sonBonk.play(1);
				vaisseau.getHurt();
				return true;
			}
		}
		return false;
	}
	
	//Copier coller du web pour regarder si un polygon et un cercle rentre en contact...
	// Retourne true si collision
	public static boolean overlaps(Polygon polygon, Circle circle) {
	    float []vertices=polygon.getTransformedVertices();
	    Vector2 center=new Vector2(circle.x, circle.y);
	    float squareRadius=circle.radius*circle.radius;
	    for (int i=0;i<vertices.length;i+=2){
	        if (i==0){
	            if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]), new Vector2(vertices[i], vertices[i + 1]), center, squareRadius))
	                return true;
	        } else {
	            if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
	                return true;
	        }
	    }
	    return polygon.contains(circle.x, circle.y);
	}
	
	// Check if Polygon intersects Rectangle
	private boolean overlapsRect(Polygon p, Rectangle r) {
	    Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
	            r.height, 0, r.height });
	    rPoly.setPosition(r.x, r.y);
	    if (Intersector.overlapConvexPolygons(rPoly, p))
	        return true;
	    return false;
	}
	
	//TO DO: utiliser des variables pour la taille de l'�cran
	public void remetVaisseauDansLimite() {
		
		if (vaisseau.getX()<0) {
			vaisseau.setX(0);
			
		} else if (vaisseau.getX()>800-99) {
			vaisseau.setX(800-99);
		}

		if (vaisseau.getY()<0) {
			vaisseau.setY(0);
			
		} else if (vaisseau.getY()>800-90) {
			vaisseau.setY(800-90);
		}
	}
	
	//R�initialise toutes les classes
	public void reinitialisationAll() {
		vaisseau.reinitialise();		
		missiles.clear();
		asteroides.clear();
		listeBonus.clear();
		listeTagTextePourAfficher.clear();	
		amogus.setPosition(-30, 400);

	}
	
	//reinitialise les trucs propre au jeux
	public void reinitialisationJeu() {
		score = 0;
		deltaAsteroide = 1000000000;
		multiplicateurScore = 1;
		lastAsteroideSpawnTime = TimeUtils.nanoTime();
		lastBonusSpawnTime = TimeUtils.nanoTime();
	}
	
	//M�thode a appel� dans le render
	public void affichageVie() {
		for (int i = 0; i<vaisseau.getVie(); i++) {
			batch.draw(textureCoeur,770, 770 - i*20, 20,20);
		}		
	}

	//A appel� avec le batch de commenc�
	public void affichageTag() {
		for (Iterator<TagText> iter = listeTagTextePourAfficher.iterator(); iter.hasNext();) {
			TagText tag = iter.next();
			TextManager.drawTag(tag);
			tag.setY(tag.getY()+1);
			tag.decrementDureeVie(Gdx.graphics.getDeltaTime());
			if (tag.getDureeVieRestanteMilli() <= 0) {
				iter.remove();
			}
		}
	}
	



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}