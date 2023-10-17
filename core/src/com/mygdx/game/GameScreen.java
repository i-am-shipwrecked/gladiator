package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    private Berserk game;

    public GameScreen(Berserk game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Инициализация игровых объектов и логики
    }

    @Override
    public void render(float delta) {
        // Логика вашей игры
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
