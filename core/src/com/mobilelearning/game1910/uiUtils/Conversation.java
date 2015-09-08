package com.mobilelearning.game1910.uiUtils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 05-03-2015
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class Conversation {
    private ObjectMap<String, DialogBox> people;
    private String speakingOrder[];
    private String conversation[];
    private ConversationCallback callback;
    private int currentDialog;


    public Conversation(String people[], DialogBox dialogBoxes[], String speakingOrder[], String conversation[]){
        this.people = new ObjectMap<>();
        for(int i=0; i<people.length; i++){
            this.people.put(people[i], dialogBoxes[i]);
        }

        this.speakingOrder = speakingOrder;
        this.conversation = conversation;

    }

    public void startConversation(){
        currentDialog = 0;

        for(DialogBox value :people.values().toArray()){
            value.setVisible(false);
            value.setCallback(new DialogCallback() {
                @Override
                public void onEvent(int type, DialogBox source) {
                    if(type == DialogCallback.CLICKED_AFTER_END)
                        continueConversation(source);
                }
            });
        }

        DialogBox first = people.get(speakingOrder[currentDialog]);
        first.setText(conversation[currentDialog]);
        first.setVisible(true);
    }

    private void continueConversation(DialogBox last){
        DialogBox next;
        currentDialog++;

        if(currentDialog == conversation.length){
            for(DialogBox value :people.values().toArray())
                value.setCallback(null);
            if(callback != null)
                callback.onCompleted();
            return;
        }

        next = people.get(speakingOrder[currentDialog]);
        next.setText(conversation[currentDialog]);
        last.setVisible(false);
        next.setVisible(true);

    }

    public void setCallback(ConversationCallback callback){
        this.callback = callback;
    }


    public interface ConversationCallback {
        void onCompleted();
    }
}
