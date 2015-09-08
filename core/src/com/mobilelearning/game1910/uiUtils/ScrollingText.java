package com.mobilelearning.game1910.uiUtils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 25-01-2015
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class ScrollingText extends Label {
    public CharSequence remainingText;
    public CharSequence textBox;
    private CharSequence currentText;
    private float textSpeed;   //Speed in characters/second
    private float lapsedTime;  //Time passed since the last character appeared;

    private boolean allTextAppeared;
    private boolean allTextScrolled;

    private boolean isStopped = false;

    private BitmapFont.TextBounds bounds;
    private int estimatedMaxBoxDimension;

    private DialogCallback callback;
    private DialogBox parent;

    private Music sound;
    //private long soundID = -1;

    public ScrollingText(DialogBox parent, CharSequence text, LabelStyle style, float textSpeed){
        super("", style);

        this.parent = parent;
        this.remainingText = text;
        currentText = "";
        this.setWrap(true);
        this.textSpeed = textSpeed;
        this.lapsedTime = 0.0f;

        if(remainingText != null && remainingText.length() != 0) {
            this.allTextAppeared = false;
            this.allTextScrolled = false;
        }
        else{
            this.allTextAppeared = true;
            this.allTextScrolled = true;
        }
    }

    public void setScrollingBounds(float width, float height){
        this.bounds = new BitmapFont.TextBounds();
        bounds.width = width;
        bounds.height = height;

        if(remainingText != null && remainingText.length() != 0) {
            calculateMaxBoxDimension();
            getNewTextBox();
        }
    }

    @Override
    public void setText(CharSequence newText) {
        remainingText = newText;
        currentText = "";
        this.lapsedTime = 0.0f;

        if(remainingText != null && remainingText.length() != 0) {
            this.allTextAppeared = false;
            this.allTextScrolled = false;
            calculateMaxBoxDimension();
            getNewTextBox();
        }
        else{
            this.allTextAppeared = true;
            this.allTextScrolled = true;
        }
    }

    public boolean isStopped(){
        return  isStopped;
    }

    public void stop(){
        isStopped = true;
    }


    public void resume(){
        isStopped = false;
    }

    private void calculateMaxBoxDimension(){

        //Binary search for estimated max box
        int start = 0;
        int end = remainingText.length()-1;
        int middle = 0;

        while(start <= end){

            middle = start + (end-start)/2;
            if(getBitmapFontCache().getFont().getWrappedBounds(remainingText.subSequence(0, middle), bounds.width).height > bounds.height){
                end = middle-1;
            }
            else
                start = middle + 1;

        }

        estimatedMaxBoxDimension = middle+1;

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible)
            resume();
        else
            stop();
    }

    @Override
    public boolean remove() {
        if(sound != null) sound.stop();
        return super.remove();
    }

    private  void getNewTextBox(){
        if(remainingText.length() == 0){
            allTextScrolled = true;
            return;
        }

        int maxSize = (remainingText.length() < estimatedMaxBoxDimension) ? remainingText.length() : estimatedMaxBoxDimension;
        boolean hasSpaces = true;


        while(maxSize != 0){

            if(hasSpaces && maxSize != remainingText.length()){
                //previous word
                while(maxSize > 0 && remainingText.charAt(maxSize-1) != ' '){
                    maxSize--;
                }

                //No spaces, gotta cut somewhere
                if(maxSize == 0){
                    maxSize = (remainingText.length() < estimatedMaxBoxDimension) ? remainingText.length() : estimatedMaxBoxDimension;
                    hasSpaces = false;
                }
            }

            //We have no spaces. Cutting wherever
            else if(maxSize != remainingText.length()){
                maxSize--;
            }

            if(getBitmapFontCache().getFont().getWrappedBounds(remainingText.subSequence(0, maxSize), bounds.width).height <= bounds.height)
                break;
            else
                maxSize--;

        }

        textBox = remainingText.subSequence(0, maxSize);
        remainingText = remainingText.subSequence(maxSize,remainingText.length());

    }

    public void act (float delta) {

        super.act(delta);

        if(allTextAppeared || isStopped)
            return;

        lapsedTime += delta;
        if(lapsedTime*textSpeed >= 1.0){
            int addedCharacters = (int)(lapsedTime*textSpeed);

            if(sound != null){
                startSound();
            }



            if(currentText.length() + addedCharacters >= textBox.length()){
                currentText = textBox;
                allTextAppeared = true;
                lapsedTime = 0.0f;
                stopSound();
                getNewTextBox();
                checkCallback();
            }
            else{
                currentText = textBox.subSequence(0, currentText.length()+addedCharacters);
                lapsedTime -= (float)addedCharacters/textSpeed;
            }
        }
        super.setText(currentText);
    }



    public void setCallback(DialogBox parent, DialogCallback callback){
        this.parent = parent;
        this.callback = callback;
    }

    private void callCallback(int type){
        if(callback != null){
            callback.onEvent(type, parent);
        }
    }
    
    private void checkCallback(){
        parent.setClickAnimationVisibility(true);
        if(allTextScrolled)
            callCallback(DialogCallback.ALL_END);
        else
            callCallback(DialogCallback.BOX_END);
    }

    public void setSound(Music newSound){
        this.sound = newSound;
    }

    public void startSound(){
        if(sound != null && !sound.isPlaying()){
            //sound.setLooping(true);
            sound.play();
        }
    }

    public void stopSound(){
        if(sound != null && sound.isPlaying()){
            //sound.setLooping(false);
            sound.stop();
        }
    }

    public void clicked(){
        if (allTextAppeared && parent != null){
            parent.setClickAnimationVisibility(false);
            if (allTextScrolled){
                callCallback(DialogCallback.CLICKED_AFTER_END);
            }
            else{
                currentText = "";
                allTextAppeared = false;
            }
        }
        else
            currentText = textBox;
    }
}
