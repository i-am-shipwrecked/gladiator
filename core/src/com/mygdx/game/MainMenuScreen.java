package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private BerserkGame game;

    public MainMenuScreen(BerserkGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Инициализация элементов меню
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Очищаем экран черным цветом

        // Ваша логика отрисовки элементов меню
        // В этом месте вы можете рисовать кнопки, текст и другие элементы меню

        // Проверяем, была ли нажата клавиша "Enter"
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Переключение на следующий экран или выполнение другой логики
            game.setScreen(new GameScreen(game)); // Пример переключения на экран с игрой
        }
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


