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
    private boolean isAttacking = false;
    private Vector2 originalPosition;
    private float returnDistance = 0;
    private boolean returning = false;

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

        originalPosition = new Vector2(x, y);
    }

    private float moveSpeedX = 1; // Скорость движения по горизонтали
    private float moveSpeedY = 1; // Скорость движения по вертикали
    @Override
    public void act(float delta) {
        super.act(delta);

        if (canMove) {
            float newX = getX() + (movingRight ? moveSpeedX : -moveSpeedX);
            float newY = getY() + moveSpeedY;

            if (newX < 0 || newX + getWidth() > screenWidth) {
                movingRight = !movingRight;
            }

            if (newY < 0 || newY + getHeight() > screenHeight) {
                moveSpeedY = -moveSpeedY;
            }

            setPosition(newX, newY);
        }

        if (isNearCharacter() && attackTimer <= 0) {
            System.out.println("Is working");
            enemyTexture = attackTexture;
            canMove = false;
            attackTimer = 2.0f;

            // Устанавливаем состояние атаки в true
            isAttacking = true;
            returning = false;
        }

        if (attackTimer > 0) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                canMove = true;
                enemyTexture = new Texture("enemy.png");

                // Возвращаемся к исходной позиции после атаки
                isAttacking = false;
                returning = true;
            }
        }

        if (returning) {
            returnDistance += moveSpeed * delta;
            float maxReturnDistance = 2; // Максимальное расстояние для возврата

            if (returnDistance >= maxReturnDistance) {
                returnDistance = maxReturnDistance;
                returning = false;
            }

            setPosition(getX() + returnDistance, getY());
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
        draw(batch, 1.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = (isAttacking) ? enemyTexture : animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY());
    }

    public void dispose() {
        enemyTexture.dispose();
        attackTexture.dispose();
    }
}

