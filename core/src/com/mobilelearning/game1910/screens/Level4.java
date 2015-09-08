package com.mobilelearning.game1910.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.SavedData;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.minigames.MissingWords;
import com.mobilelearning.game1910.uiUtils.Conversation;
import com.mobilelearning.game1910.uiUtils.DialogBox;
import com.mobilelearning.game1910.uiUtils.DialogCallback;
import com.mobilelearning.game1910.uiUtils.Notebook;
import com.mobilelearning.game1910.uiUtils.ReporterThought;
import com.mobilelearning.game1910.uiUtils.RgbColor;

import java.util.concurrent.atomic.AtomicInteger;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;

/**
 * Created by AFFonseca on 12/07/2015.
 */
public class Level4 extends LevelScreen {

    private static final String level4IntroThought = "Um ano se passou e muitas coisas já mudaram... " +
            "Acompanha-me na redação de uma notícia que as sintetiza.";

    private static final String moneyChangeComment = "Como 1000 réis corresponde a 1 escudo, os $oldCoin réis " +
            "que somei ao longo da minha carreira como jornalista são agora $newCoin1 escudos e $newCoin2 centavos." +
            " O valor é o mesmo, mas já não é preciso tantas notas ou moedas para comprar qualquer bem." +
            " Esta será uma moeda mais forte!";

    private static final String flagHelp = "Necessito de pintar corretamente a bandeira para que na tipografia a " +
            "possam imprimir. Ajuda-me a não esquecer nenhum detalhe.";

    private static final String anthemHelp = "O hino de uma nação revela muito sobre a sua vivência. Ajuda-me " +
            "a completar os espaços com as palavras que marcaram esta nossa luta.";

    private static final String [] votingPhoneCall = {
            "Bom dia! No Clube Estefânia está instalada a mesa de voto da freguesia de S. Jorge de Arroios. " +
                    "Estão várias mulheres à porta e consta-se que falam em direito de voto. Seria bom averiguar " +
                    "o que se passa.",
            "Eu próprio irei averiguar no local."
    };

    private static final String [] votingPhoneCallOrder = {"F", "R"};

    private static final String measuresHelp = "Hoje Portugal é uma República ainda muito jovem mas que muitas mudanças" +
            " trouxe. Ajuda-me a classificar corretamente cada uma destas mudanças para o último artigo que vou assinar." +
            " O cargo de diretor pouco tempo me deixa para investigar e escrever...";

    private static final String level4FinalThought = "Muito obrigado por me ajudares durante todos estes anos. O teu apoio" +
            " foi fundamental. Agora não te esqueças de transmitir a outros como tudo se passou! Viva a República!";

    public Level4(Game1910 game, PolygonSpriteBatch batch) {
        super(game, batch, Assets.maxLevelScores[3]);
    }

    @Override
    public void load() {
        Assets.loadLevel4();
    }

    @Override
    public void prepare() {
        super.prepare();
        game.getNotebook().updateNotebook(Assets.levelValues[3], true);
        game.getNotebook().addElementNoAnimation(Notebook.Element.LEVEL_1911);
        Assets.prepareLevel4();
        soundPool.add(Assets.phoneFX);
        musicPool.add(Assets.audio10);
    }

    @Override
    public void unload() {
        Assets.unloadLevel4();
    }

    @Override
    public void show() {
        firstScene();
    }

    protected void firstScene(){
        mainStage.clear();
        backgroundStage.clear();

        final Image firstBackground = new Image(Assets.level4scene4.findRegion("background1"));
        firstBackground.getColor().a = 0f; backgroundStage.addActor(firstBackground);

        float totalCoins = (SavedData.getLevel1Highscore() + SavedData.getLevel2Highscore()
                + SavedData.getLevel3Highscore())*scoreToMoney;
        final String thinkText = moneyChangeComment.replace("$oldCoin", "" +MathUtils.round(totalCoins))
                .replace("$newCoin1", "" + MathUtils.round(totalCoins / 1000))
                .replace("$newCoin2", "" + MathUtils.round(((totalCoins % 1000) / 10f)));

        final Image background = new Image(Assets.level4scene1.findRegion("background_blurred"));
        background.getColor().a = 0f; backgroundStage.addActor(background);

        final Image scene = new Image(Assets.level4scene1.findRegion("background_normal"));
        scene.getColor().a = 0f; mainStage.addActor(scene);

        Label.LabelStyle thinkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);

        final TextureRegionDrawable thoughtBalloon = new TextureRegionDrawable(Assets.level4scene1.findRegion("thought_balloon"));
        final DialogBox thoughtBox = new DialogBox(null, thinkStyle,
                155f, 544f, thoughtBalloon.getMinWidth(), thoughtBalloon.getMinHeight());
        thoughtBox.setBackground(thoughtBalloon); thoughtBox.setVisible(false);
        thoughtBox.setTextPad(30f, 30f, 30f, 150f);
        thoughtBox.setClickAnimationPosition(
                thoughtBalloon.getMinWidth() - thoughtBox.getClickAnimation().getPrefWidth() + 30f, 68f);
        thoughtBox.addToStage(mainStage);
        thoughtBox.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type == CLICKED_AFTER_END) {
                    thoughtBox.setVisible(false);
                    Timeline.createParallel()
                            .push(Tween.to(background, ActorAccessor.ALPHA, 1f).target(0f))
                            .push(Tween.to(scene, ActorAccessor.ALPHA, 1f).target(0f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    secondScene();
                                }
                            }).start(tweenManager);
                }
            }
        });

        Tween.to(firstBackground, ActorAccessor.ALPHA, 3f).target(1f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                    @Override
                    public void onCompleted() {
                        Timeline.createSequence()
                                .push(Tween.to(firstBackground, ActorAccessor.ALPHA, 1f).target(0f))
                                .beginParallel()
                                .push(Tween.to(background, ActorAccessor.ALPHA, 1f).target(1f))
                                .push(Tween.to(scene, ActorAccessor.ALPHA, 1f).target(1f))
                                .end()
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        firstBackground.remove();
                                        thoughtBox.setVisible(true);
                                        thoughtBox.setText(thinkText);
                                    }
                                }).start(tweenManager);
                    }
                });
                reporterThought.run(level4IntroThought, tweenManager);
            }
        }).start(tweenManager);


    }

    protected void secondScene(){
        changeScene(SceneValue.SCENE_2);
        mainStage.clear();
        backgroundStage.clear();

        final Color [] colorValues = {
                new RgbColor(255f, 255f, 0f, 255f), //yellow
                new RgbColor(96f, 89f, 107f, 255f), //grey
                new RgbColor(53f, 83f, 248f, 255f), //blue
                new RgbColor(229f, 34f, 22f, 255f), //red
                new RgbColor(221f, 24f, 230f, 255f), //purple
                new RgbColor(217f, 123f, 17f, 255f), //orange
                new RgbColor(47f, 157f, 71f, 255f), //green
        };

        final Color [] currentColor = {colorValues[0]};

        final float flagElementsPositions [][] = { {0f, 335f}, {323f, 335f}, {85f, 418f}, {182f, 490f},
                {186f, 496f}, {255f, 571f}
        };

        final float castlePositions [][] = { {192f, 761f}, {300f, 761f}, {407f, 761f}, {192f, 652f},
                {407f, 652f}, {239f, 531f}, {372f, 559f}};

        final int correctFlagColors [] = {6/*green*/, 3/*red*/, 0/*yellow*/, 4/*whatever*/, 3/*red*/, 2/*blue*/};

        final int [] remainingFlagElementsAndCastlesToColor = {flagElementsPositions.length-1 + castlePositions.length};

        final Image background = new Image(Assets.level4scene2.findRegion("background"));
        backgroundStage.addActor(background);

        final ImageButton finishButton = new ImageButton(
                new TextureRegionDrawable(Assets.level4scene2.findRegion("play_button_up")),
                new TextureRegionDrawable(Assets.level4scene2.findRegion("play_button_down"))
        );

        final Image [] flagParts = new Image[flagElementsPositions.length];
        for(int i=0; i<flagElementsPositions.length; i++){
            if(i!=2)
                flagParts[i] = new Image(Assets.level4scene2.findRegion("flag" +(i+1)));
            else { ////changing the box of the part 3 to a circular one
                flagParts[i] = new Image(Assets.level4scene2.findRegion("flag" +(i+1))){
                    @Override
                    public Actor hit(float x, float y, boolean touchable) {
                        //Does in belong in the circular area around actor? If so continue, else no touch
                        if(Math.pow((double)(x-getWidth()/2), 2) +
                                Math.pow((double)(y-getHeight()/2), 2) <=
                                Math.pow(getHeight()/2, 2))
                            return super.hit(x, y, touchable);
                        else
                            return null;
                    }
                };

            }
            flagParts[i].setPosition(flagElementsPositions[i][0], flagElementsPositions[i][1]);
            if(i != 3) //this will have no color
                flagParts[i].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        if(event.getListenerActor().getColor().equals(Color.WHITE))
                            remainingFlagElementsAndCastlesToColor[0]--;

                        event.getListenerActor().setColor(currentColor[0]);

                        if(remainingFlagElementsAndCastlesToColor[0] == 0)
                            finishButton.setVisible(true);
                    }
                });
            mainStage.addActor(flagParts[i]);
        }

        final  Image [] castles = new Image[castlePositions.length];
        for(int i=0; i<castlePositions.length; i++){
            castles[i] = new Image(Assets.level4scene2.findRegion("flag7"));
            castles[i].setPosition(castlePositions[i][0], castlePositions[i][1]);
            castles[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if(event.getListenerActor().getColor().equals(Color.WHITE))
                        remainingFlagElementsAndCastlesToColor[0]--;

                    event.getListenerActor().setColor(currentColor[0]);

                    if(remainingFlagElementsAndCastlesToColor[0] == 0)
                        finishButton.setVisible(true);
                }
            });
            mainStage.addActor(castles[i]);
        }
        //rotating last two castles
        castles[5].setRotation(45f); castles[6].setRotation(-45f);

        final Table colorTable = new Table();
        colorTable.setSize(mainStage.getWidth(), 335f);
        colorTable.top().padTop(28f).left().padLeft(57f).defaults().padRight(20f).padBottom(14f);

        final Image brush = new Image(Assets.level4scene2.findRegion("brush1"));
        brush.setPosition(390f, 115f); mainStage.addActor(brush);

        final Image buttonHighlight = new Image(Assets.level4scene2.findRegion("color_selected"));

        final TextureRegionDrawable [] brushColors = new TextureRegionDrawable[colorValues.length];
        for(int i=0; i<colorValues.length; i++){
            final AtomicInteger aux = new AtomicInteger(i);
            Image colorButton = new Image(Assets.level4scene2.findRegion("color" +(i+1)));
            brushColors[i] = new TextureRegionDrawable(Assets.level4scene2.findRegion("brush" +(i+1)));
            colorButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    currentColor[0] = colorValues[aux.get()];
                    brush.setDrawable(brushColors[aux.get()]);
                    buttonHighlight.setPosition(
                            event.getListenerActor().getX(),
                            event.getListenerActor().getY());
                }
            });
            colorTable.add(colorButton);
            if(i==3)
                colorTable.row();
        }

        colorTable.layout(); //needed for the positions to be correct
        buttonHighlight.setPosition(
                colorTable.getCells().first().getActor().getX(),
                colorTable.getCells().first().getActor().getY());
        colorTable.addActor(buttonHighlight);

        mainStage.addActor(colorTable);

        finishButton.setVisible(false);
        finishButton.setPosition(485f, 205f); mainStage.addActor(finishButton);
        finishButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                finishButton.setTouchable(Touchable.disabled);
                colorTable.setTouchable(Touchable.disabled);

                final Timeline flagSequences[] = new Timeline[flagParts.length];
                for (int i = 0; i < flagParts.length; i++) {
                    flagParts[i].setTouchable(Touchable.disabled);
                    final Color currentColor = new Color(flagParts[i].getColor());
                    final Color correctColor = new Color(colorValues[correctFlagColors[i]]);

                    flagSequences[i] = Timeline.createSequence()
                            .push(Tween.to(flagParts[i], ActorAccessor.COLOR, 0.35f).target(1f, 1f, 1f).repeatYoyo(3, 0f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            if (currentColor.equals(correctColor)) {
                                                addScore(20);
                                                Assets.successFX.play();
                                            } else {
                                                addScore(15);
                                                Assets.failFX.play();
                                            }
                                        }
                                    }))
                            .push(Tween.set(flagParts[i], ActorAccessor.COLOR)
                                    .target(correctColor.r, correctColor.g, correctColor.b))
                            .pushPause(1f);
                }

                final Timeline castleSequences[] = new Timeline[castles.length];
                for (int i = 0; i < castles.length; i++) {
                    castles[i].setTouchable(Touchable.disabled);
                    final Color currentColor = new Color(castles[i].getColor());
                    final Color correctColor = new Color(colorValues[0]);

                    int numberOfRepeats = 1;
                    if (i == 0) {
                        numberOfRepeats = 3;
                    }
                    castleSequences[i] = Timeline.createSequence()
                            .push(Tween.to(castles[i], ActorAccessor.COLOR, 0.35f)
                                    .target(1f, 1f, 1f).repeatYoyo(numberOfRepeats, 0f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            if (currentColor.equals(correctColor)) {
                                                addScore(5);
                                                Assets.successFX.play();
                                            } else {
                                                addScore(3);
                                                Assets.failFX.play();
                                            }
                                        }
                                    }))
                            .push(Tween.set(castles[i], ActorAccessor.COLOR)
                                    .target(correctColor.r, correctColor.g, correctColor.b))
                            .pushPause(1f);
                }

                final Image mask = new Image(Assets.level4scene2.findRegion("black_pixel"));
                mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                        mainStage.getHeight() + mainStage.getPadBottom() * 2);
                mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
                mask.setTouchable(Touchable.disabled);
                mask.getColor().a = 0.0f;
                mainStage.addActor(mask);

                final Image informations[] = new Image[5];
                final Timeline fadeIns[] = new Timeline[5];
                final Timeline fadeOuts[] = new Timeline[5];
                for (int i = 0; i < informations.length; i++) {
                    final AtomicInteger index = new AtomicInteger(i);
                    informations[i] = new Image(Assets.level4scene2.findRegion("information" + (i + 1)));
                    informations[i].getColor().a = 0f;

                    fadeIns[i] = Timeline.createParallel()
                            .push(Tween.mark()
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            mainStage.addActor(informations[index.get()]);
                                            informations[index.get()].setTouchable(Touchable.disabled);
                                        }
                                    }))
                            .push(Tween.to(informations[i], ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0.8f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            informations[index.get()].setTouchable(Touchable.enabled);
                                        }
                                    }));

                    fadeOuts[i] = Timeline.createParallel()
                            .push(Tween.mark()
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            informations[index.get()].setTouchable(Touchable.disabled);
                                        }
                                    }))
                            .push(Tween.to(informations[i], ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            informations[index.get()].remove();
                                        }
                                    }));
                }

                informations[0].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Timeline.createSequence()
                                .push(fadeOuts[0])
                                .push(flagSequences[1])
                                .push(fadeIns[1])
                                .start(tweenManager);
                    }
                });

                informations[1].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Timeline.createSequence()
                                .push(fadeOuts[1])
                                .push(flagSequences[2])
                                .push(fadeIns[2])
                                .start(tweenManager);
                    }
                });

                informations[2].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Timeline.createSequence()
                                .push(fadeOuts[2])
                                .push(flagSequences[4])
                                .push(flagSequences[5])
                                .push(castleSequences[0])
                                .push(castleSequences[1])
                                .push(castleSequences[2])
                                .push(castleSequences[3])
                                .push(castleSequences[4])
                                .push(castleSequences[5])
                                .push(castleSequences[6])
                                .push(fadeIns[3])
                                .start(tweenManager);
                    }
                });

                informations[3].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Timeline.createSequence()
                                .push(fadeOuts[3])
                                .pushPause(1f)
                                .push(game.getNotebook().addElement(mainStage, Notebook.Element.FLAG_PICTURE))
                                .push(fadeIns[4])
                                .start(tweenManager);
                    }
                });

                informations[4].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Timeline.createSequence()
                                .push(fadeOuts[4])
                                .pushPause(0.5f)
                                .push(Tween.to(mask, ActorAccessor.ALPHA, 1f).target(1f))
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        thirdScene();
                                    }
                                })
                                .start(tweenManager);
                    }
                });

                Timeline.createSequence()
                        .push(flagSequences[0])
                        .push(fadeIns[0])
                        .start(tweenManager);
            }
        });

        colorTable.setTouchable(Touchable.disabled);
        for(Image flagPart : flagParts) {
            flagPart.setTouchable(Touchable.disabled);
        }
        for(Image castle : castles) {
            castle.setTouchable(Touchable.disabled);
        }
        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                colorTable.setTouchable(Touchable.childrenOnly);
                for(Image flagPart : flagParts) {
                    flagPart.setTouchable(Touchable.enabled);
                }
                for(Image castle : castles) {
                    castle.setTouchable(Touchable.enabled);
                }
            }
        });
        reporterThought.run(flagHelp, tweenManager);

        game.getNotebook().addElementNoAnimation(Notebook.Element.FLAG_TITLE, Notebook.Element.FLAG_TEXT_PART1,
                Notebook.Element.FLAG_TEXT_PART2, Notebook.Element.FLAG_TEXT_PART3, Notebook.Element.FLAG_TEXT_PART4);

    }

    protected void thirdScene(){
        changeScene(SceneValue.SCENE_3);
        mainStage.clear();
        backgroundStage.clear();

        backgroundStage.clear();
        mainStage.clear();

        final String[][] correctWords = {
                {"povo", "valente", "portugal"},
                {"pátria", "avós", "vitória"},
                {"armas", "mar", "lutar", "marchar"}
        };

        final String[][] randomWords = {
                {"povo", "valente", "portugal", "avós", "forte"},
                {"pátria", "avós", "vitória", "portugal", "povo"},
                {"armas", "mar", "lutar", "marchar", "trabalhar"}
        };

        final Notebook.Element[][] elements = {
                {Notebook.Element.ANTHEM_TITLE, Notebook.Element.ANTHEM_PART1},
                {Notebook.Element.ANTHEM_PART2}, {Notebook.Element.ANTHEM_PART3}
        };

        final float[][][] wordsPositions = {
                {{344f, 588f}, {141f, 536f}, {261f, 432f}},
                {{76f, 536f}, {296f, 484f}, {337f, 432f}},
                {{233f, 588f}, {365f, 536f}, {216f, 432f}, {39f, 328f}}
        };

        final AtomicInteger currentParagraph = new AtomicInteger(0);


        final Image imageBackground = new Image(Assets.level4scene2.findRegion("background"));
        imageBackground.getColor().a = 0f;
        backgroundStage.addActor(imageBackground);

        final Table notebookTable = new Table();
        notebookTable.setBackground(new TextureRegionDrawable(Assets.level4scene3.findRegion("notepad")));
        notebookTable.setSize(
                notebookTable.getBackground().getMinWidth(),
                notebookTable.getBackground().getMinHeight());

        final MissingWords missingWords = new MissingWords(
                uiSkin, new TextureRegionDrawable(
                Assets.level4scene3.findRegion("text" + (currentParagraph.get() + 1))), 1f);

        final Label.LabelStyle spaceStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK);
        spaceStyle.background = new TextureRegionDrawable(Assets.level4scene3.findRegion("blank_space"));
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
                .setBackground(new TextureRegionDrawable(Assets.level4scene3.findRegion("lower_panel")));


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
                addScore(20);
                Assets.successFX.play();
            }

            @Override
            public void onWrong(Label source, Label target) {
                addScore(15);
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
                                    Assets.level4scene3.findRegion("text" + (currentParagraph.get() + 1))));
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
                            final Image finalImage = new Image(Assets.level4scene3.findRegion("text4"));
                            finalImage.addListener(new ClickListener() {
                                public void clicked(InputEvent event, float x, float y) {
                                    Timeline.createSequence()
                                            .push(Tween.to(notebookTable, ActorAccessor.MOVE_Y, 0.5f)
                                                    .target(-notebookTable.getBackground().getMinHeight()))
                                            .push(Tween.to(imageBackground, ActorAccessor.ALPHA, 1f).target(0f))
                                            .setCallback(new TweenCallback() {
                                                @Override
                                                public void onEvent(int type, BaseTween<?> source) {
                                                    forthScene();
                                                }
                                            }).start(tweenManager);
                                }
                            });

                            notebookTable.clear();
                            notebookTable.addActor(finalImage);

                            Tween.to(notebookTable, ActorAccessor.MOVE_Y, 0.5f).target(0f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            notebookTable.setTouchable(Touchable.childrenOnly);
                                        }
                                    }).start(tweenManager);
                        }
                    });
                }
                sequence.start(tweenManager);
            }
        });

        //Intro animation
        Tween.to(imageBackground, ActorAccessor.ALPHA, 0.5f).target(1f)
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
                                        }).start(tweenManager);
                            }
                        });
                        reporterThought.run(anthemHelp, tweenManager);
                    }
                })
                .start(tweenManager);
    }

    protected void forthScene(){
        changeScene(SceneValue.SCENE_4);
        mainStage.clear();
        backgroundStage.clear();

        game.getNotebook().addElementNoAnimation(Notebook.Element.FIRST_WOMAN_VOTING_TITLE);
        final Notebook.Element firstWomanVotingElements [][] =
                {
                        {
                                Notebook.Element.FIRST_WOMAN_VOTING_PICTURE,
                        },
                        {
                                Notebook.Element.FIRST_WOMAN_VOTING_PART1,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART2,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART3,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART4,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART5,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART6,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART7,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART8,
                                Notebook.Element.FIRST_WOMAN_VOTING_PART9
                        }
                };

        final Image background1 = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("background1")));
        background1.getColor().a = 0f; backgroundStage.addActor(background1);

        final Image table = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("table")));
        table.getColor().a = 0f; table.setY(-mainStage.getPadBottom()); mainStage.addActor(table);

        final Image phone = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("phone")));
        phone.setPosition(0f, 197f-mainStage.getPadBottom()); phone.setOriginX(phone.getPrefWidth());
        phone.getColor().a = 0f; phone.setTouchable(Touchable.disabled); mainStage.addActor(phone);

        final Image phoneWave = new Image(new TextureRegionDrawable(Assets.level4scene4. findRegion("phone_sound")));
        phoneWave.setPosition(68f, 395f-mainStage.getPadBottom());
        phoneWave.setTouchable(Touchable.disabled); //For the phone to be touchable
        phoneWave.getColor().a = 0f; mainStage.addActor(phoneWave);

        final Image reporterOnPhone = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("reporter")));
        reporterOnPhone.setPosition((mainStage.getWidth() - reporterOnPhone.getPrefWidth()) / 2, 197f-mainStage.getPadBottom());
        reporterOnPhone.setTouchable(Touchable.disabled); //For the phone to be touchable
        reporterOnPhone.getColor().a = 0f; mainStage.addActor(reporterOnPhone);

        Label.LabelStyle style = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.lightBrown);
        TextureRegionDrawable panelImage = new TextureRegionDrawable(Assets.level4scene4.findRegion("panel"));
        style.background = panelImage;
        final Label date = new Label("28 de maio de 1911", style); date.setAlignment(Align.center);
        date.setBounds((mainStage.getWidth() - panelImage.getMinWidth()) / 2,
                (mainStage.getHeight() - panelImage.getMinHeight()) - 5f,
                panelImage.getMinWidth(), panelImage.getMinHeight());
        date.setFontScale(1.05f);
        date.getColor().a = 0f; mainStage.addActor(date);

        final TextureRegionDrawable wordBalloon1 = new TextureRegionDrawable(Assets.level4scene4.findRegion("word_balloon1"));
        Label.LabelStyle talkStyle1 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        final DialogBox reporterBox1 = new DialogBox(null, talkStyle1,
                32f, 666f-mainStage.getPadBottom(), wordBalloon1.getMinWidth(), wordBalloon1.getMinHeight());
        reporterBox1.setBackground(wordBalloon1);
        reporterBox1.setTextPad(30f, 30f, 30f, 60f);
        reporterBox1.setVisible(false);
        reporterBox1.setClickAnimationPosition(
                wordBalloon1.getMinWidth()-reporterBox1.getClickAnimation().getPrefWidth()+30f, -25f);
        reporterBox1.addToStage(mainStage);

        final TextureRegionDrawable wordBalloon2 = new TextureRegionDrawable(Assets.level4scene4.findRegion("word_balloon2"));
        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        final DialogBox reporterBox2 = new DialogBox(null, talkStyle2,
                100f, 150f-mainStage.getPadBottom(), wordBalloon2.getMinWidth(), wordBalloon2.getMinHeight());
        reporterBox2.setBackground(wordBalloon2);
        reporterBox2.setTextPad(30f, 175f, 30f, 30f);
        reporterBox2.setVisible(false);
        reporterBox2.setClickAnimationPosition(
                wordBalloon2.getMinWidth() - reporterBox2.getClickAnimation().getPrefWidth()+30f, -60f);
        reporterBox2.addToStage(mainStage);

        //Elements after phone call
        final Image background2 = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("background2_blurred")));
        background2.getColor().a = 0f;

        final Image scene2 = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("background2_normal")));
        scene2.getColor().a = 0f;

        final Image interview = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("interview")));
        interview.setPosition((mainStage.getWidth()-interview.getPrefWidth())/2, -120f); interview.getColor().a = 0f;

        final Image audioIcon = new Image(new TextureRegionDrawable(Assets.level4scene4.findRegion("audio_play")));
        audioIcon.setPosition((mainStage.getWidth()-audioIcon.getPrefWidth())/2, 490f); audioIcon.setVisible(false);

        final Image paper = new Image(Assets.level4scene4.findRegion("paper"));
        paper.setPosition((mainStage.getWidth() - paper.getPrefWidth()) / 2, (mainStage.getHeight()-paper.getPrefHeight())/2);
        paper.setOrigin(paper.getPrefWidth() / 2, paper.getPrefHeight() / 2);
        paper.setScale(0f);


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
                .push(Tween.to(background1, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .push(Tween.to(table, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .push(Tween.to(phone, ActorAccessor.ALPHA, 1.5f).target(1.0f))
                .push(Tween.to(date, ActorAccessor.ALPHA, 1.5f).target(1.0f))
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
                        .push(Tween.to(background1, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(table, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(phone, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .end()
                        .beginParallel()
                        .push(Tween.to(background1, ActorAccessor.ALPHA, 0.25f).target(1f))
                        .push(Tween.to(reporterOnPhone, ActorAccessor.ALPHA, 0.25f).target(1f))
                        .push(Tween.to(table, ActorAccessor.ALPHA, 0.25f).target(1f))
                        .end()
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Conversation conversation = new Conversation(   new String[] {"R", "F"},
                                        new DialogBox[] {reporterBox1, reporterBox2},
                                        votingPhoneCallOrder, votingPhoneCall);
                                conversation.setCallback(new Conversation.ConversationCallback() {
                                    @Override
                                    public void onCompleted() {
                                        Timeline.createSequence()
                                                .beginParallel()
                                                .push(Tween.to(background1, ActorAccessor.ALPHA, 1f).target(0f))
                                                .push(Tween.to(reporterOnPhone, ActorAccessor.ALPHA, 1f).target(0f))
                                                .push(Tween.to(table, ActorAccessor.ALPHA, 1f).target(0f))
                                                .push(Tween.to(reporterBox1.getActor(), ActorAccessor.ALPHA, 1f).target(0f))
                                                .push(Tween.to(reporterBox2.getActor(), ActorAccessor.ALPHA, 1f).target(0f))
                                                .end()
                                                .push(Tween.mark().setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        backgroundStage.clear();
                                                        mainStage.clear();

                                                        paper.setTouchable(Touchable.disabled);
                                                        paper.addListener(new ClickListener() {
                                                            public void clicked(InputEvent event, float x, float y) {
                                                                paper.removeListener(this);
                                                                Timeline.createParallel()
                                                                        .push(Tween.to(background2, ActorAccessor.ALPHA, 4f).target(0f))
                                                                        .push(Tween.to(scene2, ActorAccessor.ALPHA, 4f).target(0f))
                                                                        .push(Tween.to(interview, ActorAccessor.ALPHA, 4f).target(0f))
                                                                        .push(Tween.to(paper, ActorAccessor.ALPHA, 4f).target(0f))
                                                                        .push(Tween.to(date, ActorAccessor.ALPHA, 4f).target(0f))
                                                                        .setCallback(new TweenCallback() {
                                                                            @Override
                                                                            public void onEvent(int type, BaseTween<?> source) {
                                                                                fifthScene();
                                                                            }
                                                                        }).start(tweenManager);
                                                            }
                                                        });

                                                        backgroundStage.addActor(background2);
                                                        mainStage.addActor(scene2);
                                                        mainStage.addActor(interview);
                                                        mainStage.addActor(audioIcon);
                                                        mainStage.addActor(date);
                                                        mainStage.addActor(paper);

                                                        Assets.audio10.setOnCompletionListener(new Music.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(Music music) {
                                                                tweenManager.killAll();
                                                                audioIcon.setVisible(false);
                                                                Assets.audio10.setOnCompletionListener(null);

                                                                Timeline.createSequence()
                                                                        .push(game.getNotebook().addElement(mainStage,
                                                                                        firstWomanVotingElements[0]))
                                                                        .setCallback(new TweenCallback() {
                                                                            @Override
                                                                            public void onEvent(int type, BaseTween<?> source) {
                                                                                Timeline.createSequence()
                                                                                        .push(game.getNotebook().addElement(mainStage,
                                                                                                firstWomanVotingElements[1]))
                                                                                        .beginParallel()
                                                                                        .push(Tween.to(paper, ActorAccessor.ROTATE, 3f)
                                                                                                .target(3600f).ease(Quad.OUT))
                                                                                        .push(Tween.to(paper, ActorAccessor.SCALEXY, 3f).target(1f))
                                                                                        .end()
                                                                                        .setCallback(new TweenCallback() {
                                                                                            @Override
                                                                                            public void onEvent(int type, BaseTween<?> source) {
                                                                                                paper.setTouchable(Touchable.enabled);
                                                                                            }
                                                                                        })
                                                                                        .start(tweenManager);
                                                                            }
                                                                        }).start(tweenManager);
                                                            }
                                                        });
                                                    }
                                                }))
                                                .beginParallel()
                                                .push(Tween.to(background2, ActorAccessor.ALPHA, 1f).target(1f))
                                                .push(Tween.to(scene2, ActorAccessor.ALPHA, 1f).target(1f))
                                                .push(Tween.to(interview, ActorAccessor.ALPHA, 1f).target(1f))
                                                .setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        Assets.audio10.play();
                                                        audioIcon.setVisible(true);
                                                        Tween.to(audioIcon, ActorAccessor.ALPHA, 1f)
                                                                .target(0f).repeatYoyo(Tween.INFINITY, 0.3f)
                                                                .ease(Linear.INOUT).start(tweenManager);
                                                    }
                                                })
                                                .end()
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

    protected void fifthScene(){
        changeScene(SceneValue.SCENE_5);
        mainStage.clear();
        backgroundStage.clear();

        game.getNotebook().addElementNoAnimation(Notebook.Element.MEASURES_TITLE);
        final Notebook.Element [][] notebookElements = {
                {Notebook.Element.MEASURES_PART6, Notebook.Element.MEASURES_PART7},
                {Notebook.Element.MEASURES_PART11, Notebook.Element.MEASURES_PART12},
                {Notebook.Element.MEASURES_PART16, Notebook.Element.MEASURES_PART17},
                {Notebook.Element.MEASURES_PART1, Notebook.Element.MEASURES_PART2},
                {Notebook.Element.MEASURES_PART3},
                {Notebook.Element.MEASURES_PART4},
                {Notebook.Element.MEASURES_PART8},
                {Notebook.Element.MEASURES_PART13},
                {Notebook.Element.MEASURES_PART18},
                {Notebook.Element.MEASURES_PART19},
                {Notebook.Element.MEASURES_PART5},
                {Notebook.Element.MEASURES_PART14},
                {Notebook.Element.MEASURES_PART20},
                {Notebook.Element.MEASURES_PART9},
                {Notebook.Element.MEASURES_PART10},
                {Notebook.Element.MEASURES_PART15},
        };
        final int [] correctAnswers = {2, 1, 3, 0, 0, 0, 2, 1, 3, 3, 0, 1, 3, 2, 2, 1};
        final AtomicInteger counter = new AtomicInteger(0);

        Image background = new Image(Assets.level4scene2.findRegion("background"));
        background.getColor().a = 0f; backgroundStage.addActor(background);

        final Table notebookTable = new Table();
        notebookTable.setBackground(new TextureRegionDrawable(Assets.level4scene3.findRegion("notepad")));
        notebookTable.setSize(
                notebookTable.getBackground().getMinWidth(),
                notebookTable.getBackground().getMinHeight());
        notebookTable.center().top().padTop(516f);
        notebookTable.setY(-mainStage.getRealHeight()); notebookTable.setTouchable(Touchable.disabled);
        mainStage.addActor(notebookTable);


        final TextureRegionDrawable [] measures = new TextureRegionDrawable[16];
        for (int i=0; i<measures.length; i++){
            measures[i] = new TextureRegionDrawable(Assets.level4scene5.findRegion("measure" +(i+1)));
        }

        final Image currentMeasure = new Image(measures[counter.get()]);
        currentMeasure.setY(52f); notebookTable.addActor(currentMeasure);

        final Image question = new Image(Assets.level4scene5.findRegion("question"));
        notebookTable.add(question).colspan(2).padBottom(7f).row();

        final Image wrongAnswer []= new Image[3];
        for(int i=0; i<wrongAnswer.length; i++){
            wrongAnswer[i] = new Image(Assets.level4scene5.findRegion("wrong" +(i+1)));
            wrongAnswer[i].getColor().a = 0.0f;
        }

        final Image [] options = new Image[4];
        final Button optionsButtons [] = new Button[options.length];
        final TextureRegionDrawable [] correctOptionsDrawables = new TextureRegionDrawable[options.length];

        for(int i=0; i< options.length; i++){
            optionsButtons[i] = new Button(
                    new TextureRegionDrawable(Assets.level4scene5.findRegion("panel_up")),
                    new TextureRegionDrawable(Assets.level4scene5.findRegion("panel_down"))
            );
            optionsButtons[i].setOrigin(optionsButtons[i].getPrefWidth()/2, optionsButtons[i].getPrefHeight()/2);
            optionsButtons[i].setTransform(true);

            options[i] = new Image(Assets.level4scene5.findRegion("option" +(i+1) +"_normal"));
            optionsButtons[i].center().add(options[i]);
            notebookTable.add(optionsButtons[i]).padBottom(-7f);

            if(i%2 == 1)
                notebookTable.row();

            correctOptionsDrawables[i] = new TextureRegionDrawable(
                    Assets.level4scene5.findRegion("option" +(i+1) +"_correct"));

            optionsButtons[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    notebookTable.setTouchable(Touchable.disabled);

                    final Image correctOption = options[correctAnswers[counter.get()]];
                    final Drawable oldDrawable = correctOption.getDrawable();
                    correctOption.setDrawable(correctOptionsDrawables[correctAnswers[counter.get()]]);

                    final Timeline changeQuestion = Timeline.createSequence()
                            .beginParallel()
                            .push(Tween.to(currentMeasure, ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(optionsButtons[0], ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .push(Tween.to(optionsButtons[1], ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .push(Tween.to(optionsButtons[2], ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .push(Tween.to(optionsButtons[3], ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .end()
                            .push(Tween.mark().setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    if (counter.incrementAndGet() == measures.length) {
                                        tweenManager.killAll();
                                        Tween.to(notebookTable, ActorAccessor.MOVE_Y, 1f)
                                                .target(-mainStage.getRealHeight())
                                                .setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        finishLevel(Game1910.ScreenType.LEVEL_SELECT);
                                                    }
                                                }).start(tweenManager);
                                    } else {
                                        correctOption.setDrawable(oldDrawable);
                                        currentMeasure.setDrawable(measures[counter.get()]);
                                    }
                                }
                            }))
                            .beginParallel()
                            .push(Tween.to(currentMeasure, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(optionsButtons[0], ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .push(Tween.to(optionsButtons[1], ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .push(Tween.to(optionsButtons[2], ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .push(Tween.to(optionsButtons[3], ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .end()
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    notebookTable.setTouchable(Touchable.childrenOnly);
                                }
                            });

                    if (event.getListenerActor() == optionsButtons[correctAnswers[counter.get()]]) {
                        addScore(30);
                        Assets.successFX.play();
                        Timeline.createSequence()
                                .pushPause(0.75f)
                                .push(game.getNotebook().addElement(mainStage, notebookElements[counter.get()]))
                                .push(changeQuestion)
                                .start(tweenManager);
                    } else {
                        addScore(20);
                        Assets.failFX.play();

                        final Image mask = new Image(Assets.level4scene2.findRegion("black_pixel"));
                        mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                                mainStage.getHeight() + mainStage.getPadBottom() * 2);
                        mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
                        mask.setTouchable(Touchable.disabled);
                        mask.getColor().a = 0.0f;
                        mainStage.addActor(mask);

                        final  Image randomWrongComment = wrongAnswer[MathUtils.random(0, wrongAnswer.length-1)];
                        randomWrongComment.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                randomWrongComment.removeListener(this);
                                Timeline.createSequence()
                                        .beginParallel()
                                        .push(Tween.to(randomWrongComment, ActorAccessor.ALPHA, 0.5f).target(0f))
                                        .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0f))
                                        .end()
                                        .push(Tween.mark().setCallback(new TweenCallback() {
                                            @Override
                                            public void onEvent(int type, BaseTween<?> source) {
                                                randomWrongComment.remove();
                                                mask.remove();
                                            }
                                        }))
                                        .push(game.getNotebook().addElement(mainStage, notebookElements[counter.get()]))
                                        .push(changeQuestion)
                                        .start(tweenManager);
                            }
                        });
                        mainStage.addActor(randomWrongComment);

                        randomWrongComment.setTouchable(Touchable.disabled);
                        Timeline.createSequence()
                                .pushPause(0.75f)
                                .beginParallel()
                                .push(Tween.to(randomWrongComment, ActorAccessor.ALPHA, 0.5f).target(1f))
                                .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0.8f))
                                .end()
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        randomWrongComment.setTouchable(Touchable.enabled);
                                    }
                                }).start(tweenManager);
                    }
                }
            });

        }

        Tween.to(background, ActorAccessor.ALPHA, 2f).target(1f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                    @Override
                    public void onCompleted() {
                        Tween.to(notebookTable, ActorAccessor.MOVE_Y, 1f).target(0f)
                                .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                notebookTable.setTouchable(Touchable.childrenOnly);
                            }
                        }).start(tweenManager);
                    }
                });
                reporterThought.run(measuresHelp, tweenManager);
            }
        }).start(tweenManager);

    }

    @Override
    protected void afterFinishLevel(Game1910.ScreenType nextScreen) {
        final Game1910.ScreenType screen = nextScreen;
        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                Level4.super.afterFinishLevel(screen);
            }
        });
        reporterThought.run(level4FinalThought, tweenManager);

    }
}
