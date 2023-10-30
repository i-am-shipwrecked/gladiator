package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemyCharacter extends Actor {
    private Texture enemyTexture;
    private float screenWidth;
    private float screenHeight;
    private float moveSpeed = 100;
    private Animation<Texture> animation;
    private float stateTime = 0;
    private boolean movingRight = true;

    public EnemyCharacter(float x, float y, float screenWidth, float screenHeight) {
        enemyTexture = new Texture("enemy.png");
        setPosition(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        System.out.println("Enemy is loaded");

        Texture textureWalk1 = new Texture("enemy_walk.png");
        Texture textureWalk2 = new Texture("enemy_walk_2.png");
        Texture[] walkFrames = { textureWalk1, textureWalk2 };

        float frameDuration = 0.1f;

        animation = new Animation<Texture>(frameDuration, walkFrames);
    }

    public void render(Batch batch) {
        draw(batch, 1.0f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float newX = getX() +(-moveSpeed) * delta;

        if (newX < 0 || newX + getWidth() > screenWidth) {
            movingRight = !movingRight;
        }

        setPosition(newX, getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY());
    }

    public void dispose() {
        enemyTexture.dispose();
    }

}















