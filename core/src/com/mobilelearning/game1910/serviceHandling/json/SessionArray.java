package com.mobilelearning.game1910.serviceHandling.json;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 26-01-2015
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class SessionArray {
    public ArrayList<Session> lastSessions;

    public static SessionArray load (String jsonString) {
        try {
            Json json = new Json();
            return json.fromJson(SessionArray.class, jsonString);
        } catch (SerializationException ex) {
            throw new SerializationException("Error reading SessionArray sessionString" , ex);
        }
    }

    public static SessionArray load (JsonValue fatherTree) {
        try {
            Json json = new Json();
            return json.readValue(SessionArray.class, fatherTree.get("sessionArray"));
        } catch (SerializationException ex) {
            throw new SerializationException("Error reading SessionArray sessionString" , ex);
        }
    }
}
