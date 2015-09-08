package com.mobilelearning.game1910;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.mobilelearning.game1910.screens.Level1;
import com.mobilelearning.game1910.screens.Level2;
import com.mobilelearning.game1910.screens.Level3;
import com.mobilelearning.game1910.screens.Level4;
import com.mobilelearning.game1910.screens.LevelSelect;
import com.mobilelearning.game1910.screens.MainMenu;
import com.mobilelearning.game1910.screens.StandardScreen;
import com.mobilelearning.game1910.screens.StartupScreen;
import com.mobilelearning.game1910.uiUtils.Notebook;

import java.util.concurrent.atomic.AtomicInteger;

public class Game1910 extends Game {
    private StandardScreen newScreen;
    private PolygonSpriteBatch batch;
    private Notebook notebook;

    @Override
    public void create () {
        batch = new PolygonSpriteBatch();

        //Loading assets and saved data
        Assets.loadStartupAssets();
        SavedData.loadSavedData();

        //Starting with main menu
        StandardScreen startup = new StartupScreen(this, batch);
        startup.prepare();
        setScreen(startup);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
        batch.dispose();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    public void loadNextScreen(final StandardScreen oldScreen, ScreenType newScreenType){

        switch (newScreenType){
            case STARTUP:
                newScreen = new StartupScreen(this, batch);
                break;
            case MAIN_MENU:
                newScreen = new MainMenu(this, batch);
                break;
            case LEVEL_SELECT:
                newScreen = new LevelSelect(this, batch);
                break;
            case LEVEL_1:
                newScreen = new Level1(this, batch);
                break;
            case LEVEL_2:
                newScreen = new Level2(this, batch);
                break;
            case LEVEL_3:
                newScreen = new Level3(this, batch);
                break;
            case LEVEL_4:
                newScreen = new Level4(this, batch);
                break;
            default:
                throw  new IllegalArgumentException("No valid screen type!");
        }

        newScreen.load();
        oldScreen.startLoadingAnimation(true);
    }

    public void changeScreen(final StandardScreen oldScreen){
        oldScreen.stopLoadingAnimation();
        newScreen.prepare();
        setScreen(newScreen);
        newScreen = null;
        oldScreen.unload();
        oldScreen.dispose();
    }

    public void createNotebook(){
        if(notebook == null)
            notebook = new Notebook();
    }

    public Notebook getNotebook(){
        return notebook;
    }

    public enum ScreenType{
        STARTUP, MAIN_MENU, LEVEL_SELECT, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4
    }
}
