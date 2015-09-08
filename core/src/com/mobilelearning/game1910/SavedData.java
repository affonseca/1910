package com.mobilelearning.game1910;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mobilelearning.game1910.serviceHandling.json.ScoreData;
import com.mobilelearning.game1910.serviceHandling.json.UserData;
import com.mobilelearning.game1910.serviceHandling.json.Class;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 26-01-2015
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
public class SavedData {

    private enum Values{

        DATA_NAME("MobileLearning"),
        TOKEN("token"),
        USERNAME("username"),
        USER_ID("userID"),
        CURRENT_CLASS_NAME("currentClassName"),
        CURRENT_CLASS_TEACHER("currentClassTeacher"),
        CURRENT_CLASS_ID("currentClassID"),
        TEXT_SPEED("textSpeed"),
        COMPLETED_LEVEL("currentLevel"),
        HIGH_SCORES_LEVEL1("highscore1"),
        HIGH_SCORES_LEVEL2("highscore2"),
        HIGH_SCORES_LEVEL3("highscore3"),
        HIGH_SCORES_LEVEL4("highscore4");


        private String value;

        private Values(String value) {
            this.value = value;
        }

        private String getValue(){
            return value;
        }

    }

    private static Preferences userData;

    public static void loadSavedData(){
        userData = Gdx.app.getPreferences(Values.DATA_NAME.getValue());
    }

    public static String getToken(){
        return userData.getString(Values.TOKEN.getValue(), null);
    }

    public static String getUsername(){
        return userData.getString(Values.USERNAME.getValue(), null);
    }

    public static String getUserID(){
        return userData.getString(Values.USER_ID.getValue(), null);
    }

    public static String getCurrentClassName(){
        return userData.getString(Values.CURRENT_CLASS_NAME.getValue(), null);
    }

    public static String getCurrentClassTeacher(){
        return userData.getString(Values.CURRENT_CLASS_TEACHER.getValue(), null);
    }

    public static String getCurrentClassID(){
        return userData.getString(Values.CURRENT_CLASS_ID.getValue(), null);
    }

    public static float getTextSpeed(){
        return userData.getFloat(Values.TEXT_SPEED.getValue(), 30f);
    }

    public static int getCurrentLevel(){
        return userData.getInteger(Values.COMPLETED_LEVEL.getValue(), 1890);
    }

    public static int getLevel1Highscore(){
        return userData.getInteger(Values.HIGH_SCORES_LEVEL1.getValue(), 0);
    }

    public static int getLevel2Highscore(){
        return userData.getInteger(Values.HIGH_SCORES_LEVEL2.getValue(), 0);
    }

    public static int getLevel3Highscore(){
        return userData.getInteger(Values.HIGH_SCORES_LEVEL3.getValue(), 0);
    }

    public static int getLevel4Highscore(){
        return userData.getInteger(Values.HIGH_SCORES_LEVEL4.getValue(), 0);
    }

    public static void setUserData(UserData user){
        userData.putString(Values.USER_ID.getValue(), "" +user.ID);
        userData.putString(Values.USERNAME.getValue(), "" +user.username);
        userData.putString(Values.TOKEN.getValue(), "" +user.hash);
        userData.flush();
    }

    public static void setCurrentClass(Class newClass){
        userData.putString(Values.CURRENT_CLASS_ID.getValue(), "" +newClass.classID);
        userData.putString(Values.CURRENT_CLASS_NAME.getValue(), "" +newClass.className);
        userData.putString(Values.CURRENT_CLASS_TEACHER.getValue(), "" +newClass.classTeacher);
        userData.flush();
    }

    public static void  setTextSpeed(float speed){
        userData.putFloat(Values.TEXT_SPEED.getValue(), speed);
        userData.flush();
    }

    public static void setCurrentLevel(int currentLevel){
        userData.putInteger(Values.COMPLETED_LEVEL.getValue(), currentLevel);
        userData.flush();
    }

    public static void setHighscores(ScoreData ScoreData){
        userData.putInteger(Values.HIGH_SCORES_LEVEL1.getValue(), ScoreData.level1Score);
        userData.putInteger(Values.HIGH_SCORES_LEVEL2.getValue(), ScoreData.level2Score);
        userData.putInteger(Values.HIGH_SCORES_LEVEL3.getValue(), ScoreData.level3Score);
        userData.putInteger(Values.HIGH_SCORES_LEVEL4.getValue(), ScoreData.level4Score);
        userData.flush();
    }

    public static void setLevel1Highscore(int score){
        if(score > userData.getInteger(Values.HIGH_SCORES_LEVEL1.getValue(), 0)){
            userData.putInteger(Values.HIGH_SCORES_LEVEL1.getValue(), score);
            userData.flush();
        }
    }

    public static void setLevel2Highscore(int score){
        if(score > userData.getInteger(Values.HIGH_SCORES_LEVEL2.getValue(), 0)){
            userData.putInteger(Values.HIGH_SCORES_LEVEL2.getValue(), score);
            userData.flush();
        }
    }

    public static void setLevel3Highscore(int score){
        if(score > userData.getInteger(Values.HIGH_SCORES_LEVEL3.getValue(), 0)){
            userData.putInteger(Values.HIGH_SCORES_LEVEL3.getValue(), score);
            userData.flush();
        }
    }

    public static void setLevel4Highscore(int score){
        if(score > userData.getInteger(Values.HIGH_SCORES_LEVEL4.getValue(), 0)){
            userData.putInteger(Values.HIGH_SCORES_LEVEL4.getValue(), score);
            userData.flush();
        }
    }

    public static void clearUserData(){
        userData.clear();
        userData.flush();
    }

}
