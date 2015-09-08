package com.mobilelearning.game1910.serviceHandling.handlers;

import com.mobilelearning.game1910.serviceHandling.json.UserData;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 26-01-2015
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public interface LoginHandler {

    void onLoginSuccess(UserData response);

    void onLoginError(String error);
}
