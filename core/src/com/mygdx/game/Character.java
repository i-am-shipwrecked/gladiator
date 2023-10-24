package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {
    private Texture texture;
    private Texture textureWalk1;
    private Texture textureWalk2;
    private Vector2 position = new Vector2();
    private float screenWidth;
    private float screenHeight;
    private Animation<Texture> animation; // Поле для анимации
    private float stateTime = 0; // Время для управления анимацией
    private Vector2 previousPosition = new Vector2();


    public Character(float x, float y, float screenWidth, float screenHeight) {
        texture = new Texture("me.png");
        textureWalk1 = new Texture("me_walk1.png");
        textureWalk2 = new Texture("me_walk2.png");
        this.position.set(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Texture[] walkFrames = { texture, textureWalk1, textureWalk2 };

        // Установите длительность отображения каждого кадра
        float frameDuration = 0.2f;

        // Создайте анимацию
        animation = new Animation<Texture>(frameDuration, walkFrames);
    }

    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(Vector2 direction) {
        previousPosition.set(position);
        float newX = position.x + direction.x;
        float newY = position.y + direction.y;

        // Проверьте, не выходит ли новая позиция за границы экрана
        if (newX >= 0 && newX + getWidth() <= screenWidth && newY >= 0 && newY + getHeight() <= screenHeight) {
            position.add(direction);

            // В зависимости от направления движения, переключите текстуру
            // Обновите время для анимации
            stateTime += Gdx.graphics.getDeltaTime();

            // Получите текущую текстуру согласно анимации
            texture = animation.getKeyFrame(stateTime, true);
        }
    }

}


