package com.mobilelearning.game1910.serviceHandling.services;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.mobilelearning.game1910.serviceHandling.Errors;
import com.mobilelearning.game1910.serviceHandling.RequestException;
import com.mobilelearning.game1910.serviceHandling.ServiceRequester;
import com.mobilelearning.game1910.serviceHandling.handlers.UserClassesHandler;
import com.mobilelearning.game1910.serviceHandling.json.ClassArray;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 26-01-2015
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class UserClassesService implements Service {

    private UserClassesHandler handler;


    public UserClassesService(UserClassesHandler handler) {
        this.handler = handler;
    }

    public void requestUserClasses() {

        try{
            ServiceRequester serviceRequester = new ServiceRequester(this, null);
            serviceRequester.postRequest();
        }
        catch (RequestException e){
            onRequestFailure(Errors.MAX_CONCURRENT_REQUESTS_REACHED.getValue());
        }
    }


    @Override
    public void onRequestSuccess(JsonValue response) {
        ClassArray out;

        try{
            out = ClassArray.load(response);
        }
        catch (SerializationException ex){
            onRequestFailure(Errors.UNKNOWN_ERROR.getValue());
            return;
        }

        handler.onGettingUserClassesSuccess(out);

    }

    @Override
    public void onRequestFailure(String error) {
        handler.onGettingUserClassesError(error);
    }

    @Override
    public String getType() {
        return ServiceType.USER_CLASSES.getValue();
    }
}
