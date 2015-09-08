package com.mobilelearning.game1910.screens;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;

/**
 * Created by AFFonseca on 16/04/2015.
 */
public class StartupScreen extends StandardScreen {
    private TextureRegionDrawable backgroundImage;
    private TextureRegionDrawable logoImage;
    private TextureRegionDrawable button_normal, button_clicked;

    public StartupScreen(Game1910 game, PolygonSpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void load() {
        Assets.loadStartupScreen();
    }

    @Override
    public void prepare() {
        super.prepare();
        TextureAtlas atlas = Assets.prepareStartupScreen();
        backgroundImage = new TextureRegionDrawable(atlas.findRegion("background"));
        logoImage = new TextureRegionDrawable(atlas.findRegion("image"));
        button_normal = new TextureRegionDrawable(atlas.findRegion("start_normal"));
        button_clicked = new TextureRegionDrawable(atlas.findRegion("start_clicked"));

    }

    @Override
    public void unload() {
        Assets.unloadStartupScreen();
    }

    @Override
    public void show() {
        super.show();

        backgroundStage.addActor(new Image(backgroundImage));
        mainStage.addActor(new Image(logoImage));

        Button startupButton = new Button(button_normal, button_clicked);
        startupButton.setPosition((mainStage.getWidth()-startupButton.getPrefWidth())/2, 195.0f);
        startupButton.addListener((new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                Assets.loadGlobalAssets();
                game.loadNextScreen(StartupScreen.this, Game1910.ScreenType.MAIN_MENU);
            }
        }));


        mainStage.addActor(startupButton);
    }
}
