package com.mobilelearning.game1910.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ObjectMap;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;


/**
 * Created by AFFonseca on 30/03/2015.
 */
public class Level3 extends LevelScreen {
    private static final String mainGuyThoughtBeforeGraph = "Necessito de convencer Machado dos Santos a " +
            "juntar-se a nós. Ajuda-me a escolher as melhores palavras...";

    private static final String conversationGraphStrings[][] = {
            {
                    "Gostei muito de o ouvir. Deixe-me que me apresente, sou Sub-diretor do Diário de Notícias",
                    "Obrigado. É um prazer conhecê-lo pessoalmente. Já tenho lido uns artigos seus."
            },
            {
                    "Não acha que devíamos entrar em ação em vez de ficarmos apenas pelas palavras? " +
                            "Uma revolta é o que o nosso país necessita.",
                    "Desculpe, não estou a perceber. Acho que o senhor não percebeu o que tentei transmitir."
            },
            {
                    "Desculpe, acho que a emoção me levou a exagerar..."
            },
            {
                    "Tome uma BICA comigo. Gostaria de conhecer melhor o seu ponto de vista sobre a mudança que defende.",
                    "Aceito. Mas com açúcar! Sigo a recomendação do cartaz lá fora 'beba isto com açúcar'. " +
                            "Pois é, meu caro. Urge uma mudança para este país. Todos já vimos que a monarquia não " +
                            "é adequada aos tempos que correm para quem almeja o país moderno."
            },
            {
                    "Não era a minha intenção ofendê-lo. Mas não se esqueça: dos fracos não reza a história.",
                    "O Sr não compreende! A violência apenas espalha o medo e fecha as mentes. " +
                            "Não posso concordar com tal ideia!"
            },
            {
                    "Concordo consigo. À frente de um país tem de estar um presidente, " +
                            "alguém que resulte da vontade da maioria. Mas temo que as mentalidades ainda não " +
                            "estejam preparadas... parece que o medo se instalou."
            },
            {
                    "Sim concordo. Mas como pode uma população escolher com consciência o seu governador " +
                            "se a maioria é analfabeta.  Temos que ser nós a escolher o nosso rumo.",
                    "Infelizmente as preocupações da monarquia nunca alcançaram as verdadeiras necessidades do povo." +
                            "Mas isso não nos dá o direto de impor as nossas escolhas, senão em nada seremos " +
                            "diferentes da monarquia..."
            },
            {
                    "Compreendo o que diz, mas temo que só pela força poderemos mudar este país...",
                    "Se é assim que pensa, desculpe mas os seus ideias são muito diferentes do " +
                            "partido republicano."
            },
            {
                    "Não são tão diferentes... concordo que à frente de um país tem de estar um presidente, " +
                            "alguém que resulte da vontade da maioria. Mas temo que as mentalidades " +
                            "ainda não estejam preparadas... parece que o medo se instalou."
            },
            {
                    "Bem sei, mas temos de divulgar os ideais em que acreditamos. O partido republicano " +
                            "tem feito campanhas nesse sentido."
            },
            {
                    " O partido republicano apenas tem feito uma guerra de palavras... Todos nós " +
                            "desejamos uma verdadeira mudança.",
                    "Já defendemos esta mudança há perto de 20 anos. E que pode aparentar que não " +
                            "passa de palavras, mas já houve mudanças. Temos de continuar a " +
                            "defender os nossos ideais para que mais pessoas se juntem a nós."
            },
            {
                    "Eu sei, tenho acompanhado. Mas permita-me que discorde. Isto não lá vai com 'palavrinhas " +
                            "mansas'. Importa que se termine de uma vez com este regime ignóbil. Só " +
                            "com uma revolta isso será possível.",
                    "Revolta?"
            },
            {
                    "Mudanças? Assistimos ao regicídio, muitos pensaram que era uma boa oportunidade, " +
                            "mas impuseram-nos um rei sem qualquer preparação. " +
                            "Temo que daqui para a frente ainda será pior!",
                    "Sim, todos tivemos esperança que realmente ocorresse uma mudança. O nosso " +
                            "pensamento tem sido divulgado e muitos concordam connosco, mas é difícil " +
                            "derrubar de um dia para o outro uma monarquia centenária como a nossa..."
            },
            {
                    "Sim. Amigo, eu faço parte de uma associação secreta. Também nós defendemos a " +
                            "República. A diferença é que nós estamos em crer que só com um golpe " +
                            "armado poderemos mudar. Precisamos de unir os nossos esforços.",
                    "Concordo. Confio em si. Sei que deseja o mesmo que nós. O golpe poderá ser" +
                            " mesmo a única opção..." +
                            " Reunimos hoje na sede do diretório do partido republicano português. " +
                            "Darei indicações para que possa entrar.",
                    "Certo. Obrigado. Não me atrasarei."
            }
    };

    private static final String conversationGraphOrder[][] = {
            {"J", "M"},
            {"J", "M"},
            {"J"},
            {"J", "M"},
            {"J", "M"},
            {"J"},
            {"J", "M"},
            {"J", "M"},
            {"J"},
            {"M"},
            {"J", "M"},
            {"J", "M"},
            {"J", "M"},
            {"J", "M", "J"},
    };

    private static final int conversationGraphConnections[][] = {
            {1, 3},
            {2, 4},
            {3},
            {5, 6},
            {2, 6},
            {9},
            {5, 7},
            {5, 8},
            {9},
            {10, 11},
            {11, 12},
            {13},
            {13},
            {},
    };

    private static final String conversationGraphConnectionKeys[][] = {
            {"Instigar a revolta", "Convidar para café"},
            {"Pedir desculpa", "Insistir"},
            {"Skip"},
            {"Defender as eleições", "Devemos escolher\no nosso rumo"},
            {"Pedir desculpa", "Concordar"},
            {"Skip"},
            {"Defender as eleições", "Defender a mudança\npela força"},
            {"Defender as eleições", "Defender a opinião"},
            {"Skip"},
            {"Discordar", "Mostrar que está a par"},
            {"Mostrar que está a par", "Discordar"},
            {"Skip"},
            {"Skip"},
            {},
    };

    private static final int conversationGraphScoreAdded[][] = {
            {-2, 0},
            {0, -2},
            {0},
            {0, -2},
            {0, -2},
            {0},
            {0, -2},
            {0, -2},
            {0},
            {-2, 0},
            {0, -2},
            {0},
            {60},
            {},
    };

    private static final String candidoDosReisTalk[] = {"Agora que estamos todos juntos temos connosco o jornalista " +
            "do DN. Está connosco para assistir a um momento histórico que deverá relatar futuramente com " +
            "a máxima precisão para que as próximas gerações conheçam os nossos propósitos independentemente " +
            "do que nos possa acontecer.",
            "Agradeço as palavras, mas estou também aqui, pois acredito na república e espero vir somente " +
                    "a publicar o sucesso da causa Repúblicana.",
            "Já conhece Miguel Bombarda e Machado dos Santos. Mas ainda" +
                    " não tive o prazer de lhe apresentar: Simões Raposo, José Barbosa, Inocêncio Camacho, " +
                    "José Cordeiro Júnior, José Relvas, Manuel Martins Cardoso e António Maria da Silva.",
            "Muito prazer.",
            "Hoje faremos história, estes são os nossos planos:",
            "Amigo Machado dos Santos conto que consiga o apoio da Infantaria 16.",
            "Deve depois a Infantaria 16 com toda a guarnição rumar à Artilharia 1.",
            "Um local bom para nos entricheirarmos será a rotunda na Praça Marquês de Pombal. Vários" +
                    " companheiros irão destruir os principais acessos à capital para que outras tropas " +
                    "não possam entrar e nos cerquem.",
            "Também os navios Adamastor e São Rafael nos irão ajudar com os militares.",
            "O nosso objetivo final é içar a bandeira da republica na Câmara Municipal.",
            "Com estes planos de certo que teremos força.",
            "Acredito que se fará história! Este é o momento!",
            "É o momento! A monarquia achincálha-nos e nós temos que nos decidir. Não posso garantir a vitória, " +
                    "mas afianço-lhes que a revolução, vencedora ou vencida, não será uma vergonha."
    };

    private static final String mapHelp = "Ajuda-me a legendar os locais para depois ser mais simples " +
            "a elaboração da notícia.";

    private static final String[] missingWordsMap = {
            "Quartel da infantaria 16",
            "Quartel da Artilharia 1",
            "Rotunda",
            "Navios",
            "Câmara Municipal"
    };

    private static final String daysOfRevolutionThought = "Esta ansiedade é agoniante... Este é um momento que " +
            "ficará assinalado na história em Portugal... Só espero que seja pelos melhores motivos e que as " +
            "novas gerações usufruam dos frutos da nossa luta. Acompanha-me para que nada fique por registar!";

    private static final String[][] phoneCalls = {
            {"Chefe, os monárquicos no Quartel General no Palácio das Necessidades estão a enviar " +
                    "pedidos a militares fora da Capital para se virem juntar a eles.",
                    "Certo, esteja atento a mais desenvolvimentos."},
            {"Amigo, sou eu, Cândido de Reis. Estive em reunião com os nossos companheiros nos Banhos" +
                    " de S. Paulo e sinto que fomos derrotados, não temos força suficiente para" +
                    " combater as tropas que ali se encontram ao lado do Rei. Temo um banho de sangue." +
                    " Eles querem fugir, mas para mim antes a morte. Adeus companheiro.",
                    "Cândido dos Reis, ainda há por onde lutar não podemos desistir!"},
            {"Chefe, soube agora que Cândido dos Reis se suicidou. Muitos populares que se tinham " +
                    "juntado às forças militares revoltosas estão descontentes e a abandonar o local.",
                    "Não podemos deixar que a morte dele tenha sido em vão... E as tropas que foram " +
                            "chamadas para apoiar a defesa do Rei no Palácio das Necessidades?",
                    "Pelo que sei não conseguem entrar em Lisboa, todos os acessos foram danificados.",
                    "Isso sim, já é uma boa notícia! Mais novidades avise-me."},
            {"O rei D. Manuel II e o séquito real abandonaram o Palácio das Necessidades. Estão em " +
                    "fuga depois do bombardeamento realizado pelos navios Adamastor e São Rafael " +
                    "cujos Almirantes se juntaram aos revolucionários.",
                    "Há alguma informação sobre para onde o Rei se dirige?",
                    "Foi-me relatado que o iate D. Amélia se dirige para a Ericeira onde o Rei irá " +
                            "embarcar para depois procurar exílio. A Inglaterra, eterna aliada da " +
                            "Monarquia, poderá ser o país escolhido!",
                    "O fim desta revolta está próximo!"}
};

    private static final String[][] phoneCallsOrder = {
            {"P", "R"},
            {"P", "R"},
            {"P", "R", "P", "R"},
            {"P", "R", "P", "R"},
    };

    private static final String [] cityHallConversation = {
            "Que alegria esta que inundou a praça.",
            "Sim, contentamento geral. Muitos dos militares que defendiam a república viram as " +
                    "bandeiras brancas da delegação alemã que queria sair de Lisboa, acharam " +
                    "que eram os revoltosos e depuseram as armas... ",
            "Ainda bem que assim foi! Evitou muitas mortes!",
            "Pelo que sei a morgue apenas registou 37 mortes nestes dias, no entanto há dezenas " +
                    "de feridos no Hospital S. José.",
            "Menos mal! Tenho que me juntar aos meus companheiros de luta. Viva a República!"
    };

    private static final String [] cityHallConversationOrder = {"R", "F", "R", "F", "R"};

    private static final String [] cityHallCrowd = {
            "Companheiros, alegrai-vos! A luta não foi em vão! Viva a República!",
            "Viva! Viva!"
    };

    private static final String quizQuestions[] = {
            "1. As tropas leais ao rei \n" +
                    "dominavam os navios:",
            "2. As tropas a favor da \n" +
                    "República dominaram:",
            "3. Um dos comandantes da\n" +
                    "revolução republicana foi:",
            "4. Os civis lutaram do\n" +
                    "lado dos:",
            "5. A república foi\n" +
                    "proclamada:",
            "6. Impedindo outros regimentos\n" +
                    "de apoiar os monárquicos,\n" +
                    " o acesso a Lisboa\n" +
                    "tinha sido cortado pela:",
            "7. O Palácio das\n" +
                    "Necessidades era:",
            "8. A bandeira branca que\n" +
                    "confundiu as tropas fiéis ao rei\n" +
                    " induzindo a sua rendição,\n" +
                    "significava apenas:"
    };
    private static final String quizAnswers[][] = {
            {"Adamastor", "D. Carlos", "Rafael"},
            {"Rossio", "Rotunda"},
            {"Machado dos Santos", "Paiva Couceiro"},
            {"Monárquicos", "Republicanos"},
            {"Na câmara municipal\nde Lisboa", "No Martinho d'Arcada"},
            {"Illuminati", "Carbonária"},
            {"A residência do rei", "O quartel general das\n" +
                    "forças republicanas"},
            {"O pedido para evacuar a\nlegação alemã", "O pedido de cessar fogo"}
    };

    private static  final String quizWrongExplanations[] = {
            "Ups! Adamastor e S. Rafael\naderiram à revolta republicana.",
            "Não! O Rossio foi o local em que\nas tropas fiés à monarquia\nse concentraram.",
            "Paiva Couceiro notabilizou-se\n" +
                    "pelas suas inccursões\nmonárquicas contra\n" +
                    "a primeira república.",
            "Estás enganado. Os monárquicos\nnão se sentiram apoiados pela\n" +
                    "maioria da população de Lisboa.",
            "O café Martinho D'Arcada foi um\nimportante local de discussão da\n" +
                    "vida política do país,\n mas não foi lá\n" +
                    "que se proclamou a república!",
            "Não. Illuminati diz respeito a uma\n" +
                    "sociedade secreta, defensora de\numa nova ordem mundial.",
            "As forças republicanas atacaram\no palácio das necessidades\n por ser a residência" +
                    "do\nrei D. Manuel",
            "Não foi bem assim. A bandeira\nbranca foi erradamente entendida\n" +
                    "como pedido de cessar fogo\nconfundindo as tropas\nmonárquicas."
    };

    private static final int correctQuizAsnwers[] = {2, 2, 1, 2, 1, 2, 1, 1};

    private static final String quizHelp = "Tenho que sintetizar as principais informações. " +
            "Por favor não me deixes esquecer nada...";

    private static final String paperHelp = "Sem querer, misturámos as placas com as fotos da " +
            "Implantação da República com outras que havia aqui no jornal. Por favor, ajuda-me a " +
            "selecionar as corretas para montar a maquete da próxima edição.";

    public Level3(Game1910 game, PolygonSpriteBatch batch) {
        super(game, batch, Assets.maxLevelScores[2]);
    }

    @Override
    public void load() {
        Assets.loadLevel3();
    }

    @Override
    public void prepare() {
        super.prepare();
        game.getNotebook().updateNotebook(Assets.levelValues[2], true);
        game.getNotebook().addElementNoAnimation(Notebook.Element.LEVEL_1910);
        Assets.prepareLevel3();
        soundPool.add(Assets.phoneFX);
    }

    @Override
    public void unload() {
        Assets.unloadLevel3();
    }

    @Override
    public void show() {
        firstScene();
    }

    protected void firstScene() {
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level3scene1.findRegion("background"));
        backgroundStage.addActor(background); background.getColor().a = 0.0f;

        final Image conversationImage = new Image(Assets.level3scene1.findRegion("conversation"));
        backgroundStage.addActor(conversationImage); conversationImage.getColor().a = 0.0f;

        final ConversationNode conversationNodes[] = new ConversationNode[conversationGraphStrings.length];
        final ConversationNode currentNode[] = {null};

        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);

        final TextureRegionDrawable thoughtBalloon = new TextureRegionDrawable(Assets.level3scene1.findRegion("thought_balloon"));
        final DialogBox thoughtBox = new DialogBox(null, talkStyle,
                121f, 508f, thoughtBalloon.getMinWidth(), thoughtBalloon.getMinHeight());
        thoughtBox.setBackground(thoughtBalloon); thoughtBox.setVisible(false);
        thoughtBox.setTextPad(30f, 30f, 30f, 90f);
        thoughtBox.setClickAnimationPosition(
                thoughtBalloon.getMinWidth() - thoughtBox.getClickAnimation().getPrefWidth() + 30f, 75f);
        thoughtBox.addToStage(mainStage);

        TextureRegion wordBalloonImage = Assets.level3scene1.findRegion("word_balloon");
        TextureRegion flippedWordBalloonImage = new TextureRegion(wordBalloonImage);
        flippedWordBalloonImage.flip(true, false);

        TextureRegionDrawable flippedWordBalloon = new TextureRegionDrawable(flippedWordBalloonImage);
        final DialogBox reporterBox = new DialogBox("", talkStyle,
                45f, 602f, flippedWordBalloon.getMinWidth(), flippedWordBalloon.getMinHeight());
        reporterBox.setBackground(flippedWordBalloon);
        reporterBox.setVisible(false);
        reporterBox.setTextPad(30f, 30f, 30f, 60f);
        reporterBox.setClickAnimationPosition(
                flippedWordBalloon.getMinWidth() - reporterBox.getClickAnimation().getPrefWidth() + 30f, -25f);
        reporterBox.addToStage(mainStage);

        TextureRegionDrawable wordBalloon = new TextureRegionDrawable(wordBalloonImage);
        final DialogBox machadoSantosBox = new DialogBox("", talkStyle,
                148f, 602f, wordBalloon.getMinWidth(), wordBalloon.getMinHeight());
        machadoSantosBox.setBackground(wordBalloon); machadoSantosBox.setVisible(false);
        machadoSantosBox.setTextPad(30f, 30f, 30f, 60f);
        machadoSantosBox.setClickAnimationPosition(
                wordBalloon.getMinWidth() - machadoSantosBox.getClickAnimation().getPrefWidth() + 30f, -25f);
        machadoSantosBox.addToStage(mainStage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(Assets.level3scene1.findRegion("button_up")),
        new TextureRegionDrawable(Assets.level3scene1.findRegion("button_down")),
                null, uiSkin.getFont("write-font")
        );

        final TextButton option1 = new TextButton("temp", textButtonStyle);
        final TextButton option2 = new TextButton("temp", textButtonStyle);

        option1.setPosition(193f, 255f);
        option1.setVisible(false);
        option1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                option1.setVisible(false);
                option2.setVisible(false);
                thoughtBox.setVisible(false);
                addScore(currentNode[0].getScore(option1.getText().toString()));
                currentNode[0] = currentNode[0].getEdge(option1.getText().toString());
                currentNode[0].getNodeValue().startConversation();
            }
        });
        mainStage.addActor(option1);

        option2.setPosition(193f, 149f);
        option2.setVisible(false);
        option2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                option1.setVisible(false);
                option2.setVisible(false);
                thoughtBox.setVisible(false);
                addScore(currentNode[0].getScore(option2.getText().toString()));
                currentNode[0] = currentNode[0].getEdge(option2.getText().toString());
                currentNode[0].getNodeValue().startConversation();
            }
        });
        mainStage.addActor(option2);

        for (int i = 0; i < conversationNodes.length; i++) {
            Conversation conversation = new Conversation(
                    new String[]{"J", "M"}, new DialogBox[]{reporterBox, machadoSantosBox},
                    conversationGraphOrder[i], conversationGraphStrings[i]);
            conversation.setCallback(new Conversation.ConversationCallback() {
                @Override
                public void onCompleted() {
                    ConversationNode node = currentNode[0];

                    switch (node.getKeys().size) {
                        case 0:
                            addScore(60);
                            reporterBox.setVisible(false);
                            machadoSantosBox.setVisible(false);
                            Timeline.createParallel()
                                    .push(Tween.to(background, ActorAccessor.ALPHA, 2f).target(0f))
                                    .push(Tween.to(conversationImage, ActorAccessor.ALPHA, 2f).target(0f))
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            secondScene();
                                        }
                                    }).start(tweenManager);
                            break;
                        case 1:
                            currentNode[0] = node.getEdge(node.getKeys().first());
                            currentNode[0].getNodeValue().startConversation();
                            break;
                        default:
                            machadoSantosBox.setVisible(false);
                            reporterBox.setVisible(false);
                            thoughtBox.setVisible(true);
                            thoughtBox.setText("O que respondo agora?");
                            option1.setText(node.getKeys().get(0));
                            option2.setText(node.getKeys().get(1));
                            thoughtBox.setCallback(new DialogCallback() {
                                @Override
                                public void onEvent(int type, DialogBox source) {
                                    if (type == ALL_END) {
                                        thoughtBox.getClickAnimation().setVisible(false);
                                        thoughtBox.setCallback(null);
                                        option1.setVisible(true);
                                        option2.setVisible(true);
                                    }
                                }
                            });
                    }
                }
            });

            conversationNodes[i] = new ConversationNode(conversation);
        }

        for (int i = 0; i < conversationNodes.length; i++) {
            for (int j = 0; j < conversationGraphConnections[i].length; j++) {
                conversationNodes[i].addEdge(conversationGraphConnectionKeys[i][j],
                        conversationNodes[conversationGraphConnections[i][j]],
                        conversationGraphScoreAdded[i][j]);
            }
        }

        Timeline.createParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 3f).target(1f))
                .push(Tween.to(conversationImage, ActorAccessor.ALPHA, 3f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        currentNode[0] = conversationNodes[0];
                        thoughtBox.setVisible(true);
                        thoughtBox.setText(mainGuyThoughtBeforeGraph);
                        thoughtBox.setCallback(new DialogCallback() {
                            @Override
                            public void onEvent(int type, DialogBox source) {
                                if (type == CLICKED_AFTER_END) {
                                    thoughtBox.setVisible(false);
                                    currentNode[0].getNodeValue().startConversation();
                                }
                            }
                        });

                    }
                }).start(tweenManager);

    }

    @Override
    protected void secondScene() {
        changeScene(SceneValue.SCENE_2);
        secondScenePart1();
    }

    private void secondScenePart1() {
        mainStage.clear();
        backgroundStage.clear();

        final float [][] markerPositions = {{78f, 767f}, {164f, 884f}, {294f, 911f},
                {195f, 660f}, {353f, 679f}};

        final Image background1 = new Image(Assets.level3scene2_1.findRegion("background1"));
        background1.getColor().a = 0.0f; backgroundStage.addActor(background1);

        final Image background2 = new Image(Assets.level3scene2_1.findRegion("background2"));
        background2.getColor().a = 0.0f; backgroundStage.addActor(background2);

        final Image map = new Image(Assets.level3scene2_2.findRegion("map"));
        map.getColor().a = 0.0f;
        map.setPosition((mainStage.getWidth() - map.getPrefWidth()) / 2, 619f);
        mainStage.addActor(map);

        final Image markers [] = new Image[5];
        for(int i=0; i<markerPositions.length; i++){
            markers[i] = new Image(Assets.level3scene2_1.findRegion("marker"));
            markers[i].setPosition(markerPositions[i][0], markerPositions[i][1]);
            markers[i].getColor().a = 0.0f;
            mainStage.addActor(markers[i]);
        }

        final AtomicInteger candidoReisPosition = new AtomicInteger(-253);
        final Image candidoReis = new Image(Assets.level3scene2_1.findRegion("candido_reis"));
        candidoReis.getColor().a = 0.0f; candidoReis.setPosition(256f, candidoReisPosition.get());
        mainStage.addActor(candidoReis);

        final AtomicInteger reporterPosition = new AtomicInteger(-121);
        final Image reporter = new Image(Assets.level3scene2_1.findRegion("reporter"));
        reporter.setPosition(203f, -reporter.getPrefHeight() - mainStage.getPadBottom());
        mainStage.addActor(reporter);

        final Table peopleTable = new Table();
        peopleTable.setSize(mainStage.getWidth(), 267f);
        peopleTable.setPosition(0f, 341f);
        peopleTable.defaults().pad(7f);
        peopleTable.setVisible(false); peopleTable.center();
        peopleTable.add(new Image(Assets.level3scene2_1.findRegion("machado_santos")));
        peopleTable.add(new Image(Assets.level3scene2_1.findRegion("miguel_bombarda")));
        peopleTable.add(new Image(Assets.level3scene2_1.findRegion("inocencio_camacho")));
        peopleTable.add(new Image(Assets.level3scene2_1.findRegion("jose_relvas"))).row();
        peopleTable.add(new Image(Assets.level3scene2_1.findRegion("antonio_maria_da_silva")));
        mainStage.addActor(peopleTable);

        final AtomicInteger counter = new AtomicInteger(0);

        Label.LabelStyle talkStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.BLACK);
        TextureRegionDrawable wordBalloon = new TextureRegionDrawable(
                Assets.level3scene2_1.findRegion("small_balloon"));
        final DialogBox dialogBox = new DialogBox(candidoDosReisTalk[0], talkStyle,
                62f, 603f, wordBalloon.getMinWidth(), wordBalloon.getMinHeight());
        dialogBox.setBackground(wordBalloon);
        dialogBox.setVisible(false);
        dialogBox.setTextPad(30f, 20f, 30f, 80f);
        dialogBox.setClickAnimationPosition(
                wordBalloon.getMinWidth() - dialogBox.getClickAnimation().getPrefWidth() + 30f, -5f);
        dialogBox.addToStage(mainStage);
        dialogBox.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type != CLICKED_AFTER_END)
                    return;

                if (counter.addAndGet(1) == candidoDosReisTalk.length) {
                    dialogBox.setTouchable(Touchable.disabled);
                    final Image mask = new Image(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("black_pixel")));
                    mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                            mainStage.getHeight() + mainStage.getPadBottom() * 2);
                    mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
                    mask.setTouchable(Touchable.disabled);
                    mask.getColor().a = 0.0f;
                    mainStage.addActor(mask);
                    Timeline.createSequence()
                            .push(game.getNotebook().addElement(mainStage,
                                    Notebook.Element.CANDIDO_REIS_SPEECH_TITLE,
                                    Notebook.Element.CANDIDO_REIS_SPEECH))
                            .push(Tween.to(mask, ActorAccessor.ALPHA, 1.0f).target(1f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    secondScenePart2();
                                }
                            }).start(tweenManager);
                    return;
                }

                dialogBox.setText(candidoDosReisTalk[counter.get()]);


                if (counter.get() < 5) {
                    dialogBox.setVisible(false);
                    Image entering, exiting;
                    int enteringPosition;

                    if (counter.get() % 2 == 0) {
                        entering = candidoReis;
                        enteringPosition = candidoReisPosition.get();
                        exiting = reporter;
                    } else {
                        entering = reporter;
                        enteringPosition = reporterPosition.get();
                        exiting = candidoReis;
                    }

                    Timeline.createSequence()
                            .push(Tween.to(exiting, ActorAccessor.MOVE_Y, 1.0f)
                                    .target(-exiting.getPrefHeight() - mainStage.getPadBottom()))
                            .push(Tween.to(entering, ActorAccessor.MOVE_Y, 1.0f).target(enteringPosition))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    dialogBox.setVisible(true);
                                    if (counter.get() == 2)
                                        peopleTable.setVisible(true);
                                }
                            }).start(tweenManager);
                }

                if (counter.get() == 4) {
                    peopleTable.setVisible(false);
                }

                if (counter.get() > 5 && counter.get() < 11) {
                    dialogBox.setVisible(false);
                    Timeline.createSequence()
                            .pushPause(0.5f)
                            .push(Tween.to(markers[counter.get() - 6], ActorAccessor.ALPHA, 1f).target(1f))
                            .pushPause(1f)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    dialogBox.setVisible(true);
                                }
                            }).start(tweenManager);
                }

            }
        });

        Timeline.createSequence()
                .push(Tween.to(background1, ActorAccessor.ALPHA, 2.0f).target(1.0f))
                .pushPause(2f)
                .push(Tween.to(background1, ActorAccessor.ALPHA, 0.5f).target(0f))
                .beginParallel()
                .push(Tween.to(background2, ActorAccessor.ALPHA, 0.5f).target(1f))
                .push(Tween.to(map, ActorAccessor.ALPHA, 2.0f).target(1f))
                .push(Tween.to(peopleTable, ActorAccessor.ALPHA, 2.0f).target(1f))
                .push(Tween.to(candidoReis, ActorAccessor.ALPHA, 2.0f).target(1f))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dialogBox.setVisible(true);
                    }
                }).start(tweenManager);
    }

    private void secondScenePart2() {
        mainStage.clear();
        backgroundStage.clear();

        final float [][] labelPositions = {{85f, -46f}, {85f, -94f}, {85f, -142f}, {85f, -191f},
                {85f, -239f}};

        final float [][] numberPositions = {{78f, 767f}, {164f, 884f}, {294f, 911f}, {195f, 660f},
                {353f, 679f}};

        final Image background = new Image(Assets.level3scene2_2.findRegion("background"));
        backgroundStage.addActor(background);

        final Image panel = new Image(Assets.level3scene2_2.findRegion("panel"));
        panel.setPosition((mainStage.getWidth() - panel.getPrefWidth()) / 2, 0f);
        mainStage.addActor(panel);

        final Table labelTable = new Table();
        labelTable.setSize(mainStage.getWidth(), 303f); labelTable.setPosition(0f, 336f);
        labelTable.center(); labelTable.padTop(12f);
        labelTable.defaults().padBottom(12f);
        for(int i=0; i<labelPositions.length; i++){
            labelTable.add(new Image(Assets.level3scene2_2.findRegion("drop" +(i+1)))).row();
        }
        mainStage.addActor(labelTable);

        TextureRegionDrawable map = new TextureRegionDrawable(Assets.level3scene2_2.findRegion("map"));
        Label.LabelStyle style = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.BLACK);
        final MissingWords missingWords = new MissingWords(uiSkin, map, 1.0f);
        missingWords.setImageTable(style, 1f, 561f, labelPositions,
                missingWordsMap);
        missingWords.getImageTable().setPosition((mainStage.getWidth() - map.getMinWidth()) / 2, 619f);
        mainStage.addActor(missingWords.getImageTable());

        missingWords.setHoverStyle(style);
        missingWords.setDragStyle(style);
        missingWords.setRandomWordScale(1.1f);
        missingWords.setDragScale(1.1f);
        missingWords.setCorrectWordsScale(1.1f);
        missingWords.setHoverScale(1.1f);

        missingWords.getWordsTable().defaults().padBottom(15f);
        missingWords.setWordsTable(style, missingWordsMap, 1);
        missingWords.getWordsTable().center();
        missingWords.getWordsTable().setSize(mainStage.getWidth(), 335f);
        mainStage.addActor(missingWords.getWordsTable());

        final Image mask = new Image(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("black_pixel")));
        mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                mainStage.getHeight() + mainStage.getPadBottom() * 2);
        mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
        mask.setTouchable(Touchable.disabled);

        missingWords.setCallback(new MissingWords.MissingWordsCallback() {
            @Override
            public void onCorrect(Label target) {
                addScore(30);
                Assets.successFX.play();
            }

            @Override
            public void onWrong(Label source, Label target) {
                addScore(-5);
                missingWords.getWordsTable().setTouchable(Touchable.disabled);
                target.setStyle(new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.MAROON));
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
                mask.setVisible(true);
                game.getNotebook().addElementNoAnimation(Notebook.Element.REVOLUTION_PLAN_MAP_TITLE);
                Timeline.createSequence()
                        .pushPause(0.5f)
                        .push(game.getNotebook().addElement(mainStage,
                                Notebook.Element.REVOLUTION_PLAN_MAP))
                        .push(Tween.to(mask, ActorAccessor.ALPHA, 1.5f).target(1f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                thirdScene();
                            }
                        }).start(tweenManager);
            }
        });

        for(int i=0; i<numberPositions.length; i++){
            Image number = new Image(Assets.level3scene2_2.findRegion("number" +(i+1)));
            number.setPosition(numberPositions[i][0], numberPositions[i][1]);
            mainStage.addActor(number);
        }

        missingWords.getWordsTable().setTouchable(Touchable.disabled);
        mainStage.addActor(mask);
        Tween.to(mask, ActorAccessor.ALPHA, 1f).target(0f)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        mask.setVisible(false);
                        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                            @Override
                            public void onCompleted() {
                                missingWords.getWordsTable().setTouchable(Touchable.childrenOnly);
                            }
                        });
                        reporterThought.run(mapHelp, tweenManager);
                    }
                }).start(tweenManager);

    }

    @Override
    protected void thirdScene() {
        changeScene(SceneValue.SCENE_3);
        thirdScenePart1();
    }

    private void thirdScenePart1() {
        mainStage.clear();
        backgroundStage.clear();

        final Image background = new Image(Assets.level3scene3_1.findRegion("background"));
        background.getColor().a = 0f; backgroundStage.addActor(background);

        final Table map = new Table();
        map.setBackground(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("map")));
        map.setSize(map.getBackground().getMinWidth(), map.getBackground().getMinHeight());
        map.setPosition(-108f, 8f); map.setTransform(true);
        map.getColor().a = 0f; mainStage.addActor(map);

        final Image phone = new Image(Assets.level3scene3_1.findRegion("phone"));
        phone.setPosition(43f, 579f); phone.setOrigin(phone.getPrefWidth() / 2, phone.getPrefHeight() / 2);
        phone.getColor().a = 0f; mainStage.addActor(phone);

        Label.LabelStyle style = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.lightBrown);
        TextureRegionDrawable panelImage = new TextureRegionDrawable(Assets.level3scene3_1.findRegion("panel"));
        style.background = panelImage;
        final Label date = new Label("3 de outubro", style); date.setAlignment(Align.center);
        date.setBounds((mainStage.getWidth() - panelImage.getMinWidth()) / 2,
                (mainStage.getHeight() - panelImage.getMinHeight()) - 5f,
                panelImage.getMinWidth(), panelImage.getMinHeight());
        date.setFontScale(1.05f);
        date.getColor().a = 0f; mainStage.addActor(date);

        final LinkedList<String []> scriptList= new LinkedList<>();

        final Image jornal = new Image(Assets.level3scene3_1.findRegion("message1"));
        jornal.setTouchable(Touchable.disabled);
        jornal.setPosition(mainStage.getWidth() + mainStage.getPadLeft(), -150f);
        mainStage.addActor(jornal);


        Timeline.createSequence()
                .pushPause(1f)
                .beginParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1.5f).target(1f))
                .push(Tween.to(map, ActorAccessor.ALPHA, 1.5f).target(1f))
                .push(Tween.to(date, ActorAccessor.ALPHA, 1.5f).target(1f))
                .push(Tween.to(phone, ActorAccessor.ALPHA, 1.5f).target(1f))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
                            @Override
                            public void onCompleted() {
                                Timeline.createSequence()
                                        .push(Tween.to(jornal, ActorAccessor.MOVE_X, 1f).target(68f))
                                        .setCallback(new TweenCallback() {
                                            @Override
                                            public void onEvent(int type, BaseTween<?> source) {
                                                jornal.setTouchable(Touchable.enabled);
                                            }
                                        }).start(tweenManager);
                            }
                        });
                        reporterThought.run(daysOfRevolutionThought, tweenManager);
                    }
                }).start(tweenManager);

        jornal.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                jornal.setTouchable(Touchable.disabled);
                Tween.to(jornal, ActorAccessor.MOVE_X, 1f)
                        .target(mainStage.getWidth() + mainStage.getPadLeft())
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                continueThirdScenePart1(background, map, phone, date, scriptList);
                            }
                        }).start(tweenManager);
            }
        });

        scriptList.add(new String[]{"notebookElement", "1"});
        scriptList.add(new String[]{"newDate", "3 de outubro (20h)"});
        scriptList.add(new String[]{"message", "2"});
        scriptList.add(new String[]{"mapLocation", "1"});
        scriptList.add(new String[]{"phoneCall", "1"});
        scriptList.add(new String[]{"mapLocation", "2"});
        scriptList.add(new String[]{"newDate", "4 de outubro (madrugada)"});
        scriptList.add(new String[]{"message", "3"});
        scriptList.add(new String[]{"mapLocation", "3"});
        scriptList.add(new String[]{"phoneCall", "2"});
        scriptList.add(new String[]{"mapLocation", "4"});
        scriptList.add(new String[]{"phoneCall", "3"});
        scriptList.add(new String[]{"notebookElement", "2"});
        scriptList.add(new String[]{"newDate", "5 de outubro (madrugada)"});
        scriptList.add(new String[]{"message", "4"});
        scriptList.add(new String[]{"mapLocation", "5"});
        scriptList.add(new String[]{"newDate", "5 de outubro (manhã)"});
        scriptList.add(new String[]{"phoneCall", "4"});
        scriptList.add(new String[]{"mapLocation", "6"});
        scriptList.add(new String[]{"newDate", "5 de outubro (tarde)"});
        scriptList.add(new String[]{"message", "5"});
        scriptList.add(new String[]{"mapLocation", "7"});

    }

    private void continueThirdScenePart1(final Image background, final  Table map, final Image phone,
                                         final Label date, final LinkedList<String []> script){

        if(script.isEmpty()){
            Timeline.createSequence()
                    .push(game.getNotebook().addElement(mainStage,
                            Notebook.Element.REVOLUTION_EVENTS_MAP_TITLE,
                            Notebook.Element.REVOLUTION_EVENTS_MAP))
                    .beginParallel()
                    .push(Tween.to(background, ActorAccessor.ALPHA, 1f).target(0f))
                    .push(Tween.to(map, ActorAccessor.ALPHA, 1f).target(0f))
                    .push(Tween.to(date, ActorAccessor.ALPHA, 1f).target(0f))
                    .push(Tween.to(phone, ActorAccessor.ALPHA, 1f).target(0f))
                    .end()
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            thirdScenePart2();
                        }
                    }).start(tweenManager);
            return;
        }

        String primaryValue = script.getFirst()[0];
        String secondaryValue = script.getFirst()[1];
        script.removeFirst();

        switch (primaryValue){
            case "notebookElement":
                thirdSceneNotebookElement(background, map, phone, date, script, secondaryValue);
                break;
            case "newDate":
                thirdSceneNewDate(background, map, phone, date, script, secondaryValue);
                break;
            case "message":
                thirdSceneNewMessage(background, map, phone, date, script, secondaryValue);
                break;
            case "phoneCall":
                thirdScenePhoneCall(background, map, phone, date, script, secondaryValue);
                break;
            case "mapLocation":
                thirdSceneMapLocation(background, map, phone, date, script, secondaryValue);
        }
    }

    private void thirdSceneNotebookElement(final Image background, final  Table map,
                                           final Image phone, final Label date,
                                           final LinkedList<String []> script, final String elementNumber){

        Notebook.Element elements [][] = {
                {Notebook.Element.REVOLUTION_DEATHS_TITLE, Notebook.Element.MIGUEL_BOMBARDA_DEATH},
                {Notebook.Element.CANDIDO_REIS_DEATH}
        };

        Timeline.createSequence()
                .push(game.getNotebook().addElement(mainStage, elements[Integer.parseInt(elementNumber)-1]))
                .pushPause(0.5f)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        continueThirdScenePart1(background, map, phone, date, script);
                    }
                }).start(tweenManager);

    }

    private void thirdSceneNewDate(final Image background, final  Table map,
                                   final Image phone, final Label date,
                                   final LinkedList<String []> script, final String newDate){
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1.5f).target(0f))
                .push(Tween.to(map, ActorAccessor.ALPHA, 1.5f).target(0f))
                .push(Tween.to(date, ActorAccessor.ALPHA, 1.5f).target(0f))
                .push(Tween.to(phone, ActorAccessor.ALPHA, 1.5f).target(0f))
                .end()
                .push(Tween.mark().setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        date.setText(newDate);
                    }
                }))
                .beginParallel()
                .push(Tween.to(background, ActorAccessor.ALPHA, 1.5f).target(1f))
                .push(Tween.to(map, ActorAccessor.ALPHA, 1.5f).target(1f))
                .push(Tween.to(date, ActorAccessor.ALPHA, 1.5f).target(1f))
                .push(Tween.to(phone, ActorAccessor.ALPHA, 1.5f).target(1f))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        continueThirdScenePart1(background, map, phone, date, script);
                    }
                }).start(tweenManager);

    }


    private void thirdSceneNewMessage(final Image background, final  Table map,
                                      final Image phone, final Label date,
                                      final LinkedList<String []> script, final String messageNumber){

        final AtomicInteger positionDifference = new AtomicInteger(-350);
        final AtomicInteger outOfViewPosition =
                new AtomicInteger((int)(-mainStage.getWidth()-mainStage.getPadLeft()));

        final Image message = new Image(Assets.level3scene3_1.findRegion("message" +messageNumber));
        message.setPosition(outOfViewPosition.get(), 170f); message.setTouchable(Touchable.disabled);
        mainStage.addActor(message);

        final Image hand = new Image(Assets.level3scene3_1.findRegion("hand"));
        hand.setPosition(outOfViewPosition.get() + positionDifference.get(), 120f);
        mainStage.addActor(hand);

        final AtomicInteger middlePosition =
                new AtomicInteger((int)((mainStage.getWidth()- message.getPrefWidth())/2));

        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(message, ActorAccessor.MOVE_X, 1f).target(middlePosition.get()))
                .push(Tween.to(hand, ActorAccessor.MOVE_X, 1f)
                        .target(middlePosition.get() + positionDifference.get()))
                .end()
                .pushPause(0.25f)
                .push(Tween.to(hand, ActorAccessor.MOVE_X, 1f)
                        .target(outOfViewPosition.get() + positionDifference.get()))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        message.setTouchable(Touchable.enabled);
                    }
                }).start(tweenManager);

        message.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                message.setTouchable(Touchable.disabled);
                Timeline.createSequence()
                        .push(Tween.to(hand, ActorAccessor.MOVE_X, 1f)
                                .target(middlePosition.get() + positionDifference.get()))
                        .pushPause(0.25f)
                        .beginParallel()
                        .push(Tween.to(message, ActorAccessor.MOVE_X, 1f).target(outOfViewPosition.get()))
                        .push(Tween.to(hand, ActorAccessor.MOVE_X, 1f)
                                .target(outOfViewPosition.get() + positionDifference.get()))
                        .end()
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                message.remove();
                                hand.remove();
                                continueThirdScenePart1(background, map, phone, date, script);
                            }
                        }).start(tweenManager);
            }
        });
    }

    private void thirdScenePhoneCall(final Image background, final  Table map,
                                     final Image phone, final Label date,
                                     final LinkedList<String []> script, final String phoneCallNumber){

        final Image phoneWave = new Image(new TextureRegionDrawable(Assets.level3scene3_1. findRegion("phone_ring")));
        phoneWave.setPosition(0f, 687f);
        phoneWave.setTouchable(Touchable.disabled); //For the phone to be touchable
        mainStage.getRoot().addActorAfter(phone, phoneWave);

        final Image mask = new Image(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("black_pixel")));
        mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                mainStage.getHeight() + mainStage.getPadBottom() * 2);
        mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
        mask.setTouchable(Touchable.disabled); mask.getColor().a = 0.0f; mainStage.addActor(mask);

        final Image reporterOnPhone = new Image(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("reporter")));
        reporterOnPhone.setPosition(216f, -74f - mainStage.getPadBottom());
        reporterOnPhone.setTouchable(Touchable.disabled); //For the phone to be touchable
        reporterOnPhone.getColor().a = 0f; mainStage.addActor(reporterOnPhone);

        final TextureRegionDrawable wordBalloon1 = new TextureRegionDrawable(Assets.level3scene3_1.findRegion("balloon"));
        Label.LabelStyle talkStyle1 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        final DialogBox reporterBox = new DialogBox(null, talkStyle1,
                99f, 546f-mainStage.getPadBottom(), wordBalloon1.getMinWidth(), wordBalloon1.getMinHeight());
        reporterBox.setBackground(wordBalloon1);
        reporterBox.setTextPad(30f, 30f, 30f, 60f);
        reporterBox.setVisible(false);
        reporterBox.setClickAnimationPosition(
                wordBalloon1.getMinWidth() - reporterBox.getClickAnimation().getPrefWidth() + 30f, -25f);
        reporterBox.addToStage(mainStage);

        final TextureRegionDrawable wordBalloon2 = new TextureRegionDrawable(Assets.level3scene3_1.findRegion("phone_balloon"));
        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        final DialogBox phoneBox = new DialogBox(null, talkStyle2,
                52f, 218f-mainStage.getPadBottom(), wordBalloon2.getMinWidth(), wordBalloon2.getMinHeight());
        phoneBox.setBackground(wordBalloon2);
        phoneBox.setTextPad(30f, 30f, 30f, 330f);
        phoneBox.setVisible(false);
        phoneBox.setClickAnimationPosition(
                wordBalloon2.getMinWidth() - phoneBox.getClickAnimation().getPrefWidth() + 30f, 235f);
        phoneBox.addToStage(mainStage);

        final AtomicInteger conversationNumber = new AtomicInteger(Integer.parseInt(phoneCallNumber)-1);

        //Phone ringing animation
        Timeline.createSequence()
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
                .repeat(Tween.INFINITY, 0.0f)
                .start(tweenManager);

        phone.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                tweenManager.killAll();
                Assets.phoneFX.stop();
                phoneWave.remove();
                phone.setRotation(0);
                phone.removeListener(phone.getListeners().first());

                Timeline.createParallel()
                        .push(Tween.to(reporterOnPhone, ActorAccessor.ALPHA, 0.5f).target(1f))
                        .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0.8f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                Conversation conversation = new Conversation(new String[]{"R", "P"},
                                        new DialogBox[]{reporterBox, phoneBox},
                                        phoneCallsOrder[conversationNumber.get()],
                                        phoneCalls[conversationNumber.get()]);
                                conversation.setCallback(new Conversation.ConversationCallback() {
                                    @Override
                                    public void onCompleted() {
                                        phoneBox.setVisible(false);
                                        reporterBox.setVisible(false);
                                        Timeline.createParallel()
                                                .push(Tween.to(reporterOnPhone, ActorAccessor.ALPHA, 0.5f).target(0f))
                                                .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0f))
                                                .setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        reporterOnPhone.remove();
                                                        mask.remove();
                                                        phoneBox.getActor().remove();
                                                        reporterBox.getActor().remove();
                                                        continueThirdScenePart1(background, map, phone, date, script);
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

    private void thirdSceneMapLocation(final Image background, final  Table map,
                                     final Image phone, final Label date,
                                     final LinkedList<String []> script, final String locationNumber){

        final float [][] positions = {{194f, 258f}, {118f, 218f}, {300f, 494f}, {547f, 167f},
                {538f, 582f}, {128f, 228f}, {678f, 164f}};

        final boolean [] isCheck = {true, false, true, false, true, false, true};

        int index = Integer.parseInt(locationNumber)-1;

        final Image location;
        if(isCheck[index]){
            location = new Image(Assets.level3scene3_1.findRegion("check"));
        }
        else{
            location = new Image(Assets.level3scene3_1.findRegion("circle"));
        }

        map.setOrigin(positions[index][0] + location.getPrefWidth() / 2,
                positions[index][1] + location.getPrefHeight() / 2);
        location.setPosition(positions[index][0], positions[index][1]);
        location.getColor().a = 0f;
        map.addActor(location);

        if(index == 1 || index==5) //near the left margin
            map.setOriginX(map.getOriginX()-location.getPrefWidth() / 2 - 10f);
        else if(index == 6) //near the right margin
            map.setOriginX(map.getOriginX() + location.getPrefWidth() / 2 + 10f);

        mainStage.getRoot().addActorAfter(phone, map);
        Timeline.createSequence()
                .push(Tween.to(map, ActorAccessor.SCALEXY, 1f).target(3f))
                .push(Tween.to(location, ActorAccessor.ALPHA, 1f).target(1f))
                .pushPause(1f)
                .push(Tween.to(map, ActorAccessor.SCALEXY, 1f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        mainStage.getRoot().addActorBefore(phone, map);
                        continueThirdScenePart1(background, map, phone, date, script);
                    }
                }).start(tweenManager);

    }

    private void thirdScenePart2(){
        mainStage.clear();
        backgroundStage.clear();

        final Image background1 = new Image(Assets.level3scene3_2.findRegion("background_blurred1"));
        final Image background2 = new Image(Assets.level3scene3_2.findRegion("background_blurred2"));
        final Image background3 = new Image(Assets.level3scene3_2.findRegion("background_blurred3"));
        background1.getColor().a = 0f; background2.getColor().a = 0f; background3.getColor().a = 0f;
        backgroundStage.addActor(background1);
        backgroundStage.addActor(background2);
        backgroundStage.addActor(background3);

        final Image foreground1 = new Image(Assets.level3scene3_2.findRegion("background_normal1"));
        final Image foreground2 = new Image(Assets.level3scene3_2.findRegion("background_normal2"));
        final Image foreground3 = new Image(Assets.level3scene3_2.findRegion("background_normal3"));
        foreground1.getColor().a = 0f; foreground2.getColor().a = 0f; foreground3.getColor().a = 0f;
        mainStage.addActor(foreground1);
        mainStage.addActor(foreground2);
        mainStage.addActor(foreground3);

        final Image reporters = new Image(Assets.level3scene3_2.findRegion("reporters"));
        reporters.setPosition(-311f, -903f); reporters.getColor().a = 0f;
        mainStage.addActor(reporters);

        final TextureRegion wordBalloonTexture = Assets.level3scene3_1.findRegion("balloon");
        final TextureRegionDrawable wordBalloon1 = new TextureRegionDrawable(wordBalloonTexture);
        Label.LabelStyle talkStyle1 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.GRAY);
        final DialogBox reporterBox1 = new DialogBox(null, talkStyle1,
                158f, 553f, wordBalloon1.getMinWidth(), wordBalloon1.getMinHeight());
        reporterBox1.setBackground(wordBalloon1);
        reporterBox1.setTextPad(30f, 30f, 30f, 60f);
        reporterBox1.setVisible(false);
        reporterBox1.setClickAnimationPosition(
                wordBalloon1.getMinWidth() - reporterBox1.getClickAnimation().getPrefWidth() + 30f, -25f);
        reporterBox1.addToStage(mainStage);

        final TextureRegion wordBalloonTextureFlipped = new TextureRegion(wordBalloonTexture);
        wordBalloonTextureFlipped.flip(true, false);
        final TextureRegionDrawable wordBalloon2 = new TextureRegionDrawable(wordBalloonTextureFlipped);
        final DialogBox reporterBox2 = new DialogBox(null, talkStyle1,
                10f, 523f, wordBalloon2.getMinWidth(), wordBalloon2.getMinHeight());
        reporterBox2.setBackground(wordBalloon2);
        reporterBox2.setTextPad(30f, 30f, 30f, 60f);
        reporterBox2.setVisible(false);
        reporterBox2.setClickAnimationPosition(
                wordBalloon2.getMinWidth() - reporterBox2.getClickAnimation().getPrefWidth() + 30f, -25f);
        reporterBox2.addToStage(mainStage);

        Label.LabelStyle talkStyle2 = new Label.LabelStyle(uiSkin.getFont("speech-font"), Color.WHITE);
        TextureRegionDrawable wordBalloon3 = new TextureRegionDrawable(Assets.level3scene3_2.findRegion("crowdBalloon"));
        final DialogBox crowdDialogBox = new DialogBox(null, talkStyle2,
                100f, 615f,
                wordBalloon3.getMinWidth(), wordBalloon3.getMinHeight());
        crowdDialogBox.setBackground(wordBalloon3);
        crowdDialogBox.setTextPad(30f, 30f, 30f, 100f);
        crowdDialogBox.setVisible(false);
        crowdDialogBox.setClickAnimationPosition(
                wordBalloon3.getMinWidth() - crowdDialogBox.getClickAnimation().getPrefWidth() + 30f, 15f);
        crowdDialogBox.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type == CLICKED_AFTER_END) {
                    crowdDialogBox.setCallback(null);
                    Timeline.createParallel()
                            .push(Tween.to(background3, ActorAccessor.ALPHA, 4f).target(0f))
                            .push(Tween.to(foreground3, ActorAccessor.ALPHA, 4f).target(0f))
                            .push(Tween.to(crowdDialogBox.getActor(), ActorAccessor.ALPHA, 4f).target(0f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    forthScene();
                                }
                            })
                            .start(tweenManager);

                }
            }
        });
        crowdDialogBox.addToStage(mainStage);

        final Timeline thirdSequence = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(background2, ActorAccessor.ALPHA, 1f).target(0f))
                .push(Tween.to(foreground2, ActorAccessor.ALPHA, 1f).target(0f))
                .end()
                .beginParallel()
                .push(Tween.to(background3, ActorAccessor.ALPHA, 1f).target(1f))
                .push(Tween.to(foreground3, ActorAccessor.ALPHA, 1f).target(1f))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        crowdDialogBox.setText(cityHallCrowd[1]);
                        crowdDialogBox.setVisible(true);
                    }
                });

        final Timeline secondSequence = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(background1, ActorAccessor.ALPHA, 1f).target(0f))
                .push(Tween.to(foreground1, ActorAccessor.ALPHA, 1f).target(0f))
                .push(Tween.to(reporters, ActorAccessor.ALPHA, 1f).target(0f))
                .end()
                .beginParallel()
                .push(Tween.to(background2, ActorAccessor.ALPHA, 1f).target(1f))
                .push(Tween.to(foreground2, ActorAccessor.ALPHA, 1f).target(1f))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        reporterBox2.getActor().setPosition(54f, 684f);
                        reporterBox2.setText(cityHallCrowd[0]);
                        reporterBox2.setVisible(true);
                        reporterBox2.setCallback(new DialogCallback() {
                            @Override
                            public void onEvent(int type, DialogBox source) {
                                if(type == CLICKED_AFTER_END) {
                                    reporterBox2.setVisible(false);
                                    thirdSequence.start(tweenManager);
                                }
                            }
                        });
                    }
                });


        Timeline.createParallel()
                .push(Tween.to(background1, ActorAccessor.ALPHA, 2f).target(1f))
                .push(Tween.to(foreground1, ActorAccessor.ALPHA, 2f).target(1f))
                .push(Tween.to(reporters, ActorAccessor.ALPHA, 2f).target(1f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Conversation conversation = new Conversation(new String[]{"R", "F"},
                                new DialogBox[]{reporterBox1, reporterBox2},
                                cityHallConversationOrder, cityHallConversation);
                        conversation.setCallback(new Conversation.ConversationCallback() {
                            @Override
                            public void onCompleted() {
                                reporterBox1.setVisible(false);
                                reporterBox2.setVisible(false);
                                secondSequence.start(tweenManager);
                            }
                        });
                        conversation.startConversation();
                    }
                }).start(tweenManager);

    }

    protected void forthScene() {
        changeScene(SceneValue.SCENE_4);
        mainStage.clear();
        backgroundStage.clear();

        final Notebook.Element [][] notebookElements = {
                {Notebook.Element.QUIZ_FROM_1910_TITLE, Notebook.Element.QUIZ_FROM_1910_QUESTION1},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION2},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION3},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION4},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION5},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION6},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION7},
                {Notebook.Element.QUIZ_FROM_1910_QUESTION8},
        };

        Image background = new Image(Assets.level3scene2_2.findRegion("background"));
        backgroundStage.addActor(background);

        TextureRegionDrawable notebook = new TextureRegionDrawable(Assets.level3scene4.findRegion("notebook"));
        final Table quiz = new Table();
        quiz.center().top();
        quiz.setSize(notebook.getMinWidth(), notebook.getMinHeight());
        quiz.setY(-quiz.getHeight()-mainStage.getPadBottom());
        quiz.setBackground(notebook);
        quiz.setTouchable(Touchable.disabled);

        final AtomicInteger counter = new AtomicInteger(0);


        final Label.LabelStyle questionStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK);
        final Label question = new Label(quizQuestions[0], questionStyle);
        question.setAlignment(Align.center); question.setFontScale(1.2f);

        final TextButton.TextButtonStyle optionStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(Assets.level3scene4.findRegion("question_button")),
                        null, null, uiSkin.getFont("default-font")
        );
        optionStyle.fontColor = Color.WHITE;

        final TextButton option1 = new TextButton(quizAnswers[0][0], optionStyle);
        option1.setOrigin(option1.getPrefWidth()/2, option1.getPrefHeight()/2);
        final TextButton option2 = new TextButton(quizAnswers[0][1], optionStyle);
        option2.setOrigin(option2.getPrefWidth()/2, option2.getPrefHeight()/2);
        final TextButton option3 = new TextButton(quizAnswers[0][2], optionStyle);
        option3.setOrigin(option3.getPrefWidth() / 2, option3.getPrefHeight() / 2);
        option1.setTransform(true); option2.setTransform(true); option3.setTransform(true);

        quiz.padTop(186f);
        quiz.add(question).padBottom(19f).row();
        quiz.add(option1).padBottom(17f).row();
        quiz.add(option2).padBottom(17f).row();
        quiz.add(option3).padBottom(17f).row();
        mainStage.addActor(quiz);

        final Image panel = new Image(Assets.level3scene4.findRegion("panel"));
        panel.setPosition((mainStage.getWidth()-panel.getPrefWidth())/2, 868f);
        mainStage.addActor(panel);

        final Image mask = new Image(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("black_pixel")));
        mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                mainStage.getHeight() + mainStage.getPadBottom() * 2);
        mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
        mask.setTouchable(Touchable.disabled); mask.getColor().a = 0.0f; mainStage.addActor(mask);

        final Label.LabelStyle wrongExplanationStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        final Label wrongExplanation = new Label(quizWrongExplanations[0], wrongExplanationStyle);
        wrongExplanation.getColor().a = 0.0f; wrongExplanation.setTouchable(Touchable.disabled);
        wrongExplanation.setAlignment(Align.center);
        wrongExplanation.setSize(mainStage.getWidth(), mainStage.getHeight());
        wrongExplanation.setFontScale(1.2f); mainStage.addActor(wrongExplanation);

        ClickListener callbackArray [] = new ClickListener[3];
        for(int i=0; i<callbackArray.length; i++){
            final AtomicInteger aux = new AtomicInteger(i);

            callbackArray[i] = new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    quiz.setTouchable(Touchable.disabled);

                    final Timeline changeQuestion = Timeline.createSequence()
                            .beginParallel()
                            .push(Tween.to(question, ActorAccessor.ALPHA, 0.5f).target(0f))
                            .push(Tween.to(option1, ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .push(Tween.to(option2, ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .push(Tween.to(option3, ActorAccessor.SCALEXY, 0.5f).target(0f))
                            .end()
                            .push(Tween.mark().setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    if (counter.addAndGet(1) == correctQuizAsnwers.length) {
                                        tweenManager.killAll();
                                        Timeline.createParallel()
                                                .push(Tween.to(quiz, ActorAccessor.MOVE_Y, 1f)
                                                        .target(-quiz.getHeight() - mainStage.getPadBottom()))
                                                .push(Tween.to(panel, ActorAccessor.ALPHA, 1f).target(0f))
                                                .setCallback(new TweenCallback() {
                                                    @Override
                                                    public void onEvent(int type, BaseTween<?> source) {
                                                        fifthScene();
                                                    }
                                                }).start(tweenManager);
                                    }
                                    else {
                                        question.setText(quizQuestions[counter.get()]);
                                        quiz.setTouchable(Touchable.childrenOnly);
                                        option1.setText(quizAnswers[counter.get()][0]);
                                        option2.setText(quizAnswers[counter.get()][1]);
                                        if (quizAnswers[counter.get()].length > 2) {
                                            option3.setText(quizAnswers[counter.get()][2]);
                                            option3.setVisible(true);
                                        } else
                                            option3.setVisible(false);
                                    }
                                }
                            }))
                            .beginParallel()
                            .push(Tween.to(question, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(option1, ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .push(Tween.to(option2, ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .push(Tween.to(option3, ActorAccessor.SCALEXY, 0.5f).target(1f))
                            .end();


                    if (correctQuizAsnwers[counter.get()] == aux.get()+1) {
                        addScore(20);
                        Assets.successFX.play();

                        Timeline.createSequence()
                                .pushPause(0.5f)
                                .push(game.getNotebook().addElement(mainStage, notebookElements[counter.get()]))
                                .push(changeQuestion)
                                .start(tweenManager);
                    } else {
                        addScore(14);
                        Assets.failFX.play();

                        wrongExplanation.setText(quizWrongExplanations[counter.get()]);
                        wrongExplanation.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                wrongExplanation.removeListener(this);
                                wrongExplanation.setTouchable(Touchable.disabled);
                                Timeline.createSequence()
                                        .beginParallel()
                                        .push(Tween.to(mask, ActorAccessor.ALPHA, 1f).target(0f))
                                        .push(Tween.to(wrongExplanation, ActorAccessor.ALPHA, 1f).target(0f))
                                        .end()
                                        .push(game.getNotebook().addElement(mainStage, notebookElements[counter.get()]))
                                        .push(changeQuestion)
                                        .start(tweenManager);

                            }
                            });

                        Timeline.createSequence()
                                .beginParallel()
                                .push(Tween.to(mask, ActorAccessor.ALPHA, 1f).target(0.8f))
                                .push(Tween.to(wrongExplanation, ActorAccessor.ALPHA, 1f).target(1f))
                                .end()
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        wrongExplanation.setTouchable(Touchable.enabled);
                                    }
                                }).start(tweenManager);
                    }
                }
            };
        }

        option1.getLabel().setFontScale(1.1f);
        option1.addListener(callbackArray[0]);

        option2.getLabel().setFontScale(1.1f);
        option2.addListener(callbackArray[1]);


        option3.getLabel().setFontScale(1.1f);
        option3.addListener(callbackArray[2]);

        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                Tween.to(quiz, ActorAccessor.MOVE_Y, 1f).target(0f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                quiz.setTouchable(Touchable.childrenOnly);
                            }
                        }).start(tweenManager);
            }
        });
        reporterThought.run(quizHelp, tweenManager);
    }

    protected void  fifthScene(){
        changeScene(SceneValue.SCENE_5);
        mainStage.clear();
        backgroundStage.clear();

        Image background = new Image(Assets.level3scene2_2.findRegion("background"));
        backgroundStage.addActor(background);

        final boolean [] transitionToPaper = {false};
        final Image [] fromAndToImages = new  Image[2];


        final Table paper = new Table();
        paper.setBackground(new TextureRegionDrawable(Assets.level3scene5.findRegion("paper")));
        paper.setSize(mainStage.getWidth(), mainStage.getHeight()); paper.setTransform(true);
        paper.setOrigin(paper.getPrefWidth() / 2, paper.getPrefHeight() / 2);
        paper.setScale(0f); paper.setTouchable(Touchable.disabled);
        paper.top();
        mainStage.addActor(paper);

        final ImageButton finishButton = new ImageButton(
                new TextureRegionDrawable(Assets.level3scene5.findRegion("play_button_up")),
                new TextureRegionDrawable(Assets.level3scene5.findRegion("play_button_down"))
        );
        finishButton.setPosition((mainStage.getWidth() - finishButton.getPrefWidth()) / 2, 265f);
        finishButton.setVisible(false); mainStage.addActor(finishButton);

        final Image mask = new Image(new TextureRegionDrawable(Assets.level3scene3_1.findRegion("black_pixel")));
        mask.setSize(mainStage.getWidth() + mainStage.getPadLeft() * 2,
                mainStage.getHeight() + mainStage.getPadBottom() * 2);
        mask.setPosition(-mainStage.getPadLeft(), -mainStage.getPadBottom());
        mask.setTouchable(Touchable.disabled); mask.getColor().a = 0.0f; mainStage.addActor(mask);

        final Table paperImagesTable = new Table();
        final Image [] blankSpaces = new Image[6];
        final Image [] images = new Image[9];
        final Image [] informations = new Image[9];

        final ImageButton acceptButton = new ImageButton(
                new TextureRegionDrawable(Assets.level3scene5.findRegion("correct_button_up")),
                new TextureRegionDrawable(Assets.level3scene5.findRegion("correct_button_down"))
        );
        acceptButton.setPosition(210f, 134f); acceptButton.setTouchable(Touchable.disabled);

        final ImageButton declineButton = new ImageButton(
                new TextureRegionDrawable(Assets.level3scene5.findRegion("wrong_button_up")),
                new TextureRegionDrawable(Assets.level3scene5.findRegion("wrong_button_down"))
        );
        declineButton.setPosition(321f, 134f); declineButton.setTouchable(Touchable.disabled);

        acceptButton.getColor().a = 0f; declineButton.getColor().a = 0f;

        final TextureRegionDrawable blankSpaceImage = new TextureRegionDrawable(Assets.level3scene5.findRegion("blank_space"));
        for(int i=0; i< blankSpaces.length; i++){
            blankSpaces[i] = new Image(blankSpaceImage);
            blankSpaces[i].setTouchable(Touchable.disabled);
            blankSpaces[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Image fromImage = (Image) event.getListenerActor();
                    if (fromImage.getDrawable().equals(blankSpaceImage))
                        return;

                    transitionToPaper[0] = false;
                    fromAndToImages[0] = fromImage;

                    Image informationToShow = null;
                    for (int i = 0; i < images.length; i++) {
                        if (fromImage.getDrawable() == images[i].getDrawable()) {
                            fromAndToImages[1] = images[i];
                            informationToShow = informations[i];
                            break;
                        }
                    }

                    paper.setTouchable(Touchable.disabled);
                    finishButton.setTouchable(Touchable.disabled);
                    if(informationToShow != null)
                        informationToShow.setVisible(true);
                    Timeline.createParallel()
                            .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0.8f))
                            .push(Tween.to(informationToShow, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(declineButton, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(acceptButton, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    declineButton.setTouchable(Touchable.enabled);
                                    acceptButton.setTouchable(Touchable.enabled);
                                }
                            })
                            .start(tweenManager);

                }
            });
            paperImagesTable.add(blankSpaces[i]);

            if(i==2)
                paperImagesTable.row();
        }
        paper.add(paperImagesTable).padTop(215f).colspan(3).row();

        for(int i=0; i< images.length; i++){
            images[i] = new Image(Assets.level3scene5.findRegion("image" +(i+1)));
            images[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Image fromImage = (Image) event.getListenerActor();

                    transitionToPaper[0] = true;
                    fromAndToImages[0] = fromImage;

                    Image informationToShow = null;
                    for (int i = 0; i < images.length; i++) {
                        if (fromImage == images[i]) {
                            informationToShow = informations[i];
                            break;
                        }
                    }

                    boolean spaceWasFound = false;
                    for (Image blankSpace : blankSpaces) {
                        if (blankSpace.getDrawable().equals(blankSpaceImage)) {
                            spaceWasFound = true;
                            fromAndToImages[1] = blankSpace;
                            break;
                        }
                    }

                    if (!spaceWasFound)
                        return;

                    paper.setTouchable(Touchable.disabled);
                    finishButton.setTouchable(Touchable.disabled);
                    if(informationToShow != null)
                        informationToShow.setVisible(true);
                    Timeline.createParallel()
                            .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0.8f))
                            .push(Tween.to(informationToShow, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(declineButton, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .push(Tween.to(acceptButton, ActorAccessor.ALPHA, 0.5f).target(1f))
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    declineButton.setTouchable(Touchable.enabled);
                                    acceptButton.setTouchable(Touchable.enabled);
                                }
                            })
                            .start(tweenManager);
                }
            });

            informations[i] = new Image(Assets.level3scene5.findRegion("information" +(i+1)));
            informations[i].setTouchable(Touchable.disabled); informations[i].setVisible(false);
            informations[i].getColor().a = 0.0f;
            mainStage.addActor(informations[i]);
        }

        //Add images randomly to the stage
        int randomOrder [] = new int[images.length];
        for(int i=0; i<images.length; i++) {
            randomOrder[i] = i;
        }
        for(int i=0; i<randomOrder.length; i++) {
            int randomIndex = MathUtils.random(i, randomOrder.length - 1);
            int auxValue = randomOrder[i];
            randomOrder[i] = randomOrder[randomIndex];
            randomOrder[randomIndex] = auxValue;
        }
        for(int i=0; i<images.length; i++){
            float padLeft = 42f;
            if(i/3==1){
                padLeft *= -1;
            }
            paper.add(images[randomOrder[i]]).padBottom(-100f).padLeft(padLeft);

            if(i%3==2)
                paper.row();
        }


        acceptButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {


                final Image informationToHide;
                Image temp = null;
                for (Image information : informations) {
                    if (information.isVisible()) {
                        temp = information;
                        break;
                    }
                }
                informationToHide = temp;

                acceptButton.setTouchable(Touchable.disabled);
                declineButton.setTouchable(Touchable.disabled);
                Timeline.createParallel()
                        .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(informationToHide, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(declineButton, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(acceptButton, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .pushPause(0.25f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                if (transitionToPaper[0]) {
                                    fromAndToImages[0].setVisible(false);
                                    fromAndToImages[1].setDrawable(fromAndToImages[0].getDrawable());
                                    fromAndToImages[1].setTouchable(Touchable.enabled);

                                    boolean allBlanksFilled = true;
                                    for (Image blankSpace : blankSpaces) {
                                        if (blankSpace.getDrawable().equals(blankSpaceImage)) {
                                            allBlanksFilled = false;
                                            break;
                                        }
                                    }

                                    if (allBlanksFilled)
                                        finishButton.setVisible(true);
                                }
                                if(informationToHide != null)
                                    informationToHide.setVisible(false);
                                finishButton.setTouchable(Touchable.enabled);
                                paper.setTouchable(Touchable.childrenOnly);
                            }
                        })
                        .start(tweenManager);
            }
        });


        declineButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                final Image informationToHide;
                Image temp = null;
                for (Image information : informations) {
                    if (information.isVisible()) {
                        temp = information;
                        break;
                    }
                }
                informationToHide = temp;

                acceptButton.setTouchable(Touchable.disabled);
                declineButton.setTouchable(Touchable.disabled);
                Timeline.createParallel()
                        .push(Tween.to(mask, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(informationToHide, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(declineButton, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .push(Tween.to(acceptButton, ActorAccessor.ALPHA, 0.5f).target(0f))
                        .pushPause(0.25f)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                if (!transitionToPaper[0]) {
                                    fromAndToImages[0].setDrawable(blankSpaceImage);
                                    fromAndToImages[0].setTouchable(Touchable.disabled);
                                    fromAndToImages[1].setVisible(true);

                                    if (finishButton.isVisible())
                                        finishButton.setVisible(false);

                                }
                                if(informationToHide != null)
                                    informationToHide.setVisible(false);
                                finishButton.setTouchable(Touchable.enabled);
                                paper.setTouchable(Touchable.childrenOnly);
                            }
                        })
                        .start(tweenManager);
            }
        });


        finishButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                finishButton.removeListener(this);

                final boolean areCorrect [] = {false, false, false, false, false, false};
                //Finding the solutions
                for(int i=0; i<blankSpaces.length; i++){
                    for(int j=0; j<6; j++) { //only the six first images are correct
                        if(blankSpaces[i].getDrawable().equals(images[j].getDrawable())){
                            addScore(15);
                            areCorrect[i] = true;
                            break;
                        }
                    }
                }

                Timeline finalTimeline = Timeline.createSequence();
                Timeline [] timelines = new Timeline[blankSpaces.length];
                for (int i=0; i<timelines.length; i++){
                    final AtomicInteger counter = new AtomicInteger(i);
                    blankSpaces[i].setOrigin(blankSpaceImage.getMinWidth()/2, blankSpaceImage.getMinHeight());
                    timelines[i] = Timeline.createSequence()
                            .push(Tween.to(blankSpaces[counter.get()], ActorAccessor.SCALEXY, 0.5f).target(1.1f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            if(areCorrect[counter.get()])
                                                Assets.successFX.play();
                                            else
                                                Assets.failFX.play();
                                        }
                                    }))
                            .pushPause(1f);

                    finalTimeline.push(timelines[i]);
                }

                finalTimeline.setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                finishLevel(Game1910.ScreenType.LEVEL_SELECT);
                            }
                        }).start(tweenManager);
            }
        });

        mainStage.addActor(acceptButton);
        mainStage.addActor(declineButton);


        reporterThought.setCallback(new ReporterThought.ReporterThoughtCallback() {
            @Override
            public void onCompleted() {
                Timeline.createParallel()
                        .push(Tween.to(paper, ActorAccessor.ROTATE, 3f).target(3600f).ease(Quad.OUT))
                        .push(Tween.to(paper, ActorAccessor.SCALEXY, 3f).target(1f))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                paper.setTouchable(Touchable.childrenOnly);
                            }
                        }).start(tweenManager);
            }
        });
        reporterThought.run(paperHelp, tweenManager);

    }

    private class ConversationNode {
        private Conversation nodeValue;
        private ObjectMap<String, ConversationNode> edges;
        private ObjectMap<String, Integer> edgesScore;

        public ConversationNode(Conversation dialog) {
            this.nodeValue = dialog;
            edges = new ObjectMap<>();
            edgesScore = new ObjectMap<>();
        }

        public Conversation getNodeValue() {
            return nodeValue;
        }

        public com.badlogic.gdx.utils.Array<String> getKeys() {
            return edges.keys().toArray();
        }

        public ConversationNode getEdge(String key) {
            return edges.get(key);
        }

        public int getScore(String key) {
            return edgesScore.get(key);
        }

        public void addEdge(String keyName, ConversationNode newEdge, int edgeScore) {
            edges.put(keyName, newEdge);
            edgesScore.put(keyName, edgeScore);
        }
    }

}
