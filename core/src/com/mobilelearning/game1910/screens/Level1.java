package com.mobilelearning.game1910.screens;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.minigames.Hangman;
import com.mobilelearning.game1910.minigames.MissingWords;
import com.mobilelearning.game1910.uiUtils.Conversation;
import com.mobilelearning.game1910.uiUtils.DialogBox;
import com.mobilelearning.game1910.uiUtils.DialogCallback;
import com.mobilelearning.game1910.uiUtils.Notebook;
import com.mobilelearning.game1910.uiUtils.ReporterThought;
import com.mobilelearning.game1910.uiUtils.RgbColor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 22-02-2015
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class Level1 extends LevelScreen{
    private final static String firstSceneText1 = "Olá! Eu sou um jornalista que vivenciou uma importante época da " +
            "história portuguesa. Preciso da tua ajuda para que o meu relato fique completo.\n" +
            "Estamos em 1890, ainda Portugal é uma Monarquia. Vivo em Lisboa e trabalho num jornal há pouco tempo, " +
            "tenho que mostrar o meu valor como jornalista. Ajudas-me? Por cada artigo recebo uns bons reis.";

    private final static String firstSceneText2 = "Mas para um bom artigo necessito de um bloco de notas e um lápis";

    private final static String firstSceneText3 = "onde possa apontar todos os pormenores.\n" +
            "Vamos a isto?";

    private final static String directorConversationOrder [] = {"Jornalista", "Diretor", "Jornalista", "Diretor",
            "Jornalista", "Diretor", "Jornalista"};
    private final static String directorConversation [] = {
            "Estou sim?",
            "Sou eu, Brito Aranha.",
            "Olá chefe, bom dia.",
            "Bom dia! Preciso que prepares uma notícia para a primeira página de amanhã, na rubrica 'Assuntos do Dia'." +
                    " Sai da redação e vai investigar sobre as condições sociais e económicas da população  e segue " +
                    "pela cidade para recolher opiniões das nossas gentes em relação à imposição inglesa.",
            "Certo chefe!",
            "Mas atenção, quero um trabalho bem feito. Olha que é para a 1ª página! Se mostrares um bom trabalho " +
                    "daqui para a frente aumento-te o salário. Mas temos de vender muito, por isso, empenha-te!",
            "Certo chefe! Vamos vender bem, pode apostar!"};

    private final static String mapHelp = "Pois bem, vou circular em várias zonas de Lisboa e escutar o que dizem os" +
            " meus conterrâneos sobre a situação atual. É necessário ouvi-los com atenção para depois registar as" +
            " palavras-chave no bloco de notas. Conto com a tua astúcia.";

    private final static String wordsHelp = "Tenho que completar esta notícia para enviar para a redação ainda hoje. " +
            "Preciso de utilizar as palavras certas. Ajudas-me?";

    public Level1(Game1910 game, PolygonSpriteBatch batch){
        super(game, batch, Assets.maxLevelScores[0]);
    }

    @Override
    public void load() {
        Assets.loadLevel1();
    }

    @Override
    public void prepare() {
        super.prepare();
        game.getNotebook().updateNotebook(Assets.levelValues[0], true);
        game.getNotebook().addElementNoAnimation(Notebook.Element.LEVEL_1890);
        Assets.prepareLevel1();
        musicPool.addAll(Arrays.asList(Assets.hangmanAudios));
        soundPool.add(Assets.phoneFX);
    }

    @Override
    public void unload() {
        Assets.unloadLevel1();
    }

    @Override
    public void show() {
        firstScene();
    }

    protected void firstScene(){
        firstScenePart1();
    }

    private void firstScenePart1(){
        mainStage.clear();
        backgroundStage.clear();

        final Image backgroundImage = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("background1")));
        backgroundStage.addActor(backgroundImage);

        final Image reporter = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("reporter1")));
        reporter.setY(-mainStage.getPadBottom()); mainStage.addActor(reporter);

        final Image moneyBag = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("money_bag")));
        moneyBag.setPosition(71f, 58f-mainStage.getPadBottom());
        moneyBag.getColor().a = 0.0f;
        mainStage.addActor(moneyBag);

        final Image notepadImage = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("notepad")));
        notepadImage.setPosition(343f, 73f-mainStage.getPadBottom());
        notepadImage.getColor().a = 0.0f;
        mainStage.addActor(notepadImage);

        TextureRegionDrawable wordBalloon = new TextureRegionDrawable(Assets.level1scene1.findRegion("word_balloon1"));
        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        final DialogBox reporterBox = new DialogBox(firstSceneText1, talkStyle,
                (mainStage.getWidth()-wordBalloon.getMinWidth())/2, 628f-mainStage.getPadBottom(),
                wordBalloon.getMinWidth(), wordBalloon.getMinHeight());
        reporterBox.setBackground(wordBalloon);
        reporterBox.setTextPad(30f, 30f, 30f, 60f);
        reporterBox.stopText();
        reporterBox.setTouchable(Touchable.disabled);
        reporterBox.setClickAnimationPosition(
                wordBalloon.getMinWidth() - reporterBox.getClickAnimation().getPrefWidth() + 30f, -25f);

        reporterBox.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type == DialogCallback.ALL_END) {
                    reporterBox.setTouchable(Touchable.disabled);
                    Tween.to(moneyBag, ActorAccessor.ALPHA, 1.0f).target(1.0f)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    reporterBox.setTouchable(Touchable.enabled);
                                }
                            })
                            .start(tweenManager);
                } else if (type == CLICKED_AFTER_END) {
                    reporterBox.setText(firstSceneText2);
                    reporterBox.setCallback(new DialogCallback() {
                        @Override
                        public void onEvent(int type, DialogBox source) {
                            if (type == DialogCallback.ALL_END) {
                                reporterBox.setTouchable(Touchable.disabled);
                                Tween.to(notepadImage, ActorAccessor.ALPHA, 1.0f).target(1.0f)
                                        .setCallback(new TweenCallback() {
                                            @Override
                                            public void onEvent(int type, BaseTween<?> source) {
                                                reporterBox.setTouchable(Touchable.enabled);
                                            }
                                        })
                                        .start(tweenManager);
                            } else if (type == CLICKED_AFTER_END) {
                                reporterBox.setText(firstSceneText3);
                                reporterBox.setCallback(new DialogCallback() {
                                    @Override
                                    public void onEvent(int type, DialogBox source) {
                                        if (type == DialogCallback.CLICKED_AFTER_END) {
                                            reporterBox.setTouchable(Touchable.disabled);
                                            Timeline.createParallel()
                                                    .push(Tween.to(backgroundImage, ActorAccessor.ALPHA, 1.5f).target(0.0f))
                                                    .push(Tween.to(reporter, ActorAccessor.ALPHA, 1.5f).target(0.0f))
                                                    .push(Tween.to(moneyBag, ActorAccessor.ALPHA, 1.5f).target(0.0f))
                                                    .push(Tween.to(notepadImage, ActorAccessor.ALPHA, 1.5f).target(0.0f))
                                                    .push(Tween.to(reporterBox.getActor(), ActorAccessor.ALPHA, 1.5f).target(0.0f))
                                                    .setCallback(new TweenCallback() {
                                                        @Override
                                                        public void onEvent(int type, BaseTween<?> source) {
                                                            firstScenePart2();
                                                        }
                                                    })
                                                    .start(tweenManager);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        reporterBox.addToStage(mainStage);

        backgroundImage.getColor().a = 0f; reporter.getColor().a = 0f;
        reporterBox.getActor().getColor().a = 0;
        Timeline.createParallel()
                .push(Tween.to(backgroundImage, ActorAccessor.ALPHA, 2.5f).target(1.0f))
                .push(Tween.to(reporter, ActorAccessor.ALPHA, 2.5f).target(1.0f))
                .push(Tween.to(reporterBox.getActor(), ActorAccessor.ALPHA, 2.5f).target(1.0f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterBox.setTouchable(Touchable.enabled);
                        reporterBox.resumeText();
                    }
                })
                .start(tweenManager);

    }

    private void firstScenePart2(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("background2")));
        background.getColor().a = 0f; backgroundStage.addActor(background);

        //Elements from before the phone call
        final Image reporterTalking = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("reporter2")));
        reporterTalking.setPosition((mainStage.getWidth() - reporterTalking.getPrefWidth()) / 2, 153f-mainStage.getPadBottom());
        reporterTalking.getColor().a = 0f; mainStage.addActor(reporterTalking);

        final Image table = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("table")));
        table.getColor().a = 0f; table.setY(-mainStage.getPadBottom()); mainStage.addActor(table);

        final Image phone = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("phone")));
        phone.setPosition(0f, 197f-mainStage.getPadBottom()); phone.setOriginX(phone.getPrefWidth());
        phone.getColor().a = 0f; phone.setTouchable(Touchable.disabled); mainStage.addActor(phone);

        final Image phoneWave = new Image(new TextureRegionDrawable(Assets.level1scene1. findRegion("phone_sound")));
        phoneWave.setPosition(68f, 395f-mainStage.getPadBottom());
        phoneWave.setTouchable(Touchable.disabled); //For the phone to be touchable
        phoneWave.getColor().a = 0f; mainStage.addActor(phoneWave);


        //Elements during the phone call
        final Image reporterOnPhone = new Image(new TextureRegionDrawable(Assets.level1scene1.findRegion("reporter3")));
        reporterOnPhone.setPosition((mainStage.getWidth() - reporterOnPhone.getPrefWidth()) / 2,
                197f-mainStage.getPadBottom());
        reporterOnPhone.setTouchable(Touchable.disabled); //For the phone to be touchable
        reporterOnPhone.getColor().a = 0f; mainStage.addActor(reporterOnPhone);

        final TextureRegionDrawable wordBalloon1 = new TextureRegionDrawable(Assets.level1scene1.findRegion("word_balloon1"));
        Label.LabelStyle talkStyle1 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        final DialogBox reporterBox1 = new DialogBox(null, talkStyle1,
                32f, 666f-mainStage.getPadBottom(), wordBalloon1.getMinWidth(), wordBalloon1.getMinHeight());
        reporterBox1.setBackground(wordBalloon1);
        reporterBox1.setTextPad(30f, 30f, 30f, 60f);
        reporterBox1.setVisible(false);
        reporterBox1.setClickAnimationPosition(
                wordBalloon1.getMinWidth()-reporterBox1.getClickAnimation().getPrefWidth()+30f, -25f);
        reporterBox1.addToStage(mainStage);

        final TextureRegionDrawable wordBalloon2 = new TextureRegionDrawable(Assets.level1scene1.findRegion("word_balloon2"));
        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        final DialogBox reporterBox2 = new DialogBox(null, talkStyle2,
                100f, 150f-mainStage.getPadBottom(), wordBalloon2.getMinWidth(), wordBalloon2.getMinHeight());
        reporterBox2.setBackground(wordBalloon2);
        reporterBox2.setTextPad(30f, 175f, 30f, 30f);
        reporterBox2.setVisible(false);
        reporterBox2.setClickAnimationPosition(
                wordBalloon2.getMinWidth() - reporterBox2.getClickAnimation().getPrefWidth()+30f, -60f);
        reporterBox2.addToStage(mainStage);

        //Phone ringing animation
        final Timeline phoneRinging = Timeline.createSequence()
                .push(Tween.mark()
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            Assets.phoneFX.stop();
                            Assets.phoneFX.play();
                        }
                    }))
                .push(Timeline.createSequence()
                                .push(Tween.set(phoneWave, ActorAccessor.ALPHA).target(1))
                                .push(Tween.set(phone, ActorAccessor.ROTATE).target(-1.5f))
                                .repeatYoyo(20, 0.125f)
                )
                .push(Tween.set(phoneWave, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(phone, ActorAccessor.ROTATE).target(0))
                .pushPause(2.5f)
                .repeat(Tween.INFINITY, 0.0f);

        //fade in and ringing animation
        Timeline.createParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .push(Tween.to(reporterTalking, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .push(Tween.to(table, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .push(Tween.to(phone, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        phone.setTouchable(Touchable.enabled);
                        phoneRinging.start(tweenManager);
                    }
                })
                .start(tweenManager);

        //phone listener changes stage
        phone.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                tweenManager.killAll();
                Assets.phoneFX.stop();
                phoneWave.remove();
                phone.removeListener(phone.getListeners().first());

                Timeline.createSequence()
                        .beginParallel()
                            .push(Tween.to(background, ActorAccessor.ALPHA, 0.25f).target(0f))
                            .push(Tween.to(reporterTalking, ActorAccessor.ALPHA, 0.25f).target(0f))
                            .push(Tween.to(table, ActorAccessor.ALPHA, 0.25f).target(0f))
                            .push(Tween.to(phone, ActorAccessor.ALPHA, 0.25f).target(0f))
                        .end()
                        .beginParallel()
                            .push(Tween.to(background, ActorAccessor.ALPHA, 0.25f).target(1f))
                            .push(Tween.to(reporterOnPhone, ActorAccessor.ALPHA, 0.25f).target(1f))
                            .push(Tween.to(table, ActorAccessor.ALPHA, 0.25f).target(1f))
                        .end()
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Conversation conversation = new Conversation(   new String[] {"Jornalista", "Diretor"},
                                        new DialogBox[] {reporterBox1, reporterBox2},
                                        directorConversationOrder, directorConversation);
                                conversation.setCallback(new Conversation.ConversationCallback() {
                                    @Override
                                    public void onCompleted() {
                                        Timeline.createParallel()
                                                .push(Tween.to(background, ActorAccessor.ALPHA, 2f).target(0f))
                                                .push(Tween.to(reporterOnPhone, ActorAccessor.ALPHA, 2f).target(0f))
                                                .push(Tween.to(table, ActorAccessor.ALPHA, 2f).target(0f))
                                                .push(Tween.to(reporterBox1.getActor(), ActorAccessor.ALPHA, 2f).target(0f))
                                                .push(Tween.to(reporterBox2.getActor(), ActorAccessor.ALPHA, 2f).target(0f))
                                                .setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        secondScene();
                                                    }
                                                })
                                                .start(tweenManager);
                                    }
                                });
                                conversation.startConversation();
                            }
                        })
                        .start(tweenManager);
            }
        }) ;

    }

    protected void secondScene(){
        changeScene(SceneValue.SCENE_2);
        secondScenePart1(true);
    }


    private Table secondScenePart1(boolean clearAndAddToStage){
        final Table scene3 = new Table();
        scene3.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());

        final String locationNames [] =
                {"1 - Mercado da Ribeira", "2 - Elevador", "3 - Largo do Conde",
                "4 - Rua das Atalaias", "5 - Monumento dos Restauradores", "6 - Estação do Rossio"};

        final float pinLocations [][] = {
                {191f,617f}, {385f,628f}, {304f,553f},
                {211f,462f}, {334f,420f}, {440f,460f}
        };

        if(clearAndAddToStage) {
            mainStage.clear();
            backgroundStage.clear();
        }


        backgroundStage.addActor(new Image(Assets.level1scene2_1.findRegion("background")));

        Table upperPanel = new Table();
        upperPanel.center().top();
        upperPanel.setSize(mainStage.getWidth(), mainStage.getHeight());
        upperPanel.setBackground(new TextureRegionDrawable(Assets.level1scene2_1.findRegion("upper_panel")));
        upperPanel.add(new Label("Mapa", uiSkin, "default-font", Assets.darkBrown)).padTop(26f);
        scene3.addActor(upperPanel);

        Table lowerPanel = new Table();
        lowerPanel.padBottom(62f).padLeft(115f);
        lowerPanel.left().bottom().defaults().padBottom(6f).padLeft(12f);
        lowerPanel.setSize(mainStage.getWidth(), mainStage.getHeight());
        lowerPanel.setBackground(new TextureRegionDrawable(Assets.level1scene2_1.findRegion("lower_panel")));

        final Image [] markers = new Image[locationNames.length];
        for(int i=0; i<locationNames.length; i++){
            Image marker;
                marker = new Image(Assets.level1scene2_1.findRegion("marker_incomplete"));

            markers[i] = marker;
            lowerPanel.add(marker);
            Label locationLabel = new Label(locationNames[i], uiSkin, "default-font", Assets.darkBrown);
            locationLabel.setFontScale(0.7f);
            lowerPanel.add(locationLabel).left();
            lowerPanel.row();
        }

        scene3.addActor(lowerPanel);

        Table pinMap = new Table();
        TextureRegionDrawable map = new TextureRegionDrawable(Assets.level1scene2_1.findRegion("map"));

        pinMap.setSize(map.getMinWidth(), map.getMinHeight());
        pinMap.setBackground(map);

        final Image [] pins = new Image[pinLocations.length];
        for(int i=0; i<pinLocations.length; i++){
            final Image pin = new Image(Assets.level1scene2_1.findRegion("pin"));
            pin.setPosition(pinLocations[i][0], pinLocations[i][1]);
            pinMap.addActor(pin);
            if(i != 0)
                pin.setVisible(false);
            pins[i] = pin;
        }
        scene3.addActor(pinMap);

        for(int i=0; i<pins.length; i++){
            final int current_i = i;
            final Image nextPin = (i+1 == pins.length) ? null : pins[i+1];
            pins[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    secondScenePart2(current_i, scene3, markers[current_i], pins[current_i], nextPin);

                }
            });
        }

        if(clearAndAddToStage) {
            mainStage.addActor(scene3);
            scene3.setTouchable(Touchable.disabled);
            reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                @Override
                public void onCompleted() {
                    scene3.setTouchable(Touchable.childrenOnly);
                }
            });
            reporterThought.run(mapHelp, tweenManager);
        }

        return scene3;

    }

    private void secondScenePart2(int exerciseNumber, final Table mapTable, final Image exerciseMarker,
                                 final  Image currentPin, final Image nextPin){
        //mainStage.clear();
        //backgroundStage.clear();
        final int [][] exerciseToBackground = {{0}, {1}, {2}, {3}, {4, 5, 6}, {7}};
        final float [] backgroundDescent = {
                backgroundStage.getHeight()*(1f/3f),
                backgroundStage.getHeight()*(1f/3f),
                backgroundStage.getHeight()*(1f/6f),
                backgroundStage.getHeight()*(1f/3f),
                backgroundStage.getHeight()*(1f/3f),
                backgroundStage.getHeight()*(0f),
                backgroundStage.getHeight()*(1f/3f),
                backgroundStage.getHeight()*(1f/3f)
        };

        final String [][] wordsForBackground = {
                {"desemprego", "aumentou"},
                {"inglaterra", "bancos", "d.carlos"},
                {"angola", "ultimato", "cor-de-rosa"},
                {"republicano", "eleições", "governo"},
                {"bretões"},
                {"portuguesa"},
                {"descontente", "monarquia"},
                {"carbonária"}
        };

        final String [][] extraWordPerWord = {
                {"", ""},
                {"", "", ""},
                {"", "", "mapa"},
                {"", "", ""},
                {""},
                {"a"},
                {"", ""},
                {""}
        };

        final int [][] lettersInPlacePerWord = {
                {2,4},
                {2,1,1},
                {1,2,2},
                {3,1,1},
                {0},
                {2},
                {6, 2},
                {2}
        };

        final Notebook.Element [][][] wordsForBackgroundNotebook = {
                {{Notebook.Element.TITLE_WORDS, Notebook.Element.WORD_DESEMPREGO}, {Notebook.Element.WORD_AUMENTOU}},
                {{Notebook.Element.WORD_INGLATERRA}, {Notebook.Element.WORD_BANCOS}, {Notebook.Element.WORD_D_CARLOS}},
                {{Notebook.Element.WORD_ANGOLA}, {Notebook.Element.WORD_ULTIMATO}, {Notebook.Element.WORD_COR_DE_ROSA}},
                {{Notebook.Element.WORD_REPUBLICANO}, {Notebook.Element.WORD_ELEICOES}, {Notebook.Element.WORD_GOVERNO}},
                {{Notebook.Element.WORD_BRETOES}},
                {{Notebook.Element.WORD_PORTUGUESA}},
                {{Notebook.Element.WORD_DESCONTENTE}, {Notebook.Element.WORD_MONARQUIA}},
                {{Notebook.Element.WORD_CARBONARIA}}
        };

        final AtomicInteger currentExercise = new AtomicInteger(exerciseNumber);
        final AtomicInteger currentBackground = new AtomicInteger(exerciseToBackground[currentExercise.get()][0]);
        final AtomicInteger currentWord = new AtomicInteger(0);

        final Image imageBackground = new Image(Assets.level1scene2_2.findRegion("background" +currentBackground));
        final Image notebookBackground = new Image(Assets.level1scene2_2.findRegion("notebook"));
        final Image audioIcon = new Image(Assets.level1scene2_2.findRegion("audio_play"));
        audioIcon.setColor(new RgbColor(96f ,89, 107, 255f)); audioIcon.setVisible(false);
        audioIcon.setPosition((mainStage.getWidth()-audioIcon.getPrefWidth())/2, 215f);
        mainStage.addActor(audioIcon);
        Tween.to(audioIcon, ActorAccessor.ALPHA, 1f)
                .target(0f).repeatYoyo(Tween.INFINITY, 0.3f)
                .ease(Linear.INOUT).start(tweenManager);


        final ImageButton soundButton = new ImageButton(
                new TextureRegionDrawable(Assets.level1scene2_2.findRegion("music_button_up")),
                new TextureRegionDrawable(Assets.level1scene2_2.findRegion("music_button_up")),
                new TextureRegionDrawable(Assets.level1scene2_2.findRegion("music_button_checked"))
        );
        soundButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (Assets.hangmanAudios[currentBackground.get()].isPlaying()) {
                    Assets.hangmanAudios[currentBackground.get()].pause();
                    soundButton.setChecked(true);
                } else {
                    Assets.hangmanAudios[currentBackground.get()].play();
                    soundButton.setChecked(false);
                }
            }
        });
        imageBackground.setY(backgroundStage.getHeight());
        backgroundStage.addActor(imageBackground);

        notebookBackground.setY(-backgroundStage.getHeight());
        backgroundStage.addActor(notebookBackground);

        final ImageButton acceptButton = new ImageButton(
                new TextureRegionDrawable(Assets.level1scene2_2.findRegion("play_button_up")),
                new TextureRegionDrawable(Assets.level1scene2_2.findRegion("play_button_down"))
        );

        final Hangman hangmanPuzzle = new Hangman(uiSkin);
        fillHangman(
                hangmanPuzzle,
                wordsForBackground[currentBackground.get()][currentWord.get()],
                lettersInPlacePerWord[currentBackground.get()][currentWord.get()],
                soundButton, acceptButton);
        hangmanPuzzle.setY(-mainStage.getHeight());
        hangmanPuzzle.setTouchable(Touchable.disabled);
        mainStage.addActor(hangmanPuzzle);

        final Label extraWord = new Label(extraWordPerWord[currentBackground.get()][currentWord.get()],
                new Label.LabelStyle(
                        uiSkin.getFont("default-font"),
                        Assets.darkBrown
                ));
        extraWord.setWidth(hangmanPuzzle.getWidth()); extraWord.setFontScale(1.5f);
        extraWord.setAlignment(Align.center);
        extraWord.setY(250f);
        hangmanPuzzle.addActor(extraWord);

        Assets.hangmanAudios[currentBackground.get()].setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                audioIcon.setVisible(false);
                soundButton.setChecked(true);
                Assets.hangmanAudios[currentBackground.get()].setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        soundButton.setChecked(true);
                    }
                });
                Tween.to(hangmanPuzzle, ActorAccessor.MOVE_Y, 1f).target(0f).setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        hangmanPuzzle.setTouchable(Touchable.childrenOnly);
                    }
                }).start(tweenManager);
            }
        });

        mapTable.setTouchable(Touchable.disabled);
        mapTable.setX(0f);
        Timeline.createSequence()
                .push(Tween.to(mapTable, ActorAccessor.MOVE_X, 0.5f).target(-mainStage.getWidth())
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                mapTable.setVisible(false);
                            }
                        }))
                .beginParallel()
                .push(Tween.to(imageBackground, ActorAccessor.MOVE_Y, 1f).target(backgroundDescent[currentBackground.get()]))
                .push(Tween.to(notebookBackground, ActorAccessor.MOVE_Y, 1f).target(0f))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        audioIcon.setVisible(true);
                        Assets.hangmanAudios[currentBackground.get()].play();
                    }
                })
                .start(tweenManager);



        acceptButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //Stop the dialog
                final AtomicBoolean wasPlaying =
                        new AtomicBoolean(Assets.hangmanAudios[currentBackground.get()].isPlaying());
                final AtomicBoolean backgroundChanged = new AtomicBoolean(false);
                Assets.hangmanAudios[currentBackground.get()].pause();
                soundButton.setChecked(true);

                //Disable the game
                hangmanPuzzle.setTouchable(Touchable.disabled);
                final Color auxColor = hangmanPuzzle.getSpaceFilledStyle().fontColor;
                if (!hangmanPuzzle.isCorrect()) {
                    addScore(-4);

                    hangmanPuzzle.getSpaceFilledStyle().fontColor = Color.MAROON;
                    Assets.failFX.play();
                    Timeline.createSequence()
                            .pushPause(0.75f)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    hangmanPuzzle.getSpaceFilledStyle().fontColor = auxColor;
                                    hangmanPuzzle.setTouchable(Touchable.childrenOnly);

                                    //Resume the sound
                                    if (wasPlaying.get()) {
                                        Assets.hangmanAudios[currentBackground.get()].play();
                                        soundButton.setChecked(false);
                                    }
                                }
                            })
                            .start(tweenManager);
                    return;
                }

                addScore(12);

                final AtomicInteger oldWord = new AtomicInteger(currentWord.get());
                final AtomicInteger oldBackground = new AtomicInteger(currentBackground.get());

                Timeline correctWordSequence = Timeline.createSequence()
                        .push(Tween.mark().setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Assets.successFX.play();
                                hangmanPuzzle.getSpaceFilledStyle().fontColor = Color.OLIVE;
                            }
                        }))
                        .pushPause(0.75f)
                        .push(game.getNotebook().addElement(
                                mainStage,
                                wordsForBackgroundNotebook[oldBackground.get()][oldWord.get()]
                        ));

                Timeline switchWordsSequence = Timeline.createSequence()
                        .push(Tween.to(hangmanPuzzle, ActorAccessor.MOVE_Y, 0.5f).target(-mainStage.getHeight()))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                hangmanPuzzle.clear();
                                fillHangman(
                                        hangmanPuzzle,
                                        wordsForBackground[currentBackground.get()][currentWord.get()],
                                        lettersInPlacePerWord[currentBackground.get()][currentWord.get()],
                                        soundButton, acceptButton);
                                extraWord.setText(extraWordPerWord[currentBackground.get()][currentWord.get()]);
                                hangmanPuzzle.addActor(extraWord);
                                hangmanPuzzle.setY(-mainStage.getHeight());
                                if (wasPlaying.get())
                                    soundButton.setChecked(false);

                                final Tween continueSwitch = Tween.to(hangmanPuzzle, ActorAccessor.MOVE_Y, 0.5f).target(0)
                                        .setCallback(new TweenCallback() {
                                            @Override
                                            public void onEvent(int type, BaseTween<?> source) {
                                                //Resume the sound
                                                if (wasPlaying.get())
                                                    Assets.hangmanAudios[currentBackground.get()].play();
                                                Assets.hangmanAudios[currentBackground.get()]
                                                        .setOnCompletionListener(new Music.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(Music music) {
                                                                soundButton.setChecked(true);
                                                            }

                                                        });
                                                hangmanPuzzle.setTouchable(Touchable.childrenOnly);
                                            }
                                        });
                                if (backgroundChanged.get()) {
                                    backgroundChanged.set(false);
                                    audioIcon.setVisible(true);

                                    Assets.hangmanAudios[currentBackground.get()].play();
                                    Assets.hangmanAudios[currentBackground.get()].setOnCompletionListener(
                                            new Music.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(Music music) {
                                                    soundButton.setChecked(true);
                                                    audioIcon.setVisible(false);
                                                    continueSwitch.start(tweenManager);
                                                }
                                            });
                                } else {
                                    continueSwitch.start(tweenManager);
                                }
                            }
                        });


                //Do one more word
                if (currentWord.addAndGet(1) < wordsForBackground[currentBackground.get()].length) {
                    Timeline.createSequence()
                            .push(correctWordSequence)
                            .push(switchWordsSequence)
                            .start(tweenManager);

                }
                //Change backgroundStage and continue if the last element of exerciseToBackground is this one
                else if (exerciseToBackground[currentExercise.get()][exerciseToBackground[currentExercise.get()].length - 1]
                        != currentBackground.get()) {
                    currentWord.set(0);

                    final Image tempBackground = new Image(imageBackground.getDrawable());
                    tempBackground.setVisible(false);
                    tempBackground.setPosition(imageBackground.getX(), imageBackground.getY());
                    backgroundStage.getRoot().addActorBefore(imageBackground, tempBackground);

                    Timeline.createSequence()
                            .push(correctWordSequence)
                            .push(Tween.mark().setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    imageBackground.setDrawable(new TextureRegionDrawable(
                                            Assets.level1scene2_2.findRegion("background" + currentBackground.addAndGet(1))));
                                    imageBackground.setPosition(backgroundStage.getWidth(), backgroundDescent[currentBackground.get()]);
                                    tempBackground.setVisible(true);
                                    backgroundChanged.set(true);
                                    wasPlaying.set(false);

                                }
                            }))
                            .beginParallel()
                            .push(switchWordsSequence)
                            .push(Tween.to(tempBackground, ActorAccessor.MOVE_X, 1f).target(-backgroundStage.getWidth())
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            backgroundStage.getRoot().removeActor(tempBackground);
                                        }
                                    }))
                            .push(Tween.to(imageBackground, ActorAccessor.MOVE_X, 1f).target(0f))
                            .end()
                            .start(tweenManager);

                }

                //In last exercise, get out!
                else if (currentExercise.addAndGet(1) == exerciseToBackground.length)
                    Timeline.createSequence()
                            .push(correctWordSequence)
                            .beginParallel()
                            .push(Tween.to(imageBackground, ActorAccessor.MOVE_Y, 1f).target(mainStage.getHeight()))
                            .push(Tween.to(notebookBackground, ActorAccessor.MOVE_Y, 1f).target(-mainStage.getHeight()))
                            .push(Tween.to(hangmanPuzzle, ActorAccessor.MOVE_Y, 1f).target(-mainStage.getHeight()))
                            .end()
                            .push(Tween.to(backgroundStage.getRoot().getChildren().first(), ActorAccessor.ALPHA, 0.5f)
                                    .target(0f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    thirdScene();
                                }
                            })
                            .start(tweenManager);

                else {

                    currentPin.setVisible(false);
                    nextPin.setVisible(true);
                    exerciseMarker.setDrawable(
                            new TextureRegionDrawable(Assets.level1scene2_1.findRegion("marker_complete")));

                    mapTable.setX(-mainStage.getWidth());
                    mapTable.setVisible(true);
                    mapTable.setTouchable(Touchable.disabled);

                    Timeline.createSequence()
                            .push(correctWordSequence)
                            .beginParallel()
                            .push(Tween.to(imageBackground, ActorAccessor.MOVE_Y, 1f).target(mainStage.getHeight()))
                            .push(Tween.to(notebookBackground, ActorAccessor.MOVE_Y, 1f).target(-mainStage.getHeight()))
                            .push(Tween.to(hangmanPuzzle, ActorAccessor.MOVE_Y, 1f).target(-mainStage.getHeight())
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            tweenManager.killTarget(audioIcon);
                                            mainStage.getRoot().removeActor(audioIcon);
                                            mainStage.getRoot().removeActor(hangmanPuzzle);
                                            backgroundStage.getRoot().removeActor(imageBackground);
                                            backgroundStage.getRoot().removeActor(notebookBackground);
                                        }
                                    }))
                            .end()
                            .push(Tween.to(mapTable, ActorAccessor.MOVE_X, 0.5f).target(0)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            mapTable.setTouchable(Touchable.childrenOnly);
                                        }
                                    }))
                            .start(tweenManager);
                }

            }
        });

    }

    private void fillHangman(final Hangman hangmanPuzzle, String word, int lettersInPlace,
                             Button soundButton, Button acceptButton){
        hangmanPuzzle.setSpaceToFillText(' ');

        Label.LabelStyle wordStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);
        wordStyle.background = new TextureRegionDrawable(Assets.level1scene2_2.findRegion("beige_word_panel"));
        hangmanPuzzle.setRandomLettersStyle(wordStyle); hangmanPuzzle.setRandomLettersScale(1.5f);
        hangmanPuzzle.setHoverStyle(wordStyle); hangmanPuzzle.setHoverScale(1.5f);
        hangmanPuzzle.setDragStyle(wordStyle); hangmanPuzzle.setDragScale(1.5f);
        hangmanPuzzle.setSpaceFilledStyle(wordStyle); hangmanPuzzle.setSpaceFilledScale(1.5f);

        Label.LabelStyle afterDragStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.LIGHT_GRAY);
        afterDragStyle.background = new TextureRegionDrawable(Assets.level1scene2_2.findRegion("black_word_panel"));
        hangmanPuzzle.setAfterDragStyle(afterDragStyle); hangmanPuzzle.setWordInPlaceStyle(afterDragStyle);
        hangmanPuzzle.setDragStartScale(1.5f);

        Label.LabelStyle spacesToFillStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        spacesToFillStyle.background = new TextureRegionDrawable(Assets.level1scene2_2.findRegion("grey_word_panel"));
        hangmanPuzzle.setSpacesToFillStyle(spacesToFillStyle); hangmanPuzzle.setSpacesToFillScale(1.5f);

        hangmanPuzzle.setBackground(new TextureRegionDrawable(Assets.level1scene2_2.findRegion("lower_panel")));
        hangmanPuzzle.setBounds((mainStage.getWidth() - hangmanPuzzle.getBackground().getMinWidth()) / 2, 0f,
                hangmanPuzzle.getBackground().getMinWidth(), hangmanPuzzle.getBackground().getMinHeight());
        hangmanPuzzle.setBackground(new TextureRegionDrawable(Assets.level1scene2_2.findRegion("lower_panel")));
        hangmanPuzzle.center().bottom();

        final ImageButton hintButton = new ImageButton(
                new TextureRegionDrawable(Assets.level1scene2_2.findRegion("hint_button")));
        hintButton.setPosition(582f, 471f);
        hintButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Dialog warning = new Dialog("", new Window.WindowStyle(
                        uiSkin.getFont("default-font"),
                        Assets.darkBrown,
                        new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_panel"))
                )){
                    @Override
                    protected void result(Object object) {
                        if(((String)object).contains("yes")) {
                            if (hangmanPuzzle.putWordInPlace())
                                addScore(-3);
                            if(hangmanPuzzle.allWordsInPlace())
                                hintButton.setVisible(false);
                        }
                    }
                };

                warning.getContentTable().padTop(40f).defaults().padBottom(20f);
                warning.getButtonTable().padBottom(51f);

                Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);

                Label title = new Label("Usar pista?", labelStyle);
                title.setAlignment(Align.center); warning.text(title).getContentTable().row();

                Label message = new Label("Usar ajudas diminui\na pontuação do nível", labelStyle);
                message.setAlignment(Align.center); message.setFontScale(0.8f);
                warning.text(message).getContentTable().row();

                TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                        new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_up")),
                        new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_down")),
                        null, uiSkin.getFont("default-font")
                );
                buttonStyle.fontColor = Assets.darkBrown;
                warning.button("Sim", "yes", buttonStyle);
                warning.button("Não", "no", buttonStyle);
                warning.show(mainStage);

            }
        });


        hangmanPuzzle.addActor(hintButton);

        soundButton.setPosition(516f, 474f);
        hangmanPuzzle.addActor(soundButton);

        hangmanPuzzle.setWord(word, word.length(), lettersInPlace);
        hangmanPuzzle.setPadBetweenAnswerAndRandom(68f);

        Label[] pieces = hangmanPuzzle.getRandomWordsLabels();
        for (int i = 0; i < pieces.length; i++) {
            float size = pieces[i].getStyle().background.getMinHeight();

            if (i % 2 == 1)
                hangmanPuzzle.getCell(pieces[i]).padTop(MathUtils.random(size, size * 2)).padLeft(4f);
            else
                hangmanPuzzle.getCell(pieces[i]).padTop(MathUtils.random(0, size)).padLeft(4f);
        }
        acceptButton.setOrigin(acceptButton.getPrefWidth()/2, acceptButton.getPrefHeight()/2);
        acceptButton.setScale(0.6f);
        hangmanPuzzle.add(acceptButton).padTop(5f).padBottom(25f).colspan(word.length());

    }

    protected void thirdScene() {
        changeScene(SceneValue.SCENE_3);
        backgroundStage.clear();
        mainStage.clear();

        final String[][] correctWords = {
                {"mapa cor-de-rosa", "angola"},
                {"inglaterra", "d. carlos", "ultimato", "descontente"},
                {"monarquia", "bretões", "a portuguesa"},
                {"aumentou", "bancos", "desemprego"},
                {"republicano", "eleições"},
                {"republicano", "carbonária"}
        };

        final String[][] randomWords = {
                {"mapa cor-de-rosa", "angola", "inglaterra", "ultimato"},
                {"ultimato", "d. carlos", "inglaterra", "pedido", "descontente"},
                {"monarquia", "bretões", "a portuguesa", "portugueses", "carbonária"},
                {"desemprego", "emprego", "aumentou", "melhorou", "bancos"},
                {"eleições", "bancos", "associações", "republicano", "conservador"},
                {"comunista", "carbonária", "republicano", "d. amélia"}
        };

        final Notebook.Element[][] elements = {
                {Notebook.Element.TEXT_FROM_1890_TITLE, Notebook.Element.TEXT_FROM_1890_PART1},
                {Notebook.Element.TEXT_FROM_1890_PART2},
                {Notebook.Element.TEXT_FROM_1890_PART3},
                {Notebook.Element.TEXT_FROM_1890_PART4},
                {Notebook.Element.TEXT_FROM_1890_PART5},
                {Notebook.Element.TEXT_FROM_1890_PART6},
        };

        final float[][][] wordsPositions = {
                {{245f, 564f}, {305f, 408f}},
                {{46f, 590f}, {136f, 538f}, {46f, 486f}, {46f, 279f}},
                {{254f, 643f}, {87f, 381f}, {322f, 330f}},
                {{212f, 590f}, {46f, 486f}, {46f, 434f}},
                {{298f, 590f}, {277f, 381f}},
                {{163f, 538f}, {41f, 486f}}
        };

        final AtomicInteger currentParagraph = new AtomicInteger(0);


        final Image imageBackground = new Image(Assets.level1scene3.findRegion("background"));
        imageBackground.getColor().a = 0f;
        backgroundStage.addActor(imageBackground);

        final Table notebookTable = new Table();
        notebookTable.setBackground(new TextureRegionDrawable(Assets.level1scene3.findRegion("notepad")));
        notebookTable.setSize(
                notebookTable.getBackground().getMinWidth(),
                notebookTable.getBackground().getMinHeight());

        final MissingWords missingWords = new MissingWords(
                uiSkin, new TextureRegionDrawable(
                Assets.level1scene3.findRegion("text" + (currentParagraph.get() + 1))), 1f);

        final Label.LabelStyle spaceStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK);
        spaceStyle.background = new TextureRegionDrawable(Assets.level1scene3.findRegion("blank_space"));
        missingWords.setHoverStyle(spaceStyle);
        missingWords.getDragStyle().fontColor = Color.BLACK;

        missingWords.setImageTable(spaceStyle, 1f, spaceStyle.background.getMinWidth(),
                wordsPositions[currentParagraph.get()], correctWords[currentParagraph.get()]);

        notebookTable.addActor(missingWords.getImageTable());
        missingWords.getImageTable().setY(26f);

        missingWords.setRandomWordScale(0.95f);
        missingWords.setDragScale(0.95f);
        missingWords.setCorrectWordsScale(0.95f);
        missingWords.setHoverScale(0.95f);


        missingWords.getWordsTable().center().top().padTop(667f)
                .defaults().padRight(42f);
        missingWords.getWordsTable().setSize(
                notebookTable.getBackground().getMinWidth(),
                notebookTable.getBackground().getMinHeight());
        missingWords.getWordsTable()
                .setBackground(new TextureRegionDrawable(Assets.level1scene3.findRegion("lower_panel")));


        missingWords.setWordsTable(new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE),
                randomWords[currentParagraph.get()], 2, true);
        //Setting different pads for the first row
        for (Cell<?> cell : missingWords.getWordsTable().getCells()) {
            cell.left();
            if (cell.getColumn() == 0)
                cell.padTop(25f);
        }
        notebookTable.addActor(missingWords.getWordsTable());

        notebookTable.setY(-mainStage.getHeight());
        notebookTable.setTouchable(Touchable.disabled);
        mainStage.addActor(notebookTable);

        missingWords.setCallback(new MissingWords.MissingWordsCallback() {
            @Override
            public void onCorrect(Label target) {
                addScore(16);
                Assets.successFX.play();
            }

            @Override
            public void onWrong(Label source, Label target) {
                addScore(-5);
                missingWords.getWordsTable().setTouchable(Touchable.disabled);
                Label.LabelStyle tempStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.MAROON);
                tempStyle.background = target.getStyle().background;
                target.setStyle(tempStyle);
                final Label sourceTemp = source;
                final Label targetTemp = target;
                Timeline.createSequence()
                        .pushPause(1f)
                        .push(Tween.set(target, ActorAccessor.ALPHA).target(1f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        missingWords.revertWrongAnswer(sourceTemp, targetTemp);
                                        missingWords.getWordsTable().setTouchable(Touchable.childrenOnly);
                                    }
                                }))
                        .start(tweenManager);
                Assets.failFX.play();
            }

            @Override
            public void onCompleted() {
                notebookTable.setTouchable(Touchable.disabled);
                Timeline sequence = Timeline.createSequence()
                        .pushPause(0.7f)
                        .push(game.getNotebook().addElement(mainStage, elements[currentParagraph.get()]))
                        .push(Tween.to(notebookTable, ActorAccessor.MOVE_Y, 0.5f)
                                .target(-notebookTable.getBackground().getMinHeight()));

                if (currentParagraph.addAndGet(1) != correctWords.length) {
                    sequence.push(Tween.mark().setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            missingWords.getImageTable().setBackground(new TextureRegionDrawable(
                                    Assets.level1scene3.findRegion("text" + (currentParagraph.get() + 1))));
                            missingWords.setImageTable(spaceStyle, 1f, spaceStyle.background.getMinWidth(),
                                    wordsPositions[currentParagraph.get()], correctWords[currentParagraph.get()]);
                            missingWords.setWordsTable(missingWords.getRandomWordsStyle(),
                                    randomWords[currentParagraph.get()], 2, true);
                            //Setting different pads for the first row
                            for (Cell<?> cell : missingWords.getWordsTable().getCells()) {
                                cell.left();
                                if (cell.getColumn() == 0)
                                    cell.padTop(25f);
                            }
                        }
                    }))
                            .push(Tween.to(notebookTable, ActorAccessor.MOVE_Y, 0.5f).target(0f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    notebookTable.setTouchable(Touchable.childrenOnly);
                                }
                            });
                } else {
                    sequence.setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            finishLevel(Game1910.ScreenType.LEVEL_SELECT);
                        }
                    });
                }

                sequence.start(tweenManager);

            }
        });

        //Intro animation
        Timeline.createSequence()
                .push(Tween.to(imageBackground, ActorAccessor.ALPHA, 0.5f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                            @Override
                            public void onCompleted() {
                                Tween.to(notebookTable, ActorAccessor.MOVE_Y, 0.5f).target(0f)
                                        .setCallback(new TweenCallback() {
                                            @Override
                                            public void onEvent(int type, BaseTween<?> source) {
                                                notebookTable.setTouchable(Touchable.childrenOnly);
                                            }
                                        })
                                        .start(tweenManager);
                            }
                        });
                        reporterThought.run(wordsHelp, tweenManager);
                    }
                })
                .start(tweenManager);
    }

    @Override
    protected void forthScene() {

    }

    @Override
    protected void fifthScene() {

    }
}
