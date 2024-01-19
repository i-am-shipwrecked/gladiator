package com.mygdx.game;

import adapters.KeyboardAdapter;
import audio_manager.MusicController;
import characters.Character;
import characters.EnemyCharacter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class Berserk extends Game {
    private SpriteBatch batch;
    private Texture img;
    private int screenWidth;
    private int screenHeight;
    private Stage stage;
    private BerserkGame game;
    private boolean buttonClicked;
    private static Character character;
    private static EnemyCharacter enemyCharacter;
    private KeyboardAdapter inputProcessor = new KeyboardAdapter();
    private static boolean isGameOver = false;
    private Texture darkOverlay;
    private Texture gameOverTexture;
    private SpriteBatch endGameBatch;
    private MusicController musicController;
    private boolean isSoundEnabled = true;
    private Texture soundOnTexture;
    private Texture soundOffTexture;
    private ImageButton soundButton;

    public static Character getCharacter() {
        return character;
    }

    public static EnemyCharacter getEnemyCharacter() {
        return enemyCharacter;
    }

    @Override
    public void create() {
        musicController = new MusicController();
        musicController.play();
        batch = new SpriteBatch();
        img = new Texture("arena.jpg");

        Pixmap cursorPixmap = new Pixmap(Gdx.files.internal("cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap, 0, 0));
        cursorPixmap.dispose();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        game = new BerserkGame();

        soundOnTexture = new Texture("sound_on.png");
        soundOffTexture = new Texture("sound_off.png");

        TextureRegionDrawable soundOnDrawable = new TextureRegionDrawable(new TextureRegion(soundOnTexture));
        TextureRegionDrawable soundOffDrawable = new TextureRegionDrawable(new TextureRegion(soundOffTexture));
        soundButton = new ImageButton(soundOnDrawable, soundOffDrawable);

        soundButton.setPosition(screenWidth - soundButton.getWidth() - 20, 20);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(soundButton);
        updateSoundButtonImage();

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleSound();
            }
        });

        Texture normalTexture = new Texture("play.png");
        Texture hoverTexture = new Texture("play_hover.png");
        TextureRegionDrawable normalDrawable = new TextureRegionDrawable(new TextureRegion(normalTexture));
        TextureRegionDrawable hoverDrawable = new TextureRegionDrawable(new TextureRegion(hoverTexture));
        final ImageButton button = new ImageButton(normalDrawable, hoverDrawable);
        button.setPosition(screenWidth / 2 - button.getWidth() / 2, screenHeight / 2 - button.getHeight() / 2);

        stage.addActor(button);
        stage.getViewport().update(screenWidth, screenHeight, true);
        Gdx.input.setInputProcessor(stage);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClicked = true;
                character = new Character(0, 0, 1100, 100, inputProcessor);
                enemyCharacter = new EnemyCharacter(1100, 0, 1100, 100);
                Gdx.input.setInputProcessor(inputProcessor);
                System.out.println("characters are loaded");
                button.remove();
            }
        });

        gameOverTexture = new Texture("game_over.png");
    }


    @Override
    public void render() {
        if (!isGameOver) {
            ScreenUtils.clear(0, 0, 0, 1);
            batch.begin();
            batch.draw(img, 0, 0, screenWidth, screenHeight);
            batch.end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 10 / 1f));
            stage.draw();
            if (buttonClicked) {
                float deltaTime = Gdx.graphics.getDeltaTime();
                enemyCharacter.act(deltaTime);
                character.moveTo(inputProcessor.getDirection());
                batch.begin();
                enemyCharacter.render(batch);
                character.render(batch);
                batch.end();
            }
        } else {
            renderEndGameScreen();
        }
    }

    private void renderEndGameScreen() {
        if (endGameBatch == null) {
            darkOverlay = new Texture("dark_overlay.jpg");
            gameOverTexture = new Texture("game_over.png");
            endGameBatch = new SpriteBatch();
        }
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        endGameBatch.begin();
        endGameBatch.draw(darkOverlay, 0, 0, screenWidth, screenHeight);
        endGameBatch.draw(gameOverTexture, (screenWidth - gameOverTexture.getWidth()) / 2f,
                (screenHeight - gameOverTexture.getHeight()) / 2f);
        endGameBatch.end();
    }

    public static void endGame() {
        isGameOver = true;
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        stage.dispose();
        if (musicController != null) {
            musicController.dispose();
        }
        if (endGameBatch != null) {
            endGameBatch.dispose();
            darkOverlay.dispose();
            gameOverTexture.dispose();
        }
        soundOnTexture.dispose();
        soundOffTexture.dispose();
    }

    private void toggleSound() {
        isSoundEnabled = !isSoundEnabled;

        if (isSoundEnabled) {
            musicController.play();
            soundButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(soundOnTexture));
        } else {
            musicController.pause();
            soundButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(soundOffTexture));
        }
    }

    private void updateSoundButtonImage() {
        if (isSoundEnabled) {
            soundButton.getImage().setDrawable(new TextureRegionDrawable(new TextureRegion(soundOnTexture)));
        } else {
            soundButton.getImage().setDrawable(new TextureRegionDrawable(new TextureRegion(soundOffTexture)));
        }
    }


}
