package com.mobilelearning.game1910.uiUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.SavedData;
import com.mobilelearning.game1910.accessors.ActorAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by AFFonseca on 04/05/2015.
 */
public class Notebook extends Table{
    private ObjectMap<Element, Actor> elements;
    private Array<Actor> level1Elements;
    private Array<Actor> level2Elements;
    private Array<Actor> level3Elements;
    private Array<Actor> level4Elements;
    private Table paneTable, level1Table, level2Table, level3Table, level4Table;
    private boolean isClosing, isOpening = false;

    private static final String [] textFrom1890 = {
            "Lembremos que em 1886, Portugal apresentou o mapa cor-de-rosa" +
                    " onde estavam assinaladas as pretensões portuguesas de unir os territórios entre Angola " +
                    "e Moçambique.",
            "Este projeto não foi aceite pela Inglaterra que enviou ao rei D. Carlos um ultimato exigindo " +
                    "o abandono dessa pretensão. O governo acabou por ceder às exigências inglesas, o que " +
                    "deixou o povo português bastante descontente.",
            "O povo culpa a monarquia de não conseguir responder de outro modo à Inglaterra e uma música " +
                    "foi recentemente escrita por Lopes de Mendonça com o refrão sonoro “contra os bretões, marchar, " +
                    "marchar”), de nome A Portuguesa.",
            "Pelas ruas ouvem-se as lamentações sobre o que encareceu de dia para dia, sobre as falências de bancos " +
                    "e sobre o desemprego.",
            "E agora fala-se muito desta traição inglesa. O Partido Republicano tem intensificado" +
                    " a sua posição contra este sistema de governo e propõe a mudança para um regime republicano, onde " +
                    "através de eleições se vote num Presidente.",
            "Assim se sentiam os manifestantes nos Restauradores que tal como o Partido Republicano ou a " +
                    "Carbonária acreditavam num futuro diferente para a Nação."};

    private static final String [] quizFrom1908 = {
            "O aumento do custo de vida era uma razão do descontentamento social.",
            "Os republicanos defendem que a primeira figura de um país deve ser eleita.",
            "No início do séc. XX, aumentaram-se os impostos de modo a pagar dívidas externas.",
            "Na República, um presidente governa somente até ao fim do mandato para o qual foi eleito " +
                    "(em Portugal, 5 anos).",
            "Os republicanos defendem que o presidente deve ser eleito.",
            "Para os republicanos, os ideais de liberdade, de igualdade e de justiça são essenciais numa República.",
            "Na República, o chefe de Estado é o Presidente da República."

    };

    private static final String candidoDeReisSpeech = "\"É o momento! A monarquia achincálha-nos e nós temos que nos" +
            " decidir. Não posso garantir a vitória, mas afianço-lhes que a Revolução, vencedora ou vencida, " +
            "não será uma vergonha.\" ";

    private static final String [] quizFrom1910 = {
            "As tropas leais ao rei dominavam o navio D. Carlos.",
            "As tropas a favor da República dominaram a Rotunda.",
            "Um dos comandantes da revolução republicana foi Machado dos Santos.",
            "Os civis lutaram do lado dos Republicanos.",
            "A república foi proclamada na câmara municipal de Lisboa.",
            "Impedindo outros regimentos de apoiar os monárquicos, o acesso a Lisboa tinha sido cortado pela Carbonária.",
            "O Palácio das Necessidades era a residência do rei.",
            "A bandeira branca que confundiu as tropas fiéis ao rei induzindo a sua rendição, significava " +
                    "apenas o pedido para evacuar a legação alemã."
    };

    private static final String [] flagText = {
            "VERDE\n" +
                    "\"Cor de esperança e do relâmpago, significa uma mudança representativa na vida do país\".",
            "VERMELHO\n" +
                    "\"Cor combativa e quente, é a cor da conquista e do riso.\n" +
                    "Uma cor cantante, ardente, alegre. Lembra o sangue e incita à vitória\".",
            "AMARELO\n" +
                    "Simboliza as viagens dos navegadores portugueses pelo Mundo, nos séculos XV e XVI.",
            "O ESCUDO\n" +
                    "Um escudo com as armas nacionais, constituído por uma área interior branca (PAZ), com cinco " +
                    "escudetes azuis (lembra a defesa do território), em homenagem à bravura dos que lutaram pela " +
                    "independência. As Quinas, a azul, representam as primeiras batalhas na luta pela independência.\n" +
                    "Os sete castelos amarelos representam os castelos tornados aos mouros por D. Afonso III."
    };

    private static final String [] portugueseAnthem = {
            "Heróis do mar, nobre povo,\n" +
                    "nação valente, imortal,\n" +
                    "levantai hoje de novo\n" +
                    "o esplendor de Portugal!",
            "Entre as brumas da memória,\n" +
                    "ó Pátria, sente-se a voz\n" +
                    "dos teus egrégios avós,\n" +
                    "que há-de guiar-te à vitória!",
            "Às armas, às armas!\n" +
                    "Sobre a terra, sobre o mar,\n" +
                    "às armas, às armas!\n" +
                    "Pela Pátria lutar!\n" +
                    "Contra os canhões, marchar, marchar!"
    };

    private static final String [] firsWomanVotingText = {
            "Nome: Carolina Beatriz  Ângelo",
            "Sexo: Feminino",
            "Data de nascimento: 16 de Abril de 1878",
            "Naturalidade: São Vicente, Guarda",
            "Profissão: Médica",
            "Estado civil: Viúva (de Januário Barreto)",
            "Causas: luta pela emancipação  e pelos direitos da mulher",
            "Feito histórico: Exerceu o direito de voto nas eleições constituintes de 28 de Maio de 1911.",
            "Faleceu: 3 de Outubro de 1911."
    };

    private static final String [] firstRepublicMeasures = {
            "Medidas Laicas",
            "Legalização do divórcio.",
            "Expulsão das ordens religiosas e nacionalização dos seus bens.",
            "Lei da separação da Igreja e do Estado.",
            "Registo civil obrigatório.",

            "Medidas  Sociais",
            "Limitação dos horários de trabalho.",
            "Instituição do descanso semanal.",
            "Seguro social obrigatório.",
            "Autorização e regulamentação da greve.",

            "Medidas  Educativas",
            "Fundação de escolas normais para professores.",
            "Criação de escolas primárias e liceus. ",
            "Instrução obrigatória e gratuita (dos 7 aos 12 anos).",
            "Fundação das Universidades de Lisboa e Porto.",

            "Medidas  Políticas",
            "Participação de Portugal na I Guerra  Mundial.",
            "Constituição de 1911.",
            "Criação da Guarda Nacional Republicana.",
            "Novo Hino e Bandeira Nacional."
    };

    private ScrollPane pane;

    public Notebook() {
        super();

        elements = new ObjectMap<>();

        level1Elements = new Array<>();
        level2Elements = new Array<>();
        level3Elements = new Array<>();
        level4Elements = new Array<>();

        paneTable = new Table();
        level1Table = new Table();
        level2Table = new Table();
        level3Table = new Table();
        level4Table = new Table();

        TextureRegionDrawable notebookImage = new TextureRegionDrawable(Assets.notebook.findRegion("notepad"));
        setBackground(notebookImage);
        setSize(notebookImage.getMinWidth(), notebookImage.getMinHeight());

        padTop(85f);
        padBottom(20f);
        center().top();

        addPrimaryTitleElement("1890", level1Elements, Element.LEVEL_1890, level1Table);
        addSecondaryTitleElement("Palavras:", level1Elements, Element.TITLE_WORDS, level1Table);
        addNormalLabelElement("Desemprego", level1Elements, Element.WORD_DESEMPREGO, level1Table);
        addNormalLabelElement("Aumentou", level1Elements, Element.WORD_AUMENTOU, level1Table);
        addNormalLabelElement("Inglaterra", level1Elements, Element.WORD_INGLATERRA, level1Table);
        addNormalLabelElement("Bancos", level1Elements, Element.WORD_BANCOS, level1Table);
        addNormalLabelElement("D. Carlos", level1Elements, Element.WORD_D_CARLOS, level1Table);
        addNormalLabelElement("Angola", level1Elements, Element.WORD_ANGOLA, level1Table);
        addNormalLabelElement("Ultimato", level1Elements, Element.WORD_ULTIMATO, level1Table);
        addNormalLabelElement("Mapa Cor de Rosa", level1Elements, Element.WORD_COR_DE_ROSA, level1Table);
        addNormalLabelElement("Republicano", level1Elements, Element.WORD_REPUBLICANO, level1Table);
        addNormalLabelElement("Eleições", level1Elements, Element.WORD_ELEICOES, level1Table);
        addNormalLabelElement("Governo", level1Elements, Element.WORD_GOVERNO, level1Table);
        addNormalLabelElement("Bretões", level1Elements, Element.WORD_BRETOES, level1Table);
        addNormalLabelElement("A Portuguesa", level1Elements, Element.WORD_PORTUGUESA, level1Table);
        addNormalLabelElement("Descontente", level1Elements, Element.WORD_DESCONTENTE, level1Table);
        addNormalLabelElement("Monarquia", level1Elements, Element.WORD_MONARQUIA, level1Table);
        addNormalLabelElement("Carbonária", level1Elements, Element.WORD_CARBONARIA, level1Table);
        level1Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Texto - Assuntos do dia", level1Elements, Element.TEXT_FROM_1890_TITLE, level1Table);
        addNormalLabelElement(textFrom1890[0], level1Elements, Element.TEXT_FROM_1890_PART1, level1Table);
        addNormalLabelElement(textFrom1890[1], level1Elements, Element.TEXT_FROM_1890_PART2, level1Table);
        addNormalLabelElement(textFrom1890[2], level1Elements, Element.TEXT_FROM_1890_PART3, level1Table);
        addNormalLabelElement(textFrom1890[3], level1Elements, Element.TEXT_FROM_1890_PART4, level1Table);
        addNormalLabelElement(textFrom1890[4], level1Elements, Element.TEXT_FROM_1890_PART5, level1Table);
        addNormalLabelElement(textFrom1890[5], level1Elements, Element.TEXT_FROM_1890_PART6, level1Table);
        level1Table.getCells().peek().padBottom(60f);

        addPrimaryTitleElement("1908", level2Elements, Element.LEVEL_1908, level2Table);

        addSecondaryTitleElement("Foto do Regicídio:", level2Elements, Element.PHOTO_REGICIDE_TITLE, level2Table);
        level2Table.getCells().peek().padBottom(10f);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("regicide_photo")),
                level2Elements, Element.PHOTO_REGICIDE, level2Table);
        level2Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Pessoas importantes:", level2Elements, Element.PHOTOS_FROM_1908_TITLE, level2Table);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("candido_reis_photo")),
                level2Elements, Element.CANDIDO_REIS_PHOTO_AND_NAME, level2Table);
        level2Table.getCells().peek().padBottom(12f);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("miguel_bombarda_photo")),
                level2Elements, Element.MIGUEL_BOMBARDA_PHOTO_AND_NAME, level2Table);
        level2Table.getCells().peek().padBottom(12f);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("luz_almeida_photo")),
                level2Elements, Element.LUZ_ALMEIDA_PHOTO_AND_NAME, level2Table);
        level2Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Quiz - Verdadeiro ou Falso", level2Elements, Element.QUIZ_FROM_1908_TITLE, level2Table);
        addNormalLabelElement(quizFrom1908[0], level2Elements, Element.QUIZ_FROM_1908_QUESTION1, level2Table);
        addNormalLabelElement(quizFrom1908[1], level2Elements, Element.QUIZ_FROM_1908_QUESTION2, level2Table);
        addNormalLabelElement(quizFrom1908[2], level2Elements, Element.QUIZ_FROM_1908_QUESTION3, level2Table);
        addNormalLabelElement(quizFrom1908[3], level2Elements, Element.QUIZ_FROM_1908_QUESTION4, level2Table);
        addNormalLabelElement(quizFrom1908[4], level2Elements, Element.QUIZ_FROM_1908_QUESTION5, level2Table);
        addNormalLabelElement(quizFrom1908[5], level2Elements, Element.QUIZ_FROM_1908_QUESTION6, level2Table);
        addNormalLabelElement(quizFrom1908[6], level2Elements, Element.QUIZ_FROM_1908_QUESTION7, level2Table);
        level2Table.getCells().peek().padBottom(60f);

        addPrimaryTitleElement("1910", level3Elements, Element.LEVEL_1910, level3Table);
        addSecondaryTitleElement("Discurso de Cândido dos Reis", level3Elements, Element.CANDIDO_REIS_SPEECH_TITLE, level3Table);
        addNormalLabelElement(candidoDeReisSpeech, level3Elements, Element.CANDIDO_REIS_SPEECH, level3Table);
        level3Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Mapa do plano para implantar a República", level3Elements,
                Element.REVOLUTION_PLAN_MAP_TITLE, level3Table);
        level3Table.getCells().peek().padBottom(10f);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("revolution_map1")),
                level3Elements, Element.REVOLUTION_PLAN_MAP, level3Table);
        level3Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Casualidades da Implantação da República", level3Elements,
                Element.REVOLUTION_DEATHS_TITLE, level3Table);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("miguel_bombarda_death")),
                level3Elements, Element.MIGUEL_BOMBARDA_DEATH, level3Table);
        level3Table.getCells().peek().padBottom(10f);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("candido_reis_death")),
                level3Elements, Element.CANDIDO_REIS_DEATH, level3Table);
        level3Table.getCells().peek().padBottom(20f);

        addSecondaryTitleElement("Mapa dos eventos da implantação da República", level3Elements,
                Element.REVOLUTION_EVENTS_MAP_TITLE, level3Table);
        level3Table.getCells().peek().padBottom(-10f);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("revolution_map2")),
                level3Elements, Element.REVOLUTION_EVENTS_MAP, level3Table);
        level3Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Quiz - Escolha múltipla", level3Elements, Element.QUIZ_FROM_1910_TITLE, level3Table);
        addNormalLabelElement(quizFrom1910[0], level3Elements, Element.QUIZ_FROM_1910_QUESTION1, level3Table);
        addNormalLabelElement(quizFrom1910[1], level3Elements, Element.QUIZ_FROM_1910_QUESTION2, level3Table);
        addNormalLabelElement(quizFrom1910[2], level3Elements, Element.QUIZ_FROM_1910_QUESTION3, level3Table);
        addNormalLabelElement(quizFrom1910[3], level3Elements, Element.QUIZ_FROM_1910_QUESTION4, level3Table);
        addNormalLabelElement(quizFrom1910[4], level3Elements, Element.QUIZ_FROM_1910_QUESTION5, level3Table);
        addNormalLabelElement(quizFrom1910[5], level3Elements, Element.QUIZ_FROM_1910_QUESTION6, level3Table);
        addNormalLabelElement(quizFrom1910[6], level3Elements, Element.QUIZ_FROM_1910_QUESTION7, level3Table);
        addNormalLabelElement(quizFrom1910[7], level3Elements, Element.QUIZ_FROM_1910_QUESTION8, level3Table);
        level3Table.getCells().peek().padBottom(60f);

        addPrimaryTitleElement("1911", level4Elements, Element.LEVEL_1911, level4Table);
        addSecondaryTitleElement("Bandeira Nacional", level4Elements, Element.FLAG_TITLE, level4Table);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("flag")),
                level4Elements, Element.FLAG_PICTURE, level4Table);
        level4Table.getCells().peek().padBottom(10f);
        addNormalLabelElement(flagText[0], level4Elements, Element.FLAG_TEXT_PART1, level4Table);
        addNormalLabelElement(flagText[1], level4Elements, Element.FLAG_TEXT_PART2, level4Table);
        addNormalLabelElement(flagText[2], level4Elements, Element.FLAG_TEXT_PART3, level4Table);
        addNormalLabelElement(flagText[3], level4Elements, Element.FLAG_TEXT_PART4, level4Table);
        level4Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Hino Nacional - \"A Portuguesa\"", level4Elements, Element.ANTHEM_TITLE, level4Table);
        addNormalLabelElement(portugueseAnthem[0], level4Elements, Element.ANTHEM_PART1, level4Table);
        addNormalLabelElement(portugueseAnthem[1], level4Elements, Element.ANTHEM_PART2, level4Table);
        addNormalLabelElement(portugueseAnthem[2], level4Elements, Element.ANTHEM_PART3, level4Table);
        level4Table.getCells().peek().padBottom(30f);

        addSecondaryTitleElement("Primeira mulher a votar em Portugal", level4Elements,
                Element.FIRST_WOMAN_VOTING_TITLE, level4Table);
        addImage(new TextureRegionDrawable(Assets.notebook.findRegion("first_woman_voting")),
                level4Elements, Element.FIRST_WOMAN_VOTING_PICTURE, level4Table);
        level4Table.getCells().peek().padBottom(10f);
        addNormalLabelElement(firsWomanVotingText[0], level4Elements, Element.FIRST_WOMAN_VOTING_PART1, level4Table);
        addNormalLabelElement(firsWomanVotingText[1], level4Elements, Element.FIRST_WOMAN_VOTING_PART2, level4Table);
        addNormalLabelElement(firsWomanVotingText[2], level4Elements, Element.FIRST_WOMAN_VOTING_PART3, level4Table);
        addNormalLabelElement(firsWomanVotingText[3], level4Elements, Element.FIRST_WOMAN_VOTING_PART4, level4Table);
        addNormalLabelElement(firsWomanVotingText[4], level4Elements, Element.FIRST_WOMAN_VOTING_PART5, level4Table);
        addNormalLabelElement(firsWomanVotingText[5], level4Elements, Element.FIRST_WOMAN_VOTING_PART6, level4Table);
        addNormalLabelElement(firsWomanVotingText[6], level4Elements, Element.FIRST_WOMAN_VOTING_PART7, level4Table);
        addNormalLabelElement(firsWomanVotingText[7], level4Elements, Element.FIRST_WOMAN_VOTING_PART8, level4Table);
        addNormalLabelElement(firsWomanVotingText[8], level4Elements, Element.FIRST_WOMAN_VOTING_PART9, level4Table);
        level4Table.getCells().peek().padBottom(45f);

        addPrimaryTitleElement("Medidas da\n1ª República", level4Elements, Element.MEASURES_TITLE, level4Table);
        level4Table.getCells().peek().padBottom(10f);

        addSecondaryTitleElement(firstRepublicMeasures[0], level4Elements, Element.MEASURES_PART1, level4Table);
        addNormalLabelElement(firstRepublicMeasures[1], level4Elements, Element.MEASURES_PART2, level4Table);
        addNormalLabelElement(firstRepublicMeasures[2], level4Elements, Element.MEASURES_PART3, level4Table);
        addNormalLabelElement(firstRepublicMeasures[3], level4Elements, Element.MEASURES_PART4, level4Table);
        addNormalLabelElement(firstRepublicMeasures[4], level4Elements, Element.MEASURES_PART5, level4Table);
        level4Table.getCells().peek().padBottom(20f);

        addSecondaryTitleElement(firstRepublicMeasures[5], level4Elements, Element.MEASURES_PART6, level4Table);
        addNormalLabelElement(firstRepublicMeasures[6], level4Elements, Element.MEASURES_PART7, level4Table);
        addNormalLabelElement(firstRepublicMeasures[7], level4Elements, Element.MEASURES_PART8, level4Table);
        addNormalLabelElement(firstRepublicMeasures[8], level4Elements, Element.MEASURES_PART9, level4Table);
        addNormalLabelElement(firstRepublicMeasures[9], level4Elements, Element.MEASURES_PART10, level4Table);
        level4Table.getCells().peek().padBottom(20f);

        addSecondaryTitleElement(firstRepublicMeasures[10], level4Elements, Element.MEASURES_PART11, level4Table);
        addNormalLabelElement(firstRepublicMeasures[11], level4Elements, Element.MEASURES_PART12, level4Table);
        addNormalLabelElement(firstRepublicMeasures[12], level4Elements, Element.MEASURES_PART13, level4Table);
        addNormalLabelElement(firstRepublicMeasures[13], level4Elements, Element.MEASURES_PART14, level4Table);
        addNormalLabelElement(firstRepublicMeasures[14], level4Elements, Element.MEASURES_PART15, level4Table);
        level4Table.getCells().peek().padBottom(20f);

        addSecondaryTitleElement(firstRepublicMeasures[15], level4Elements, Element.MEASURES_PART16, level4Table);
        addNormalLabelElement(firstRepublicMeasures[16], level4Elements, Element.MEASURES_PART17, level4Table);
        addNormalLabelElement(firstRepublicMeasures[17], level4Elements, Element.MEASURES_PART18, level4Table);
        addNormalLabelElement(firstRepublicMeasures[18], level4Elements, Element.MEASURES_PART19, level4Table);
        addNormalLabelElement(firstRepublicMeasures[19], level4Elements, Element.MEASURES_PART20, level4Table);

        //pane settings
        pane = new ScrollPane(paneTable);
        pane.setOverscroll(false, false);
        pane.setForceScroll(false, true);
        add(pane).row();


    }

    /*

    private Label addPrimaryTitleElement(String text, Array<Actor> levelTable, Element element) {
        return addPrimaryTitleElement(text, levelTable, element, elementsToTable.get(levelTable));
    }

    private Label addSecondaryTitleElement(String text, Array<Actor> levelTable, Element element) {
        return addSecondaryTitleElement(text, levelTable, element, elementsToTable.get(levelTable));
    }

    private Label addNormalLabelElement(String text, Array<Actor> levelTable, Element element) {
        return addNormalLabelElement(text, levelTable, element, elementsToTable.get(levelTable));
    }

    private Container<?> addImage(TextureRegionDrawable region, Array<Actor> levelTable, Element element){
        return addImage(region, levelTable, element, elementsToTable.get(levelTable));
    }*/

    private Label addPrimaryTitleElement(String text, Array<Actor> levelTable, Element element, Table targetTable){
        Label tempLabel = new Label(text, Assets.uiSkin, "write-font", Color.BLACK);
        if(levelTable != null) levelTable.add(tempLabel);
        if(element != null) elements.put(element, tempLabel);
        tempLabel.setFontScale(1.5f);
        targetTable.add(tempLabel).center().row();
        return tempLabel;
    }

    private Label addSecondaryTitleElement(String text, Array<Actor> levelTable, Element element, Table targetTable){
        Label tempLabel = new Label(text, Assets.uiSkin, "write-font", Color.BLACK);
        if(levelTable != null) levelTable.add(tempLabel);
        if(element != null) elements.put(element, tempLabel);
        tempLabel.setWrap(true);
        targetTable.add(tempLabel).left().width(getWidth() - 80f).padLeft(40f).row();
        return tempLabel;
    }

    private Label addNormalLabelElement(String text, Array<Actor> levelTable, Element element, Table targetTable){
        Label tempLabel = new Label("   " +text, Assets.uiSkin, "speech-font", Color.BLACK);
        if(levelTable != null) levelTable.add(tempLabel);
        if(element != null) elements.put(element, tempLabel);
        tempLabel.setWrap(true);
        targetTable.add(tempLabel).left().padLeft(40f).width(getWidth() - 80f).row();
        return tempLabel;
    }

    private Container<?> addImage(TextureRegionDrawable region, Array<Actor> levelTable, Element element, Table targetTable){
        Container<Image> container = new Container<>();
        float scale = (getWidth()*0.9f)/region.getMinWidth();
        container.setBackground(region);
        if(levelTable != null) levelTable.add(container);
        if(element != null) elements.put(element, container);
        targetTable.add(container).center().width(getWidth() * 0.9f).height(scale * region.getMinHeight()).row();
        return container;
    }

    public Tween showNotebook(Stage targetStage, TweenManager tweenManager){
        if(!isClosing && getStage() != null)
            return null;

        if(!isClosing) {
            setPosition(
                    (targetStage.getWidth() - getWidth()) / 2 - targetStage.getWidth(),
                    (targetStage.getHeight() - getHeight()) / 2
            );
            targetStage.addActor(this);
            //Updating the layout according to the new Stage
            this.layout(); pane.layout();

        }
        else {
            isClosing = false;
            tweenManager.killTarget(this);
        }

        pane.setScrollY(pane.getMaxY()); //show until last element added

        isOpening = true;
        setTouchable(Touchable.disabled);
        return Tween.to(this, ActorAccessor.MOVE_X, 0.5f).target((targetStage.getWidth() - getWidth()) / 2f)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setTouchable(Touchable.childrenOnly);
                        isOpening = false;
                    }
                });
    }

    public Tween hideNotebook(final Stage targetStage, TweenManager tweenManager){
        if(getStage() != targetStage)
            return null;

        if(isOpening){
            isOpening = false;
            tweenManager.killTarget(this);
        }

        isClosing = true;
        setTouchable(Touchable.disabled);
        return Tween.to(this, ActorAccessor.MOVE_X, 0.5f).target((targetStage.getWidth()-getWidth())/2f - targetStage.getWidth())
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setTouchable(Touchable.childrenOnly);
                        isClosing = false;
                        remove();
                        setStage(null);
                    }
                });
    }

    public boolean isShowing(Stage stage){
        return getStage() == stage;
    }

    public boolean isClosing(){
        return isClosing;
    }

    public boolean isOpening(){
        return isOpening;
    }

    public void updateNotebook(boolean alphaCurrentLevel){
        updateNotebook(SavedData.getCurrentLevel(), alphaCurrentLevel);
    }

    public void updateNotebook(int currentLevel, boolean alphaCurrentLevel){

        Table extraTableToAdd = null;
        if(alphaCurrentLevel) {
            if (currentLevel == Assets.levelValues[0]) {
                for (Actor element : level1Elements)
                    element.getColor().a = 0f;
                extraTableToAdd = level1Table;
            }
            else if (currentLevel == Assets.levelValues[1]) {
                for(Actor element :level2Elements)
                    element.getColor().a = 0f;
                extraTableToAdd = level2Table;
            }
            else if (currentLevel == Assets.levelValues[2]){
                for(Actor element :level3Elements)
                    element.getColor().a = 0f;
                extraTableToAdd = level3Table;
            }
            else if (currentLevel == Assets.levelValues[3]){
                for(Actor element :level4Elements)
                    element.getColor().a = 0f;
                extraTableToAdd = level4Table;
            }
        }

        //Only showing the right tables
        paneTable.clear();
        if(currentLevel > Assets.levelValues[0]){
            for (Actor element : level1Elements)
                element.getColor().a = 1f;
            paneTable.add(level1Table).row();
        }
        if(currentLevel > Assets.levelValues[1]){
            for (Actor element : level2Elements)
                element.getColor().a = 1f;
            paneTable.add(level2Table).row();
        }
        if(currentLevel > Assets.levelValues[2]){
            for (Actor element : level3Elements)
                element.getColor().a = 1f;
            paneTable.add(level3Table).row();
        }
        if(currentLevel > Assets.levelValues[3]){
            for (Actor element : level4Elements)
                element.getColor().a = 1f;
            paneTable.add(level4Table).row();
        }

        if(extraTableToAdd != null){
            paneTable.add(extraTableToAdd).row();
        }
    }

    public void addElementNoAnimation(Element... elementValues){
        for(Element element:elementValues)
            elements.get(element).getColor().a = 1f;
    }

    public Timeline addElement(final Stage targetStage, Element... elementValues){
        targetStage.getRoot().setTouchable(Touchable.disabled);
        setPosition(
                (targetStage.getWidth() - getWidth()) / 2 - targetStage.getWidth(),
                (targetStage.getHeight() - getHeight()) / 2
        );
        targetStage.addActor(this);
        //Updating the layout according to the new Stage
        this.layout(); pane.layout();

        final Actor[] targets = new Actor[elementValues.length];

        float middlePoint = 0f;
        for(int i=0; i<targets.length; i++) {
            targets[i] = elements.get(elementValues[i]);
            targets[i].getColor().a = 0f;
            middlePoint += targets[i].getY() + targets[i].getHeight() / 2;
        }
        middlePoint /= targets.length;
        //Adding the table height
        middlePoint += elements.get(elementValues[0]).getParent().getY();

        //Scrolling in a way for the middle point to be in the middle of the scrolling area
        pane.setScrollY(pane.getMaxY() + pane.getScrollHeight() / 2 - middlePoint);

        Timeline out = Timeline.createSequence()
                .push(Tween.to(this, ActorAccessor.MOVE_X, 0.5f).target((targetStage.getWidth() - getWidth()) / 2f))
                .beginParallel();

        for(Actor target:targets){
            out.push(Tween.to(target, ActorAccessor.ALPHA, 1f).target(1f));
        }

        out.end().push(Tween.to(this, ActorAccessor.MOVE_X, 0.5f)
                        .target((targetStage.getWidth() - getWidth()) / 2f - targetStage.getWidth())
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                targetStage.getRoot().setTouchable(Touchable.enabled);
                                remove();
                                setStage(null);
                            }
                        }));

        return out;

    }

    public enum Element{
        LEVEL_1890, TITLE_WORDS, WORD_DESEMPREGO, WORD_AUMENTOU, WORD_INGLATERRA, WORD_BANCOS,
        WORD_ANGOLA, WORD_ULTIMATO,  WORD_REPUBLICANO, WORD_ELEICOES, WORD_BRETOES, WORD_COR_DE_ROSA,
        WORD_D_CARLOS, WORD_MONARQUIA, WORD_DESCONTENTE, WORD_PORTUGUESA, WORD_GOVERNO, WORD_CARBONARIA,
        TEXT_FROM_1890_TITLE, TEXT_FROM_1890_PART1, TEXT_FROM_1890_PART2, TEXT_FROM_1890_PART3,
        TEXT_FROM_1890_PART4, TEXT_FROM_1890_PART5, TEXT_FROM_1890_PART6,

        LEVEL_1908, PHOTO_REGICIDE, PHOTO_REGICIDE_TITLE, PHOTOS_FROM_1908_TITLE, CANDIDO_REIS_PHOTO_AND_NAME,
        MIGUEL_BOMBARDA_PHOTO_AND_NAME, LUZ_ALMEIDA_PHOTO_AND_NAME, QUIZ_FROM_1908_TITLE, QUIZ_FROM_1908_QUESTION1,
        QUIZ_FROM_1908_QUESTION2, QUIZ_FROM_1908_QUESTION3, QUIZ_FROM_1908_QUESTION4, QUIZ_FROM_1908_QUESTION5,
        QUIZ_FROM_1908_QUESTION6, QUIZ_FROM_1908_QUESTION7,

        LEVEL_1910, CANDIDO_REIS_SPEECH_TITLE, CANDIDO_REIS_SPEECH, REVOLUTION_PLAN_MAP_TITLE, REVOLUTION_PLAN_MAP,
        REVOLUTION_DEATHS_TITLE, MIGUEL_BOMBARDA_DEATH, CANDIDO_REIS_DEATH, REVOLUTION_EVENTS_MAP_TITLE,
        REVOLUTION_EVENTS_MAP, QUIZ_FROM_1910_TITLE, QUIZ_FROM_1910_QUESTION1, QUIZ_FROM_1910_QUESTION2,
        QUIZ_FROM_1910_QUESTION3, QUIZ_FROM_1910_QUESTION4, QUIZ_FROM_1910_QUESTION5, QUIZ_FROM_1910_QUESTION6,
        QUIZ_FROM_1910_QUESTION7, QUIZ_FROM_1910_QUESTION8,

        LEVEL_1911, FLAG_TITLE, FLAG_PICTURE, FLAG_TEXT_PART1, FLAG_TEXT_PART2, FLAG_TEXT_PART3, FLAG_TEXT_PART4,
        ANTHEM_TITLE, ANTHEM_PART1, ANTHEM_PART2, ANTHEM_PART3, FIRST_WOMAN_VOTING_TITLE, FIRST_WOMAN_VOTING_PICTURE,
        FIRST_WOMAN_VOTING_PART1, FIRST_WOMAN_VOTING_PART2, FIRST_WOMAN_VOTING_PART3, FIRST_WOMAN_VOTING_PART4,
        FIRST_WOMAN_VOTING_PART5, FIRST_WOMAN_VOTING_PART6, FIRST_WOMAN_VOTING_PART7, FIRST_WOMAN_VOTING_PART8,
        FIRST_WOMAN_VOTING_PART9, MEASURES_TITLE, MEASURES_PART1, MEASURES_PART2, MEASURES_PART3, MEASURES_PART4,
        MEASURES_PART5, MEASURES_PART6, MEASURES_PART7, MEASURES_PART8, MEASURES_PART9, MEASURES_PART10,
        MEASURES_PART11, MEASURES_PART12, MEASURES_PART13, MEASURES_PART14, MEASURES_PART15, MEASURES_PART16,
        MEASURES_PART17, MEASURES_PART18, MEASURES_PART19, MEASURES_PART20
    }
}
