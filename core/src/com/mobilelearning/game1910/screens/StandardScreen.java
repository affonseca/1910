package com.mobilelearning.game1910.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.uiUtils.AnimatedActor;
import com.mobilelearning.game1910.uiUtils.AnimationDrawable;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 02-03-2015
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class StandardScreen extends ScreenAdapter {
    protected Game1910 game;

    private final static int width = 640, height = 960;

    protected final StandardStage mainStage;
    protected final Stage backgroundStage;
    public Skin uiSkin;
    protected final TweenManager tweenManager;

    private boolean loadingNextScreen;
    private AnimatedActor loadingScreen;
    private boolean [] actorsState;
    private InputMultiplexer inputMultiplexer;

    private static final ExtendViewport stageViewport = new ExtendViewport(width,height);
    private static final FillViewport backgroundViewport = new FillViewport(width,height);

    public StandardScreen(Game1910 game, PolygonSpriteBatch batch){
        this.game = game;
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        backgroundStage = new Stage(backgroundViewport, batch);
        mainStage = new StandardStage(stageViewport, batch, width, height);
        inputMultiplexer = new InputMultiplexer(mainStage, backgroundStage);
        uiSkin = Assets.uiSkin;

        createLoadingScreen();

    }

    private void createLoadingScreen(){
        loadingNextScreen = false;

        loadingScreen = new AnimatedActor(new AnimationDrawable(Assets.loading));
        loadingScreen.setVisible(false);
        mainStage.addActor(loadingScreen);
        mainStage.setLastOverlayActor(loadingScreen);

    }

    public void startLoadingAnimation(boolean loadingNextScreen){
        this.loadingNextScreen = loadingNextScreen;

        Gdx.input.setInputProcessor(null);

        if(loadingNextScreen) {
            Array<Actor> actors = mainStage.getActors();
            actorsState = new boolean[actors.size];
            for (int i = 0; i < mainStage.getActors().size; i++) {
                actorsState[i] = actors.get(i).isVisible();
                actors.get(i).setVisible(false);
            }
        }
        else {
            actorsState = null;
        }

        loadingScreen.setVisible(true);
    }

    public void stopLoadingAnimation(){
        this.loadingNextScreen = false;

        if(actorsState != null)
        {
            for(int i=0; i< mainStage.getActors().size; i++){
                mainStage.getActors().get(i).setVisible(actorsState[i]);
            }
        }

        Gdx.input.setInputProcessor(inputMultiplexer);
        loadingScreen.setVisible(false);

    }

    protected void updateUI(float delta){
        backgroundStage.getViewport().apply();
        backgroundStage.act(delta);
        backgroundStage.draw();

        mainStage.getViewport().apply();
        mainStage.act(delta);
        mainStage.draw();

        tweenManager.update(delta);
    }

    @Override
    public void render (float delta){
        if(loadingNextScreen && Assets.update())
            game.changeScreen(StandardScreen.this);

        updateUI(delta);
    }

    @Override
    public void resize (int width, int height) {
        mainStage.getViewport().update(width, height, true);
        backgroundStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        mainStage.dispose();
        backgroundStage.dispose();
    }

    public abstract void load();

    public void prepare(){
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public abstract void unload();

    //This stage has 2 different capabilities:
    //1 - Using an extended view it works similar to a fit view without black bars.
    //2 - Actors can be defined to always be on top. This is used with the loading and exit buttons
    public class StandardStage extends Stage{
        private Actor lastOverlayActor;
        protected float realWidth, realHeight;
        private float padLeft, padBottom;

        public StandardStage(Viewport viewport, Batch batch, float realWidth, float realHeight) {
            super(viewport, batch);
            this.realWidth = realWidth;
            this.realHeight = realHeight;
            this.padLeft = (super.getWidth()-realWidth)/2;
            this.padBottom = (super.getHeight()-realHeight)/2;
            getRoot().setPosition(padLeft, padBottom);
            getRoot().setCullingArea(new Rectangle(-padLeft, -padBottom, super.getWidth(), super.getHeight()));
        }

        public void setLastOverlayActor(Actor actor){
            lastOverlayActor = actor;
        }

        @Override
        public void addActor(Actor actor) {
            if(lastOverlayActor == null)
                super.addActor(actor);
            else
                getRoot().addActorBefore(lastOverlayActor, actor);
        }

        @Override
        public float getWidth() {
            return realWidth;
        }

        public  float getRealWidth(){
            return super.getWidth();
        }

        @Override
        public float getHeight() {
            return  realHeight;
        }

        public  float getRealHeight(){
            return super.getHeight();
        }

        public float getPadLeft(){
            return padLeft;
        }

        public float getPadBottom(){
            return padBottom;
        }

        @Override
        public void clear() {
            if(lastOverlayActor == null){
                super.clear();
                return;
            }

            SnapshotArray<Actor> root = getRoot().getChildren();
            while (root.get(0)!=lastOverlayActor && root.size != 0){
                unfocus(root.get(0));
                root.get(0).remove();
            }
        }

        @Override
        public void unfocusAll() {
            if(lastOverlayActor == null){
                super.unfocusAll();
                return;
            }

            SnapshotArray<Actor> root = getRoot().getChildren();
            for(int i=0; root.get(i)!=lastOverlayActor && i<root.size; i++){
                unfocus(root.get(i));
            }
        }

        @Override
        public void dispose() {
            super.unfocusAll();
            getRoot().clear();
        }

    }
}
