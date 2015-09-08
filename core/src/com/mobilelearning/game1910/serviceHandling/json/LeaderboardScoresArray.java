package com.mobilelearning.game1910.serviceHandling.json;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Created by AFFonseca on 23/07/2015.
 */
public class LeaderboardScoresArray {
    public Array<LeaderboardScore> leaderboardScores;

    public static LeaderboardScoresArray load (JsonValue fatherTree) {
        try {
            Json json = new Json();
            return json.readValue(LeaderboardScoresArray.class, fatherTree.get("leaderboardScoresArray"));
        } catch (SerializationException ex) {
            throw new SerializationException("Error reading leaderboardScoresArray from Json" , ex);
        }
    }
}
