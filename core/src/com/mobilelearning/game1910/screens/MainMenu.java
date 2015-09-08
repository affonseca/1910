package com.mobilelearning.game1910.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.Game1910;
import com.mobilelearning.game1910.SavedData;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.serviceHandling.handlers.GetScoreHandler;
import com.mobilelearning.game1910.serviceHandling.handlers.JoinClassHandler;
import com.mobilelearning.game1910.serviceHandling.handlers.LoginHandler;
import com.mobilelearning.game1910.serviceHandling.handlers.OtherClassesHandler;
import com.mobilelearning.game1910.serviceHandling.handlers.RegisterHandler;
import com.mobilelearning.game1910.serviceHandling.handlers.UserClassesHandler;
import com.mobilelearning.game1910.serviceHandling.json.ClassArray;
import com.mobilelearning.game1910.serviceHandling.json.ScoreData;
import com.mobilelearning.game1910.serviceHandling.json.UserData;
import com.mobilelearning.game1910.serviceHandling.json.Class;
import com.mobilelearning.game1910.serviceHandling.services.GetScoreService;
import com.mobilelearning.game1910.serviceHandling.services.JoinClassService;
import com.mobilelearning.game1910.serviceHandling.services.LoginService;
import com.mobilelearning.game1910.serviceHandling.services.OtherClassesService;
import com.mobilelearning.game1910.serviceHandling.services.RegisterService;
import com.mobilelearning.game1910.serviceHandling.services.UserClassesService;
import com.mobilelearning.game1910.uiUtils.UpdateScoreDialog;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 12-01-2015
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
*/
public class MainMenu extends StandardScreen implements LoginHandler, RegisterHandler, OtherClassesHandler,
        JoinClassHandler, UserClassesHandler, GetScoreHandler {
    private TextureAtlas mainMenuAtlas;
    private TextButton.TextButtonStyle textButtonStyle, checkedButtonStyle;
    private Button.ButtonStyle continueButtonStyle;
    private Button.ButtonStyle backButtonStyle;
    private TextField.TextFieldStyle textFieldStyle;

    private Table loginTable;
    private TextField usernameText;
    private TextField passwordText;

    private Table userClassesTable;
    private ClassArray userClasses;
    private TextButton userClassesJoinButton;
    private List<String> userClassesList;

    private Table otherClassesTable;
    private int joinedClassIndex;
    private ClassArray classesToJoin;
    private List<String> otherClassesList;

    private Table mainMenuTable;
    private Table optionsTable;
    private Table textSpeedTable;

    private Dialog messageDialog;
    private Label messageLabel;

    private Table lastTable;
    private Table currentTable;

    public MainMenu(Game1910 game, PolygonSpriteBatch batch){
        super(game, batch);
    }

    @Override
    public void show() {
        super.show();

        backgroundStage.addActor(new Image(mainMenuAtlas.findRegion("background")));
        textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(mainMenuAtlas.findRegion("text_button_up")),
                new TextureRegionDrawable(mainMenuAtlas.findRegion("text_button_down")),
                null,
                uiSkin.getFont("default-font")
        );
        textButtonStyle.fontColor = textButtonStyle.downFontColor = Assets.darkBrown; //dark brown

        checkedButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(mainMenuAtlas.findRegion("text_button_up")),
                new TextureRegionDrawable(mainMenuAtlas.findRegion("text_button_down")),
                new TextureRegionDrawable(mainMenuAtlas.findRegion("text_button_down")),
                uiSkin.getFont("default-font")
        );
        checkedButtonStyle.fontColor = textButtonStyle.downFontColor = Assets.darkBrown; //dark brown

        textFieldStyle = new TextField.TextFieldStyle(uiSkin.get(TextField.TextFieldStyle.class));
        textFieldStyle.font = uiSkin.getFont("write-font");
        textFieldStyle.messageFont = uiSkin.getFont("default-font");
        textFieldStyle.messageFontColor = textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = new TextureRegionDrawable(mainMenuAtlas.findRegion("text_field"));

        continueButtonStyle = new Button.ButtonStyle(
                new TextureRegionDrawable(mainMenuAtlas.findRegion("continue_button_up")),
                new TextureRegionDrawable(mainMenuAtlas.findRegion("continue_button_down")),
                null
        );

        backButtonStyle = new Button.ButtonStyle(
                new TextureRegionDrawable(mainMenuAtlas.findRegion("back_button_up")),
                new TextureRegionDrawable(mainMenuAtlas.findRegion("back_button_down")),
                null
        );

        createLoginTable();
        createUserClassesTable();
        createOtherClassesTable();
        createMainMenuTable();
        createOptionsTable();
        createTextSpeedTable();
        createDialogWindow();

        //Show no table!
        loginTable.setVisible(false);
        otherClassesTable.setVisible(false);
        userClassesTable.setVisible(false);
        mainMenuTable.setVisible(false);
        optionsTable.setVisible(false);
        textSpeedTable.setVisible(false);

        if(SavedData.getCurrentClassID() == null){
            //login required
            loginTable.setVisible(true);
            currentTable = loginTable;

            if(SavedData.getUserID() != null){
                //login not required. Going to step 2
                startLoadingAnimation(false);

                UserClassesService getClasses = new UserClassesService(MainMenu.this);
                getClasses.requestUserClasses();
            }
        }
        //Class not required. Going to main menu!
        else{
            mainMenuTable.setVisible(true);
            currentTable = mainMenuTable;
        }


    }

    @Override
    public void load() {
    }

    @Override
    public void prepare() {
        super.prepare();
        mainMenuAtlas = Assets.prepareMainMenu();
        Assets.prepareGlobalAssets();
        game.createNotebook();
        uiSkin = Assets.uiSkin;

    }

    @Override
    public void unload() {
    }

    private void createLoginTable(){

        //Creating the login Table and it's components
        loginTable = new Table();
        loginTable.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());
        loginTable.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("table")));
        loginTable.center().top().defaults().padBottom(15f);

        TextButton loginButton = new TextButton("Login", textButtonStyle);
        loginButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                usernameText.getOnscreenKeyboard().show(false);
                startLoadingAnimation(false);

                LoginService login = new LoginService(MainMenu.this);
                login.requestLogin(usernameText.getText(), passwordText.getText());

            }
        }) ;

        TextButton registerButton = new TextButton("Registar", textButtonStyle);
        registerButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                usernameText.getOnscreenKeyboard().show(false);
                startLoadingAnimation(false);

                RegisterService register = new RegisterService(MainMenu.this);
                register.requestRegistration(usernameText.getText(), passwordText.getText());

            }
        }) ;

        usernameText = new TextField("", textFieldStyle);
        usernameText.setMessageText("        username");
        usernameText.setSize(
                usernameText.getStyle().background.getMinWidth(),
                usernameText.getStyle().background.getMinHeight());

        passwordText = new TextField("", textFieldStyle);
        passwordText.setMessageText("        password");
        passwordText.setPasswordCharacter('*');
        passwordText.setPasswordMode(true);
        passwordText.setSize(
                passwordText.getStyle().background.getMinWidth(),
                passwordText.getStyle().background.getMinHeight());

        loginTable.add(usernameText).padTop(270f).width(usernameText.getStyle().background.getMinWidth()); loginTable.row();
        loginTable.add(passwordText).width(passwordText.getStyle().background.getMinWidth()); loginTable.row();
        loginTable.add(registerButton); loginTable.row();
        loginTable.add(loginButton); loginTable.row();
        //loginTable.debug();

        mainStage.addActor(loginTable);

    }

    private void createUserClassesTable(){
        //Now creating the User Classes Table
        userClassesTable = new Table();
        userClassesTable.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());
        userClassesTable.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("table")));
        userClassesTable.center().top().defaults().padBottom(15f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), textButtonStyle.fontColor);
        Label title = new Label("Escolhe uma turma", labelStyle);

        userClassesList = new List<>(uiSkin);
        userClassesList.getStyle().font = uiSkin.getFont("write-font");
        ScrollPane classesPane = new ScrollPane(userClassesList,
                new ScrollPane.ScrollPaneStyle(textFieldStyle.background, null, null, null, null));
        classesPane.setScrollBarPositions(false, true);

        Button backButton2 = new Button(backButtonStyle);
        backButton2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (lastTable == loginTable || lastTable == otherClassesTable) {
                    changeTable(loginTable, false);

                    SavedData.clearUserData();
                    usernameText.setText("");
                    passwordText.setText("");

                } else
                    changeTable(optionsTable, false);

            }
        }) ;

        Button continueButton2 = new Button(continueButtonStyle);
        continueButton2.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                if(userClasses.classes.size == 0)
                    return;

                startLoadingAnimation(false);

                Class chosenClass = userClasses.classes.get(userClassesList.getSelectedIndex());
                GetScoreService getScoreService = new GetScoreService(MainMenu.this);
                getScoreService.requestScore(chosenClass.classID);

            }
        }) ;


        userClassesJoinButton = new TextButton("Entrar numa turma", textButtonStyle);
        userClassesJoinButton.getLabel().setFontScale(0.9f);
        userClassesJoinButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                startLoadingAnimation(false);

                OtherClassesService getClasses = new OtherClassesService(MainMenu.this);
                getClasses.requestOtherClasses();

            }
        }) ;


        userClassesTable.add(title).padTop(245f); userClassesTable.row();

        userClassesTable.add(classesPane).height(243f).width(textFieldStyle.background.getMinWidth());
        userClassesTable.row();

        userClassesTable.add(userClassesJoinButton); userClassesTable.row();
        userClassesTable.add(continueButton2); userClassesTable.row();

        backButton2.setPosition(
                mainStage.getWidth() - backButton2.getPrefWidth() - 25f,
                mainStage.getHeight() - backButton2.getPrefHeight() - 25f);
        userClassesTable.addActor(backButton2);

        mainStage.addActor(userClassesTable);

    }

    private void createOtherClassesTable(){
        //Now creating the other Classes Table

        otherClassesTable = new Table();
        otherClassesTable.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());
        otherClassesTable.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("table")));
        otherClassesTable.center().top().defaults().padBottom(15f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), textButtonStyle.fontColor);
        Label title = new Label("Escolhe uma turma", labelStyle);


        otherClassesList = new List<>(uiSkin);
        otherClassesList.getStyle().font = uiSkin.getFont("write-font");
        ScrollPane classesPane = new ScrollPane(otherClassesList,
                new ScrollPane.ScrollPaneStyle(textFieldStyle.background, null, null, null, null));
        classesPane.setScrollBarPositions(false, true);

        Button joinButton = new Button(continueButtonStyle);
        joinButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                startLoadingAnimation(false);

                joinedClassIndex = otherClassesList.getSelectedIndex();
                long classToJoinID = classesToJoin.classes.get(joinedClassIndex).classID;

                JoinClassService join = new JoinClassService(MainMenu.this);
                join.requestJoinClass("" + classToJoinID);

            }
        }) ;

        Button backButton = new Button(backButtonStyle);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (lastTable == userClassesTable) {

                    startLoadingAnimation(false);

                    UserClassesService myClassesService = new UserClassesService(MainMenu.this);
                    myClassesService.requestUserClasses();

                } else {
                    changeTable(optionsTable, false);
                }

            }
        });

        otherClassesTable.add(title).padTop(245f); otherClassesTable.row();

        otherClassesTable.add(classesPane).height(243f).width(textFieldStyle.background.getMinWidth());
        otherClassesTable.row();

        otherClassesTable.add(joinButton).padTop(70f); otherClassesTable.row();

        backButton.setPosition(
                mainStage.getWidth() - backButton.getPrefWidth() - 25f,
                mainStage.getHeight() - backButton.getPrefHeight() - 25f);
        otherClassesTable.addActor(backButton);

        mainStage.addActor(otherClassesTable);

    }

    private void createMainMenuTable(){
        mainMenuTable = new Table();
        mainMenuTable.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());
        mainMenuTable.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("table")));
        mainMenuTable.center().top().defaults().padBottom(15f);


        TextButton play = new TextButton("JOGAR", textButtonStyle);
        play.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                game.loadNextScreen(MainMenu.this, Game1910.ScreenType.LEVEL_SELECT);
            }
        }) ;

        TextButton options = new TextButton("OPÇÕES", textButtonStyle);
        options.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                changeTable(optionsTable, true);
            }
        }) ;

        TextButton logout = new TextButton("LOGOUT", textButtonStyle);
        logout.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                UpdateScoreDialog scoreDialog = new UpdateScoreDialog(MainMenu.this, mainStage,
                        new UpdateScoreDialog.ButtonType[]{
                                UpdateScoreDialog.ButtonType.GO_BACK,
                                UpdateScoreDialog.ButtonType.CONTINUE},
                        new UpdateScoreDialog.ButtonType[]{
                                UpdateScoreDialog.ButtonType.GO_BACK,
                                UpdateScoreDialog.ButtonType.CONTINUE},
                        new UpdateScoreDialog.ButtonType[]{
                                UpdateScoreDialog.ButtonType.GO_BACK,
                                UpdateScoreDialog.ButtonType.RETRY,
                                UpdateScoreDialog.ButtonType.CONTINUE},
                        new UpdateScoreDialog.UpdateScoreDialogCallback() {
                            @Override
                            public void onContinue() {
                                changeTable(loginTable, false);

                                SavedData.clearUserData();
                                usernameText.setText("");
                                passwordText.setText("");
                            }

                            @Override
                            public void onGoBack() {
                                //DO absolutely nothing :)
                            }
                        });
                scoreDialog.updateScore();
            }


        }) ;

        Label.LabelStyle versionStyle = new Label.LabelStyle(uiSkin.getFont("speech-font"), Assets.darkBrown);
        Label version = new Label("Versão: " +Assets.currentVersion, versionStyle);

        mainMenuTable.add(play).padTop(330f);  mainMenuTable.row();
        mainMenuTable.add(options).center();  mainMenuTable.row();
        mainMenuTable.add(logout).center();  mainMenuTable.row();
        mainMenuTable.add(version).center().padTop(80f); mainMenuTable.row();

        mainStage.addActor(mainMenuTable);

    }

    private void createOptionsTable(){
        optionsTable = new Table();
        optionsTable.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());
        optionsTable.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("table")));
        optionsTable.center().top().defaults().padBottom(15f);

        TextButton joinClass = new TextButton("Inscrever em Turma", textButtonStyle);
        joinClass.getLabel().setFontScale(0.9f);
        joinClass.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                startLoadingAnimation(false);

                OtherClassesService getClasses = new OtherClassesService(MainMenu.this);
                getClasses.requestOtherClasses();

            }
        }) ;

        TextButton changeClass = new TextButton("Mudar turma", textButtonStyle);
        changeClass.getLabel().setFontScale(0.9f);
        changeClass.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                startLoadingAnimation(false);

                UserClassesService getClasses = new UserClassesService(MainMenu.this);
                getClasses.requestUserClasses();

            }

        }) ;

        TextButton changeTextSpeed = new TextButton("Velocidade do texto", textButtonStyle);
        changeTextSpeed.getLabel().setFontScale(0.9f);
        changeTextSpeed.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                changeTable(textSpeedTable, true);

            }

        }) ;

        Button backButton = new Button(backButtonStyle);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                changeTable(mainMenuTable, false);
            }
        }) ;

        optionsTable.add(joinClass).center().padTop(335f);  optionsTable.row();
        optionsTable.add(changeClass).center();  optionsTable.row();
        optionsTable.add(changeTextSpeed).center();  optionsTable.row();
        backButton.setPosition(
                mainStage.getWidth() - backButton.getPrefWidth() - 25f,
                mainStage.getHeight() - backButton.getPrefHeight() - 25f);
        optionsTable.addActor(backButton);

        mainStage.addActor(optionsTable);
    }

    private void createTextSpeedTable(){
        textSpeedTable = new Table();
        textSpeedTable.setBounds(0, 0, mainStage.getWidth(), mainStage.getHeight());
        textSpeedTable.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("table")));
        textSpeedTable.center().top().defaults().padBottom(15f);

        final TextButton lowSpeed = new TextButton("lento", checkedButtonStyle);
        final TextButton mediumSpeed = new TextButton("médio", checkedButtonStyle);
        final TextButton highSpeed = new TextButton("rápido", checkedButtonStyle);
        final TextButton veryHighSpeed = new TextButton("muito rápido", checkedButtonStyle);

        lowSpeed.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                SavedData.setTextSpeed(15f);
                lowSpeed.setChecked(true);
                mediumSpeed.setChecked(false);
                highSpeed.setChecked(false);
                veryHighSpeed.setChecked(false);
            }
        }) ;

        mediumSpeed.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                SavedData.setTextSpeed(30f);
                lowSpeed.setChecked(false);
                mediumSpeed.setChecked(true);
                highSpeed.setChecked(false);
                veryHighSpeed.setChecked(false);
            }

        }) ;

        highSpeed.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                SavedData.setTextSpeed(45f);
                lowSpeed.setChecked(false);
                mediumSpeed.setChecked(false);
                highSpeed.setChecked(true);
                veryHighSpeed.setChecked(false);
            }
        }) ;

        veryHighSpeed.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                SavedData.setTextSpeed(60f);
                lowSpeed.setChecked(false);
                mediumSpeed.setChecked(false);
                highSpeed.setChecked(false);
                veryHighSpeed.setChecked(true);
            }
        }) ;


        switch ((int)SavedData.getTextSpeed()){
            case 15:
                lowSpeed.setChecked(true);
                break;
            case 30:
                mediumSpeed.setChecked(true);
                break;
            case 45:
                highSpeed.setChecked(true);
                break;
            case 60:
                veryHighSpeed.setChecked(true);
                break;
        }


        Button backButton = new Button(backButtonStyle);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                changeTable(optionsTable, false);
            }


        }) ;

        textSpeedTable.add(lowSpeed).center().padTop(305f);  textSpeedTable.row();
        textSpeedTable.add(mediumSpeed).center();  textSpeedTable.row();
        textSpeedTable.add(highSpeed).center();  textSpeedTable.row();
        textSpeedTable.add(veryHighSpeed).center();  textSpeedTable.row();
        backButton.setPosition(
                mainStage.getWidth() - backButton.getPrefWidth() - 25f,
                mainStage.getHeight() - backButton.getPrefHeight() - 25f);
        textSpeedTable.addActor(backButton);

        mainStage.addActor(textSpeedTable);
    }



    private void createDialogWindow(){
        messageDialog = new Dialog("", new Window.WindowStyle(
                uiSkin.getFont("default-font"),
                Assets.darkBrown,
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_panel"))
        ));
        messageDialog.getContentTable().padTop(40f);
        messageDialog.getButtonTable().padBottom(51f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Assets.darkBrown);

        messageLabel = new Label("", labelStyle);
        messageLabel.setAlignment(Align.center); messageDialog.text(messageLabel);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_up")),
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_down")),
                null, uiSkin.getFont("default-font")
        );
        buttonStyle.fontColor = Assets.darkBrown;
        TextButton okButton = new TextButton("OK", buttonStyle);
        okButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                currentTable.setTouchable(Touchable.enabled);
            }
        }) ;
        messageDialog.button(okButton);

    }

    private void showMessage(String message){
        //Since dialog is buggy and I cannot make it \n if the message is bigger than the mainStage:
        currentTable.setTouchable(Touchable.disabled);

        String newMessage = "";

        while (true){
            int currentCharacter = 20;

            if(message.length() <= currentCharacter){
                newMessage = newMessage + message;
                break;
            }

            while(message.charAt(currentCharacter-1) != ' ' && currentCharacter >0)
                currentCharacter--;

            if(currentCharacter == 0)
                currentCharacter = 35;

            newMessage = newMessage + message.substring(0, currentCharacter) + "\n";
            message = message.substring(currentCharacter);

        }
        messageLabel.setText(newMessage);
        messageDialog.show(mainStage);
    }

    private void changeTable(Table to, boolean goFront){
        to.setTouchable(Touchable.disabled);
        currentTable.setTouchable(Touchable.disabled);

        float newTableStart = mainStage.getWidth(), oldTableEnd = - mainStage.getWidth();

        if(!goFront){
            newTableStart *= -1;
            oldTableEnd *= -1;
        }

        to.setX(newTableStart);
        to.setVisible(true);
        Tween.to(to, ActorAccessor.MOVE_X, 1.0f).target(0.0f).start(tweenManager);
        Tween.to(currentTable, ActorAccessor.MOVE_X, 1.0f).target(oldTableEnd)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        if(type != TweenCallback.COMPLETE)
                            return;
                        lastTable.setVisible(false);
                        currentTable.setTouchable(Touchable.enabled);
                        lastTable.setTouchable(Touchable.enabled);
                    }
                })
                .start(tweenManager);
        lastTable = currentTable;
        currentTable = to;
    }

    @Override
    public void onLoginSuccess(UserData response) {
        //Login was successful. Now it's necessary to load the classes.
        SavedData.setUserData(response);

        UserClassesService getClasses = new UserClassesService(MainMenu.this);
        getClasses.requestUserClasses();

    }

    @Override
    public void onLoginError(String error) {
        stopLoadingAnimation();
        showMessage(error);
    }

    @Override
    public void onRegistrationSuccess(UserData response) {
        onLoginSuccess(response);
    }

    @Override
    public void onRegistrationError(String error) {
        stopLoadingAnimation();
        showMessage(error);
    }

    @Override
    public void onGettingUserClassesSuccess(ClassArray response) {

        stopLoadingAnimation();
        if(currentTable == optionsTable)
            userClassesJoinButton.setVisible(false);
        else
            userClassesJoinButton.setVisible(true);
        if(currentTable == otherClassesTable)
            changeTable(userClassesTable, false);
        else
            changeTable(userClassesTable, true);

        Array<String> classesArray = new Array<>(response.classes.size);
        for(Class it:response.classes){
            classesArray.add(it.className);
        }

        userClasses = response;
        userClassesList.setItems(classesArray);
    }

    @Override
    public void onGettingUserClassesError(String error) {
        stopLoadingAnimation();
        showMessage(error);
    }

    @Override
    public void onGettingOtherClassesSuccess(ClassArray response) {
        stopLoadingAnimation();

        changeTable(otherClassesTable, true);

        Array<String> classesArray = new Array<>(response.classes.size);
        for(Class it:response.classes){
            classesArray.add(it.className);
        }

        classesToJoin = response;
        otherClassesList.setItems(classesArray);
    }

    @Override
    public void onGettingOtherClassesError(String error) {
        stopLoadingAnimation();
        showMessage(error);
    }

    @Override
    public void onJoinClassSuccess() {
        stopLoadingAnimation();
        showMessage("Pedido de entrada adicionado com sucesso!");

        otherClassesList.getItems().removeIndex(joinedClassIndex);
        classesToJoin.classes.removeIndex(joinedClassIndex);
    }

    @Override
    public void onJoinClassError(String error) {
        stopLoadingAnimation();
        showMessage(error);
    }

    @Override
    public void onGetScoreSuccess(ScoreData response) {
        stopLoadingAnimation();
        SavedData.setHighscores(response);

        Class chosenClass = userClasses.classes.get(userClassesList.getSelectedIndex());
        SavedData.setCurrentClass(chosenClass);

        if(response.level4Score > 0){
            SavedData.setCurrentLevel(Assets.levelValues[4]);
        }
        else if(response.level3Score > 0){
            SavedData.setCurrentLevel(Assets.levelValues[3]);
        }
        else if(response.level2Score > 0){
            SavedData.setCurrentLevel(Assets.levelValues[2]);
        }
        else if(response.level1Score > 0){
            SavedData.setCurrentLevel(Assets.levelValues[1]);
        }

        if(lastTable == loginTable || lastTable == otherClassesTable)
            changeTable(mainMenuTable, true);
        else
            changeTable(optionsTable, false);
    }

    @Override
    public void onGetScoreError(String error) {
        stopLoadingAnimation();
        showMessage(error);
    }
}
