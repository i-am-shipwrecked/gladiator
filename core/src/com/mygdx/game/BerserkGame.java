package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class BerserkGame extends Game {
    @Override
    public void create() {
        // Создайте экран меню (или другой начальный экран) и установите его как текущий экран
        setScreen(new MainMenuScreen(this)); // Замените на ваш класс экрана меню
    }
}

