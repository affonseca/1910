package com.mobilelearning.game1910.serviceHandling.services;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.mobilelearning.game1910.serviceHandling.Errors;
import com.mobilelearning.game1910.serviceHandling.RequestException;
import com.mobilelearning.game1910.serviceHandling.ServiceRequester;
import com.mobilelearning.game1910.serviceHandling.handlers.GetLeaderboardScoresHandler;
import com.mobilelearning.game1910.serviceHandling.json.LeaderboardScoresArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public class GetLeaderboardScoresService implements Service {

    private GetLeaderboardScoresHandler handler;


    public GetLeaderboardScoresService(GetLeaderboardScoresHandler handler) {
        this.handler = handler;
    }

    public void requestLeaderboardScores(long classID, int level1Score, int level2Score,
                                         int level3Score, int level4Score, int levelValue) {

        Map<String, String> parameters = new HashMap<>();

        parameters.put("classID", "" +classID);
        parameters.put("level1Score", "" +level1Score);
        parameters.put("level2Score", "" +level2Score);
        parameters.put("level3Score", "" +level3Score);
        parameters.put("level4Score", "" +level4Score);
        parameters.put("levelValue", "" +levelValue);

        try{
            ServiceRequester serviceRequester = new ServiceRequester(this, parameters);
            serviceRequester.postRequest();
        }
        catch (RequestException e){
            onRequestFailure(Errors.MAX_CONCURRENT_REQUESTS_REACHED.getValue());
        }
    }


    @Override
    public void onRequestSuccess(JsonValue response) {
        LeaderboardScoresArray out;

        try{
            out = LeaderboardScoresArray.load(response);
        }
        catch (SerializationException ex){
            onRequestFailure(Errors.UNKNOWN_ERROR.getValue());
            return;
        }

        handler.onGetLeaderboardScoresSuccess(out);
    }

    @Override
    public void onRequestFailure(String error) {
        switch (error) {
            case "A turma selecionada já não existe":
                handler.onGetLeaderboardScoresClassError();
                break;
            case "Já não pertences à turma selecionada":
                handler.onGetLeaderboardScoresUserError();
                break;
            default:
                handler.onGetLeaderboardScoresError(error);
        }
    }

    @Override
    public String getType() {
        return ServiceType.GET_LEADERBORADS_SCORES.getValue();
    }
}
