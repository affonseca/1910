package com.mobilelearning.game1910.minigames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.mobilelearning.game1910.screens.StandardScreen;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 02-03-2015
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class MissingWords {
    private DragAndDrop dragAndDrop;
    private MissingWordsCallback callback;
    private Array<WordsDragAndDropTarget> targets;
    private Array<WordsDragAndDropSource> sources;

    private String missingWords [];

    private Table imageTable;
    private Table wordsTable;

    private Label.LabelStyle correctWordsStyle;
    private Label.LabelStyle randomWordsStyle;
    private Label.LabelStyle dragStyle;
    private Label.LabelStyle hoverStyle;

    private float correctWordsScale = 1f, randomWordsScale = 1f,
            dragScale = 1.5f, hoverScale = 2f;

    private float imageScale;
    private float fontToScaledImage;


    public MissingWords(Skin uiSkin, Drawable image, float scale){

        dragAndDrop = new DragAndDrop();

        targets = new Array<>();
        sources = new Array<>();

        imageTable = new Table();
        imageTable.setBackground(image);
        imageScale = scale;
        imageTable.setSize(image.getMinWidth() * scale, image.getMinHeight() * scale);
        imageTable.setBackground(image);

        wordsTable = new Table();

        dragStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        hoverStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.LIGHT_GRAY);
    }

    public Table setImageTable(Skin uiSkin, float fontToImage, float spaceWidth, float wordsPositions [][], String words []) {
        return setImageTable(new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK),
                fontToImage, spaceWidth, wordsPositions, words, Align.center);
    }

    public Table setImageTable(Skin uiSkin, float fontToImage, float spaceWidth, float wordsPositions [][], String words [], int align) {
        return setImageTable(new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK),
                fontToImage, spaceWidth, wordsPositions, words, align);
    }

    public Table setImageTable(Label.LabelStyle style, float fontToImage, float spaceWidth, float wordsPositions [][], String words []) {
        return setImageTable(style, fontToImage, spaceWidth, wordsPositions, words, Align.center);
    }


    public Table setImageTable(Label.LabelStyle style, float fontToImage, float spaceWidth, float wordsPositions [][], String words [], int align){
        imageTable.clear();
        missingWords = words;

        while(targets.size != 0){
            dragAndDrop.removeTarget(targets.pop());
        }

        dragAndDrop.clear();

        fontToScaledImage = fontToImage;

        correctWordsScale *= fontToScaledImage*imageScale;
        hoverScale *= fontToScaledImage*imageScale;

        correctWordsStyle = style;
        for(int i=0; i<words.length; i++){
            Label word = new Label("", correctWordsStyle);
            word.setPosition(wordsPositions[i][0] * imageScale, wordsPositions[i][1] * imageScale);
            word.setWidth(spaceWidth * imageScale);
            word.setAlignment(align);
            word.setHeight(word.getStyle().font.getCapHeight() * imageScale);
            word.setFontScale(correctWordsScale);
            targets.add(new WordsDragAndDropTarget(word));
            dragAndDrop.addTarget(targets.peek());
            if(correctWordsStyle.background != null)
                word.setSize(word.getStyle().background.getMinWidth(), word.getStyle().background.getMinHeight());
            imageTable.addActor(word);
        }

        return imageTable;
    }

    public Table setWordsTable(Skin uiSkin, String randomWords[], int wordsPerRow){
        return setWordsTable(new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK),
                randomWords, wordsPerRow, false);
    }

    public Table setWordsTable(Label.LabelStyle style, String randomWords[], int wordsPerRow) {
        return  setWordsTable(style, randomWords, wordsPerRow, false);
    }

    public Table setWordsTable(Label.LabelStyle style, String words[], int wordsPerRow, boolean lastOnRight){
        wordsTable.clear();

        String randomWords[] = words.clone();
        for (int i=0; i<randomWords.length; i++){
            int randomIndex = MathUtils.random(i, randomWords.length-1);
            String aux = randomWords[randomIndex];
            randomWords[randomIndex] = randomWords[i];
            randomWords[i] = aux;
        }

        while(sources.size != 0){
            dragAndDrop.removeSource(sources.pop());
        }

        randomWordsStyle = style;
        for (int i = 0; i<randomWords.length; i++){
            if(lastOnRight && i == randomWords.length-1 && randomWords.length % 2 == 1)
                wordsTable.add();

            Label wordLabel = new Label(randomWords[i], randomWordsStyle);
            wordLabel.setFontScale(randomWordsScale);
            sources.add(new WordsDragAndDropSource(wordLabel));
            dragAndDrop.addSource(sources.peek());
            if(randomWordsStyle.background != null)
                wordsTable.add(wordLabel).width(wordLabel.getStyle().background.getMinWidth())
                        .height(wordLabel.getStyle().background.getMinHeight());
            else
                wordsTable.add(wordLabel);
            if((i+1)%wordsPerRow == 0)
                wordsTable.row();
        }

        return wordsTable;
    }

    public void setCorrectWordsStyle(Label.LabelStyle style){
        correctWordsStyle = style;
    }

    public void setRandomWordsStyle(Label.LabelStyle style){
        randomWordsStyle = style;
    }

    public void setDragStyle(Label.LabelStyle style){
        dragStyle = style;
    }

    public void setHoverStyle(Label.LabelStyle style){
        hoverStyle = style;
    }

    public Label.LabelStyle getCorrectWordsStyle(){
        return correctWordsStyle;
    }

    public Label.LabelStyle getRandomWordsStyle(){
        return randomWordsStyle;
    }

    public Label.LabelStyle getDragStyle(){
        return dragStyle;
    }

    public Label.LabelStyle getHoverStyle(){
        return hoverStyle;
    }

    public void setCorrectWordsScale(float scale){
        correctWordsScale = scale*fontToScaledImage*imageScale;
    }

    public void setRandomWordScale(float scale){
        randomWordsScale = scale;
    }

    public void setDragScale(float scale){
        dragScale = scale;
    }

    public void setHoverScale(float scale){
        hoverScale = scale*fontToScaledImage*imageScale;
    }

    public Table getImageTable(){
        return imageTable;
    }

    public Table getWordsTable(){
        return wordsTable;
    }

    public void addToStage(Stage stage){
        stage.addActor(imageTable);
        stage.addActor(wordsTable);
    }

    public void revertWrongAnswer(Label source, Label target){
        source.setVisible(true);
        source.setText(target.getText());
        source.setStyle(randomWordsStyle);
        source.setFontScale(randomWordsScale);

        target.setVisible(true);
        target.setText("");
        target.setStyle(correctWordsStyle);
        target.setFontScale(correctWordsScale);

    }

    public void setCallback(MissingWordsCallback callback){
        this.callback = callback;
    }

    private class WordsDragAndDropSource extends DragAndDrop.Source{

        public WordsDragAndDropSource(Label source){
            super(source);
        }

        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            Label temp = (Label) this.getActor();
            temp.setVisible(false);
            payload.setObject(temp.getText());

            Label dragLabel = new Label(temp.getText(), dragStyle);
            dragLabel.setFontScale(dragScale);
            payload.setDragActor(dragLabel);
            if(temp.getStage() instanceof StandardScreen.StandardStage) {
                StandardScreen.StandardStage stage = (StandardScreen.StandardStage) temp.getStage();
                dragAndDrop.setDragActorPosition(-x - stage.getPadLeft(),
                        -y + dragLabel.getHeight() - stage.getPadBottom());
            }
            else {
                dragAndDrop.setDragActorPosition(-x, -y + dragLabel.getHeight());
            }
            return payload;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
            if(target == null)
                getActor().setVisible(true);
        }
    }

    private class WordsDragAndDropTarget extends DragAndDrop.Target{
        boolean dropped;

        public WordsDragAndDropTarget(Label source){
            super(source);
        }

        @Override
        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            Label sourceLabel = (Label)source.getActor();
            Label targetLabel = (Label)this.getActor();

            targetLabel.setText(sourceLabel.getText());
            targetLabel.setStyle(hoverStyle);
            targetLabel.setFontScale(hoverScale);

            if(dragAndDrop.getDragActor() != null)
                dragAndDrop.getDragActor().setVisible(false);
            return true;
        }

        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            dropped = true;

            Label sourceLabel = (Label)source.getActor();
            Label targetLabel = (Label)this.getActor();

            int wordIndex;
            for(wordIndex=0; wordIndex<missingWords.length; wordIndex++){
                if(sourceLabel.getText().toString().equals(missingWords[wordIndex]))
                    break;
            }

            boolean isCorrect = ((wordIndex!=missingWords.length) &&
                    (imageTable.getChildren().get(wordIndex) == targetLabel));

            targetLabel.setText(sourceLabel.getText());
            if(isCorrect){
                dragAndDrop.removeSource(source);
                dragAndDrop.removeTarget(this);

                if(callback != null)
                    callback.onCorrect(targetLabel);

                //Check if are all correct
                for(int i=0; i<missingWords.length; i++){
                    String text = ((Label)imageTable.getChildren().get(i)).getText().toString();
                    if(!text.equals(missingWords[i]))
                        return;
                }

                if(callback != null)
                    callback.onCompleted();
            }
            else {
                if(callback != null)
                    callback.onWrong(sourceLabel, targetLabel);

            }

        }

        @Override
        public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
            Label targetLabel = (Label)this.getActor();

            targetLabel.setFontScale(correctWordsScale);

            //Not dropped
            if(!dropped){
                targetLabel.setText("");
                targetLabel.setStyle(correctWordsStyle);
            }
            else
                dropped = false;

            if(dragAndDrop.getDragActor() != null)
                dragAndDrop.getDragActor().setVisible(true);

        }
    }

    public interface MissingWordsCallback {
        public void onCorrect(Label target);

        public void onWrong(Label source, Label target);

        public void onCompleted();
    }
}
