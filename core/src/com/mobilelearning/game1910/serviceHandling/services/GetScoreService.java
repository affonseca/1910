package com.mobilelearning.game1910.serviceHandling.services;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.mobilelearning.game1910.serviceHandling.Errors;
import com.mobilelearning.game1910.serviceHandling.RequestException;
import com.mobilelearning.game1910.serviceHandling.ServiceRequester;
import com.mobilelearning.game1910.serviceHandling.handlers.GetScoreHandler;
import com.mobilelearning.game1910.serviceHandling.json.ScoreData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public class GetScoreService implements Service {

    private GetScoreHandler handler;


    public GetScoreService(GetScoreHandler handler) {
        this.handler = handler;
    }

    public void requestScore(long classID) {

        Map<String, String> parameters = new HashMap<>();

        parameters.put("classID", "" +classID);

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
        ScoreData out;

        try{
            out = ScoreData.load(response);
        }
        catch (SerializationException ex){
            onRequestFailure(Errors.UNKNOWN_ERROR.getValue());
            return;
        }

        handler.onGetScoreSuccess(out);

    }

    @Override
    public void onRequestFailure(String error) {
        handler.onGetScoreError(error);
    }

    @Override
    public String getType() {
        return ServiceType.GET_SCORE.getValue();
    }
}
