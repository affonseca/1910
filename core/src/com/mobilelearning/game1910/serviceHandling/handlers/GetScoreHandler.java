package com.mobilelearning.game1910.serviceHandling.handlers;

import com.mobilelearning.game1910.serviceHandling.json.ScoreData;

/**
 * Created by AFFonseca on 16/07/2015.
 */
public interface GetScoreHandler {

    void onGetScoreSuccess(ScoreData response);
    void onGetScoreError(String error);

}
