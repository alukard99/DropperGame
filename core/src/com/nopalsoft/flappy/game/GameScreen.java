package com.nopalsoft.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nopalsoft.flappy.Assets;
import com.nopalsoft.flappy.MainFlappyBird;
import com.nopalsoft.flappy.screens.Screens;

public class GameScreen extends Screens {
    static final int STATE_READY = 0;
    static final int STATE_RUNNING = 1;
    static final int STATE_GAME_OVER = 2;
    int state;
    private Music bgmusic;
    WorldGame oWorld;
    WorldGameRenderer renderer;

    Image getReady, gameOver;

    public GameScreen(MainFlappyBird game) {
        super(game);
        state = STATE_READY;

        oWorld = new WorldGame();
        renderer = new WorldGameRenderer(spriteBatch, oWorld);

        getReady = new Image(Assets.getReady);
        getReady.setPosition(SCREEN_WIDTH / 2f - getReady.getWidth() / 2f, 160);

        gameOver = new Image(Assets.gameOver);
        gameOver.setPosition(SCREEN_WIDTH / 2f - getReady.getWidth() / 2f, 350);

        stage.addActor(getReady);

        this.bgmusic = Gdx.audio.newMusic(Gdx.files.internal("data/bgmusic.mp3"));

        //loop
        this.bgmusic.setLooping(true);
        //play
        this.bgmusic.play();
    }

    @Override
    public void update(float delta) {

        switch (state) {
            case STATE_READY:
                updateReady(delta);
                break;
            case STATE_RUNNING:
                updateRunning(delta);
                break;
            case STATE_GAME_OVER:
                updateGameOver(delta);
                break;
        }

    }

    private void updateReady(float delta) {
        if (Gdx.input.justTouched()) {
            getReady.addAction(Actions.sequence(Actions.fadeOut(.3f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            getReady.remove();
                            state = STATE_RUNNING;
                        }
                    })));
        }

    }

    private void updateRunning(float delta) {
        if (Gdx.input.isTouched()){
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2){
                oWorld.update(delta, true, 1);
            }
        else
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2){
            oWorld.update(delta, true, 2);
        }
        }
        else {
            oWorld.update(delta, false, 0);
        }
        if (oWorld.state == WorldGame.STATE_GAME_OVER) {
            state = STATE_GAME_OVER;
            stage.addActor(gameOver);

        }
    }

    private void updateGameOver(float delta) {
        if (Gdx.input.justTouched()) {
            gameOver.addAction(Actions.sequence(Actions.fadeOut(.3f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            gameOver.remove();
                            game.setScreen(new GameScreen(game));
                        }
                    })));
        }
    }

    @Override
    public void draw(float delta) {
        renderer.render(delta);

        oCam.update();
        spriteBatch.setProjectionMatrix(oCam.combined);


        spriteBatch.begin();
        float width = Assets.getTextWidth(oWorld.score + "");
        Assets.font.draw(spriteBatch, oWorld.score + "", SCREEN_WIDTH / 2f - width / 2f, 700);
        spriteBatch.end();
    }
}
