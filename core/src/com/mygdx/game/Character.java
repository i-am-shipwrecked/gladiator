package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character {
    private Texture characterTexture;
    private float x, y;

    public Character(float initialX, float initialY) {
        characterTexture = new Texture("me.png"); // Замените на имя вашего файла изображения
        if (characterTexture == null) {
            Gdx.app.error("Character", "Failed to load character texture.");
        }
        x = initialX;
        y = initialY;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(characterTexture, x, y);
    }

    public void update(float delta) {
        // Здесь можно добавить логику перемещения персонажа или другие действия
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
