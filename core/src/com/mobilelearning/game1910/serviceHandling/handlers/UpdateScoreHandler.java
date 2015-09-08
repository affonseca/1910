package com.mobilelearning.game1910.serviceHandling.handlers;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public interface UpdateScoreHandler {

    void onUpdateScoreSuccess();

    void onUpdateScoreClassError();

    void onUpdateScoreUserError();

    void onUpdateScoreOtherError(String error);

}
