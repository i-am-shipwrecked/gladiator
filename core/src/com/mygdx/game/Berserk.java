package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class Berserk extends Game {
	private SpriteBatch batch;
	private Texture img;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	private BerserkGame game;
	private boolean buttonClicked;
	private static Character character;
	private static EnemyCharacter enemyCharacter;
	private KeybordAdapter inputProcessor = new KeybordAdapter();


	public static Character getCharacter() {
		return character;
	}

	public static EnemyCharacter getEnemyCharacter() {
		return enemyCharacter;
	}

	private Texture attackTexture;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("arena.jpg");

		Pixmap cursorPixmap = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap, 0, 0));
		cursorPixmap.dispose();


		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		game = new BerserkGame();

		Texture normalTexture = new Texture("play.png");
		Texture hoverTexture = new Texture("play_hover.png");
		TextureRegionDrawable normalDrawable = new TextureRegionDrawable(new TextureRegion(normalTexture));
		TextureRegionDrawable hoverDrawable = new TextureRegionDrawable(new TextureRegion(hoverTexture));
		ImageButton button = new ImageButton(normalDrawable, hoverDrawable);
		button.setPosition(screenWidth / 2 - button.getWidth() / 2, screenHeight / 2 - button.getHeight() / 2);

		stage = new Stage();
		stage.addActor(button);
		stage.getViewport().update(screenWidth, screenHeight, true);
		Gdx.input.setInputProcessor(stage);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				buttonClicked = true;
				character = new Character(0,0, 1100,100, inputProcessor);
				enemyCharacter = new EnemyCharacter(1100, 0, 1100, 100);
				Gdx.input.setInputProcessor(inputProcessor);
				System.out.println("characters are loaded");
			}
		});
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		float x = 0;
		float y = 0;
		batch.draw(img, x, y, screenWidth, screenHeight);
		batch.end();

		stage.act();
		stage.draw();

		if (buttonClicked) {
			Vector2 characterPosition = new Vector2(character.getX(), character.getY());
			Vector2 enemyPosition = new Vector2(enemyCharacter.getX(), enemyCharacter.getY());


			enemyCharacter.act(Gdx.graphics.getDeltaTime());
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.begin();
			batch.draw(img, 0, 0, screenWidth, screenHeight);
			enemyCharacter.render(batch);
			character.render(batch);
			character.moveTo(inputProcessor.getDirection());

			batch.end();
		}
	}



	@Override
	public void dispose() {
		batch.dispose();
		enemyCharacter.dispose();
		character.dispose();
		img.dispose();
		stage.dispose();
	}
}
