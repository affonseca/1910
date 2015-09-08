package com.mobilelearning.game1910.screens;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.minigames.JigsawPuzzle;
import com.mobilelearning.game1910.minigames.MissingWords;
import com.mobilelearning.game1910.uiUtils.AnimatedActor;
import com.mobilelearning.game1910.uiUtils.AnimationDrawable;
import com.mobilelearning.game1910.uiUtils.Conversation;
import com.mobilelearning.game1910.uiUtils.DialogBox;
import com.mobilelearning.game1910.uiUtils.DialogCallback;
import com.mobilelearning.game1910.uiUtils.Notebook;
import com.mobilelearning.game1910.uiUtils.ReporterThought;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 05-03-2015
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class Level2 extends LevelScreen {

    private static final  String cardosoConversationOrder [] = {"Jornalista", "Cardoso", "Jornalista", "Cardoso"};
    private static final  String cardosoConversation [] = {
            "Cardoso termina de compor com os tipos móveis a última frase da notícia. São já 16h e tenho de ir à " +
                    "Estação registar a chegada da família real para termos notícia de abertura para o jornal de " +
                    "domingo. Eles vêm de Vila Viçosa...",
            "E vais à estação, a esta hora? Mais vale ficares logo ao lado do Terreiro do Paço. Melhor, junto do " +
                    "Ministério do Reino! Hoje é sábado e já lá deve estar um mar de gente à espera deles!",
            "Talvez tenhas razão. Vou ver, vou ver... O certo é que a tensão paira no ar... Com tantos republicanos " +
                    "revolucionários presos… quero ver o que lá se vai passar. Levo a máquina fotográfica. " +
                    "Sinto que algo de importante poderá acontecer.",
            "Vai lá, não te atrases."
    };

    private static final String pictureHelp = "Vem aí o rei e a família real! Ajuda-me a registar este momento. " +
            "Estou nervoso... Pressiona o disparador quando achares que é a foto ideal para a capa do jornal...";

    private static final String pictureEndHelp = "Parece que não tirámos a fotografia a tempo. Vamos tentar de novo...";

    private static final String afterPhotoThought = "Ainda não acredito no que acabou de acontecer... Tenho de " +
            "correr já para a redação e publicar esta foto! Não tenho tempo a perder!";

    private static final String puzzleHelp = "No meio da confusão que se instalou, acabei por partir o vidro onde tinha" +
            " impressa a fotografia. Ajuda-me a montá-lo para que possa sair ainda no jornal desta edição.";

    private static final String afterPuzzleThought = "Já está! Esta vai ser uma notícia surpreendente!";

    private static final String afterPaperThought = "Estou indignado! Tenho a receber $score, mas os bancos estão sem dinheiro, " +
            "o Jornal não poderá pagar salários... espero na próxima edição receber a totalidade do que me é devido...";

    private static final String envelopeSceneThought = "Vários dias já se passaram desde o Regicídio " +
            "e parece que tudo está calmo... Mas... o que é isto na mesa?";

    private static final String conversationHelp = "Quem será que está à minha espera? Acho que vou tentar conversar" +
            " com pessoas até encontrar quem procuro.";

    private static final String randomPersonConversationOrder [] = {"Jornalista", "Cidadão"};
    private static final String mainGuyQuestionToRandomPeople = "Estava à minha espera?";
    private static final String randomPersonresponses [] = {"Eu não! Porque haveria de estar?", "Desculpe? Deve estar " +
            "enganado!", "Eu não marco encontros que depois me esqueça! Eu não, ora!"};

    private static final String luzAlmeidaConversationOrder [] = {"Jornalista", "Luz Almeida", "Jornalista",
            "Luz Almeida", "Jornalista", "Luz Almeida", "Luz Almeida", "Jornalista", "Luz Almeida", "Jornalista",
            "Luz Almeida"};
    private static final String luzAlmeidaConversation [] = {
            "Estava à minha espera?",
            "Pela República! Sim estava. Precisava de falar consigo. Acompanhe-me num passeio pela avenida.",
            "Claro, mas tenho o prazer de falar com?",
            "Almeida. Luz de Almeida. Agradeço que me acompanhe. Sei que é jornalista e já tenho lido notícias " +
                    "escritas por si. Muito bem escritas, parabéns! A razão do meu contacto é apresentar-lhe a " +
                    "associação a que faço parte e revelar-lhe os nossos objetivos.",
            "Associação secreta?",
            "Sim. Sou dirigente da Carbonária e a nossa ação consiste em limpar o bosque dos lobos, ou seja destruir " +
                    "os tiranos. Defendemos a liberdade pública e a perfeição humana. Da nossa associação fazem parte" +
                    " elementos de todas as classes sociais, assumimo-nos como anticlericais e não reconhecemos a" +
                    " monarquia como uma forma justa de governo.",
            "Gostaria de contar consigo para a nossa causa. Aceita?",
            "Humm… aceito. A minha consciência pede-me isso. Há anos que trabalho no jornal e escrevo sobre as " +
                    "dificuldades sentidas pelo povo e o quão difícil é para Portugal se igualar ao estrangeiro. " +
                    "Estou convicto que só uma pessoa escolhida pelo povo poderá resolver os nossos problemas.",
            "Assim concordo. Mas se é sua vontade entrar na associação, terá de provar que merece e que vai ser um " +
                    "fiel membro. Lanço-lhe um desafio. Continue o passeio e reconheça dois elementos importantes da" +
                    " associação.",
            "Mas como vou conseguir isso?",
            "Pois bem, a sua senha é 'Que deseja'’. Aquele que lhe der a contra-senha 'A República' é Cândido dos " +
                    "Reis. O que lhe der a contrasenha 'um presidente eleito pelo povo' é Miguel Bombarda. Eles estão" +
                    " aqui, à sua espera. Lembre-se, só se conseguir contactá-los poderá reunir-se connosco.",
    };

    private static final String mainGuyQuestionToRandomPeople2 = "Que deseja?";
    private static final String randomPersonresponses2 [] = {"Eu nada! E você?", "Nada, você é que me está a perguntar?",
                "Não me lembro de o conhecer?", "Desejo que o país melhore, é o que é!"};

    private static final String randomPeopleForMissingWords [] = {"Cândido dos Reis", "Miguel Bombarda",
            "Luz Almeida"};

    private static final String forthSceneMissingWordsThought = "Não me posso esquecer dos nomes destes homens, eu sei " +
            "que eles vão fazer parte da história de Portugal! Ajuda-me a colocar o nome correto na respetiva foto.";

    private static final String forthSceneMissingWordsHelp = "Luz Almeida disse-me \" Aquele que lhe der a contra-senha " +
            "'A República' é Cândido dos Reis. o que lhe der a contrasenha 'um presidente eleito pelo povo'\" é " +
            "Miguel Bombarda.";

    private static final String forthSceneFinalDialog = "Tenho que conseguir apoios importantes para esta causa. " +
            "Amanhã mesmo vou até ao Martinho d'Arcada já que lá se encontram os mais eloquentes e críticos " +
            "da vida política portuguesa.";

    private static final String beforeSpeechThought = "Machado dos Santos irá discursar esta noite. Aposto que terá " +
            "ideias importantes a transmitir! Não vou perder isto nem por nada!";

    private static final String speechFromMachadoSantos [] = {"Amigos, todos vós já se aperceberam do aumento do custo " +
                "de vida. Por todo o lado vemos o queixume das gentes que nada têm.",
            "E tudo isto porque para pagar as obras públicas e financiar os luxos da família real contraíram-se dívidas" +
                    " externas. Agora é hora de pagar e por isso os impostos não param de aumentar.",
            "Caros amigos há muito que é sabido que a monarquia é a causa dos males nacionais! E meus caros, isto só se " +
                "resolve com uma nova forma de governo. Só a República pode colocar Portugal no caminho do progresso e da " +
                "grandeza nacional.",
            "Urge mudar. Importa que nos unamos na defesa da Liberdade, da Igualdade, da Justiça e da Dignidade Humana." +
                    " Importa, por isso, que sejam os cidadãos a escolher quem querem que os governe.",
            "É por crer nisto que o Partido Republicano defende que seja o povo a eleger os seus representantes! Só " +
                    "desta forma, justa e verdadeira, Portugal progredirá.",
            "É justo alguém adquirir de modo vitalício um cargo pelo mero facto do nascimento? Poderá alguém tornar-se " +
                    "na primeira figura de um País por simples via hereditária?",
            "E o lugar do mérito, do engenho e reconhecimento dos concidadãos, não tem valor algum?",
            "Só quando formos nós a escolher quem nos governa, algum avanço poderá ocorrer.",
            "À frente dos destinos da Nação não deve estar um rei que, por berço, herda o trono, mas antes, um cidadão que " +
                    "se preocupe com o país e que o povo nele acredite para se fazerem as mudanças necessárias!",
            "Devemos ser nós, portugueses, a escolher o chefe do país: um presidente. É por isso, amigos, que eu " +
                    "defendo os ideais republicanos. É por tudo isto."};

    private static final String publicResponseToSpeech =
            "Viva o Partido Republicano! Queremos escolher quem nos governa!";

    private static final String trueOrFalseHelp = "A minha mente fervilha de emoção! É preciso dar a conhecer estas ideias " +
            "que Machado dos Santos apresentou. Ajuda-me, quais foram mesmo as ideias apresentadas?";

    private static final String trueOrFalseQuestions [] ={
            "O aumento do custo de vida era uma" +
                    " razão do descontentamento social.",
            "Os republicanos defendem que a" +
                    " primeira figura de um país deve ser nomeada.",
            "No início do séc. XX, aumentaram-se" +
                    " os impostos de modo a pagar dívidas externas.",
            "Na República, um presidente governa até morrer.",
            "Os republicanos defendem que o presidente deve ser eleito.",
            "Para os republicanos, os ideias de" +
                    " Liberdade, de Igualdade e de" +
                    " Injustiça são essenciais numa República.",
            "Na República, o chefe de Estado é o Primeiro-ministro."
    };

    private static boolean trueOrFalseAnswers [] = {true, false, true, false, true, false, false};

    private static final String afterLevelThought = "Com estes impostos para pagar cada vez recebo menos... " +
            "É necessário um ponto final nesta agonia! Pelo menos recebi tudo o que me deviam...";


    private TweenManager concurrentManager;

    public Level2(Game1910 game, PolygonSpriteBatch batch){
        super(game, batch, Assets.maxLevelScores[1]);
        concurrentManager = new TweenManager();
    }

    @Override
    public void load() {
        Assets.loadLevel2();
    }

    @Override
    public void prepare() {
        super.prepare();
        game.getNotebook().updateNotebook(Assets.levelValues[1], true);
        game.getNotebook().addElementNoAnimation(Notebook.Element.LEVEL_1908);
        Assets.prepareLevel2();
        musicPool.addAll(Arrays.asList(Assets.audio9));
        musicPool.add(Assets.regicideFX);
    }

    @Override
    public void unload() {
        Assets.unloadLevel2();
    }

    @Override
    public void show() {
        firstScene();
    }

    protected void firstScene(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level2scene1.findRegion("background"));
        background.getColor().a = 0f; backgroundStage.addActor(background);
        final Image reporters = new Image(Assets.level2scene1.findRegion("reporters"));
        reporters.getColor().a = 0f; reporters.setY(-150f);
        mainStage.addActor(reporters);

        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);

        TextureRegion wordBalloonImage = Assets.level2scene1.findRegion("word_balloon");
        TextureRegionDrawable wordBalloon = new TextureRegionDrawable(wordBalloonImage);
        final DialogBox reporterBox = new DialogBox("", talkStyle,
                (mainStage.getWidth()-wordBalloon.getMinWidth())*(1f/4f), 628f,
                wordBalloon.getMinWidth(), wordBalloon.getMinHeight());
        reporterBox.setBackground(wordBalloon); reporterBox.setVisible(false);
        reporterBox.setTextPad(30f, 30f, 30f, 60f);
        reporterBox.stopText();
        reporterBox.setClickAnimationPosition(
                wordBalloon.getMinWidth() - reporterBox.getClickAnimation().getPrefWidth() + 30f, -25f);
        reporterBox.addToStage(mainStage);

        TextureRegion flippedWordBalloonImage = new TextureRegion(wordBalloonImage);
        flippedWordBalloonImage.flip(true, false);
        TextureRegionDrawable flippedWordBalloon = new TextureRegionDrawable(flippedWordBalloonImage);
        final DialogBox cardosoBox = new DialogBox("", talkStyle,
                (mainStage.getWidth()-flippedWordBalloon.getMinWidth())*(3f/4f), 628f,
                flippedWordBalloon.getMinWidth(), flippedWordBalloon.getMinHeight());
        cardosoBox.setBackground(flippedWordBalloon); cardosoBox.setVisible(false);
        cardosoBox.setTextPad(30f, 30f, 30f, 60f);
        cardosoBox.stopText();
        cardosoBox.setClickAnimationPosition(
                flippedWordBalloon.getMinWidth() - cardosoBox.getClickAnimation().getPrefWidth() + 30f, -25f);
        cardosoBox.addToStage(mainStage);

        final Conversation conversation = new Conversation(   new String[] {"Jornalista", "Cardoso"},
                                                        new DialogBox[] {reporterBox, cardosoBox},
                                                        cardosoConversationOrder, cardosoConversation);
        conversation.setCallback(new Conversation.ConversationCallback() {
            @Override
            public void onCompleted() {
                reporterBox.setVisible(false); cardosoBox.setVisible(false);
                Timeline.createParallel()
                        .push(Tween.to(background, ActorAccessor.ALPHA, 1f).target(0f))
                        .push(Tween.to(reporters, ActorAccessor.ALPHA, 1f).target(0f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                secondScene();
                            }
                        }).start(tweenManager);

            }
        });

        Timeline.createParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 2f).target(1f))
                .push(Tween.to(reporters, ActorAccessor.ALPHA, 2f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        conversation.startConversation();
                    }
                }).start(tweenManager);

    }

    @Override
    protected void secondScene() {
        changeScene(SceneValue.SCENE_2);
        secondScenePart1();
    }

    private void secondScenePart1(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level2scene2_1.findRegion("background"));
        backgroundStage.addActor(background);
        final AnimatedActor path = new AnimatedActor(new AnimationDrawable(Assets.pathAnimation));
        path.setVisible(false); backgroundStage.addActor(path);

        Timeline.createSequence()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1f).target(1f).setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        path.setVisible(true);
                        path.getDrawable().reset();
                    }
                }))
                .pushPause(3f)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        secondScenePart2();
                    }
                }).start(tweenManager);
    }

    private void secondScenePart2(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level2scene2_2.findRegion("background"));
        background.getColor().a = 0.0f;
        backgroundStage.addActor(background);
        final Image camera = new Image(Assets.level2scene2_2.findRegion("camera"));
        camera.setPosition((mainStage.getWidth() - camera.getPrefWidth()) / 2,
                -camera.getPrefHeight() - mainStage.getPadBottom());
        mainStage.addActor(camera);

        Tween.to(background, ActorAccessor.ALPHA, 0.5f).target(1f)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterThought.run(pictureHelp, tweenManager);
                    }
                }).start(tweenManager);

        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                Timeline.createSequence()
                        .push(Tween.to(camera, ActorAccessor.MOVE_Y, 1f)
                                .target((mainStage.getHeight() - camera.getPrefHeight()) * 0.125f))
                        .pushPause(1.5f)
                        .beginParallel()
                        .push(Tween.to(camera, ActorAccessor.ALPHA, 2f).target(0f))
                        .push(Tween.to(background, ActorAccessor.ALPHA, 2f).target(0f))
                        .end()
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                secondScenePart3();
                            }
                        })
                        .start(tweenManager);
            }
        });

    }

    private void secondScenePart3(){
        mainStage.clear();
        backgroundStage.clear();

        final Container<Image> imageContainer = new Container<>();
        imageContainer.setBounds(40f, 275f, 570f, 570f);

        final Image [] imageArray = {
                new Image(Assets.level2scene2_3.findRegion("image1")),
                new Image(Assets.level2scene2_3.findRegion("image2")),
                new Image(Assets.level2scene2_3.findRegion("image3")),
                new Image(Assets.level2scene2_3.findRegion("image4")),
                new Image(Assets.level2scene2_3.findRegion("image5")),
        };

        final AtomicInteger counter = new AtomicInteger(0);
        imageContainer.setActor(imageArray[counter.get()]);
        backgroundStage.addActor(imageContainer);

        backgroundStage.addActor(new Image(Assets.level2scene2_3.findRegion("lens")));

        final ImageButton cameraButton = new ImageButton(
                new TextureRegionDrawable(Assets.level2scene2_3.findRegion("button_up")),
                new TextureRegionDrawable(Assets.level2scene2_3.findRegion("button_clicked"))
        );
        cameraButton.setPosition(mainStage.getWidth() - cameraButton.getPrefWidth(), -mainStage.getPadBottom());
        cameraButton.setVisible(false);
        cameraButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                tweenManager.pause();
                if (counter.get() == 3) {
                    cameraButton.setTouchable(Touchable.disabled);
                    Assets.successFX.play();
                    Timer timer = new Timer();
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            tweenManager.killAll();
                            tweenManager.resume();
                            Timeline.createSequence()
                                    .push(game.getNotebook().addElement(mainStage,
                                            Notebook.Element.PHOTO_REGICIDE_TITLE,
                                            Notebook.Element.PHOTO_REGICIDE))
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                                                @Override
                                                public void onCompleted() {
                                                    Timeline.createParallel()
                                                            .push(Tween.to(imageArray[3], ActorAccessor.ALPHA, 1.0f).target(0f))
                                                            .push(Tween.to(cameraButton, ActorAccessor.ALPHA, 1.0f).target(0f))
                                                            .setCallback(new TweenCallback() {
                                                                @Override
                                                                public void onEvent(int type, BaseTween<?> source) {
                                                                    addScore(50);
                                                                    thirdScene();
                                                                }
                                                            }).start(tweenManager);
                                                }
                                            });
                                            reporterThought.run(afterPhotoThought, tweenManager);
                                        }
                                    })
                                    .start(tweenManager);
                        }
                    }, 1.0f);
                }
            }
        });
        mainStage.addActor(cameraButton);

        Assets.regicideFX.play();
        Timeline.createSequence()
                .pushPause(2.5f)
                .push(Tween.mark().setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        counter.set((counter.get() + 1) % imageArray.length);
                        if (counter.get() == 3) {
                            cameraButton.setVisible(true);
                        } else if (counter.get() == 0) {
                            tweenManager.pause();
                            addScore(-15);
                            reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                                @Override
                                public void onCompleted() {
                                    imageContainer.setActor(imageArray[counter.get()]);
                                    Assets.regicideFX.play();
                                    tweenManager.resume();
                                }
                            });
                            reporterThought.run(pictureEndHelp, concurrentManager);
                            return;
                        } else
                            cameraButton.setVisible(false);
                        imageContainer.setActor(imageArray[counter.get()]);
                    }
                }))
                .repeat(Tween.INFINITY, 0)
                .start(tweenManager);

    }

    protected void thirdScene(){
        changeScene(SceneValue.SCENE_3);
        thirdScenePart1();
    }

    private void thirdScenePart1(){
        mainStage.clear();
        backgroundStage.clear();

        backgroundStage.addActor(new Image(Assets.level2scene3_1.findRegion("background")));


        Image reporter = new Image(Assets.level2scene3_1.findRegion("reporter"));
        reporter.setPosition(102f, -115f);
        mainStage.addActor(reporter);

        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        TextureRegion wordBalloonImage = Assets.level2scene1.findRegion("word_balloon");
        wordBalloonImage.flip(true, false);
        TextureRegionDrawable wordBalloon = new TextureRegionDrawable(wordBalloonImage);
        final DialogBox guy1Box = new DialogBox(puzzleHelp, talkStyle,
                105f, 568f, wordBalloon.getMinWidth(), wordBalloon.getMinHeight());
        guy1Box.setBackground(wordBalloon);
        guy1Box.setTextPad(30f, 30f, 30f, 60f);
        guy1Box.setClickAnimationPosition(
                wordBalloon.getMinWidth() - guy1Box.getClickAnimation().getPrefWidth() + 30f, -25f);
        guy1Box.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type == CLICKED_AFTER_END)
                    thirdScenePart2();
            }
        });
        guy1Box.addToStage(mainStage);

    }

    private void thirdScenePart2(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level2scene3_2.findRegion("background"));
        backgroundStage.addActor(background);

        final Image paper = new Image(Assets.level2scene3_2.findRegion("paper"));
        paper.setY(-178f);
        paper.setOrigin(paper.getPrefWidth() / 2, paper.getPrefHeight() / 2);
        paper.setScale(0f);
        backgroundStage.addActor(paper);

        final Image puzzleBox = new Image(Assets.level2scene3_2.findRegion("puzzle_box"));
        puzzleBox.setPosition(19f, 446f);
        mainStage.addActor(puzzleBox);

        TextureRegionDrawable panelImage = new TextureRegionDrawable(Assets.level2scene3_2.findRegion("panel"));
        final Table panel = new Table();
        panel.setBounds((mainStage.getWidth() - panelImage.getMinWidth()) / 2,
                mainStage.getHeight() - panelImage.getMinHeight(),
                panelImage.getMinWidth(), panelImage.getMinHeight());
        panel.setBackground(panelImage);
        panel.center().defaults().pad(11f);

        Label text = new Label("tipografia", uiSkin, "default-font", Color.BLACK);
        text.setFontScale(1.2f);
        text.getStyle().fontColor = Assets.darkBrown;
        panel.add(text);
        mainStage.addActor(panel);

        final JigsawPuzzle jigsawPuzzle = new JigsawPuzzle(mainStage);
        jigsawPuzzle.getPieceSpace().setSize(600f, 400f);
        jigsawPuzzle.padTop(132f).defaults().padBottom(25f);
        jigsawPuzzle.setImageAndPieces(new TextureRegionDrawable(Assets.level2scene3_2.findRegion("puzzle_image")),
                Assets.regicidePuzzle);

        final  ImageButton hintButton = new ImageButton(
                new TextureRegionDrawable(Assets.level2scene3_2.findRegion("hint_button")));
        hintButton.setPosition(556f, 363f);
        hintButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Dialog warning = new Dialog("", new Window.WindowStyle(
                        uiSkin.getFont("default-font"),
                        Assets.darkBrown,
                        new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_panel"))
                )) {
                    @Override
                    protected void result(Object object) {
                        if (((String) object).contains("yes")) {
                            if (jigsawPuzzle.getNumberOfPiecesLeft() > 1) {
                                addScore(9);
                                jigsawPuzzle.putRandomPieceInPlace();
                                if (jigsawPuzzle.getNumberOfPiecesLeft() == 1)
                                    hintButton.setVisible(false);
                            }
                        }
                    }
                };

                warning.getContentTable().padTop(40f).defaults().padBottom(20f);
                warning.getButtonTable().padBottom(51f);

                Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);

                Label title = new Label("Usar pista?", labelStyle);
                title.setAlignment(Align.center);
                warning.text(title).getContentTable().row();

                Label message = new Label("Usar ajudas diminui\na pontuação do nível", labelStyle);
                message.setAlignment(Align.center);
                message.setFontScale(0.8f);
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
        mainStage.addActor(hintButton);

        final Image dummy = new Image();
        dummy.setSize(mainStage.getWidth(), mainStage.getHeight());
        dummy.getColor().a = 0.0f; dummy.setVisible(false);
        dummy.setTouchable(Touchable.disabled);
        dummy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dummy.remove();

                reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                    @Override
                    public void onCompleted() {
                        Timeline.createParallel()
                                .push(Tween.to(panel, ActorAccessor.ALPHA, 2f).target(0f))
                                .push(Tween.to(paper, ActorAccessor.ALPHA, 2f).target(0f))
                                .push(Tween.to(background, ActorAccessor.ALPHA, 1.5f).target(0f))
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        forthScene();
                                    }
                                }).start(tweenManager);
                    }
                });
                reporterThought.run(afterPaperThought.replace("$score", "" + ((int) (scoreToMoney * getScore()))),
                        tweenManager);
            }
        });
        mainStage.addActor(dummy);

        jigsawPuzzle.setCallback(new JigsawPuzzle.JigsawPuzzleCallback() {
            @Override
            public void onCorrect(Image target) {
                addScore(12);
                Assets.successFX.play();
            }

            @Override
            public void onWrong(Image target) {
                addScore(-4);
                Assets.failFX.play();
            }

            @Override
            public void onCompleted(Image lastCorrect) {
                addScore(12);
                Assets.successFX.play();
                Timeline.createSequence()
                        .pushPause(0.5f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                                    @Override
                                    public void onCompleted() {
                                        Timeline.createSequence()
                                                .beginParallel()
                                                .push(Tween.to(jigsawPuzzle, ActorAccessor.ALPHA, 1.0f).target(0f))
                                                .push(Tween.to(background, ActorAccessor.ALPHA, 1.0f).target(0f))
                                                .push(Tween.to(hintButton, ActorAccessor.ALPHA, 1.0f).target(0f))
                                                .push(Tween.to(puzzleBox, ActorAccessor.ALPHA, 1.0f).target(0f))
                                                .push(Tween.to(panel, ActorAccessor.ALPHA, 1.0f).target(0f)
                                                        .setCallback(new TweenCallback() {
                                                            @Override
                                                            public void onEvent(int type, BaseTween<?> source) {
                                                                hintButton.setVisible(false);
                                                            }
                                                        }))
                                                .end()
                                                .push(Tween.set(background, ActorAccessor.ALPHA).target(1f))
                                                .push(Tween.set(panel, ActorAccessor.ALPHA).target(1f))
                                                .beginParallel()
                                                .push(Tween.to(paper, ActorAccessor.ROTATE, 3f).target(3600f).ease(Quad.OUT))
                                                .push(Tween.to(paper, ActorAccessor.SCALEXY, 3f).target(1f))
                                                .end()
                                                .pushPause(0.5f)
                                                .setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        dummy.setVisible(true);
                                                        dummy.setTouchable(Touchable.enabled);
                                                    }
                                                }).start(tweenManager);
                                    }
                                });
                                reporterThought.run(afterPuzzleThought, tweenManager);
                            }
                        }).start(tweenManager);
            }
        });
    }

    protected void forthScene(){
        changeScene(SceneValue.SCENE_4);
        forthScenePart1();
    }

    private void forthScenePart1(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level2scene4_1.findRegion("background"));
        backgroundStage.addActor(background); background.getColor().a = 0.0f;
        final Image envelope_small = new Image(Assets.level2scene4_1.findRegion("envelope_small"));
        envelope_small.setPosition(156f, 231f); envelope_small.getColor().a = 0.0f;
        backgroundStage.addActor(envelope_small);

        final Image envelope_bottom = new Image(Assets.level2scene4_1.findRegion("envelope_bottom"));
        envelope_bottom.setPosition(
                (mainStage.getWidth() - envelope_bottom.getPrefWidth()) / 2, 318f);
        envelope_bottom.setVisible(false);
        envelope_bottom.getColor().a = 0.0f; mainStage.addActor(envelope_bottom);

        final Image letter = new Image(Assets.level2scene4_1.findRegion("letter"));
        letter.setPosition(
                (mainStage.getWidth() - letter.getPrefWidth()) / 2, 196f);
        letter.setVisible(false);
        letter.getColor().a = 0.0f; mainStage.addActor(letter);

        final Image envelope_top = new Image(Assets.level2scene4_1.findRegion("envelope_top"));
        envelope_top.setPosition(
                (mainStage.getWidth() - envelope_top.getPrefWidth()) / 2, 196f);
        envelope_top.setVisible(false);
        envelope_top.getColor().a = 0.0f;
        mainStage.addActor(envelope_top);

        final Image dummy = new Image(Assets.level2scene4_1.findRegion("background"));
        dummy.getColor().a = 0.0f; dummy.setVisible(false);
        dummy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dummy.removeListener(this);
                dummy.setVisible(true);
                Timeline.createParallel()
                        .push(Tween.to(envelope_bottom, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .push(Tween.to(envelope_top, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .push(Tween.to(letter, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .push(Tween.to(background, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .push(Tween.to(envelope_small, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                forthScenePart2();
                            }
                        }).start(tweenManager);
            }
        });
        mainStage.addActor(dummy);

        envelope_small.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                envelope_bottom.setVisible(true);
                letter.setVisible(true);
                envelope_top.setVisible(true);
                Timeline.createSequence()
                        .beginParallel()
                        .push(Tween.to(envelope_bottom, ActorAccessor.ALPHA, 1.0f).target(1f))
                        .push(Tween.to(letter, ActorAccessor.ALPHA, 1.0f).target(1f))
                        .push(Tween.to(envelope_top, ActorAccessor.ALPHA, 1.0f).target(1f))
                        .end()
                        .push(Tween.to(letter, ActorAccessor.MOVE_Y, 1.5f).target(358f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                dummy.setVisible(true);
                            }
                        })
                        .start(tweenManager);

            }
        });
        envelope_small.setTouchable(Touchable.disabled);

        Timeline.createParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 2f).target(1f))
                .push(Tween.to(envelope_small, ActorAccessor.ALPHA, 2f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                            @Override
                            public void onCompleted() {
                                envelope_small.setTouchable(Touchable.enabled);
                            }
                        });
                        reporterThought.run(envelopeSceneThought, tweenManager);
                    }
                }).start(tweenManager);
    }

    private void forthScenePart2(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level2scene4_2.findRegion("background"));
        background.getColor().a = 0.0f; backgroundStage.addActor(background);
        final  Image crowd = new Image(Assets.level2scene4_2.findRegion("crowd"));
        crowd.getColor().a = 0.0f; mainStage.addActor(crowd);

        TextureRegionDrawable reporterBalloon = new TextureRegionDrawable(Assets.level2scene4_2.findRegion("reporter_balloon"));
        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.BLACK);
        final DialogBox reporterBox = new DialogBox(null,
                talkStyle, 249f, 150f, reporterBalloon.getMinWidth(), reporterBalloon.getMinHeight());
        reporterBox.setTextPad(30, 30, 30, 30);
        reporterBox.setBackground(reporterBalloon);
        reporterBox.setVisible(false);
        reporterBox.setClickAnimationPosition(
                reporterBalloon.getMinWidth() - reporterBox.getClickAnimation().getPrefWidth() + 30f, -55f);
        reporterBox.addToStage(mainStage);

        final TextureRegionDrawable crowdBalloon = new TextureRegionDrawable(Assets.level2scene4_2.findRegion("crowd_balloon"));
        final TextureRegionDrawable crowdBalloonFlipped = new TextureRegionDrawable(
                new TextureRegion(Assets.level2scene4_2.findRegion("crowd_balloon")));
        crowdBalloonFlipped.getRegion().flip(true, false);
        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        final DialogBox crowdBox = new DialogBox(null, talkStyle2, 0f, 505f,
                crowdBalloon.getMinWidth(), crowdBalloon.getMinHeight());
        crowdBox.setTextPad(30, 30, 30, 130);
        crowdBox.setVisible(false);
        crowdBox.addToStage(mainStage);
        crowdBox.setClickAnimationPosition(
                crowdBalloon.getMinWidth() - crowdBox.getClickAnimation().getPrefWidth() + 40f, 32f);


        final Table crowdLocation = new Table();
        crowdLocation.setSize(mainStage.getWidth(), mainStage.getHeight());
        mainStage.addActor(crowdLocation);

        final Conversation randomConversations [] = new Conversation[3];
        for(int i=0;i<3;i++){
            randomConversations[i] = new Conversation(   new String[] {"Jornalista", "Cidadão"},
                    new DialogBox[] {reporterBox, crowdBox},
                    randomPersonConversationOrder, new String[] {mainGuyQuestionToRandomPeople, randomPersonresponses[i]});
            randomConversations[i].setCallback(new Conversation.ConversationCallback() {
                @Override
                public void onCompleted() {
                    reporterBox.setVisible(false);
                    crowdBox.setVisible(false);
                    crowdLocation.setTouchable(Touchable.childrenOnly);
                }
            });
        }

        final Conversation targetConversation = new Conversation(   new String[] {"Jornalista", "Luz Almeida"},
                new DialogBox[] {reporterBox, crowdBox}, luzAlmeidaConversationOrder, luzAlmeidaConversation);
        targetConversation.setCallback(new Conversation.ConversationCallback() {
            @Override
            public void onCompleted() {
                crowdBox.setVisible(false);
                Timeline.createParallel()
                        .push(Tween.to(background, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .push(Tween.to(crowd, ActorAccessor.ALPHA, 1.0f).target(0f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                addScore(20);
                                forthScenePart3();
                            }
                        }).start(tweenManager);
            }
        });

        final Image randomLocation1 = new Image();
        randomLocation1.setBounds(83f, 181f, 88f, 316f);
        crowdLocation.addActor(randomLocation1);
        randomLocation1.setVisible(false);
        randomLocation1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                addScore(-1);
                crowdBox.setBackground(crowdBalloon);
                crowdBox.getActor().setX(128f);
                randomConversations[0].startConversation();
                crowdLocation.setTouchable(Touchable.disabled);

            }
        });

        final Image randomLocation2 = new Image();
        randomLocation2.setBounds(187f, 162f, 95f, 325f);
        crowdLocation.addActor(randomLocation2);
        randomLocation2.setVisible(false);
        randomLocation2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                addScore(-1);
                crowdBox.setBackground(crowdBalloon);
                crowdBox.getActor().setX(243f);
                randomConversations[1].startConversation();
                crowdLocation.setTouchable(Touchable.disabled);

            }
        });

        final Image randomLocation3 = new Image();
        randomLocation3.setBounds(295f, 189f, 124f, 300f);
        crowdLocation.addActor(randomLocation3);
        randomLocation3.setVisible(false);
        randomLocation3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                addScore(-1);
                crowdBox.setBackground(crowdBalloonFlipped);
                crowdBox.getActor().setX(13f);
                randomConversations[2].startConversation();
                crowdLocation.setTouchable(Touchable.disabled);

            }
        });

        final Image targetLocation = new Image();
        targetLocation.setBounds(446f, 178f, 101f, 362f);
        crowdLocation.addActor(targetLocation);
        targetLocation.setVisible(false);
        targetLocation.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                crowdBox.setBackground(crowdBalloonFlipped);
                crowdBox.getActor().setX(146f);
                targetConversation.startConversation();
                crowdLocation.setTouchable(Touchable.disabled);

            }
        });

        Timeline.createParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1.0f).target(1f))
                .push(Tween.to(crowd, ActorAccessor.ALPHA, 1.0f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                            @Override
                            public void onCompleted() {
                                randomLocation1.setVisible(true);
                                randomLocation2.setVisible(true);
                                randomLocation3.setVisible(true);
                                targetLocation.setVisible(true);
                            }
                        });
                        reporterThought.run(conversationHelp, tweenManager);
                    }
                }).start(tweenManager);

    }

    private void forthScenePart3(){
        backgroundStage.clear();
        mainStage.clear();


        final Image background = new Image(Assets.level2scene4_3.findRegion("background"));
        background.getColor().a = 0.0f; backgroundStage.addActor(background);
        final  Image crowd = new Image(Assets.level2scene4_3.findRegion("crowd"));
        crowd.getColor().a = 0.0f; mainStage.addActor(crowd);

        TextureRegionDrawable reporterBalloon = new TextureRegionDrawable(Assets.level2scene4_2.findRegion("reporter_balloon"));
        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.BLACK);
        final DialogBox reporterBox = new DialogBox(null,
                talkStyle, 249f, 150f, reporterBalloon.getMinWidth(), reporterBalloon.getMinHeight());
        reporterBox.setTextPad(30, 30, 30, 30);
        reporterBox.setBackground(reporterBalloon);
        reporterBox.setVisible(false);
        reporterBox.setClickAnimationPosition(
                reporterBalloon.getMinWidth() - reporterBox.getClickAnimation().getPrefWidth() + 30f, -55f);
        reporterBox.addToStage(mainStage);

        final TextureRegionDrawable crowdBalloon = new TextureRegionDrawable(Assets.level2scene4_2.findRegion("crowd_balloon"));
        final TextureRegionDrawable crowdBalloonFlipped = new TextureRegionDrawable(
                new TextureRegion(Assets.level2scene4_2.findRegion("crowd_balloon")));
        crowdBalloonFlipped.getRegion().flip(true, false);
        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        final DialogBox crowdBox = new DialogBox(null, talkStyle2, 0f, 0f,
                crowdBalloon.getMinWidth(), crowdBalloon.getMinHeight());
        crowdBox.setTextPad(30, 30, 30, 130);
        crowdBox.setBackground(crowdBalloon);
        crowdBox.setVisible(false);
        crowdBox.addToStage(mainStage);
        crowdBox.setClickAnimationPosition(
                crowdBalloon.getMinWidth() - crowdBox.getClickAnimation().getPrefWidth() + 40f, 32f);

        final Table crowdTable = new Table();
        crowdTable.setSize(mainStage.getWidth(), mainStage.getHeight());
        crowdTable.setTouchable(Touchable.disabled); mainStage.addActor(crowdTable);

        final float locations [][] = {{11f, 132f, 65f, 200f}, {76f, 177f, 72f, 185f},
                {399f, 176f, 64f, 215f}, {520f, 139f, 119f, 249f}};

        final float balloonLocations [][] = {{65f, 331f}, {105f, 352f}, {75f, 365f}, {250f, 373f}};

        final TextureRegionDrawable balloonType [] = {crowdBalloon, crowdBalloon,
                crowdBalloonFlipped, crowdBalloonFlipped};


        for(int i=0;i<4;i++){
            final AtomicInteger aux = new AtomicInteger(i);
            final Conversation currentConverstation = new Conversation(   new String[] {"Jornalista", "Cidadão"},
                    new DialogBox[] {reporterBox, crowdBox},
                    randomPersonConversationOrder, new String[] {mainGuyQuestionToRandomPeople2, randomPersonresponses2[i]});
            currentConverstation.setCallback(new Conversation.ConversationCallback() {
                @Override
                public void onCompleted() {
                    reporterBox.setVisible(false);
                    crowdBox.setVisible(false);
                    crowdTable.setTouchable(Touchable.enabled);
                }
            });

            Image randomLocation = new Image();
            randomLocation.setBounds(locations[i][0], locations[i][1], locations[i][2], locations[i][3]);
            randomLocation.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    addScore(-1);
                    crowdBox.setBackground(balloonType[aux.get()]);
                    crowdBox.getActor().setPosition(balloonLocations[aux.get()][0],
                            balloonLocations[aux.get()][1]);
                    currentConverstation.startConversation();
                    crowdTable.setTouchable(Touchable.disabled);

                }
            });
            crowdTable.addActor(randomLocation);
        }

        final boolean targetFound [] = {false, false};

        final Conversation targetConversation1 = new Conversation(   new String[] {"Jornalista", "Cândido dos Reis"},
                new DialogBox[] {reporterBox, crowdBox}, new String[] {"Jornalista", "Cândido dos Reis"},
                new String[] {mainGuyQuestionToRandomPeople2, "A República!"});
        targetConversation1.setCallback(new Conversation.ConversationCallback() {
            @Override
            public void onCompleted() {
                reporterBox.setVisible(false);
                crowdBox.setVisible(false);
                crowdTable.setTouchable(Touchable.enabled);

                if(!targetFound[0]) {
                    targetFound[0] = true;
                    addScore(20);
                }if (targetFound[1])
                    forthScenePart4();
            }
        });

        final Conversation targetConversation2 = new Conversation(   new String[] {"Jornalista", "Miguel Bombarda"},
                new DialogBox[] {reporterBox, crowdBox}, new String[] {"Jornalista", "Miguel Bombarda"},
                new String[] {mainGuyQuestionToRandomPeople2, "Um presidente eleito pelo povo!"});
        targetConversation2.setCallback(new Conversation.ConversationCallback() {
            @Override
            public void onCompleted() {
                reporterBox.setVisible(false);
                crowdBox.setVisible(false);
                crowdTable.setTouchable(Touchable.enabled);

                if(!targetFound[1]) {
                    targetFound[1] = true;
                    addScore(20);
                }
                if (targetFound[0])
                    forthScenePart4();
            }
        });

        final Image targetLocation1 = new Image();
        targetLocation1.setBounds(153f, 168f, 54f, 195f);
        crowdTable.addActor(targetLocation1);
        targetLocation1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                crowdBox.setBackground(crowdBalloon);
                crowdBox.getActor().setPosition(183f, 365f);
                targetConversation1.startConversation();
                crowdTable.setTouchable(Touchable.disabled);

            }
        });

        final Image targetLocation2 = new Image();
        targetLocation2.setBounds(465f, 151f, 54f, 198f);
        crowdTable.addActor(targetLocation2);
        targetLocation2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                crowdBox.setBackground(crowdBalloonFlipped);
                crowdBox.getActor().setPosition(146f, 357f);
                targetConversation2.startConversation();
                crowdTable.setTouchable(Touchable.disabled);

            }
        });

        Timeline.createParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1.0f).target(1f))
                .push(Tween.to(crowd, ActorAccessor.ALPHA, 1.0f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        crowdTable.setTouchable(Touchable.childrenOnly);
                    }
                }).start(tweenManager);

    }

    private void forthScenePart4(){
        backgroundStage.clear();
        mainStage.clear();

        final Image background = new Image(Assets.level2scene4_3.findRegion("background"));
        backgroundStage.addActor(background);
        final  Image crowd = new Image(Assets.level2scene4_3.findRegion("crowd"));
        mainStage.addActor(crowd);

        TextureRegionDrawable notebookImage = new TextureRegionDrawable(Assets.notebook.findRegion("notepad"));
        final Table notebookTable = new Table();
        notebookTable.center().top();
        notebookTable.setBackground(notebookImage);
        notebookTable.setTouchable(Touchable.disabled);
        notebookTable.setSize(notebookImage.getMinWidth(), notebookImage.getMinHeight());
        notebookTable.setPosition(
                (mainStage.getWidth() - notebookTable.getWidth()) / 2 - mainStage.getWidth(),
                (mainStage.getHeight() - notebookTable.getHeight()) / 2
        );
        notebookTable.padTop(80f);
        mainStage.addActor(notebookTable);

        Label.LabelStyle style = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK);
        final MissingWords missingWords = new MissingWords(uiSkin,
                new TextureRegionDrawable(Assets.level2scene4_3.findRegion("missingWords")), 1.0f);
        missingWords.setImageTable(style, 1f, 270f, new float[][]{{137f, 322f}, {137f, 189f}, {137f, 50f}},
                randomPeopleForMissingWords);
        notebookTable.add(missingWords.getImageTable()).padBottom(30f).row();
        notebookTable.addActor(missingWords.getImageTable());

        missingWords.setHoverStyle(style);
        missingWords.setDragStyle(style);
        missingWords.setRandomWordScale(1f);
        missingWords.setDragScale(1f);
        missingWords.setCorrectWordsScale(1f);
        missingWords.setHoverScale(1f);

        missingWords.setWordsTable(style, randomPeopleForMissingWords, 1);
        missingWords.getWordsTable().center();
        notebookTable.add(missingWords.getWordsTable());

        final  ImageButton hintButton = new ImageButton(
                new TextureRegionDrawable(Assets.level2scene3_2.findRegion("hint_button")));
        hintButton.setPosition(427f, 13f);
        hintButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Dialog warning = new Dialog("", new Window.WindowStyle(
                        uiSkin.getFont("default-font"),
                        Assets.darkBrown,
                        new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_panel"))
                )) {
                    @Override
                    protected void result(Object object) {
                        if (((String) object).contains("yes")) {
                            addScore(11);
                            missingWords.getWordsTable().setTouchable(Touchable.disabled);
                            reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                                @Override
                                public void onCompleted() {
                                    missingWords.getWordsTable().setTouchable(Touchable.enabled);
                                }
                            });
                            reporterThought.run(forthSceneMissingWordsHelp, tweenManager);
                        }
                    }
                };

                warning.getContentTable().padTop(40f).defaults().padBottom(20f);
                warning.getButtonTable().padBottom(51f);

                Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);

                Label title = new Label("Usar pista?", labelStyle);
                title.setAlignment(Align.center);
                warning.text(title).getContentTable().row();

                Label message = new Label("Usar ajudas diminui\na pontuação do nível", labelStyle);
                message.setAlignment(Align.center);
                message.setFontScale(0.8f);
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
        notebookTable.addActor(hintButton);

        final Image message = new Image(Assets.level2scene4_3.findRegion("message"));
        message.setPosition((mainStage.getWidth() - message.getPrefWidth()) / 2,
                (mainStage.getHeight() - message.getPrefHeight()) / 2);
        message.getColor().a = 0f; message.setTouchable(Touchable.disabled);
        message.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                    @Override
                    public void onCompleted() {
                        Timeline.createParallel()
                                .push(Tween.to(background, ActorAccessor.ALPHA, 1.0f).target(0f))
                                .push(Tween.to(crowd, ActorAccessor.ALPHA, 1.0f).target(0f))
                                .push(Tween.to(message, ActorAccessor.ALPHA, 1.0f).target(0f))
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        fifthScene();
                                    }
                                }).start(tweenManager);
                    }
                });
                reporterThought.run(forthSceneFinalDialog, tweenManager);
            }
        });
        mainStage.addActor(message);

        missingWords.setCallback(new MissingWords.MissingWordsCallback() {
            @Override
            public void onCorrect(Label target) {
                addScore(15);
                Assets.successFX.play();
            }

            @Override
            public void onWrong(Label source, Label target) {
                addScore(-5);
                missingWords.getWordsTable().setTouchable(Touchable.disabled);
                target.setStyle(new Label.LabelStyle(uiSkin.getFont("default-font"), Color.MAROON));
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
                game.getNotebook().addElementNoAnimation(
                        Notebook.Element.PHOTOS_FROM_1908_TITLE,
                        Notebook.Element.CANDIDO_REIS_PHOTO_AND_NAME,
                        Notebook.Element.MIGUEL_BOMBARDA_PHOTO_AND_NAME,
                        Notebook.Element.LUZ_ALMEIDA_PHOTO_AND_NAME);

                Timeline.createSequence()
                        .pushPause(0.5f)
                        .push(Tween.to(notebookTable, ActorAccessor.MOVE_X, 1f)
                                .target((mainStage.getWidth() - notebookTable.getWidth()) / 2 - mainStage.getWidth()))
                        .push(Tween.to(message, ActorAccessor.ALPHA, 1f).target(1f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                message.setTouchable(Touchable.enabled);
                            }
                        }).start(tweenManager);
            }
        });

        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                Tween.to(notebookTable, ActorAccessor.MOVE_X, 1f)
                        .target((mainStage.getWidth() - notebookTable.getWidth()) / 2)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                notebookTable.setTouchable(Touchable.childrenOnly);
                            }
                        }).start(tweenManager);
            }
        });
        reporterThought.run(forthSceneMissingWordsThought, tweenManager);


    }

    protected void fifthScene(){
        changeScene(SceneValue.SCENE_5);
        fifthScenePart1();
    }

    private void fifthScenePart1() {
        mainStage.clear();
        backgroundStage.clear();

        final Image background1 = new Image(Assets.level2scene5_1.findRegion("background1"));
        final Image machadoSantos = new Image(Assets.level2scene5_1.findRegion("machado_santos"));
        background1.getColor().a = 0.0f; machadoSantos.getColor().a = 0f;
        machadoSantos.setPosition((mainStage.getWidth() - machadoSantos.getPrefWidth()) / 2, -154);
        machadoSantos.setOrigin(machadoSantos.getPrefWidth() / 2, machadoSantos.getPrefHeight());
        machadoSantos.setScale(1.15f);
        backgroundStage.addActor(background1);
        mainStage.addActor(machadoSantos);

        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        TextureRegionDrawable wordBalloon = new TextureRegionDrawable(Assets.level2scene5_1.findRegion("balloon1"));
        final DialogBox machadoSantosDialogBox = new DialogBox("", talkStyle,
                (mainStage.getWidth() - wordBalloon.getMinWidth()) / 2f, 590f,
                wordBalloon.getMinWidth(), wordBalloon.getMinHeight());
        machadoSantosDialogBox.setBackground(wordBalloon);
        machadoSantosDialogBox.setVisible(false);
        machadoSantosDialogBox.setSound(null);
        machadoSantosDialogBox.setTextPad(30f, 30f, 30f, 60f);
        machadoSantosDialogBox.stopText();
        machadoSantosDialogBox.setClickAnimationPosition(
                wordBalloon.getMinWidth() - machadoSantosDialogBox.getClickAnimation().getPrefWidth() + 30f, -25f);
        machadoSantosDialogBox.addToStage(mainStage);

        final Image background2 = new Image(Assets.level2scene5_1.findRegion("background2"));
        background2.getColor().a = 0.0f;
        background2.setVisible(false);
        backgroundStage.addActor(background2);

        final Image crowd = new Image(Assets.level2scene5_1.findRegion("crowd"));
        crowd.getColor().a = 0.0f;
        crowd.setVisible(false);
        mainStage.addActor(crowd);

        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        TextureRegionDrawable wordBalloon2 = new TextureRegionDrawable(Assets.level2scene5_1.findRegion("balloon2"));
        final DialogBox crowdDialogBox = new DialogBox(null, talkStyle2,
                100f, 335f,
                wordBalloon2.getMinWidth(), wordBalloon2.getMinHeight());
        crowdDialogBox.setBackground(wordBalloon2);
        crowdDialogBox.setTextPad(30f, 30f, 30f, 100f);
        crowdDialogBox.setVisible(false);
        crowdDialogBox.setClickAnimationPosition(
                wordBalloon2.getMinWidth() - crowdDialogBox.getClickAnimation().getPrefWidth() + 30f, 15f);
        crowdDialogBox.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type == CLICKED_AFTER_END) {
                    Timeline.createParallel()
                            .push(Tween.to(background2, ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(crowd, ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(crowdDialogBox.getActor(), ActorAccessor.ALPHA, 0.5f).target(0f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    fifthScenePart2();
                                }
                            })
                            .start(tweenManager);

                }
            }
        });
        crowdDialogBox.addToStage(mainStage);

        final AtomicInteger counter = new AtomicInteger(0);
        machadoSantosDialogBox.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if(type != CLICKED_AFTER_END)
                    return;

                if(counter.get() < speechFromMachadoSantos.length)
                    Assets.audio9[counter.get()].stop();

                if(counter.incrementAndGet() >= speechFromMachadoSantos.length){
                    machadoSantosDialogBox.setTouchable(Touchable.disabled);
                    Timeline.createSequence()
                            .beginParallel()
                            .push(Tween.to(background1, ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(machadoSantos, ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(machadoSantosDialogBox.getActor(), ActorAccessor.ALPHA, 0.5f).target(0f))
                            .end()
                            .push(Tween.mark().setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    background1.setVisible(false);
                                    machadoSantos.setVisible(false);
                                    machadoSantosDialogBox.setVisible(false);
                                    background2.setVisible(true);
                                    crowd.setVisible(true);
                                }
                            }))
                            .beginParallel()
                            .push(Tween.to(background2, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(crowd, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .end()
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    crowdDialogBox.setVisible(true);
                                    crowdDialogBox.setText(publicResponseToSpeech);
                                }
                            })
                            .start(tweenManager);
                }
                else {
                    machadoSantosDialogBox.setText(speechFromMachadoSantos[counter.get()]);
                    Assets.audio9[counter.get()].play();
                }
            }
        });

        Tween.to(background1, ActorAccessor.ALPHA, 1f).target(1f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                    @Override
                    public void onCompleted() {
                        Timeline.createSequence()
                                .push(Tween.to(background1, ActorAccessor.ALPHA, 0.5f).target(0f))
                                .beginParallel()
                                .push(Tween.to(background1, ActorAccessor.ALPHA, 0.5f).target(1f))
                                .push(Tween.to(machadoSantos, ActorAccessor.ALPHA, 0.5f).target(1f))
                                .end()
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        machadoSantosDialogBox.setVisible(true);
                                        machadoSantosDialogBox.setText(speechFromMachadoSantos[0]);
                                        Assets.audio9[0].play();
                                    }
                                }).start(tweenManager);
                    }
                });
                reporterThought.run(
                        beforeSpeechThought,
                        tweenManager);
            }
        }).start(tweenManager);


    }

    private void fifthScenePart2() {
        mainStage.clear();
        backgroundStage.clear();

        final Notebook.Element[][] notebookElements = {
                {Notebook.Element.QUIZ_FROM_1908_TITLE, Notebook.Element.QUIZ_FROM_1908_QUESTION1},
                {Notebook.Element.QUIZ_FROM_1908_QUESTION2},
                {Notebook.Element.QUIZ_FROM_1908_QUESTION3},
                {Notebook.Element.QUIZ_FROM_1908_QUESTION4},
                {Notebook.Element.QUIZ_FROM_1908_QUESTION5},
                {Notebook.Element.QUIZ_FROM_1908_QUESTION6},
                {Notebook.Element.QUIZ_FROM_1908_QUESTION7},
        };

        backgroundStage.addActor(new Image(Assets.level2scene5_2.findRegion("background")));
        backgroundStage.addActor(new Image(Assets.level2scene5_2.findRegion("notebook")));

        final Table trueOrFalse = new Table();
        trueOrFalse.center().bottom();
        trueOrFalse.setVisible(false);
        trueOrFalse.setTransform(true);
        trueOrFalse.setBackground(new TextureRegionDrawable(Assets.level2scene5_2.findRegion("panel")));
        trueOrFalse.setSize(trueOrFalse.getPrefWidth(), trueOrFalse.getPrefHeight());
        trueOrFalse.setOrigin(trueOrFalse.getWidth() / 2, trueOrFalse.getHeight() / 2);
        trueOrFalse.setPosition((mainStage.getWidth() - trueOrFalse.getWidth()) / 2f, 195f);

        final AtomicInteger counter = new AtomicInteger(0);

        Label.LabelStyle questionStyle = new Label.LabelStyle(uiSkin.getFont("write-font"), Color.BLACK);
        final Label question = new Label("O aumento do custo de vida era uma razão do descontentamento social.", questionStyle);
        question.setFontScale(0.8f);
        question.setWrap(true);

        ImageButton correctButton = new ImageButton(
                new TextureRegionDrawable(Assets.level2scene5_2.findRegion("correct_button_up")),
                new TextureRegionDrawable(Assets.level2scene5_2.findRegion("correct_button_down")));
        correctButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (trueOrFalseAnswers[counter.get()]) {
                    addScore(20);
                    Assets.successFX.play();
                } else {
                    addScore(10);
                    Assets.failFX.play();
                }
                trueOrFalse.setTouchable(Touchable.disabled);
                Timeline.createSequence()
                        .pushPause(0.25f)
                        .push(game.getNotebook().addElement(mainStage, notebookElements[counter.get()]))
                        .push(Tween.to(trueOrFalse, ActorAccessor.SCALEXY, 0.5f).target(0f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        if (counter.addAndGet(1) == trueOrFalseQuestions.length) {
                                            tweenManager.killAll();
                                            finishLevel(Game1910.ScreenType.LEVEL_SELECT);
                                        } else {
                                            question.setText(trueOrFalseQuestions[counter.get()]);
                                        }
                                    }
                                }))
                        .push(Tween.to(trueOrFalse, ActorAccessor.SCALEXY, 0.5f).target(1f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        trueOrFalse.setTouchable(Touchable.childrenOnly);
                                    }
                                }))
                        .start(tweenManager);
            }
        });

        ImageButton wrongButton = new ImageButton(
                new TextureRegionDrawable(Assets.level2scene5_2.findRegion("wrong_button_up")),
                new TextureRegionDrawable(Assets.level2scene5_2.findRegion("wrong_button_down")));
        wrongButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (trueOrFalseAnswers[counter.get()]) {
                    addScore(10);
                    Assets.failFX.play();
                } else {
                    addScore(20);
                    Assets.successFX.play();
                }
                trueOrFalse.setTouchable(Touchable.disabled);
                Timeline.createSequence()
                        .pushPause(0.25f)
                        .push(game.getNotebook().addElement(mainStage, notebookElements[counter.get()]))
                        .push(Tween.to(trueOrFalse, ActorAccessor.SCALEXY, 0.5f).target(0f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        if (counter.addAndGet(1) == trueOrFalseQuestions.length) {
                                            tweenManager.killAll();
                                            finishLevel(Game1910.ScreenType.LEVEL_SELECT);
                                        } else {
                                            question.setText(trueOrFalseQuestions[counter.get()]);
                                        }
                                    }
                                }))
                        .push(Tween.to(trueOrFalse, ActorAccessor.SCALEXY, 0.5f).target(1f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        trueOrFalse.setTouchable(Touchable.childrenOnly);
                                    }
                                }))
                        .start(tweenManager);
            }
        });

        trueOrFalse.add(question).colspan(2).padBottom(55f).width(410f).row();
        trueOrFalse.add(correctButton).right().padRight(32f).padBottom(43f);
        trueOrFalse.add(wrongButton).left().padLeft(32f).padBottom(43f);
        mainStage.addActor(trueOrFalse);


        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                trueOrFalse.setScale(0f);
                trueOrFalse.setVisible(true);
                trueOrFalse.setTouchable(Touchable.disabled);
                Tween.to(trueOrFalse, ActorAccessor.SCALEXY, 0.5f).target(1f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                trueOrFalse.setTouchable(Touchable.childrenOnly);
                            }
                        }).start(tweenManager);
            }
        });
        reporterThought.run(trueOrFalseHelp, tweenManager);


    }

    @Override
    protected void afterFinishLevel(Game1910.ScreenType nextScreen) {
        final Game1910.ScreenType screen = nextScreen;
        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                Level2.super.afterFinishLevel(screen);
            }
        });
        reporterThought.run(afterLevelThought, tweenManager);

    }

    @Override
    protected void updateUI(float delta) {
        super.updateUI(delta);
        concurrentManager.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
