package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Berserk;

import static com.mygdx.game.Berserk.getCharacter;


public class EnemyCharacter extends Actor {
    private static Texture enemyTexture;
    private float screenWidth;
    private float screenHeight;
    private Animation<Texture> animation;
    private float stateTime = 0;
    private boolean movingRight = true;
    private Texture attackTexture = new Texture("attack_enemy.png");
    private boolean canMove = true;
    private float attackTimer = 0;
    private boolean isAttacking = false;
    private Vector2 originalPosition;
    private boolean returning = false;
    private float moveSpeedX = 1;
    private float moveSpeedY = 1;
    public EnemyCharacter(float x, float y, float screenWidth, float screenHeight) {
        enemyTexture = new Texture("enemy.png");
        setPosition(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Texture textureWalk1 = new Texture("enemy_walk.png");
        Texture textureWalk2 = new Texture("enemy_walk_2.png");
        Texture[] walkFrames = {textureWalk1, textureWalk2};

        float frameDuration = 0.1f;
        animation = new Animation<Texture>(frameDuration, walkFrames);

        originalPosition = new Vector2(x, y);
    }

    public static Texture getEnemyTexture() {
        return enemyTexture;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


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
            Character character = Berserk.getCharacter();
            if (getBounds().overlaps(character.getBounds())) {
                character.takeDamage();
            }

            enemyTexture = attackTexture;
            canMove = false;
            attackTimer = 0.5f;

            isAttacking = true;
            returning = false;
        }

        if (attackTimer > 0) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                canMove = true;
                enemyTexture = new Texture("enemy.png");
                isAttacking = false;
                returning = true;
            }
        }

        if (returning) {
            float returnSpeed = 2;
            setPosition(getX() + returnSpeed, getY());

            if (getX() >= originalPosition.x) {
                returning = false;
            }
        }
    }

    private boolean isNearCharacter() {
        Character character = getCharacter();
        Vector2 characterPos = character.getPosition();
        float distance = characterPos.dst(getX(), getY());
        float threshold = 100;
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

