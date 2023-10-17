package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MainMenuScreen implements Screen {
    private BerserkGame game;

    public MainMenuScreen(BerserkGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Здесь вы можете инициализировать элементы меню, кнопки и логику экрана
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Здесь вы рисуете элементы меню и обрабатываете нажатия на кнопки
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

