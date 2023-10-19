package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private Berserk game;
    private Character character;
    private SpriteBatch batch;
    private Texture img; // Добавьте это поле

    private int screenWidth;
    private int screenHeight;

    public GameScreen(Berserk game) {
        this.game = game;
        this.batch = game.getBatch();
        this.img = game.getImg();
    }
    @Override
    public void show() {
        batch = game.getBatch(); // Получаем batch из класса Berserk
        character = new Character(100, Gdx.graphics.getHeight() / 2); // Начальные координаты персонажа

    }

    @Override
    public void render(float delta) {
        character.update(delta); // Обновление логики персонажа

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Отрисовка изображения с фиксированными координатами
        float x = 0;
        float y = 0;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Отрисовка изображения на всем экране
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        // Отрисовка персонажа
        character.draw(batch);

        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        // Обработка изменения размеров экрана
    }

    @Override
    public void pause() {
        // Пауза игры
    }

    @Override
    public void resume() {
        // Возобновление игры после паузы
    }

    @Override
    public void hide() {
        // Скрытие экрана (например, при переключении на другой экран)
    }

    @Override
    public void dispose() {
        // Освобождение ресурсов
    }
}
