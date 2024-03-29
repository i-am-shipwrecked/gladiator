package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Berserk;
import adapters.KeyboardAdapter;

public class Character extends Actor {
    private Texture texture;
    private Texture textureWalk1;
    private Texture textureWalk2;
    private Vector2 position = new Vector2();
    private float screenWidth;
    private float screenHeight;
    private Animation<Texture> animation;
    private float stateTime = 0;
    private Vector2 previousPosition = new Vector2();
    private float animationTime = 0.0f;
    private float animationDuration = 0.1f;
    private boolean animationTriggered = false;
    private KeyboardAdapter inputProcessor;
    private Texture attackTexture;
    private Texture fullHpTexture;
    private Texture halfHpTexture;
    private Texture lowHpTexture;
    private int hitCount = 0;


    public Character(float x, float y, float screenWidth, float screenHeight, KeyboardAdapter inputProcessor) {
        this.inputProcessor = inputProcessor;
        texture = new Texture("me.png");
        textureWalk1 = new Texture("me_walk1.png");
        textureWalk2 = new Texture("me_walk2.png");
        attackTexture = new Texture("attack.png");
        fullHpTexture = new Texture("my_full_hp.png");
        halfHpTexture = new Texture("me_half_hp.png");
        lowHpTexture = new Texture("me_low_hp.png");
        this.position.set(x, y);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Texture[] walkFrames = { texture, textureWalk1, textureWalk2 };

        float frameDuration = -0.9f;

        animation = new Animation<Texture>(frameDuration, walkFrames);
    }



    public void render(Batch batch) {
        if (animationTriggered) {
            animationTime += Gdx.graphics.getDeltaTime();
            if (animationTime > animationDuration) {
                animationTime = 0.0f;

                if (texture == textureWalk1) {
                    texture = textureWalk2;
                } else {
                    texture = textureWalk1;
                }
            }
        }

        if (inputProcessor.isEnterPressed()) {
            texture = attackTexture;

        }

        batch.draw(texture, position.x, position.y);

        float healthTextureX = 10;
        float healthTextureY = 650;
        batch.draw(fullHpTexture, healthTextureX, healthTextureY);

        switch (hitCount) {
            case 0:
                batch.draw(fullHpTexture, healthTextureX, healthTextureY);
                break;
            case 1:
                batch.draw(halfHpTexture, healthTextureX, healthTextureY);
                break;
            case 2:
                batch.draw(lowHpTexture, healthTextureX, healthTextureY);
                Berserk.endGame();
                break;
        }
    }


    public void dispose() {
        attackTexture.dispose();
        texture.dispose();
    }
    private boolean isCollidingWithEnemy(EnemyCharacter enemyCharacter) {
        Rectangle characterRect = new Rectangle(position.x, position.y, getWidth(), getHeight());
        Rectangle enemyRect = new Rectangle(enemyCharacter.getX(), enemyCharacter.getY(), enemyCharacter.getWidth(), enemyCharacter.getHeight());
        return characterRect.overlaps(enemyRect);
    }

    public void moveTo(Vector2 direction) {
        float newX = position.x + direction.x;
        float newY = position.y + direction.y;

        if (newX >= 0 && newX + getWidth() <= screenWidth && newY >= 0 && newY + getHeight() <= screenHeight) {
            if (position.x != newX || position.y != newY) {
                animationTriggered = true;
            } else {
                animationTriggered = false;
                texture = new Texture("me.png");
            }
            position.set(newX, newY);
        }
    }
    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void onHit() {
        hitCount++;
    }
    public void takeDamage() {
        hitCount++;
    }

}


