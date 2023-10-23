package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {
    private Texture texture;
    private Vector2 position = new Vector2();
    private float screenWidth;
    private float screenHeight;

    public Character(float x, float y, float screenWidth, float screenHeight) {
        texture = new Texture("me.png");
        this.position.set(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(Vector2 direction) {
        float newX = position.x + direction.x;
        float newY = position.y + direction.y;

        // Проверьте, не выходит ли новая позиция за границы экрана
        if (newX >= 0 && newX + getWidth() <= screenWidth && newY >= 0 && newY + getHeight() <= screenHeight) {
            position.add(direction);
        }
    }
}


