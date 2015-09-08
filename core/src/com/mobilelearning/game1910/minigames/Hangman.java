package com.mobilelearning.game1910.minigames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mobilelearning.game1910.screens.StandardScreen;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 02-03-2015
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
public class Hangman extends Table {
    private DragAndDrop dragAndDrop;

    private Label.LabelStyle spacesToFillStyle;
    private Label.LabelStyle randomLettersStyle;
    private Label.LabelStyle afterDragStyle;
    private Label.LabelStyle dragStyle;
    private Label.LabelStyle hoverStyle;
    private Label.LabelStyle spaceFilledStyle;
    private Label.LabelStyle wordInPlaceStyle;
    private ObjectMap<Label, DragAndDrop.Target> targets;
    private ObjectMap<Label, DragAndDrop.Source> sources;

    private char spaceToFillText;

    private float spacesToFillScale = 1f, randomLettersScale = 1f,
            dragStartScale = 1f, dragScale = 1.5f, hoverScale = 3f, spaceFilledScale = 1f;

    private Label spacesToFill [];
    private Label randomWords [];
    private String correctAnswer;

    public Hangman(Skin uiSkin){
        super();
        this.dragAndDrop = new DragAndDrop();
        this.targets = new ObjectMap<>();
        this.sources = new ObjectMap<>();

        afterDragStyle = null;
        dragStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        hoverStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.LIGHT_GRAY);
        spacesToFillStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        randomLettersStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        wordInPlaceStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.BLACK);
        spaceFilledStyle = new Label.LabelStyle(uiSkin.getFont("default-font"), Color.WHITE);
        spaceToFillText = '?';
    }

    public Hangman (Skin uiSkin, String correctWord, int lettersPerRow, int correctlyPlacedLetters){
        this(uiSkin);
        setWord(correctWord, lettersPerRow, correctlyPlacedLetters);
    }

    public Hangman (Skin uiSkin, String correctWord){
        this(uiSkin, correctWord, correctWord.length(), 0);
    }

    public void setWord(String correctWord, int lettersPerRow, int correctlyPlacedLetters){
        //clear();
        correctAnswer = correctWord;

        //Getting random letters to be put in place
        boolean [] lettersToPutInPlace = new boolean[correctWord.length()];

        //Creating an index array
        Array<Integer> wordIndexes = new Array<>();
        for(int i=0; i<correctWord.length(); i++)
            wordIndexes.add(i);

        //Getting a random index, removing from the list (to not happen twice) and adding to letters to put in place
        for(int i=0; i<correctlyPlacedLetters && i<correctWord.length(); i++) {
            int randomIndex = wordIndexes.removeIndex(MathUtils.random(0, wordIndexes.size - 1));
            lettersToPutInPlace[randomIndex] = true;
        }

        boolean lettersToPutInPlaceRandom [] = lettersToPutInPlace.clone();

        //Randomizing the letters to put on top
        StringBuilder randomLettersBuilder = new StringBuilder(correctWord);
        for(int i=0; i<randomLettersBuilder.length(); i++){
            int randomIndex = MathUtils.random(i, randomLettersBuilder.length()-1);
            char aux = randomLettersBuilder.charAt(randomIndex);
            randomLettersBuilder.setCharAt(randomIndex, randomLettersBuilder.charAt(i));
            randomLettersBuilder.setCharAt(i, aux);

            boolean aux2 = lettersToPutInPlaceRandom[randomIndex];
            lettersToPutInPlaceRandom[randomIndex] = lettersToPutInPlaceRandom[i];
            lettersToPutInPlaceRandom[i] = aux2;
        }

        String randomLettersString = randomLettersBuilder.toString();

        int correctWordLength = correctWord.length();

        int randomLettersLength = randomLettersString.length();
        randomWords = new Label[randomLettersLength];
        for(int i=0; i<randomLettersLength; i++) {
            randomWords[i] = new Label(randomLettersString.subSequence(i, i + 1), randomLettersStyle);
            randomWords[i].setName(randomLettersString.subSequence(i, i + 1).toString());
            randomWords[i].setAlignment(Align.center);
            randomWords[i].setFontScale(randomLettersScale);

            if(lettersToPutInPlaceRandom[i]){
                randomWords[i].setStyle(afterDragStyle);
                randomWords[i].setFontScale(dragStartScale);
            }
            else{
                sources.put(randomWords[i], new LettersDragAndDropSource(randomWords[i]));
                dragAndDrop.addSource(sources.get(randomWords[i]));
            }
            if(randomWords[i].getStyle().background != null)
                add(randomWords[i]).width(randomWords[i].getStyle().background.getMinWidth())
                        .height(randomWords[i].getStyle().background.getMinHeight());
            else
                add(randomWords[i]);

            if ((i + 1) % lettersPerRow == 0)
                row();
        }

        spacesToFill = new Label[correctWordLength];
        for(int i=0; i<correctWordLength; i++){

            spacesToFill[i] = new Label(spaceToFillText +"", spacesToFillStyle);
            spacesToFill[i].setAlignment(Align.center);
            if(lettersToPutInPlace[i]){
                spacesToFill[i].setText(correctWord.charAt(i) +"");
                spacesToFill[i].setName(correctWord.charAt(i) + "");
                spacesToFill[i].setStyle(wordInPlaceStyle);
                spacesToFill[i].setFontScale(spaceFilledScale);
            }
            else {
                spacesToFill[i].setName(spaceToFillText + "");
                spacesToFill[i].setFontScale(spacesToFillScale);
                targets.put(spacesToFill[i], new LettersDragAndDropTarget(spacesToFill[i]));
                dragAndDrop.addTarget(targets.get(spacesToFill[i]));

            }
            if(spacesToFill[i].getStyle().background != null)
                add(spacesToFill[i]).width(spacesToFill[i].getStyle().background.getMinWidth())
                        .height(spacesToFill[i].getStyle().background.getMinHeight());
            else
                add(spacesToFill[i]);
        }
        row();


    }

    public void setWord (String correctWord){
        setWord(correctWord, correctWord.length(),0);
    }

    private Label getRandomWordWithoutText(String it){
        for(Label out:randomWords)
            if(out.getText().toString().equals(it) && out.getStyle() == randomLettersStyle)
                return out;
        return null;
    }

    private Label getRandomWordWithText(String it){
        for(Label out:randomWords)
            if(out.getText().toString().equals(it) && out.getStyle() != randomLettersStyle)
                return out;
        return null;
    }

    private boolean isSpaceToFill(Label it){
        for(Label spaceToFill:spacesToFill){
            if(spaceToFill == it)
                return  true;
        }

        return false;

    }

    public void setSpacesToFillStyle(Label.LabelStyle style){
        spacesToFillStyle = style;
    }

    public void setRandomLettersStyle(Label.LabelStyle style){
        randomLettersStyle = style;
    }

    public void setAfterDragStyle(Label.LabelStyle style){
        afterDragStyle = style;
    }

    public void setDragStyle(Label.LabelStyle style){
        dragStyle = style;
    }

    public void setHoverStyle(Label.LabelStyle style){
        hoverStyle = style;
    }

    public void setSpaceFilledStyle(Label.LabelStyle style){
        spaceFilledStyle = style;
    }

    public void setWordInPlaceStyle(Label.LabelStyle style){
        wordInPlaceStyle = style;
    }

    public Label.LabelStyle getSpacesToFillStyle(){
        return spacesToFillStyle;
    }

    public Label.LabelStyle getRandomLettersStyle(){
        return randomLettersStyle;
    }

    public Label.LabelStyle getAfterDragStyle(){
        return afterDragStyle;
    }

    public Label.LabelStyle getDragStyle(){
        return dragStyle;
    }

    public Label.LabelStyle getHoverStyle(){
        return hoverStyle;
    }

    public Label.LabelStyle getSpaceFilledStyle(){ return spaceFilledStyle; }

    public char getSpaceToFillText() {
        return spaceToFillText;
    }

    public void setSpacesToFillScale(float scale){
        spacesToFillScale = scale;
    }

    public void setRandomLettersScale(float scale){
        randomLettersScale = scale;
    }

    public void setDragStartScale(float scale){
        dragStartScale = scale;
    }

    public void setDragScale(float scale){
        dragScale = scale;
    }

    public void setHoverScale(float scale){
        hoverScale = scale;
    }

    public void setSpaceToFillText(char text){
        this.spaceToFillText = text;
    }

    public void setSpaceFilledScale(float scale){
        spaceFilledScale = scale;
    }

    public void setPadBetweenAnswerAndRandom(float pad){
        for(Label space:spacesToFill)
            this.getCell(space).padTop(pad);
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public boolean isCorrect(){
        for(int i=0; i<spacesToFill.length; i++){
            if(spacesToFill[i].getText().toString().compareTo(correctAnswer.substring(i, i + 1)) != 0){
                return  false;
            }
        }
        return true;
    }

    public Label [] getRandomWordsLabels(){
        return randomWords;
    }

    public Label [] getSpacesToFillLabels(){
        return spacesToFill;
    }

    @Override
    public void clearActions() {
        super.clearActions();
        dragAndDrop.clear();
    }

    public boolean allWordsInPlace(){
        Array<Label> availableLetters = new Array<>();
        for(Label letter:spacesToFill){
            if(letter.getStyle() != wordInPlaceStyle){
                availableLetters.add(letter);
            }
        }

        return (availableLetters.size == 0);
    }

    public boolean putWordInPlace(){
        Array<Label> availableLetters = new Array<>();
        for(Label letter:spacesToFill){
            if(letter.getStyle() != wordInPlaceStyle){
                availableLetters.add(letter);
            }
        }

        if(availableLetters.size == 0)
            return false;

        Label chosenLetter = availableLetters.get(MathUtils.random(0, availableLetters.size-1));
        if(sources.containsKey(chosenLetter))
            dragAndDrop.removeSource(sources.remove(chosenLetter));
        dragAndDrop.removeTarget(targets.remove(chosenLetter));
        chosenLetter.setStyle(wordInPlaceStyle);

        for(int i=0; i<spacesToFill.length; i++){
            if(spacesToFill[i] == chosenLetter){
                chosenLetter.setText("" + correctAnswer.charAt(i));

                Label otherLetter = getRandomWordWithoutText("" + correctAnswer.charAt(i));
                if(otherLetter == null)
                    break;

                if(afterDragStyle != null)
                    otherLetter.setStyle(afterDragStyle);
                else
                    otherLetter.setVisible(false);
                otherLetter.setFontScale(dragStartScale);
                dragAndDrop.removeSource(sources.remove(otherLetter));
                break;
            }
        }

        return true;

    }

    private class LettersDragAndDropSource extends DragAndDrop.Source{

        public LettersDragAndDropSource(Label source){
            super(source);
        }

        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            Label temp = (Label) this.getActor();

            if(!isSpaceToFill(temp)) {
                if (afterDragStyle == null)
                    temp.setVisible(false);
                else {
                    temp.setStyle(afterDragStyle);
                    temp.setFontScale(dragStartScale);
                }
            }
            else{
                temp.setStyle(spacesToFillStyle);
                temp.setFontScale(spacesToFillScale);
                temp.setText("" +spaceToFillText);
            }

            if(dragStyle != null){
                Label dragLabel = new Label(temp.getName(), dragStyle);
                dragLabel.setFontScale(dragScale);
                if(dragLabel.getStyle().background != null){
                    dragLabel.setAlignment(Align.center);
                    dragLabel.setSize(
                            dragLabel.getStyle().background.getMinWidth(),
                            dragLabel.getStyle().background.getMinHeight());
                }

                payload.setDragActor(dragLabel);
                if(temp.getStage() instanceof StandardScreen.StandardStage) {
                    StandardScreen.StandardStage stage = (StandardScreen.StandardStage) temp.getStage();
                    dragAndDrop.setDragActorPosition(-x - stage.getPadLeft(),
                            -y + dragLabel.getHeight() - stage.getPadBottom());
                }
                else {
                    dragAndDrop.setDragActorPosition(-x, -y + dragLabel.getHeight());
                }
            }

            return payload;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
            Label sourceLabel = (Label)this.getActor();
            Label targetLabel = null;
            if(target != null)
                targetLabel= (Label)target.getActor();

            if(isSpaceToFill(sourceLabel)){
                sourceLabel.setText(sourceLabel.getName());
                if(sourceLabel.getName().equals("" +spaceToFillText)) {
                    sourceLabel.setStyle(spacesToFillStyle);
                    sourceLabel.setFontScale(spacesToFillScale);
                }
                else {
                    sourceLabel.setStyle(spaceFilledStyle);
                    sourceLabel.setFontScale(spaceFilledScale);
                }
            }
            else if(target == null || targetLabel.getText().charAt(0) != sourceLabel.getText().charAt(0)){
                if(afterDragStyle == null)
                    sourceLabel.setVisible(true);
                else{
                    sourceLabel.setStyle(randomLettersStyle);
                    sourceLabel.setFontScale(randomLettersScale);
                }
            }

            payload.setObject("dragStopped");
        }
    }

    private class LettersDragAndDropTarget extends DragAndDrop.Target{
        private boolean dropOccurred;

        public LettersDragAndDropTarget(Label source){
            super(source);
        }

        @Override
        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            Label targetLabel = (Label)this.getActor();
            Label sourceLabel = (Label)source.getActor();

            targetLabel.setStyle(hoverStyle);
            targetLabel.setFontScale(hoverScale);
            targetLabel.setText(sourceLabel.getName());

            if(dragAndDrop.getDragActor() != null)
                dragAndDrop.getDragActor().setVisible(false);
            return true;
        }

        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            dropOccurred = true;

            Label sourceLabel = (Label)source.getActor();
            Label targetLabel = (Label)this.getActor();

            String aux = targetLabel.getName();
            targetLabel.setText(sourceLabel.getName());
            targetLabel.setName(sourceLabel.getName());
            if(isSpaceToFill(sourceLabel)) {
                sourceLabel.setText(aux);
                sourceLabel.setName(aux);
            }

            if(aux.charAt(0) == spaceToFillText){
                dragAndDrop.removeSource(sources.remove(sourceLabel));
                sources.put(targetLabel, new LettersDragAndDropSource(targetLabel));
                dragAndDrop.addSource(sources.get(targetLabel));
            }
            else if(isSpaceToFill(targetLabel) && !isSpaceToFill(sourceLabel)){
                dragAndDrop.removeSource(sources.remove(sourceLabel));
                Label newSource = getRandomWordWithText(aux);
                if(newSource != null){
                    sources.put(newSource, new LettersDragAndDropSource(newSource));
                    dragAndDrop.addSource(sources.get(newSource));
                    newSource.setStyle(randomLettersStyle);
                    newSource.setFontScale(randomLettersScale);
                }
            }

        }

        @Override
        public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {

            Label targetLabel = (Label)this.getActor();
            Label sourceLabel = (Label)source.getActor();

            if(isSpaceToFill(targetLabel)){
                if(targetLabel.getName().equals("" +spaceToFillText) ||
                        (targetLabel == sourceLabel && !dropOccurred && payload.getObject() == null)) {
                    targetLabel.setText("" +spaceToFillText);
                    targetLabel.setStyle(spacesToFillStyle);
                    targetLabel.setFontScale(spacesToFillScale);
                }
                else {
                    targetLabel.setStyle(spaceFilledStyle);
                    targetLabel.setFontScale(spaceFilledScale);
                }
            }
            else{
                targetLabel.setStyle(randomLettersStyle);
                targetLabel.setFontScale(randomLettersScale);
            }

            if(dragAndDrop.getDragActor() != null)
                dragAndDrop.getDragActor().setVisible(true);
            if(targetLabel != sourceLabel || dropOccurred)
                targetLabel.setText(targetLabel.getName());

            payload.setObject(null);
            dropOccurred = false;
        }
    }
}
