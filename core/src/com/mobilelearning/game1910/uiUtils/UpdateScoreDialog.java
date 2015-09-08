package com.mobilelearning.game1910.uiUtils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.SavedData;
import com.mobilelearning.game1910.screens.StandardScreen;
import com.mobilelearning.game1910.serviceHandling.handlers.GetLeaderboardScoresHandler;
import com.mobilelearning.game1910.serviceHandling.handlers.UpdateScoreHandler;
import com.mobilelearning.game1910.serviceHandling.json.LeaderboardScoresArray;
import com.mobilelearning.game1910.serviceHandling.services.GetLeaderboardScoresService;
import com.mobilelearning.game1910.serviceHandling.services.UpdateScoreService;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public class UpdateScoreDialog implements UpdateScoreHandler, GetLeaderboardScoresHandler {
    private StandardScreen screen;
    private Stage stage;
    private ButtonType[] buttonsOnClassError;
    private ButtonType[] buttonsOnUserError;
    private ButtonType[] buttonsOnOtherErrors;
    private UpdateScoreDialogCallback callback;
    private int levelValue;

    public UpdateScoreDialog(StandardScreen screen, Stage stage, ButtonType [] buttonsOnClassError,
                             ButtonType[] buttonsOnUserError, ButtonType[] buttonsOnOtherErrors,
                             UpdateScoreDialogCallback callback) {
        this.screen = screen;
        this.stage = stage;
        this.buttonsOnClassError = buttonsOnClassError;
        this.buttonsOnUserError = buttonsOnUserError;
        this.buttonsOnOtherErrors = buttonsOnOtherErrors;
        this.callback = callback;
    }

    public void updateScore(){
        levelValue = -1;

        screen.startLoadingAnimation(false);
        UpdateScoreService service = new UpdateScoreService(this);
        service.requestUpdateScore(
                Long.parseLong(SavedData.getCurrentClassID()),
                SavedData.getLevel1Highscore(),
                SavedData.getLevel2Highscore(),
                SavedData.getLevel3Highscore(),
                SavedData.getLevel4Highscore()
        );
    }

    public void getLeaderboardScores(int levelValue){
        this.levelValue = levelValue;

        screen.startLoadingAnimation(false);
        GetLeaderboardScoresService service = new GetLeaderboardScoresService(this);
        service.requestLeaderboardScores(
                Long.parseLong(SavedData.getCurrentClassID()),
                SavedData.getLevel1Highscore(),
                SavedData.getLevel2Highscore(),
                SavedData.getLevel3Highscore(),
                SavedData.getLevel4Highscore(),
                levelValue
        );
    }

    private void showDialog(String errorMessage, ButtonType[] buttons){

        Dialog out = new Dialog("", new Window.WindowStyle(
                screen.uiSkin.getFont("default-font"),
                Assets.darkBrown,
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_panel")))){
            @Override
            protected void result(Object object) {
                if (((String) object).contains("continue")) {
                    callback.onContinue();
                }
                else if (((String) object).contains("retry")) {
                    if(callback instanceof GetLeaderboardScoresDialogCallback)
                        getLeaderboardScores(levelValue);
                    else
                        updateScore();
                }
                else { //if goBack
                    callback.onGoBack();
                }
            }
        };

        out.getContentTable().padTop(40f).defaults().padBottom(20f);
        out.getButtonTable().padBottom(51f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(screen.uiSkin.getFont("default-font"), Assets.darkBrown);

        Label title = new Label("Erro a guardar a pontuação:", labelStyle);
        title.setAlignment(Align.center);
        title.setFontScale(0.875f);
        out.text(title).getContentTable().row();

        Label message = new Label(errorMessage, labelStyle);
        message.setAlignment(Align.center);
        message.setFontScale(0.7f);
        out.text(message).getContentTable().row();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_up")),
                new TextureRegionDrawable(Assets.miscellaneous.findRegion("dialog_button_down")),
                null, screen.uiSkin.getFont("default-font")
        );
        buttonStyle.fontColor = Assets.darkBrown;

        for(ButtonType buttonType : buttons){
            switch (buttonType){
                case CONTINUE:
                    out.button("Continuar", "continue", buttonStyle);
                    break;
                case GO_BACK:
                    out.button("Cancelar", "goBack", buttonStyle);
                    break;
                case RETRY:
                    out.button("Tentar\nNovamente", "retry", buttonStyle);
                    break;
            }
        }

        for(Actor actor : out.getButtonTable().getChildren()){
            TextButton button = (TextButton)actor;
            button.getLabel().setFontScale(0.75f);
        }

        out.show(stage);
    }

    private String formatMessage(String originalMessage){
        String newMessage = "";

        while (true) {
            int currentCharacter = 35;

            if (originalMessage.length() <= currentCharacter) {
                newMessage = newMessage + originalMessage;
                break;
            }

            while (originalMessage.charAt(currentCharacter - 1) != ' ' && currentCharacter > 0)
                currentCharacter--;

            if (currentCharacter == 0)
                currentCharacter = 35;

            newMessage = newMessage + originalMessage.substring(0, currentCharacter) + "\n";
            originalMessage = originalMessage.substring(currentCharacter);
        }


        return newMessage;
    }

    @Override
    public void onUpdateScoreSuccess() {
        screen.stopLoadingAnimation();
        callback.onContinue();
    }

    @Override
    public void onUpdateScoreClassError() {
        screen.stopLoadingAnimation();
        showDialog(formatMessage("A turma selecionada já não existe"), buttonsOnClassError);
    }

    @Override
    public void onUpdateScoreUserError() {
        screen.stopLoadingAnimation();
        showDialog(formatMessage("Já não pertences à turma selecionada"), buttonsOnUserError);
    }

    @Override
    public void onUpdateScoreOtherError(String error) {
        screen.stopLoadingAnimation();
        showDialog(formatMessage(error), buttonsOnOtherErrors);
    }

    @Override
    public void onGetLeaderboardScoresSuccess(LeaderboardScoresArray response) {
        screen.stopLoadingAnimation();
        GetLeaderboardScoresDialogCallback castedCallback = (GetLeaderboardScoresDialogCallback)callback;
        castedCallback.onSuccess(response);
    }

    @Override
    public void onGetLeaderboardScoresClassError() {
        onUpdateScoreClassError();
    }

    @Override
    public void onGetLeaderboardScoresUserError() {
        onUpdateScoreUserError();
    }

    @Override
    public void onGetLeaderboardScoresError(String error) {
        onUpdateScoreOtherError(error);
    }

    public enum ButtonType {
        CONTINUE, GO_BACK, RETRY
    }

    public interface UpdateScoreDialogCallback {
        void onContinue();
        void onGoBack();
    }

    public interface GetLeaderboardScoresDialogCallback extends  UpdateScoreDialogCallback{
        void onSuccess(LeaderboardScoresArray response);
    }
}
