package com.mobilelearning.game1910.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.SavedData;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.serviceHandling.json.LeaderboardScore;
import com.mobilelearning.game1910.serviceHandling.json.LeaderboardScoresArray;
import com.mobilelearning.game1910.uiUtils.UpdateScoreDialog;

import java.util.concurrent.atomic.AtomicInteger;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by AFFonseca on 16/04/2015.
 */
public class LevelSelect extends StandardScreen {
    private ImageButton levelButtons[];
    private static final float levelPlaces [][] = {{61f, 146f}, {40f, 392f}, {119f, 600f}, {68f, 766f}};
    private static final String levelStatus [] = {"Jornalista", "Assessor de Redação", "Sub-Diretor",
            "Diretor"};
    private static final Game1910.ScreenType levelClicks [] = {
            Game1910.ScreenType.LEVEL_1,
            Game1910.ScreenType.LEVEL_2,
            Game1910.ScreenType.LEVEL_3,
            Game1910.ScreenType.LEVEL_4
    };
    private Button scoreButton, notebookButton, leaderboardButton, creditsButton;
    private ButtonState [] buttonStates = {ButtonState.OUT_OF_SCENE, ButtonState.OUT_OF_SCENE,
            ButtonState.OUT_OF_SCENE, ButtonState.OUT_OF_SCENE};
    private TextureRegionDrawable backgroundImage, backgroundReporter;
    private TextureRegionDrawable lowerPanel, upperPanel, timeline;
    private String currentStatus = "Diretor";


    private Table scoreTable, leaderboardTable, leaderboardEntries;
    private Image credits;

    private Label leaderboardLevelLabel;
    private int currentLeaderboardLevel = 1;

    private Button currentButton, nextButton;
    private boolean currentButtonIsClosing;

    public LevelSelect(Game1910 game, PolygonSpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void load() {
    }

    @Override
    public void prepare() {
        super.prepare();

        game.getNotebook().updateNotebook(false);

        TextureAtlas atlas = Assets.prepareLevelSelect();

        int currentLevel = SavedData.getCurrentLevel();
        levelButtons = new ImageButton[Assets.levelValues.length-1]; //Last one is complete state

        for(int i=0; i<levelButtons.length; i++){

            String buttonType;
            if(currentLevel > Assets.levelValues[i])
                buttonType = "_complete";
            else if(currentLevel == Assets.levelValues[i]) {
                currentStatus = levelStatus[i];
                buttonType = "_current";
            }
            else //if(currentLevel < levelNames[i])
                buttonType = "_blocked";

            levelButtons[i] = new ImageButton(new TextureRegionDrawable(
                        atlas.findRegion(Assets.levelValues[i] +buttonType)));
            levelButtons[i].setPosition(levelPlaces[i][0], levelPlaces[i][1]);

            final int aux = i;

            if(currentLevel >= Assets.levelValues[i])
                levelButtons[i].addListener((new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        game.loadNextScreen(LevelSelect.this, levelClicks[aux]);
                    }
                }));
        }

        scoreButton = new Button(
                new TextureRegionDrawable(atlas.findRegion("button_money_up")),
                new TextureRegionDrawable(atlas.findRegion("button_money_down"))
        );
        notebookButton = new Button(
                new TextureRegionDrawable(atlas.findRegion("button_notebook_up")),
                new TextureRegionDrawable(atlas.findRegion("button_notebook_down"))
        );
        leaderboardButton = new Button(
                new TextureRegionDrawable(atlas.findRegion("button_score_up")),
                new TextureRegionDrawable(atlas.findRegion("button_score_down"))
        );
        creditsButton = new Button(
                new TextureRegionDrawable(atlas.findRegion("button_credits_up")),
                new TextureRegionDrawable(atlas.findRegion("button_credits_down"))
        );

        backgroundImage = new TextureRegionDrawable(atlas.findRegion("background"));
        backgroundReporter = new TextureRegionDrawable(atlas.findRegion("reporter"));

        lowerPanel = new TextureRegionDrawable(atlas.findRegion("lower_panel"));
        upperPanel = new TextureRegionDrawable(atlas.findRegion("upper_panel"));
        timeline = new TextureRegionDrawable(atlas.findRegion("timeline"));

        credits = new Image(atlas.findRegion("credits"));
        credits.setPosition(-mainStage.getRealWidth(),(mainStage.getHeight()-credits.getPrefHeight())/2);
        createScoreTable(atlas);
        createLeaderboardTable(atlas);

    }

    private void createScoreTable(TextureAtlas atlas){
        scoreTable = new Table();
        scoreTable.setBackground(new TextureRegionDrawable(atlas.findRegion("score_table")));
        scoreTable.setBounds(-mainStage.getRealWidth(), 0, mainStage.getWidth(), mainStage.getHeight());
        scoreTable.top().padTop(315f).left().padLeft(200f).defaults().size(179f, 117f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("write-font"), Assets.darkBrown);

        int [] scores = {SavedData.getLevel1Highscore(), SavedData.getLevel2Highscore(),
                SavedData.getLevel3Highscore(), SavedData.getLevel4Highscore()};

        for(int i=0; i< scores.length; i++){
            Label scoreLabel = new Label("" +scores[i]*(int)LevelScreen.scoreToMoney, labelStyle);
            scoreLabel.setAlignment(Align.center); scoreLabel.setFontScale(0.8f);
            scoreTable.add(scoreLabel);

            Label maxScoreLabel = new Label("" +Assets.maxLevelScores[i]*(int)LevelScreen.scoreToMoney, labelStyle);
            maxScoreLabel.setAlignment(Align.center); maxScoreLabel.setFontScale(0.8f);
            scoreTable.add(maxScoreLabel).row();

        }
    }

    private void createLeaderboardTable(TextureAtlas atlas){
        leaderboardTable = new Table();
        leaderboardTable.setBackground(new TextureRegionDrawable(atlas.findRegion("leaderboard_table")));
        leaderboardTable.setBounds(-mainStage.getRealWidth(), 0, mainStage.getWidth(), mainStage.getHeight());
        leaderboardTable.left().padLeft(71f).top().padTop(315f).padBottom(263f);

        leaderboardEntries = new Table();
        leaderboardEntries.defaults().padBottom(15f).padTop(15f);

        Container<ScrollPane> container = new Container<>();
        ScrollPane pane = new ScrollPane(leaderboardEntries);
        pane.setOverscroll(false, false);
        container.setActor(pane);
        leaderboardTable.add(container);


        Label.LabelStyle classStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);
        Label classLabel = new Label(SavedData.getCurrentClassName(), classStyle);
        classLabel.setBounds(122f, 703f, 379f, 49f); classLabel.setAlignment(Align.center);
        leaderboardTable.addActor(classLabel);

        Button backButton = new Button(
                new TextureRegionDrawable(atlas.findRegion("button_back_up")),
                new TextureRegionDrawable(atlas.findRegion("button_back_down"))
        );
        backButton.setPosition(111f, 158f);
        backButton.addListener((new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (buttonStates[2] == ButtonState.IN_SCENE) {
                    int value = currentLeaderboardLevel -1;
                    if(value == 0)
                        value = 4;
                    getLeaderboardsTable(value, false);
                }
            }
        }));
        leaderboardTable.addActor(backButton);

        Button forwardButton = new Button(
                new TextureRegionDrawable(atlas.findRegion("button_forward_up")),
                new TextureRegionDrawable(atlas.findRegion("button_forward_down"))
        );
        forwardButton.setPosition(434f, 158f);
        forwardButton.addListener((new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (buttonStates[2] == ButtonState.IN_SCENE)
                    getLeaderboardsTable((currentLeaderboardLevel) % 4 + 1, false);
            }
        }));
        leaderboardTable.addActor(forwardButton);


        leaderboardLevelLabel = new Label("" +Assets.levelValues[currentLeaderboardLevel-1], classStyle);
        leaderboardLevelLabel.setPosition(171f, 176f); leaderboardLevelLabel.setWidth(263f);
        leaderboardLevelLabel.setAlignment(Align.center); leaderboardTable.addActor(leaderboardLevelLabel);

    }

    @Override
    public void unload() {
    }

    @Override
    public void show() {
        super.show();

        //Setting the backgroundStage
        backgroundStage.addActor(new Image(backgroundImage));
        Image reporterImage = new Image(backgroundReporter);
        reporterImage.setPosition(
                mainStage.getWidth()*3/4-reporterImage.getPrefWidth()/2,
                mainStage.getHeight()/2-reporterImage.getPrefHeight()*3/4);
        backgroundStage.addActor(reporterImage);

        //Setting the lower panel and it's buttons
        Table lower_table = new Table();
        lower_table.setBounds((mainStage.getWidth()-lowerPanel.getMinWidth())/2, 0f,
                lowerPanel.getMinWidth(), lowerPanel.getMinHeight());
        lower_table.setBackground(lowerPanel);
        lower_table.center().defaults().pad(11f);

        scoreButton.addListener((new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                buttonToggler(scoreButton);
            }
        }));

        notebookButton.addListener((new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                buttonToggler(notebookButton);
            }}));

        leaderboardButton.addListener((new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                buttonToggler(leaderboardButton);
            }
        }));

        creditsButton.addListener((new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                buttonToggler(creditsButton);
            }
        }));

        lower_table.add(scoreButton);
        lower_table.add(notebookButton);
        lower_table.add(leaderboardButton);
        lower_table.add(creditsButton);

        mainStage.addActor(lower_table);

        //Setting the upper table.
        Table upper_table = new Table();
        upper_table.setBounds((mainStage.getWidth()-upperPanel.getMinWidth())/2,
                mainStage.getHeight()-upperPanel.getMinHeight(),
                upperPanel.getMinWidth(), upperPanel.getMinHeight());
        upper_table.setBackground(upperPanel);
        upper_table.center().defaults().pad(11f);

        upper_table.add(new Label(currentStatus,
                new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK)));

        mainStage.addActor(upper_table);

        Image timelineImage = new Image(timeline);
        //from calculations I did
        timelineImage.setPosition(98f, 128f);
        mainStage.addActor(timelineImage);

        for(Button button:levelButtons){
            mainStage.addActor(button);
        }

        mainStage.addActor(credits);
        mainStage.addActor(scoreTable);
        mainStage.addActor(leaderboardTable);

        ImageButton exitButton = new ImageButton(
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("exit_up")),
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("exit_down"))
        );
        exitButton.setPosition(
                mainStage.getWidth() - exitButton.getPrefWidth() - 25f,
                mainStage.getHeight() + mainStage.getPadBottom() - exitButton.getPrefHeight() - 25f);
        mainStage.addActor(exitButton);

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.loadNextScreen(LevelSelect.this, Game1910.ScreenType.MAIN_MENU);
            }
        });
    }

    private void buttonToggler(Button button){
        if(button == scoreButton){
            toggleButton(
                    scoreButton, 0,
                    Tween.to(scoreTable, ActorAccessor.MOVE_X, 0.5f)
                            .target((mainStage.getWidth() - scoreTable.getPrefWidth()) / 2),
                    Tween.to(scoreTable, ActorAccessor.MOVE_X, 0.5f).target(-mainStage.getRealWidth())
            );
        }
        else if(button == notebookButton) {
            toggleButton(
                    notebookButton, 1,
                    game.getNotebook().showNotebook(mainStage, tweenManager),
                    game.getNotebook().hideNotebook(mainStage, tweenManager)
            );
        }
        else if(button == leaderboardButton){
            if(buttonStates[2] == ButtonState.OUT_OF_SCENE){
                getLeaderboardsTable(currentLeaderboardLevel, true);
            }
            else {
                toggleButton(
                        leaderboardButton, 2,
                        Tween.to(leaderboardTable, ActorAccessor.MOVE_X, 0.5f)
                                .target((mainStage.getWidth() - leaderboardTable.getPrefWidth()) / 2),
                        Tween.to(leaderboardTable, ActorAccessor.MOVE_X, 0.5f).target(-mainStage.getRealWidth())
                );
            }
        }
        else if(button == creditsButton){
            toggleButton(
                    creditsButton, 3,
                    Tween.to(credits, ActorAccessor.MOVE_X, 0.5f)
                            .target((mainStage.getWidth() - credits.getPrefWidth()) / 2),
                    Tween.to(credits, ActorAccessor.MOVE_X, 0.5f).target(-mainStage.getRealWidth())
            );
        }
    }

    private void toggleButton(Button button, final int stateIndex, Tween animationOpen, Tween animationClose){
        if(currentButton == null || currentButton == button){
            final AtomicInteger indexAux = new AtomicInteger(stateIndex);
            disableLevels();
            tweenManager.killAll();
            currentButton = button;

            if(buttonStates[stateIndex] == ButtonState.ENTERING || buttonStates[stateIndex] == ButtonState.IN_SCENE) {
                currentButtonIsClosing = true;
                buttonStates[stateIndex] = ButtonState.EXITING;
                Timeline.createSequence()
                        .push(animationClose)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                buttonStates[indexAux.get()] = ButtonState.OUT_OF_SCENE;
                                onClosingAnimationEnd();
                            }
                        }).start(tweenManager);
            }
            else{
                nextButton = null;
                buttonStates[stateIndex] = ButtonState.ENTERING;
                currentButtonIsClosing = false;
                Timeline.createSequence()
                        .push(animationOpen)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                buttonStates[indexAux.get()] = ButtonState.IN_SCENE;
                            }
                        }).start(tweenManager);
            }
        }

        else{
            nextButton = button;

            if(!currentButtonIsClosing)
                closeCurrentButton();
        }
    }

    private void closeCurrentButton(){
        if(currentButton == null || currentButtonIsClosing)
            return;

        buttonToggler(currentButton);
    }

    private void onClosingAnimationEnd(){
        currentButton = nextButton;
        nextButton = null;
        currentButtonIsClosing = false;

        if(currentButton == null){
            enableLevels();
            return;
        }

        buttonToggler(currentButton);
    }

    private void getLeaderboardsTable(final int level, final boolean hasAnimation){
        UpdateScoreDialog scoreDialog = new UpdateScoreDialog(LevelSelect.this, mainStage,
                new UpdateScoreDialog.ButtonType[]{
                        UpdateScoreDialog.ButtonType.GO_BACK},
                new UpdateScoreDialog.ButtonType[]{
                        UpdateScoreDialog.ButtonType.GO_BACK},
                new UpdateScoreDialog.ButtonType[]{
                        UpdateScoreDialog.ButtonType.GO_BACK,
                        UpdateScoreDialog.ButtonType.RETRY},
                new UpdateScoreDialog.GetLeaderboardScoresDialogCallback() {
                    @Override
                    public void onSuccess(LeaderboardScoresArray response) {
                        fillLeaderboardsTable(level, response);
                        if(hasAnimation)
                            toggleButton(
                                    leaderboardButton, 2,
                                    Tween.to(leaderboardTable, ActorAccessor.MOVE_X, 0.5f)
                                            .target((mainStage.getWidth() - leaderboardTable.getPrefWidth()) / 2),
                                    Tween.to(leaderboardTable, ActorAccessor.MOVE_X, 0.5f).target(-mainStage.getRealWidth())
                            );

                    }
                    @Override
                    public void onContinue() {
                        //Continue is not an option xD
                    }

                    @Override
                    public void onGoBack() {
                        if(hasAnimation)
                            //Error. Button has no effect and is removed
                            nextButton = null;
                    }
                });
        scoreDialog.getLeaderboardScores(level);
    }

    private void fillLeaderboardsTable(int levelValue, LeaderboardScoresArray scoresArray){
        leaderboardEntries.clear();

        Label.LabelStyle entryStyle = new Label.LabelStyle(uiSkin.getFont("write-font"), Assets.darkBrown);
        for(int i=0; i<scoresArray.leaderboardScores.size; i++){

            LeaderboardScore entry = scoresArray.leaderboardScores.get(i);

            Label positionLabel = new Label("" +(i+1), entryStyle);
            positionLabel.setAlignment(Align.right); positionLabel.setFontScale(0.8f);
            leaderboardEntries.add(positionLabel).padRight(33f).width(59f);

            Label nameLabel = new Label(entry.username, entryStyle);
            nameLabel.setAlignment(Align.center);

            float shrink = 0f;
            if(entry.username.length() > 10){
                shrink = (entry.username.length()-10)/10f;
            }
            nameLabel.setFontScale(0.8f - 0.4f * shrink);

            leaderboardEntries.add(nameLabel).width(216f);

            Label scoreLabel = new Label("" +entry.score, entryStyle);
            scoreLabel.setAlignment(Align.center); scoreLabel.setFontScale(0.8f);
            leaderboardEntries.add(scoreLabel).width(163f).row();
        }

        currentLeaderboardLevel = levelValue;
        leaderboardLevelLabel.setText("" + Assets.levelValues[levelValue-1]);

    }

    private void enableLevels(){
        for(Button button:levelButtons)
            button.setTouchable(Touchable.enabled);
    }

    private void disableLevels(){
        for(Button button:levelButtons)
        button.setTouchable(Touchable.disabled);
    }

    private enum ButtonState {
        OUT_OF_SCENE, ENTERING, IN_SCENE, EXITING
    }
}
