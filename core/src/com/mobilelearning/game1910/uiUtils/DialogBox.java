package com.mobilelearning.game1910.uiUtils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.SavedData;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 16-02-2015
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class DialogBox {

    private final Table container;
    private final ScrollingText scrollingText;
    private Label title;
    private AnimatedActor clickAnimation;

    public DialogBox(CharSequence text, Label.LabelStyle style, float x, float y, float maxX, float maxY){
        this(null, text, style, x, y, maxX, maxY);

    }

    public DialogBox(CharSequence title, CharSequence text, Label.LabelStyle style, float x, float y, float maxX, float maxY){
        container = new Table(){
            @Override
            public void setVisible(boolean visible) {
                super.setVisible(visible);
                if(visible)
                    scrollingText.resume();
                else
                    scrollingText.stop();
            }

        };
        container.setBounds(x, y, maxX, maxY);

        clickAnimation = new AnimatedActor(new AnimationDrawable(Assets.clickAnimation));
        setClickAnimationVisibility(false);

        if(title != null){
            this.title = new Label(title, style);
            container.add(this.title);
            container.row();
        }

        scrollingText =  new ScrollingText(this, text, style, SavedData.getTextSpeed());
        scrollingText.setSound(Assets.textBoxFX);

        container.add(scrollingText);
        container.addActor(clickAnimation);

        container.top();
        container.left();
        container.setTouchable(Touchable.enabled);
        container.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                scrollingText.clicked();

            }
        }) ;
        //container.debug();

    }

    public void setTextPad(float padLeft, float padUp, float padRight, float padBottom){
        float textSizeX = container.getWidth()-padRight-padLeft;
        float textSizeY = container.getHeight()-padUp-padBottom;

        if(title != null){
            textSizeY -= title.getStyle().font.getBounds(title.getText()).height;
        }

        container.getCell(scrollingText).width(textSizeX);
        container.pad(padUp, padLeft, 0, padRight);
        scrollingText.setScrollingBounds(textSizeX, textSizeY);
    }

    public void setClickAnimation(Animation it){
        clickAnimation = new AnimatedActor(new AnimationDrawable(it));
    }

    public AnimatedActor getClickAnimation(){
        return clickAnimation;
    }

    public void setClickAnimationPosition(float x, float y){
        clickAnimation.setPosition(x, y);
    }

    public void setClickAnimationVisibility(boolean isVisible){
        clickAnimation.getDrawable().reset();
        clickAnimation.setVisible(isVisible);
    }

    public void setText(CharSequence text){
        setClickAnimationVisibility(false);
        scrollingText.setText(text);
    }

    public String getText(){
        return scrollingText.getText().toString();
    }

    public void changeTitle(CharSequence newTitle){
        if(title != null){
            title.setText(newTitle);
        }
    }

    public Actor getActor(){
        return container;
    }

    public void setTouchable(Touchable it){
        this.container.setTouchable(it);
    }

    public void setVisible(boolean visible){
        this.container.setVisible(visible);
        if(visible)
            scrollingText.resume();
        else
            scrollingText.stop();
    }

    public void setBackground(Drawable background){
        this.container.setBackground(background);
    }

    public void setSound(Music newSound){
        scrollingText.setSound(newSound);
    }

    public boolean isTextStopped(){
        return  scrollingText.isStopped();
    }

    public void stopText(){
        scrollingText.stop();
    }

    public void resumeText(){
        scrollingText.resume();
    }

    public void setCallback(DialogCallback callback){
        scrollingText.setCallback(this, callback);
    }

    public void addToStage(Stage it){
        it.addActor(container);
    }


}
