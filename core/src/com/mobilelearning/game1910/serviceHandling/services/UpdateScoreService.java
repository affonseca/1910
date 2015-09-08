package com.mobilelearning.game1910.serviceHandling.services;

import com.badlogic.gdx.utils.JsonValue;
import com.mobilelearning.game1910.serviceHandling.Errors;
import com.mobilelearning.game1910.serviceHandling.RequestException;
import com.mobilelearning.game1910.serviceHandling.ServiceRequester;
import com.mobilelearning.game1910.serviceHandling.handlers.UpdateScoreHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public class UpdateScoreService implements Service{
    private UpdateScoreHandler handler;


    public UpdateScoreService(UpdateScoreHandler handler) {
        this.handler = handler;
    }

    public void requestUpdateScore(long classID, int level1Score, int level2Score,
                                   int level3Score, int level4Score) {

        Map<String, String> parameters = new HashMap<>();

        parameters.put("classID", "" +classID);
        parameters.put("level1Score", "" +level1Score);
        parameters.put("level2Score", "" +level2Score);
        parameters.put("level3Score", "" +level3Score);
        parameters.put("level4Score", "" +level4Score);

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
        handler.onUpdateScoreSuccess();
    }

    @Override
    public void onRequestFailure(String error) {
        switch (error) {
            case "A turma selecionada já não existe":
                handler.onUpdateScoreClassError();
                break;
            case "Já não pertences à turma selecionada":
                handler.onUpdateScoreUserError();
                break;
            default:
                handler.onUpdateScoreOtherError(error);
        }
    }

    @Override
    public String getType() {
        return ServiceType.UPDATE_SCORE.getValue();
    }
}
