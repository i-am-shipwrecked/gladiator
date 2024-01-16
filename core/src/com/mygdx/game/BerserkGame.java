package com.mygdx.game;

import com.badlogic.gdx.Game;
import screens.MainMenuScreen;

public class BerserkGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}

