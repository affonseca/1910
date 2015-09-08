package com.mobilelearning.game1910.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.SavedData;
import com.mobilelearning.game1910.accessors.LabelAccessor;
import com.mobilelearning.game1910.uiUtils.ReporterThought;
import com.mobilelearning.game1910.uiUtils.UpdateScoreDialog;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 10-03-2015
 * Time: 10:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class LevelScreen extends StandardScreen {
    protected final ReporterThought reporterThought;

    private int score, tempScore;
    protected final String congratulationsMessagesPart1 [] = {"Gostaria que",           "Sinto que",            "Os jornais vão"};
    protected final String congratulationsMessagesPart2 [] = { "revisses o artigo...", "falta qualquer coisa!", "vender muito bem!"};
    protected final int targetScore;
    public static final float scoreToMoney = 10f;
    protected SceneValue currentScene = SceneValue.SCENE_1;
    protected final LinkedList<Music> musicPool;
    protected final LinkedList<Sound> soundPool;
    private ImageButton restartButton;

    public LevelScreen(final Game1910 game, PolygonSpriteBatch batch, int targetScore) {
        super(game, batch);
        Tween.registerAccessor(Label.class, new LabelAccessor());
        score = tempScore = 0;
        this.targetScore = targetScore;

        reporterThought = new ReporterThought(mainStage, uiSkin.getFont("speech-font"));

        musicPool = new LinkedList<>();
        soundPool = new LinkedList<>();


        Window.WindowStyle dialogStyle = new Window.WindowStyle(
                uiSkin.getFont("default-font"),
                Assets.darkBrown,
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_panel")));

        //Exit stuff
        ImageButton exitButton = new ImageButton(
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("exit_up")),
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("exit_down")));
        exitButton.setPosition(
                mainStage.getWidth() - exitButton.getPrefWidth() - 25f,
                mainStage.getHeight() + mainStage.getPadBottom() - exitButton.getPrefHeight() - 25f);
        mainStage.addActor(exitButton);
        mainStage.setLastOverlayActor(exitButton);

        final  Dialog exitDialog = new Dialog("", dialogStyle){
            @Override
            protected void result(Object object) {
                if(((String)object).matches("yes")) {
                    tweenManager.killAll();
                    for (Sound sound : soundPool)
                        sound.pause();
                    for (Music music : musicPool)
                        if (music.isPlaying())
                            music.pause();
                    game.loadNextScreen(LevelScreen.this, Game1910.ScreenType.LEVEL_SELECT);
                }
            }
        };

        fillYesNoDialog(exitDialog, "Tens a certeza\nque queres sair?",
                "Se saíres perderás o\nprogresso deste nível.");
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                exitDialog.show(mainStage);
            }
        });

        //Restart checkpoint stuff
        restartButton = new ImageButton(
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("checkpoint_up")),
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("checkpoint_down")));
        restartButton.setPosition(25f,
                mainStage.getHeight() + mainStage.getPadBottom() - restartButton.getPrefHeight() - 25f);
        mainStage.addActor(restartButton);
        mainStage.setLastOverlayActor(restartButton);

        final  Dialog restartDialog = new Dialog("", dialogStyle){
            @Override
            protected void result(Object object) {
                if(((String)object).matches("yes")) {
                    tweenManager.killAll();
                    for (Sound sound : soundPool)
                        sound.pause();
                    for (Music music : musicPool)
                        if (music.isPlaying())
                            music.pause();
                    tempScore = 0;
                    switch (currentScene){
                        case SCENE_1:
                            firstScene();
                            break;
                        case SCENE_2:
                            secondScene();
                            break;
                        case SCENE_3:
                            thirdScene();
                            break;
                        case SCENE_4:
                            forthScene();
                            break;
                        case SCENE_5:
                            fifthScene();
                            break;
                    }
                }
            }
        };

        fillYesNoDialog(restartDialog, "Tens a certeza que\nqueres repetir?",
                "Se repetires perderás o\nprogresso desta cena.");
        restartButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                restartDialog.show(mainStage);
            }
        });
    }

    private void fillYesNoDialog(Dialog dialog, String titleText, String messageText){
        dialog.getContentTable().padTop(40f).defaults().padBottom(20f);
        dialog.getButtonTable().padBottom(51f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);

        Label title = new Label(titleText, labelStyle);
        title.setAlignment(Align.center); dialog.text(title).getContentTable().row();

        Label message = new Label(messageText, labelStyle);
        message.setAlignment(Align.center); message.setFontScale(0.8f);
        dialog.text(message).getContentTable().row();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_up")),
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_down")),
                null, uiSkin.getFont("default-font")
        );
        buttonStyle.fontColor = Assets.darkBrown;
        dialog.button("Sim", "yes", buttonStyle);
        dialog.button("Não", "no", buttonStyle);
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void prepare() {
        super.prepare();
        musicPool.add(Assets.textBoxFX);
        soundPool.add(Assets.paperFX);
        soundPool.add(Assets.penFX);
        soundPool.add(Assets.moneyFX);
        soundPool.add(Assets.fanfareFX);
        soundPool.add(Assets.successFX);
        soundPool.add(Assets.failFX);
    }

    protected void addScore(int value){
        tempScore += value;
    }

    protected int getScore(){
        return score + tempScore;
    }

    protected void changeScene(SceneValue newScene){
        currentScene = newScene;
        score += tempScore;
        tempScore = 0;
    }

    public void finishLevel(final Game1910.ScreenType nextScreen){
        restartButton.setTouchable(Touchable.disabled);
        score += tempScore;
        tempScore = 0;

        if(score < 0)
            score = 0;
        int moneyGained = Math.round((float)score*scoreToMoney);

        final AtomicBoolean updateScore = new AtomicBoolean(false);
        int levelValue;
        if(LevelScreen.this instanceof Level1) {
            levelValue = 1;
            if(score > SavedData.getLevel1Highscore()) {
                updateScore.set(true);
                SavedData.setLevel1Highscore(score);
            }
        }
        else if(LevelScreen.this instanceof Level2) {
            levelValue = 2;
            if(score > SavedData.getLevel2Highscore()) {
                updateScore.set(true);
                SavedData.setLevel2Highscore(score);
            }
        }
        else if(LevelScreen.this instanceof Level3) {
            levelValue = 3;
            if(score > SavedData.getLevel3Highscore()) {
                updateScore.set(true);
                SavedData.setLevel3Highscore(score);
            }        }
        else { //if(LevelScreen.this instanceof Level4)
            levelValue = 4;
            if(score > SavedData.getLevel4Highscore()) {
                updateScore.set(true);
                SavedData.setLevel4Highscore(score);
            }
        }

        //Update progress if this is the most advanced level
        if(SavedData.getCurrentLevel() < Assets.levelValues[levelValue])
            SavedData.setCurrentLevel(Assets.levelValues[levelValue]);

        mainStage.unfocusAll();

        //Creating the table itself!
        final Table scoreTable = new Table();
        scoreTable.setTouchable(Touchable.disabled);
        scoreTable.addCaptureListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                scoreTable.removeListener(this);

                if (!updateScore.get()) {
                    afterFinishLevel(nextScreen);
                    return;
                }

                UpdateScoreDialog scoreDialog = new UpdateScoreDialog(LevelScreen.this, mainStage,
                        new UpdateScoreDialog.ButtonType[]{
                                UpdateScoreDialog.ButtonType.CONTINUE},
                        new UpdateScoreDialog.ButtonType[]{
                                UpdateScoreDialog.ButtonType.CONTINUE},
                        new UpdateScoreDialog.ButtonType[]{
                                UpdateScoreDialog.ButtonType.RETRY,
                                UpdateScoreDialog.ButtonType.CONTINUE},
                        new UpdateScoreDialog.UpdateScoreDialogCallback() {
                            @Override
                            public void onContinue() {
                                afterFinishLevel(nextScreen);
                            }

                            @Override
                            public void onGoBack() {
                                //There is no going back :O
                            }
                        });
                scoreDialog.updateScore();

            }
        });

        scoreTable.setBackground(new TextureRegionDrawable(Assets.miscellaneous.findRegion("paymentSheet")));
        scoreTable.setBounds(mainStage.getWidth() / 2 - scoreTable.getBackground().getMinWidth() / 2,
                mainStage.getHeight() / 2 - scoreTable.getBackground().getMinHeight() / 2,
                scoreTable.getBackground().getMinWidth(),
                scoreTable.getBackground().getMinHeight()
        );
        mainStage.addActor(scoreTable);

        //Creating the label with the username
        Label.LabelStyle endStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK);

        Label name = new Label("", endStyle);
        name.setName(SavedData.getUsername());
        name.setHeight(name.getStyle().font.getCapHeight());
        name.setPosition(153f, 508f);
        scoreTable.addActor(name);

        //Creating the label with the money
        Label moneyLabel = new Label("0", endStyle);
        moneyLabel.setSize(347f, name.getStyle().font.getCapHeight());
        moneyLabel.setAlignment(Align.right);
        moneyLabel.setPosition(153f, 425f);
        scoreTable.addActor(moneyLabel);

        //Finding the correct congratulations message
        String congratulationsMessage1, congratulationsMessage2;

        if(score >= targetScore-14){
            congratulationsMessage1 = congratulationsMessagesPart1[2];
            congratulationsMessage2 = congratulationsMessagesPart2[2];
        }
        else if (score >= targetScore-42){
            congratulationsMessage1 = congratulationsMessagesPart1[1];
            congratulationsMessage2 = congratulationsMessagesPart2[1];
        }
        else {
            congratulationsMessage1 = congratulationsMessagesPart1[0];
            congratulationsMessage2 = congratulationsMessagesPart2[0];
        }

        //Creating the label with the congratulations Message
        Label note1 = new Label("", endStyle);
        note1.setName(congratulationsMessage1);
        note1.setHeight(name.getStyle().font.getCapHeight());
        note1.setPosition(228f, 278f);
        scoreTable.addActor(note1);

        Label note2 = new Label("", endStyle);
        note2.setName(congratulationsMessage2);
        note2.setHeight(name.getStyle().font.getCapHeight());
        note2.setPosition(153f, 206f);
        scoreTable.addActor(note2);

        Assets.paperFX.play();
        Timeline.createSequence()
                .push(Timeline.createSequence()
                        .pushPause(1.5f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.penFX.play();
                            }
                        })
                )
                .push(Tween.to(name, LabelAccessor.SCROLL, 1f).target(1f).ease(Linear.INOUT)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.penFX.stop();
                            }
                        }))
                .push(Timeline.createSequence()
                        .pushPause(0.5f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.moneyFX.play();
                            }
                        })
                )
                .push(Tween.to(moneyLabel, LabelAccessor.NUMBER_CHANGE, 5f).target((float)moneyGained).ease(Cubic.OUT)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.moneyFX.stop();
                            }
                        }))
                .push(Timeline.createSequence()
                        .pushPause(0.5f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.fanfareFX.play();
                            }
                        })
                )
                .push(Timeline.createSequence()
                        .pushPause(3.0f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.fanfareFX.stop();
                                Assets.penFX.play();
                            }
                        })
                )
                .push(Tween.to(note1, LabelAccessor.SCROLL, 1f).target(1f).ease(Linear.INOUT))
                .push(Tween.to(note2, LabelAccessor.SCROLL, 1f).target(1f).ease(Linear.INOUT))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Assets.penFX.stop();
                        scoreTable.setTouchable(Touchable.enabled);
                    }
                })
                .start(tweenManager);

    }

    protected abstract void firstScene();
    protected abstract void secondScene();
    protected abstract void thirdScene();
    protected abstract void forthScene();
    protected abstract void fifthScene();

    protected void afterFinishLevel(Game1910.ScreenType nextScreen){
        game.loadNextScreen(LevelScreen.this, nextScreen);
    }

    protected enum SceneValue{
        SCENE_1, SCENE_2, SCENE_3, SCENE_4, SCENE_5,
    }
}
