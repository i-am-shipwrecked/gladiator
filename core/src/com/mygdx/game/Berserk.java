package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Berserk extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int screenWidth;
	int screenHeight;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("dungeon.png");

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		// Рассчитываем позицию изображения, чтобы оно было внизу экрана
		float x = 0;
		float y = 0;

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		// Отрисовка изображения с фиксированными координатами
		batch.draw(img, x, y, screenWidth, screenHeight);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}




