package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class Berserk extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	private BerserkGame game; // Добавьте поле для хранения экземпляра BerserkGame

	public SpriteBatch getBatch() {
		return batch;
	}

	public Texture getImg() {
		return img;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("arena.jpg");

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		game = new BerserkGame();

		// Создаем кнопку
		Texture normalTexture = new Texture("play.png"); // Обычное изображение
		Texture hoverTexture = new Texture("play_hover.png"); // Изображение при наведении
		TextureRegionDrawable normalDrawable = new TextureRegionDrawable(new TextureRegion(normalTexture));
		TextureRegionDrawable hoverDrawable = new TextureRegionDrawable(new TextureRegion(hoverTexture));
		ImageButton button = new ImageButton(normalDrawable, hoverDrawable);


		// Устанавливаем позицию кнопки
		button.setPosition(screenWidth / 2 - button.getWidth() / 2, screenHeight / 2 - button.getHeight() / 2);

		// Добавляем обработчик события нажатия на кнопку
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Очищаем текущую сцену (удаляем кнопку)
				stage.clear();

				// Создаем новый экран (сцену) для вашей игры и устанавливаем его в качестве текущего экрана
				GameScreen gameScreen = new GameScreen(Berserk.this);
				game.setScreen(gameScreen);
			}
		});

		// Создаем сцену и добавляем кнопку
		stage = new Stage();
		stage.addActor(button);

		// Устанавливаем сцене размеры экрана
		stage.getViewport().update(screenWidth, screenHeight, true);

		// Устанавливаем сцену как процессор ввода
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		// Отрисовка изображения с фиксированными координатами
		float x = 0;
		float y = 0;

		// Отрисовка изображения на всем экране
		batch.draw(img, x, y, screenWidth, screenHeight);

		batch.end();

		// Обновление и отрисовка сцены для кнопки
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		stage.dispose(); // Не забудьте освободить ресурсы сцены
	}
}
