package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemyCharacter extends Actor {
    private Texture enemyTexture;
    private Texture textureWalk1;
    private Texture textureWalk2;
    private Vector2 position = new Vector2();
    private float screenWidth;
    private float screenHeight;
    private Animation<Texture> animation;
    private KeybordAdapter inputProcessor;
    private Texture attackTexture;
    private Texture healthTexture;

    public EnemyCharacter(float x, float y, float screenWidth, float screenHeight) {
        enemyTexture = new Texture("enemy.png");
        this.position.set(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        System.out.println("Enemy is loaded");
    }

    public void render(Batch batch) {
        batch.draw(enemyTexture, position.x, position.y);
    }

    public void dispose() {
        enemyTexture.dispose();
    }

}
