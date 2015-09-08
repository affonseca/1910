package com.mobilelearning.game1910.serviceHandling.json;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public class ScoreData {
    public int level1Score;
    public int level2Score;
    public int level3Score;
    public int level4Score;

    public static ScoreData load (JsonValue fatherTree) {
        try {
            Json json = new Json();
            return json.readValue(ScoreData.class, fatherTree.get("scoreData"));
        } catch (SerializationException ex) {
            throw new SerializationException("Error reading scoreData from Json" , ex);
        }
    }
}
