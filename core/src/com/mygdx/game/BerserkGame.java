package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class BerserkGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}

