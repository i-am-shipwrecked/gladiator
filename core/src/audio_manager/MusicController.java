package audio_manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicController {
    private Music backgroundMusic;
    private boolean soundOn = true;
    public MusicController() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/susumu-hirasawa-gatts.mp3"));
        backgroundMusic.setLooping(true);
        setVolume(0.5f);
    }

    public void play() {
        if (soundOn) {
            backgroundMusic.play();
        }
    }

    public void pause() {
        backgroundMusic.pause();
    }

    public void stop() {
        backgroundMusic.stop();
    }

    public void setVolume(float volume) {
        backgroundMusic.setVolume(volume);
    }

    public void dispose() {
        backgroundMusic.dispose();
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
        if (!soundOn) {
            backgroundMusic.pause();
        } else {
            backgroundMusic.play();
        }
    }
}

