package com.mobilelearning.game1910.uiUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mobilelearning.game1910.Assets;
import com.mobilelearning.game1910.accessors.ActorAccessor;
import com.mobilelearning.game1910.screens.StandardScreen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by AFFonseca on 21/06/2015.
 */
public class ReporterThought {
    private  ReporterThoughtCallback callback;
    private StandardScreen.StandardStage stage;
    private DialogBox thought;
    private Image reporter;
    private float finalReporterY = -135f;

    public ReporterThought(StandardScreen.StandardStage stage, BitmapFont font){
        this.stage = stage;

        reporter = new Image(Assets.miscellaneous.findRegion("reporter_thought_reporter"));
        reporter.setPosition((stage.getWidth() - reporter.getPrefWidth()) / 2f, finalReporterY);

        TextureRegionDrawable thinkBalloon = new TextureRegionDrawable(Assets.miscellaneous.findRegion("reporter_thought_balloon"));
        Label.LabelStyle thinkStyle = new Label.LabelStyle(font, Color.GRAY);
        thought = new DialogBox(null, thinkStyle,
                (stage.getWidth()-thinkBalloon.getMinWidth())/2, 505f,
                thinkBalloon.getMinWidth(), thinkBalloon.getMinHeight());
        thought.setBackground(thinkBalloon);
        thought.setTextPad(50f, 50f, 50f, 170f);
        thought.setClickAnimationPosition(
                thinkBalloon.getMinWidth() - thought.getClickAnimation().getPrefWidth() + 30f, 85f);

    }

    public void run(String text, final TweenManager tweenManager){
        thought.setText(text);
        thought.stopText();
        thought.setVisible(false);
        thought.addToStage(stage);

        reporter.setY(-reporter.getPrefHeight()-stage.getPadBottom());
        stage.addActor(reporter);

        Tween.to(reporter, ActorAccessor.MOVE_Y, 0.5f).target(finalReporterY)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        thought.setVisible(true);
                        thought.resumeText();
                    }
                })
                .start(tweenManager);

        thought.setCallback(new DialogCallback() {
            @Override
            public void onEvent(int type, DialogBox source) {
                if (type == CLICKED_AFTER_END) {
                    stage.getRoot().removeActor(thought.getActor());
                    Tween.to(reporter, ActorAccessor.MOVE_Y, 0.5f).target(-reporter.getPrefHeight() - stage.getPadBottom())
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    stage.getRoot().removeActor(reporter);
                                    if (callback != null) {
                                        callback.onCompleted();
                                        callback = null;
                                    }
                                }
                            })
                            .start(tweenManager);
                }
            }
        });
    }

    public void setCallback(ReporterThoughtCallback callback){
        this.callback = callback;
    }

    public interface ReporterThoughtCallback {

        void onCompleted();
    }

}
