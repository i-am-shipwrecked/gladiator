package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Berserk.getCharacter;


public class EnemyCharacter extends Actor {
    private Texture enemyTexture;
    private float screenWidth;
    private float screenHeight;
    private float moveSpeed = 1; // Скорость движения врага
    private Animation<Texture> animation;
    private float stateTime = 0;
    private boolean movingRight = true;
    private Texture attackTexture = new Texture("attack_enemy.png");
    private boolean canMove = true;
    private float attackTimer = 0;

    public EnemyCharacter(float x, float y, float screenWidth, float screenHeight) {
        enemyTexture = new Texture("enemy.png");
        setPosition(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Texture textureWalk1 = new Texture("enemy_walk.png");
        Texture textureWalk2 = new Texture("enemy_walk_2.png");
        Texture[] walkFrames = { textureWalk1, textureWalk2 };

        float frameDuration = 0.1f;
        animation = new Animation<Texture>(frameDuration, walkFrames);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (canMove) {
            float newX = getX() + (movingRight ? moveSpeed : -moveSpeed);
            if (newX < 0 || newX + getWidth() > screenWidth) {
                movingRight = !movingRight;
            }
            setPosition(newX, getY());
        }

        if (isNearCharacter() && attackTimer <= 0) {
            System.out.println("Is working");
            enemyTexture = attackTexture;
            canMove = false;
            attackTimer = 2.0f;
        }

        if (attackTimer > 0) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                canMove = true;
                enemyTexture = new Texture("enemy.png");
            }
        }
    }

    private boolean isNearCharacter() {
        Character character = getCharacter();
        Vector2 characterPos = character.getPosition();
        float distance = characterPos.dst(getX(), getY());
        float threshold = 50;
        return distance <= threshold;
    }
    public void render(Batch batch) {
        draw(batch, 1.0f); // В противном случае, отобразите анимацию передвижения
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = (attackTimer > 0) ? enemyTexture : animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY());
    }

    public void dispose() {
        enemyTexture.dispose();
        attackTexture.dispose();
    }
}

