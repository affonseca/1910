package com.mobilelearning.game1910.serviceHandling.services;

import com.badlogic.gdx.utils.*;
import com.mobilelearning.game1910.serviceHandling.Errors;
import com.mobilelearning.game1910.serviceHandling.RequestException;
import com.mobilelearning.game1910.serviceHandling.ServiceRequester;
import com.mobilelearning.game1910.serviceHandling.handlers.LoginHandler;
import com.mobilelearning.game1910.serviceHandling.json.UserData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 26-01-2015
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class LoginService implements Service {

    private LoginHandler handler;


    public LoginService(LoginHandler handler) {
        this.handler = handler;
    }

    public void requestLogin(String username, String password) {

        Map<String, String> parameters = new HashMap<>();

        parameters.put("username", username);
        parameters.put("password", password);

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
        UserData out;

        try{
            out = UserData.load(response);
        }
        catch (SerializationException ex){
            onRequestFailure(Errors.UNKNOWN_ERROR.getValue());
            return;
        }

        handler.onLoginSuccess(out);

    }

    @Override
    public void onRequestFailure(String error) {
        handler.onLoginError(error);
    }

    @Override
    public String getType() {
        return ServiceType.LOGIN.getValue();
    }
}
